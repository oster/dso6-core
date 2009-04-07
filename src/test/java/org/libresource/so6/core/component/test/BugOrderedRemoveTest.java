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
package org.libresource.so6.core.component.test;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.engine.util.RmDir;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;


public class BugOrderedRemoveTest extends TestCase {
    private String clientName = System.getProperty("clientName");
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;

    public BugOrderedRemoveTest(String name) {
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

    public void testOrderedRemove() throws Exception {
        // create 50 files at replica1 in a subdirectory
        File dir = new File(dir1 + File.separator + "dir1/dir1.1/dir1.1.1/dir1.1.1.1");
        dir.mkdirs();
        dir = new File(dir1 + File.separator + "dir1/dir1.2/dir1.2.1/dir1.2.1.1");
        dir.mkdirs();
        dir = new File(dir1 + File.separator + "dir1/dir1.3/dir1.3.1/dir1.3.1.1");
        dir.mkdirs();
        dir = new File(dir1 + File.separator + "dir2/dir1.1/dir1.1.1/dir1.1.1.1");
        dir.mkdirs();
        dir = new File(dir1 + File.separator + "dir2/dir1.2/dir1.2.1/dir1.2.1.1");
        dir.mkdirs();
        dir = new File(dir1 + File.separator + "dir2/dir1.3/dir1.3.1/dir1.3.1.1");
        dir.mkdirs();

        // synchronize replica 1
        ws1.updateAndCommit();

        RmDir rm = new RmDir(dir1 + File.separator + "dir1");
        rm.execute();
        rm = new RmDir(dir1 + File.separator + "dir2");
        rm.execute();

        // synchronize replica 1
        ws1.updateAndCommit();

        // synchronize replica 2
        ws2.updateAndCommit();
        dir = new File(dir1 + File.separator + "dir1");
        assertTrue("The directory dir1 shouldn't exist", !dir.exists());
        dir = new File(dir1 + File.separator + "dir2");
        assertTrue("The directory dir2 shouldn't exist", !dir.exists());
        dir = new File(dir2 + File.separator + "dir1");
        assertTrue("The directory dir1 shouldn't exist", !dir.exists());
        dir = new File(dir2 + File.separator + "dir2");
        assertTrue("The directory dir2 shouldn't exist", !dir.exists());
    }
}
