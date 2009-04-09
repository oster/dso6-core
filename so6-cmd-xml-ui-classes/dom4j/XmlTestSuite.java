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

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


/**
 * @author tani
 */
public class XmlTestSuite extends TestCase {
    public static final String zipFilePath = "c:/so6/demoxml/EDUNIxmlts043004.zip";
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;
    private int nbFile = 0;
    private String currentEntry;
    private String previousEntry;

    public XmlTestSuite(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2);
        ws1 = ws[0];
        ws2 = ws[1];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();

        String xmlFilePath = dir1 + File.separator + "init.xml";
        FileUtils.createXmlFile("root", xmlFilePath);

        //Document dom = XmlUtil.createDocument("root");
        //XmlUtil.setEncoding(dom,"UTF-8");
        //XmlUtil.createXmlFile(dom,xmlFilePath);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
    }

    public void tearDown() throws Exception {
        System.out.println("Previous File Name " + previousEntry);
        System.out.println("Current File Name " + currentEntry);
        System.out.println(dir);
    }

    public void testXmlTestSuite() throws Exception {
        String xmlFilePath = dir1 + File.separator + "init.xml";
        File sourceZipFile = new File(zipFilePath);
        File unzipDestinationFile = new File(xmlFilePath);
        ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
        Enumeration zipFileEntries = zipFile.entries();
        String currentProblem = new String();

        while (zipFileEntries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
            currentEntry = entry.getName();

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
            }

            System.out.println("File in zip -> " + currentEntry + " Number of files already checked: " + nbFile);

            // Check if correct
            ws1.updateAndCommit();
            ws2.updateAndCommit();

            //				
            System.out.println(unzipDestinationFile.getAbsolutePath());

            //
            String delta = dir + "/delta.xml";
            assertTrue("File different: " + currentEntry, FileUtils.compareXmlFile(dir1 + "/init.xml", dir2 + "/init.xml"));

            if (FileUtils.isXmlFile(xmlFilePath)) {
                nbFile++;
            }

            previousEntry = currentEntry;
        }

        zipFile.close();
    }

    public void testXmlTestSuiteWithoutReWrite() throws Exception {
        //String xmlFilePath = dir1 + File.separator + "init.xml";
        File sourceZipFile = new File(zipFilePath);
        ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
        Enumeration zipFileEntries = zipFile.entries();
        String currentProblem = new String();

        while (zipFileEntries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
            currentEntry = entry.getName();

            StringTokenizer tokens = new StringTokenizer(currentEntry, "/");
            String fileName = new String();

            while (tokens.hasMoreTokens()) {
                fileName = tokens.nextToken();
            }

            //System.out.println(fileName);
            File unzipDestinationFile = new File(dir1 + File.separator + fileName);
            FileUtils.createXmlFile("root", unzipDestinationFile.getAbsolutePath());
            ws1.updateAndCommit();
            ws2.updateAndCommit();

            String delta = dir + "/delta.xml";
            assertTrue("File different: " + currentEntry, FileUtils.compareXmlFile(dir1 + File.separator + fileName, dir2 + File.separator + fileName));

            if (!entry.isDirectory()) {
                BufferedInputStream is = new BufferedInputStream(zipFile.getInputStream(entry));
                FileOutputStream fos = new FileOutputStream(unzipDestinationFile);
                int size = is.available();
                int currentByte;
                byte[] data = new byte[size];
                BufferedOutputStream dest = new BufferedOutputStream(fos, size);

                while ((currentByte = is.read(data)) != -1) {
                    dest.write(data, 0, currentByte);
                }

                dest.close();
                is.close();
                fos.close();
            }

            ws1.updateAndCommit();
            ws2.updateAndCommit();
            assertTrue("File different: " + currentEntry, FileUtils.compareXmlFile(dir1 + File.separator + fileName, dir2 + File.separator + fileName));
        }
    }

    public void testXmlTestSuiteA() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<!DOCTYPE root [\n  <!ELEMENT root (a,b)>\n  <!ELEMENT a EMPTY>\n  <!ELEMENT b (#PCDATA|c)* >\n  <!ELEMENT c ANY>\n  <!ELEMENT d ((e,e)|f)+ >\n  <!ELEMENT e ANY>\n  <!ELEMENT f EMPTY>\n]>\n<root><a/><b>\n    <c></c> \n  content of b element\n  <c>\n\n     <d><e>no more children</e><e><f/></e><f/></d>\n  </c>\n</b></root>\n<!--* test P39's syntax and Element Valid VC *-->\n";
        File f = new File(dir1 + "/init.xml");
        FileOutputStream fos = new FileOutputStream(f);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.flush();
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    public void testXmlTestSuiteB() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<!DOCTYPE root [\n  <!ELEMENT root (a,b)>\n  <!ELEMENT a EMPTY>\n  <!ELEMENT b (#PCDATA|c)* >\n  <!ELEMENT c ANY>\n]>\n<root><a/><b>\n   <c></c > : End tag with a space inside\n   content of b element\n</b></root>\n<!--* test P42 *-->\n\n";
        File f = new File(dir1 + "/init.xml");
        FileOutputStream fos = new FileOutputStream(f);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.flush();
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    public void testXmlTestSuiteC() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<!DOCTYPE root [\n  <!ELEMENT root (#PCDATA|b)* >\n  <!ELEMENT b (#PCDATA) >\n  <!ATTLIST b attr2 (abc|def) \"abc\">\n  <!ATTLIST b attr3 CDATA #FIXED \"fixed\">\n]>\n<root>\n  <b attr1=\"value1\" attr2=\"def\" attr3=\"fixed\">attr1 not declared</b>\n</root>\n<!--* testing VC:Attribute Value Type  *-->\n";
        File f = new File(dir1 + "/init.xml");
        FileOutputStream fos = new FileOutputStream(f);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.flush();
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    public void testXmlTestSuite001A() throws Exception {
        // Extract from W3C XML test suite
        // path xmlconf/eduni/xml-1.1/0001.xml
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n		<!--External subset has later version number--><!DOCTYPE foo SYSTEM \"001.dtd\"> <foo/> ";
        File f = new File(dir1 + "/init.xml");
        FileOutputStream fos = new FileOutputStream(f);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.flush();
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        String delta = dir1 + "/delta.xml";
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    public void testXmlTestSuite001B() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<!DOCTYPE doc [\n<!ENTITY e SYSTEM \"001.ent\">\n]>\n<doc>&e;</doc>";
        File f = new File(dir1 + "/init.xml");
        FileOutputStream fos = new FileOutputStream(f);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.flush();
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }
}
