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
package org.libresource.so6.core.client.dummy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import org.libresource.so6.core.client.ClientI;
import org.libresource.so6.core.client.LocalException;
import org.libresource.so6.core.engine.OpVectorFsImpl;
import org.libresource.so6.core.engine.PatchFile;
import org.libresource.so6.core.engine.util.FileUtils;


public class DummyClient implements ClientI {
    private static String DEFAULT_BINEXT = "class pdf ps eps exe zip jar gif jpg png";

    /** Name of the queue property file */
    public static String PROP_FILE = "so6.properties";

    /** Key name for the last ticket information in the property file */
    public static String LAST_TICKET = "so6.last.ticket";

    /** Key name for the binary extention information in the property file */
    public static String BINEXT = "so6.bin.ext";

    // liste des extensions binaires...

    /** Name of the local directory where the patch will be stored */
    public static final String PATCHFILES = "PATCHFILES";

    /**
     * Name of the local directory where the command during compression will be
     * stored
     */
    public static final String CMDS = "CMDS";

    /**
     * Name of the local directory where the attachement of the command will be
     * stored
     */
    public static final String ATTACH = "ATTACH";
    private String dir;
    private Properties prop = new Properties();
    private boolean loaded = false;
    private Vector exts = new Vector();

    /**
     * Create a So6 queue in a tmp directory
     */
    public DummyClient() throws Exception {
        this(FileUtils.createTmpDir().getPath());
    }

    /**
     * Default constructor
     *
     * @param props
     */
    public DummyClient(Properties props) {
        this(props.getProperty(SO6_QUEUE_ID));
    }

    /**
     * Create a So6 queue in the specified directory
     *
     * @param path
     *            the base path for the queue
     */
    public DummyClient(String path) {
        this.dir = path;

        File d = new File(path);

        if (!d.exists() || (d.list().length == 0)) {
            if (!d.exists() && !d.mkdirs()) {
                throw new RuntimeException("Cannot create " + d.getPath());
            }

            prop.setProperty(DummyClient.LAST_TICKET, "0");
            exts = DummyClient.readBinExt(DEFAULT_BINEXT);
            save();
        }

        d = new File(dir, PATCHFILES);

        if (!d.exists()) {
            if (!d.mkdirs()) {
                throw new RuntimeException("Cannot create " + d.getPath());
            }
        }

        d = new File(dir, CMDS);

        if (!d.exists()) {
            if (!d.mkdirs()) {
                throw new RuntimeException("Cannot create " + d.getPath());
            }
        }

        d = new File(dir, ATTACH);

        if (!d.exists()) {
            if (!d.mkdirs()) {
                throw new RuntimeException("Cannot create " + d.getPath());
            }
        }

        load();
    }

    /**
     * Return the base path of the Dummy queue directory
     *
     * @return the base path of the queue.
     */
    public String getDummyPath() {
        return dir;
    }

    /**
     * Load meta data from the queue property file
     */
    public void load() {
        File propfile = new File(dir + File.separator + DummyClient.PROP_FILE);

        if ((propfile.exists()) && (propfile.isFile())) {
            try {
                FileInputStream fis = new FileInputStream(propfile);
                prop.load(fis);
                exts = DummyClient.readBinExt(prop.getProperty(DummyClient.BINEXT));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Cannot read property file:" + propfile.getPath());
            }
        } else {
            throw new RuntimeException("Cannot find property file:" + propfile.getPath());
        }

        loaded = true;
    }

    /**
     * Save queue meta data
     */
    public void save() {
        if (dir == null) { // true for testing...

            return;
        }

        try {
            FileOutputStream fos = new FileOutputStream(dir + File.separator + DummyClient.PROP_FILE);
            prop.setProperty(DummyClient.BINEXT, getBinExt());
            prop.store(fos, "do not edit");
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException("Cannot save property file" + dir + File.separator + DummyClient.PROP_FILE);
        }
    }

    public long getLastTicket() {
        load();

        return Long.parseLong(prop.getProperty(DummyClient.LAST_TICKET));
    }

    private void setLastTicket(long i) {
        prop.setProperty(DummyClient.LAST_TICKET, Long.toString(i));
        this.save();
    }

    public void addBinExt(String ext) {
        if (!(exts.contains(ext))) {
            exts.add(ext);
        }
    }

    public void removeBinExt(String ext) {
        exts.remove(ext);
    }

    public String getBinExt() {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < exts.size(); i++) {
            sb.append(exts.get(i) + " ");
        }

