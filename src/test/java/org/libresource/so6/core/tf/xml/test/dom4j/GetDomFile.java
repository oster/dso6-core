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
package org.libresource.so6.core.tf.xml.test.dom4j;

import fr.loria.ecoo.so6.xml.util.XmlUtil;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.util.Enumeration;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


/**
 * @author tani
 */
public class GetDomFile {
    public static void main(String[] args) {
        try {
            String dir;
            String dir1;
            WsConnection ws1;
            dir = FileUtils.createTmpDir().getPath();

            WsConnection[] ws = TestUtil.createWorkspace(dir, 1);
            ws1 = ws[0];
            dir1 = ws1.getPath();

            String xmlFilePath = dir1 + "/test.xml";
            FileUtils.createXmlFile(xmlFilePath, "root");
            ws1.updateAndCommit();

            // Read the path to go to the ZIP File
            String zipFilePath = args[0];
            File sourceZipFile = new File(zipFilePath);
            File unzipDestinationFile = new File(xmlFilePath);
            ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
            FileOutputStream f = new FileOutputStream(sourceZipFile.getParent() + "/domtest.zip");
            CheckedOutputStream csum = new CheckedOutputStream(f, new Adler32());
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(csum));
            Enumeration zipFileEntries = zipFile.entries();
            int nbFiles = 0;
            int ok = 0;
            int ko = 0;
            int isNoXmlFile = 0;

            while (zipFileEntries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
                String currentEntry = entry.getName();

                if (!entry.isDirectory()) {
                    BufferedInputStream is = new BufferedInputStream(zipFile.getInputStream(entry));
                    FileOutputStream fos = new FileOutputStream(unzipDestinationFile);
                    int currentByte;
                    byte[] data = new byte[1024];
                    BufferedOutputStream dest = new BufferedOutputStream(fos, 1024);

                    while ((currentByte = is.read(data)) != -1) {
                        dest.write(data, 0, currentByte);
                    }

                    dest.close();
                    is.close();
                    fos.close();
                    System.out.println("File in zip -> " + currentEntry);

                    try {
                        if (FileUtils.isXmlFile(xmlFilePath)) {
                            try {
                                XmlUtil.load(xmlFilePath);
                            } catch (Exception spe) {
                                System.err.println("Error: getDOM()");
                                spe.printStackTrace();
                            }

                            ok++;

                            // Put the correct entry in the result ZIP file
                            System.out.println("Writing file " + currentEntry);

                            BufferedReader in = new BufferedReader(new FileReader(unzipDestinationFile));
                            out.putNextEntry(new ZipEntry(currentEntry));

                            int c;

                            while ((c = in.read()) != -1) {
                                out.write(c);
                            }

                            in.close();
                        } else {
                            isNoXmlFile++;
                        }
                    } catch (IOException ioe) {
                        System.err.println("Error : cannot write the result in ZIP file");
                        ioe.printStackTrace();
                    } catch (Exception e) {
                        System.out.println("Error File : " + currentEntry);
                    }

                    nbFiles++;
                }
            }

            ko = (nbFiles - (ok + isNoXmlFile));
            System.out.println("Number of Files checked : " + nbFiles);
            System.out.println("Number of Files ok      : " + ok);
            System.out.println("Number of Files ko      : " + ko);
            System.out.println("Number of Files no xml  : " + isNoXmlFile);
            zipFile.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
