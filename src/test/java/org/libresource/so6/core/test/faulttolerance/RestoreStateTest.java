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
package org.libresource.so6.core.test.faulttolerance;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;
import org.libresource.so6.core.test.util.TestUtil.CommitThread;
import org.libresource.so6.core.test.util.TestUtil.ThreadKiller;
import org.libresource.so6.core.test.util.TestUtil.UpdateThread;

import java.io.File;


/**
 * @author smack
 */
public class RestoreStateTest extends TestCase {
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;

    public RestoreStateTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2);
        ws1 = ws[0];
        ws2 = ws[1];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();
    }

    public void tearDown() throws Exception {
    }

    /**
     * UPDATE_CORRUPTION_PATCH_LOCAL_COPY UPDATE_CORRUPTION_PATCH_REF_COPY
     * UPDATE_CORRUPTION_SAVE_PATCH UPDATE_CORRUPTION_REMOVE_PATCH
     */
    public void testCorruptionUpdatePatchLocalCopy() throws Exception {
        for (int i = 0; i < 100; i++) {
            FileUtils.createDir(dir1 + File.separator + "file" + i);
        }

        FileUtils.createTxtFile(dir2, "LocalTxtFile", "a little message");
        FileUtils.createTxtFile(dir, "LocalTxtFile", "a little message");
        ws1.commit("first");

        UpdateThread update = new UpdateThread(ws2);
        update.setPriority(Thread.MIN_PRIORITY);

        ThreadKiller killer = new ThreadKiller(update, 0);
        update.start();
        update.join();
        assertTrue("Workspace is not corrupted ", ws2.isCorrupted());
        assertTrue("Workspace is not in the proper corrupted state (got=" + ws2.getCorruptedState() + " expected=" +
            WsConnection.UPDATE_CORRUPTION_PATCH_LOCAL_COPY + ")", ws2.getCorruptedState() == WsConnection.UPDATE_CORRUPTION_PATCH_LOCAL_COPY);

        // Test the restore part
        // check that the update doesn't work after corrutpion
        try {
            ws2.update();
            assertTrue("Corruption doesn't stop update", false);
        } catch (Exception e) {
            // Normal
        }

        // restore
        ws2.restore();

        //
        /*
         * String ws2Path = ws2.getPath(); FileUtils.remove(ws2Path); File f1 =
         * new File(ws2Path + ".tmp"); File f2 = new File(ws2Path);
         * f1.renameTo(f2); ws2 = new WsConnection(ws2Path + File.separator +
         * Workspace.SO6PREFIX + File.separator + WsConnection.SO6_WSC_FILE);
         */
        assertTrue("The locals commands have been removed !!!", new File(dir2, "LocalTxtFile").exists());
        assertTrue("The locals commands have been removed !!! (content)", FileUtils.compareTxtFile(dir + "/LocalTxtFile", dir2 + "/LocalTxtFile"));
        ws2.update();
        ws2.commit("commit local op");
        ws1.update();

        //
        assertTrue("The 2 ws didn't converged", FileUtils.compareDir(dir1, dir2));
    }

    public void testCorruptionUpdatePatchRefCopy() throws Exception {
        for (int i = 0; i < 100; i++) {
            FileUtils.createDir(dir1 + File.separator + "file" + i);
        }

        FileUtils.createTxtFile(dir2, "LocalTxtFile", "a little message");
        FileUtils.createTxtFile(dir, "LocalTxtFile", "a little message");
        ws1.commit("first");

        UpdateThread update = new UpdateThread(ws2);
        update.setPriority(Thread.MIN_PRIORITY);

        ThreadKiller killer = new ThreadKiller(update, 1);
        update.start();
        update.join();
        assertTrue("Workspace is not corrupted ", ws2.isCorrupted());
        assertTrue("Workspace is not in the proper corrupted state (got=" + ws2.getCorruptedState() + " expected=" +
            WsConnection.UPDATE_CORRUPTION_PATCH_REF_COPY + ")", ws2.getCorruptedState() == WsConnection.UPDATE_CORRUPTION_PATCH_REF_COPY);

        // Test the restore part
        // check that the update doesn't work after corrutpion
        try {
            ws2.update();
            assertTrue("Corruption doesn't stop update", false);
        } catch (Exception e) {
            // Normal
        }

        // restore
        ws2.restore();
        System.out.println(ws2.getNs());

        //
        /*
         * String ws2Path = ws2.getPath(); FileUtils.remove(ws2Path); File f1 =
         * new File(ws2Path + ".tmp"); File f2 = new File(ws2Path);
         * f1.renameTo(f2); ws2 = new WsConnection(ws2Path + File.separator +
         * Workspace.SO6PREFIX + File.separator + WsConnection.SO6_WSC_FILE);
         */
        assertTrue("The locals commands have been removed !!!", new File(dir2, "LocalTxtFile").exists());
        assertTrue("The locals commands have been removed !!! (content)", FileUtils.compareTxtFile(dir + "/LocalTxtFile", dir2 + "/LocalTxtFile"));
        ws2.update();
        ws2.commit("commit local op");
        ws1.update();

        //
        assertTrue("The 2 ws didn't converged", FileUtils.compareDir(dir1, dir2));
    }

    public void testCorruptionUpdateSavePatch() throws Exception {
        for (int i = 0; i < 100; i++) {
            FileUtils.createDir(dir1 + File.separator + "file" + i);
        }

        FileUtils.createTxtFile(dir2, "LocalTxtFile", "a little message");
        FileUtils.createTxtFile(dir, "LocalTxtFile", "a little message");
        ws1.commit("first");

        UpdateThread update = new UpdateThread(ws2);
        update.setPriority(Thread.MIN_PRIORITY);

        ThreadKiller killer = new ThreadKiller(update, 2);
        update.start();
        update.join();
        assertTrue("Workspace is not corrupted ", ws2.isCorrupted());
        assertTrue("Workspace is not in the proper corrupted state (got=" + ws2.getCorruptedState() + " expected=" + WsConnection.UPDATE_CORRUPTION_SAVE_PATCH +
            ")", ws2.getCorruptedState() == WsConnection.UPDATE_CORRUPTION_SAVE_PATCH);

        // Test the restore part
        // check that the update doesn't work after corrutpion
        try {
            ws2.update();
            assertTrue("Corruption doesn't stop update", false);
        } catch (Exception e) {
            // Normal
        }

        // restore
        ws2.restore();
        assertTrue("The locals commands have been removed !!!", new File(dir2, "LocalTxtFile").exists());
        assertTrue("The locals commands have been removed !!! (content)", FileUtils.compareTxtFile(dir + "/LocalTxtFile", dir2 + "/LocalTxtFile"));
        ws2.update();
        ws2.commit("commit local op");
        ws1.update();

        //
        assertTrue("The 2 ws didn't converged", FileUtils.compareDir(dir1, dir2));
    }

    public void testCorruptionUpdateRemovePatch() throws Exception {
        for (int i = 0; i < 100; i++) {
            FileUtils.createDir(dir1 + File.separator + "file" + i);
        }

        FileUtils.createTxtFile(dir2, "LocalTxtFile", "a little message");
        FileUtils.createTxtFile(dir, "LocalTxtFile", "a little message");
        ws1.commit("first");

        UpdateThread update = new UpdateThread(ws2);
        update.setPriority(Thread.MIN_PRIORITY);

        ThreadKiller killer = new ThreadKiller(update, 3);
        update.start();
        update.join();
        assertTrue("Workspace is not corrupted ", ws2.isCorrupted());
        assertTrue("Workspace is not in the proper corrupted state (got=" + ws2.getCorruptedState() + " expected=" +
            WsConnection.UPDATE_CORRUPTION_REMOVE_PATCH + ")", ws2.getCorruptedState() == WsConnection.UPDATE_CORRUPTION_REMOVE_PATCH);

        // Test the restore part
        // check that the update doesn't work after corrutpion
        try {
            ws2.update();
            assertTrue("Corruption doesn't stop update", false);
        } catch (Exception e) {
            // Normal
        }

        // restore
        ws2.restore();
        assertTrue("The locals commands have been removed !!!", new File(dir2, "LocalTxtFile").exists());
        assertTrue("The locals commands have been removed !!! (content)", FileUtils.compareTxtFile(dir + "/LocalTxtFile", dir2 + "/LocalTxtFile"));
        ws2.update();
        ws2.commit("commit local op");
        ws1.update();

        //
        assertTrue("The 2 ws didn't converged", FileUtils.compareDir(dir1, dir2));
    }

    /**
     * COMMIT_CORRUPTION_PATCH_REF_COPY COMMIT_CORRUPTION_SAVE_PATCH
     * COMMIT_CORRUPTION_UPDATE_RECEIVED_TICKET
     * COMMIT_CORRUPTION_UPDATE_LOCAL_DBTYPE
     */
    public void testCorruptionCommitPatchRefCopy() throws Exception {
        for (int i = 0; i < 100; i++) {
            FileUtils.createDir(dir1 + File.separator + "file" + i);
        }

        CommitThread commit = new CommitThread(ws1);
        commit.setPriority(Thread.MIN_PRIORITY);

        ThreadKiller killer = new ThreadKiller(commit, 0);
        commit.start();
        commit.join();
        assertTrue("Workspace is not corrupted ", ws1.isCorrupted());
        assertTrue("Workspace is not in the proper corrupted state (got=" + ws1.getCorruptedState() + " expected=" +
            WsConnection.COMMIT_CORRUPTION_PATCH_REF_COPY + ")", ws1.getCorruptedState() == WsConnection.COMMIT_CORRUPTION_PATCH_REF_COPY);

        // Test the restore part
        // check that the update doesn't work after corrutpion
        try {
            ws1.commit("try to commit with corrupted ws");
            assertTrue("Corruption doesn't stop commit", false);
        } catch (Exception e) {
            // Normal
        }

        // restore
        ws1.restore();
        ws2.update();
        assertTrue("The 2 ws didn't converged", FileUtils.compareDir(dir1, dir2));
    }

    public void testCorruptionCommitSavePatch() throws Exception {
        for (int i = 0; i < 100; i++) {
            FileUtils.createDir(dir1 + File.separator + "file" + i);
        }

        CommitThread commit = new CommitThread(ws1);
        commit.setPriority(Thread.MIN_PRIORITY);

        ThreadKiller killer = new ThreadKiller(commit, 1);
        commit.start();
        commit.join();
        assertTrue("Workspace is not corrupted ", ws1.isCorrupted());
        assertTrue("Workspace is not in the proper corrupted state (got=" + ws1.getCorruptedState() + " expected=" + WsConnection.COMMIT_CORRUPTION_SAVE_PATCH +
            ")", ws1.getCorruptedState() == WsConnection.COMMIT_CORRUPTION_SAVE_PATCH);

        // Test the restore part
        // check that the update doesn't work after corrutpion
        try {
            ws1.commit("try to commit with corrupted ws");
            assertTrue("Corruption doesn't stop commit", false);
        } catch (Exception e) {
            // Normal
        }

        // restore
        ws1.restore();

        // check that the local op are still there
        assertTrue("no more local op in commit " + new File(dir1).list().length, new File(dir1).list().length == 101);

        // send local op
        ws1.commit("from restore");

        //
        ws2.update();
        assertTrue("The 2 ws didn't converged", FileUtils.compareDir(dir1, dir2));
    }

    public void testCorruptionCommitUpdateReceivedTicket()
        throws Exception {
        for (int i = 0; i < 100; i++) {
            FileUtils.createDir(dir1 + File.separator + "file" + i);
        }

        CommitThread commit = new CommitThread(ws1);
        commit.setPriority(Thread.MIN_PRIORITY);

        ThreadKiller killer = new ThreadKiller(commit, 2);
        commit.start();
        commit.join();
        assertTrue("Workspace is not corrupted ", ws1.isCorrupted());
        assertTrue("Workspace is not in the proper corrupted state (got=" + ws1.getCorruptedState() + " expected=" +
            WsConnection.COMMIT_CORRUPTION_UPDATE_RECEIVED_TICKET + ")", ws1.getCorruptedState() == WsConnection.COMMIT_CORRUPTION_UPDATE_RECEIVED_TICKET);

        // Test the restore part
        // check that the update doesn't work after corrutpion
        try {
            ws1.commit("try to commit with corrupted ws");
            assertTrue("Corruption doesn't stop commit", false);
        } catch (Exception e) {
            // Normal
        }

        // restore
        ws1.restore();

        // check that the local op are still there
        assertTrue("no more local op in commit " + new File(dir1).list().length, new File(dir1).list().length == 101);

        // send local op
        ws1.commit("commit after restore");

        //
        ws2.update();
        assertTrue("The 2 ws didn't converged", FileUtils.compareDir(dir1, dir2));
    }

    public void testCorruptionCommitUpdateLocalDBType()
        throws Exception {
        for (int i = 0; i < 100; i++) {
            FileUtils.createDir(dir1 + File.separator + "file" + i);
        }

        CommitThread commit = new CommitThread(ws1);
        ThreadKiller killer = new ThreadKiller(commit, 3);
        commit.start();
        commit.join();
        assertTrue("Workspace is not corrupted ", ws1.isCorrupted());
        assertTrue("Workspace is not in the proper corrupted state (got=" + ws1.getCorruptedState() + " expected=" +
            WsConnection.COMMIT_CORRUPTION_UPDATE_LOCAL_DBTYPE + ")", ws1.getCorruptedState() == WsConnection.COMMIT_CORRUPTION_UPDATE_LOCAL_DBTYPE);

        // Test the restore part
        // check that the update doesn't work after corrutpion
        try {
            ws1.commit("try to commit with corrupted ws");
            assertTrue("Corruption doesn't stop commit", false);
        } catch (Exception e) {
            // Normal
        }

        // restore
        ws1.restore();

        // check that the local op are still there
        assertTrue("no more local op in commit " + new File(dir1).list().length, new File(dir1).list().length == 101);

        // send local op
        ws1.commit("commit after restore");

        //
        ws2.update();
        assertTrue("The 2 ws didn't converged", FileUtils.compareDir(dir1, dir2));
    }

    public void testRestore() throws Exception {
        for (int i = 0; i < 10; i++) {
            FileUtils.createDir(dir1 + File.separator + i);

            for (int j = 0; j < 10; j++) {
                FileUtils.createDir(dir1 + File.separator + i + File.separator + "file" + j);
            }
        }

        ws1.commit("1");
        ws1.cleanLocalOp();
        ws1.setProperty(WsConnection.CORRUPTION_STATE, "" + WsConnection.UPDATE_CORRUPTION_PATCH_LOCAL_COPY);
        ws1.restore();
    }
}
