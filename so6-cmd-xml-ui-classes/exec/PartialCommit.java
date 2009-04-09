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
import org.libresource.so6.core.engine.log.LogUtils;
import org.libresource.so6.core.engine.log.StandardOutputWriter;
import org.libresource.so6.core.engine.log.monitoring.XMLMonitoringThread;

import java.io.FileReader;
import java.io.LineNumberReader;

import java.util.Vector;
import java.util.logging.Logger;


/**
 * The <code>PartialCommit</code> class is used to commit parts of local
 * changes
 * <p>
 * Parameters of that command
 * <ul>
 * <li>wscPath : Path of the connection property file (.so6/1/so6.properties)
 * </li>
 * <li>comment : The comment for the commit. (Don't forget to put " around the
 * comment)</li>
 * <li>filterFile : The path of the file where we specify the files that we
 * want to commit</li>
 * </ul>
 *
 * <p>
 * Filter file content exemple :
 *
 * <pre>
 *
 *  src
 *  src/org
 *  src/org/libresource
 *  src/org/libresource/so6
 *  src/org/libresource/so6/Workspace.java
 *  src/org/libresource/so6/WsConnection.java
 *
 * </pre>
 *
 * @author Smack
 * @version 1.0, 26/05/04
 * @see org.libresource.so6.core.exec.Main
 * @since JDK1.4
 */
public class PartialCommit {
    private WsConnection ws;
    private String comment;
    private XMLMonitoringThread monitoringThread;

    /**
     * Instantiate a partial commit process
     *
     * @param wscPath :
     *            Path of the connection property file (.so6/1/so6.properties)
     * @param comment :
     *            The comment for the commit. (Don't forget to put " around the
     *            comment)
     * @param filterPath :
     *            The path of the file where we specify the files that we want
     *            to commit
     *
     * @throws Exception
     */
    public PartialCommit(String wscPath, String comment, String filterPath)
        throws Exception {
        this.ws = new WsConnection(wscPath);
        this.comment = comment;

        // load filter
        Vector filter = new Vector();
        LineNumberReader lineReader = new LineNumberReader(new FileReader(filterPath));
        String line;

        while ((line = lineReader.readLine()) != null) {
            filter.add(line);
        }

        lineReader.close();
        ws.setFilter(filter);

        // init output
        LogUtils.removeAllHandlers(Logger.getLogger("ui.log"));
        LogUtils.removeAllHandlers(StateMonitoring.getInstance().getXMLMonitoringLogger());
        monitoringThread = new XMLMonitoringThread();

        StandardOutputWriter outWriter = new StandardOutputWriter(monitoringThread.getTreeContext());
        monitoringThread.attachMessageWriter(outWriter);
    }

    /**
     * Execute the partial commit process
     *
     * @throws Exception
     */
    public void execute() throws Exception {
        monitoringThread.start();
        ws.commit(comment);

        //monitoringThread.join();
    }

    /**
     * Simulate the partial commit process and build the patch file in the
     * specified directory
     *
     * @param outputDir :
     *            output directory path
     *
     * @throws Exception
     */
    public void simulate(String outputDir) throws Exception {
        ws.setSimulationMode(true, outputDir);

        //
        monitoringThread.start();
        ws.commit(comment);

        //monitoringThread.join();
    }

    /**
     * The <code>PartialCommit</code> class is used to commit parts of local
     * changes
     * <p>
     * Parameters of that command
     * <ul>
     * <li>wscPath : Path of the connection property file
     * (.so6/1/so6.properties)</li>
     * <li>comment : The comment for the commit. (Don't forget to put " around
     * the comment)</li>
     * <li>filterFile : The path of the file where we specify the files that we
     * want to commit</li>
     * </ul>
     *
     * <p>
     * Filter file content exemple :
     *
     * <pre>
     *
     *  src
     *  src/org
     *  src/org/libresource
     *  src/org/libresource/so6
     *  src/org/libresource/so6/Workspace.java
     *  src/org/libresource/so6/WsConnection.java
     *
     * </pre>
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: wscPath (comment)");
            System.err.println(" (1) wscPath: path of the file " + WsConnection.SO6_WSC_FILE);
            System.err.println(" (2) comment: commit comment");
            System.err.println(" (3) file: filter file name");
        } else {
            String wsPath = args[0];
            String comment = args[1];
            String filterFileName = args[2];
            PartialCommit commit = new PartialCommit(wsPath, comment, filterFileName);
            commit.execute();

            //
            System.out.println("\007");
            System.exit(0);
        }
    }
}
