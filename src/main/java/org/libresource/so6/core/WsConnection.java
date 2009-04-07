/**
 * LibreSource
 * Copyright (C) 2004-2008 Artenum SARL / INRIA
 * http://www.libresource.org - contact@artenum.com
 *
 * This file is part of the LibreSource software, 
 * which can be used and distributed under license conditions.
 * The license conditions are provided in the LICENSE.TXT file 
 * at the root path of the packaging that enclose this file. 
 * More information can be found at 
 * - http://dev.libresource.org/home/license
 *
 * Initial authors :
 *
 * Guillaume Bort / INRIA
 * Francois Charoy / Universite Nancy 2
 * Julien Forest / Artenum
 * Claude Godart / Universite Henry Poincare
 * Florent Jouille / INRIA
 * Sebastien Jourdain / INRIA / Artenum
 * Yves Lerumeur / Artenum
 * Pascal Molli / Universite Henry Poincare
 * Gerald Oster / INRIA
 * Mariarosa Penzi / Artenum
 * Gerard Sookahet / Artenum
 * Raphael Tani / INRIA
 *
 * Contributors :
 *
 * Stephane Bagnier / Artenum
 * Amadou Dia / Artenum-IUP Blois
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package org.libresource.so6.core;

import org.libresource.so6.core.client.ClientI;
import org.libresource.so6.core.command.Command;
import org.libresource.so6.core.command.fs.AddBinaryFile;
import org.libresource.so6.core.command.fs.AddDir;
import org.libresource.so6.core.command.fs.Remove;
import org.libresource.so6.core.command.fs.UpdateBinaryFile;
import org.libresource.so6.core.command.text.AddTxtFile;
import org.libresource.so6.core.command.xml.AddXmlFile;
import org.libresource.so6.core.compress.CompressUtil;
import org.libresource.so6.core.engine.DBType;
import org.libresource.so6.core.engine.FileParser;
import org.libresource.so6.core.engine.FilterIgnoreFile;
import org.libresource.so6.core.engine.OpVector;
import org.libresource.so6.core.engine.OpVectorFsImpl;
import org.libresource.so6.core.engine.PatchFile;
import org.libresource.so6.core.engine.PatchRepository;
import org.libresource.so6.core.engine.RefCopy;
import org.libresource.so6.core.engine.util.Base64;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.engine.util.ObjectCloner;
import org.libresource.so6.core.report.CVSReportMaker;
import org.libresource.so6.core.tf.TransformationFunctions;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.lang.reflect.Constructor;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Logger;


/*
   class Workspace:
   Responsability:
   manage the persistence of workspace data
   Collaboration:
   Log
 */

/**
 * The <code>WsConnection</code> class represents a connection to a So6 Queue.
 * This class is used to manage the synchronisation process.
 *
 * @author Smack
 * @version 1.0, 26/05/04
 * @see org.libresource.so6.core.Workspace
 * @see org.libresource.so6.core.client.ClientI
 * @since JDK1.4
 */
public class WsConnection implements java.io.Serializable {
    /** Name of the file where the WsConnection meta data will be stored */
    public static final String SO6_WSC_FILE = "so6.properties";

    /** Name of last patch file sent during the commit */
    public static final String SO6_LAST_COMMIT_PATCH_FILE = "lastPatch.commit";

    /**
     * Property used in the SO6_WSC_FILE property file to represente the
     * worskpace base path
     */
    public static final String PATH = "so6.path";

    /**
     * Property used in the SO6_WSC_FILE property file to represente the data
     * path of that connection
     */
    public static final String DATAPATH = "so6.connection.path";

    /**
     * Property used in the SO6_WSC_FILE property file to represente the name of
     * the concrete class of the ClientI interface that need to be used for that
     * connection
     */
    public static final String SYNC_CLIENT_NAME = "so6.clienti.name";

    /**
     * Property used in the SO6_WSC_FILE property file to represente the name of
     * that connection in order to know who did what?
     */
    public static final String WS_NAME = "so6.wsc.name";

    /**
     * Property used in the SO6_WSC_FILE propertie file is use to set the XML
     * merge (true/false)
     */
    public static final String XML_AUTO_DETECTION = "so6.xml.autodetect";

    /**
     * Property used in the SO6_WSC_FILE property file to represente the current
     * corrupted state
     */
    public static final String CORRUPTION_STATE = "so6.corruption.state";

    /**
     * Property used in the SO6_WSC_FILE property file to represente the last
     * integrated ticket before any corruption
     */
    public static final String LAST_TICKET_BEFORE_CORRUPTION = "so6.last.ticket.before.corruption";

    /**
     * Property used in the SO6_WSC_FILE property file to represente the list of
     * the binary extension
     */
    public static final String LAST_BIN_EXT = "so6.binary.ext";

    /** Constant for the corruption property : WsConnection not corrupted */
    public static final int NO_CORRUPTION = -1;

    /**
     * Constant for the corruption property : WsConnection corrupted while
     * patching local copy
     */
    public static final int UPDATE_CORRUPTION_PATCH_LOCAL_COPY = 1;

    /**
     * Constant for the corruption property : WsConnection corrupted while
     * patching the local reference copy after an update
     */
    public static final int UPDATE_CORRUPTION_PATCH_REF_COPY = 2;

    /**
     * Constant for the corruption property : WsConnection corrupted while
     * saving patch in the applied patch directory after an update
     */
    public static final int UPDATE_CORRUPTION_SAVE_PATCH = 3;

    /**
     * Constant for the corruption property : WsConnection corrupted while
     * removing the applied patch in the received directory after an update
     */
    public static final int UPDATE_CORRUPTION_REMOVE_PATCH = 4;

