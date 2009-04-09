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
import org.libresource.so6.core.exec.Rename;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;

import java.util.logging.Logger;


public class RenameTest extends TestCase {
    private String dir;
    private String dir1;
    private String dir2;
    private String wsPath1;
    private String wsPath2;
    private WsConnection ws1;
    private WsConnection ws2;

    public RenameTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2);
        ws1 = ws[0];
        ws2 = ws[1];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();
        wsPath1 = ws1.getDataPath() + File.separator + "so6.properties";
        wsPath2 = ws2.getDataPath() + File.separator + "so6.properties";
    }

    //////////////////////////////////////////
    public void testSynchronize() throws Exception {
        FileUtils.createTxtFile(dir1, "fileUser1.txt", "File of the user 1");
        FileUtils.createTxtFile(dir2, "fileUser2.txt", "File of the user 2");

        //FileUtils.createFile(dir3,"fileUser3.txt","File of the user 3");
        Logger.getLogger("test.SimpleSyncTest").info("Synchronize");

        // Synchronize all
        Logger.getLogger("test.SimpleSyncTest").info("Synch: " + dir1);
        ws1.commit("sync 1");
        Logger.getLogger("test.SimpleSyncTest").info("Synch: " + dir2);
        ws2.update();
        ws2.commit("sync 2");
        ws1.update();
        assertTrue("Check first synchro before rename", FileUtils.compareDir(dir1, dir2));
        System.out.println("ws1: " + ws1.getNs());
        System.out.println("ws2: " + ws2.getNs());

        Rename rename = new Rename(ws1.getDataPath() + File.separator + "so6.properties", "fileUser1.txt", "1.txt");
        ws1 = new WsConnection(wsPath1);
        ws2 = new WsConnection(wsPath2);
        ws1.getClient();
        ws2.getClient();
        ws1.update();
        ws2.update();
        ws1 = new WsConnection(wsPath1);
        ws2 = new WsConnection(wsPath2);
        assertTrue("No convergence after rename", FileUtils.compareDir(dir1, dir2));
    }

    //////////////////////////////////////////
}
