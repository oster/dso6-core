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


public class UpdateBinaryFileTest extends TestCase {
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

    //      use this to order test
    //	public static Test suite() {
    //		TestSuite suite = new TestSuite();
    //		suite.addTest(new
    // UpdateBinaryFileTest("testUpdateBinaryFileUpdateBinaryFileWithConflict"));
    //		return suite;
    //	}
    public UpdateBinaryFileTest(String name) {
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
    public void testUpdateBinaryFileUpdateBinaryFile()
        throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createBinFile(dir1, "fileOfUser1.bin", "Content of the file of the user 1...");
        FileUtils.createBinFile(dir2, "fileOfUser2.bin", "Content of the file of the user 2...");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser1.bin");
        assertTrue("dir1 does not contain fileOfUser1.bin", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.bin");
        assertTrue("dir2 does not contain fileOfUser1.bin", f.exists());
        f = new File(dir1 + File.separator + "fileOfUser2.bin");
        assertTrue("dir1 does not contain fileOfUser2.bin", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.bin");
        assertTrue("dir2 does not contain fileOfUser2.bin", f.exists());

        // ChangeFile user1 / ChangeFile user2
        FileUtils.editBinFile(dir1 + File.separator + "fileOfUser1.bin", "The new content of the file of the user1");
        FileUtils.editBinFile(dir2 + File.separator + "fileOfUser2.bin", "The new content of the file of the user2");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser1.bin", dir2 + File.separator + "fileOfUser1.bin"));
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser2.bin", dir2 + File.separator + "fileOfUser2.bin"));
    }

    public void testUpdateBinaryFileUpdateBinaryFileWithConflict()
        throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createBinFile(dir1, "conflict.bin", "Content of the binary file...");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "conflict.bin");
        assertTrue("dir1 does not contain conflict.bin", f.exists());
        f = new File(dir2 + File.separator + "conflict.bin");
        assertTrue("dir2 does not contain conflict.bin", f.exists());

        // ChangeFile user1 / ChangeFile user2
        FileUtils.editBinFile(dir1 + File.separator + "conflict.bin", "The new content of the file of the user1");
        FileUtils.editBinFile(dir2 + File.separator + "conflict.bin", "The new content of the file of the user2");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "conflict.bin");
        assertTrue("dir1 does not contain conflict.bin", f.exists());
        f = new File(dir2 + File.separator + "conflict.bin");
        assertTrue("dir2 does not contain conflict.bin", f.exists());
        f = new File(dir1 + File.separator + "conflict.bin#2");
        assertTrue("dir1 does not contain conflict.bin#3", f.exists());
        f = new File(dir2 + File.separator + "conflict.bin#2");
        assertTrue("dir2 does not contain conflict.bin#2", f.exists());
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "conflict.bin", dir2 + File.separator + "conflict.bin"));
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "conflict.bin#2", dir2 + File.separator + "conflict.bin#2"));
    }

    public void testUpdateBinaryFileUpdateFile() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createBinFile(dir1, "fileOfUser1.bin", "Content of the file of the user 1...");
        FileUtils.createTxtFile(dir2, "fileOfUser2.txt", "line1\n line2\n line3\n");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser1.bin");
        assertTrue("dir1 does not contain fileOfUser1.bin", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.bin");
        assertTrue("dir2 does not contain fileOfUser1.bin", f.exists());
        f = new File(dir1 + File.separator + "fileOfUser2.txt");
        assertTrue("dir1 does not contain fileOfUser2.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.txt");
        assertTrue("dir2 does not contain fileOfUser2.txt", f.exists());

        // ChangeFile user1 / ChangeFile user2
        FileUtils.editBinFile(dir1 + File.separator + "fileOfUser1.bin", "The new content of the file of the user1");
        FileUtils.editTxtFile(dir2 + File.separator + "fileOfUser2.txt", "line1\n line3\n");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser1.bin", dir2 + File.separator + "fileOfUser1.bin"));
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser2.txt", dir2 + File.separator + "fileOfUser2.txt"));
    }

    //     public void testUpdateFileUpdateFileWithConflict() throws Exception {
    // 	// Tmp var
    // 	
    // 	File f = null;
    // 	// Set the context
    // 	FileUtils.createFile(dir1,"fileOfUser1","Content of the file of the user
    // 1...");
    // 	sync = new Synchronize(dir1,projectName);
    // 	
    // 	sync = new Synchronize(dir2,projectName);
    // 		
    // 	// Check the context
    // 	f=new File(dir1+File.separator+"fileOfUser1");
    // 	assertTrue("dir1 does not contain fileOfUser1",f.exists());
    // 	f=new File(dir2+File.separator+"fileOfUser1");
    // 	assertTrue("dir2 does not contain fileOfUser1",f.exists());
    // 	// ChangeFile user1 / ChangeFile user2
    // 	FileUtils.editFile(dir1+File.separator+"fileOfUser1","The new content of
    // the file of the user1");
    // 	FileUtils.editFile(dir2+File.separator+"fileOfUser1","The new conflicting
    // content of the file of the user2");
    // 	// Synchronize user1 / user2
    // 	sync = new Synchronize(dir1,projectName);
    // 	
    // 	Logger.getLogger("test.out").info("dir1 contents:"+FileUtils.list(dir1));
    // 	Logger.getLogger("test.out").info("dir2 contents:"+FileUtils.list(dir2));
    // 	sync = new Synchronize(dir2,projectName);
    // 		//<--plante
    // 	Logger.getLogger("test.out").info("dir1 contents:"+FileUtils.list(dir1));
    // 	Logger.getLogger("test.out").info("dir2 contents:"+FileUtils.list(dir2));
    // 	sync = new Synchronize(dir1,projectName);
    // 	
    // 	Logger.getLogger("test.out").info("dir1 contents:"+FileUtils.list(dir1));
    // 	Logger.getLogger("test.out").info("dir2 contents:"+FileUtils.list(dir2));
    // 	// Check if the result is correct
    // 	f=new File(dir1+File.separator+"fileOfUser1");
    // 	assertTrue("dir1 does not contain fileOfUser1",f.exists());
    // 	f=new File(dir2+File.separator+"fileOfUser1");
    // 	assertTrue("dir2 does not contain fileOfUser1",f.exists());
    // 	f=new File(dir1+File.separator+"fileOfUser1#3");
    // 	assertTrue("dir1 does not contain fileOfUser1#3",f.exists());
    // 	f=new File(dir2+File.separator+"fileOfUser1#3");
    // 	assertTrue("dir2 does not contain fileOfUser1#3",f.exists());
    // 	// Check contents
    // 	assertTrue(FileUtils.compareFile( dir1+File.separator+"fileOfUser1" ,
    // 					  dir2+File.separator+"fileOfUser1" ));
    // 	assertTrue(FileUtils.compareFile( dir1+File.separator+"fileOfUser1#3" ,
    // 					  dir2+File.separator+"fileOfUser1#3" ));
    //     }
    public void testUpdateBinaryFileAddFile() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createBinFile(dir1, "fileOfUser1.bin", "Content of the file of the user 1...\néééééééééééééééééééééééé");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser1.bin");
        assertTrue("dir1 does not contain fileOfUser1.bin", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.bin");
        assertTrue("dir2 does not contain fileOfUser1.bin", f.exists());

        // AddFile user1 / Change the file for user2
        FileUtils.editBinFile(dir1 + File.separator + "fileOfUser1.bin",
            "new Content of the file fileOfUser1... 123456789123546789123456789123456789\néééééééééééééééééééééééé");
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
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "fileOfUser2.txt", dir2 + File.separator + "fileOfUser2.txt"));
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser1.bin", dir2 + File.separator + "fileOfUser1.bin"));
    }

    public void testUpdateBinaryFileAddBinaryFile() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createBinFile(dir1, "fileOfUser1.bin", "Content of the file of the user 1...\néééééééééééééééééééééééé");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser1.bin");
        assertTrue("dir1 does not contain fileOfUser1.bin", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.bin");
        assertTrue("dir2 does not contain fileOfUser1.bin", f.exists());

        // AddFile user1 / Change the file for user2
        FileUtils.editBinFile(dir1 + File.separator + "fileOfUser1.bin",
            "new Content of the file fileOfUser1... 123456789123546789123456789123456789\néééééééééééééééééééééééé");
        FileUtils.createBinFile(dir2, "fileOfUser2.bin", "Content of the new file of the user 2...");

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
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser1.bin", dir2 + File.separator + "fileOfUser1.bin"));
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser2.bin", dir2 + File.separator + "fileOfUser2.bin"));
    }

    public void testUpdateBinaryFileAddDir() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createBinFile(dir1, "fileOfUser1.bin", "Content of the file of the user 1...\néééééééééééééééééééééééé");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser1.bin");
        assertTrue("dir1 does not contain fileOfUser1.bin", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.bin");
        assertTrue("dir2 does not contain fileOfUser1.bin", f.exists());
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser1.bin", dir2 + File.separator + "fileOfUser1.bin"));

        // AddFile user1 / Change the file for user2
        FileUtils.editBinFile(dir1 + File.separator + "fileOfUser1.bin", "new Content of the file fileOfUser1\néééééééééééééééééééééééé");
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
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser1.bin", dir2 + File.separator + "fileOfUser1.bin"));
    }

    public void testUpdateBinaryFileRemoveFile() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createBinFile(dir1, "fileOfUser1.bin", "Content of the file of the user 1...\néééééééééééééééééééééééé");
        FileUtils.createTxtFile(dir2, "fileOfUser2.txt", "Content of the file of the user 2...");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser1.bin");
        assertTrue("dir1 does not contain fileOfUser1", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.bin");
        assertTrue("dir2 does not contain fileOfUser1.bin", f.exists());
        f = new File(dir1 + File.separator + "fileOfUser2.txt");
        assertTrue("dir1 does not contain fileOfUser2.txt", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.txt");
        assertTrue("dir2 does not contain fileOfUser2.txt", f.exists());

        // UpdateFile user1 / RemoveFile user2
        FileUtils.editBinFile(dir1 + File.separator + "fileOfUser1.bin",
            "new Content of the file fileOfUser1.bin\néééééééééééééééééééééééé");
        FileUtils.remove(dir2 + File.separator + "fileOfUser2.txt");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "fileOfUser1.bin");
        assertTrue("fileOfUser1.bin does not exist", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.bin");
        assertTrue("fileOfUser1.bin does not exist", f.exists());
        f = new File(dir1 + File.separator + "fileOfUser2.txt");
        assertTrue(!f.exists());
        f = new File(dir2 + File.separator + "fileOfUser2.txt");
        assertTrue(!f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser1.bin", dir2 + File.separator + "fileOfUser1.bin"));
    }

    public void testUpdateBinaryFileRemoveFileWithConflict()
        throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createBinFile(dir1, "fileOfUser1.bin", "Content of the file of the user 1...\néééééééééééééééééééééééé");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser1.bin");
        assertTrue("dir1 does not contain fileOfUser1.bin", f.exists() && f.isFile());
        f = new File(dir2 + File.separator + "fileOfUser1.bin");
        assertTrue("dir2 does not contain fileOfUser1.bin", f.exists() && f.isFile());

        // UpdateFile user1 / RemoveFile user2
        FileUtils.editBinFile(dir1 + File.separator + "fileOfUser1.bin", "new Content of the file fileOfUser1.bin");
        FileUtils.remove(dir2 + File.separator + "fileOfUser1.bin");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "fileOfUser1.bin");
        assertTrue("fileOfUser1.bin does not exist", !f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.bin");
        assertTrue("fileOfUser1.bin does not exist", !f.exists());
    }

    public void testUpdateBinaryFileRemoveDir() throws Exception {
        // Tmp var
        File f = null;

        // Set the context
        FileUtils.createBinFile(dir1, "fileOfUser1.bin", "Content of the file of the user 1...\néééééééééééééééééééééééé");
        FileUtils.createDir(dir2 + File.separator + "dirOfUser2");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check the context
        f = new File(dir1 + File.separator + "fileOfUser1.bin");
        assertTrue("dir1 does not contain fileOfUser1.bin", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.bin");
        assertTrue("dir2 does not contain fileOfUser1.bin", f.exists());
        f = new File(dir1 + File.separator + "dirOfUser2");
        assertTrue("dir1 does not contain dirOfUser2", f.exists());
        f = new File(dir2 + File.separator + "dirOfUser2");
        assertTrue("dir2 does not contain dirOfUser2", f.exists());

        // AddDir user1 / RemoveDir user2
        FileUtils.editBinFile(dir1 + File.separator + "fileOfUser1.bin", "new Content of the file fileOfUser1\néééééééééééééééééééééééé");
        FileUtils.remove(dir2 + File.separator + "dirOfUser2");

        // Synchronize user1 / user2
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws1.updateAndCommit();

        // Check if the result is correct
        f = new File(dir1 + File.separator + "fileOfUser1.bin");
        assertTrue("fileOfUser1.bin does not exist", f.exists());
        f = new File(dir2 + File.separator + "fileOfUser1.bin");
        assertTrue("fileOfUser1.bin does not exist", f.exists());
        f = new File(dir1 + File.separator + "dirOfUser2");
        assertTrue(!f.exists());
        f = new File(dir2 + File.separator + "dirOfUser2");
        assertTrue(!f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareBinFile(dir1 + File.separator + "fileOfUser1.bin", dir2 + File.separator + "fileOfUser1.bin"));
    }
}
