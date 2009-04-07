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


public class AddDirTest extends TestCase {
    private String clientName = System.getProperty("clientName");
    private String dir;
    private String dir1;
    private String dir2;
    private String tmpdir;
    private WsConnection ws1;
    private WsConnection ws2;

    public AddDirTest(String name) {
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
    }

    public void tearDown() throws Exception {
    }

    //////////////////////////////////////////
    public void testAddDirAddDirNoConflict() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createDir(dir1 + File.separator + "dirOfUser1");
        FileUtils.createDir(dir2 + File.separator + "dirOfUser2");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "dirOfUser1");
        assertTrue("dirOfUser1 does not exist", f.exists());
        f = new File(dir2 + File.separator + "dirOfUser1");
        assertTrue("dirOfUser1 does not exist", f.exists());
        f = new File(dir2 + File.separator + "dirOfUser2");
        assertTrue("dirOfUser2 does not exist", f.exists());

        // Last Synchro to ckeck the convergence
        ws1.updateAndCommit();

        // Last check
        f = new File(dir1 + File.separator + "dirOfUser2");
        assertTrue("dirOfUser2 does not exist", f.exists());
    }

    public void testAddDirAddDirWithConflict() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createDir(dir1 + File.separator + "ConflictDir");
        FileUtils.createDir(dir2 + File.separator + "ConflictDir");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "ConflictDir");
        assertTrue("ConflictDir does not exist", f.exists());
        f = new File(dir2 + File.separator + "ConflictDir");
        assertTrue("ConflictDir does not exist", f.exists());

        //  !!!!! Il manque des verifications !!!!!
        // Check if it didn't create something else?
    }

    public void testAddDirAddFileNoConflict() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createDir(dir1 + File.separator + "dirOfUser1");
        FileUtils.createTxtFile(dir2, "fileOfUser2.txt", "Content of the file of the user 2...");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "dirOfUser1");
        assertTrue("dirOfUser2 does not exist", f.exists());
        f = new File(dir2 + File.separator + "dirOfUser1");
        assertTrue("dirOfUser1 does not exist", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.txt");
        assertTrue("dir2 does not contain fileOfUser2.txt", f.exists());

        // Last Synchro to ckeck the convergence
        ws1.updateAndCommit();
        f = new File(dir1 + File.separator + "fileOfUser2.txt");
        assertTrue("dir1 does not contain fileOfUser2.txt", f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser2.txt", dir2 + File.separator + "fileOfUser2.txt"));
    }

    public void testAddDirAddBinaryFileNoConflict() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createDir(dir1 + File.separator + "dirOfUser1");
        FileUtils.createBinFile(dir2, "fileOfUser2.bin", "Content of the file of the user 2...");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "dirOfUser1");
        assertTrue("dirOfUser2 does not exist", f.exists());
        f = new File(dir2 + File.separator + "dirOfUser1");
        assertTrue("dirOfUser1 does not exist", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.bin");
        assertTrue("dir2 does not contain fileOfUser2.bin", f.exists());

        // Last Synchro to ckeck the convergence
        ws1.updateAndCommit();
        f = new File(dir1 + File.separator + "fileOfUser2.bin");
        assertTrue("dir1 does not contain fileOfUser2.bin", f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser2.bin", dir2 + File.separator + "fileOfUser2.bin"));
    }

    public void testAddDirAddFileWithConflict() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createDir(dir1 + File.separator + "ConflictName.txt");
        FileUtils.createTxtFile(dir2, "ConflictName.txt", "Content of the file of the user 2...");

        // Synchronize user1 / user2
        ws1.updateAndCommit();

        // 	Logger.getLogger("test.out").info("contents after sync1
        // dir1:"+FileUtils.list(dir1));
        // 	Logger.getLogger("test.out").info("contents after sync1
        // dir2:"+FileUtils.list(dir2));
        ws2.updateAndCommit();

        // 	Logger.getLogger("test.out").info("contents after sync2
        // dir1:"+FileUtils.list(dir1));
        // 	Logger.getLogger("test.out").info("contents after sync2
        // dir2:"+FileUtils.list(dir2));
        ws1.updateAndCommit();

        // 	Logger.getLogger("test.out").info("contents after sync3
        // dir1:"+FileUtils.list(dir1));
        // 	Logger.getLogger("test.out").info("contents after sync3
        // dir2:"+FileUtils.list(dir2));
        // Check if the result is correct
        f = new File(dir1 + File.separator + "ConflictName.txt");
        assertTrue("dir1 does not contain directory ConflictName.txt", f.exists() && f.isDirectory());
        f = new File(dir1 + File.separator + "ConflictName.txt#1");
        assertTrue("dir1 does not contain file ConflictName.txt#1", f.exists() && f.isFile());
        f = new File(dir2 + File.separator + "ConflictName.txt");
        assertTrue("dir2 does not contain directory ConflictName", f.exists() && f.isDirectory());
        f = new File(dir2 + File.separator + "ConflictName.txt#1");
        assertTrue("dir2 does not contain file ConflictName.txt#1", f.exists() && f.isFile());

        // Check the content of the files
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "ConflictName.txt#1", dir2 + File.separator + "ConflictName.txt#1"));
    }

    public void testAddDirAddBinaryFileWithConflict() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createDir(dir1 + File.separator + "ConflictName.bin");
        FileUtils.createBinFile(dir2, "ConflictName.bin", "Content of the file of the user 2...");

        // Synchronize user1 / user2
        ws1.updateAndCommit();

        // 	Logger.getLogger("test.out").info("contents after sync1
        // dir1:"+FileUtils.list(dir1));
        // 	Logger.getLogger("test.out").info("contents after sync1
        // dir2:"+FileUtils.list(dir2));
        ws2.updateAndCommit();

        // 	Logger.getLogger("test.out").info("contents after sync2
        // dir1:"+FileUtils.list(dir1));
        // 	Logger.getLogger("test.out").info("contents after sync2
        // dir2:"+FileUtils.list(dir2));
        ws1.updateAndCommit();

        // 	Logger.getLogger("test.out").info("contents after sync3
        // dir1:"+FileUtils.list(dir1));
        // 	Logger.getLogger("test.out").info("contents after sync3
        // dir2:"+FileUtils.list(dir2));
        // Check if the result is correct
        f = new File(dir1 + File.separator + "ConflictName.bin");
        assertTrue("dir1 does not contain directory ConflictName.bin", f.exists() && f.isDirectory());
        f = new File(dir1 + File.separator + "ConflictName.bin#1");
        assertTrue("dir1 does not contain file ConflictName.bin#1", f.exists() && f.isFile());
        f = new File(dir2 + File.separator + "ConflictName.bin");
        assertTrue("dir2 does not contain directory ConflictName", f.exists() && f.isDirectory());
        f = new File(dir2 + File.separator + "ConflictName.bin#1");
        assertTrue("dir2 does not contain file ConflictName.bin#1", f.exists() && f.isFile());

        // Check the content of the files
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "ConflictName.bin#1", dir2 + File.separator + "ConflictName.bin#1"));
    }

    public void testAddDirUpdateFile() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createTxtFile(dir2, "fileOfUser2.txt", "line1\n line2\n line3\n");
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser2.txt");
        assertTrue("dir1 does not contain fileOfUser2.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.txt");
        assertTrue("dir2 does not contain fileOfUser2.txt", f.exists());

        // AddDir user1 / ChangeFile user2
        FileUtils.createDir(dir1 + File.separator + "dirOfUser1");
        FileUtils.editTxtFile(dir2 + File.separator + "fileOfUser2.txt", "line1\n line3\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "dirOfUser1");
        assertTrue("dirOfUser1 does not exist", f.exists());
        f = new File(dir2 + File.separator + "dirOfUser1");
        assertTrue("dirOfUser1 does not exist", f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser2.txt", dir2 + File.separator + "fileOfUser2.txt"));
    }

    public void testAddDirUpdateBinaryFile() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createBinFile(dir2, "fileOfUser2.bin", "Content of the file of the user 2...");
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser2.bin");
        assertTrue("dir1 does not contain fileOfUser2.bin", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.bin");
        assertTrue("dir2 does not contain fileOfUser2.bin", f.exists());
        assertTrue("first sync but not the same bin file",
            FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser2.bin", dir2 + File.separator + "fileOfUser2.bin"));

        // AddDir user1 / ChangeFile user2
        FileUtils.createDir(dir1 + File.separator + "dirOfUser1");
        FileUtils.editBinFile(dir2 + File.separator + "fileOfUser2.bin", "The new content of the file");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "dirOfUser1");
        assertTrue("dirOfUser1 does not exist", f.exists());
        f = new File(dir2 + File.separator + "dirOfUser1");
        assertTrue("dirOfUser1 does not exist", f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser2.bin", dir2 + File.separator + "fileOfUser2.bin"));
    }

    public void testAddDirRemoveFile() throws Exception {
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
        FileUtils.createDir(dir1 + File.separator + "dirOfUser1");
        FileUtils.remove(dir2 + File.separator + "fileOfUser2.txt");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "dirOfUser1");
        assertTrue("dirOfUser1 does not exist", f.exists());
        f = new File(dir2 + File.separator + "dirOfUser1");
        assertTrue("dirOfUser1 does not exist", f.exists());
        f = new File(dir1 + File.separator + "fileOfUser2.txt");
        assertTrue(!f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.txt");
        assertTrue(!f.exists());
    }

    public void testAddDirRemoveDir() throws Exception {
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
        FileUtils.createDir(dir1 + File.separator + "dirOfUser1");
        FileUtils.remove(dir2 + File.separator + "dirOfUser2");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "dirOfUser1");
        assertTrue("dirOfUser1 does not exist", f.exists());
        f = new File(dir2 + File.separator + "dirOfUser1");
        assertTrue("dirOfUser1 does not exist", f.exists());
        f = new File(dir1 + File.separator + "dirOfUser2");
        assertTrue(!f.exists());
        f = new File(dir2 + File.separator + "dirOfUser2");
        assertTrue(!f.exists());
    }

    //////////////////////////////////////////
}
