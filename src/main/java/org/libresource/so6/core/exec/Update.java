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
import org.libresource.so6.core.exec.tools.CheckWscParameters;

import java.util.Properties;
import java.util.logging.Logger;


/**
 * The <code>Commit</code> class is used to commit local changes
 *
 * @author Smack
 * @version 1.0, 26/05/04
 * @see org.libresource.so6.core.WsConnection
 * @since JDK1.4
 */
public class Update {
    private WsConnection ws;
    private XMLMonitoringThread monitoringThread;

    /**
     * Instantiate the update process
     *
     * @param wsPath
     * @throws Exception
     */
    public Update(String wsPath) throws Exception {
        this.ws = new WsConnection(wsPath);

        if (this.ws.getProperty("so6.clienti.name").equals("org.libresource.so6.core.client.ClientIServletImpl")) {
            CheckWscParameters checkWscParameters = new CheckWscParameters(wsPath);
            checkWscParameters.editProperties();

            Properties newProps = checkWscParameters.getWscProps();
            this.ws.updateProp(newProps);
        }

        // init output
        LogUtils.removeAllHandlers(Logger.getLogger("ui.log"));
        LogUtils.removeAllHandlers(StateMonitoring.getInstance().getXMLMonitoringLogger());
        monitoringThread = new XMLMonitoringThread();

        StandardOutputWriter outWriter = new StandardOutputWriter(monitoringThread.getTreeContext());
        monitoringThread.attachMessageWriter(outWriter);
    }

    /**
     * Execute the update process
     *
     * @throws Exception
     */
    public void execute() throws Exception {
        monitoringThread.setDaemon(true);
        monitoringThread.start();
        ws.update();

        // monitoringThread.join();
        if (ws.getClient() instanceof WorkspaceListener) {
            ((WorkspaceListener) ws.getClient()).notifyQueue(ws.getNs());
        }

        System.out.println("\n*** Report ***\n");
        System.out.println(ws.getReport());
    }

    /**
     * Simulate the update process
     *
     * @param outputDir
     * @throws Exception
     */
    public void simulate(String outputDir) throws Exception {
        ws.setSimulationMode(true, outputDir);

        //
        monitoringThread.start();
        ws.update();
        monitoringThread.join();
    }

    /**
     * Instantiate and execute the update process
     *
     * @param args
     *            path of the connection property file
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: wscPath");
            System.err.println(" (1) wscPath: path of the file " + WsConnection.SO6_WSC_FILE);
        } else {
            Update update = new Update(args[0]);
            update.execute();
            System.out.println("\007");
            System.exit(0);
        }
    }
}
