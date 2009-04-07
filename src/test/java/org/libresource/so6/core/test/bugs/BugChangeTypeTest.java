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
public class BugChangeTypeTest extends TestCase {
    private String projectName = "BugChangeTypeTest";
    private String dir;
    private String dir1;
    private WsConnection ws1;

    public BugChangeTypeTest(String name) {
        super(name);
    }

    public void testXmlSyncInDirTest() throws Exception {
        FileUtils.createDir(dir1 + File.separator + "test");
        FileUtils.createTxtFile(dir1 + "/test", "file", "<?xml version='1.0'?><root/>");
        ws1.commit("1");

        long prevTicket = ws1.getClient().getLastTicket();
        assertTrue("Invalide detection", prevTicket == 2);
        FileUtils.editTxtFile(dir1 + "/test/file", "<root><tutu/></root>");
        ws1.commit("2");

        long lastTicketTicket = ws1.getClient().getLastTicket();
        assertTrue("Invalide change type " + (lastTicketTicket - prevTicket), (lastTicketTicket - prevTicket) == 1);
    }

    public void testXmlSyncTest() throws Exception {
        FileUtils.createTxtFile(dir1, "file", "<?xml version='1.0'?><root/>");
        ws1.commit("1");

        long prevTicket = ws1.getClient().getLastTicket();
        assertTrue("Invalide detection", prevTicket == 1);
        FileUtils.editTxtFile(dir1 + "/file", "<root><tutu/></root>");
        ws1.commit("2");

        long lastTicketTicket = ws1.getClient().getLastTicket();
        assertTrue("Invalide change type " + (lastTicketTicket - prevTicket), (lastTicketTicket - prevTicket) == 1);
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 1, true);
        ws1 = ws[0];
        dir1 = ws1.getPath();
    }
}
