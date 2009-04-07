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
/*
 * Created on 17 f�vr. 2004
 */
package org.libresource.so6.core.component.test;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;
import java.io.FileInputStream;

import java.util.Properties;


/**
 * @author molli
 */
public class PatchFileTest extends TestCase {
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;

    public PatchFileTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(PatchFileTest.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2);
        ws1 = ws[0];
        ws2 = ws[1];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();
    }

    public void testCommit() throws Exception {
        FileUtils.createTxtFile(dir1, "a", "toto");
        FileUtils.createBinFile(dir1, "b", "yop");
        ws1.commit("yop");
        assertTrue(ws1.getRefCopy().exists("a"));
        assertTrue(ws1.getRefCopy().exists("b"));
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "a", ws1.getRefCopy().getPath("a")));
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "b", ws1.getRefCopy().getPath("b")));

        //		Properties p1 = new Properties();
        //		p1.load(new FileInputStream(ws1.getDBType().getPath()));
        //		Properties p2 = new Properties();
        //		p2.load(new FileInputStream(ws1.getRefCopy().getDBType().getPath()));
        //		assertTrue(p1.equals(p2));
        //assertTrue(FileUtils.compareBinFile(ws1.getDBType().getPath(),
        // ws1.getRefCopy().getDBType().getPath()));
        assertTrue(ws1.getClient().getLastTicket() == ws1.getNs());
        assertTrue(ws1.getAppliedPatch().list().length == 1);
    }

    public void testCommit2() throws Exception {
        FileUtils.createTxtFile(dir1, "a", "téàtoÛ");
        FileUtils.createBinFile(dir1, "b", "yop&é");
        ws1.commit("yop");
        assertTrue(ws1.getRefCopy().exists("a"));
        assertTrue(ws1.getRefCopy().exists("b"));
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "a", ws1.getRefCopy().getPath("a")));
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "b", ws1.getRefCopy().getPath("b")));

        //		Properties p1 = new Properties();
        //		p1.load(new FileInputStream(ws1.getDBType().getPath()));
        //		Properties p2 = new Properties();
        //		p2.load(new FileInputStream(ws1.getRefCopy().getDBType().getPath()));
        //		assertTrue(p1.equals(p2));
        //assertTrue(FileUtils.compareBinFile(ws1.getDBType().getPath(),
        // ws1.getRefCopy().getDBType().getPath()));
        assertTrue(ws1.getClient().getLastTicket() == ws1.getNs());
        assertTrue(ws1.getAppliedPatch().list().length == 1);
    }

    /**
     * @throws Exception
     */
    public void testCommit3() throws Exception {
        FileUtils.createTxtFile(dir1, "a", "àéè\n\ndjshfkhsdkfjhsfhdsfgjsfh\n");
        FileUtils.createBinFile(dir1, "b", "yàp");
        ws1.commit("yop");
        assertTrue(ws1.getRefCopy().exists("a"));
        assertTrue(ws1.getRefCopy().exists("b"));
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "a", ws1.getRefCopy().getPath("a")));
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "b", ws1.getRefCopy().getPath("b")));

        //		Properties p1 = new Properties();
        //		p1.load(new FileInputStream(ws1.getDBType().getPath()));
        //		Properties p2 = new Properties();
        //		p2.load(new FileInputStream(ws1.getRefCopy().getDBType().getPath()));
        //		assertTrue(p1.equals(p2));
        //assertTrue(FileUtils.compareBinFile(ws1.getDBType().getPath(),
        // ws1.getRefCopy().getDBType().getPath()));
        assertTrue(ws1.getClient().getLastTicket() == ws1.getNs());
        assertTrue(ws1.getAppliedPatch().list().length == 1);

        FileUtils.editTxtFile2(dir1 + File.separator + "a", "éé\nfsdfsdfsf");

        System.out.println("YOOO:" + dir1 + " File.encoding:" + System.getProperty("file.encoding"));

        ws1.commit("yop");
        assertTrue(ws1.getRefCopy().exists("a"));
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "a", ws1.getRefCopy().getPath("a")));
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "a", ws1.getRefCopy().getPath("a")));
    }

    public void testUpdate() throws Exception {
        testCommit();
        ws2.update();
        assertTrue(ws2.getAppliedPatch().list().length == 1);
        assertTrue(ws2.getReceivedPatch().list().length == 0);
        assertTrue(FileUtils.compareDir(dir1, dir2));
        assertTrue(FileUtils.compareDir(ws1.getRefCopyPath(), ws2.getRefCopyPath()));

        //		Properties p1 = new Properties();
        //		p1.load(new FileInputStream(ws1.getDBType().getPath()));
        //		Properties p2 = new Properties();
        //		p2.load(new FileInputStream(ws2.getDBType().getPath()));
        //		assertTrue(p1.equals(p2));
        //assertTrue(FileUtils.compareBinFile(ws1.getDBType().getPath(),ws2.getDBType().getPath()));
        Properties p1 = new Properties();
        FileInputStream fis = new FileInputStream(ws1.getRefCopy().getDBType().getPath());
        p1.load(fis);
        fis.close();

        Properties p2 = new Properties();
        fis = new FileInputStream(ws2.getRefCopy().getDBType().getPath());
        p2.load(fis);
        fis.close();
        assertTrue(p1.equals(p2));

        //assertTrue(FileUtils.compareBinFile(ws1.getRefCopy().getDBType().getPath(),ws2.getRefCopy().getDBType().getPath()));
        assertTrue(ws1.getNs() == ws2.getNs());
        assertTrue(ws1.getNs() == ws1.getClient().getLastTicket());
    }
}
