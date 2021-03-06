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
package org.libresource.so6.core.engine.longtest;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;


public class OptmisationTest extends TestCase {
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

    public OptmisationTest(String name) {
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

    public void testWithoutSubTree() throws Exception {
        // create 50 files at replica1 in a subdirectory
        FileUtils.createDir(dir1 + File.separator + "subDir1");

        for (int i = 0; i < 50; i++) {
            FileUtils.createTxtFile(dir1 + File.separator + "subDir1", "file1_" + i + ".txt", "some content");
        }

        // create 50 files at replica2 in a subdirectory
        FileUtils.createDir(dir2 + File.separator + "subDir2");

        for (int i = 0; i < 50; i++) {
            FileUtils.createTxtFile(dir2 + File.separator + "subDir2", "file2_" + i + ".txt", "some content");
        }

        // synchronize replica 1
        ws1.updateAndCommit();

        // synchronize replica 2
        ws2.updateAndCommit();

        // synchronize replica 1
        ws1.updateAndCommit();

        // check the synchronisation result
        assertTrue(FileUtils.compareDir(dir1, dir2));
        assertTrue(FileUtils.compareDir(dir1 + File.separator + "subDir1", dir2 + File.separator + "subDir1"));
        assertTrue(FileUtils.compareDir(dir1 + File.separator + "subDir2", dir2 + File.separator + "subDir2"));
    }
}
