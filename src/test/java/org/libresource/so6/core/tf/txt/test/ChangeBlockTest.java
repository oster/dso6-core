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


public class ChangeBlockTest extends TestCase {
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

    public ChangeBlockTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2);
        ws1 = ws[0];
        ws2 = ws[1];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();
        tmpdir = FileUtils.createTmpDir().getPath();
        common1 = dir1 + File.separator + common;
        common2 = dir2 + File.separator + common;
        expectedPath = tmpdir + File.separator + expectedName;
    }

    //////////////////////////////////////////
    // un seul ChangeBlock
    public void testChangeBlock() throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n line6\n line7\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // initial state
        // user1's actions
        FileUtils.remove(common1);
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3a\n line4\n line5\n line6\n line7\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));
        FileUtils.createTxtFile(tmpdir, expectedName, " line1\n line2\n line3a\n line4\n line5\n line6\n line7\n");
        assertTrue("unexpected result in dir1", FileUtils.compareTxtFile(expectedPath, common1));
        assertTrue("unexpected result in dir2", FileUtils.compareTxtFile(expectedPath, common2));
    }

    // un seul ChangeBlock
    public void testTwoChangeBlock() throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n line6\n line7\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // initial state
        // user1's actions
        FileUtils.remove(common1);
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3a\n line4\n line5a\n line6a\n line7\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));
        FileUtils.createTxtFile(tmpdir, expectedName, " line1\n line2\n line3a\n line4\n line5a\n line6a\n line7\n");
        assertTrue("unexpected result in dir1", FileUtils.compareTxtFile(expectedPath, common1));
        assertTrue("unexpected result in dir2", FileUtils.compareTxtFile(expectedPath, common2));
    }

    public void testChangeBlocChangeBloc_A_before_B() throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n line6\n line7\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // initial state
        // user1's actions
        FileUtils.remove(common1);
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3a\n line4a\n line5\n line6\n line7\n");

        // user2's actions
        FileUtils.remove(common2);
        FileUtils.createTxtFile(dir2, common, " line1\n line2\n line3\n line4\n line5\n line6b\n line7\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));
        FileUtils.createTxtFile(tmpdir, expectedName, " line1\n line2\n line3a\n line4a\n line5\n line6b\n line7\n");
        assertTrue("unexpected result in dir1", FileUtils.compareTxtFile(expectedPath, common1));
        assertTrue("unexpected result in dir2", FileUtils.compareTxtFile(expectedPath, common2));
    }

    public void testChangeBlocChangeBloc_A_after_B() throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n line6\n line7\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // initial state
        // user1's actions
        FileUtils.remove(common1);
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n line6b\n line7\n");

        // user2's actions
        FileUtils.remove(common2);
        FileUtils.createTxtFile(dir2, common, " line1\n line2\n line3a\n line4a\n line5\n line6\n line7\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));
        FileUtils.createTxtFile(tmpdir, expectedName, " line1\n line2\n line3a\n line4a\n line5\n line6b\n line7\n");
        assertTrue("unexpected result in dir1", FileUtils.compareTxtFile(expectedPath, common1));
        assertTrue("unexpected result in dir2", FileUtils.compareTxtFile(expectedPath, common2));
    }

    // possible de faire le meme test ou la position du chgt de A est inclus
    // dans B mais le contenu est diff
    public void testChangeBlocChangeBloc_AinB() throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n line6\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // initial state
        // user1's actions
        FileUtils.remove(common1);
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4b\n line5\n line6\n");

        // user2's actions
        FileUtils.remove(common2);
        FileUtils.createTxtFile(dir2, common, " line1\n line2\n line3b\n line4b\n line5b\n line6\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));

        /*
         * FileUtils.createTxtFile( tmpdir, expectedName, " line1\n
         * line2\n>add>>>>>>>>>>>>>>>>\n>>remove>>>>>>>>>>>>>\n>>
         * line5\n>====================\n>> line4b\n> <add < < < < < < < < < < < < < < <
         * <\n====================\n> line3b\n> line4b\n> line5b\n <add < < < < < < < < < < < < < < <
         * <\n line6\n");
         */
        assertTrue("No convergence", FileUtils.compareTxtFile(common1, common2));

        //assertTrue("unexpected result in dir2",
        // FileUtils.compareTxtFile(expectedPath, common2));
        System.out.println("testChangeBlocChangeBloc_AinB " + dir1);
    }

    public void testChangeBlocChangeBloc_BinA() throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n line6\n line7\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // initial state
        // user1's actions
        FileUtils.remove(common1);
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3a\n line4a\n line5a\n line6\n line7\n");

        // user2's actions
        FileUtils.remove(common2);
        FileUtils.createTxtFile(dir2, common, " line1\n line2\n line3\n line4b\n line5\n line6\n line7\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));

        /*
         * FileUtils.createTxtFile( tmpdir, expectedName, " line1\n
         * line2\n>add>>>>>>>>>>>>>>>>\n> line3a\n> line4a\n>
         * line5a\n====================\n>>remove>>>>>>>>>>>>>\n>>
         * line5\n>====================\n>> line4b\n> <add < < < < < < < < < < < < < < <
         * <\n <add < < < < < < < < < < < < < < < <\n line6\n line7\n");
         */
        assertTrue("No convergence", FileUtils.compareTxtFile(common1, common2));

        //assertTrue("unexpected result in dir2",
        // FileUtils.compareTxtFile(expectedPath, common2));
        System.out.println("testChangeBlocChangeBloc_BinA " + dir1);
    }

    public void testChangeBlocChangeBloc_OverLap_A_before_B()
        throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n line6\n line7\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // initial state
        // user1's actions
        FileUtils.remove(common1);
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3a\n line4a\n line5a\n line6\n line7\n");

        // user2's actions
        FileUtils.remove(common2);
        FileUtils.createTxtFile(dir2, common, " line1\n line2\n line3\n line4\n line5b\n line6b\n line7b\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));

        /*
         * FileUtils.createTxtFile( tmpdir, expectedName, " line1\n
         * line2\n>remove>>>>>>>>>>>>>\n> line6\n>
         * line7\n====================\n> line3a\n> line4a\n> line5a\n <add < < < < < < < < < < < < < < <
         * <\n line5b\n line6b\n line7b\n");
         */
        assertTrue("No convergence", FileUtils.compareTxtFile(common1, common2));

        //assertTrue("unexpected result in dir2",
        // FileUtils.compareTxtFile(expectedPath, common2));
        System.out.println("testChangeBlocChangeBloc_OverLap_A_before_B " + dir1);
    }

    public void testChangeBlocChangeBloc_OverLap_A_after_B()
        throws Exception {
        // initial state
        // AddFile common files
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5\n line6\n line7\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // initial state
        // user1's actions
        FileUtils.remove(common1);
        FileUtils.createTxtFile(dir1, common, " line1\n line2\n line3\n line4\n line5a\n line6a\n line7a\n");

        // user2's actions
        FileUtils.remove(common2);
        FileUtils.createTxtFile(dir2, common, " line1\n line2\n line3b\n line4b\n line5b\n line6\n line7\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check convergence...
        assertTrue("dir1 does not contain common.txt", FileUtils.fileExists(common1));
        assertTrue("dir2 does not contain common.txt", FileUtils.fileExists(common2));

        /*
         * String currentResult = " line1\n line2\n>remove>>>>>>>>>>>>>\n>
         * line6\n> line7\n====================\n> line3b\n> line4b\n> line5b\n
         * <add < < < < < < < < < < < < < < < <\n line5a\n line6a\n line7a\n";
         * FileUtils.createTxtFile(tmpdir, expectedName, currentResult);
         */
        assertTrue("No convergence", FileUtils.compareTxtFile(common1, common2));

        //assertTrue("unexpected result in dir2",
        // FileUtils.compareTxtFile(expectedPath, common2));
        //assertTrue("convergence mais resultat pas satisfaisant", false);
        System.out.println("testChangeBlocChangeBloc_OverLap_A_after_B : " + dir1);
    }
}
