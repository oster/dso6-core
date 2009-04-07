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
package org.libresource.so6.core.engine.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class ZipUtil {
    static final int BUFFER = 2048;

    public static void unzip(String inFileName, String destinationDirectory)
        throws Exception {
        File sourceZipFile = new File(inFileName);
        File unzipDestinationDirectory = new File(destinationDirectory);
        ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
        Enumeration zipFileEntries = zipFile.entries();

        while (zipFileEntries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
            String currentEntry = entry.getName();
            File destFile = new File(unzipDestinationDirectory, currentEntry);
            File destinationParent = destFile.getParentFile();
            destinationParent.mkdirs();

            if (entry.isDirectory()) {
                destFile.mkdirs();
            } else {
                BufferedInputStream is = new BufferedInputStream(zipFile.getInputStream(entry));
                int currentByte;
                byte[] data = new byte[BUFFER];
                FileOutputStream fos = new FileOutputStream(destFile);
                BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);

                while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                    dest.write(data, 0, currentByte);
                }

                dest.flush();
                dest.close();
                is.close();
            }
        }

        zipFile.close();
    }

    public static void unzip(ZipInputStream zin, String destinationDirectory)
        throws Exception {
        File unzipDestinationDirectory = new File(destinationDirectory);
        ZipEntry entry = null;

        while ((entry = zin.getNextEntry()) != null) {
            String currentEntry = entry.getName();
            File destFile = new File(unzipDestinationDirectory, currentEntry);
            File destinationParent = destFile.getParentFile();
            destinationParent.mkdirs();

            if (entry.isDirectory()) {
                destFile.mkdirs();
            } else {
                FileOutputStream out = new FileOutputStream(destFile);
                byte[] b = new byte[BUFFER];
                int len = 0;

                while ((len = zin.read(b)) != -1) {
                    out.write(b, 0, len);
                }

                out.close();
            }
        }
    }

    private static Object[] getContent(String dir) {
        Vector v = new Vector();
        FileUtils.walk(dir, v);

        return v.toArray();
    }

    public static void zip(ZipOutputStream zos, String dir)
        throws Exception {
        File d = new File(dir);
        zos.setMethod(ZipOutputStream.DEFLATED); //indicate deflated
        zos.setLevel(Deflater.DEFAULT_COMPRESSION);

        Object[] contents = getContent(d.getPath());

        for (int i = 0; i < contents.length; i++) {
            if (d.getPath().equals((String) contents[i])) {
                continue;
            }

            String name = ((String) contents[i]).substring(d.getPath().length() + 1);
            File fin = new File((String) contents[i]);

            if (fin.isDirectory()) {
                ZipEntry ze = new ZipEntry(name + '/');
                zos.putNextEntry(ze);
            }

            if (fin.isFile()) {
                ZipEntry ze = new ZipEntry(name);
                zos.putNextEntry(ze);

                InputStream ins = new FileInputStream(fin);
                int bread;
                byte[] bin = new byte[BUFFER];

                while ((bread = ins.read(bin, 0, BUFFER)) != -1) {
                    zos.write(bin, 0, bread);
                }
            }
        }

        zos.flush();
    }
}
