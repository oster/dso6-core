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
package org.libresource.so6.core.test.scenari;

import fr.loria.ecoo.so6.xml.xydiff.DeleteAttribute;
import fr.loria.ecoo.so6.xml.xydiff.DeleteNode;
import fr.loria.ecoo.so6.xml.xydiff.InsertAttribute;
import fr.loria.ecoo.so6.xml.xydiff.InsertNode;
import fr.loria.ecoo.so6.xml.xydiff.UpdateAttribute;
import fr.loria.ecoo.so6.xml.xydiff.XMLCommand;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;


/**
 * @author tani
 */
public class XmlCommandOrderTest extends TestCase {
    private String dir;
    private String dir1;
    private String xmlFilePath;
    private WsConnection ws1;

    public XmlCommandOrderTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 1, true);
        ws1 = ws[0];
        dir1 = ws1.getPath();
        xmlFilePath = dir1 + File.separator + "test.xml";
        FileUtils.createXmlFile("root", xmlFilePath);
        ws1.updateAndCommit();
    }

    protected void tearDown() throws Exception {
        System.out.println(dir);
    }

    // TOOLS Functions
    private XMLCommand insertAttribute(String nodePath) {
        return new InsertAttribute(nodePath, null, null);
    }

    private XMLCommand deleteAttribute(String nodePath) {
        return new DeleteAttribute(nodePath, null);
    }

    private XMLCommand updateAttribute(String nodePath) {
        return new UpdateAttribute(nodePath, null, null, null);
    }

    private XMLCommand insertNode(String nodePath) {
        return new InsertNode(nodePath, null);
    }

    private XMLCommand deleteNode(String nodePath) {
        return new DeleteNode(nodePath, null);
    }

    //
    // TEST Functions
    public void testInsertNodeOrder() throws Exception {
        assertTrue(insertNode("0").compareTo(insertNode("0:1")) == -1);
        assertTrue(insertNode("0:0").compareTo(insertNode("0:1")) == -1);
        assertTrue(insertNode("0:0").compareTo(insertNode("0:1:0")) == -1);
        assertTrue(insertNode("0:1").compareTo(insertNode("0:0")) == 1);
        assertTrue(insertNode("0:1").compareTo(insertNode("0:1")) == 0);
        assertTrue(insertNode("0:1:0").compareTo(insertNode("0:1")) == 1);
        assertTrue(insertNode("0:1:0").compareTo(insertNode("0:0")) == 1);
    }

    public void testDeleteNodeOrder() throws Exception {
        assertTrue(deleteNode("0").compareTo(deleteNode("0:1")) == 1);
        assertTrue(deleteNode("0:0").compareTo(deleteNode("0:1")) == 1);
        assertTrue(deleteNode("0:0").compareTo(deleteNode("0:1:0")) == 1);
        assertTrue(deleteNode("0:1").compareTo(deleteNode("0:0")) == -1);
        assertTrue(deleteNode("0:1").compareTo(deleteNode("0:1")) == 0);
        assertTrue(deleteNode("0:1:0").compareTo(deleteNode("0:1")) == -1);
        assertTrue(deleteNode("0:1:0").compareTo(deleteNode("0:0")) == -1);
    }

    public void testInsertAttributeOrder() throws Exception {
        assertTrue(insertAttribute("0").compareTo(insertAttribute("0:1")) == -1);
        assertTrue(insertAttribute("0:0").compareTo(insertAttribute("0:1")) == -1);
        assertTrue(insertAttribute("0:0").compareTo(insertAttribute("0:1:0")) == -1);
        assertTrue(insertAttribute("0:1").compareTo(insertAttribute("0:0")) == 1);
        assertTrue(insertAttribute("0:1").compareTo(insertAttribute("0:1")) == 0);
        assertTrue(insertAttribute("0:1:0").compareTo(insertAttribute("0:1")) == 1);
        assertTrue(insertAttribute("0:1:0").compareTo(insertAttribute("0:0")) == 1);
    }

    public void testDeleteAttributeOrder() throws Exception {
        assertTrue(deleteAttribute("0").compareTo(deleteAttribute("0:1")) == -1);
        assertTrue(deleteAttribute("0:0").compareTo(deleteAttribute("0:1")) == -1);
        assertTrue(deleteAttribute("0:0").compareTo(deleteAttribute("0:1:0")) == -1);
        assertTrue(deleteAttribute("0:1").compareTo(deleteAttribute("0:0")) == 1);
        assertTrue(deleteAttribute("0:1").compareTo(deleteAttribute("0:1")) == 0);
        assertTrue(deleteAttribute("0:1:0").compareTo(deleteAttribute("0:1")) == 1);
        assertTrue(deleteAttribute("0:1:0").compareTo(deleteAttribute("0:0")) == 1);
    }

    public void testUpdateAttributeOrder() throws Exception {
        assertTrue(updateAttribute("0").compareTo(updateAttribute("0:1")) == -1);
        assertTrue(updateAttribute("0:0").compareTo(updateAttribute("0:1")) == -1);
        assertTrue(updateAttribute("0:0").compareTo(updateAttribute("0:1:0")) == -1);
        assertTrue(updateAttribute("0:1").compareTo(updateAttribute("0:0")) == 1);
        assertTrue(updateAttribute("0:1").compareTo(updateAttribute("0:1")) == 0);
        assertTrue(updateAttribute("0:1:0").compareTo(updateAttribute("0:1")) == 1);
        assertTrue(updateAttribute("0:1:0").compareTo(updateAttribute("0:0")) == 1);
    }

    public void testDeleteAttributeVSOtherOrder() throws Exception {
        assertTrue(deleteAttribute("0").compareTo(deleteAttribute("0")) == 0);
        assertTrue(deleteAttribute("0").compareTo(updateAttribute("0")) == -1);
        assertTrue(deleteAttribute("0").compareTo(insertAttribute("0")) == 1);
        assertTrue(deleteAttribute("0").compareTo(deleteNode("0")) == 1);
        assertTrue(deleteAttribute("0").compareTo(insertNode("0")) == 1);
    }

    public void testInsertAttributeVSOtherOrder() throws Exception {
        assertTrue(insertAttribute("0").compareTo(deleteAttribute("0")) == -1);
        assertTrue(insertAttribute("0").compareTo(updateAttribute("0")) == -1);
        assertTrue(insertAttribute("0").compareTo(insertAttribute("0")) == 0);
        assertTrue(insertAttribute("0").compareTo(deleteNode("0")) == 1);
        assertTrue(insertAttribute("0").compareTo(insertNode("0")) == 1);
    }

    public void testDeleteNodeVSOtherOrder() throws Exception {
        assertTrue(deleteNode("0").compareTo(deleteAttribute("0")) == -1);
        assertTrue(deleteNode("0").compareTo(updateAttribute("0")) == -1);
        assertTrue(deleteNode("0").compareTo(insertAttribute("0")) == -1);
        assertTrue(deleteNode("0").compareTo(deleteNode("0")) == 0);
        assertTrue(deleteNode("0").compareTo(insertNode("0")) == -1);
    }

    public void testInsertNodeVSOtherOrder() throws Exception {
        assertTrue(insertNode("0").compareTo(deleteAttribute("0")) == -1);
        assertTrue(insertNode("0").compareTo(updateAttribute("0")) == -1);
        assertTrue(insertNode("0").compareTo(insertAttribute("0")) == -1);
        assertTrue(insertNode("0").compareTo(deleteNode("0")) == 1);
        assertTrue(insertNode("0").compareTo(insertNode("0")) == 0);
    }

    //
}
