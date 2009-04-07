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

import fr.loria.ecoo.so6.xml.node.CommentNode;
import fr.loria.ecoo.so6.xml.node.DocTypeNode;
import fr.loria.ecoo.so6.xml.node.Document;
import fr.loria.ecoo.so6.xml.node.ElementNode;
import fr.loria.ecoo.so6.xml.node.TextNode;
import fr.loria.ecoo.so6.xml.util.XmlUtil;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;
import java.io.FileOutputStream;


/**
 * @author tani
 */
public class XmlDeclarationTest extends TestCase {
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;
    private String xmlFilePath1;
    private String xmlFilePath2;

    /**
     * Constructor for XmlDeclarationTest.
     *
     * @param arg0
     */
    public XmlDeclarationTest(String arg0) {
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
    }

    /*
     * public void testRapport() throws Exception {
     *
     * Document d1 =
     * XmlUtil.load("c:/xml/bug/diff-bug/ls-user-manual-REFCOPY.xml");
     * d1.save(xmlFilePath1, false); //Document d2 = XmlUtil.load("c:/ex2.xml");
     * //d2.save(xmlFilePath2,false);
     *
     * ws1.updateAndCommit(); ws2.updateAndCommit(); //ws1.updateAndCommit();
     *
     * assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1,
     * xmlFilePath2));
     *
     * Document d2 = XmlUtil.load("c:/xml/bug/diff-bug/ls-user-manual.xml");
     * d2.save(xmlFilePath1, false); ws1.updateAndCommit();
     * ws2.updateAndCommit();
     *
     * assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1,
     * xmlFilePath2)); }
     */

    // to remove
    public void testTEMPO() throws Exception {
        String c = "<?xml version=\"1.0\"?><root/>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        Document doc = XmlUtil.load(xmlFilePath1);
        doc.appendChild(new TextNode("?&gt;&lt;a&gt;&lt;!--test1--&gt;&lt;/a&gt;."));
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    //
    public void testWithStandaloneDeclaration() throws Exception {
        String c = "<?xml version=\"1.0\" standalone=\"yes\"?><root/>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        Document doc = XmlUtil.load(xmlFilePath1);
        doc.appendChild(new TextNode("abcdefghijkl"));
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testWithStandaloneAndEncodingDeclaration()
        throws Exception {
        String c = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><root/>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        Document doc = XmlUtil.load(xmlFilePath1);
        doc.appendChild(new TextNode("ab��@��"));
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testStandaloneDeclarationWithWitheSpace()
        throws Exception {
        /*
         * extract from XML test suite path xmlconf/ibm/valid/P32/ibm32v04.xml
         */
        Document doc = new Document();
        doc.setVersion("1.0");
        doc.setStandalone("no");

        String dtContent = "animal SYSTEM \"ibm32v04.dtd\"";
        DocTypeNode dt = new DocTypeNode(dtContent);
        ElementNode animal = new ElementNode("animal");
        TextNode text1 = new TextNode("This is a \n\t");
        ElementNode a = new ElementNode("a");
        animal.appendChild(text1);
        animal.appendChild(a);

        TextNode text2 = new TextNode("   \n\nyelow tiger");
        animal.appendChild(text2);

        CommentNode comment = new CommentNode(" Tests VC: Standalone Document Declaration with whitespace in mixed content ");
        doc.appendChild(dt);
        doc.appendChild(animal);
        doc.appendChild(comment);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    public void testStandaloneDeclarationWithEntityDeclared()
        throws Exception {
        /*
         * Extract from the XML test suite path
         * xmlconf/ibm/valid/P69/ibm69v01.xml
         */
        String c = "<?xml version=\"1.0\" standalone=\"no\"?><root/>";
        FileOutputStream fos = new FileOutputStream(xmlFilePath1);
        byte[] buffer = c.getBytes();
        fos.write(buffer);
        fos.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(dir, FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));

        /*
         * <?xml version="1.0" standalone="no" ?> <!DOCTYPE root SYSTEM
         * "ibm69v01.dtd" [ <!ELEMENT root (#PCDATA|a)* > <!ENTITY % pe1 " <!--
         * comment in PE -->"> %pe1; ]> <root> pcdata content <a attr1="xyz"/>
         * </root> <!--* a valid test for P69 VC:Entity Declared *-->
         */
        String d = "<?xml version=\"1.0\" standalone=\"no\"?>\n<!DOCTYPE root SYSTEM \"ibm69v01.dtd\" [\n<!ELEMENT root (#PCDATA|a)* >\n<!ENTITY % pe1 \"<!-- comment in PE -->\">\n]>\n<root>\npcdata content<a attr1=\"xyz\"/>\n</root>\n<!--* a valid test for P69 VC:Entity Declared *-->\n";
        FileOutputStream fos2 = new FileOutputStream(xmlFilePath1);
        byte[] buffer2 = d.getBytes();
        fos2.write(buffer2);
        fos2.close();
        ws1.updateAndCommit();
        ws2.updateAndCommit();
    }

    public void testExternalAttListWithStandaloneDecl()
        throws Exception {
        /*
         * Extract from the XML test suite path xmlconf/eduni/errata-2e/E36.xml
         */
        Document doc = new Document();
        doc.setVersion("1.0");
        doc.setStandalone("yes");

        DocTypeNode dt = new DocTypeNode("foo SYSTEM \"E36.dtd\"");
        ElementNode foo = new ElementNode("foo");
        foo.setAttribute("bar", "123\n456");
        doc.appendChild(dt);
        doc.appendChild(foo);
        doc.save(xmlFilePath1, true);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue("The file1 <> file2", FileUtils.compareXmlFile(xmlFilePath1, xmlFilePath2));
    }

    protected void tearDown() throws Exception {
        System.out.println(dir);
    }
}
