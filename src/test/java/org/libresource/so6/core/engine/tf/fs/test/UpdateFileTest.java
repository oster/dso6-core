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

import java.util.logging.Logger;


public class UpdateFileTest extends TestCase {
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

    public UpdateFileTest(String name) {
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
    public void testUpdateFileUpdateFile() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createTxtFile(dir1, "fileOfUser1.txt", "Content of the file of the user 1...");
        FileUtils.createTxtFile(dir2, "fileOfUser2.txt", "Content of the file of the user 2...");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser1.txt");
        assertTrue("dir1 does not contain fileOfUser1.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.txt");
        assertTrue("dir2 does not contain fileOfUser1.txt", f.exists());
        f = new File(dir1 + File.separator + "fileOfUser2.txt");
        assertTrue("dir1 does not contain fileOfUser2.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.txt");
        assertTrue("dir2 does not contain fileOfUser2.txt", f.exists());

        // ChangeFile user1 / ChangeFile user2
        FileUtils.editTxtFile(dir1 + File.separator + "fileOfUser1.txt", "The new content of the file of the user1");
        FileUtils.editTxtFile(dir2 + File.separator + "fileOfUser2.txt", "The new content of the file of the user2");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser1.txt", dir2 + File.separator + "fileOfUser1.txt"));
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser2.txt", dir2 + File.separator + "fileOfUser2.txt"));
    }

    public void testUpdateFileUpdateBinaryFile() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createTxtFile(dir1, "fileOfUser1.txt", "Content of the file of the user 1...");
        FileUtils.createBinFile(dir2, "fileOfUser2.bin", "Content of the file of the user 2...\néééééééééééééééééééééééé");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser1.txt");
        assertTrue("dir1 does not contain fileOfUser1.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.txt");
        assertTrue("dir2 does not contain fileOfUser1.txt", f.exists());
        f = new File(dir1 + File.separator + "fileOfUser2.bin");
        assertTrue("dir1 does not contain fileOfUser2.bin", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.bin");
        assertTrue("dir2 does not contain fileOfUser2.bin", f.exists());

        // ChangeFile user1 / ChangeFile user2
        FileUtils.editTxtFile(dir1 + File.separator + "fileOfUser1.txt", "The new content of the file of the user1");
        FileUtils.editBinFile(dir2 + File.separator + "fileOfUser2.bin",
            "The new content of the file of the user2.\néééééééééééééééééééééééé");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser1.txt", dir2 + File.separator + "fileOfUser1.txt"));
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser2.bin", dir2 + File.separator + "fileOfUser2.bin"));
    }

    public void testUpdateFileUpdateFileWithConflict()
        throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createBinFile(dir1, "fileOfUser1", "Content of the file of the user 1....\néééééééééééééééééééééééé");
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser1");
        assertTrue("dir1 does not contain fileOfUser1", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1");
        assertTrue("dir2 does not contain fileOfUser1", f.exists());

        // ChangeFile user1 / ChangeFile user2
        FileUtils.editBinFile(dir1 + File.separator + "fileOfUser1",
            "The new content of the file of the user1.\néééééééééééééééééééééééé");
        FileUtils.editBinFile(dir2 + File.separator + "fileOfUser1",
            "The new conflicting content of the file of the user2.\néééééééééééééééééééééééé");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        Logger.getLogger("test.out").info("dir1 contents:" + FileUtils.list(dir1));
        Logger.getLogger("test.out").info("dir2 contents:" + FileUtils.list(dir2));
        ws2.updateAndCommit();

        //<--plante
        Logger.getLogger("test.out").info("dir1 contents:" + FileUtils.list(dir1));
        Logger.getLogger("test.out").info("dir2 contents:" + FileUtils.list(dir2));
        ws1.updateAndCommit();
        Logger.getLogger("test.out").info("dir1 contents:" + FileUtils.list(dir1));
        Logger.getLogger("test.out").info("dir2 contents:" + FileUtils.list(dir2));

        // Check if the result is correct
        f = new File(dir1 + File.separator + "fileOfUser1");
        assertTrue("dir1 does not contain fileOfUser1", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1");
        assertTrue("dir2 does not contain fileOfUser1", f.exists());
        f = new File(dir1 + File.separator + "fileOfUser1#2");
        assertTrue("dir1 does not contain fileOfUser1#2", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1#2");
        assertTrue("dir2 does not contain fileOfUser1#2", f.exists());

        // Check contents
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser1", dir2 + File.separator + "fileOfUser1"));
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser1#2", dir2 + File.separator + "fileOfUser1#2"));
    }

    public void testUpdateFileAddFile() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createTxtFile(dir1, "fileOfUser1.txt", "Content of the file of the user 1...");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser1.txt");
        assertTrue("dir1 does not contain fileOfUser1.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.txt");
        assertTrue("dir2 does not contain fileOfUser1.txt", f.exists());

        // AddFile user1 / Change the file for user2
        FileUtils.editTxtFile(dir1 + File.separator + "fileOfUser1.txt", "new Content of the file fileOfUser1");
        FileUtils.createTxtFile(dir2, "fileOfUser2.txt", "Content of the new file of the user 2...");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "fileOfUser2.txt");
        assertTrue("dir1 does not contain fileOfUser2.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.txt");
        assertTrue("dir2 does not contain fileOfUser2.txt", f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser1.txt", dir2 + File.separator + "fileOfUser1.txt"));
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser2.txt", dir2 + File.separator + "fileOfUser2.txt"));
    }

    public void testUpdateFileAddBinaryFile() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createTxtFile(dir1, "fileOfUser1.txt", "Content of the file of the user 1...");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser1.txt");
        assertTrue("dir1 does not contain fileOfUser1.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.txt");
        assertTrue("dir2 does not contain fileOfUser1.txt", f.exists());

        // AddFile user1 / Change the file for user2
        FileUtils.editTxtFile(dir1 + File.separator + "fileOfUser1.txt", "new Content of the file fileOfUser1");
        FileUtils.createBinFile(dir2, "fileOfUser2.bin", "Content of the new file of the user 2...\néééééééééééééééééééééééé");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "fileOfUser2.bin");
        assertTrue("dir1 does not contain fileOfUser2.bin", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.bin");
        assertTrue("dir2 does not contain fileOfUser2.bin", f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser1.txt", dir2 + File.separator + "fileOfUser1.txt"));
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser2.bin", dir2 + File.separator + "fileOfUser2.bin"));
    }

    public void testUpdateFileAddDir() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createTxtFile(dir1, "fileOfUser1.txt", "Content of the file of the user 1...");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser1.txt");
        assertTrue("dir1 does not contain fileOfUser1.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.txt");
        assertTrue("dir2 does not contain fileOfUser1.txt", f.exists());
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser1.txt", dir2 + File.separator + "fileOfUser1.txt"));

        // AddFile user1 / Change the file for user2
        FileUtils.editTxtFile(dir1 + File.separator + "fileOfUser1.txt", "new Content of the file fileOfUser1");
        FileUtils.createDir(dir2 + File.separator + "dirOfUser2");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "dirOfUser2");
        assertTrue("dir1 does not contain dirOfUser2", f.exists());
        f = new File(dir2 + File.separator + "dirOfUser2");
        assertTrue("dir2 does not contain dirOfUser2", f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser1.txt", dir2 + File.separator + "fileOfUser1.txt"));
    }

