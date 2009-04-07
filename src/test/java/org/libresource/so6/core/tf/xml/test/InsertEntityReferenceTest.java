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

import java.io.FileOutputStream;


/**
 * @author tani
 */
public class InsertEntityReferenceTest extends TestCase {
    private String delta;
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;
    private String xmlFilePath1;
    private String xmlFilePath2;

    /**
     * Constructor for InsertEntityReferenceTest.
     *
     * @param arg0
     */
    public InsertEntityReferenceTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2, true);
        ws1 = ws[0];
        ws2 = ws[1];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();
        xmlFilePath1 = dir1 + "/test.xml";
        xmlFilePath2 = dir2 + "/test.xml";
        delta = dir + "/delta.xml";
        FileUtils.createXmlFile("root", xmlFilePath1);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
    }

    protected void tearDown() throws Exception {
        System.out.println(dir);
    }

    public void testCRLF() throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        /*
         * XML Test Suite path xmlconf/xmltest/valid/sa/108.xml
         */
        String c = "<?xml version=\"1.0\"?>\n<!DOCTYPE doc [\n<!ELEMENT doc (#PCDATA)>\n<!ENTITY e \"\n\">\n<!ATTLIST doc a CDATA #IMPLIED>\n]>\n<doc a=\"x&e;y\"></doc>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertDefinedEntityInAttributeA() throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<doc a=\"&lt;&gt;&quot;\">&amp;&quot;</doc>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertDefinedEntityInAttributeB() throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<doc a=\"&lt;test&quot;&gt;\">test</doc>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertDefineInternalEntity() throws Exception {
        assertTrue(FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<!DOCTYPE doc [\n  <!ELEMENT doc (#PCDATA)*>]><doc>&amp;&lt;&gt;&quot;&apos;</doc>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertEntityCarriageReturnInDocumentTypeA()
        throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        /*
         * XML Test Suite path xmlconf/xmltest/valid/sa/068.xml
         */
        String c = "<?xml version=\"1.0\"?>\n<!DOCTYPE doc [\n  <!ELEMENT doc (#PCDATA)*>\n  <!ENTITY e \"&#13;\">\n]><doc>&e;<a/></doc>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertEntityCarriageReturnInDocumentTypeB()
        throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        /*
         * XML Test Suite path xmlconf/eduni/xml-1.1/050.xml
         */
        String c = "<?xml version=\"1.0\"?>\n<!DOCTYPE foo [\n  <!ELEMENT foo (foo*)>\n  <!ENTITY e \"&#13;\">\n]><foo>&e;</foo>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertEntityCRInAttribute() throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<doc a=\"&#13;\"/>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertEntityInAttributeC() throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<doc a=\"perch&#232;\">test</doc>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertExternalEntityReference() throws Exception {
        /*
         * XML Test Suite path xmlconf/xmltest/valid/ext-sa/002.xml
         */
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<!DOCTYPE doc [\n  <!ELEMENT doc (#PCDATA)*>\n  <!ENTITY e SYSTEM \"005.ent\">\n]><doc>&e;</doc>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertExternalEntityReferenceWithURI()
        throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<!DOCTYPE doc [\n  <!ELEMENT doc (#PCDATA)*>\n  <!ENTITY e SYSTEM \"http://example.org/test.ent\">\n]><doc>&e;</doc>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertInternalEntity() throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<doc>&#232;�</doc>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertInternalEntityCarriageReturn()
        throws Exception {
        /*
         * XML Test Suite path xmlconf/xmltest/valid/sa/out/068.xml
         */
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<doc>&#13;</doc>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertInternalEntityCarriageReturnInHexa()
        throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<doc>&#x0d;</doc>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertInternalEntityInDecimal() throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<doc>perch&#232;</doc>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertInternalEntityWithStringValue()
        throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<doc>&amp;&lt;&gt;&apos;&quot;</doc>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertPEReference() throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        /*
         * XML Test Suite path xmlconf/xmltest/valid/sa/out/094.xml
         */
        String c = "<?xml version=\"1.0\"?>\n<doc a1=\"%e;\"/>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertTextNode() throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<doc>perch</doc>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testSA01AFromXMLTestSuite() throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        /*
         * XML Test Suite path xmlconf/sun/valid/out/sa01.xml
         */
        String c = "<?xml version=\"1.0\"?>\n<root>&#10;<child>&#10;    The whitespace around this element would be&#10;    invalid as standalone were the DTD external.&#10;</child>&#10;</root>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }
}
