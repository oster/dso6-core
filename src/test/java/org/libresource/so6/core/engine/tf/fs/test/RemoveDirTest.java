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
package org.libresource.so6.core.engine.tf.fs.test;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;


public class RemoveDirTest extends TestCase {
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

    public RemoveDirTest(String name) {
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
    private void setContext() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createDir(dir1 + File.separator + "dirOfUser1");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "dirOfUser1");
        assertTrue("dir1 does not contain dirOfUser1", f.exists());
        f = new File(dir2 + File.separator + "dirOfUser1");
        assertTrue("dir2 does not contain dirOfUser1", f.exists());
    }

    public void testRemoveDirRemoveDir() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createDir(dir2 + File.separator + "dirOfUser2");
        this.setContext();

        // Check my context
        f = new File(dir1 + File.separator + "dirOfUser2");
        assertTrue("dir1 does not contain dirOfUser2", f.exists());
        f = new File(dir2 + File.separator + "dirOfUser2");
        assertTrue("dir2 does not contain dirOfUser2", f.exists());

        // RemoveDir / RemoveFile
        FileUtils.remove(dir1 + File.separator + "dirOfUser1");
        FileUtils.remove(dir2 + File.separator + "dirOfUser2");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "dirOfUser1");
        assertTrue(!f.exists());
        f = new File(dir2 + File.separator + "dirOfUser1");
        assertTrue(!f.exists());
        f = new File(dir1 + File.separator + "dirOfUser2");
        assertTrue(!f.exists());
        f = new File(dir2 + File.separator + "dirOfUser2");
        assertTrue(!f.exists());
    }

    public void testRemoveDirRemoveDirWithConflict() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        this.setContext();

        // RemoveDir / RemoveFile
        FileUtils.remove(dir1 + File.separator + "dirOfUser1");
        FileUtils.remove(dir2 + File.separator + "dirOfUser1");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "dirOfUser1");
        assertTrue(!f.exists());
        f = new File(dir2 + File.separator + "dirOfUser1");
        assertTrue(!f.exists());
    }

    public void testRemoveDirRemoveFile() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createTxtFile(dir2, "fileOfUser2.txt", "Content of the file of the user 2...");
        this.setContext();

        // Check my context
        f = new File(dir1 + File.separator + "fileOfUser2.txt");
        assertTrue("dir1 does not contain fileOfUser2.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.txt");
        assertTrue("dir2 does not contain fileOfUser2.txt", f.exists());

        // RemoveDir / RemoveFile
        FileUtils.remove(dir1 + File.separator + "dirOfUser1");
        FileUtils.remove(dir2 + File.separator + "fileOfUser2.txt");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "dirOfUser1");
        assertTrue(!f.exists());
        f = new File(dir2 + File.separator + "dirOfUser1");
        assertTrue(!f.exists());
        f = new File(dir1 + File.separator + "fileOfUser2.txt");
        assertTrue(!f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.txt");
        assertTrue(!f.exists());
    }

    public void testRemoveDirAddFile() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        this.setContext();

        // RemoveDir / AddFile
        FileUtils.remove(dir1 + File.separator + "dirOfUser1");
        FileUtils.createTxtFile(dir2, "fileOfUser2.txt", "Content of the file of the user 2...");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "dirOfUser1");
        assertTrue(!f.exists());
        f = new File(dir2 + File.separator + "dirOfUser1");
        assertTrue(!f.exists());
        f = new File(dir1 + File.separator + "fileOfUser2.txt");
        assertTrue(f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.txt");
        assertTrue(f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser2.txt", dir2 + File.separator + "fileOfUser2.txt"));
    }

    public void testRemoveDirAddBinaryFile() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        this.setContext();

        // RemoveDir / AddFile
        FileUtils.remove(dir1 + File.separator + "dirOfUser1");
        FileUtils.createBinFile(dir2, "fileOfUser2.bin", "Content of the file of the user 2...");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "dirOfUser1");
        assertTrue(!f.exists());
        f = new File(dir2 + File.separator + "dirOfUser1");
        assertTrue(!f.exists());
        f = new File(dir1 + File.separator + "fileOfUser2.bin");
        assertTrue(f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.bin");
        assertTrue(f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser2.bin", dir2 + File.separator + "fileOfUser2.bin"));
    }

    public void testRemoveDirAddDir() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        this.setContext();

        // RemoveDir / AddDir
        FileUtils.remove(dir1 + File.separator + "dirOfUser1");
        FileUtils.createDir(dir2 + File.separator + "dirOfUser2");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "dirOfUser1");
        assertTrue(!f.exists());
        f = new File(dir2 + File.separator + "dirOfUser1");
        assertTrue(!f.exists());
        f = new File(dir1 + File.separator + "dirOfUser2");
        assertTrue(f.exists());
        f = new File(dir2 + File.separator + "dirOfUser2");
        assertTrue(f.exists());
    }

    public void testRemoveDirUpdateFile() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createTxtFile(dir2, "fileOfUser2.txt", "line1\n line2\n line3\n");
        this.setContext();

        // Check my context
        f = new File(dir1 + File.separator + "fileOfUser2.txt");
        assertTrue("dir1 does not contain fileOfUser2.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.txt");
        assertTrue("dir2 does not contain fileOfUser2.txt", f.exists());

        // RemoveDir / ChangeFile
        FileUtils.remove(dir1 + File.separator + "dirOfUser1");
        FileUtils.editTxtFile(dir2 + File.separator + "fileOfUser2.txt", "line1\n line3\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "dirOfUser1");
        assertTrue(!f.exists());
        f = new File(dir2 + File.separator + "dirOfUser1");
        assertTrue(!f.exists());
        f = new File(dir1 + File.separator + "fileOfUser2.txt");
        assertTrue(f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.txt");
        assertTrue(f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser2.txt", dir2 + File.separator + "fileOfUser2.txt"));
    }

    public void testRemoveDirUpdateBinaryFile() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createBinFile(dir2, "fileOfUser2.bin", "Content of the file of the user 2...");
        this.setContext();

        // Check my context
        f = new File(dir1 + File.separator + "fileOfUser2.bin");
        assertTrue("dir1 does not contain fileOfUser2.bin", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.bin");
        assertTrue("dir2 does not contain fileOfUser2.bin", f.exists());

        // RemoveDir / ChangeFile
        FileUtils.remove(dir1 + File.separator + "dirOfUser1");
        FileUtils.editBinFile(dir2 + File.separator + "fileOfUser2.bin", "New content of the file of the user 2...");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "dirOfUser1");
        assertTrue(!f.exists());
        f = new File(dir2 + File.separator + "dirOfUser1");
        assertTrue(!f.exists());
        f = new File(dir1 + File.separator + "fileOfUser2.bin");
        assertTrue(f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.bin");
        assertTrue(f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser2.bin", dir2 + File.separator + "fileOfUser2.bin"));
    }
}
