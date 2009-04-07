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
package org.libresource.so6.core.component.test;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.report.CVSReportMaker;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;

import java.util.Arrays;


public class ReportTest extends TestCase {
    private String dir;
    private String dir1;
    private String dir2;
    private WsConnection ws1;
    private WsConnection ws2;

    public ReportTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();
        System.out.println(dir);

        WsConnection[] ws = TestUtil.createWorkspace(dir, 2);
        ws1 = ws[0];
        ws2 = ws[1];
        dir1 = ws1.getPath();
        dir2 = ws2.getPath();
    }

    //////////////////////////////////////////
    public void testPrintReport() throws Exception {
        for (int i = 0; i < 10; i++) {
            FileUtils.createTxtFile(dir1, "fileUser1_" + i, "File of the user 1\na\nb\nc");
            FileUtils.createDir(dir1 + "/dir" + i);
            FileUtils.createTxtFile(dir2, "fileUser1_" + i, "File of the user 1");
        }

        //FileUtils.createFile(dir3,"fileUser3.txt","File of the user 3");
        // Synchronize all
        ws1.commit("sync 1");

        for (int i = 5; i < 10; i++) {
            FileUtils.editTxtFile(dir1 + "/fileUser1_" + i, "File of the user 1 with change\na\nbb\nc\ntutu");
        }

        ws1.commit("sync 2");
        ws2.update();
        ws2.commit("sync 3");
        ws1.update();

        // 
        File mergedOp1 = new File(ws1.getMergedOpBasePath());
        String[] op1List = mergedOp1.list();
        Arrays.sort(op1List);

        for (int i = 0; i < op1List.length; i++) {
            if (op1List[i].startsWith("op_")) {
                CVSReportMaker report = new CVSReportMaker(ws1.getMergedOpBasePath() + File.separator + op1List[i]);
                report.buildIndexTable();
                report.writeReport(System.out);
            }
        }

        System.out.println("\n###\n");

        File mergedOp2 = new File(ws2.getMergedOpBasePath());
        String[] op2List = mergedOp2.list();
        Arrays.sort(op2List);

        for (int i = 0; i < op2List.length; i++) {
            if (op2List[i].startsWith("op_")) {
                CVSReportMaker report = new CVSReportMaker(ws2.getMergedOpBasePath() + File.separator + op2List[i]);
                report.buildIndexTable();
                report.writeReport(System.out);
            }
        }
    }

    //////////////////////////////////////////
}
