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


public class AddFileTest extends TestCase {
    private String clientName = System.getProperty("clientName");
    private String dir;
    private String dir1;
    private String dir2;
    private String tmpdir;
    private WsConnection ws1;
    private WsConnection ws2;

    public AddFileTest(String name) {
        super(name);
    }

    //	public static Test suite() {
    //		TestSuite suite = new TestSuite();
    //		suite.addTest(new AddFileTest("testAddFileAddFileWithConflict"));
    //		return suite;
    //	}
    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2);
        ws1 = ws[0];
        ws2 = ws[1];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();
        tmpdir = dir + File.separator + "tmpdir";

        File d = new File(tmpdir);

        if (!(d.mkdirs())) {
            throw new Exception("cannot create:" + d.getPath());
        }
    }

    //////////////////////////////////////////
    public void testAddFileAddFileNoConflict() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createTxtFile(dir1, "fileOfUser1.txt", "Content of the file of the user 1...");
        FileUtils.createTxtFile(dir2, "fileOfUser2.txt", "Content of the file of the user 2...");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "fileOfUser1.txt");
        assertTrue("dir1 does not contain fileOfUser1.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.txt");
        assertTrue("dir2 does not contain fileOfUser1.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.txt");
        assertTrue("dir2 does not contain fileOfUser2.txt", f.exists());

        // Last Synchro to ckeck the convergence
        ws1.updateAndCommit();
        f = new File(dir1 + File.separator + "fileOfUser2.txt");
        assertTrue("dir1 does not contain fileOfUser2.txt", f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser2.txt", dir2 + File.separator + "fileOfUser2.txt"));
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser1.txt", dir2 + File.separator + "fileOfUser1.txt"));
    }

    public void testAddFileAddBinaryFile() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createTxtFile(dir1, "fileOfUser1.txt", "Content of the file of the user 1...");
        FileUtils.createBinFile(dir2, "fileOfUser2.bin", "Content of the file of the user 2...");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "fileOfUser1.txt");
        assertTrue("dir1 does not contain fileOfUser1.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.txt");
        assertTrue("dir2 does not contain fileOfUser1.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.bin");
        assertTrue("dir2 does not contain fileOfUser2.bin", f.exists());

        // Last Synchro to ckeck the convergence
        ws1.updateAndCommit();
        f = new File(dir1 + File.separator + "fileOfUser2.bin");
        assertTrue("dir1 does not contain fileOfUser2.bin", f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser2.bin", dir2 + File.separator + "fileOfUser2.bin"));
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser1.txt", dir2 + File.separator + "fileOfUser1.txt"));
    }

    public void testAddFileAddFileWithConflict() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createTxtFile(dir1, "ConflictFile.txt", "Content of the file of the user 1...");
        FileUtils.createTxtFile(dir2, "ConflictFile.txt", "Content of the file of the user 2...");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "ConflictFile.txt");
        assertTrue("dir1 does not contain ConflictFile.txt", f.exists());
        f = new File(dir2 + File.separator + "ConflictFile.txt");
        assertTrue("dir2 does not contain ConflictFile.txt", f.exists());

        // Check the content of the files
        assertTrue("file contents are not equals",
            FileUtils.compareTxtFile(dir1 + File.separator + "ConflictFile.txt", dir2 + File.separator + "ConflictFile.txt"));

        // not yet ;D !!!
    }

    public void testAddFileAddDirNoConflict() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createTxtFile(dir1, "fileOfUser1.txt", "Content of the file of the user 1...");
        FileUtils.createDir(dir2 + File.separator + "dirOfUser2");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "fileOfUser1.txt");
        assertTrue("dir1 does not contain fileOfUser1.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.txt");
        assertTrue("dir2 does not contain fileOfUser1.txt", f.exists());
        f = new File(dir2 + File.separator + "dirOfUser2");
        assertTrue("dirOfUser2 does not exist", f.exists());

        // Last Synchro to ckeck the convergence
        ws1.updateAndCommit();
        f = new File(dir1 + File.separator + "dirOfUser2");
        assertTrue("dirOfUser2 does not exist", f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser1.txt", dir2 + File.separator + "fileOfUser1.txt"));
    }

    public void testAddFileAddDirWithConflict() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createTxtFile(dir1, "NameInConflict.txt", "Content of the file of the user 1...");
        FileUtils.createDir(dir2 + File.separator + "NameInConflict.txt");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "NameInConflict.txt#1");
        assertTrue("dir1 does not contain directory NameInConflict.txt#1", f.exists() && f.isDirectory());
        f = new File(dir1 + File.separator + "NameInConflict.txt");
        assertTrue("file1 does not contain file NameInConflict", f.exists() && f.isFile());
        f = new File(dir2 + File.separator + "NameInConflict.txt#1");
        assertTrue("dir2 does not contain directory NameInConflict.txt#1", f.exists() && f.isDirectory());
        f = new File(dir2 + File.separator + "NameInConflict.txt");
        assertTrue("file2 does not contain file NameInConflict.txt", f.exists() && f.isFile());

        // Check the content of the files
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "NameInConflict.txt", dir2 + File.separator + "NameInConflict.txt"));
    }

    public void testAddFileUpdateFile() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createTxtFile(dir1, "fileOfUser1.txt", "Content of the file of the user 1...");
        FileUtils.createTxtFile(dir2, "fileOfUser2.txt", "line1\n line2\n line3\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // AddFile user1 / Change the file for user2
        FileUtils.createTxtFile(dir1, "file2OfUser1.txt", "Content of the new file of the user 1...");
        FileUtils.editTxtFile(dir2 + File.separator + "fileOfUser2.txt", "line1\n line3\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "file2OfUser1.txt");
        assertTrue("dir1 does not contain fileOfUser1.txt", f.exists());
        f = new File(dir2 + File.separator + "file2OfUser1.txt");
        assertTrue("dir2 does not contain fileOfUser1.txt", f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser2.txt", dir2 + File.separator + "fileOfUser2.txt"));
    }

    public void testAddFileUpdateBinaryFile() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createTxtFile(dir1, "fileOfUser1.txt", "Content of the file of the user 1...");
        FileUtils.createBinFile(dir2, "fileOfUser2.bin", "Content of the file of the user 2...");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // AddFile user1 / Change the file for user2
        FileUtils.createTxtFile(dir1, "file2OfUser1.txt", "Content of the new file of the user 1...");
        FileUtils.editBinFile(dir2 + File.separator + "fileOfUser2.bin", "new Content of the file fileOfUser2");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "file2OfUser1.txt");
        assertTrue("dir1 does not contain fileOfUser1.txt", f.exists());
        f = new File(dir2 + File.separator + "file2OfUser1.txt");
        assertTrue("dir2 does not contain fileOfUser1.txt", f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser2.bin", dir2 + File.separator + "fileOfUser2.bin"));
    }

    public void testAddFileRemoveFile() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createTxtFile(dir2, "fileOfUser2.txt", "Content of the file of the user 2...");
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser2.txt");
        assertTrue("dir1 does not contain fileOfUser2.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.txt");
        assertTrue("dir2 does not contain fileOfUser2.txt", f.exists());

        // AddDir user1 / RemoveFile user2
        FileUtils.createTxtFile(dir1, "fileOfUser1.txt", "Content of the file of the user 1...");
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

    public void testAddFileRemoveDir() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createDir(dir2 + File.separator + "dirOfUser2");
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "dirOfUser2");
        assertTrue("dir1 does not contain dirOfUser2", f.exists());
        f = new File(dir2 + File.separator + "dirOfUser2");
        assertTrue("dir2 does not contain dirOfUser2", f.exists());

        // AddDir user1 / RemoveDir user2
        FileUtils.createTxtFile(dir1, "fileOfUser1.txt", "Content of the file of the user 1...");
        FileUtils.remove(dir2 + File.separator + "dirOfUser2");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "fileOfUser1.txt");
        assertTrue("dirOfUser1 does not exist", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.txt");
        assertTrue("dirOfUser1 does not exist", f.exists());
        f = new File(dir1 + File.separator + "dirOfUser2");
        assertTrue(!f.exists());
        f = new File(dir2 + File.separator + "dirOfUser2");
        assertTrue(!f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser1.txt", dir2 + File.separator + "fileOfUser1.txt"));
    }
}
