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
package org.libresource.so6.core.tf.xml.test.dom4j;

import fr.loria.ecoo.so6.xml.node.CommentNode;
import fr.loria.ecoo.so6.xml.node.ElementNode;
import fr.loria.ecoo.so6.xml.node.TextNode;
import fr.loria.ecoo.so6.xml.util.XmlUtil;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;


/**
 * @author tani
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TestInsertNode extends TestCase {
    /**
     * Constructor for TestINsertNode.
     *
     * @param arg0
     */
    private String dir1;
    private String dir2;
    private String dir;
    private WsConnection ws1;
    private WsConnection ws2;

    public TestInsertNode(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        dir = System.getProperty("java.io.tmpdir") + java.io.File.separator + "lstest" + System.currentTimeMillis();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2);
        ws1 = ws[0];
        ws2 = ws[1];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();

        // init xml file
        String xmlFilePath = dir1 + "/text1.xml";
        FileUtils.createXmlFile("foot", xmlFilePath);
        XmlUtil.insertNode(xmlFilePath, "1", new CommentNode("test1"));
        XmlUtil.insertNode(xmlFilePath, "2", new CommentNode("test1"));
        XmlUtil.insertNode(xmlFilePath, "3", new CommentNode("test1"));
        XmlUtil.insertNode(xmlFilePath, "0:0", new ElementNode("test1"));
        XmlUtil.insertNode(xmlFilePath, "0:1", new ElementNode("test2"));
        XmlUtil.insertNode(xmlFilePath, "0:2", new ElementNode("test3"));
        XmlUtil.insertNode(xmlFilePath, "0:3", new ElementNode("test4"));
        XmlUtil.insertNode(xmlFilePath, "0:4", new ElementNode("test5"));
        XmlUtil.insertNode(xmlFilePath, "0:5", new ElementNode("test6"));
        XmlUtil.insertNode(xmlFilePath, "0:6", new ElementNode("test7"));
        XmlUtil.insertNode(xmlFilePath, "0:6:0", new TextNode("text text 01234"));
        XmlUtil.insertNode(xmlFilePath, "0:6:1", new ElementNode("test61"));
        XmlUtil.insertNode(xmlFilePath, "0:6:2", new ElementNode("test62"));
        XmlUtil.insertNode(xmlFilePath, "0:6:2:0", new ElementNode("test620"));
        XmlUtil.setAttribute(xmlFilePath, "0:6", "name1", "311", true);
        XmlUtil.setAttribute(xmlFilePath, "0:6", "name2", "311", true);
        XmlUtil.setAttribute(xmlFilePath, "0:6", "name3", "311", true);
        XmlUtil.setAttribute(xmlFilePath, "0:6", "name4", "311", true);
        XmlUtil.setAttribute(xmlFilePath, "0:6", "name1", "test");

        /*
         * System.out.println(last.getText());
         * System.out.println(DOMNodeHelper.getNodeValue(last));
         *
         * System.out.println(":" + first.asXML()); System.out.println(":" +
         * last.asXML());
         */
        /*
         *
         * String delta = " <unit_delta>\n <t from=\"path1\" to=\"path2\"
         * fromXidMap=\"(1-8|9)\" toXidMap=\"(1-7|9)\">\n <au xid=\"4\"
         * a=\"attr1\" ov=\"value1\" nv=\"val1 update\" path=\"0:0\"/>\n </t>\n
         * </unit_delta>";
         *
         * byte[] buffer = delta.getBytes(); SAXReader r =
         * XmlUtil2.getSAXReader(); Document doc = r.read(new
         * ByteArrayInputStream(buffer)); Node node =
         * XmlUtil2.getFirstChild(XmlUtil2.getFirstChild(doc));
         * System.out.println(node.toString());
         *
         * DOMDocument init = new DOMDocument(); Element elem =
         * (Element)XmlUtil2.createNode(init, Node.ELEMENT_NODE, "unit_delta");
         * Element childElem = (Element)XmlUtil2.createNode(init,
         * Node.ELEMENT_NODE, "unit_delta");
         * XmlUtil2.appendChild(elem,childElem);
         * System.out.println(elem.asXML());
         */
        if ((1 < 1) || (1 > 2)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }

    /**
     * Elements
     */
    public void tearDown() {
        System.out.println(dir);
    }

    public void testInsertElementInsertElement() throws Exception {
        //
        //assertTrue("The dir1 <> dir2", FileUtils.compareDir(dir1, dir2));
    }
}
