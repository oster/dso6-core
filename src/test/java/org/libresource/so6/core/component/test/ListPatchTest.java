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
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;


public class ListPatchTest extends TestCase {
    private String clientName = System.getProperty("clientName");
    private String dir;
    private String dir1;
    private String dir2;
    private String tmpdir;
    private WsConnection ws1;

    public ListPatchTest(String name) {
        super(name);
    }

    //	public static Test suite() {
    //		TestSuite suite = new TestSuite();
    //		suite.addTest(new AddFileTest("testAddFileAddFileWithConflict"));
    //		return suite;
    //	}
    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection[] ws = TestUtil.createWorkspace(dir, 1);
        ws1 = ws[0];
        dir1 = ws1.getPath();
        tmpdir = dir + File.separator + "tmpdir";

        File d = new File(tmpdir);

        if (!(d.mkdirs())) {
            throw new Exception("cannot create:" + d.getPath());
        }
    }

    //////////////////////////////////////////
    public void testAddFileAddFileNoConflict() throws Exception {
        // Tmp var
        File f = null;

        // AddFile user1 / AddFile user2
        FileUtils.createTxtFile(dir1, "fileOfUser1.txt", "Content of the file of the user 1");
        ws1.commit("a");
        FileUtils.createTxtFile(dir1, "fileOfUser2.txt", "Content of the file of the user 2\nContent of the file of the user 2");
        ws1.commit("b");
        FileUtils.createTxtFile(dir1, "fileOfUser3.txt",
            "Content of the file of the user 3\nContent of the file of the user 3\nContent of the file of the user 3");
        ws1.commit("c");
        FileUtils.createTxtFile(dir1, "fileOfUser4.txt",
            "Content of the file of the user 4\nContent of the file of the user 4\nContent of the file of the user 4\nContent of the file of the user 4");
        ws1.commit("d");

        long[][] listPatch = ws1.getClient().listPatch();
        assertTrue("invalid list patch length : expected 4 got " + listPatch.length, listPatch.length == 4);

        for (int i = 0; i < listPatch.length; i++) {
            for (int j = 0; j < listPatch[0].length; j++) {
                assertTrue("invalid list patch content : expected " + (i + 1) + " got " + listPatch[i][0],
                    (listPatch[i][0] == listPatch[i][1]) && (listPatch[i][0] == (i + 1)));
            }
        }
    }
}
