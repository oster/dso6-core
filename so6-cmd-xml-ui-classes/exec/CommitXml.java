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

import org.libresource.so6.core.StateMonitoring;
import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.client.WorkspaceListener;
import org.libresource.so6.core.engine.log.LogUtils;
import org.libresource.so6.core.engine.log.StandardOutputWriter;
import org.libresource.so6.core.engine.log.monitoring.XMLMonitoringThread;

import java.util.logging.Logger;


/**
 * The <code>Commit</code> class is used to commit local changes
 *
 * @author Smack
 * @version 1.0, 26/05/04
 * @see org.libresource.so6.core.WsConnection
 * @since JDK1.4
 */
public class CommitXml {
    private WsConnection ws;
    private String comment;
    private XMLMonitoringThread monitoringThread;

    /**
     * Instantiate a commit process
     *
     * @param wsPath
     * @param comment
     * @throws Exception
     */
    public CommitXml(String wscPath, String comment) throws Exception {
        this.ws = new WsConnection(wscPath);
        this.comment = comment;

        // init output
        LogUtils.removeAllHandlers(Logger.getLogger("ui.log"));
        LogUtils.removeAllHandlers(StateMonitoring.getInstance().getXMLMonitoringLogger());
        monitoringThread = new XMLMonitoringThread();

        StandardOutputWriter outWriter = new StandardOutputWriter(monitoringThread.getTreeContext());
        monitoringThread.attachMessageWriter(outWriter);
    }

    /**
     * Execute the commit process
     *
     * @throws Exception
     */
    public void execute() throws Exception {
        monitoringThread.start();
        ws.commit(comment);
        monitoringThread.join();

        if (ws.getClient() instanceof WorkspaceListener) {
            ((WorkspaceListener) ws.getClient()).notifyQueue(ws.getNs());
        }

        System.out.println("\n*** Report ***\n");
        System.out.println(ws.getReport());
    }

    /**
     * Simulate the commit process
     *
     * @param outputDir
     * @throws Exception
     */
    public void simulate(String outputDir) throws Exception {
        ws.setSimulationMode(true, outputDir);

        //
        monitoringThread.start();
        ws.commit(comment);
        monitoringThread.join();
    }

    /**
     * Instantiate and execute the commit process
     *
     * @param args
     *            <ul>
     *            <li>Path of the connection property file</li>
     *            <li>Comment for the commit</li>
     *            </ul>
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: wsPath (comment)");
            System.err.println(" (1) wsPath: path of the file " + WsConnection.SO6_WSC_FILE);
            System.err.println(" (2) comment: commit comment");
        } else {
            String wsPath = args[0];
            String comment = "No comment";

            if (args.length > 1) {
                comment = "";

                for (int i = 1; i < args.length; i++)
                    comment += (args[i] + " ");
            }

            CommitXml commit = new CommitXml(wsPath, comment);
            commit.execute();

            //
            System.out.println("\007");
            System.exit(0);
        }
    }
}
