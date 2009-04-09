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
package org.libresource.so6.core.compress.test;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.command.Command;
import org.libresource.so6.core.command.NeutralCommand;
import org.libresource.so6.core.compress.CompressUtil;
import org.libresource.so6.core.engine.DBType;
import org.libresource.so6.core.engine.OpVectorFsImpl;
import org.libresource.so6.core.engine.PatchFile;
import org.libresource.so6.core.engine.util.FileUtils;

import java.io.File;
import java.io.FileWriter;

import java.util.ListIterator;


public class Main {
    public static void main(String[] args) throws Exception {
        File dir = FileUtils.createTmpDir();
        File opVectorDir = FileUtils.createTmpDir();
        File attachDir = FileUtils.createTmpDir();
        OpVectorFsImpl opVector = new OpVectorFsImpl(opVectorDir.getAbsolutePath());
        File execDir = FileUtils.createTmpDir();
        File execCompDir = FileUtils.createTmpDir();
        DBType dbType = new DBType(dir.getAbsolutePath() + "/dbType.txt", "");
        WsConnection wsc = new WsConnection("c:/replicaTest/original/.so6/1/so6.properties");
        File[] patchList = wsc.getAppliedPatch().list();
        int index = 1;

        for (int i = 0; i < patchList.length; i++) {
            PatchFile pf = new PatchFile(patchList[i].getAbsolutePath());
            pf.buildOpVector(opVector, attachDir.getAbsolutePath(), null);
        }

        // print history
        Command cmd = null;
        FileWriter fw1 = new FileWriter("c:/originalHist.txt");

        for (ListIterator i = opVector.getCommands(); i.hasNext();) {
            cmd = (Command) i.next();
            System.out.println(index);

            if (!(cmd instanceof NeutralCommand)) {
                cmd.execute(execDir.getAbsolutePath(), dbType);
                fw1.write(index++ + "\t" + cmd.getClass().getName().substring(cmd.getClass().getName().lastIndexOf(".") + 1) + "\t" + cmd.getPath() + "\t" +
                    cmd.getAttachement() + "\n");
            }
        }

        fw1.close();

        // compression
        CompressUtil.compressLog(opVector);

        // print new history
        FileWriter fw2 = new FileWriter("c:/compressedHist.txt");
        index = 1;

        for (ListIterator i = opVector.getCommands(); i.hasNext();) {
            cmd = (Command) i.next();

            if (!(cmd instanceof NeutralCommand)) {
                System.out.println(index);
                cmd.execute(execCompDir.getAbsolutePath(), dbType);
                fw2.write(index++ + "\t" + cmd.getClass().getName().substring(cmd.getClass().getName().lastIndexOf(".") + 1) + "\t" + cmd.getPath() + "\t" +
                    cmd.getAttachement() + "\n");
            }
        }

        fw2.close();
        System.out.println("Exec dir: " + execDir);
        System.out.println("Exec compression dir: " + execCompDir);
        System.out.println("Compare: " + FileUtils.compareDir(execDir.getAbsolutePath(), execCompDir.getAbsolutePath()));
    }
}
