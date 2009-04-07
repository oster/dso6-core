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


/*
 * This test illustrates reported bug with case problem on Windows/Unix
 * cooperation
 */
public class BugCaseTest extends TestCase {
    private String projectName = "BugCaseTest";
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;

    public BugCaseTest(String name) {
        super(name);
    }

    public void testSimpleBugCaseConflictTest() throws Exception {
        FileUtils.createTxtFile(dir1, "file", "some contents 1\n");
        FileUtils.createTxtFile(dir2, "File", "some contents 2\n");
        ws1.updateAndCommit();

        // shouldn't fail !!! -> but will on windows
        ws2.updateAndCommit();
    }

    public void testCaseTest() throws Exception {
        FileUtils.createTxtFile(dir1, "file", "some contents 1\n");
        ws1.updateAndCommit();
        FileUtils.createTxtFile(dir1, "File", "some contents 1\n");
        FileUtils.remove(dir1 + File.separator + "file");
        ws1.updateAndCommit();

        // shouldn't fail !!!
        ws2.updateAndCommit();
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2);
        ws1 = ws[0];
        ws2 = ws[1];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();
    }
}
