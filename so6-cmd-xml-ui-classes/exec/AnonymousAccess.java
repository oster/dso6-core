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
import org.libresource.so6.core.engine.DBType;
import org.libresource.so6.core.engine.PatchFile;
import org.libresource.so6.core.engine.util.InfoPatchHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.lang.reflect.Constructor;

import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * The <code>AnonymousAccess</code> class is used to download a remote project
 *
 * @author Smack
 * @version 1.0, 26/05/04
 * @since JDK1.4
 */
public class AnonymousAccess {
    public static final String LAST_TICKET = "anonymous.last.ticket";
    public static final String DBTYPE = "anonymous.dbtype";
    private Properties props;
    private String propsFilePath;
    private String dbTypeFilePath;
    private long maxTicket;

    /**
     * Instantiate a Download process with a ticket limit
     *
     * @param downloadPropPath
     *            Path of the property file of the AnonymousAccess
     * @param maxTicket
     *            Ticket limit
     * @throws Exception
     */
    public AnonymousAccess(String downloadFilePropPath, long maxTicket)
        throws Exception {
        this.maxTicket = maxTicket;
        this.propsFilePath = downloadFilePropPath;
        dbTypeFilePath = new File(downloadFilePropPath).getParent() + File.separator + DBTYPE;
        props = new Properties();

        FileInputStream fis = new FileInputStream(downloadFilePropPath);
        props.load(fis);
        fis.close();
    }

    /**
     * Instantiate a Download process without ticket limit.
     *
     * @param downloadFilePropPath
     *            Path of the property file of the AnonymousAccess
     * @throws Exception
     */
    public AnonymousAccess(String downloadFilePropPath)
        throws Exception {
        this(downloadFilePropPath, -1);
    }

    private String getNextPatch(ClientI client) throws Exception {
        if (maxTicket > 0) {
            long[][] patchList = client.listPatch();

            for (int i = 0; i < patchList.length; i++) {
                if ((patchList[i][0] == (getLastTicket() + 1)) && (patchList[i][1] == maxTicket)) {
                    return client.getPatch(getLastTicket() + 1, maxTicket);
                }
            }

            for (int i = 0; i < patchList.length; i++) {
                if ((patchList[i][0] == (getLastTicket() + 1)) && (patchList[i][1] < maxTicket)) {
                    return client.getPatch(getLastTicket() + 1, patchList[i][1]);
                }
            }
        }

        return client.getPatch(getLastTicket() + 1);
    }

    /**
     * Execute the download process
     *
     * @throws Exception
     */
    public void execute() throws Exception {
        Constructor construct = Class.forName(props.getProperty(WsConnection.SYNC_CLIENT_NAME)).getConstructor(new Class[] { Properties.class });
        ClientI client = ((ClientI) construct.newInstance(new Object[] { props }));
        long lastQueueTicket = client.getLastTicket();

        if (maxTicket > 0) {
            lastQueueTicket = maxTicket;
        }

        //
        String basePath = props.getProperty(WsConnection.PATH);
        DBType dbType = new DBType(dbTypeFilePath, "");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        InfoPatchHandler infoPatch = new InfoPatchHandler();

        while (getLastTicket() < lastQueueTicket) {
            String patchPath = getNextPatch(client);
            saxParser.parse(new File(patchPath), infoPatch);

            PatchFile pf = new PatchFile(patchPath);
            pf.patch(basePath, dbType);
            System.out.println("Updated to " + infoPatch.getToTicket());
            setLastTicket(infoPatch.getToTicket());
        }
    }

    private long getLastTicket() {
        return Long.parseLong(props.getProperty(LAST_TICKET, "0"));
    }

    private void setLastTicket(long lastTicket) throws Exception {
        props.setProperty(LAST_TICKET, Long.toString(lastTicket));

        FileOutputStream fos = new FileOutputStream(propsFilePath);
        props.store(fos, "");
        fos.close();
    }

    /**
     * Instantiate and execute the AnonymousAccess process
     *
     * @param args
     *            <ul>
     *            <li>Path of the donwload property file</li>
     *            <li>ticket boundary (should be avoided when we want to reach
     *            the last state)</li>
     *            </ul>
     *            <p>
     *            The property file should look like this
     *
     * <pre>
     *
     *  so6.clienti.name=org.libresource.so6.client.soap.ClientISoapImpl
     *  so6.path=/home/seb/so6
     *
     *  and all the properties needed to instanciate the ClientI...
     *  (so6.soap.url.endpoint=http\://courgette.loria.fr\:80/ls-so6/services/so6)
     *  (so6.soap.synchronizer.uri=/projects/So6/Sources)
     *
     * </pre>
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: downloadFilePath (lastTicket)");
            System.err.println(" (1) downloadFilePath: path of the file needed to instantiate the client");
            System.err.println(" (2) lastTicket: if you want to get an older state");
        } else {
            AnonymousAccess co = null;

            if (args.length == 1) {
                co = new AnonymousAccess(args[0]);
            }

            if (args.length == 2) {
                co = new AnonymousAccess(args[0], Long.parseLong(args[1]));
            }

            if (co != null) {
                co.execute();
            }
        }
    }
}
