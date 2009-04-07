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
package org.libresource.so6.core.test.scenari;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;

import java.util.Enumeration;
import java.util.Vector;


public class ConflictResolutionTest extends TestCase {
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

    /**
     * @param arg0
     */
    public ConflictResolutionTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2);
        ws1 = ws[0];
        ws2 = ws[1];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();
        tmpdir = dir + File.separator + "tmpdir";
        common1 = dir1 + File.separator + common;
        common2 = dir2 + File.separator + common;
        expectedPath = tmpdir + File.separator + expectedName;

        File d = new File(tmpdir);

        if (!(d.mkdirs())) {
            throw new Exception("cannot create:" + d.getPath());
        }
    }

    public void testSimpleConflictResolutionTest() throws Exception {
        FileUtils.createTxtFile(dir1, "file.txt", "line1\nline2\nline3\nline4\nline5\n");
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "file.txt", dir2 + File.separator + "file.txt"));
        FileUtils.editTxtFile(dir1 + File.separator + "file.txt", "line1-1\nline2\nline3\nline4\nline5\n");
        FileUtils.editTxtFile(dir2 + File.separator + "file.txt", "line1-2\nline2\nline3\nline4\nline5\n");
        ws1.updateAndCommit(); // send his changes
        ws2.updateAndCommit(); // detect conflict
        ws1.updateAndCommit(); // integrate conflict
        assertTrue("contents differ step1", FileUtils.compareTxtFile(dir1 + File.separator + "file.txt", dir2 + File.separator + "file.txt"));

        // conflict inside
        FileUtils.editTxtFile(dir2 + File.separator + "file.txt", "line1-solved\nline2\nline3\nline4\nline5\n");
        ws2.updateAndCommit(); // send conflict resolution

        //--
        //   	Workspace ws1 = new Workspace(channelId, dir1);
        //   	FileParser fp = new FileParser(ws1);
        //   	Vector localOp = new Vector();
        //   	ws1.getLog().extractLocalOp(localOp, fp);
        //   	Logger.getLogger("test.ConflictResolutionTest").info("ws1 localop:
        // "+listSOAPLog(localOp));
        // --
        ws1.updateAndCommit(); // integrate resolution
        assertTrue("contents differ step2", FileUtils.compareTxtFile(dir1 + File.separator + "file.txt", dir2 + File.separator + "file.txt"));
        this.testNoop(ws1);
        this.testNoop(ws2);
    }

    private void testNoop(WsConnection ws) throws Exception {
        long ns = ws.getNs();
        ws.updateAndCommit();
        assertEquals(ns, ws.getNs());
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
}
