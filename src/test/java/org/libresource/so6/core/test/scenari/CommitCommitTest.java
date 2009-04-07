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


public class CommitCommitTest extends TestCase {
    private String dir;
    private String dir1;
    private WsConnection ws1;

    public CommitCommitTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 1);
        ws1 = ws[0];
        dir1 = ws1.getPath();
    }

    //////////////////////////////////////////
    public void testSynchronize() throws Exception {
        FileUtils.createDir(dir1 + "/a/b/c");
        FileUtils.createTxtFile(dir1 + "/a/b/c", "fileUser1.txt", "File of the user 1");

        // Synchronize all
        long lastTicket = -1;
        ws1.commit("sync 1");
        lastTicket = ws1.getNs();
        ws1.commit("sync 2");
        ws1.commit("sync 3");
        assertTrue("A commit after a commit still send op", lastTicket == ws1.getNs());
    }

    //////////////////////////////////////////
}
