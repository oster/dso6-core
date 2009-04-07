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

import org.libresource.so6.core.Workspace;
import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.client.ClientI;
import org.libresource.so6.core.client.DummyClient;
import org.libresource.so6.core.engine.util.FileUtils;

import java.io.File;

import java.util.Properties;


public class WorkspaceTest extends TestCase {
    private String dir;
    private String queue;

    public WorkspaceTest(String name) {
        super(name);
    }

    //	public static Test suite() {
    //		TestSuite suite = new TestSuite();
    //		suite.addTest(new AddFileTest("testAddFileAddFileWithConflict"));
    //		return suite;
    //	}
    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();
        queue = FileUtils.createTmpDir().getPath();
    }

    //////////////////////////////////////////
    public void testWorkspaceCreation() throws Exception {
        Workspace ws = null;

        try {
            ws = new Workspace(dir);
            assertTrue("new Workspace didn't throw exception", false);
        } catch (Exception e) {
            // Normal
        }

        ws = Workspace.createWorkspace(dir);

        String wsId = ws.getId();
        String subWs = dir + File.separator + "subWs1";
        FileUtils.createDir(subWs);

        Workspace ws2 = Workspace.createWorkspace(subWs);
        wsId = ws2.getId();
    }

    public void testWorkspaceConnections() throws Exception {
        DummyClient dc = new DummyClient(queue);
        Workspace ws = Workspace.createWorkspace(dir);
        Properties props = new Properties();
        props.setProperty(ClientI.SO6_QUEUE_ID, queue);
        ws.createConnection(props, DummyClient.class.getName(), "test");

        WsConnection[] wsCon = ws.listConnections();
        int nbCon = wsCon.length;
        assertTrue("Invalide number of connection expected 1 and got " + nbCon, nbCon == 1);
        ws.getConnection("1");
        Workspace.deleteConnection(dir + File.separator + Workspace.SO6PREFIX + File.separator + "1" + File.separator + WsConnection.SO6_WSC_FILE);
        nbCon = ws.listConnections().length;
        assertTrue("Invalide number of connection expected 0 and got " + nbCon, nbCon == 0);
    }
}