        return sb.toString();
    }

    /**
     * Split binary extention into a vector of extention
     *
     * @param s
     * @return
     */
    public static Vector readBinExt(String s) {
        Vector v = new Vector();
        StringTokenizer st = new StringTokenizer(s);

        while (st.hasMoreTokens()) {
            v.add(st.nextToken());
        }

        return v;
    }

    public void sendPatch(long ticket, long lasticket, String patchfile, boolean validate)
        throws LocalException {
        File f = new File(patchfile);

        if (ticket != (getLastTicket() + 1)) {
            throw new LocalException("bad patch file, waiting for " + (getLastTicket() + 1) + " got " + ticket);
        }

        try {
            FileUtils.copy(patchfile, dir + File.separator + PATCHFILES + File.separator + ticket + "." + lasticket);
        } catch (Exception e) {
            throw new LocalException(e);
        }

        setLastTicket(lasticket);
    }

    public String getPatch(final long fromTicket) throws LocalException {
        File d = new File(dir + File.separator + PATCHFILES);
        String[] entries = d.list(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        if (name.startsWith(Long.toString(fromTicket) + ".")) {
                            return true;
                        }

                        return false;
                    }
                });

        if (entries.length == 0) {
            throw new LocalException("No patch available with ticket " + fromTicket);
        }

        long toTicket = fromTicket;

        for (int i = 0; i < entries.length; i++) {
            long ticket = Long.parseLong(entries[i].split("\\.")[1]);

            if (ticket > toTicket) {
                toTicket = ticket;
            }
        }

        return dir + File.separator + PATCHFILES + File.separator + fromTicket + "." + toTicket;
    }

    public String getPatch(final long fromTicket, final long toTicket)
        throws LocalException {
        File d = new File(dir + File.separator + PATCHFILES);
        String patchName = fromTicket + "." + toTicket;
        File f = new File(d, patchName);

        if (!f.exists()) {
            throw new LocalException("Patch " + patchName + " does not exist!!!");
        }

        return f.getPath();
    }

    //	public ArrayList getPatchListFromTo(final long from, final long to)
    // throws Exception {
    //		File d = new File(dir + File.separator + PATCHFILES);
    //		String[] allPatch = d.list();
    //		// Check that's possible
    //		boolean impossible = true;
    //		for (int i = 0; i < allPatch.length; i++) {
    //			if (allPatch[i].substring(allPatch[i].indexOf(".") +
    // 1).equals(Long.toString(to))) {
    //				impossible = false;
    //				break;
    //			}
    //		}
    //		if (impossible)
    //			throw new Exception("Impossible to find a patch list to reach state of
    // ticket " + to);
    //
    //		// build the list
    //		long lastTicket = from;
    //		ArrayList result = new ArrayList();
    //		lastTicket++;
    //		while (lastTicket <= to) {
    //			String name = getPatch(lastTicket);
    //			lastTicket = Integer.parseInt(name.substring(name.lastIndexOf(".") + 1))
    // + 1;
    //			result.add(name);
    //		}
    //		return result;
    //	}
    public long[][] listPatch() {
        File d = new File(dir + File.separator + PATCHFILES);
        File patchFile = null;
        String[] allPatch = d.list();
        long[][] result = new long[allPatch.length][3];

        for (int i = 0; i < allPatch.length; i++) {
            patchFile = new File(d, allPatch[i]);

            String[] tickets = allPatch[i].split("\\.");
            result[i][0] = Long.parseLong(tickets[0]); // fromTicket
            result[i][1] = Long.parseLong(tickets[1]); // toTicket
            result[i][2] = patchFile.length(); // fileSize
        }

        return result;
    }

    /**
     * Build an equivalent compressed patch.
     *
     * @return the path of that patch file
     * @throws Exception
     */
    public String tagState() throws Exception {
        File outputFile = new File(dir + File.separator + PATCHFILES, "1." + getLastTicket());

        if (outputFile.exists()) {
            return outputFile.getPath();
        }

        long[][] patchList = listPatch();

        // find the shortest path
        long ticket = 1;
        long tmp = -1;
        ArrayList path = new ArrayList();

        while (ticket < getLastTicket()) {
            tmp = ticket;

            for (int i = 0; i < patchList.length; i++) {
                long[] line = patchList[i];

                if (line[0] == ticket) {
                    if (tmp < line[1]) {
                        tmp = line[1];
                    }
                }
            }

            path.add(ticket + "." + tmp);
            ticket = tmp + 1;
        }

        OpVectorFsImpl opv = new OpVectorFsImpl(dir + File.separator + CMDS);

        for (Iterator i = path.iterator(); i.hasNext();) {
            String patchName = (String) i.next();
            PatchFile pf = new PatchFile(dir + File.separator + PATCHFILES + File.separator + patchName);
            pf.buildOpVector(opv, dir + File.separator + ATTACH, null);
        }

        //TODO: reintegrate log compression
        //CompressUtil.compressLog(opv);

        OutputStreamWriter osw = new FileWriter(outputFile);
        PatchFile.makePatch(opv, osw, null, 1, getLastTicket(), "compress", "patch");
        osw.close();

        return outputFile.getPath();
    }

    /**
     * Construct a Dummy queue to the path specified in parameter.
     *
     * @param args
     */
    public static void main(String[] args) {
        new DummyClient(new File(args[0]).getAbsolutePath());
    }

    public static String[] getInternalPropertyList() {
        return new String[] { SO6_QUEUE_ID };
    }
}
