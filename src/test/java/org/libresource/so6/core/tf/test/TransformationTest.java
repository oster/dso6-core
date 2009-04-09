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
package org.libresource.so6.core.tf.test;

import java.io.File;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.command.Command;
import org.libresource.so6.core.command.EmptyOp;
import org.libresource.so6.core.command.Id;
import org.libresource.so6.core.command.NoOp;
import org.libresource.so6.core.command.fs.AddBinaryFile;
import org.libresource.so6.core.command.fs.AddDir;
import org.libresource.so6.core.command.fs.Remove;
import org.libresource.so6.core.command.fs.Rename;
import org.libresource.so6.core.command.fs.UpdateBinaryFile;
import org.libresource.so6.core.command.text.AddBlock;
import org.libresource.so6.core.command.text.AddTxtFile;
import org.libresource.so6.core.command.text.DelBlock;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;
import org.libresource.so6.core.tf.TransformationFunctions;


public class TransformationTest extends TestCase {
    private Command[] cmds;
    private TransformationFunctions tf;
    private String dir;

    public TransformationTest(String name) {
        super(name);
    }

    //      use this to order test
    //	public static Test suite() {
    //		TestSuite suite = new TestSuite();
    //		suite.addTest(new
    // AddBinaryFileTest("testAddBinaryFileAddBinaryFileWithConflict"));
    //		return suite;
    //	}
    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();

        WsConnection ws = TestUtil.createWorkspace(dir, 1)[0];
        tf = new TransformationFunctions(ws);
        FileUtils.createTxtFile(dir, "tmpFile", "just to have a real file");

        String attachement = dir + File.separator + "tmpFile";

        //
        Command basicCmd = new EmptyOp(-1, "test", System.currentTimeMillis());
        cmds = new Command[17];

        // neutral
        cmds[0] = new EmptyOp(1, "test", System.currentTimeMillis());
        cmds[1] = new Id(2, "tutu", "test", System.currentTimeMillis(), basicCmd);
        cmds[2] = new NoOp(3, "titi", "test", System.currentTimeMillis(), basicCmd);

        // FS
        cmds[3] = new AddBinaryFile(4, "tata", "test", System.currentTimeMillis(), false, attachement);
        cmds[4] = new AddDir(5, "toto", "test", System.currentTimeMillis(), false, null);
        cmds[5] = new Remove(6, "tutu2", "test", System.currentTimeMillis(), false, null);
        cmds[6] = new Rename(7, "ola", "test", System.currentTimeMillis(), false, null, "ola2");
        cmds[7] = new UpdateBinaryFile(8, "tata", "test", System.currentTimeMillis(), false, attachement);

        // TXT
        cmds[8] = new AddBlock(9, "txt", "test", System.currentTimeMillis(), false, 1, new ArrayList());
        cmds[9] = new AddTxtFile(10, "txtNew", "test", System.currentTimeMillis(), false, attachement);
        cmds[10] = new DelBlock(11, "txtD", "test", System.currentTimeMillis(), false, 1, new ArrayList());

/*TODO: Reintegrate XML supports as a plugin feature?
        //XML
        cmds[11] = new AddXmlFile(12, "xmlNew", "test", System.currentTimeMillis(), attachement);
        cmds[12] = new DeleteAttribute(13, "xmlDA", "test", System.currentTimeMillis(), "0:0", "attr");
        cmds[13] = new DeleteNode(14, "xmlDN", "test", System.currentTimeMillis(), "0:0", new TextNode("test"));
        cmds[14] = new InsertAttribute(15, "xmlIA", "test", System.currentTimeMillis(), "0:0", "titi", "titiValue");
        cmds[15] = new InsertNode(17, "xmlIN", "test", System.currentTimeMillis(), "0:0", new TextNode("test"));
        cmds[16] = new UpdateAttribute(22, "xmlM", "test", System.currentTimeMillis(), "0:0", "attrName", "attrValue.old", "attrValue.new");
*/
    }

    public void tearDown() throws Exception {
    }

    //////////////////////////////////////////
    public void testCouverture() throws Exception {
        for (int i = 0; i < cmds.length; i++) {
            for (int j = 0; j < cmds.length; j++) {
                try {
                    tf.transp(cmds[i], cmds[j]);
                } catch (Exception e) {
                    System.out.println("i=" + i + " / j=" + j);
                    System.out.println(cmds[i] + " / " + cmds[j]);
                    throw e;
                }
            }
        }
    }

    //////////////////////////////////////////
    protected void runTest() throws Throwable {
        super.runTest();
    }
}
