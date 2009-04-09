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
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.engine.util.UniqueId;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Properties;


/**
 * The <code>Workspace</code> class represents your local shared directory in
 * the so6 representation. Any workspace can be connected to one or several So6
 * Queue. Those connection are represented by the <code>WsConnection<code>
 * class and all the synchronisation operations will be made on those connections.
 * <p>
 * The <code>Workspace</code> class is just there to federate those connections
 * in order to build and represent the synchronisation network.
 * <p>
 * Workspace are identified by an unique ID and have several queue connections.
 *
 * @author  Smack
 * @version 1.0, 26/05/04
 * @see     org.libresource.so6.core.WsConnection
 * @see     org.libresource.so6.core.engine.util.UniqueId
 * @since   JDK1.4
 */
public class Workspace {
    /** Name of the file where the workspace id will be stored */
    public static final String SO6_WS_FILE = "workspaceId";

    /**
     * Name of the data directory where the Workspace store the meta data (Id,
     * Connections...)
     */
    public static final String SO6PREFIX = ".so6";

    //
    private String wsBasePath;

    /**
     * Load an existing <code>Workspace</code> object in order to access to
     * its id and WsConnections.
     *
     * @param wsBasePath
     *            workspace base path a <code>String</code>.
     */
    public Workspace(String wsBasePath) throws IOException {
        this.wsBasePath = wsBasePath;

        File f = new File(wsBasePath + File.separator + SO6PREFIX + File.separator + SO6_WS_FILE);

        if (!f.exists()) {
            throw new IOException("Invalid workspace path (" + wsBasePath + ")");
        }
    }

    /**
     * Create a new <code>Workspace</code> object and generate its unique
     * identifier
     *
     * @param basePath
     *            workspace base path a <code>String</code>.
     */
    public static Workspace createWorkspace(String basePath)
        throws IOException {
        File ws = new File(basePath + File.separator + SO6PREFIX, SO6_WS_FILE);

        if (ws.exists()) {
            return new Workspace(basePath);
        }

        FileUtils.createDirIfNotExist(ws.getParent());

        if (ws.createNewFile()) {
            FileWriter fw = new FileWriter(ws);
            fw.write(UniqueId.getUniqueId());
            fw.close();

            return new Workspace(basePath);
        } else {
            throw new IOException("Unable to create a new workspace: Maybe filesystem failure");
        }
    }

    /**
     * Returns the unique id as a string using caratere [a-z,A-Z,0-9,_] (The
     * content of the file workspaceId)
     *
     * @return the workspace id as a String
     */
    public String getId() throws IOException {
        return extractIdFromFile(new File(wsBasePath + File.separator + SO6PREFIX, SO6_WS_FILE));
    }

    /**
     * Create a new <code>WsConnection</code> linked to that workspace.
     * <p>
     * As WsConnection can be made to any kind of So6 Queue, we let the user set
     * his personal connections properties, then we set the default ones.
     *
     * @param externalProperties
     *            external properties need to init the clientI class
     * @param clientIClassName
     *            class to call when we use the connection
     * @param connectionName
     *            name of that connection
     *
     * @return the file path of the WsConnection propertie file
     *
     * @exception Exception
     *                If any error occured during the creation of the connection
     *                files
     */
    public String createConnection(Properties externalProperties, String clientIClassName, String connectionName)
        throws IOException {
        int i = 1;
        File wsBaseDataDir = new File(wsBasePath + File.separator + SO6PREFIX, "" + i);

        while (wsBaseDataDir.exists()) {
            wsBaseDataDir = new File(wsBasePath + File.separator + SO6PREFIX, new Integer(i++).toString());
        }

        if (!wsBaseDataDir.mkdirs()) {
            throw new IOException("Unable to create workspace data dir : " + wsBaseDataDir.getPath());
        }

        String dataDir = wsBaseDataDir.getAbsolutePath();
        String propFilePath = dataDir + File.separator + WsConnection.SO6_WSC_FILE;

        //
        externalProperties.setProperty(WsConnection.PATH, new File(wsBasePath).getAbsolutePath());
        externalProperties.setProperty(WsConnection.WS_NAME, connectionName);
        externalProperties.setProperty(WsConnection.SYNC_CLIENT_NAME, clientIClassName);
        externalProperties.setProperty(WsConnection.DATAPATH, dataDir);

        FileOutputStream fos = new FileOutputStream(propFilePath);
        externalProperties.store(fos, "Do not edit");
        fos.close();

        return propFilePath;
    }

