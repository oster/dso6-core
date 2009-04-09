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
package org.libresource.so6.core.tf.xml.test.openoffice;

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
public class OpenOffice3Test extends TestCase {
    // Guides des polices
    private String baseDir;
    private String path;
    private String dir1;
    private String dir2;
    private String dir;
    private String xmlFilePath1;
    private String xmlFilePath2;
    private WsConnection ws1;
    private WsConnection ws2;

    public OpenOffice3Test(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        baseDir = "./demoxml/oo";
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2, true);
        ws1 = ws[0];
        ws2 = ws[1];
        new File(dir).mkdirs();
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();
        xmlFilePath1 = dir1 + File.separator + "test.xml";
        xmlFilePath2 = dir2 + File.separator + "test.xml";

        // Init XML File
        FileUtils.createXmlFile("root", xmlFilePath1);

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
    }

    protected void tearDown() throws Exception {
        System.out.println(dir);
    }

    public void testContentFile3() throws Exception {
        assertTrue("Dir1 <> Dir2", FileUtils.compareDir(dir1, dir2));
        path = baseDir + File.separator + "file3" + File.separator + "content.xml";

        Document content = XmlUtil.load(path);
        content.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("Content", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testManifestFile3() throws Exception {
        assertTrue("Dir1 <> Dir2", FileUtils.compareDir(dir1, dir2));
        path = baseDir + File.separator + "file3" + File.separator + "manifest.xml";

        Document manifest = XmlUtil.load(path);
        manifest.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("Manifest", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testMetaFile3() throws Exception {
        assertTrue("Dir1 <> Dir2", FileUtils.compareDir(dir1, dir2));
        path = baseDir + File.separator + "file3" + File.separator + "meta.xml";

        Document meta = XmlUtil.load(path);
        meta.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("Meta", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSettingsFile3() throws Exception {
        assertTrue("Dir1 <> Dir2", FileUtils.compareDir(dir1, dir2));
        path = baseDir + File.separator + "file3" + File.separator + "settings.xml";

        Document settings = XmlUtil.load(path);
        settings.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("Settings", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testStylesFile3() throws Exception {
        assertTrue("Dir1 <> Dir2", FileUtils.compareDir(dir1, dir2));
        path = baseDir + File.separator + "file3" + File.separator + "styles.xml";

        Document styles = XmlUtil.load(path);
        styles.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("Styles", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }
}