    /**
     * Constant for the corruption property : WsConnection corrupted while
     * patching the local reference copy after a commit
     */
    public static final int COMMIT_CORRUPTION_PATCH_REF_COPY = 11;

    /**
     * Constant for the corruption property : WsConnection corrupted while
     * saving patch in the applied patch directory after a commit
     */
    public static final int COMMIT_CORRUPTION_SAVE_PATCH = 12;

    /**
     * Constant for the corruption property : WsConnection corrupted while
     * updating the received ticket
     */
    public static final int COMMIT_CORRUPTION_UPDATE_RECEIVED_TICKET = 13;

    /**
     * Constant for the corruption property: WsConnection corrupted while trying
     * to replace the local dbType by the ref dbType
     */
    public static final int COMMIT_CORRUPTION_UPDATE_LOCAL_DBTYPE = 14;
    private ClientI clienti;
    private TransformationFunctions de;
    private PatchRepository appliedPatch;
    private PatchRepository receivedPatch;
    private DBType dbtype;
    private RefCopy refcopy;
    private Vector patchFilter;

    //
    private StringBuffer report;

    //
    private OpVector mergedOp;
    private long mergedTime;
    private String wscPath; // path of property file...
    private Properties prop = new Properties();
    private boolean loaded = false;
    private boolean needToCheckLocalOp = true;

    // For simulation
    private boolean simulationMode = false;
    private String simulationOutputDir = null;

    /**
     * Load an EXISTING <code>WsConnection</code> object in order to access to
     * the synchronisation process.
     *
     * @param wscPath
     *            <code>SO6_WSC_FILE<code> file path
     */
    public WsConnection(String wscPath) throws IOException {
        this.wscPath = wscPath;
        load(wscPath);
        FileUtils.createDirIfNotExist(getDataPath());
        FileUtils.createDirIfNotExist(getDBTypePath());
        FileUtils.createDirIfNotExist(getRefCopyPath());
        this.de = new TransformationFunctions(this);
        this.refcopy = new RefCopy(this);
        this.dbtype = new DBType(this.getDBTypePath() + File.separator + "local.dbtype", getLastBinExt());
        this.appliedPatch = new PatchRepository(this, "APPLIED");
        this.receivedPatch = new PatchRepository(this, "RECEIVED");
    }

    /**
     * Return the workspace connection
     *
     * @return The workspace of that connection
     */
    public Workspace getWorkspace() throws Exception {
        return new Workspace(getPath());
    }

    /**
     * The logger is used to monitor what it's going on an update or commit
     * operation. The logger follow the XML syntaxe in order to represent the
     * current state of the current Job.
     * <p>
     * To get more information look at the
     * org.libresource.so6.engine.log.monitoring.XMLLogHandler source code
     *
     * @return The monitoring XML logger
     */
    /**
     * Return the base path where the DbType are stored (local.dbtype /
     * refcopy.dbtype)
     *
     * @return path of the directory
     */
    public String getDBTypePath() {
        return getDataPath() + File.separator + "DBTYPE";
    }

    /**
     * Return the base path where the local command are temporary stored when
     * they are computed from the diff beetwen the refcopy and the local copy.
     *
     * @return path of the directory
     */
    public String getLocalCmdPath() {
        return getDataPath() + File.separator + "LOCALS";
    }

    /**
     * Return the base path where the attachement of the local command are
     * temporary stored.
     *
     * @return path of the directory
     */
    public String getAttachFilePath() {
        return getDataPath() + File.separator + "ATTACH";
    }

    /**
     * Return the base path where the local reference copy is stored.
     *
     * @return path of the directory
     */
    public String getRefCopyPath() {
        return getDataPath() + File.separator + "REFCOPY";
    }

    /**
     * Return the base path where the merged commands are stored.
     *
     * @return path of the directory
     */
    public String getMergedOpBasePath() {
        return getDataPath() + File.separator + "MERGED_OP";
    }

    /**
     * Return the current path of the merged commands.
     *
     * @return path of the directory
     */
    public String getMergedOpPath() {
        return getMergedOpBasePath() + File.separator + "op_" + mergedTime;
    }

    /**
     * The current path of the attachement of the merged commands.
     *
     * @return path of the directory
     */
    public String getMergedAttachPath() {
        return getMergedOpBasePath() + File.separator + "attach_" + mergedTime;
    }

    /**
     * The temp working directory
     *
     * @return path of the directory
     */
    public String getTempDir() {
        return System.getProperty("java.io.tmpdir") + File.separator + "so6_compress_" + System.currentTimeMillis();
    }

    /**
     * Load meta data from the filesystem. (Need to be used when concurrent
     * update on the file systeme.)
     *
     * @param path
     *            The path of the workspace connection property file
     *
     * @throws Exception
     */
    public void load(String path) throws IOException {
        File propfile = new File(path);

        if (propfile.exists() && (!propfile.isDirectory())) {
            try {
                FileInputStream fis = new FileInputStream(propfile);
                prop.load(fis);
                fis.close();
            } catch (Exception e) {
                throw new IOException("Cannot read property file:" + path);
            }
        } else {
            throw new IOException("Cannot find property file: " + path + "\n The workspace might not be on this computer.");
        }

        loaded = true;
    }

    /**
     * Save the data in the property file
     */
    public void save() {
        if (wscPath == null) { // true for testing...

            return;
        }

        try {
            // remove password
            Properties propToSave = (Properties) prop.clone();

            //propToSave.remove(ClientI.SO6_PASSWORD);
            FileOutputStream fos = new FileOutputStream(wscPath);
            propToSave.store(fos, "do not edit");
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException("Cannot save property file" + wscPath);
        }
    }

