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
public class XMLSyncWFTest extends TestCase {
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;
    private String baseDir;
    private String xmlFilePath1;
    private String xmlFilePath2;

    public XMLSyncWFTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        // TODO something else (relative path or copy files) ???
        baseDir = "c:/xmltree/test/wf";

        //
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

    public void testSyncFile1() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file1.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile2() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file2.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile3() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file3.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile4() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file4.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile5() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file5.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile6() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file6.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile7() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file7.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile8() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file8.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile9() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file9.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile10() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file10.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile11() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file11.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile12() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file12.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile13() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file13.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile14() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file14.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile15() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file15.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile16() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file16.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile17() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file17.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile18() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file18.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile19() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file19.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile20() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file20.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile21() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file21.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile22() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file22.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile23() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file23.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile24() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file24.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile25() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file25.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile26() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file26.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile27() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file27.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile28() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file28.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile29() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file29.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile30() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file30.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile31() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file31.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile32() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file32.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile33() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file33.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile34() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file34.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile35() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file35.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile36() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file36.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile37() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file37.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile38() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file38.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile39() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file39.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile40() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file40.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile41() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file41.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile42() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file42.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile43() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file43.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile44() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file44.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile45() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file45.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile46() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file46.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile47() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file47.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile48() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file48.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile49() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file49.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile50() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file50.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile51() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file51.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile52() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file52.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile53() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file53.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile54() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file54.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile55() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file55.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile56() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file56.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile57() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file57.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile58() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file58.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile59() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file59.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile60() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file60.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile61() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file61.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile62() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file62.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile63() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file63.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile64() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file64.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile65() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file65.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile66() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file66.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile67() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file67.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile68() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file68.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile69() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file69.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile70() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file70.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile71() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file71.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile72() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file72.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile73() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file73.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile74() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file74.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile75() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file75.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile76() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file76.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile77() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file77.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile78() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file78.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile79() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file79.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile80() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file80.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile81() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file81.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile82() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file82.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile83() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file83.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile84() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file84.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile85() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file85.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile86() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file86.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile87() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file87.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile88() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file88.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile89() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file89.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile90() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file90.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile91() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file91.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile92() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file92.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile93() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file93.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile94() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file94.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile95() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file95.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile96() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file96.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile97() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file97.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile98() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file98.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile99() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file99.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile100() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file100.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile101() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file101.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile102() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file102.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile103() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file103.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile104() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file104.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile105() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file105.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile106() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file106.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile107() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file107.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile108() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file108.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile109() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file109.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile110() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file110.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile111() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file111.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile112() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file112.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile113() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file113.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile114() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file114.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile115() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file115.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile116() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file116.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile117() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file117.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile118() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file118.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile119() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file119.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile120() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file120.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile121() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file121.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile122() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file122.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile123() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file123.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile124() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file124.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile125() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file125.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile126() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file126.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile127() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file127.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile128() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file128.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile129() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file129.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile130() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file130.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile131() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file131.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile132() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file132.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile133() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file133.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile134() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file134.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile135() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file135.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile136() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file136.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile137() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file137.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile138() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file138.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile139() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file139.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile140() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file140.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile141() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file141.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile142() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file142.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile143() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file143.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile144() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file144.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile145() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file145.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile146() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file146.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile147() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file147.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile148() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file148.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile149() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file149.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile150() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file150.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile151() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file151.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile152() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file152.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile153() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file153.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile154() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file154.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile155() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file155.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile156() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file156.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile157() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file157.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile158() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file158.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile159() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file159.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile160() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file160.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile161() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file161.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile162() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file162.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile163() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file163.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile164() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file164.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile165() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file165.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile166() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file166.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile167() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file167.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile168() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file168.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile169() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file169.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile170() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file170.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile171() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file171.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile172() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file172.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile173() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file173.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile174() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file174.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile175() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file175.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile176() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file176.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile177() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file177.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile178() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file178.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile179() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file179.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile180() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file180.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile181() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file181.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile182() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file182.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile183() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file183.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile184() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file184.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile185() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file185.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile186() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file186.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile187() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file187.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile188() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file188.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile189() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file189.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile190() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file190.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile191() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file191.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile192() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file192.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile193() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file193.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile194() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file194.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile195() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file195.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile196() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file196.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile197() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file197.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile198() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file198.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile199() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file199.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile200() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file200.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile201() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file201.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile202() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file202.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile203() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file203.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile204() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file204.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile205() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file205.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile206() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file206.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile207() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file207.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile208() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file208.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile209() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file209.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile210() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file210.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile211() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file211.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile212() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file212.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile213() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file213.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile214() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file214.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile215() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file215.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile216() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file216.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile217() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file217.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile218() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file218.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile219() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file219.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile220() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file220.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile221() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file221.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile222() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file222.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile223() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file223.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile224() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file224.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile225() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file225.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile226() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file226.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile227() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file227.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile228() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file228.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile229() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file229.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile230() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file230.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile231() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file231.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile232() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file232.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile233() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file233.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile234() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file234.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile235() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file235.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile236() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file236.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile237() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file237.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile238() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file238.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile239() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file239.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile240() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file240.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile241() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file241.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile242() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file242.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile243() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file243.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile244() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file244.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile245() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file245.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile246() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file246.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile247() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file247.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile248() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file248.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile249() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file249.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile250() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file250.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile251() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file251.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile252() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file252.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile253() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file253.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile254() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file254.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile255() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file255.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile256() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file256.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile257() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file257.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile258() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file258.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile259() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file259.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile260() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file260.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile261() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file261.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile262() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file262.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile263() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file263.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile264() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file264.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile265() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file265.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile266() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file266.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile267() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file267.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile268() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file268.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile269() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file269.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile270() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file270.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile271() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file271.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile272() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file272.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile273() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file273.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile274() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file274.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile275() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file275.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile276() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file276.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile277() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file277.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile278() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file278.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile279() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file279.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile280() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file280.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile281() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file281.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile282() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file282.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile283() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file283.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile284() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file284.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile285() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file285.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile286() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file286.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile287() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file287.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile288() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file288.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile289() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file289.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile290() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file290.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile291() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file291.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile292() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file292.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile293() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file293.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile294() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file294.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile295() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file295.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile296() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file296.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile297() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file297.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile298() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file298.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile299() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file299.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile300() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file300.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile301() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file301.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile302() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file302.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile303() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file303.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile304() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file304.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile305() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file305.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile306() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file306.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile307() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file307.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile308() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file308.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile309() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file309.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile310() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file310.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile311() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file311.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile312() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file312.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile313() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file313.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile314() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file314.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile315() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file315.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile316() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file316.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile317() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file317.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile318() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file318.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile319() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file319.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile320() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file320.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile321() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file321.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile322() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file322.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile323() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file323.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile324() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file324.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile325() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file325.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile326() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file326.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile327() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file327.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile328() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file328.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile329() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file329.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile330() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file330.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile331() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file331.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile332() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file332.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile333() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file333.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile334() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file334.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile335() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file335.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile336() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file336.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile337() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file337.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile338() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file338.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile339() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file339.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile340() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file340.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile341() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file341.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile342() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file342.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile343() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file343.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile344() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file344.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile345() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file345.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile346() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file346.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile347() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file347.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile348() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file348.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile349() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file349.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile350() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file350.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile351() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file351.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile352() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file352.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile353() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file353.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile354() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file354.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile355() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file355.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile356() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file356.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile357() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file357.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile358() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file358.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile359() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file359.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile360() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file360.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile361() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file361.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile362() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file362.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile363() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file363.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile364() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file364.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile365() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file365.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile366() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file366.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile367() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file367.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile368() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file368.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile369() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file369.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile370() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file370.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile371() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file371.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile372() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file372.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile373() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file373.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile374() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file374.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile375() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file375.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile376() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file376.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile377() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file377.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile378() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file378.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile379() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file379.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile380() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file380.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile381() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file381.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile382() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file382.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile383() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file383.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile384() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file384.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile385() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file385.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile386() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file386.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile387() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file387.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile388() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file388.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile389() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file389.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile390() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file390.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile391() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file391.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile392() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file392.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile393() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file393.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile394() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file394.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile395() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file395.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile396() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file396.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile397() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file397.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile398() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file398.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile399() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file399.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile400() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file400.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile401() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file401.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile402() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file402.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile403() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file403.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile404() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file404.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile405() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file405.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile406() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file406.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile407() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file407.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile408() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file408.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile409() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file409.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile410() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file410.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile411() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file411.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile412() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file412.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile413() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file413.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile414() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file414.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile415() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file415.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile416() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file416.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile417() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file417.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile418() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file418.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile419() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file419.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile420() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file420.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile421() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file421.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile422() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file422.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile423() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file423.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile424() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file424.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile425() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file425.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile426() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file426.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile427() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file427.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile428() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file428.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile429() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file429.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile430() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file430.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile431() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file431.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile432() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file432.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile433() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file433.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile434() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file434.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile435() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file435.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile436() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file436.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile437() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file437.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile438() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file438.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile439() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file439.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile440() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file440.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile441() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file441.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile442() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file442.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile443() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file443.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile444() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file444.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile445() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file445.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile446() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file446.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile447() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file447.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile448() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file448.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile449() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file449.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile450() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file450.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile451() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file451.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile452() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file452.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile453() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file453.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile454() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file454.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile455() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file455.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile456() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file456.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile457() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file457.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile458() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file458.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile459() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file459.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile460() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file460.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile461() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file461.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile462() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file462.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile463() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file463.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile464() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file464.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile465() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file465.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile466() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file466.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile467() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file467.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile468() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file468.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile469() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file469.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile470() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file470.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile471() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file471.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile472() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file472.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile473() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file473.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile474() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file474.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile475() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file475.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile476() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file476.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile477() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file477.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile478() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file478.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile479() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file479.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile480() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file480.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile481() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file481.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile482() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file482.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile483() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file483.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile484() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file484.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile485() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file485.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile486() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file486.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile487() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file487.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile488() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file488.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile489() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file489.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile490() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file490.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile491() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file491.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile492() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file492.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile493() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file493.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile494() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file494.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile495() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file495.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile496() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file496.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile497() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file497.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile498() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file498.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile499() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file499.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile500() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file500.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile501() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file501.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile502() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file502.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile503() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file503.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile504() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file504.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile505() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file505.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile506() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file506.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile507() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file507.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile508() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file508.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile509() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file509.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile510() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file510.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile511() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file511.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile512() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file512.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile513() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file513.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile514() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file514.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile515() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file515.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile516() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file516.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile517() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file517.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile518() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file518.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile519() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file519.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile520() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file520.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile521() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file521.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile522() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file522.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile523() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file523.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile524() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file524.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile525() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file525.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile526() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file526.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile527() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file527.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile528() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file528.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile529() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file529.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile530() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file530.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile531() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file531.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile532() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file532.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile533() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file533.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile534() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file534.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile535() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file535.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile536() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file536.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile537() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file537.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile538() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file538.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile539() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file539.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile540() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file540.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile541() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file541.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile542() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file542.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile543() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file543.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile544() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file544.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile545() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file545.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile546() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file546.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile547() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file547.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile548() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file548.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile549() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file549.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile550() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file550.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile551() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file551.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile552() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file552.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile553() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file553.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile554() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file554.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile555() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file555.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile556() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file556.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile557() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file557.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile558() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file558.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile559() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file559.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile560() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file560.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile561() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file561.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile562() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file562.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile563() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file563.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile564() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file564.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile565() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file565.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile566() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file566.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile567() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file567.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile568() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file568.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile569() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file569.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile570() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file570.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile571() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file571.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile572() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file572.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile573() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file573.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile574() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file574.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile575() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file575.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile576() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file576.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile577() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file577.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile578() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file578.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile579() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file579.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile580() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file580.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile581() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file581.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile582() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file582.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile583() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file583.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile584() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file584.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile585() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file585.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile586() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file586.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile587() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file587.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile588() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file588.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile589() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file589.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile590() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file590.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile591() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file591.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile592() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file592.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile593() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file593.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile594() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file594.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile595() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file595.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile596() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file596.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile597() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file597.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile598() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file598.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile599() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file599.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile600() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file600.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile601() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file601.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile602() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file602.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile603() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file603.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile604() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file604.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile605() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file605.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile606() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file606.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile607() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file607.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile608() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file608.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile609() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file609.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile610() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file610.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile611() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file611.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile612() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file612.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile613() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file613.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile614() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file614.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile615() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file615.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile616() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file616.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile617() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file617.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile618() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file618.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile619() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file619.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile620() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file620.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile621() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file621.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile622() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file622.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile623() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file623.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile624() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file624.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile625() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file625.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile626() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file626.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile627() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file627.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile628() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file628.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile629() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file629.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile630() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file630.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile631() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file631.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile632() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file632.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile633() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file633.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile634() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file634.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile635() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file635.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile636() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file636.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile637() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file637.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile638() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file638.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile639() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file639.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile640() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file640.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile641() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file641.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile642() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file642.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile643() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file643.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile644() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file644.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile645() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file645.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile646() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file646.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile647() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file647.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile648() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file648.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile649() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file649.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile650() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file650.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile651() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file651.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile652() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file652.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile653() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file653.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile654() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file654.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile655() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file655.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile656() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file656.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile657() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file657.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile658() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file658.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile659() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file659.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile660() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file660.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile661() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file661.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile662() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file662.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile663() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file663.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile664() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file664.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile665() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file665.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile666() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file666.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile667() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file667.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile668() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file668.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile669() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file669.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile670() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file670.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile671() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file671.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile672() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file672.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile673() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file673.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile674() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file674.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile675() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file675.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile676() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file676.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile677() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file677.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile678() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file678.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile679() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file679.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile680() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file680.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile681() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file681.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile682() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file682.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile683() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file683.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile684() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file684.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile685() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file685.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile686() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file686.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile687() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file687.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile688() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file688.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile689() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file689.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile690() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file690.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile691() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file691.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile692() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file692.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile693() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file693.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile694() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file694.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile695() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file695.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile696() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file696.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile697() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file697.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile698() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file698.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile699() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file699.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile700() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file700.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile701() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file701.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile702() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file702.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile703() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file703.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile704() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file704.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile705() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file705.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile706() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file706.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile707() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file707.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile708() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file708.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile709() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file709.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile710() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file710.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile711() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file711.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile712() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file712.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile713() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file713.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile714() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file714.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile715() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file715.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile716() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file716.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile717() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file717.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile718() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file718.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile719() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file719.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile720() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file720.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile721() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file721.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile722() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file722.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile723() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file723.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile724() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file724.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile725() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file725.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile726() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file726.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile727() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file727.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile728() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file728.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile729() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file729.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile730() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file730.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile731() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file731.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile732() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file732.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile733() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file733.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile734() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file734.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile735() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file735.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile736() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file736.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile737() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file737.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile738() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file738.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile739() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file739.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile740() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file740.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile741() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file741.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile742() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file742.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile743() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file743.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile744() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file744.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile745() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file745.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile746() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file746.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile747() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file747.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile748() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file748.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile749() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file749.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile750() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file750.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile751() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file751.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile752() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file752.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile753() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file753.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile754() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file754.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile755() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file755.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile756() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file756.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile757() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file757.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile758() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file758.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile759() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file759.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile760() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file760.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile761() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file761.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile762() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file762.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile763() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file763.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile764() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file764.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile765() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file765.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile766() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file766.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile767() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file767.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile768() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file768.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile769() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file769.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile770() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file770.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile771() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file771.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile772() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file772.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile773() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file773.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile774() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file774.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile775() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file775.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile776() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file776.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile777() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file777.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile778() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file778.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile779() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file779.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile780() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file780.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile781() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file781.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile782() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file782.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile783() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file783.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile784() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file784.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile785() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file785.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile786() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file786.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile787() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file787.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile788() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file788.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile789() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file789.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile790() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file790.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile791() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file791.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile792() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file792.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile793() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file793.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile794() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file794.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile795() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file795.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile796() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file796.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile797() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file797.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile798() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file798.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile799() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file799.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile800() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file800.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile801() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file801.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile802() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file802.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile803() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file803.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile804() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file804.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile805() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file805.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile806() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file806.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile807() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file807.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile808() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file808.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile809() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file809.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile810() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file810.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile811() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file811.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile812() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file812.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile813() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file813.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile814() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file814.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile815() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file815.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile816() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file816.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile817() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file817.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile818() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file818.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile819() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file819.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile820() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file820.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile821() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file821.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile822() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file822.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile823() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file823.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile824() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file824.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile825() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file825.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile826() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file826.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile827() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file827.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile828() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file828.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile829() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file829.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile830() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file830.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile831() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file831.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile832() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file832.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile833() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file833.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile834() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file834.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile835() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file835.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile836() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file836.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile837() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file837.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile838() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file838.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile839() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file839.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile840() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file840.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile841() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file841.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile842() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file842.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile843() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file843.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile844() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file844.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile845() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file845.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile846() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file846.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile847() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file847.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile848() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file848.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile849() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file849.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile850() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file850.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile851() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file851.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile852() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file852.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile853() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file853.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile854() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file854.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile855() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file855.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile856() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file856.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile857() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file857.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile858() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file858.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile859() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file859.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile860() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file860.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile861() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file861.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile862() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file862.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile863() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file863.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile864() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file864.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile865() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file865.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile866() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file866.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile867() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file867.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile868() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file868.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile869() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file869.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile870() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file870.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile871() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file871.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile872() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file872.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile873() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file873.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile874() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file874.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile875() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file875.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile876() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file876.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile877() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file877.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile878() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file878.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile879() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file879.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile880() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file880.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile881() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file881.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile882() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file882.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile883() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file883.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile884() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file884.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile885() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file885.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile886() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file886.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile887() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file887.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile888() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file888.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile889() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file889.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile890() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file890.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile891() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file891.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile892() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file892.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile893() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file893.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile894() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file894.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile895() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file895.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile896() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file896.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile897() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file897.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile898() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file898.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile899() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file899.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile900() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file900.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile901() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file901.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile902() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file902.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile903() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file903.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile904() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file904.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile905() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file905.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile906() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file906.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile907() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file907.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile908() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file908.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile909() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file909.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile910() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file910.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile911() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file911.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile912() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file912.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile913() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file913.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile914() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file914.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile915() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file915.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile916() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file916.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile917() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file917.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile918() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file918.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile919() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file919.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile920() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file920.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile921() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file921.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile922() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file922.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile923() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file923.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile924() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file924.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile925() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file925.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSyncFile926() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        String path = baseDir + File.separator + "file926.xml";
        Document doc = XmlUtil.load(path);
        doc.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }
}
