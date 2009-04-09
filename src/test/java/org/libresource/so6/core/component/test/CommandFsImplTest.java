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
 * Created on 29 d�c. 2003
 */
package org.libresource.so6.core.component.test;

import junit.framework.TestCase;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.client.dummy.DummyClient;
import org.libresource.so6.core.command.Command;
import org.libresource.so6.core.command.fs.AddDir;
import org.libresource.so6.core.command.text.AddTxtFile;
import org.libresource.so6.core.engine.OpVector;
import org.libresource.so6.core.engine.OpVectorFsImpl;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.File;

import java.util.ListIterator;


/**
 * @author molli
 */
public class CommandFsImplTest extends TestCase {
    private String dir;
    private OpVector ov;
    private WsConnection ws;

    public CommandFsImplTest(String arg0) {
        super(arg0);
    }

    protected void setUp() throws Exception {
        super.setUp();
        dir = FileUtils.createTmpDir().getPath();

        File f = new File(dir);
        f.mkdir();
        ws = TestUtil.createWs(dir, "", "wsCommandFsImplTest", new DummyClient());
        ov = new OpVectorFsImpl(FileUtils.createTmpDir().getPath());
        ov.clear();
    }

    public void testOpVector() throws Exception {
        AddDir adddir = new AddDir("toto", ws);
        ov.add(adddir);

        Command cmd = ov.getCommand(0);
        assert (cmd instanceof AddDir);
        assertEquals(adddir.getPath(), cmd.getPath());
    }

    public void testSetCommand() throws Exception {
        AddDir adddir = new AddDir("toto", ws);
        ov.add(adddir);

        AddDir adddir2 = new AddDir("titi", ws);
        ov.update(adddir2, 0);

        Command cmd = ov.getCommand(0);
        assertEquals(adddir2.getPath(), cmd.getPath());
    }

    public void testRealCommand() throws Exception {
        FileUtils.createTxtFile(dir, "a", "toto");

        Command cmd = new AddTxtFile("a", ws);
        ov.add(cmd);
    }

    public void testRealCommand2() throws Exception {
        ov.clear();
        FileUtils.createTxtFile(dir, "a", "toto");

        Command cmd = new AddTxtFile("a", ws);
        ov.add(cmd);
    }

    public void testIterator() throws Exception {
        Command[] data = new Command[3];
        data[0] = new AddDir("toto", ws);
        data[1] = new AddDir("titi", ws);
        data[2] = new AddDir("tutu", ws);

        for (int i = 0; i < data.length; i++) {
            ov.add(data[i]);
        }

        ListIterator ovi = ov.getCommands();

        for (int i = 0; i < data.length; i++) {
            Command cmd = (Command) ovi.next();
            assertEquals(cmd, data[i]);
        }

        assertNull(ovi.next());
    }
}
