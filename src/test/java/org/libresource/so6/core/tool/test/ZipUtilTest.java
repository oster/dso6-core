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
/*
 * Created on 10 f�vr. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.libresource.so6.core.tool.test;

import junit.framework.TestCase;

import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.engine.util.ZipUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


/**
 * @author molli
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ZipUtilTest extends TestCase {
    private String dir4;
    private String dir;
    private String dir1;
    private String dir2;
    private String dir3;

    /**
     * Constructor for ZipUtilTest.
     *
     * @param arg0
     */
    public ZipUtilTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(ZipUtilTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        dir = FileUtils.createTmpDir().getPath();

        File f = new File(dir);
        f.mkdir();
        dir1 = dir + File.separator + "dir1";
        f = new File(dir1);
        f.mkdir();
        dir2 = dir + File.separator + "dir2";
        f = new File(dir2);
        f.mkdir();
        dir3 = dir + File.separator + "dir3";
        f = new File(dir3);
        f.mkdir();
        dir4 = dir + File.separator + "dir4";
        f = new File(dir4);
        f.mkdir();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testZip() throws Exception {
        FileUtils.createTxtFile(dir1, "a", "toto");
        FileUtils.createDir(dir1 + File.separator + "b");
        FileUtils.createTxtFile(dir1 + File.separator + "b", "a", "titi");

        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(dir3 + File.separator + "out.zip"));
        ZipUtil.zip(zos, dir1);
        zos.close();
        ZipUtil.unzip(dir3 + File.separator + "out.zip", dir2);
        assertTrue(FileUtils.compareDir(dir1, dir2));

        ZipInputStream zin = new ZipInputStream(new FileInputStream(dir3 + File.separator + "out.zip"));
        ZipUtil.unzip(zin, dir4);
        assertTrue(FileUtils.compareDir(dir1, dir4));
    }

    public void testZipOnlyDir() throws Exception {
        FileUtils.createTxtFile(dir1, "a", "toto");
        FileUtils.createDir(dir1 + File.separator + "b");

        PipedOutputStream pos = new PipedOutputStream();
        final ZipOutputStream zos = new ZipOutputStream(pos);
        PipedInputStream pis = new PipedInputStream(pos);
        final ZipInputStream zis = new ZipInputStream(pis);
        Thread zipper = new Thread() {
                public void run() {
                    try {
                        ZipUtil.zip(zos, dir1);
                        zos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

        Thread unzipper = new Thread() {
                public void run() {
                    try {
                        ZipUtil.unzip(zis, dir4);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

        zipper.start();
        unzipper.start();
        zipper.join();
        unzipper.join();
        assertTrue(FileUtils.compareDir(dir1, dir4));
    }

    public void testZipUnzip() throws Exception {
        FileUtils.createTxtFile(dir1, "a", "toto");
        FileUtils.createDir(dir1 + File.separator + "b");
        FileUtils.createTxtFile(dir1 + File.separator + "b", "a", "titi");

        PipedOutputStream pos = new PipedOutputStream();
        final ZipOutputStream zos = new ZipOutputStream(pos);
        PipedInputStream pis = new PipedInputStream(pos);
        final ZipInputStream zis = new ZipInputStream(pis);
        Thread zipper = new Thread() {
                public void run() {
                    try {
                        ZipUtil.zip(zos, dir1);
                        zos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

        Thread unzipper = new Thread() {
                public void run() {
                    try {
                        ZipUtil.unzip(zis, dir4);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

        zipper.start();
        unzipper.start();
        zipper.join();
        unzipper.join();
        assertTrue(FileUtils.compareDir(dir1, dir4));
    }
}
