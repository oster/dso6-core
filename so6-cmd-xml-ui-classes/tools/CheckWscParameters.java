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
package org.libresource.so6.core.exec.tools;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.client.ClientI;
import org.libresource.so6.core.client.ClientIServletImpl;
import org.libresource.so6.core.engine.util.CryptUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Properties;


/**
 * @author smack
 */
public class CheckWscParameters {
    private Properties wscProps;

    public CheckWscParameters(String wscPath) throws IOException {
        wscProps = new Properties();
        load(wscPath);
    }

    private void display(String line) {
        System.out.println(line);
    }

    private void displayProperty(String key, String value) {
        System.out.println("  - " + key + " : " + value);
    }

    public Properties getWscProps() {
        return wscProps;
    }

    public Properties editProperties() throws Exception {
        display("Check your workspace connection Parameters...");
        displayProperty("Service URL", wscProps.getProperty(ClientIServletImpl.SO6_SERVICE_URL));
        displayProperty("Workspace Name", wscProps.getProperty(WsConnection.WS_NAME));
        displayProperty("Base Path", wscProps.getProperty(WsConnection.PATH));
        displayProperty("Synchronizer URI", wscProps.getProperty(ClientI.SO6_QUEUE_ID));
        displayProperty("Login", wscProps.getProperty(ClientI.SO6_LOGIN));

        // read password
        System.out.print("  - Password : ");

        EraserThread et = new EraserThread();
        et.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String password = "";

        try {
            password = br.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        et.stopMasking();
        wscProps.put(ClientI.SO6_PASSWORD, CryptUtil.encode(password));

        return wscProps;
    }

    /**
     * Load meta data from the filesystem. (Need to be used when concurrent
     * update on the file systeme.)
     *
     * @param path
     *            The path of the workspace connection property file
     *
     * @throws Exception
     */
    public void load(String path) throws IOException {
        File propfile = new File(path);

        if (propfile.exists() && (!propfile.isDirectory())) {
            try {
                FileInputStream fis = new FileInputStream(propfile);
                wscProps.load(fis);
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new IOException("Cannot read property file:" + path);
            }
        } else {
            throw new IOException("Cannot find property file: " + path + "\n The workspace might not be on this computer.");
        }
    }
}


class EraserThread extends Thread {
    private boolean stop;

    /**
     * Begin masking...
     */
    public void run() {
        stop = true;

        while (stop) {
            System.out.print("\010 ");

            try {
                Thread.currentThread().sleep(1);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    /**
     * Instruct the thread to stop masking
     */
    public void stopMasking() {
        this.stop = false;
    }
}
