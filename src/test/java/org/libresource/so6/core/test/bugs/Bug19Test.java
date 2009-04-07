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
package org.libresource.so6.core.test.bugs;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;

import java.util.Enumeration;
import java.util.Vector;


/*
 * This test illustrates reported bug #19
 *
 */
public class Bug19Test extends TestCase {
    private String projectName = "Bug19Test";
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;

    public Bug19Test(String name) {
        super(name);
    }

    public void testSimpleBug19FileToDirTest() throws Exception {
        FileUtils.createTxtFile(dir1, "file1", "some contents\n");
        ws1.updateAndCommit();
        FileUtils.remove(dir1 + File.separator + "file1");
        FileUtils.createDir(dir1 + File.separator + "file1");
        ws1.updateAndCommit();

        /*
         * File logDir = new File(System.getProperty("java.io.tmpdir") + "/log_" +
         * System.currentTimeMillis()); logDir.mkdirs();
         * FileUtils.saveLogToFile(logDir.getAbsolutePath() + "/bug19.txt", new
         * LogFsImpl(dir1 + "/.so6/test/LOG"));
         */
        assertTrue("file1 must be a dir", FileUtils.fileExists(dir1 + File.separator + "file1") && FileUtils.isDirectory(dir1 + File.separator + "file1"));
        testNoop(dir1, ws1);

        // shouldn't fail !!!
        ws2.updateAndCommit();
    }

    public void testSimpleBug19DirToFileTest() throws Exception {
        FileUtils.createDir(dir1 + File.separator + "file1");
        ws1.updateAndCommit();
        FileUtils.remove(dir1 + File.separator + "file1");
        FileUtils.createDir(dir1 + File.separator + "file1bis"); // simulates
                                                                 // rename(file1,
                                                                 // file1bis)

        FileUtils.createTxtFile(dir1, "file1", "some contents\n");
        ws1.updateAndCommit();
        assertTrue("file1 must be a file", FileUtils.fileExists(dir1 + File.separator + "file1") && FileUtils.isFile(dir1 + File.separator + "file1"));
        assertTrue("file1bis must be a directory", FileUtils.fileExists(dir1 + File.separator + "file1") && FileUtils.isFile(dir1 + File.separator + "file1"));
        testNoop(dir1, ws1);

        // shouldn't fail !!!
        ws2.updateAndCommit();
    }

    private void testNoop(String dir, WsConnection ws)
        throws Exception {
        long lastTicket = ws.getNs();
        ws.updateAndCommit();
        assertTrue("TestNoOp: " + lastTicket + " -> " + ws.getNs(), ws.getNs() == lastTicket);

        /*
         * long time = System.currentTimeMillis(); ws.updateAndCommit();
         *
         * SynchroManager reportManager = new SynchroManager(ws); SynchroItem
         * syncItem = null; int nbSync = 0; for (Iterator i =
         * reportManager.getIterator(); i.hasNext();) { SynchroItem tmp =
         * (SynchroItem) i.next(); if (tmp.getDate() > time) { syncItem = tmp; }
         * nbSync++; } if (syncItem != null) { File logDir = new
         * File(System.getProperty("java.io.tmpdir") + "/noop_log_" +
         * System.currentTimeMillis()); logDir.mkdirs();
         * FileUtils.saveLogToFile(logDir.getAbsolutePath() + "/bug19.txt", new
         * LogFsImpl(dir1 + "/.so6/test/LOG")); assertTrue( "(log contain more
         * than 0 elt) nbSync:" + nbSync + " To:" + syncItem.getTo() + "
         * Remote:" + syncItem.getRemote() + " From:" + syncItem.getFrom(),
         * (syncItem.getTo() - syncItem.getRemote()) == 0); }
         */
    }

    private String listSOAPLog(Vector log) {
        StringBuffer sb = new StringBuffer();
        sb.append("Log={");

        for (Enumeration e = log.elements(); e.hasMoreElements();) {
            sb.append(e.nextElement().toString() + ";");
        }

        sb.append("}");

        return sb.toString();
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
}
