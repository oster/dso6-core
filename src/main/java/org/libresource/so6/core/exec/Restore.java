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

import org.libresource.so6.core.WsConnection;


/**
 * The <code>Restore</code> class is used to restore a corrupted connection
 * <p>
 * Parameter of that command
 * <ul>
 * <li>wscPath : Path of the connection property file (.so6/1/so6.properties)
 * </li>
 * </ul>
 *
 *
 * @author Smack
 * @version 1.0, 26/05/04
 * @see org.libresource.so6.core.exec.Main
 * @since JDK1.4
 */
public class Restore {
    private WsConnection ws;

    /**
     * Instantiate the restore process
     *
     * @param wscPath :
     *            Path of the connection property file (.so6/1/so6.properties)
     * @throws Exception
     */
    public Restore(String wscPath) throws Exception {
        this.ws = new WsConnection(wscPath);
    }

    /**
     * Execute the restore process
     *
     * @throws Exception
     */
    public void execute() throws Exception {
        ws.restore();
    }

    /**
     * Instantiate and execute the restore process.
     * <p>
     * Caution: Some corruptions need manually restoration afterward.
     *
     * @param args
     *            <ul>
     *            <li>wscPath : Path of the connection property file
     *            (.so6/1/so6.properties)</li>
     *            </ul>
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: wscPath");
            System.err.println(" (1) wscPath: path of the file " + WsConnection.SO6_WSC_FILE);
        } else {
            String wsPath = args[0];
            Restore restore = new Restore(wsPath);
            restore.execute();

            //
            System.out.println("\007");
            System.exit(0);
        }
    }
}