    public void testUpdateFileRemoveFile() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createTxtFile(dir1, "fileOfUser1.txt", "Content of the file of the user 1...");
        FileUtils.createTxtFile(dir2, "fileOfUser2.txt", "Content of the file of the user 2...");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser1.txt");
        assertTrue("dir1 does not contain fileOfUser1", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.txt");
        assertTrue("dir2 does not contain fileOfUser1.txt", f.exists());
        f = new File(dir1 + File.separator + "fileOfUser2.txt");
        assertTrue("dir1 does not contain fileOfUser2.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.txt");
        assertTrue("dir2 does not contain fileOfUser2.txt", f.exists());

        // UpdateFile user1 / RemoveFile user2
        FileUtils.editTxtFile(dir1 + File.separator + "fileOfUser1.txt", "new Content of the file fileOfUser1.txt");
        FileUtils.remove(dir2 + File.separator + "fileOfUser2.txt");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "fileOfUser1.txt");
        assertTrue("fileOfUser1.txt does not exist", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.txt");
        assertTrue("fileOfUser1.txt does not exist", f.exists());
        f = new File(dir1 + File.separator + "fileOfUser2.txt");
        assertTrue(!f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.txt");
        assertTrue(!f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser1.txt", dir2 + File.separator + "fileOfUser1.txt"));
    }

    public void testUpdateFileRemoveFileWithConflict()
        throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createTxtFile(dir1, "fileOfUser1.txt", "Content of the file of the user 1...");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser1.txt");
        assertTrue("dir1 does not contain fileOfUser1", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.txt");
        assertTrue("dir2 does not contain fileOfUser1.txt", f.exists());

        // UpdateFile user1 / RemoveFile user2
        FileUtils.editTxtFile(dir1 + File.separator + "fileOfUser1.txt", "new Content of the file fileOfUser1.txt");
        FileUtils.remove(dir2 + File.separator + "fileOfUser1.txt");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "fileOfUser1.txt");
        assertTrue(!f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.txt");
        assertTrue(!f.exists());
    }

    public void testUpdateFileRemoveDir() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createTxtFile(dir1, "fileOfUser1.txt", "Content of the file of the user 1...");
        FileUtils.createDir(dir2 + File.separator + "dirOfUser2");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser1.txt");
        assertTrue("dir1 does not contain fileOfUser1.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.txt");
        assertTrue("dir2 does not contain fileOfUser1.txt", f.exists());
        f = new File(dir1 + File.separator + "dirOfUser2");
        assertTrue("dir1 does not contain dirOfUser2", f.exists());
        f = new File(dir2 + File.separator + "dirOfUser2");
        assertTrue("dir2 does not contain dirOfUser2", f.exists());

        // AddDir user1 / RemoveDir user2
        FileUtils.editTxtFile(dir1 + File.separator + "fileOfUser1.txt", "new Content of the file fileOfUser1");
        FileUtils.remove(dir2 + File.separator + "dirOfUser2");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "fileOfUser1.txt");
        assertTrue("fileOfUser1.txt does not exist", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.txt");
        assertTrue("fileOfUser1.txt does not exist", f.exists());
        f = new File(dir1 + File.separator + "dirOfUser2");
        assertTrue(!f.exists());
        f = new File(dir2 + File.separator + "dirOfUser2");
        assertTrue(!f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser1.txt", dir2 + File.separator + "fileOfUser1.txt"));
    }
}
