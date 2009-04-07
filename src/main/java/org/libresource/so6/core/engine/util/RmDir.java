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
package org.libresource.so6.core.engine.util;

import java.io.File;


public class RmDir {
    private File path;

    /**
     * Instanciate the command that allow a recursive delete
     *
     * @param path
     */
    public RmDir(String path) {
        this.path = new File(path);
    }

    /**
     * Execute that command
     *
     * @throws java.io.IOException
     */
    public void execute() throws java.io.IOException {
        if (path.exists()) {
            delrec(path);
        }
    }

    private void delrec(File f) throws java.io.IOException {
        if (f.isDirectory()) {
            File[] fs = f.listFiles();

            for (int i = 0; i < fs.length; i++) {
                delrec(fs[i]);
            }
        }

        if (!(f.delete())) {
            throw new java.io.IOException("cannot delete " + f.getPath());
        }
    }
}
