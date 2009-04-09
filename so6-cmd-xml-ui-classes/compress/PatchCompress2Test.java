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

import fr.loria.ecoo.so6.xml.node.ElementNode;

import junit.framework.TestCase;

import org.libresource.so6.core.command.Command;
import org.libresource.so6.core.command.NeutralCommand;
import org.libresource.so6.core.command.fs.AddBinaryFile;
import org.libresource.so6.core.command.fs.AddDir;
import org.libresource.so6.core.command.fs.AddFile;
import org.libresource.so6.core.command.fs.Rename;
import org.libresource.so6.core.command.fs.UpdateBinaryFile;
import org.libresource.so6.core.command.text.AddBlock;
import org.libresource.so6.core.command.text.AddTxtFile;
import org.libresource.so6.core.command.text.DelBlock;
import org.libresource.so6.core.command.xml.AddXmlFile;
import org.libresource.so6.core.command.xml.InsertNode;
import org.libresource.so6.core.compress.CompressUtil;
import org.libresource.so6.core.engine.OpVectorFsImpl;
import org.libresource.so6.core.engine.util.FileUtils;

import java.io.FileReader;
import java.io.LineNumberReader;

import java.util.ArrayList;
import java.util.ListIterator;


public class PatchCompress2Test extends TestCase {
    private String projectName = "CompressTest2";
    private String dir;
    private String opVectorDir;
    private OpVectorFsImpl opVector;

    public PatchCompress2Test(String name) {
        super(name);
    }

    public void testCompressHistoryTest() throws Exception {
        System.out.println("History:");

        for (ListIterator i = opVector.getCommands(); i.hasNext();) {
            Command cmd = (Command) i.next();
            System.out.println(cmd);

            if (cmd instanceof AddFile) {
                FileReader fr = new FileReader(cmd.getAttachement());
                LineNumberReader lnr = new LineNumberReader(fr);
                String line = null;

                while ((line = lnr.readLine()) != null) {
                    System.out.println(line);
                }

                lnr.close();
            }
        }

        System.out.println("Start compress");
        CompressUtil.compressLog(opVector);
        System.out.println("End compress");
        System.out.println("New history:");

        for (ListIterator i = opVector.getCommands(); i.hasNext();) {
            Command cmd = (Command) i.next();

            if (!(cmd instanceof NeutralCommand)) {
                System.out.println(cmd);

                if (cmd instanceof AddFile) {
                    FileReader fr = new FileReader(cmd.getAttachement());
                    LineNumberReader lnr = new LineNumberReader(fr);
                    String line = null;

                    while ((line = lnr.readLine()) != null) {
                        System.out.println(line);
                    }

                    lnr.close();
                }
            }
        }
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();
        opVectorDir = FileUtils.createTmpDir().getPath();
        opVector = new OpVectorFsImpl(opVectorDir);

        //
        String wsName = "test compression";
        String txtFileName = "test.txt";
        String binFileName = "test.bin";
        String xmlFileName = "test.xml";
        String txtFileName_new = "test_new.txt";
        String binFileName_new = "test_new.bin";
        String xmlFileName_new = "test_new.xml";
        String dirName1_new = "aa";
        String dirName1 = "a";
        String dirName1_1 = "a/b";
        String dirName1_1_1 = "a/b/c";

        //
        ArrayList linesToAdd = new ArrayList();
        ArrayList linesToRemove = new ArrayList();

        //
        FileUtils.createTxtFile(dir, txtFileName, "Un petit test\nAvec plusieurs\nLignes...");
        FileUtils.createTxtFile(dir, binFileName, "Un petit fichier binaire");
        FileUtils.createTxtFile(dir, binFileName_new, "Un autre petit fichier binaire");
        FileUtils.createXmlFile("root", dir + "/" + xmlFileName);

        //
        opVector.add(new AddTxtFile(1, txtFileName, wsName, System.currentTimeMillis(), false, dir + "/" + txtFileName));
        opVector.add(new AddBinaryFile(2, binFileName, wsName, System.currentTimeMillis(), false, dir + "/" + binFileName));
        opVector.add(new AddXmlFile(3, xmlFileName, wsName, System.currentTimeMillis(), dir + "/" + xmlFileName));
        opVector.add(new AddDir(4, dirName1, wsName, System.currentTimeMillis(), false, null));
        opVector.add(new AddDir(5, dirName1_1, wsName, System.currentTimeMillis(), false, null));
        opVector.add(new AddDir(6, dirName1_1_1, wsName, System.currentTimeMillis(), false, null));
        opVector.add(new Rename(7, dirName1, wsName, System.currentTimeMillis(), false, null, dirName1_new));

        // update txt
        linesToAdd.add("Ligne 1: Un ajout au debut");
        linesToAdd.add("Ligne 2: a");
        linesToAdd.add("Ligne 3: b");
        linesToAdd.add("Ligne 4: c");
        linesToAdd.add("Ligne 5: d");
        opVector.add(new AddBlock(8, txtFileName, wsName, System.currentTimeMillis(), false, 1, linesToAdd));
        linesToRemove.addAll(linesToAdd);
        linesToRemove.remove(0);
        linesToRemove.remove(0);
        linesToRemove.remove(linesToRemove.size() - 1);
        opVector.add(new DelBlock(8, txtFileName, wsName, System.currentTimeMillis(), false, 3, linesToRemove));

        // update xml
        opVector.add(new InsertNode(8, xmlFileName, wsName, System.currentTimeMillis(), "0:1:0", new ElementNode("tutu")));

        // update bin
        opVector.add(new UpdateBinaryFile(8, binFileName, wsName, System.currentTimeMillis(), false, dir + "/" + binFileName_new));

        //
        opVector.add(new Rename(6, txtFileName, wsName, System.currentTimeMillis(), false, null, txtFileName_new));
        opVector.add(new Rename(6, binFileName, wsName, System.currentTimeMillis(), false, null, binFileName_new));
        opVector.add(new Rename(6, xmlFileName, wsName, System.currentTimeMillis(), false, null, xmlFileName_new));
    }
}
