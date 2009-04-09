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
import org.libresource.so6.core.client.ClientI;
import org.libresource.so6.core.client.dummy.DummyClient;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;

import java.util.Enumeration;
import java.util.Vector;


public class MProjectTest extends TestCase {
    private String dir1;
    private String dir2;
    private String dir3;
    private WsConnection ws1;
    private WsConnection ws2;
    private WsConnection ws2prime;
    private WsConnection ws3;

    public MProjectTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        long time = System.currentTimeMillis();
        dir1 = FileUtils.createTmpDir().getPath();
        dir2 = FileUtils.createTmpDir().getPath();
        dir3 = FileUtils.createTmpDir().getPath();

        ClientI client1 = new DummyClient();
        ClientI client2 = new DummyClient();
        ws1 = TestUtil.createWs(dir1, "", "ws1", client1);
        ws2 = TestUtil.createWs(dir2, "normal", "ws2", client1);
        ws2prime = TestUtil.createWs(dir2, "prime", "ws2prime", client2);
        ws3 = TestUtil.createWs(dir3, "", "ws3", client2);
    }

    private String listSOAPLog(Vector log) {
        StringBuffer sb = new StringBuffer();

        for (Enumeration e = log.elements(); e.hasMoreElements();) {
            sb.append(e.nextElement().toString() + ";");
        }

        return sb.toString();
    }

    public void testDir1Dir3() throws Exception {
        String msg = "yo momo";
        FileUtils.createTxtFile(dir1, "toto.txt", msg);
        ws1.updateAndCommit();
        ws2.updateAndCommit();
        ws2prime.updateAndCommit();
        ws3.updateAndCommit();

        File f = new File(dir3 + File.separator + "toto.txt");
        assertTrue("dir3 does not contain toto.txt", f.exists());

        // Check the content of the files
        assertTrue(FileUtils.compareTxtFile(dir1 + File.separator + "toto.txt", dir3 + File.separator + "toto.txt"));
    }
}
