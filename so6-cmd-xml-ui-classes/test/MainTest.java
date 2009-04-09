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

import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.tool.Main;


/**
 * @author molli
 */
public class MainTest extends TestCase {
    private String[] dirs;
    private String dummydir;

    public MainTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        super.setUp();
        dummydir = FileUtils.createTmpDir().getPath();
        dirs = new String[4];

        for (int i = 0; i < dirs.length; i++) {
            dirs[i] = FileUtils.createTmpDir().getPath();
        }
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSimple() throws Exception {
        for (int i = 0; i < dirs.length; i++) {
            FileUtils.createTxtFile(dirs[i], "toto" + i, "toto" + i);
        }

        String[] args = new String[dirs.length + 1];
        args[0] = dummydir;

        for (int i = 0; i < dirs.length; i++) {
            args[i + 1] = dirs[i];
        }

        Main.main(args);

        for (int i = 0; i < (dirs.length - 1); i++) {
            assertTrue("The directory are not the same", FileUtils.compareDir(dirs[i], dirs[i + 1]));
        }
    }

    public void testRedo() throws Exception {
        testSimple();
        FileUtils.createTxtFile(dirs[0], "tata", "tata");

        String[] args = new String[dirs.length + 1];
        args[0] = dummydir;

        for (int i = 0; i < dirs.length; i++) {
            args[i + 1] = dirs[i];
        }

        Main.main(args);

        for (int i = 0; i < (dirs.length - 1); i++) {
            assertTrue("The directory are not the same", FileUtils.compareDir(dirs[i], dirs[i + 1]));
        }
    }
}
