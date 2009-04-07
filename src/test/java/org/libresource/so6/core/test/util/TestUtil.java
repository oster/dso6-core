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
package org.libresource.so6.core.test.util;

import org.libresource.so6.core.StateMonitoring;
import org.libresource.so6.core.Workspace;
import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.client.ClientI;
import org.libresource.so6.core.client.DummyClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Observable;
import java.util.Observer;
import java.util.Properties;


/**
 * @author smack
 */
public class TestUtil {
    private static byte[] megaBuffer = new byte[1024];

    public static void createBinaryFile(String fileName, long fileSize)
        throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);

        for (int i = 0; i < (fileSize / megaBuffer.length); i++) {
            fos.write(megaBuffer);
        }

        for (int i = 0; i < (fileSize % megaBuffer.length); i++) {
            fos.write(i);
        }

        fos.close();
    }

    public static WsConnection[] createWorkspace(String baseDir, int nbWs, ClientI client, boolean xmlDetection)
        throws Exception {
        WsConnection[] result = new WsConnection[nbWs];
        File d = new File(baseDir);
        d.mkdirs();

        //
        for (int i = 0; i < nbWs; i++) {
            Properties prop = new Properties();
            d = new File(baseDir + "/dir" + (i + 1));
            d.mkdirs();
            prop.setProperty(WsConnection.PATH, d.getAbsolutePath());
            d = new File(baseDir + "/dir" + (i + 1) + "/" + Workspace.SO6PREFIX);
            d.mkdirs();
            prop.setProperty(WsConnection.DATAPATH, d.getAbsolutePath());
            prop.setProperty(WsConnection.WS_NAME, "ws" + (i + 1));
            prop.setProperty(WsConnection.SYNC_CLIENT_NAME, client.getClass().getName());

            if (client instanceof DummyClient) {
                prop.setProperty(DummyClient.SO6_QUEUE_ID, ((DummyClient) client).getDummyPath());
            }

            String propfile = d.getAbsolutePath() + File.separator + "so6.properties";
            prop.store(new FileOutputStream(propfile), "do not edit");
            result[i] = new WsConnection(propfile);
            result[i].setClient(client);
            result[i].setXmlAutoDetection(xmlDetection);
        }

        return result;
    }

    public static WsConnection[] createWorkspace(String baseDir, int nbWs)
        throws Exception {
        return createWorkspace(baseDir, nbWs, false);
    }

    public static WsConnection[] createWorkspace(String baseDir, int nbWs, boolean xmlDetection)
        throws Exception {
        return createWorkspace(baseDir, nbWs, new DummyClient(), xmlDetection);
    }

    public static WsConnection createWs(String baseDir, String dataDir, String wsName, ClientI client)
        throws Exception {
        Properties prop = new Properties();
        prop.setProperty(WsConnection.PATH, baseDir);

        File d = new File(baseDir + "/" + Workspace.SO6PREFIX + "/" + dataDir);
        d.mkdirs();
        prop.setProperty(WsConnection.DATAPATH, d.getPath());
        prop.setProperty(WsConnection.WS_NAME, wsName);

        String propfile = d.getAbsolutePath() + File.separator + "so6.properties";
        prop.store(new FileOutputStream(propfile), "do not edit");

        WsConnection ws = new WsConnection(propfile);
        ws.setClient(client);

        return ws;
    }

    // -------------------------------------------------------------
    public static class UpdateThread extends Thread {
        private WsConnection ws;

        public UpdateThread(WsConnection ws) {
            this.ws = ws;
            setPriority(Thread.MIN_PRIORITY);
        }

        public void run() {
            try {
                ws.update();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class CommitThread extends Thread {
        private WsConnection ws;

        public CommitThread(WsConnection ws) {
            this.ws = ws;
            setPriority(Thread.MIN_PRIORITY);
        }

        public void run() {
            try {
                ws.commit("with corruption");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class ThreadKiller implements Observer {
        private Thread thread;
        private int nbPartToSkip = -1;

        public ThreadKiller(Thread thread, int killAfterNCriticalPart)
            throws Exception {
            this.thread = thread;
            this.nbPartToSkip = killAfterNCriticalPart;
            StateMonitoring.getInstance().addObserver(this);
        }

        public void update(Observable arg0, Object arg1) {
            if (nbPartToSkip < StateMonitoring.getInstance().getCurrentCriticalPartNumber()) {
                thread.stop();
            }
        }
    }
}
