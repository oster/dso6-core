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

import fr.loria.ecoo.so6.xml.node.ElementNode;
import fr.loria.ecoo.so6.xml.util.XmlUtil;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;


public class SimpleXmlSyncTest2 extends TestCase {
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;

    public SimpleXmlSyncTest2(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();
        System.out.println(dir);

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2);
        ws1 = ws[0];
        ws2 = ws[1];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();
        ws1.setXmlAutoDetection(true);
        ws2.setXmlAutoDetection(true);
    }

    //////////////////////////////////////////
    public void testSynchronize() throws Exception {
        String xmlFilePath = dir1 + File.separator + "text.xml";
        String xmlFilePath2 = dir2 + File.separator + "text.xml";
        FileUtils.createXmlFile("root", xmlFilePath);
        XmlUtil.insertNode(xmlFilePath, "0:1", new ElementNode("a"));
        XmlUtil.insertNode(xmlFilePath, "0:1", new ElementNode("b"));
        XmlUtil.insertNode(xmlFilePath, "0:1", new ElementNode("c"));
        XmlUtil.insertNode(xmlFilePath, "0:2:0", new ElementNode("aa"));
        XmlUtil.insertNode(xmlFilePath, "0:2:1", new ElementNode("ab"));
        XmlUtil.insertNode(xmlFilePath, "0:2:2", new ElementNode("ac"));

        //
        XmlUtil.setAttribute(xmlFilePath, "0:2", "attr1", "value1");
        XmlUtil.setAttribute(xmlFilePath, "0:2", "attr2", "value2");
        XmlUtil.setAttribute(xmlFilePath, "0:2", "attr3", "value3");

        //		
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath, xmlFilePath2));

        //
        XmlUtil.insertNode(xmlFilePath, "0:1:0", new ElementNode("ba"));
        XmlUtil.insertNode(xmlFilePath, "0:1:1", new ElementNode("bb"));
        XmlUtil.insertNode(xmlFilePath, "0:1:2", new ElementNode("bc"));
        XmlUtil.insertNode(xmlFilePath, "0:2:0", new ElementNode("ca"));
        XmlUtil.insertNode(xmlFilePath, "0:2:1", new ElementNode("cb"));
        XmlUtil.insertNode(xmlFilePath, "0:2:2", new ElementNode("cc"));
        XmlUtil.setAttribute(xmlFilePath, "0:1", "attr1", "value1");
        XmlUtil.setAttribute(xmlFilePath, "0:1", "attr2", "value2");
        XmlUtil.setAttribute(xmlFilePath, "0:1", "attr3", "value3");
        XmlUtil.setAttribute(xmlFilePath, "0:2", "attr1", "value1");
        XmlUtil.setAttribute(xmlFilePath, "0:2", "attr2", "value2");
        XmlUtil.setAttribute(xmlFilePath, "0:2", "attr3", "value3");

        //
        XmlUtil.deleteAttribute(xmlFilePath, "0:0", "attr1");
        XmlUtil.deleteAttribute(xmlFilePath, "0:0", "attr2");

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2", FileUtils.compareXmlFile(xmlFilePath, xmlFilePath2));
    }

    //////////////////////////////////////////
}
