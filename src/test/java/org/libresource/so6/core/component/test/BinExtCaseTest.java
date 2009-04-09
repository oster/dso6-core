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
 * Created on 17 d�c. 2003
 */
package org.libresource.so6.core.component.test;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.client.dummy.DummyClient;
import org.libresource.so6.core.command.fs.AddBinaryFile;
import org.libresource.so6.core.engine.FileParser;
import org.libresource.so6.core.engine.OpVector;
import org.libresource.so6.core.engine.OpVectorFsImpl;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;


/**
 * @author molli
 */
public class BinExtCaseTest extends TestCase {
    private String dir;
    private WsConnection ws;

    public BinExtCaseTest(String arg0) {
        super(arg0);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();
        ws = TestUtil.createWs(dir, "", "wsBinExtCase", new DummyClient());
        ws.cleanLocalOp();
    }

    public void testVeryBasic() throws Exception {
        assertTrue(ws.getClient().getBinExt().indexOf("class") != -1);
    }

    public void testBasicBinExt() throws Exception {
        FileUtils.createTxtFile(dir, "toto.class", "yop");

        FileParser fp = new FileParser(ws);
        File tmpv = FileUtils.createTmpDir();
        OpVector v = new OpVectorFsImpl(FileUtils.createTmpDir().getPath());
        fp.compute(v);
        assertEquals(1, v.size());
        assertTrue(v.getCommand(0) instanceof AddBinaryFile);
    }

    public void testAddExt() throws Exception {
        ws.getClient().addBinExt("prout");
        ws.updateBinExt();
        FileUtils.createTxtFile(dir, "toto.prout", "yop");

        FileParser fp = new FileParser(ws);
        File tmpv = FileUtils.createTmpDir();
        OpVector v = new OpVectorFsImpl(tmpv.getPath());
        fp.compute(v);
        assertEquals(1, v.size());
        assertTrue(v.getCommand(0) instanceof AddBinaryFile);
    }
}
