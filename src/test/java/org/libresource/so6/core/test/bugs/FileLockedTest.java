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

import org.libresource.so6.core.engine.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;


/*
 * This test illustrates reported bug #19
 *
 */
public class FileLockedTest extends TestCase {
    private String projectName = "FileLockedTest";
    private String dir;

    public FileLockedTest(String name) {
        super(name);
    }

    public void testLockCheck() throws Exception {
        String file = "fileTest";
        FileUtils.createTxtFile(dir, file, "test");
        assertTrue("The file " + file + " shouldn't be locked", !FileUtils.isLocked(dir + "/" + file));

        FileInputStream fis = new FileInputStream(dir + "/" + file);
        fis.read();
        assertTrue("The file is not locked !!! " + file, !new File(dir + "/" + file).delete());
        assertTrue("The file " + file + " should be locked", FileUtils.isLocked(dir + "/" + file));
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();
    }
}
