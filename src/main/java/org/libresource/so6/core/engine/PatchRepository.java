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
/*
 * Created on 13 f�vr. 2004
 */
package org.libresource.so6.core.engine;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.engine.util.InfoPatchHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;

import java.util.Arrays;
import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * @author molli
 */
public class PatchRepository {
    //private WsConnection ws;
    private String name;
    private String dir;
    private Properties prop = new Properties();

    public PatchRepository(WsConnection ws, String name) {
        //this.ws = ws;
        this.name = name;
        this.dir = ws.getDataPath() + File.separator + name;

        File f = new File(dir);

        if (!(f.exists())) {
            if (!(f.mkdirs())) {
                throw new RuntimeException("Cannot make patch repository:" + name);
            }

            setLastTicket(0);
        } else {
            load();
        }
    }

    public String getBaseDataPath() {
        return dir;
    }

    public void load() {
        File propfile = new File(dir + File.separator + name + ".properties");

        if ((propfile.exists()) && (propfile.isFile())) {
            try {
                FileInputStream fis = new FileInputStream(propfile);
                prop.load(fis);
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Cannot read property file:" + propfile.getPath());
            }
        } else {
            throw new RuntimeException("Cannot find property file:" + propfile.getPath());
        }
    }

    public void save() {
        try {
            FileOutputStream fos = new FileOutputStream(dir + File.separator + name + ".properties");
            prop.store(fos, "do not edit");
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException("Cannot save property file" + dir + File.separator + name + ".properties");
        }
    }

    public void add(String patch) throws Exception {
        File f = new File(patch);

        // Update the patch name
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        InfoPatchHandler infoPatch = new InfoPatchHandler();
        saxParser.parse(f, infoPatch);

        String pacthName = infoPatch.getFromTicket() + "." + infoPatch.getToTicket();

        // 
        FileUtils.copy(patch, dir + File.separator + pacthName);
        setLastTicket(infoPatch.getToTicket());
    }

    public void remove(String patchfile) {
        File f = new File(patchfile);
        System.gc();

        if (!f.delete()) {
            throw new RuntimeException("Unable to remove patch file.");
        }
    }

    public void setLastTicket(long ticket) {
        prop.setProperty("LAST_TICKET", Long.toString(ticket));
        this.save();
    }

    public long getLastTicket() {
        return Long.parseLong(prop.getProperty("LAST_TICKET"));
    }

    public File[] list() {
        File baseDir = new File(dir);
        String[] patches = baseDir.list(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        if (name.endsWith("properties")) {
                            return false;
                        }

                        return true;
                    }
                });

        // well patches are not ordered...
        long[] tickets = new long[2 * patches.length]; // mon dieu...

        for (int i = 0; i < patches.length; i++) {
            String[] parts = patches[i].split("\\.");
            tickets[i * 2] = Long.parseLong(parts[0]);
            tickets[(i * 2) + 1] = Long.parseLong(parts[1]);
        }

        Arrays.sort(tickets);

        File[] sortedPatches = new File[patches.length];

        for (int i = 0; i < sortedPatches.length; i++) {
            sortedPatches[i] = new File(dir, tickets[i * 2] + "." + tickets[(i * 2) + 1]);
        }

        return sortedPatches;
    }

    public File findPatchNameWithFromTicket(long fromTicket) {
        File[] list = list();
        String[] line;
        long from;

        for (int i = 0; i < list.length; i++) {
            line = list[i].getName().split("\\.");
            from = Long.parseLong(line[0]);

            if (from == fromTicket) {
                return list[i];
            }
        }

        return null;
    }

    public File findPatchNameWithToTicket(long toTicket) {
        File[] list = list();
        String[] line;
        long to;

        for (int i = 0; i < list.length; i++) {
            line = list[i].getName().split("\\.");
            to = Long.parseLong(line[1]);

            if (to == toTicket) {
                return list[i];
            }
        }

        return null;
    }
}
