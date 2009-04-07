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
import org.libresource.so6.core.engine.FileParser;
import org.libresource.so6.core.engine.OpVectorFsImpl;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.exec.PartialCommit;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;

import java.util.Vector;


public class PartialCommitTest extends TestCase {
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;

    public PartialCommitTest(String name) {
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

    //////////////////////////////////////////
    public void testSimplePartialCommit() throws Exception {
        FileUtils.createDir(dir1 + "/a/b/c");
        FileUtils.createTxtFile(dir1 + "/a/b/c", "fileUser1.txt", "File of the user 1");
        FileUtils.createTxtFile(dir1 + "/a/b/c", "fileUser2.txt", "File of the user 11");

        // make filter
        Vector filter = new Vector();
        ws1.cleanLocalOp();

        OpVectorFsImpl opV = new OpVectorFsImpl(FileUtils.createTmpDir().getPath());
        FileParser fp = new FileParser(ws1);
        fp.compute(opV);

        //
        filter.add("a/b/c/fileUser1.txt");
        filter.add("a");
        ws1.setFilter(filter);

        try {
            ws1.computeNbCommandToSend(opV);
        } catch (Exception e) {
            // normal
        }

        //
        filter.add("a/b/c/fileUser1.txt");
        filter.add("a/b/c");
        ws1.setFilter(filter);

        try {
            ws1.computeNbCommandToSend(opV);
        } catch (Exception e) {
            // normal
        }

        //
        filter.add("a/b/c/fileUser1.txt");
        filter.add("a");
        filter.add("a/b/c");
        ws1.setFilter(filter);

        try {
            ws1.computeNbCommandToSend(opV);
        } catch (Exception e) {
            // normal
        }

        //
        filter.add("a/b/c/fileUser1.txt");
        filter.add("a");
        filter.add("a/b");
        ws1.setFilter(filter);

        try {
            ws1.computeNbCommandToSend(opV);
        } catch (Exception e) {
            // normal
        }

        //
        filter.add("a/b/c/fileUser1.txt");
        filter.add("a");
        filter.add("a/b");
        filter.add("a/b/c");
        ws1.setFilter(filter);
        assertTrue("wrong command number expected 4/5 got " + ws1.computeNbCommandToSend(opV) + "/" + opV.size(), ws1.computeNbCommandToSend(opV) == 4);
    }

    public void testPartialCommit() throws Exception {
        FileUtils.createDir(dir1 + "/a/b/c");
        FileUtils.createTxtFile(dir1 + "/a/b/c", "fileUser1.txt", "File of the user 1");
        FileUtils.createTxtFile(dir1 + "/a/b/c", "fileUser2.txt", "File of the user 11");
        FileUtils.createTxtFile(dir1, "filter", "a\na/b\na/b/c\na/b/c/fileUser1.txt");

        // partial commit of ws1
        PartialCommit pc = new PartialCommit(ws1.getDataPath() + File.separator + WsConnection.SO6_WSC_FILE, "first partial commit", dir1 + "/filter");
        pc.execute();
        ws2.update();
        assertTrue("No partial convergence", new File(dir2 + File.separator + "a/b/c/fileUser1.txt").exists());
        assertTrue("No partial convergence", !new File(dir2 + File.separator + "a/b/c/fileUser2.txt").exists());
        assertTrue("No partial convergence", !new File(dir2 + File.separator + "filter").exists());
    }

    //////////////////////////////////////////
}
