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
package org.libresource.so6.core.compress;

import org.libresource.so6.core.engine.OpVectorFsImpl;
import org.libresource.so6.core.engine.PatchFile;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;


/**
 * @author smack
 */
public class CompressUtil {
    /**
     * Compress an OpVectorFsImpl
     *
     * @param log
     * @throws Exception
     */
    public static void compressLog(OpVectorFsImpl opVector)
        throws Exception {
        int size = opVector.size();
        CompressEngine ce = new CompressEngine();
        int i;
        int j;

        //System.out.println("Compress " + size);
        for (i = 1; i < size; i++) {
            // i is the indice of the op that we want to move
            //System.out.print(".");
            //System.out.println("av: "+arrayLog[i - 1]+" : "+ arrayLog[i]);
            CompressEngine.TranspResult tr = ce.transp(opVector.getCommand(i - 1), opVector.getCommand(i));
            opVector.update(tr.getCmd1(), i - 1);
            opVector.update(tr.getCmd2(), i);

            //System.out.println("ap: "+arrayLog[i - 1]+" : "+ arrayLog[i]);
            j = i;

            while ((tr.isKeepGoing()) && (j > 1)) {
                j--;

                //System.out.println("up av: "+arrayLog[j - 1]+" : "+
                // arrayLog[j]);
                tr = ce.transp(opVector.getCommand(j - 1), opVector.getCommand(j));
                opVector.update(tr.getCmd1(), j - 1);
                opVector.update(tr.getCmd2(), j);
            }
        }

        //System.out.println();
    }

    /**
     * Concat two patch file in a third one.
     *
     * @param patch1
     * @param patch2
     * @param resultPatch
     * @param baseWorkingDir
     *            tmp dir.
     * @throws Exception
     */
    public static void mergePatch(File patch1, File patch2, File resultPatch, File baseWorkingDir)
        throws Exception {
        File attachDir = new File(baseWorkingDir, "ATTACH");
        File cmdsDir = new File(baseWorkingDir, "CMDS");

        if (!attachDir.exists() && !attachDir.mkdir()) {
            throw new Exception("Unable to create directory " + attachDir.getPath());
        }

        if (!cmdsDir.exists() && !cmdsDir.mkdir()) {
            throw new Exception("Unable to create directory " + attachDir.getPath());
        }

        //
        OpVectorFsImpl opv = new OpVectorFsImpl(cmdsDir.getPath());
        PatchFile pf = new PatchFile(patch1.getPath());
        pf.buildOpVector(opv, attachDir.getPath(), null);
        pf = new PatchFile(patch2.getPath());
        pf.buildOpVector(opv, attachDir.getPath(), null);

        // 
        compressLog(opv);

        OutputStreamWriter osw = new FileWriter(resultPatch);
        PatchFile.makePatch(opv, osw, null, opv.getFromTicket(), opv.getToTicket(), "compress", null);
        osw.close();
    }
}
