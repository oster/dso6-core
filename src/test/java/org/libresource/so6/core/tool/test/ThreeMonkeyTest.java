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
package org.libresource.so6.core.tool.test;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;


/**
 * @author molli
 */
public class ThreeMonkeyTest extends TestCase {
    private String dir1;
    private String dir2;
    private String dir3;
    private WsConnection ws1;
    private WsConnection ws2;
    private WsConnection ws3;

    /**
     * Constructor for ThreeMonkeyTest.
     *
     * @param arg0
     */
    public ThreeMonkeyTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        String dir = FileUtils.createTmpDir().getPath();
        WsConnection[] ws = TestUtil.createWorkspace(dir, 3);
        ws1 = ws[0];
        ws2 = ws[1];
        ws3 = ws[2];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();
        dir3 = ws3.getPath();
    }

    public void test3monkey() throws Exception {
        FileUtils.createBinFile(dir1, "toto.class", "this is a binary file 1");
        FileUtils.createBinFile(dir2, "toto.class", "this is a binary file 2");
        FileUtils.createBinFile(dir3, "toto.class", "this is a binary file 3");
        ws1.commit("ws1 sync");
        ws2.update();
        ws2.commit("ws2 sync");
        ws3.update();
        ws3.commit("ws3 sync");
        ws2.update();
        ws1.update();
        assertTrue(FileUtils.compareDir(dir1, dir2));
        assertTrue(FileUtils.compareDir(dir1, dir3));
        assertTrue(FileUtils.compareDir(dir2, dir3));
        assertTrue(FileUtils.compareBinFile(new File(dir1 + File.separator + "toto.class"), new File(dir2 + File.separator + "toto.class")));
        assertTrue(FileUtils.compareBinFile(new File(dir2 + File.separator + "toto.class"), new File(dir3 + File.separator + "toto.class")));
    }
}
