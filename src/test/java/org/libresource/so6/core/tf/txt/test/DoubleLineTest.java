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
package org.libresource.so6.core.tf.txt.test;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;


public class DoubleLineTest extends TestCase {
    private String clientName = System.getProperty("clientName");
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;
    private String tmpdir;
    private String common = "common.txt";
    private String expectedName = "expected.txt";
    private String common1;
    private String common2;
    private String expectedPath;

    public DoubleLineTest(String name) {
        super(name);
    }

    // 	public static Test suite() {
    // 		TestSuite suite = new TestSuite();
    // 		suite.addTest(new AddBlockTest("testTwoAddBlocks"));
    // 		return suite;
    // 	}
    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2);
        ws1 = ws[0];
        ws2 = ws[1];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();
        tmpdir = dir + File.separator + "tmpdir";
        common1 = dir1 + File.separator + common;
        common2 = dir2 + File.separator + common;
        expectedPath = tmpdir + File.separator + expectedName;

        File d = new File(tmpdir);

        if (!(d.mkdirs())) {
            throw new Exception("cannot create:" + d.getPath());
        }
    }

    public void tearDown() throws Exception {
    }

    //////////////////////////////////////////
    public void testDoubleLine() throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, "A\nB\nC\n");

        // Synchronize user1 / user2
        ws1.commit("sync ws1");
        ws2.update();

        // concurrent change
        FileUtils.editTxtFile(dir1 + File.separator + common, "A\nX\nC\n");
        FileUtils.editTxtFile(dir2 + File.separator + common, "A\nY\nC\n");

        // sync
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();
        System.out.println(common1);

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));

        //FileUtils.createTxtFile(tmpdir, expectedName, "\ntata");
        assertTrue("Divergence in the 2 synchronized files", FileUtils.compareTxtFile(common1, common2));

        //assertTrue("unexpected result in " + dir,
        // FileUtils.compareTxtFile(expectedPath, common2));
    }

    // un seul AddBlok
    public void kotestDoubleLine() throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, "\n\ntest\n");

        // Synchronize user1 / user2
        ws1.commit("sync ws1");
        ws2.update();

        // concurrent change
        FileUtils.editTxtFile(dir1 + File.separator + common, "\ntata\n");
        FileUtils.editTxtFile(dir2 + File.separator + common, "\n\ntata\n");

        // sync
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));
        FileUtils.createTxtFile(tmpdir, expectedName, "\ntata");
        assertTrue("Divergence in the 2 synchronized files", FileUtils.compareTxtFile(common1, common2));
        assertTrue("unexpected result in " + dir, FileUtils.compareTxtFile(expectedPath, common2));
    }

    public void oktestDoubleLine2() throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, "\n\ntest\nabc\n");

        // Synchronize user1 / user2
        ws1.commit("sync ws1");
        ws2.update();

        // concurrent change
        FileUtils.editTxtFile(dir1 + File.separator + common, "\ntata\nabc\n");
        FileUtils.editTxtFile(dir2 + File.separator + common, "\n\ntata\nabc\n");

        // sync
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));
        FileUtils.createTxtFile(tmpdir, expectedName, "\ntata\nabc\n");
        assertTrue("Divergence in the 2 synchronized files", FileUtils.compareTxtFile(common1, common2));
        assertTrue("unexpected result in " + dir, FileUtils.compareTxtFile(expectedPath, common2));
    }
}
