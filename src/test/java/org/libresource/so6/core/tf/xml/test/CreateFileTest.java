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

import fr.loria.ecoo.so6.xml.node.TextNode;
import fr.loria.ecoo.so6.xml.util.XmlUtil;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;
import java.io.FileOutputStream;


/**
 * @author tani
 */
public class CreateFileTest extends TestCase {
    private String dir1;
    private String dir2;
    private String dir;
    private WsConnection ws1;
    private WsConnection ws2;
    private String xmlFilePath1;
    private String xmlFilePath2;
    private String delta;

    public CreateFileTest(String name) {
        super(name);
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
        delta = dir + File.separator + "delta.xml";
        FileUtils.createXmlFile("root", xmlFilePath1);
        XmlUtil.insertNode(xmlFilePath1, "0:0", new TextNode("txt1"));
        XmlUtil.insertNode(xmlFilePath1, "0:0", new TextNode("txt2"));
        ws1.updateAndCommit();
        ws2.updateAndCommit();
    }

    protected void tearDown() {
        System.out.println(dir);
    }

    public void testCreateFileB() throws Exception {
        // path of the file in the XML Test Suite
        // xmlconf/xmltest/valid/sa/out/085.xml
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        //String c = "<doc></doc>";
        String c = "<?xml version=\"1.0\"?><doc/>";
        File f = new File(xmlFilePath1);
        FileOutputStream fos = new FileOutputStream(f);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.flush();
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }
}