    /**
     * Remove a <code>WsConnection</code>
     *
     * @param wsConnectionPath
     *            absolute file path of a WsConnection property file
     *            (so6.properties)
     *
     * @exception Exception
     *                If any error occured during the removed of the connection
     *                files
     */
    public static void deleteConnection(String wsConnectionPath)
        throws Exception {
        File f = new File(wsConnectionPath).getParentFile();

        if (f.getParentFile().getName().equals(SO6PREFIX)) {
            FileUtils.remove(f.getPath());
        } else {
            throw new Exception("Invalide workpsace connection path value (ex: /exemple/" + SO6PREFIX + "/1/" + WsConnection.SO6_WSC_FILE + ")");
        }
    }

    /**
     * Remove a <code>WsConnection</code> linked to that workspace.
     *
     * @param connectionNumber
     *            the connection number of that workspace
     *
     * @exception Exception
     *                If any error occured during the removed of the connection
     *                files
     */
    public void deleteConnection(int connectionNumber)
        throws Exception {
        File f = new File(wsBasePath, Integer.toString(connectionNumber));

        if (!f.exists()) {
            throw new Exception("Invalide workpsace connection number");
        }

        FileUtils.remove(f.getPath());
    }

    /**
     * List the <code>WsConnection</code> linked to that workspace.
     *
     * @return an array of the linked WsConnection
     *
     * @exception Exception
     *                If any error occured during the removed of the connection
     *                files
     */
    public WsConnection[] listConnections() {
        File dataDir = new File(wsBasePath, SO6PREFIX);
        File[] connections = dataDir.listFiles(new FileFilter() {
                    public boolean accept(File pathname) {
                        return pathname.isDirectory();
                    }
                });

        ArrayList tmpResult = new ArrayList();

        for (int i = 0; i < connections.length; i++) {
            try {
                tmpResult.add(new WsConnection(connections[i].getAbsolutePath() + File.separator + WsConnection.SO6_WSC_FILE));
            } catch (Exception e) {
            }
        }

        WsConnection[] wsConnection = new WsConnection[tmpResult.size()];

        for (int i = 0; i < wsConnection.length; i++) {
            wsConnection[i] = (WsConnection) tmpResult.get(i);
        }

        return wsConnection;
    }

    /**
     * Get a <code>WsConnection</code> linked to that workspace.
     * <p>
     * If the parameter is
     * <code>null<code> then the connection number will be 1.
     *
     * @param        connectionNumber                the connection number of that WsConnection
     *
     * @return        a WsConnection
     *
     * @exception Exception                                If any error occured during the removed of the connection files
     */
    public WsConnection getConnectionByQueueId(String queueId)
        throws Exception {
        File basePath = new File(wsBasePath + File.separator + SO6PREFIX);
        File[] connections = basePath.listFiles();

        for (int i = 0; i < connections.length; i++) {
            File propsFile = new File(basePath, WsConnection.SO6_WSC_FILE);

            if (propsFile.exists()) {
                Properties props = new Properties();
                FileInputStream fis = new FileInputStream(propsFile);
                props.load(fis);
                fis.close();

                if (props.getProperty(ClientI.SO6_QUEUE_ID).equals(queueId)) {
                    return new WsConnection(propsFile.getAbsolutePath() + File.separator + WsConnection.SO6_WSC_FILE);
                }
            }
        }

        throw new Exception("Unable to find the specified replica");
    }

    public WsConnection getConnection(String connectionNumber)
        throws Exception {
        if (connectionNumber == null) {
            connectionNumber = "1";
        }

        File propFile = new File(wsBasePath + File.separator + SO6PREFIX + File.separator + connectionNumber + File.separator + WsConnection.SO6_WSC_FILE);

        if (propFile.exists()) {
            return new WsConnection(propFile.getAbsolutePath());
        } else {
            throw new Exception("Invalide relative workspace connection path");
        }
    }

    // Private methodes
    private String extractIdFromFile(File f) throws IOException {
        StringBuffer result = new StringBuffer();
        FileInputStream fis = new FileInputStream(f);
        byte[] buffer = new byte[1024];
        int length;

        while ((length = fis.read(buffer)) != -1) {
            result.append(new String(buffer, 0, length));
        }

        fis.close();

        return result.toString();
    }

    private void buildRecursiveId(ArrayList idList, File baseDir)
        throws Exception {
        File[] subFiles = baseDir.listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        return name.equals(SO6PREFIX);
                    }
                });

        if (subFiles.length == 1) {
            File[] wsId = subFiles[0].listFiles(new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return name.equals(SO6_WS_FILE);
                        }
                    });

            idList.add(0, extractIdFromFile(wsId[0]));
        }

        File parentBasePath = baseDir.getParentFile();

        if (parentBasePath != null) {
            buildRecursiveId(idList, parentBasePath);
        }
    }
}
