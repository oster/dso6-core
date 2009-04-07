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


/*
 * This test illustrates reported bug #19
 *
 */
public class Bug111Test extends TestCase {
    private String projectName = "Bug111Test";
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;

    public Bug111Test(String name) {
        super(name);
    }

    public void testSimpleBug111Test() throws Exception {
        FileUtils.createTxtFile(dir1, "file1", "Line 1\nLine 2\nLine 3\nLine 4\nLine 5\n\n\n");
        ws1.updateAndCommit();

        // del 1 line
        FileUtils.editTxtFile(dir1 + "/file1", "Line 1\nLine 2\nLine 3\nLine 5\n\n\n");
        ws1.updateAndCommit();

        // del 1 line
        FileUtils.editTxtFile(dir1 + "/file1", "Line 1\nLine 2\nLine 3\n\n\n");
        ws1.updateAndCommit();

        // shouldn't fail !!!
        ws2.updateAndCommit();

        // Check convergence
        assertTrue("No convergence", FileUtils.compareTxtFile(dir1 + "/file1", dir2 + "/file1"));
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
