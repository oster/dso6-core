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

import fr.loria.ecoo.so6.xml.node.CDataNode;
import fr.loria.ecoo.so6.xml.node.CommentNode;
import fr.loria.ecoo.so6.xml.node.DocTypeNode;
import fr.loria.ecoo.so6.xml.node.Document;
import fr.loria.ecoo.so6.xml.node.ElementNode;
import fr.loria.ecoo.so6.xml.node.ProcessingInstructionNode;
import fr.loria.ecoo.so6.xml.node.TextNode;
import fr.loria.ecoo.so6.xml.util.XmlUtil;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;
import java.io.FileOutputStream;


public class InsertNodeTest extends TestCase {
    private String dir1;
    private String dir2;
    private String dir;
    private WsConnection ws1;
    private WsConnection ws2;

    public InsertNodeTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2, true);
        ws1 = ws[0];
        ws2 = ws[1];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();

        // init xml file
        String xmlFilePath = dir1 + "/text.xml";
        FileUtils.createXmlFile("root", xmlFilePath);
        XmlUtil.insertNode(xmlFilePath, "0:0", new ElementNode("a"));
        XmlUtil.insertNode(xmlFilePath, "0:1", new ElementNode("b"));
        XmlUtil.insertNode(xmlFilePath, "0:2", new ElementNode("c"));
        XmlUtil.insertNode(xmlFilePath, "0:0:0", new ElementNode("aa"));
        XmlUtil.insertNode(xmlFilePath, "0:0:1", new ElementNode("ab"));
        XmlUtil.insertNode(xmlFilePath, "0:0:2", new ElementNode("ac"));

        //
        XmlUtil.insertAttribute(xmlFilePath, "0:0", "attr1", "value1");
        XmlUtil.insertAttribute(xmlFilePath, "0:0", "attr2", "value2");
        XmlUtil.insertAttribute(xmlFilePath, "0:0", "attr3", "value3");

        //		
        ws1.updateAndCommit();
        ws2.updateAndCommit();
    }

    /**
     * Illustrate a conflict InsertNode - UpdateAttribute
     *
     * @throws Exception
     */
    public void testInsertNodeUpdateAttribute() throws Exception {
        String xmlFilePath1 = dir1 + "/text.xml";
        String xmlFilePath2 = dir2 + "/text.xml";
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        Document doc = new Document();
        ElementNode a = new ElementNode("a");
        ElementNode b = new ElementNode("b");
        ElementNode c = new ElementNode("c");
        c.setAttribute("attr", "val");
        a.appendChild(b);
        a.appendChild(c);
        doc.appendChild(a);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        TextNode t = new TextNode("text");
        XmlUtil.updateAttribute(xmlFilePath1, "0:0:1", "attr", "val2");
        XmlUtil.insertNode(xmlFilePath1, "0:0:1", t);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    /**
     * Elements
     */
    public void testInsertElementInsertElement() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        //
        String xmlFilePath1 = dir1 + "/text.xml";
        String xmlFilePath2 = dir2 + "/text.xml";
        XmlUtil.insertNode(xmlFilePath1, "0:1:0", new ElementNode("ba_1"));
        XmlUtil.insertNode(xmlFilePath2, "0:2:0", new ElementNode("ca_2"));

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertElementInsertElementAAfterB()
        throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        //
        String xmlFilePath1 = dir1 + "/text.xml";
        String xmlFilePath2 = dir2 + "/text.xml";
        XmlUtil.insertNode(xmlFilePath1, "0:1", new ElementNode("ba_1"));
        XmlUtil.insertNode(xmlFilePath2, "0:2:0", new ElementNode("ca_2"));

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertElementInsertElementBAfterA()
        throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        //
        String xmlFilePath1 = dir1 + "/text.xml";
        String xmlFilePath2 = dir2 + "/text.xml";
        XmlUtil.insertNode(xmlFilePath1, "0:1:0", new ElementNode("ba_1"));
        XmlUtil.insertNode(xmlFilePath2, "0:2", new ElementNode("ca_2"));

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertElementInsertElementBAfterAsamepos()
        throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        //
        String xmlFilePath1 = dir1 + "/text.xml";
        String xmlFilePath2 = dir2 + "/text.xml";
        XmlUtil.insertNode(xmlFilePath1, "0:1:0", new ElementNode("ba_1"));
        XmlUtil.insertNode(xmlFilePath2, "0:1", new ElementNode("ca_2"));

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertElementInsertElementAAfterBsameParent()
        throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        //
        String xmlFilePath1 = dir1 + "/text.xml";
        String xmlFilePath2 = dir2 + "/text.xml";
        XmlUtil.insertNode(xmlFilePath1, "0:1", new ElementNode("ba_1"));
        XmlUtil.insertNode(xmlFilePath2, "0:2", new ElementNode("ca_2"));

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertElementInsertElementWithPathConflict()
        throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        //
        String xmlFilePath1 = dir1 + "/text.xml";
        String xmlFilePath2 = dir2 + "/text.xml";
        XmlUtil.insertNode(xmlFilePath1, "0:1:0", new ElementNode("ba_1"));
        XmlUtil.insertNode(xmlFilePath2, "0:1:0", new ElementNode("ba_2"));

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertElementDeleteElement() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        //
        String xmlFilePath1 = dir1 + "/text.xml";
        String xmlFilePath2 = dir2 + "/text.xml";
        XmlUtil.insertNode(xmlFilePath1, "0:1:0", new ElementNode("ba_1"));
        XmlUtil.deleteNode(xmlFilePath2, "0:2");

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertElementDeleteElementWithConflict()
        throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        //
        String xmlFilePath1 = dir1 + "/text.xml";
        String xmlFilePath2 = dir2 + "/text.xml";
        XmlUtil.insertNode(xmlFilePath1, "0:1:0", new ElementNode("ba_1"));
        XmlUtil.deleteNode(xmlFilePath2, "0:1");

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    /**
     * Text Node
     */
    public void testUpdateText() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        //
        String xmlFilePath1 = dir1 + "/text.xml";
        String xmlFilePath2 = dir2 + "/text.xml";
        XmlUtil.insertNode(xmlFilePath1, "0:1:0", new TextNode("Du text init"));
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        //		
        XmlUtil.updateTextNode(xmlFilePath1, "0:1:0", "text1");
        XmlUtil.updateTextNode(xmlFilePath2, "0:1:0", "text2");

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        //
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    // Insert ATTRIBUTE Node
    public void testInsertAttributeNode() throws Exception {
        String xmlFilePath1 = dir1 + "/text.xml";
        XmlUtil.insertAttribute(xmlFilePath1, "0:0", "testName", "testVal");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("THe dir1 <> dir2)", FileUtils.compareDir(dir1, dir2));
    }

    // Insert CDATA Node
    public void testInsertCDataSectionNode() throws Exception {
        String xmlFilePath1 = dir1 + "/text.xml";
        XmlUtil.insertNode(xmlFilePath1, "0:0", new CDataNode("test"));
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertCDataSectionNodeWithCR() throws Exception {
        /*
         * path xmlconf/xmltest/valid-sa/116.xml
         */
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        String c = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE doc [\n<!ELEMENT doc (#PCDATA)>\n]>\n<doc><![CDATA[\n]]></doc>";
        File f = new File(dir1 + "/text.xml");
        FileOutputStream fos = new FileOutputStream(f);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        String delta = dir + "/delta.xml";
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(dir1 + "/text.xml", dir2 + "/text.xml"));
    }

    public void testInsertTwoConcurrentCDataSectionNodes()
        throws Exception {
        String xmlFilePath1 = dir1 + "/text.xml";
        String xmlFilePath2 = dir2 + "/text.xml";
        XmlUtil.insertNode(xmlFilePath1, "0:0", new CDataNode("test1"));
        XmlUtil.insertNode(xmlFilePath2, "0:0", new CDataNode("test2"));
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    // Insert COMMENT Node
    public void testInsertCommentNode() throws Exception {
        String xmlFilePath1 = dir1 + "/text.xml";
        XmlUtil.insertNode(xmlFilePath1, "0:0", new CommentNode("commentnode1"));
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertTwoConcurrentCommentNodes() throws Exception {
        String xmlFilePath1 = dir1 + "/text.xml";
        String xmlFilePath2 = dir2 + "/text.xml";
        XmlUtil.insertNode(xmlFilePath1, "0:0", new CommentNode("commentnode1"));
        XmlUtil.insertNode(xmlFilePath2, "0:0", new CommentNode("commentnode2"));
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    // Insert DOCUMENT FRAGMENT Node
    public void testInsertDocumentFragmentNode() throws Exception {
        String xmlFilePath1 = dir1 + "/text.xml";

        //XmlUtil.insertNode(xmlFilePath1, "0:0", Node.DOCUMENT_FRAGMENT_NODE,
        // "test");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertTwoConcurrentDocumentFragmentNodes()
        throws Exception {
        String xmlFilePath1 = dir1 + "/text.xml";
        String xmlFilePath2 = dir2 + "/text.xml";

        //XmlUtil.insertNode(xmlFilePath1, "0:0", Node.DOCUMENT_FRAGMENT_NODE,
        // "test1");
        //XmlUtil.insertNode(xmlFilePath2, "0:0", Node.DOCUMENT_FRAGMENT_NODE,
        // "test2");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    // Insert DOCUMENT TYPE Node
    public void testInsertDocumentTypeNode() throws Exception {
        String xmlFilePath1 = dir1 + "/text.xml";
        XmlUtil.insertNode(xmlFilePath1, "0:0", new DocTypeNode("test"));
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    // Insert ENTITY Node
    public void testInsertEntityNode() throws Exception {
        String xmlFilePath1 = dir1 + "/text.xml";

        //XmlUtil.insertNode(xmlFilePath1, "0:0", Node.ENTITY_NODE, "test");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    // Insert NOTATION Node
    public void testInsertNotationNode() throws Exception {
        String xmlFilePath1 = dir1 + "/text.xml";

        //XmlUtil.insertNode(xmlFilePath1, "0:0", Node.NOTATION_NODE, "test");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    // Insert PROCESSING INSTRUCTION Node
    public void testInsertProcessingInstructionNode() throws Exception {
        String xmlFilePath1 = dir1 + "/text.xml";
        XmlUtil.insertNode(xmlFilePath1, "0:0", new ProcessingInstructionNode("target", "content"));
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertCommentBeforeRoot() throws Exception {
        String xmlFilePath1 = dir1 + "/text.xml";
        XmlUtil.insertNode(xmlFilePath1, "0:0", new CommentNode("comment0"));
        XmlUtil.insertNode(xmlFilePath1, "0:0", new CommentNode("comment1"));
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    public void testInsertCommentAfterRoot() throws Exception {
        String xmlFilePath1 = dir1 + "/text.xml";
        XmlUtil.insertNode(xmlFilePath1, "0:1", new CommentNode("test After"));
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("THe dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    protected void tearDown() {
        System.out.println(dir);
    }
}
