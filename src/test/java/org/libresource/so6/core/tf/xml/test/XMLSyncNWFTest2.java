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
public class XMLSyncNWFTest2 extends TestCase {
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;
    private String baseDir;
    private String xmlFilePath1;
    private String xmlFilePath2;

    public XMLSyncNWFTest2(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        // TODO something else (relative path or copy files) ???
        baseDir = "c:/xmltree/test/nwf";

        //
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2, true);
        ws1 = ws[0];
        ws2 = ws[1];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();
        xmlFilePath1 = dir1 + "/test.xml";
        xmlFilePath2 = dir2 + "/test.xml";
        FileUtils.createXmlFile("root", xmlFilePath1, "UTF-8");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
    }

    protected void tearDown() throws Exception {
        System.out.println(dir);
    }

    public void testSyncFile801() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file801.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile802() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file802.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile803() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file803.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile804() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file804.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile805() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file805.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile806() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file806.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile807() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file807.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile808() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file808.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile809() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file809.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile810() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file810.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile811() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file811.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile812() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file812.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile813() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file813.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile814() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file814.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile815() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file815.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile816() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file816.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile817() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file817.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile818() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file818.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile819() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file819.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile820() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file820.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile821() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file821.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile822() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file822.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile823() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file823.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile824() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file824.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile825() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file825.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile826() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file826.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile827() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file827.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile828() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file828.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile829() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file829.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile830() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file830.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile831() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file831.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile832() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file832.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile833() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file833.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile834() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file834.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile835() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file835.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile836() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file836.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile837() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file837.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile838() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file838.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile839() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file839.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile840() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file840.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile841() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file841.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile842() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file842.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile843() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file843.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile844() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file844.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile845() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file845.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile846() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file846.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile847() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file847.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile848() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file848.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile849() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file849.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile850() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file850.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile851() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file851.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile852() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file852.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile853() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file853.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile854() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file854.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile855() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file855.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile856() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file856.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile857() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file857.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile858() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file858.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile859() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file859.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile860() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file860.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile861() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file861.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile862() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file862.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile863() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file863.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile864() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file864.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile865() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file865.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile866() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file866.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile867() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file867.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile868() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file868.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile869() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file869.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile870() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file870.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile871() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file871.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile872() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file872.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile873() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file873.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile874() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file874.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile875() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file875.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile876() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file876.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile877() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file877.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile878() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file878.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile879() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file879.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile880() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file880.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile881() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file881.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile882() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file882.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile883() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file883.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile884() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file884.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile885() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file885.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile886() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file886.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile887() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file887.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile888() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file888.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile889() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file889.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile890() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file890.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile891() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file891.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile892() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file892.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile893() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file893.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile894() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file894.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile895() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file895.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile896() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file896.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile897() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file897.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile898() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file898.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile899() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file899.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile900() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file900.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile901() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file901.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile902() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file902.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile903() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file903.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile904() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file904.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile905() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file905.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile906() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file906.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile907() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file907.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile908() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file908.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile909() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file909.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile910() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file910.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile911() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file911.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile912() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file912.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile913() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file913.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile914() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file914.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile915() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file915.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile916() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file916.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile917() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file917.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile918() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file918.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile919() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file919.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile920() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file920.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile921() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file921.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile922() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file922.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile923() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file923.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile924() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file924.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile925() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file925.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile926() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file926.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile927() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file927.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile928() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file928.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile929() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file929.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile930() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file930.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile931() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file931.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile932() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file932.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile933() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file933.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile934() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file934.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile935() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file935.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile936() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file936.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile937() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file937.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile938() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file938.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile939() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file939.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile940() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file940.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile941() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file941.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile942() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file942.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile943() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file943.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile944() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file944.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile945() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file945.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile946() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file946.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile947() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file947.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile948() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file948.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile949() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file949.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile950() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file950.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile951() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file951.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile952() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file952.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile953() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file953.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile954() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file954.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile955() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file955.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile956() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file956.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile957() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file957.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile958() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file958.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile959() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file959.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile960() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file960.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile961() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file961.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile962() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file962.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile963() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file963.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile964() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file964.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile965() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file965.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile966() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file966.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile967() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file967.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile968() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file968.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile969() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file969.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile970() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file970.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile971() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file971.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile972() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file972.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile973() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file973.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile974() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file974.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile975() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file975.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile976() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file976.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile977() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file977.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile978() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file978.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile979() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file979.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile980() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file980.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile981() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file981.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile982() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file982.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile983() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file983.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile984() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file984.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile985() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file985.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile986() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file986.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile987() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file987.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile988() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file988.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile989() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file989.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile990() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file990.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile991() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file991.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile992() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file992.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile993() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file993.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile994() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file994.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile995() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file995.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile996() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file996.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile997() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file997.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile998() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file998.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile999() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file999.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile1000() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file1000.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }
}
