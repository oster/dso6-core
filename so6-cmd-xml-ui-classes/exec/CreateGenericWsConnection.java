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

import org.libresource.so6.core.Workspace;
import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.client.AuthenticationException;
import org.libresource.so6.core.client.ClientI;
import org.libresource.so6.core.client.LocalException;
import org.libresource.so6.core.client.ServerException;
import org.libresource.so6.core.client.UnableToContactServerException;
import org.libresource.so6.core.engine.Ignore;
import org.libresource.so6.core.engine.util.CryptUtil;
import org.libresource.so6.core.net.DataflowClientI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.lang.reflect.Method;

import java.util.Iterator;
import java.util.Properties;


/**
 * @author smack
 */
public class CreateGenericWsConnection {
    public final static String LS_CLIENT_NAME = "org.libresource.so6.core.client.ClientIServletImpl";
    public final static String FS_CLIENT_NAME = "org.libresource.so6.core.client.DummyClient";
    private Workspace ws;
    private WsConnection wsc;
    private String wscPath;
    private String path;
    private String login;
    private String password;
    private String queueId;
    private String wsName;
    private String clientName;
    private Properties props;

    public CreateGenericWsConnection(String basePath, String wsName, String clientName, String login, String password, String queueId, Properties personalProps) {
        this.path = basePath;
        this.queueId = queueId;
        this.wsName = wsName;
        this.login = login;
        this.password = password;
        this.clientName = clientName;
        this.props = personalProps;
    }

    public String executeLocalCreation() throws IOException, LocalException {
        ws = Workspace.createWorkspace(path);

        Properties prop = new Properties();
        prop.setProperty(ClientI.SO6_QUEUE_ID, queueId);
        prop.setProperty(ClientI.SO6_LOGIN, "" + login);
        prop.setProperty(ClientI.SO6_PASSWORD, CryptUtil.encode("" + password));

        if (props != null) {
            for (Iterator i = props.keySet().iterator(); i.hasNext();) {
                String key = (String) i.next();
                String value = props.getProperty(key);
                prop.setProperty(key, value);
            }
        } else {
            String[] ops;

            try {
                Method m = Class.forName(clientName).getMethod("getInternalPropertyList", new Class[] {  });
                ops = (String[]) m.invoke(null, new Object[] {  });
            } catch (Exception e) {
                throw new LocalException(e);
            }

            for (int i = 0; i < ops.length; i++) {
                String value = System.getProperties().getProperty(ops[i]);

                if (value != null) {
                    prop.setProperty(ops[i], value);
                }
            }
        }

        wscPath = ws.createConnection(prop, clientName, wsName);

        return wscPath;
    }

    public String executeRemoteCreation()
        throws Exception, IOException, LocalException, AuthenticationException, ServerException, UnableToContactServerException {
        wsc = new WsConnection(wscPath);

        if (wsc.getClient() instanceof DataflowClientI) {
            String wscId = ((DataflowClientI) wsc.getClient()).addWsConnection(ws.getId(), queueId, wsName, wscPath).trim();
            wsc.setProperty(DataflowClientI.SO6_CONNECTION_ID, wscId);

            return wscId;
        }

        return null;
    }

    public void execute() throws Exception, IOException, LocalException, AuthenticationException, ServerException, UnableToContactServerException {
        execute(null);
    }

    public void execute(String ignoreFile)
        throws Exception, IOException, LocalException, AuthenticationException, ServerException, UnableToContactServerException {
        executeLocalCreation();
        executeRemoteCreation();

        if (ignoreFile != null) {
            FileWriter fw = new FileWriter(wsc.getPath() + File.separator + Ignore.ignoreFile);
            fw.write(ignoreFile);
            fw.close();
        }
    }

    /**
     * @return Returns the synchronizerURI.
     */
    public String getQueueId() {
        return queueId;
    }

    /**
     * @param synchronizerURI
     *            The synchronizerURI to set.
     */
    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    /**
     * @return Returns the ws.
     */
    public Workspace getWs() {
        return ws;
    }

    /**
     * @param ws
     *            The ws to set.
     */
    public void setWs(Workspace ws) {
        this.ws = ws;
    }

    /**
     * @return Returns the wsc.
     */
    public WsConnection getWsc() {
        return wsc;
    }

    /**
     * @param wsc
     *            The wsc to set.
     */
    public void setWsc(WsConnection wsc) {
        this.wsc = wsc;
    }

    /**
     * @return Returns the wscPath.
     */
    public String getWscPath() {
        return wscPath;
    }

    /**
     * @param wscPath
     *            The wscPath to set.
     */
    public void setWscPath(String wscPath) {
        this.wscPath = wscPath;
    }

    /**
     * @return Returns the wsName.
     */
    public String getWsName() {
        return wsName;
    }

    /**
     * @param wsName
     *            The wsName to set.
     */
    public void setWsName(String wsName) {
        this.wsName = wsName;
    }

    /**
     * @return Returns the clientName.
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @param clientName
     *            The clientName to set.
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * @return Returns the login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login
     *            The login to set.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return Returns the path.
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     *            The path to set.
     */
    public void setPath(String path) {
        this.path = path;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: path wsName clientName login password queueId");
            System.err.println(" (1) path: path to share");
            System.err.println(" (2) wsName: workspace name");
            System.err.println(" (2) clientName: class name of the client to use");
            System.err.println(" (3) login: ");
            System.err.println(" (4) password: ");
            System.err.println(" (5) queueId: URI or file path");
        } else {
            CreateGenericWsConnection ws = new CreateGenericWsConnection(args[0], args[1], args[2], args[3], args[4], args[5], null);
            ws.execute();
        }
    }
}
