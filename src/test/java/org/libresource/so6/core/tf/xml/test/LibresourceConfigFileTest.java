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

import fr.loria.ecoo.so6.xml.node.Document;
import fr.loria.ecoo.so6.xml.util.XmlUtil;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;


/**
 * @author tani
 */
public class LibresourceConfigFileTest extends TestCase {
    private String dirPath = "./demoxml/ls-test/";
    private String delta;
    private String dir;
    private String dir1;
    private String dir2;
    private String tempo;
    private WsConnection ws1;
    private WsConnection ws2;
    private String xmlFilePath1;
    private String xmlFilePath2;

    /**
     * Constructor for LibresourceConfigFileTest.
     *
     * @param arg0
     */
    public LibresourceConfigFileTest(String arg0) {
        super(arg0);
    }

    public void setUp() throws Exception {
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
        ws1.updateAndCommit();
        ws2.updateAndCommit();
    }

    public void tearDown() {
        System.out.println(dir);
    }

    public void testFileTest1() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
        tempo = dirPath + "test1.xml";

        Document doc = XmlUtil.load(tempo);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testFileTest2() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
        tempo = dirPath + "test2.xml";

        Document doc = XmlUtil.load(tempo);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testFileTest3() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
        tempo = dirPath + "test3.xml";

        Document doc = XmlUtil.load(tempo);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testFileTest4() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
        tempo = dirPath + "test4.xml";

        Document doc = XmlUtil.load(tempo);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }
}
