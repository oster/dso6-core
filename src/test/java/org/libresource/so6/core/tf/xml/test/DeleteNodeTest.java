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

import fr.loria.ecoo.so6.xml.node.ElementNode;
import fr.loria.ecoo.so6.xml.util.XmlUtil;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;


public class DeleteNodeTest extends TestCase {
    private String dir1;
    private String dir2;
    private String dir;
    private WsConnection ws1;
    private WsConnection ws2;
    private String xmlFilePath1;
    private String xmlFilePath2;

    public DeleteNodeTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2, true);
        ws1 = ws[0];
        ws2 = ws[1];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();
        xmlFilePath1 = dir1 + "/text.xml";
        xmlFilePath2 = dir2 + "/text.xml";

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
     * Elements
     */
    public void tearDown() {
        System.out.println(dir);
    }

    public void testDeleteElementInsertElement() throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        //
        XmlUtil.deleteNode(xmlFilePath1, "0:2");
        XmlUtil.insertNode(xmlFilePath2, "0:1:0", new ElementNode("ba_1"));

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }

    public void testDeleteElementInsertElementWithConflict()
        throws Exception {
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));

        //
        XmlUtil.insertNode(xmlFilePath2, "0:1:0", new ElementNode("ba_1"));
        XmlUtil.deleteNode(xmlFilePath1, "0:1");

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }
}
