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

import fr.loria.ecoo.so6.xml.util.XmlUtil;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;
import java.io.FileOutputStream;


public class InsertAttributeTest extends TestCase {
    private String dir1;
    private String dir2;
    private String dir;
    private String xmlFilePath1;
    private String xmlFilePath2;
    private WsConnection ws1;
    private WsConnection ws2;

    public InsertAttributeTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2, true);
        ws1 = ws[0];
        ws2 = ws[1];
        new File(dir).mkdirs();
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();

        // init xml file
        String xmlFilePath = dir1 + File.separator + "text.xml";

        //
        FileUtils.createXmlFile("root", xmlFilePath);

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
    }

    /**
     * Attribute
     */
    public void tearDown() {
        System.out.println(dir);
    }

    public void testInsertAttribute() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        //
        String xmlFilePath1 = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        XmlUtil.insertAttribute(xmlFilePath1, "0:1", "attr1", "val1");
        XmlUtil.insertAttribute(xmlFilePath2, "0:1", "attr2", "val2");

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertAttributeWithConflict() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        //
        String xmlFilePath1 = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        XmlUtil.insertAttribute(xmlFilePath1, "0:1", "attr1", "val1");
        XmlUtil.insertAttribute(xmlFilePath2, "0:1", "attr1", "val2");

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertAttributeDeleteElement() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        //
        String xmlFilePath1 = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        XmlUtil.insertAttribute(xmlFilePath1, "0:1", "attr1", "val1");
        XmlUtil.deleteNode(xmlFilePath2, "0:1");

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    // extract from XML Test Suite W3C
    // v-lang01.xml
    public void testInsertAttributeWithSimpleQuote() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String xmlFilePath1 = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        String c = "<?xml version=\"1.0\"?>\n<root test='testA'/>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertWithDoubleQuoteContent() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<root test='te\"st'/>";
        String xmlFilePath1 = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    /*
     * Reference Attribute
     */
    public void testInsertEntityRef() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        //String c = "<?xml version=\"1.0\"?>\n<root test=\"&e;\"/>";
        String c = "<!DOCTYPE doc [\n<!ENTITY e SYSTEM \"001.ent\">\n]><doc>&e;</doc>";
        String xmlFilePath1 = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertCharRefDecimal() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<root test=\"&#10;\"/>";
        String xmlFilePath1 = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertCharRefHexaDecimalLowerCase()
        throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<root test=\"&#x0a;\"/>";
        String xmlFilePath1 = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertCharHexaDecimalUpperCase() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<root test=\"&#x0A;\"/>";
        String xmlFilePath1 = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    /*
     * Namespace
     */
    public void testInsertNamespaceA() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<root xmlns:example=\"http://example.org\"/>";
        String xmlFilePath1 = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertNamespaceB() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<root example=\"http://example.org\"/>";
        String xmlFilePath1 = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertNamespaceC() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<root test=\"val\" xmlns:example=\"http://example.org\"/>";
        String xmlFilePath1 = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertNamespaceD() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<root xmlns:example=\"http://example.org\" test=\"val\"/>";
        String xmlFilePath1 = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertNamespaceE() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<root xmlns=\"http://example.org/namespace#apples\"/>";
        String xmlFilePath1 = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testNamespaceF() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<root test=\"a:b\"/>";
        String xmlFilePath1 = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertNamespaceG() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<root xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"/>";
        String xmlFilePath1 = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertNamespaceH() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<root xml:lang=\"fr\"/>";
        String xmlFilePath1 = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertNamespaceI() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<root a:attr=\"val1\" xmlns:a=\"http://example.org\"/>";
        String xmlFilePath1 = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }
}