    /**
     * Automaticaly instantiate the class specified for property
     * <code>SYNC_CLIENT_NAME<code>
     * with the default constructor that take the <code>SO6_WSC_FILE<code> property file as parameter.
     *
     * @return The ClientI of that connection
     */
    public ClientI getClient() throws Exception {
        if (clienti == null) {
            Constructor construct = Class.forName(prop.getProperty(SYNC_CLIENT_NAME)).getConstructor(new Class[] { Properties.class });
            setClient((ClientI) construct.newInstance(new Object[] { prop }));
        }

        return clienti;
    }

    /**
     * Set a property in the <code>SO6_WSC_FILE<code> property file
     * @param key The key of the property
     * @param value The value associated to that key
     */
    public void setProperty(String key, String value) {
        prop.setProperty(key, value);
        save();
    }

    /**
     * Return the property value for the key passed in parameter.
     *
     * @param propName
     *            key name
     * @return The property value for the key passed in parameter.
     */
    public String getProperty(String propName) {
        return prop.getProperty(propName);
    }

    private String getSynchronizeClientName() {
        return getProperty(WsConnection.SYNC_CLIENT_NAME);
    }

    /**
     * Return the current connection ticket. The ticket is equivalent to a
     * timestamp in the shared data history.
     *
     * @return The current connection ticket.
     *
     */
    public long getNs() {
        return appliedPatch.getLastTicket();
    }

    /**
     * Return the name of that connection
     *
     * @return The name of that connection
     */
    public String getWsName() {
        return getProperty(WsConnection.WS_NAME);
    }

    /**
     * Return the base path of the workspace
     *
     * @return The base path of the workspace
     */
    public String getPath() {
        return getProperty(WsConnection.PATH);
    }

    /**
     * The data path is the place where the WsConnection store all the important
     * data to manage the synchronisation process.
     *
     * @return The base path of the WsConnection data path
     */
    public String getDataPath() {
        return getProperty(WsConnection.DATAPATH);
    }

    /**
     * Reload the binary extention definition from the ClientI interface.
     */
    public void updateBinExt() throws Exception {
        String binExt = getClient().getBinExt();
        dbtype.updateBinExt(binExt);
        refcopy.getDBType().updateBinExt(binExt);
        setLastBinExt(binExt);
    }

    /**
     * Set the ClientI for that connection and update the binary extention. This
     * methode won't update the property file. This is just for the current run
     * time.
     *
     * @param clientI
     */
    public void setClient(ClientI clientI) throws Exception {
        this.clienti = clientI;
        updateBinExt();
    }

    /**
     * Return the refCopy object that represent the reference copy of the local
     * workspace
     *
     * @return The refCopy object
     */
    public RefCopy getRefCopy() {
        return refcopy;
    }

    /**
     * Automaticaly update and commit on the current connection.
     *
     * @param comment
     *            The comment of the commit.
     *
     * @throws Exception
     */
    public void updateAndCommit(String comment) throws Exception {
        update();
        commit(comment);
    }

    /**
     * Automaticaly update and commit on the current connection. Without any
     * comment.
     *
     * @throws Exception
     */
    public void updateAndCommit() throws Exception {
        updateAndCommit("No comment...");
    }

    /**
     * This methode is used to simulate actions. It means that the local data
     * won't be modified, but a report will be made in the specified directory
     * in order to explain what would happen if it wasn't a simulation.
     *
     * @param simulation
     *            To set or unset the simulation mode
     * @param outputDir
     *            The output directory where will be stored the report.
     */
    public void setSimulationMode(boolean simulation, String outputDir) {
        this.simulationMode = simulation;
        this.simulationOutputDir = outputDir;
    }

    /**
     * Commit the local changes with the specified comment.
     *
     * @param comment
     *            The comment for that commit
     *
     * @throws Exception
     */
    public void commit(String comment) throws Exception {
        // check corruption
        if (isCorrupted()) {
            throw new WorkspaceCorruptedException("The local workspace is corrupted");
        }

        report = new StringBuffer();
        StateMonitoring.getInstance().setXMLMonitoringStartAction("COMMIT");
        StateMonitoring.getInstance().setXMLMonitoringStartSubCall(4, null);
        StateMonitoring.getInstance().setXMLMonitoringComment("Start the commit process");

        //
        long ticket = this.getNs() + 1;
        cleanLocalOp();

        File tmp = new File(getLocalCmdPath());
        tmp.mkdirs();

        //
        StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1, "Compute local operations");

        //
        OpVectorFsImpl opv = new OpVectorFsImpl(tmp.getPath());
        FileParser fp = new FileParser(this);
        fp.compute(opv);
        StateMonitoring.getInstance().setXMLMonitoringEndSubCall();

        if (opv.size() == 0) {
            StateMonitoring.getInstance().setXMLMonitoringComment(false, "No local operation found");
            StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
            StateMonitoring.getInstance().setXMLMonitoringComment(true, "Commit process done");
            StateMonitoring.getInstance().setXMLMonitoringEndAction("COMMIT");

            return; // Nothing to commit
        }

        // check partial for commit
        long lastticket = -1;

        if (patchFilter == null) {
            lastticket = (ticket + opv.size()) - 1;
        } else {
            // check nb command that need to be sent
            lastticket = (ticket + computeNbCommandToSend(opv)) - 1;

            // System.out.println("Op to send with filter: " +
            // computeNbCommandToSend(opv) + "/" + opv.size());
        }

        StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1, "");
        StateMonitoring.getInstance().setXMLMonitoringComment(true, "Building patch file");
        StateMonitoring.getInstance().setXMLMonitoringComment(false, "Inserting local command");

        //
        File f = new File(getDataPath(), SO6_LAST_COMMIT_PATCH_FILE);
        OutputStreamWriter osw = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(f.getPath())), "UTF-8");
        PatchFile.makePatch(opv, osw, patchFilter, ticket, lastticket, getWsName(), comment);
        osw.close();

        //
        StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
        StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1, "");
        StateMonitoring.getInstance().setXMLMonitoringComment(true, "Connecting to the server");

        CVSReportMaker reportMaker = new CVSReportMaker(getLocalCmdPath());
        reportMaker.buildIndexTable();
        report.append(reportMaker.getReport());

        if (simulationMode) {
            FileUtils.copy(f.getPath(), simulationOutputDir + File.separator + "commit-" + System.currentTimeMillis() + ".xml");
            StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
            StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1, "");
            StateMonitoring.getInstance().setXMLMonitoringComment(true, "Patch local reference");
            StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
            StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
            StateMonitoring.getInstance().setXMLMonitoringComment(false, "Done");
            StateMonitoring.getInstance().setXMLMonitoringComment(true, "Commit process done (simulation mode)");
            StateMonitoring.getInstance().setXMLMonitoringEndAction("COMMIT");
        } else {
            getClient().sendPatch(getNs() + 1, lastticket, f.getPath(), true);
            StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
            StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1, "");
            StateMonitoring.getInstance().setXMLMonitoringComment(true, "Patch local reference");
            setCorrupted(COMMIT_CORRUPTION_PATCH_REF_COPY);
            StateMonitoring.getInstance().setXMLMonitoringStartCriticalPart();
            refcopy.patch(f.getPath());
            setCorrupted(COMMIT_CORRUPTION_SAVE_PATCH);
            StateMonitoring.getInstance().setXMLMonitoringStartCriticalPart();
            appliedPatch.add(f.getPath());
            setCorrupted(COMMIT_CORRUPTION_UPDATE_RECEIVED_TICKET);
            StateMonitoring.getInstance().setXMLMonitoringStartCriticalPart();
            receivedPatch.setLastTicket(getNs());

            // update local db type
            setCorrupted(COMMIT_CORRUPTION_UPDATE_LOCAL_DBTYPE);
            StateMonitoring.getInstance().setXMLMonitoringStartCriticalPart();
            getDBType().updateFromDBType(refcopy.getDBType());
            setCorrupted(NO_CORRUPTION);
            StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
            StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
            StateMonitoring.getInstance().setXMLMonitoringComment(false, "Done");
            StateMonitoring.getInstance().setXMLMonitoringComment(true, "Commit process done");
            StateMonitoring.getInstance().setXMLMonitoringEndAction("COMMIT");
        }
    }

    private void update(String patchfile) throws Exception {
        FileParser fp = new FileParser(this);
        cleanLocalOp();

        File localOpDir = new File(getLocalCmdPath());
        File mergedOpDir = new File(getMergedOpPath());
        StateMonitoring.getInstance().setXMLMonitoringStartSubCall(3, "");
        StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1, "");
        StateMonitoring.getInstance().setXMLMonitoringComment(true, "Check local operartions");

        OpVector locals = new OpVectorFsImpl(localOpDir.getPath());

        if (needToCheckLocalOp) {
            fp.compute(locals);
        }

        if (locals.size() == 0) {
            needToCheckLocalOp = false;
        }

        StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
        StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1, "");
        StateMonitoring.getInstance().setXMLMonitoringComment(true, "Patch local path");
        mergedOp = new OpVectorFsImpl(mergedOpDir.getPath());
        this.merge(patchfile, locals);
        StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
        StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1, "");
        StateMonitoring.getInstance().setXMLMonitoringComment(true, "Patch ref copy");

        if (!simulationMode) {
            File lastPatch = new File(getDataPath() + File.separator + SO6_LAST_COMMIT_PATCH_FILE);

            if (!lastPatch.exists() || !FileUtils.compareBinFile(lastPatch.getPath(), patchfile)) {
                setCorrupted(UPDATE_CORRUPTION_PATCH_LOCAL_COPY);

                // System.out.println("UPDATE_CORRUPTION_PATCH_LOCAL_COPY : "
                // +UPDATE_CORRUPTION_PATCH_LOCAL_COPY);
                StateMonitoring.getInstance().setXMLMonitoringStartCriticalPart();

                ListIterator iterator = mergedOp.getCommands();
                Command cmd = null;

                while ((cmd = (Command) iterator.next()) != null) {
                    // execute remote cmd
                    Logger.getLogger("ui.log").info("executing:" + cmd);
                    cmd.execute(this.getPath(), this.getDBType());
                }
            } else {
                // System.out.println("Same patch do not apply it localy !!!");
            }

            setCorrupted(UPDATE_CORRUPTION_PATCH_REF_COPY);

            // System.out.println("UPDATE_CORRUPTION_PATCH_REF_COPY : "
            // +UPDATE_CORRUPTION_PATCH_REF_COPY);
            StateMonitoring.getInstance().setXMLMonitoringStartCriticalPart();
            this.getRefCopy().patch(patchfile);
            setCorrupted(UPDATE_CORRUPTION_SAVE_PATCH);

            // System.out.println("UPDATE_CORRUPTION_SAVE_PATCH : "
            // +UPDATE_CORRUPTION_SAVE_PATCH);
            StateMonitoring.getInstance().setXMLMonitoringStartCriticalPart();
            this.appliedPatch.add(patchfile);
            setCorrupted(UPDATE_CORRUPTION_REMOVE_PATCH);

            // System.out.println("UPDATE_CORRUPTION_REMOVE_PATCH : "
            // +UPDATE_CORRUPTION_REMOVE_PATCH);
            StateMonitoring.getInstance().setXMLMonitoringStartCriticalPart();
            this.receivedPatch.remove(patchfile);
            setCorrupted(NO_CORRUPTION);
        }

        StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
        StateMonitoring.getInstance().setXMLMonitoringEndSubCall();

        // Save patch report
        CVSReportMaker reportMaker = new CVSReportMaker(getMergedOpPath());
        reportMaker.buildIndexTable();
        report.append("Merging patch file: " + patchfile + "\n");
        report.append(reportMaker.getReport());
        report.append("\n");
    }

    /**
     * Update the local workspace in order to integrate the concurrent change.
     *
     * @throws Exception
     */
    public void update() throws Exception {
        // check corruption
        if (isCorrupted()) {
            throw new WorkspaceCorruptedException("The local workspace is corrupted");
        }

        report = new StringBuffer();
        needToCheckLocalOp = true;
        StateMonitoring.getInstance().setXMLMonitoringStartAction("UPDATE");
        StateMonitoring.getInstance().setXMLMonitoringStartSubCall(4, "");
        StateMonitoring.getInstance().setXMLMonitoringComment(true, "Start the update process");
        StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1, "");
        StateMonitoring.getInstance().setXMLMonitoringComment(true, "Download all patch");
        receive();
        StateMonitoring.getInstance().setXMLMonitoringEndSubCall();

        File[] patches = receivedPatch.list();
        StateMonitoring.getInstance().setXMLMonitoringStartSubCall(patches.length, "Updating...");
        StateMonitoring.getInstance().setXMLMonitoringComment(true, "Merging");

        for (int i = 0; i < patches.length; i++) {
            StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1, "");
            this.update(patches[i].getPath());
            StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
        }

        StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
        StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
        StateMonitoring.getInstance().setXMLMonitoringComment(false, "Done update");
        StateMonitoring.getInstance().setXMLMonitoringComment(true, "Update process done");
        StateMonitoring.getInstance().setXMLMonitoringEndAction("UPDATE");
        getDBType().updateFromDBType(refcopy.getDBType());
    }

    /**
     * Download the remote patch without applying them localy.
     *
     * @throws Exception
     */
    public void receive() throws Exception {
        // check corruption
        if (isCorrupted()) {
            throw new WorkspaceCorruptedException("The local workspace is corrupted");
        }

        int nbPatchToDownload = 0;
        int currentPatchNumber = 1;
        long[][] patchList = getClient().listPatch();

        for (int i = 0; i < patchList.length; i++) {
            if (patchList[i][0] > receivedPatch.getLastTicket()) {
                nbPatchToDownload++;
            }
        }

        while (getClient().getLastTicket() > receivedPatch.getLastTicket()) {
            StateMonitoring.getInstance().setXMLMonitoringState(0, nbPatchToDownload, currentPatchNumber,
                "Downloading patch " + currentPatchNumber + " / " + nbPatchToDownload);
            currentPatchNumber++;

            String fname = getClient().getPatch(receivedPatch.getLastTicket() + 1);
            receivedPatch.add(fname);
        }
    }

    private void setCorrupted(int b) {
        prop.setProperty(CORRUPTION_STATE, Integer.toString(b));
        prop.setProperty(LAST_TICKET_BEFORE_CORRUPTION, Long.toString(getNs()));
        save();
    }

    /**
     * Return the corrupted state
     *
     * @return The corrupted state
     */
    public int getCorruptedState() {
        String value = prop.getProperty(CORRUPTION_STATE);

        if (value == null) {
            return -1;
        }

        return Integer.parseInt(value);
    }

    private long getLastTicketBeforeCorruption() {
        return Long.parseLong(prop.getProperty(LAST_TICKET_BEFORE_CORRUPTION));
    }

    /**
     * Return true if the connection is corrupted
     *
     * @return True if the connection is corrupted
     */
    public boolean isCorrupted() {
        return getCorruptedState() > 0;
    }

    /**
     * Restore the local corrupted connection
     * <p>
     * If the connection is not corrupted, throw an Excpetion
     *
     * @throws Exception
     */
    public void restore() throws Exception {
        String patchFileName;
        String tmpBasePath;
        String tmpDataPath;
        File src;
        File dst;

        // check corruption
        if (!isCorrupted()) {
            throw new Exception("The local workspace is NOT corrupted !!!");
        }

        // check locks
        File[] children = new File(getPath()).listFiles();

        for (int i = 0; i < children.length; i++) {
            if (FileUtils.isLocked(children[i])) {
                throw new FileLockedException(children[i]);
            }
        }

        switch (getCorruptedState()) {
        // For update
        // - exec merged op
        // - patch refcopy
        // - save patch (appliedPatch.add)
        // - remove patch (receivedPatch.remove)
        // For commit
        // - patch refcopy
        // - save patch (appliedPatch.add)
        // - update last ticket (receivedPatch.setLastTicket)
        // - update local dbType
        // (getDBType().updateFromDBType(refcopy.getDBType()))
        case UPDATE_CORRUPTION_PATCH_REF_COPY:

            // clean ref copy
            FileUtils.remove(getRefCopyPath());
            FileUtils.createDirIfNotExist(getRefCopyPath());

            // Rebuild REFCOPY
            File[] patch = appliedPatch.list();

            for (int i = 0; i < patch.length; i++) {
                if (Long.parseLong(patch[i].getName().split("\\.")[1]) <= getLastTicketBeforeCorruption()) {
                    getRefCopy().patch(patch[i].getAbsolutePath());
                    System.out.println(patch[i].getName());
                }
            }

        case UPDATE_CORRUPTION_PATCH_LOCAL_COPY:

            // clean local files
            FileUtils.remove(getPath(), new FilterIgnoreFile(this), false);

            // Rebuild local state
            src = new File(getRefCopyPath());
            dst = new File(getPath());
            FileUtils.copy(src, dst);
            System.out.println("Copy ok !");

            OpVectorFsImpl localOp = new OpVectorFsImpl(getLocalCmdPath());
            localOp.load();

            ListIterator i = localOp.getCommands();
            Command cmd = null;
            DBType localDBType = new DBType(getDataPath() + File.separator + "DBTYPE" + File.separator + "local.dbtype", "");

            while ((cmd = (Command) i.next()) != null) {
                System.out.println("execute: " + cmd);
                cmd.execute(getPath(), localDBType);
            }

            setCorrupted(NO_CORRUPTION);

            break;

        case COMMIT_CORRUPTION_PATCH_REF_COPY:
            FileUtils.remove(getRefCopyPath());
            FileUtils.createDir(getRefCopyPath());

            // rebuild patch copy state
            File[] patchList = appliedPatch.list();

            for (int index = 0; index < patchList.length; index++) {
                if (Long.parseLong(patchList[index].getName().split("\\.")[1]) <= getLastTicketBeforeCorruption()) {
                    PatchFile pf = new PatchFile(appliedPatch.getBaseDataPath() + File.separator + patchList[index].getName());
                    pf.patch(getRefCopyPath(), new DBType(getDBTypePath() + File.separator + "refcopy.dbtype", ""));
                }
            }

            // rebuild patch and update refcopy
            localOp = new OpVectorFsImpl(getLocalCmdPath());
            localOp.load();

            ListIterator li = localOp.getCommands();
            cmd = null;

            //
            File dir = FileUtils.createTmpDir();
            File f = File.createTempFile("commit_", null, dir);
            OutputStreamWriter osw = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(f.getPath())));
            osw.write("<?xml version=\"1.0\"?>");
            osw.write("<patch>");
            osw.write("<name>" + Base64.encodeBytes(getWsName().getBytes("UTF-8")) + "</name>");
            osw.write("<begin>" + localOp.getFromTicket() + "</begin>");
            osw.write("<end>" + localOp.getToTicket() + "</end>");
            osw.write("<comment>" + Base64.encodeBytes("Patch rebuild from a restore".getBytes("UTF-8")) + "</comment>");

            while ((cmd = (Command) li.next()) != null) {
                osw.write("<command>");
                osw.write("<class>" + cmd.getClass().getName() + "</class>");
                cmd.toXML(osw);
                cmd.execute(getRefCopyPath(), new DBType(getDBTypePath() + File.separator + "refcopy.dbtype", ""));
                osw.write("</command>");
            }

            osw.write("</patch>");
            osw.flush();
            osw.close();

            // save patch
            appliedPatch.add(f.getPath());
            setCorrupted(COMMIT_CORRUPTION_UPDATE_RECEIVED_TICKET);
            receivedPatch.setLastTicket(appliedPatch.getLastTicket());
            setCorrupted(NO_CORRUPTION);

            break;

        case UPDATE_CORRUPTION_REMOVE_PATCH:
            patchFileName = receivedPatch.findPatchNameWithToTicket(getLastTicketBeforeCorruption()).getPath();
            receivedPatch.remove(patchFileName);
            setCorrupted(NO_CORRUPTION);

            break;

        case UPDATE_CORRUPTION_SAVE_PATCH:
            patchFileName = receivedPatch.findPatchNameWithFromTicket(getLastTicketBeforeCorruption() + 1).getPath();
            appliedPatch.add(patchFileName);
            setCorrupted(UPDATE_CORRUPTION_REMOVE_PATCH);
            receivedPatch.remove(patchFileName);
            setCorrupted(NO_CORRUPTION);

            break;

        case COMMIT_CORRUPTION_SAVE_PATCH:

            String patchFile = getClient().getPatch(getLastTicketBeforeCorruption() + 1);
            appliedPatch.add(patchFile);
            setCorrupted(NO_CORRUPTION);

            break;

        case COMMIT_CORRUPTION_UPDATE_LOCAL_DBTYPE:
            getDBType().updateFromDBType(refcopy.getDBType());
            setCorrupted(NO_CORRUPTION);

            break;

        case COMMIT_CORRUPTION_UPDATE_RECEIVED_TICKET:
            receivedPatch.setLastTicket(getNs());
            setCorrupted(NO_CORRUPTION);

            break;
        }
    }

    private void merge(String patchfile, OpVector locals)
        throws Exception {
        PatchFile pf = new PatchFile(patchfile);
        pf.merge(this, locals);
    }

    /**
     * Methode called by the MergePatchHandler
     *
     * @param cmd
     * @param local
     * @throws Exception
     */
    public void merge(Command cmd, OpVector local) throws Exception {
        Command origcmd = (Command) ObjectCloner.deepCopy(cmd);
        Command opl;

        // for root node
        // ca sert a rien...

        /*
         * iterator = localop.getNodeCommands("");
         * System.out.println("1============ (rien)"); while ((opl =
         * iterator.next()) != null) { System.out.println("opl " + opl );
         * Command oplt = de.transp(opl, cmd); if (!oplt.equals(opl)) {
         * iterator.update(oplt); } cmd = de.transp(cmd, opl); }
         * iterator.close();
         */

        // for each parent node

        /*
         *
         * String cmdPath = cmd.getPath(); System.out.println("cmd="+cmd);
         * StringTokenizer tokenizer = new StringTokenizer(cmdPath, "/"); String
         * nodeName = ""; while (tokenizer.hasMoreTokens()) { nodeName =
         * nodeName + tokenizer.nextToken(); iterator =
         * localop.getNodeCommands(nodeName); System.out.println("2============ " +
         * nodeName); while ((opl = iterator.next()) != null) {
         * System.out.println("opl " + opl); Command oplt = de.transp(opl, cmd);
         * if (!oplt.equals(opl)) { iterator.update(oplt); } cmd =
         * de.transp(cmd, opl); } nodeName = nodeName + "/"; iterator.close(); } //
         * For cascade rename while (!cmdPath.equals(cmd.getPath())) {
         * System.out.println("cmd="+cmd); cmdPath = cmd.getPath(); iterator =
         * localop.getNodeCommands(cmdPath); System.out.println("2'============ " +
         * cmdPath); while ((opl = iterator.next()) != null) {
         * System.out.println("opl " + opl); Command oplt = de.transp(opl, cmd);
         * if (!oplt.equals(opl)) { iterator.update(oplt); } cmd =
         * de.transp(cmd, opl); } iterator.close(); }
         *
         */

        // for each child
        // iterator = localop.getSubTreeCommands(cmd.getPath());
        ListIterator iterator = local.getCommands();

        while ((opl = (Command) iterator.next()) != null) {
            Command oplt = de.transp(opl, cmd);

            if (!oplt.equals(opl)) {
                iterator.set(oplt);
            }

            cmd = de.transp(cmd, opl);
        }

        // insert in merged op vector for deffered execution
        mergedOp.add(cmd);
    }

    /**
     * Return the local DBType
     *
     * @return The local DBType
     */
    public DBType getDBType() {
        return dbtype;
    }

    /**
     * Return the applied patch repository
     *
     * @return The applied patch repository
     */
    public PatchRepository getAppliedPatch() {
        return appliedPatch;
    }

    /**
     * Return the received patch repository
     *
     * @return The received patch repository
     */
    public PatchRepository getReceivedPatch() {
        return receivedPatch;
    }

    /**
     * Remove the local computed command
     *
     * @throws Exception
     */
    public void cleanLocalOp() throws Exception {
        FileUtils.remove(getAttachFilePath());
        FileUtils.remove(getLocalCmdPath());
        FileUtils.createDir(getAttachFilePath());
        FileUtils.createDir(getLocalCmdPath());

        //
        mergedTime = System.currentTimeMillis();
        FileUtils.createDir(getMergedOpPath());
        FileUtils.createDir(getMergedAttachPath());
    }

    /**
     * Set a commit filter.
     * <p>
     * To allow a commit of limited set of files. The vector filter must contain
     * all the relative file path that you want to commit.
     *
     * @param filter
     */
    public void setFilter(Vector filter) {
        this.patchFilter = filter;
    }

    /**
     * Return the number of commands that are kept with the current filter in
     * the localOpVector.
     *
     * @param localOp
     *            The command vector.
     *
     * @return The number of operation kept with that filter
     *
     * @throws Exception
     *             On dependency error or if no filter set.
     */
    public long computeNbCommandToSend(OpVector localOp)
        throws Exception {
        long nbCommandToKeep = 0;

        if (patchFilter == null) {
            throw new Exception("No filter set !!!");
        }

        ListIterator iterator = localOp.getCommands();
        Command cmd = null;

        while ((cmd = (Command) iterator.next()) != null) {
            String path = cmd.getPath();

            if (patchFilter.contains(path)) {
                nbCommandToKeep++;
            } else {
                for (Iterator i = patchFilter.iterator(); i.hasNext();) {
                    File pathFiltered = new File((String) i.next());

                    if ((pathFiltered.getParent() != null) && pathFiltered.getParent().startsWith(path)) {
                        throw new Exception("Dependency error: " + path);
                    }
                }
            }
        }

        //
        return nbCommandToKeep;
    }

    /**
     * Return if the connection merge XML data
     *
     * @return if the connection merge XML data
     */
    public boolean getXmlAutoDetection() {
        return prop.getProperty(XML_AUTO_DETECTION, "false").toLowerCase().equals("true");
    }

    /**
     * Return the last set of binary extention
     *
     * @return The last set of binary extention
     */
    public String getLastBinExt() {
        return prop.getProperty(LAST_BIN_EXT, "");
    }

    private void setLastBinExt(String binExt) {
        prop.setProperty(LAST_BIN_EXT, binExt);
    }

    /**
     * Enable or disable the Xml auto detection
     *
     * @param autoDetect
     *            true if you want to activate the Xml dectection.
     */
    public void setXmlAutoDetection(boolean autoDetect) {
        prop.setProperty(XML_AUTO_DETECTION, Boolean.toString(autoDetect));
        save();
    }

    /**
     * Return the current report as a String for simple output. (a CVS like
     * report)
     *
     * @return The current report as a String for simple output. (a CVS like
     *         report)
     */
    public String getReport() {
        if (report == null) {
            return "No Report available...";
        }

        return report.toString();
    }

    /**
     * Remove old merged op
     */
    public void removedOldMergedOp() throws Exception {
        FileUtils.remove(getMergedOpBasePath());
    }

    public void updateProp(Properties prop) {
        this.prop.putAll(prop);
    }

    private void walkToBuildOpFromRefCopy(File rootDir, String relativePath, OpVector ops)
        throws Exception {
        File tmpFile = null;

        if (!relativePath.equals(".")) {
            tmpFile = new File(rootDir, relativePath);

            if (!(tmpFile.exists())) {
                throw new java.io.IOException(tmpFile.getPath() + " not exists");
            }

            if (!(tmpFile.isDirectory()) && !(tmpFile.isFile())) {
                Logger.getLogger("ui.log").warning("unmanaged file type:" + tmpFile.getPath());

                return;
            }

            Logger.getLogger("ui.log").info("examining:" + relativePath);

            // monitoring xml
            int fileType = getRefCopy().getDBType().getType(relativePath);

            switch (fileType) {
            case DBType.TYPE_DIR:
                ops.add(new AddDir(relativePath, this));

                break;

            case DBType.TYPE_FILE_BIN:
                ops.add(new AddBinaryFile(relativePath, this, getRefCopyPath() + "/" + relativePath));

                break;

            case DBType.TYPE_FILE_TXT:
                ops.add(new AddTxtFile(relativePath, this, getRefCopyPath() + "/" + relativePath));

                break;

            case DBType.TYPE_FILE_XML:

                // XmlUtil.normalizeDocument(newFile);
                ops.add(new AddXmlFile(relativePath, this, getRefCopyPath() + "/" + relativePath));

                break;

            default:

                // nothing
                System.out.println(relativePath + " => " + fileType);

                break;
            }

            // recurse walking to seek children changes
            if (tmpFile.isDirectory()) { // recurse walking to detect new

                // children
                File[] subs = tmpFile.listFiles();

                for (int i = 0; i < subs.length; i++) {
                    walkToBuildOpFromRefCopy(rootDir, relativePath + "/" + subs[i].getName(), ops);
                }
            }
        } else {
            File[] subs = rootDir.listFiles();

            for (int i = 0; i < subs.length; i++) {
                walkToBuildOpFromRefCopy(rootDir, subs[i].getName(), ops);
            }
        }
    }

    public String sendCurrentCompressState() throws Exception {
        StateMonitoring.getInstance().setXMLMonitoringStartAction("COMPRESS");
        StateMonitoring.getInstance().setXMLMonitoringStartSubCall(4, null);
        StateMonitoring.getInstance().setXMLMonitoringComment("Start the compress process");

        // TODO from - to
        long from = 1;
        long to = getAppliedPatch().getLastTicket();

        File patchToDo = new File(getAppliedPatch().getBaseDataPath() + File.separator + from + "." + to);

        if (!patchToDo.exists()) {
            cleanLocalOp();

            File tmp = new File(getLocalCmdPath());
            tmp.mkdirs();

            // Generate patch from REFCOPY
            OpVectorFsImpl opv = new OpVectorFsImpl(tmp.getPath());
            StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1, "Compute compressed operations");
            walkToBuildOpFromRefCopy(new File(getRefCopyPath()), ".", opv);

            //System.out.println(opv.size());
            StateMonitoring.getInstance().setXMLMonitoringEndSubCall();

            // Generate patch
            StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1, "Generate compressed patch file");

            OutputStreamWriter osw = new FileWriter(patchToDo);
            PatchFile.makePatch(opv, osw, null, from, to, "compress", "compressed patch");
            osw.close();
            StateMonitoring.getInstance().setXMLMonitoringEndSubCall();

            // send patch without validate
            StateMonitoring.getInstance().setXMLMonitoringStartSubCall(2, "Send compressed patch");
            getClient().sendPatch(from, to, patchToDo.getPath(), false);
            StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
        } else {
            // Patch already exists
            StateMonitoring.getInstance().setXMLMonitoringComment(false, "Nothing done.");
        }

        StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
        StateMonitoring.getInstance().setXMLMonitoringComment(false, "Done");
        StateMonitoring.getInstance().setXMLMonitoringComment(true, "Compress process done");
        StateMonitoring.getInstance().setXMLMonitoringEndAction("COMPRESS");

        return patchToDo.getPath();
    }

    public void compressLocalHistory(long from, long to)
        throws Exception {
        StateMonitoring.getInstance().setXMLMonitoringStartAction("COMPRESS");
        StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1, null);
        StateMonitoring.getInstance().setXMLMonitoringComment("Start the compress process");
        StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1, "Try to find compressed patch");

        // TODO from - to
        from = 1;
        to = getAppliedPatch().getLastTicket();

        File patchToDo = new File(getAppliedPatch().getBaseDataPath() + File.separator + from + "." + to);

        if (!patchToDo.exists()) {
            StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1, "Generate compressed patch file");

            // patch does not exist > generate it
            File cmds = new File(getDataPath() + File.separator + "CMDS");
            cmds.mkdirs();

            File attach = new File(getDataPath() + File.separator + "ATTACH");
            attach.mkdirs();

            OpVectorFsImpl opv = new OpVectorFsImpl(cmds.getPath());

            File[] patchs = getAppliedPatch().list();

            for (int i = 0; i < patchs.length; i++) {
                File patchFile = patchs[i];
                StateMonitoring.getInstance().setXMLMonitoringComment(false, patchFile.getAbsolutePath());

                PatchFile pf = new PatchFile(patchFile.getName());
                pf.buildOpVector(new FileInputStream(patchFile), opv, attach.getPath(), null);
            }

            CompressUtil.compressLog(opv);

            OutputStreamWriter osw = new FileWriter(patchToDo);
            PatchFile.makePatch(opv, osw, null, from, to, "compress", "compressed patch");
            osw.close();
            StateMonitoring.getInstance().setXMLMonitoringEndSubCall();

            // send patch without validate
            StateMonitoring.getInstance().setXMLMonitoringStartSubCall(2, "Send compressed patch");
            getClient().sendPatch(from, to, patchToDo.getPath(), false);
            StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
        } else {
            // Patch already exists
            StateMonitoring.getInstance().setXMLMonitoringComment(false, "Nothing done.");
        }

        StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
        StateMonitoring.getInstance().setXMLMonitoringComment(false, "Done");
        StateMonitoring.getInstance().setXMLMonitoringComment(true, "Compress process done");
        StateMonitoring.getInstance().setXMLMonitoringEndAction("COMPRESS");
    }
}
