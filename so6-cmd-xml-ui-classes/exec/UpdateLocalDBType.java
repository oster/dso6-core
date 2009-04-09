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
 * The <code>UpdateLocalDBType</code> class is used to pre-compute local file
 * types
 * <p>
 * Parameter of that executable
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
public class UpdateLocalDBType {
    /**
     * The <code>UpdateLocalDBType</code> class is used to pre-compute local
     * file types
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
            System.err.println(" (1) wscPath : Path of the connection property file (.so6/1/so6.properties)");
        } else if (args.length == 1) {
            WsConnection ws = new WsConnection(args[0]);
            ws.getDBType().updateFromWalk(ws.getPath(), true);
            System.exit(0);
        }
    }
}
