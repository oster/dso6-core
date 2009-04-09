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
import org.libresource.so6.core.client.ClientI;
import org.libresource.so6.core.engine.util.CryptUtil;

import java.lang.reflect.Method;

import java.util.Properties;


/**
 * @author smack
 */
public class UpdateWsConnection {
    private String clientName;
    private String wscPath;
    private Properties props;

    public UpdateWsConnection(String wscPath, String clientName) {
        this(clientName, wscPath, null);
    }

    public UpdateWsConnection(String clientName, String wscPath, Properties props) {
        this.wscPath = wscPath;
        this.clientName = clientName;

        if (props == null) {
            this.props = System.getProperties();
        } else {
            this.props = props;
        }
    }

    public void execute() throws Exception {
        WsConnection wsc = new WsConnection(wscPath);
        Method m = Class.forName(clientName).getMethod("getInternalPropertyList", new Class[] {  });
        String[] ops = (String[]) m.invoke(null, new Object[] {  });
        wsc.setProperty(WsConnection.SYNC_CLIENT_NAME, clientName);

        for (int i = 0; i < ops.length; i++) {
            String value = System.getProperties().getProperty(ops[i]);

            if (value != null) {
                if (ops[i].equals(ClientI.SO6_PASSWORD)) {
                    wsc.setProperty(ops[i], value); //CryptUtil.encode(value));
                } else {
                    wsc.setProperty(ops[i], value);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: wscPath clientName");
            System.err.println(" (1) wscPath: path of the so6.properties");
            System.err.println(" (2) clientName: class name of the client");
            System.err.println(" (3) set all the required props in the JVM");
        } else {
            new UpdateWsConnection(args[0], args[1]).execute();
        }
    }
}
