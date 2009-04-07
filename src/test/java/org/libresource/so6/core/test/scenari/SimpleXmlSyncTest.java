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

import fr.loria.ecoo.so6.xml.node.Document;
import fr.loria.ecoo.so6.xml.node.ElementNode;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;


public class SimpleXmlSyncTest extends TestCase {
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;

    public SimpleXmlSyncTest(String name) {
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
        Document doc = new Document();
        ElementNode root = new ElementNode("root");
        doc.appendChild(root);

        ElementNode a = new ElementNode("a");
        root.appendChild(a);

        ElementNode b = new ElementNode("b");
        root.appendChild(b);

        ElementNode c = new ElementNode("c");
        root.appendChild(c);
        a.appendChild(new ElementNode("aa"));
        a.appendChild(new ElementNode("ab"));
        a.appendChild(new ElementNode("ac"));
        a.setAttribute("attr1", "value1");
        a.setAttribute("attr2", "value2");
        a.setAttribute("attr3", "value3");
        doc.save(xmlFilePath, true);

        //		
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The dir1 <> dir2 (txt)", FileUtils.compareTxtFile(xmlFilePath, xmlFilePath2));
        assertTrue("The dir1 <> dir2 (xml)", FileUtils.compareXmlFile(xmlFilePath, xmlFilePath2));

        //
        b.appendChild(new ElementNode("ba"));
        b.appendChild(new ElementNode("bb"));
        b.appendChild(new ElementNode("bc"));
        b.setAttribute("attr1", "value1");
        b.setAttribute("attr2", "value2");
        b.setAttribute("attr3", "value3");
        c.appendChild(new ElementNode("ca"));
        c.appendChild(new ElementNode("cb"));
        c.appendChild(new ElementNode("cc"));
        c.setAttribute("attr1", "value1");
        c.setAttribute("attr2", "value2");
        c.setAttribute("attr3", "value3");

        //
        a.removeAttribute("attr1");
        a.removeAttribute("attr2");

        //
        doc.save(xmlFilePath, true);

        //
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        System.out.println(dir);
        assertTrue("The dir1 <> dir2 (xml)", FileUtils.compareXmlFile(xmlFilePath, xmlFilePath2));
    }

    protected void tearDown() throws Exception {
        System.out.println(dir);
    }

    //////////////////////////////////////////
}
