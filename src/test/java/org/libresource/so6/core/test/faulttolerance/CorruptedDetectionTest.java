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
public class CorruptedDetectionTest extends TestCase {
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;

    public CorruptedDetectionTest(String name) {
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
     * UPDATE_CORRUPTION_PATCH_REF_COPY UPDATE_CORRUPTION_SAVE_PATCH
     * UPDATE_CORRUPTION_REMOVE_PATCH
     */
    public void testCorruptionUpdatePatchRefCopy() throws Exception {
        for (int i = 0; i < 100; i++) {
            FileUtils.createDir(dir1 + File.separator + "file" + i);
        }

        ws1.commit("first");

        UpdateThread update = new UpdateThread(ws2);
        update.setPriority(Thread.MIN_PRIORITY);

        ThreadKiller killer = new ThreadKiller(update, 1);
        update.start();
        update.join();
        assertTrue("Workspace is not corrupted ", ws2.isCorrupted());
        assertTrue("Workspace is not in the proper corrupted state (got=" + ws2.getCorruptedState() + " expected=" +
            WsConnection.UPDATE_CORRUPTION_PATCH_REF_COPY + ")", ws2.getCorruptedState() == WsConnection.UPDATE_CORRUPTION_PATCH_REF_COPY);
    }

    public void testCorruptionUpdateSavePatch() throws Exception {
        for (int i = 0; i < 100; i++) {
            FileUtils.createDir(dir1 + File.separator + "file" + i);
        }

        ws1.commit("first");

        UpdateThread update = new UpdateThread(ws2);
        update.setPriority(Thread.MIN_PRIORITY);

        ThreadKiller killer = new ThreadKiller(update, 2);
        update.start();
        update.join();
        assertTrue("Workspace is not corrupted ", ws2.isCorrupted());
        assertTrue("Workspace is not in the proper corrupted state (got=" + ws2.getCorruptedState() + " expected=" + WsConnection.UPDATE_CORRUPTION_SAVE_PATCH +
            ")", ws2.getCorruptedState() == WsConnection.UPDATE_CORRUPTION_SAVE_PATCH);
    }

    public void testCorruptionUpdateRemovePatch() throws Exception {
        for (int i = 0; i < 100; i++) {
            FileUtils.createDir(dir1 + File.separator + "file" + i);
        }

        ws1.commit("first");

        UpdateThread update = new UpdateThread(ws2);
        update.setPriority(Thread.MIN_PRIORITY);

        ThreadKiller killer = new ThreadKiller(update, 3);
        update.start();
        update.join();
        assertTrue("Workspace is not corrupted ", ws2.isCorrupted());
        assertTrue("Workspace is not in the proper corrupted state (got=" + ws2.getCorruptedState() + " expected=" +
            WsConnection.UPDATE_CORRUPTION_REMOVE_PATCH + ")", ws2.getCorruptedState() == WsConnection.UPDATE_CORRUPTION_REMOVE_PATCH);
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
    }

    public void testCorruptionCommitUpdateLocalDBType()
        throws Exception {
        for (int i = 0; i < 100; i++) {
            FileUtils.createDir(dir1 + File.separator + "file" + i);
        }

        CommitThread commit = new CommitThread(ws1);
        commit.setPriority(Thread.MIN_PRIORITY);

        ThreadKiller killer = new ThreadKiller(commit, 3);
        commit.start();
        commit.join();
        assertTrue("Workspace is not corrupted ", ws1.isCorrupted());
        assertTrue("Workspace is not in the proper corrupted state (got=" + ws1.getCorruptedState() + " expected=" +
            WsConnection.COMMIT_CORRUPTION_UPDATE_LOCAL_DBTYPE + ")", ws1.getCorruptedState() == WsConnection.COMMIT_CORRUPTION_UPDATE_LOCAL_DBTYPE);
    }
}
