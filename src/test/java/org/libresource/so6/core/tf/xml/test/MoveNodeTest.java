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
import fr.loria.ecoo.so6.xml.node.ElementNode;
import fr.loria.ecoo.so6.xml.node.TextNode;
import fr.loria.ecoo.so6.xml.util.XmlUtil;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;


/**
 * @author tani
 */
public class MoveNodeTest extends TestCase {
    private String dir;
    private String dir1;
    private String dir2;
    private String xmlFilePath1;
    private String xmlFilePath2;
    private WsConnection ws1;
    private WsConnection ws2;

    public MoveNodeTest(String arg0) {
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

        // init xml file
        Document test = new Document();
        ElementNode root = new ElementNode("root");
        ElementNode a = new ElementNode("a");
        ElementNode b = new ElementNode("b");
        ElementNode c = new ElementNode("c");
        a.appendChild(b);
        a.appendChild(c);
        root.appendChild(a);
        test.appendChild(root);
        test.save(xmlFilePath1, false);

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
    }

    public void testInsertNodeAndMoveNode() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        Document test = new Document();
        ElementNode root = new ElementNode("root");
        ElementNode a = new ElementNode("a");
        ElementNode b = new ElementNode("b");
        ElementNode c = new ElementNode("c");
        ElementNode d = new ElementNode("d");
        a.appendChild(c);
        d.appendChild(b);
        root.appendChild(a);
        root.appendChild(d);
        test.appendChild(root);
        test.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertSubtreeAndMoveNode() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        Document test = new Document();

        //<root><a><c/></a><d><e/><b/></d></root>
        ElementNode root = new ElementNode("root");
        ElementNode a = new ElementNode("a");
        ElementNode b = new ElementNode("b");
        ElementNode c = new ElementNode("c");
        ElementNode d = new ElementNode("d");
        ElementNode e = new ElementNode("e");
        a.appendChild(c);
        d.appendChild(e);
        d.appendChild(b);
        root.appendChild(a);
        root.appendChild(d);
        test.appendChild(root);
        test.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertNodeAndMoveSubtree() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        // Modify file to get a subtree
        XmlUtil.load(xmlFilePath1);

        TextNode t = new TextNode("text");
        XmlUtil.insertNode(xmlFilePath1, "0:0:0:0:0", t);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        //
        // Construct the document for test
        // <root><a><c/></a><d><b>text</b></d></root>
        Document test = new Document();
        ElementNode root = new ElementNode("root");
        ElementNode a = new ElementNode("a");
        ElementNode b = new ElementNode("b");
        ElementNode c = new ElementNode("c");
        ElementNode d = new ElementNode("d");
        a.appendChild(c);
        b.appendChild(t);
        d.appendChild(b);
        root.appendChild(a);
        root.appendChild(d);
        test.appendChild(root);
        test.save(xmlFilePath1, false);

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testInsertSubtreeAndMoveSubtree() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        // Modify file to get a subtree
        XmlUtil.load(xmlFilePath1);

        TextNode t = new TextNode("text");
        XmlUtil.insertNode(xmlFilePath1, "0:0:0:0:0", t);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        //
        // Construct the document for test
        // <?xml version=\"1.0\"?>" +
        // "<root><a><c/></a><d><e/><b>text</b></d></root>
        Document test = new Document();
        ElementNode root = new ElementNode("root");
        ElementNode a = new ElementNode("a");
        ElementNode b = new ElementNode("b");
        ElementNode c = new ElementNode("c");
        ElementNode d = new ElementNode("d");
        ElementNode e = new ElementNode("e");
        b.appendChild(t);
        a.appendChild(c);
        d.appendChild(e);
        d.appendChild(b);
        root.appendChild(a);
        root.appendChild(d);
        test.appendChild(root);
        test.save(xmlFilePath1, false);

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testWeakMove() throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        // <?xml version=\"1.0\"?>" + "<root><b/><a><c/></a></root>
        Document test = new Document();
        ElementNode root = new ElementNode("root");
        ElementNode a = new ElementNode("a");
        ElementNode b = new ElementNode("b");
        ElementNode c = new ElementNode("c");
        a.appendChild(c);
        root.appendChild(b);
        root.appendChild(a);
        test.appendChild(root);
        test.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testWeakMoveSubtreeWithTheSameWeight()
        throws Exception {
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        // Modify file
        // <root><a><c/></a><b><d/></b></root>
        XmlUtil.deleteNode(xmlFilePath1, "0:0:0:0");

        ElementNode b = new ElementNode("b");
        ElementNode d = new ElementNode("d");
        XmlUtil.insertNode(xmlFilePath1, "0:0:1", b);
        XmlUtil.insertNode(xmlFilePath1, "0:0:1:0", d);

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        // Construct the document for test
        // <?xml version=\"1.0\"?>" + "<root><b><d/></b><a><c/></a></root>
        Document test = new Document();
        ElementNode root = new ElementNode("root");
        ElementNode a = new ElementNode("a");
        ElementNode c = new ElementNode("c");
        a.appendChild(c);
        b.appendChild(d);
        root.appendChild(b);
        root.appendChild(a);
        test.appendChild(root);
        test.save(xmlFilePath1, false);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    protected void tearDown() throws Exception {
        System.out.println(dir);
    }
}
