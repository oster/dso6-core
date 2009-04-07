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

import fr.loria.ecoo.so6.xml.node.DocTypeNode;
import fr.loria.ecoo.so6.xml.node.Document;
import fr.loria.ecoo.so6.xml.node.ElementNode;
import fr.loria.ecoo.so6.xml.node.ProcessingInstructionNode;
import fr.loria.ecoo.so6.xml.util.XmlUtil;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.FileOutputStream;


/**
 * @author tani
 */
public class InsertProcessingInstructionTest extends TestCase {
    private String delta;
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;
    private String xmlFilePath1;
    private String xmlFilePath2;

    /**
     * Constructor for InsertProcessingInstructionTest.
     *
     * @param arg0
     */
    public InsertProcessingInstructionTest(String arg0) {
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

    public void testInsertProcessingIntructionOnRootElement()
        throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));
        XmlUtil.insertNode(xmlFilePath1, "0:0", new ProcessingInstructionNode("target", "content"));
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertProcessingInstructionOnElementA()
        throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        Document doc = new Document();
        ElementNode e = new ElementNode("a");
        e.setAttribute("xmlns:exemple", "http://example.org");
        e.appendChild(new ProcessingInstructionNode("test1", "test2"));
        doc.appendChild(e);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));
    }

    /*
     * public void testInsertValidCharacterData() throws Exception {
     *  // XML Test Suite // path xmlconf/oasis/p43pass1.xml
     *
     *
     * assertTrue(dir, FileUtils.compareDir(dir1, dir2));
     *
     * String c = " <?xml version=\"1.0\"?>\n <!DOCTYPE elem\n[\n <!ELEMENT elem
     * (#PCDATA|elem)*>\n <!ENTITY ent \"CharData\">\n]>\n
     * <elem>\nCharData&#32;\n <!--comment-->\n <![CDATA[\n
     * <elem>\nCharData&#32;\n <!--comment-->\n <?pi?>&ent;&quot;\nCharData\n
     * </elem>\n]]>\n <![CDATA[\n <elem>\nCharData&#32;\n <!--comment-->\n
     * <?pi?>&ent;&quot;\nCharData\n </elem>\n]]>\n
     * <?pi?>&ent;&quot;\nCharData\n </elem>"; FileOutputStream fos = new
     * FileOutputStream(xmlFilePath1); byte[] buffer = c.getBytes();
     * fos.write(buffer); fos.close();
     *
     * ws1.updateAndCommit(); ws2.updateAndCommit();
     *
     * assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2,
     * delta)); }
     */
    public void testInsertValidProcessingInstruction()
        throws Exception {
        /*
         * XML Test Suite path xmlconf/xmltest/valid/sa/036.xml
         */
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        Document doc = new Document();
        ElementNode e = new ElementNode("doc");
        e.appendChild(new ProcessingInstructionNode("pi", "data"));
        doc.appendChild(new DocTypeNode("doc [<!ELEMENT doc (#PCDATA)>]"));
        doc.appendChild(e);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertProceesingInstructionBeforeDocumentType()
        throws Exception {
        /*
         * XML Test Suite path xmlconf/xmltest/valid/sa/039.xml
         */
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<?pi data?>\n<!DOCTYPE doc [\n<!ELEMENT doc (#PCDATA)>\n]>\n<doc></doc>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertProcessingInstructionBeforeRoot()
        throws Exception {
        /*
         * XML Test Suite path xmlconf/xmltest/valid/sa/055.xml
         */
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        Document doc = new Document();
        ElementNode e = new ElementNode("doc");
        doc.appendChild(new DocTypeNode("doc [<!ELEMENT doc (#PCDATA)>]"));
        doc.appendChild(new ProcessingInstructionNode("pi", "data"));
        doc.appendChild(e);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertProcessingInstructionWithCarriageReturnValue()
        throws Exception {
        /*
         * XML Test Suite path xmlconf/xmltest/valid/sa/098.xml
         */
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        Document doc = new Document();
        ElementNode e = new ElementNode("doc");
        doc.appendChild(new DocTypeNode("doc [<!ELEMENT doc (#PCDATA)>]"));
        doc.appendChild(new ProcessingInstructionNode("pi", "x\ny"));
        doc.appendChild(e);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertProcessingInstructionWithEmptyValue()
        throws Exception {
        /*
         * XML Test Suite path xmlconf/xmltest/valid/sa/016.xml
         */
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        Document doc = new Document();
        ElementNode e = new ElementNode("doc");
        doc.appendChild(new DocTypeNode("doc [<!ELEMENT doc (#PCDATA)>]"));
        doc.appendChild(new ProcessingInstructionNode("pi", ""));
        doc.appendChild(e);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertProcessingInstructionWithNullValue()
        throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?>\n<root><?pi?></root>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertProcessingInstructionWithSeveralInstruction()
        throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        Document doc = new Document();
        ElementNode e = new ElementNode("doc");
        e.appendChild(new ProcessingInstructionNode("pi", "a testA b testB c testC d testD e testE f testF"));
        doc.appendChild(new DocTypeNode("doc [<!ELEMENT doc (#PCDATA)>]"));
        doc.appendChild(e);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertAmbiguousProcessingInstruction()
        throws Exception {
        /*
         * XML Test Suite path xmlconf/xmltest/valid/sa/017.xml
         */
        Document doc = new Document();
        ElementNode e = new ElementNode("doc");
        e.appendChild(new ProcessingInstructionNode("pi some", "data"));
        e.appendChild(new ProcessingInstructionNode("pi", "some data"));
        doc.appendChild(new DocTypeNode("doc [<!ELEMENT doc (#PCDATA)>]"));
        doc.appendChild(e);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));
    }

    /*
     * public void testInsertProcessingInstructionValidFormA() throws Exception { /*
     * XML Test Suite path xmlconf/oasis/p16pass1.xml
     *
     * assertTrue(dir, FileUtils.compareDir(dir1, dir2)); String c = " <?xml
     * version=\"1.0\"?>\n <?pitarget?>\n <?xmla <!DOCTYPE <[ CDATA [ </doc>
     * &a%b&#c?>\n <?pitarget ...?>\n <?pitarget \n ?>\n <?pitarget > ?>\n
     * <doc/>";
     *
     * FileOutputStream fos = new FileOutputStream(xmlFilePath1); byte[] buffer =
     * c.getBytes(); fos.write(buffer); fos.close();
     *
     * ws1.updateAndCommit(); ws2.updateAndCommit();
     *
     * assertTrue(dir, FileUtils.compareDir(dir1, dir2)); }
     */
    public void testInsertProcessingInstructionValidFormB()
        throws Exception {
        /*
         * XML Test Suite path xmlconf/oasis/p16pass2.xml
         */
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        Document doc = XmlUtil.load(xmlFilePath1);
        doc.appendChild(new ProcessingInstructionNode("pitarget", " '"));
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertProcessingInstructionvalidFormC()
        throws Exception {
        /*
         * XML Test Suite path xmlconf/oasis/p16pass3.xml
         */
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        Document doc = XmlUtil.load(xmlFilePath1);
        doc.appendChild(new ProcessingInstructionNode("pitarget", " \""));
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertCombinationCommentProcessingInstruction()
        throws Exception {
        /*
         * XML Test Suite path xmlconf/oasis/p27pass4.xml
         */
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\"?><?pi?>\n\n\t\n\n<!--comment-->\n<?pi?>\n\n \n\n<!--comment-->\n<?pi?><doc/>\n";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testDeleteProcessingInstruction() throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        Document doc = XmlUtil.load(xmlFilePath1);
        doc.appendChild(new ProcessingInstructionNode("pi", "data"));
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));
        doc = XmlUtil.load(xmlFilePath1);
        doc.removeChild(doc.getLastChild());
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));
    }

    public void testUpdateProcessingInstruction() throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        Document doc = XmlUtil.load(xmlFilePath1);
        doc.appendChild(new ProcessingInstructionNode("pi", "data"));
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));
        doc = XmlUtil.load(xmlFilePath1);
        doc.removeChild(doc.getLastChild());
        doc.save(xmlFilePath1, true);
        doc = XmlUtil.load(xmlFilePath1);
        doc.appendChild(new ProcessingInstructionNode("pi2", "data2"));
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertProcessingInstructionOnElementB()
        throws Exception {
        assertTrue(dir, FileUtils.compareDir(dir1, dir2));

        Document doc = new Document();
        doc.appendChild(new ElementNode("root"));
        doc.save(xmlFilePath1, true);
        doc = XmlUtil.load(xmlFilePath1);

        ElementNode elem1 = new ElementNode("a");
        ElementNode elem2 = new ElementNode("aa");
        ElementNode elem3 = new ElementNode("ab");
        ElementNode elem4 = new ElementNode("aba");
        elem3.appendChild(elem4);
        elem1.appendChild(elem2);
        elem1.appendChild(elem3);
        doc.getFirstChild().appendChild(elem1);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        Document doc2 = XmlUtil.load(xmlFilePath1);
        (doc2.getNode("0:0:0:1:0")).appendChild(new ProcessingInstructionNode("test1", " test2"));
        doc2.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    protected void tearDown() throws Exception {
        System.out.println(dir);
    }
}
