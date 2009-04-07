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
package org.libresource.so6.core.tf.xml.test;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;
import java.io.FileOutputStream;


/**
 * @author tani
 */
public class XmlTestAttribute012 extends TestCase {
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;
    private String xmlFilePath1;
    private String xmlFilePath2;

    // These test failed, be in the end of the package not to change the result
    // of the other test
    public XmlTestAttribute012(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2, true);
        ws1 = ws[0];
        ws2 = ws[1];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();
        xmlFilePath1 = dir1 + File.separator + "test.xml";
        xmlFilePath2 = dir2 + File.separator + "test.xml";
        FileUtils.createXmlFile("root", xmlFilePath1, "UTF-8");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
    }

    protected void tearDown() throws Exception {
        System.out.println(dir);
    }

    public void testXML012C() throws Exception {
        /*
         * path xmlconf/xmltest/valid/sa/012.xml
         */
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String c = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE doc [\n<!ELEMENT doc (#PCDATA)>\n<!ATTLIST doc : CDATA #IMPLIED>\n]>\n<doc :=\"v1\"></doc>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testXML012D() throws Exception {
        /*
         * path xmlconf/valid/sa/out/012.xml
         */
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String c = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<doc :=\"v1\"></doc>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }
}
