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
package org.libresource.so6.core.exec.ui.tools;

import java.awt.Component;

import java.io.IOException;

import java.util.Hashtable;
import java.util.Properties;

import javax.swing.JOptionPane;


/**
 * @author smack
 */
public class CheckWscParameters extends LsClassicPropertiesEditor {
    private Properties wscProps;

    public CheckWscParameters(String serviceUrl_, String queueId_, String basePath_, String login_, String wsName_, String clientName_,
        Properties clientProperties_) throws IOException {
        super("Check your workspace connection Parameters...");
        addTextPropertie("serviceUrl", "Service URL :", serviceUrl_, false);
        addTextPropertie("wsName", "Workspace Name :", wsName_, false);
        addPathPropertie("basePath", "Base Path :", basePath_, false);
        addTextPropertie("synchronizerURI", "Synchronizer URI :", queueId_, false);
        addTextPropertie("login", "Login :", login_, false);
        addPasswordPropertie("password", "Password :", "", true);

        wscProps = new Properties();

        Hashtable props = editProperties();

        wscProps.putAll(props);
    }

    public static void main(String[] args) throws Exception {
        if (args.length > 1) {
            System.err.println("The only parameter required is the client class name");
            System.err.println("To set default value please use System properties");
            System.err.println(" (1) createLibresourceReplica.basePath = workspace base path");
            System.err.println(" (2) createLibresourceReplica.wsName   = name of the workspace");
            System.err.println(" (3) createLibresourceReplica.queueId  = the synchronizer URI");
            System.err.println(" (4) createLibresourceReplica.login    = user login");
            System.err.println(" (5) createLibresourceReplica.password = user password");
        } else if (args.length == 1) {
            String serviceUrl = System.getProperties().getProperty("createLibresourceReplica.serviceUrl", "");
            String queueId = System.getProperties().getProperty("createLibresourceReplica.queueId", "");
            String basePath = System.getProperties().getProperty("createLibresourceReplica.basePath", "");
            String login = System.getProperties().getProperty("createLibresourceReplica.login", "");
            String password = System.getProperties().getProperty("createLibresourceReplica.password", "");
            String wsName = System.getProperties().getProperty("createLibresourceReplica.wsName", "");
            String clientName = args[0];

            new CheckWscParameters(serviceUrl, queueId, basePath, login, wsName, clientName, null);
            JOptionPane.showMessageDialog(null, "<html>Your replica has been created localy.<br>(Refresh your browser to see it...)</html>");
            System.exit(0);
        }
    }

    public Properties getWscProps() {
        return wscProps;
    }
}
