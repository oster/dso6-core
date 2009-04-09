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
package org.libresource.so6.core.exec;

import org.libresource.so6.core.engine.DBType;
import org.libresource.so6.core.engine.PatchFile;
import org.libresource.so6.core.engine.util.FileUtils;

import java.io.File;


/**
 * The <code>ApplyPatch</code> class is used to apply a set of patch on a
 * local directory
 *
 * @author Smack
 * @version 1.0, 26/05/04
 * @see org.libresource.so6.core.exec.Main
 * @since JDK1.4
 */
public class ApplyPatch {
    /**
     * Instantiate and execute the applying patch process
     *
     * @param args
     *            <ul>
     *            <li>Path where we want to apply the patch set</li>
     *            <li>List of patch file path in the proper order</li>
     *            </ul>
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: dirToPatch patch1 patch2 ...");
            System.err.println(" (1) dirToPatch: base path to patch");
            System.err.println(" (2) patch(x): file path of the patch");
        } else {
            File[] patchList = new File[args.length - 1];

            for (int i = 0; i < patchList.length; i++) {
                patchList[i] = new File(args[i + 1]);

                if (!patchList[i].exists()) {
                    System.err.println("File " + args[i + 1] + " does not exist... Please set a valide path");
                }
            }

            File dbTypeFile = new File(FileUtils.createTmpDir(), "dbType.data");
            DBType dbType = new DBType(dbTypeFile.getPath(), "");

            for (int i = 0; i < patchList.length; i++) {
                PatchFile pf = new PatchFile(patchList[i].getPath());
                pf.patch(args[0], dbType);
            }
        }
    }
}
