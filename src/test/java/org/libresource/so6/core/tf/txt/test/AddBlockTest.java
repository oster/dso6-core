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


public class AddBlockTest extends TestCase {
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

    public AddBlockTest(String name) {
        super(name);
    }

    /*
     * public static Test suite() { TestSuite suite = new TestSuite();
     * suite.addTest(new AddBlockTest("
     * testAddBlockAddBlock_sameLocationDifferentSize")); return suite; }
     */
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
    // un seul AddBlok
    public void testAddBlock() throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n");

        // Synchronize user1 / user2
        ws1.commit("sync ws1");
        ws2.update();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));
        FileUtils.createTxtFile(tmpdir, expectedName, " line1\n line2\n line3\n line4\n line5\n");
        assertTrue("unexpected result in dir1", FileUtils.compareTxtFile(expectedPath, common1));
        assertTrue("unexpected result in dir2", FileUtils.compareTxtFile(expectedPath, common2));
    }

    // deux AddBlok
    public void testTwoAddBlocks() throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n");

        // Synchronize user1 / user2
        ws1.commit("sync ws1");
        ws2.update();

        // user1's actions
        FileUtils.remove(common1);
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line2-1\n line2-2\n line2-3\n line3\n line4\n line4-1\n line4-2\n line5\n");

        // Synchronize user1 / user2
        //ws1.update();
        ws1.commit("sync ws1");
        ws2.update();
        ws2.commit("sync ws2");
        ws1.update();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));
        FileUtils.createTxtFile(tmpdir, expectedName, " line1\n line2\n line2-1\n line2-2\n line2-3\n line3\n line4\n line4-1\n line4-2\n line5\n");
        assertTrue("unexpected result in dir1", FileUtils.compareTxtFile(expectedPath, common1));
        assertTrue("unexpected result in dir2", FileUtils.compareTxtFile(expectedPath, common2));
    }

    // deux AddBlock disjoints celui du user1 derriere celui du user2
    public void testAddBlockAddBlock_AafterB() throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n");

        // Synchronize user1 / user2
        ws1.commit("sync ws1");
        ws2.update();

        // initial state
        // user1's actions
        FileUtils.remove(common1);
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line4-1\n line4-2\n line5\n"); // add
                                                                                                               // lines
                                                                                                               // 5-6

        // user2's actions
        FileUtils.remove(common2);
        FileUtils.createTxtFile(dir2, common, " line1\n line2\n line2-1\n line2-2\n line3\n line4\n line5\n"); // add
                                                                                                               // lines
                                                                                                               // 3-4

        // Synchronize user1 / user2
        ws1.update();
        ws1.commit("sync ws1");
        ws2.update();
        ws2.commit("sync ws2");
        ws1.update();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));
        FileUtils.createTxtFile(tmpdir, expectedName, " line1\n line2\n line2-1\n line2-2\n line3\n line4\n line4-1\n line4-2\n line5\n"); // add
                                                                                                                                           // lines
                                                                                                                                           // 3-4
                                                                                                                                           // 5-6

        assertTrue("unexpected result in dir1", FileUtils.compareTxtFile(expectedPath, common1));
        assertTrue("unexpected result in dir2", FileUtils.compareTxtFile(expectedPath, common2));
    }

    // deux addBlock disjoints celui du user1 devant celui du user2
    public void testAddBlockAddBlock_AbeforeB() throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n");

        // Synchronize user1 / user2
        ws1.update();
        ws1.commit("sync ws1");
        ws2.update();
        ws2.commit("sync ws2");
        ws1.update();

        // initial state
        // user1's actions
        FileUtils.remove(common1);
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line2-1\n line2-2\n line3\n line4\n line5\n"); // add
                                                                                                               // lines
                                                                                                               // 3-4

        // user2's actions
        FileUtils.remove(common2);
        FileUtils.createTxtFile(dir2, common, " line1\n line2\n line3\n line4\n line4-1\n line4-2\n line5\n"); // add
                                                                                                               // lines
                                                                                                               // 5-6

        // Synchronize user1 / user2
        ws1.update();
        ws1.commit("sync ws1");
        ws2.update();
        ws2.commit("sync ws2");
        ws1.update();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));
        FileUtils.createTxtFile(tmpdir, expectedName, " line1\n line2\n line2-1\n line2-2\n line3\n line4\n line4-1\n line4-2\n line5\n"); // add
                                                                                                                                           // lines
                                                                                                                                           // 3-4
                                                                                                                                           // 5-6

        assertTrue("unexpected result in dir1", FileUtils.compareTxtFile(expectedPath, common1));
        assertTrue("unexpected result in dir2", FileUtils.compareTxtFile(expectedPath, common2));
    }

    public void testAddBlockAddBlock_sameLocationDifferentSize()
        throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n");

        // Synchronize user1 / user2
        ws1.update();
        ws1.commit("sync ws1");
        ws2.update();
        ws2.commit("sync ws2");
        ws1.update();

        // initial state
        // user1's actions
        FileUtils.remove(common1);
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line2-1\n line2-2\n line3\n line4\n line5\n");

        // user2's actions
        FileUtils.remove(common2);
        FileUtils.createTxtFile(dir2, common, " line1\n line2\n line2-a\n line2-b\n line2-c\n line3\n line4\n line5\n");

        // Synchronize user1 / user2
        ws1.update();
        ws1.commit("sync ws1");
        ws2.update();
        ws2.commit("sync ws2");
        ws1.update();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));

        /*
         * FileUtils.createTxtFile( tmpdir, expectedName, " line1\n
         * line2\n>add>>>>>>>>>>>>>>>>\n> line2-1\n>
         * line2-2\n====================\n> line2-a\n> line2-b\n> line2-c\n <add < < < < < < < < < < < < < < <
         * <\n line3\n line4\n line5\n");
         */
        assertTrue("No convergence", FileUtils.compareTxtFile(common1, common2));

        //assertTrue("unexpected result in dir2",
        // FileUtils.compareTxtFile(expectedPath, common2));
    }

    public void testAddBlockAddBlock_sameLocationSameSizeDiffContents()
        throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n");

        // Synchronize user1 / user2
        ws1.update();
        ws1.commit("sync ws1");
        ws2.update();
        ws2.commit("sync ws2");
        ws1.update();

        // initial state
        // user1's actions
        FileUtils.remove(common1);
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line2-1\n line2-2\n line3\n line4\n line5\n");

        // user2's actions
        FileUtils.remove(common2);
        FileUtils.createTxtFile(dir2, common, " line1\n line2\n line2-a\n line2-b\n line3\n line4\n line5\n");

        // Synchronize user1 / user2
        ws1.update();
        ws1.commit("sync ws1");
        ws2.update();
        ws2.commit("sync ws2");
        ws1.update();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));

        /*
         * FileUtils.createTxtFile( tmpdir, expectedName, " line1\n
         * line2\n>add>>>>>>>>>>>>>>>>\n> line2-1\n>
         * line2-2\n====================\n> line2-a\n> line2-b\n <add < < < < < < < < < < < < < < <
         * <\n line3\n line4\n line5\n");
         */
        assertTrue("No convergence", FileUtils.compareTxtFile(common1, common2));

        //assertTrue("unexpected result in dir2",
        // FileUtils.compareTxtFile(expectedPath, common2));
    }

    public void testAddBlockAddBlock_Idem() throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n");

        // Synchronize user1 / user2
        ws1.update();
        ws1.commit("sync ws1");
        ws2.update();
        ws2.commit("sync ws2");
        ws1.update();

        // initial state
        // user1's actions
        FileUtils.remove(common1);
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line2-1\n line2-2\n line3\n line4\n line5\n");

        // user2's actions
        FileUtils.remove(common2);
        FileUtils.createTxtFile(dir2, common, " line1\n line2\n line2-1\n line2-2\n line3\n line4\n line5\n");

        // Synchronize user1 / user2
        ws1.update();
        ws1.commit("sync ws1");
        ws2.update();
        ws2.commit("sync ws2");
        ws1.update();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));
        FileUtils.createTxtFile(tmpdir, expectedName, " line1\n line2\n line2-1\n line2-2\n line3\n line4\n line5\n");
        assertTrue("unexpected result in dir1", FileUtils.compareTxtFile(expectedPath, common1));
        assertTrue("unexpected result in dir2", FileUtils.compareTxtFile(expectedPath, common2));
    }

    public void testAddBlockDelBlock_AbeforeB() throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n line6\n");

        // Synchronize user1 / user2
        ws1.update();
        ws1.commit("sync ws1");
        ws2.update();
        ws2.commit("sync ws2");
        ws1.update();

        // initial state
        // user1's actions
        FileUtils.remove(common1);
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line2-1\n line2-2\n line3\n line4\n line5\n line6\n");

        // user2's actions
        FileUtils.remove(common2);
        FileUtils.createTxtFile(dir2, common, " line1\n line2\n line3\n line6\n");

        // Synchronize user1 / user2
        ws1.update();
        ws1.commit("sync ws1");
        ws2.update();
        ws2.commit("sync ws2");
        ws1.update();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));
        FileUtils.createTxtFile(tmpdir, expectedName, " line1\n line2\n line2-1\n line2-2\n line3\n line6\n");
        assertTrue("unexpected result in dir1", FileUtils.compareTxtFile(expectedPath, common1));
        assertTrue("unexpected result in dir2", FileUtils.compareTxtFile(expectedPath, common2));
    }

    public void testAddBlockDelBlock_AafterB() throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n line6\n");

        // Synchronize user1 / user2
        ws1.update();
        ws1.commit("sync ws1");
        ws2.update();
        ws2.commit("sync ws2");
        ws1.update();

        // initial state
        // user1's actions
        FileUtils.remove(common1);
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n line5-1\n line5-2\n line6\n");

        // user2's actions
        FileUtils.remove(common2);
        FileUtils.createTxtFile(dir2, common, " line1\n line2\n line5\n line6\n");

        // Synchronize user1 / user2
        ws1.update();
        ws1.commit("sync ws1");
        ws2.update();
        ws2.commit("sync ws2");
        ws1.update();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));
        FileUtils.createTxtFile(tmpdir, expectedName, " line1\n line2\n line5\n line5-1\n line5-2\n line6\n");
        assertTrue("unexpected result in dir1", FileUtils.compareTxtFile(expectedPath, common1));
        assertTrue("unexpected result in dir2", FileUtils.compareTxtFile(expectedPath, common2));
    }

    public void testAddBlockDelBlock_AinB() throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n line6\n");

        // Synchronize user1 / user2
        ws1.update();
        ws1.commit("sync ws1");
        ws2.update();
        ws2.commit("sync ws2");
        ws1.update();

        // initial state
        // user1's actions
        FileUtils.remove(common1);
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line3-1\n line3-2\n line4\n line5\n line6\n");

        // user2's actions
        FileUtils.remove(common2);
        FileUtils.createTxtFile(dir2, common, " line1\n line2\n line5\n line6\n");

        // Synchronize user1 / user2
        ws1.update();
        ws1.commit("sync ws1");
        ws2.update();
        ws2.commit("sync ws2");
        ws1.update();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));

        /*
         * FileUtils.createTxtFile( tmpdir, expectedName, " line1\n
         * line2\n>remove>>>>>>>>>>>>>\n> line3\n>
         * line4\n====================\n> line3-1\n> line3-2\n <add < < < < < < < < < < < < < < <
         * <\n line5\n line6\n");
         */
        assertTrue("No convergence", FileUtils.compareTxtFile(common1, common2));

        //assertTrue("unexpected result in dir2",
        // FileUtils.compareTxtFile(expectedPath, common2));
    }
}
