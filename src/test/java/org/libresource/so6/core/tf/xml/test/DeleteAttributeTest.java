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
import fr.loria.ecoo.so6.xml.util.XmlUtil;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;


/**
 * @author tani
 */
public class DeleteAttributeTest extends TestCase {
    private String dir1;
    private String dir2;
    private String dir;
    private WsConnection ws1;
    private WsConnection ws2;
    private String xmlFilePath1;
    private String xmlFilePath2;

    public DeleteAttributeTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2, true);
        ws1 = ws[0];
        ws2 = ws[1];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();
        xmlFilePath1 = dir1 + File.separator + "text.xml";
        xmlFilePath2 = dir2 + File.separator + "text.xml";

        Document doc = new Document();
        ElementNode root = new ElementNode("root");
        ElementNode a = new ElementNode("a");
        ElementNode b = new ElementNode("b");
        ElementNode c = new ElementNode("c");
        a.setAttribute("name1", "val1");
        root.appendChild(a);
        root.appendChild(b);
        root.appendChild(c);
        doc.appendChild(root);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
    }

    public void testDeleteAttributeOnElement() throws Exception {
        assertTrue("Dir1 <> Dir2", FileUtils.compareDir(dir1, dir2));

        Document doc = XmlUtil.load(xmlFilePath1);
        ElementNode n = (ElementNode) doc.getNode("0:0:0");
        n.removeAttribute("name1");
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("Dir1 <> Dir2", FileUtils.compareDir(dir1, dir2));
        assertTrue("Dir1 <> Dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testDeleteAttributeOnDocument() throws Exception {
        assertTrue("Dir1 <> Dir2", FileUtils.compareDir(dir1, dir2));
        XmlUtil.deleteAttribute(xmlFilePath1, "0", "encoding");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("Dir1 <> Dir2", FileUtils.compareDir(dir1, dir2));
        assertTrue("Dir1 <> Dir2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testDeleteAttributeWithConflict() throws Exception {
        assertTrue("Dir1 <> Dir2", FileUtils.compareDir(dir1, dir2));
        XmlUtil.updateAttribute(xmlFilePath1, "0:0:0", "name1", "newVal1");
        XmlUtil.deleteAttribute(xmlFilePath2, "0:0:0", "name1");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();
        assertTrue("Dir1 <> Dir2", FileUtils.compareDir(dir1, dir2));
        assertTrue("File1 <> File2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    protected void tearDown() throws Exception {
        System.out.println(dir);
    }
}
