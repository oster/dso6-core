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
package org.libresource.so6.core.client.servlet;

import org.libresource.so6.core.StateMonitoring;
import org.libresource.so6.core.client.AuthenticationException;
import org.libresource.so6.core.client.ConnectionException;
import org.libresource.so6.core.client.InvalidTicketException;
import org.libresource.so6.core.client.LocalException;
import org.libresource.so6.core.client.PatchNotFoundException;
import org.libresource.so6.core.client.ServerException;
import org.libresource.so6.core.client.UnableToContactServerException;
import org.libresource.so6.core.engine.util.MonitoredInputStream;
import org.libresource.so6.core.net.DataflowClientI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;


/**
 * @author smack
 */
public class ClientIServletImpl implements DataflowClientI {
    // Default properties
    public final static String SO6_SERVICE_URL = "so6.service.url";
    public final static String SO6_DOWNLOAD_DIR = "so6.download.dir";

    //
    public final static String SO6_ACTION = "so6.action";

    // Internal parameters
    private static int BUFFER_SIZE = 1024 * 1024 * 2;

    // HTTP header props
    protected final static String SO6_PATCH_TICKET = "so6.patch.ticket";
    protected final static String SO6_PATCH_FROM_TICKET = "so6.patch.from.ticket";
    protected final static String SO6_PATCH_TO_TICKET = "so6.patch.to.ticket";
    protected final static String SO6_BIN_EXT = "so6.bin.ext";
    public final static String SO6_SYNCHRONIZER_URI = "so6.synchronizer.uri";
    public final static String SO6_REPLICA_TICKET = "so6.replica.ticket";
    public final static String SO6_WORKSPACE_ID = "so6.ws.id";
    public final static String SO6_REPLICA_URI = "so6.replica.uri";
    public final static String SO6_WSC_PATH = "so6.wsc.path";
    public final static String SO6_WS_NAME = "so6.ws.name";
    public final static String SO6_VALIDATE_TICKET_NUMBER = "so6.validate.ticket.number";

    // status code
    public final static int STATUS_CODE_OK = 200;
    public final static int STATUS_CODE_AUTH_ERROR = 402;
    public final static int STATUS_CODE_SERVER_ERROR = 403;
    public final static int STATUS_CODE_INVALIDE_TICKET = 404;
    public final static int STATUS_CODE_PATCH_NOT_FOUND = 405;

    // Client http
    private HttpURLConnection httpUrlConnection;

    // props
    private String serviceUrl;
    private String login;
    private String password;
    private String synchronizerURI;
    private String replicaURI;
    private String downloadDir = System.getProperty("java.io.tmpdir") + "/so6.tmp_" + System.getProperty("user.name");

    public ClientIServletImpl() {
    }

    public ClientIServletImpl(Properties props) throws Exception {
        // init client
        serviceUrl = props.getProperty(SO6_SERVICE_URL);
        login = props.getProperty(SO6_LOGIN);

        if (props.getProperty(SO6_PASSWORD) != null) {
            password = props.getProperty(SO6_PASSWORD);

            // CryptUtil.decode(props.getProperty(SO6_PASSWORD));
        }

        synchronizerURI = props.getProperty(SO6_QUEUE_ID);
        replicaURI = props.getProperty(SO6_CONNECTION_ID);

        //
        if ((serviceUrl == null) || (synchronizerURI == null)) {
            throw new RuntimeException("Properties are missing for ClientIServletImpl initilisation");
        }

        //
        URL url = new URL(serviceUrl);
        httpUrlConnection = (HttpURLConnection) url.openConnection();
        this.initProxy(httpUrlConnection);

        File download = new File(downloadDir);

        if (download.exists()) {
            download.mkdirs();
        }
    }

    private void initProxy(HttpURLConnection httpUrlConnection) {
        if (System.getProperty("proxyHost") != null) {
            // Init proxy
            // httpUrlConnection.getHostConfiguration().setProxy(System.getProperty("proxyHost"),
            // Integer.parseInt(System.getProperty("proxyPort")));
        }
    }

    private void basicHTTPLogin(String login, String password, HttpURLConnection httpUrlConnection) {
        // String crypt = login + ":" + password;
        // post.addRequestHeader("auth",
        // Base64.encode(crypt.getBytes("UTF-8")));
        httpUrlConnection.addRequestProperty("login", login);
        httpUrlConnection.addRequestProperty("password", password);
    }

    private HttpURLConnection createPost(String action)
        throws UnableToContactServerException {
        try {
            URL url = new URL(serviceUrl);
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.addRequestProperty(SO6_ACTION, action);
            httpUrlConnection.addRequestProperty(SO6_SYNCHRONIZER_URI, synchronizerURI);
            httpUrlConnection.addRequestProperty("Content-Length", "0");

            // Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
            if ((login != null) && (password != null)) {
                basicHTTPLogin(login, password, httpUrlConnection);
            }

            return httpUrlConnection;
        } catch (Exception e) {
            throw new UnableToContactServerException();
        }
    }

    private int executePost(HttpURLConnection connection)
        throws ServerException {
        try {
            // connection.connect();
            return connection.getResponseCode();
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }

    public long getLastTicket() throws AuthenticationException, UnableToContactServerException, ServerException, ConnectionException, LocalException {
        HttpURLConnection post = createPost("getLastTicket");
        int statusCode = executePost(post);

        switch (statusCode) {
        case STATUS_CODE_OK:

            long result = Long.parseLong(post.getHeaderField("lastTicket"));

            // post.disconnect();
            return result;

        case STATUS_CODE_AUTH_ERROR:
            throw new AuthenticationException();

        case STATUS_CODE_SERVER_ERROR:default:
            throw new ServerException("Server error on getLastTicket. Status code=" + statusCode);
        }
    }

    public String getPatch(long ticket)
        throws AuthenticationException, PatchNotFoundException, UnableToContactServerException, ServerException, ConnectionException, LocalException {
        HttpURLConnection post = createPost("getPatch");
        post.addRequestProperty(SO6_PATCH_TICKET, Long.toString(ticket));

        int statusCode = executePost(post);

        switch (statusCode) {
        case STATUS_CODE_OK:
            StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1, "");

            String patchName = post.getHeaderField("patchName");

            if (!new File(downloadDir).exists()) {
                new File(downloadDir).mkdirs();
            }

            String patchPathName = downloadDir + File.separator + patchName;
            MonitoredInputStream mis;

            try {
                mis = new MonitoredInputStream(post.getInputStream(), post.getContentLength());
            } catch (IOException e) {
                throw new ConnectionException(e);
            }

            mis.setComment("Download finished", "Downloading");

            try {
                FileOutputStream fos = new FileOutputStream(patchPathName);
                int length;
                byte[] buffer = new byte[BUFFER_SIZE];

                while ((length = mis.read(buffer)) != -1) {
                    fos.write(buffer, 0, length);
                }

                fos.close();
                mis.close();
            } catch (Exception e) {
                throw new LocalException(e);
            }

            StateMonitoring.getInstance().setXMLMonitoringEndSubCall();

            // post.disconnect();
            return patchPathName;

        case STATUS_CODE_AUTH_ERROR:
            throw new AuthenticationException();

        case STATUS_CODE_INVALIDE_TICKET:
        case STATUS_CODE_PATCH_NOT_FOUND:
            throw new PatchNotFoundException();

        case STATUS_CODE_SERVER_ERROR:default:
            throw new ServerException("Server error on getPatch. Status code=" + statusCode);
        }
    }

    public void sendPatch(long fromTicket, long toTicket, String patchFileName, boolean validate)
        throws AuthenticationException, InvalidTicketException, UnableToContactServerException, ServerException, ConnectionException, LocalException {
        sendPatch_1_4(fromTicket, toTicket, patchFileName, validate);
    }

    //    private void sendPatch_1_5(long fromTicket, long toTicket, String patchFileName, boolean validate)
    //        throws AuthenticationException, InvalideTicketException, UnableToContactServerException, ServerException, ConnectionException, LocalException {
    //        final File patch = new File(patchFileName);
    //
    //        try {
    //            final MonitoredInputStream mis = new MonitoredInputStream(new FileInputStream(patch), patch.length());
    //            URL url = new URL(serviceUrl);
    //
    //            HttpURLConnection post = (HttpURLConnection) url.openConnection();
    //
    //            // FIXME Only valid in JDK 1.5 (Fix the bug with client being behind a proxy)
    //            post.setFixedLengthStreamingMode((int) patch.length());
    //
    //            post.setDoInput(true);
    //            post.setDoOutput(true);
    //            post.setUseCaches(false);
    //            post.setRequestProperty("Connection", "Keep-Alive");
    //            post.setRequestMethod("POST");
    //            post.addRequestProperty(SO6_ACTION, "sendPatch");
    //            post.addRequestProperty(SO6_SYNCHRONIZER_URI, synchronizerURI);
    //
    //            // Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
    //            if ((login != null) && (password != null)) {
    //                basicHTTPLogin(login, password, post);
    //            }
    //
    //            try {
    //                post.addRequestProperty(SO6_PATCH_FROM_TICKET, Long.toString(fromTicket));
    //                post.addRequestProperty(SO6_PATCH_TO_TICKET, Long.toString(toTicket));
    //                post.addRequestProperty(SO6_VALIDATE_TICKET_NUMBER, Boolean.toString(validate));
    //                post.addRequestProperty("Accept", "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");
    //                post.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    //                post.addRequestProperty("Content-Length", String.valueOf(patch.length()));
    //
    //                OutputStreamWriter writer = new OutputStreamWriter(post.getOutputStream());
    //                int length;
    //                byte[] buffer = new byte[BUFFER_SIZE];
    //
    //                while ((length = mis.read(buffer)) != -1) {
    //                    String s = new String(buffer, "UTF-8");
    //                    writer.write(s.toCharArray(), 0, length);
    //                    writer.flush();
    //                }
    //
    //                writer.close();
    //            } catch (IOException e) {
    //                throw new LocalException(e);
    //            }
    //
    //            // response
    //            int statusCode = executePost(post);
    //
    //            switch (statusCode) {
    //            case 202:
    //
    //                // good response
    //                break;
    //
    //            case STATUS_CODE_AUTH_ERROR:
    //                throw new AuthenticationException();
    //
    //            case STATUS_CODE_INVALIDE_TICKET:
    //                throw new InvalideTicketException();
    //
    //            case STATUS_CODE_SERVER_ERROR:default:
    //                throw new ServerException("Server error on sendPatch. Status code=" + statusCode);
    //            }
    //        } catch (NumberFormatException e) {
    //            throw new LocalException(e);
    //        } catch (FileNotFoundException e) {
    //            throw new LocalException(e);
    //        } catch (MalformedURLException e) {
    //            throw new UnableToContactServerException();
    //        } catch (IOException e) {
    //            throw new LocalException(e);
    //        }
    //
    //        // post.disconnect();
    //    }
    private void sendPatch_1_4(long fromTicket, long toTicket, String patchFileName, boolean validate)
        throws AuthenticationException, InvalidTicketException, UnableToContactServerException, ServerException, ConnectionException, LocalException {
        final File patch = new File(patchFileName);

        try {
            final MonitoredInputStream mis = new MonitoredInputStream(new FileInputStream(patch), patch.length());

            //
            URL url = new URL(serviceUrl);

            //
            Socket socket = new Socket(url.getHost(), url.getPort());
            socket.setKeepAlive(true);

            OutputStream out = socket.getOutputStream();

            //
            OutputStreamWriter writer = new OutputStreamWriter(out);
            int responseStatus = Math.abs((int) System.currentTimeMillis());
            writer.write("POST " + url.getFile() + " HTTP/1.1\r\n");
            writer.write("Host: " + url.getHost() + ":" + url.getPort() + "\r\n");
            writer.write("Content-Length: " + patch.length() + "\r\n");
            writer.write(SO6_ACTION + ": " + "sendPatch" + "\r\n");
            writer.write(SO6_PATCH_FROM_TICKET + ": " + Long.toString(fromTicket) + "\r\n");
            writer.write(SO6_PATCH_TO_TICKET + ": " + Long.toString(toTicket) + "\r\n");
            writer.write(SO6_VALIDATE_TICKET_NUMBER + ": " + Boolean.toString(validate) + "\r\n");

            // writer.write("ResponseStatus: " + responseStatus + "\r\n");
            writer.write("login: " + login + "\r\n");
            writer.write("password: " + password + "\r\n");
            writer.write(SO6_SYNCHRONIZER_URI + ": " + synchronizerURI + "\r\n");
            writer.write("Cache-Control: no-cache\r\n");
            writer.write("Connection: Keep-Alive\r\n");
            writer.write("Pragma: no-cache\r\n");
            writer.write("User-Agent: Java/1.4.2_03\r\n");
            writer.write("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n");
            writer.write("Content-Type: application/x-www-form-urlencoded\r\n");
            writer.write("\r\n");
            writer.flush();

            int length;
            byte[] buffer = new byte[BUFFER_SIZE];

            while ((length = mis.read(buffer)) != -1) {
                out.write(buffer, 0, length);
                out.flush();
            }

            // out.close();
            // mis.close();
            // response
            InputStream in = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            LineNumberReader lnr = new LineNumberReader(reader);
            String line = lnr.readLine();
            socket.close();

            int statusCode = Integer.parseInt(line.split(" ")[1]);

            switch (statusCode) {
            case 202:

                // good response
                break;

            case STATUS_CODE_AUTH_ERROR:
                throw new AuthenticationException();

            case STATUS_CODE_INVALIDE_TICKET:
                throw new InvalidTicketException();

            case STATUS_CODE_SERVER_ERROR:default:
                throw new ServerException("Server error on sendPatch. Status code=" + statusCode);
            }
        } catch (NumberFormatException e) {
            throw new LocalException(e);
        } catch (FileNotFoundException e) {
            throw new LocalException(e);
        } catch (MalformedURLException e) {
            throw new UnableToContactServerException();
        } catch (UnknownHostException e) {
            throw new UnableToContactServerException();
        } catch (SocketException e) {
            throw new ConnectionException(e);
        } catch (IOException e) {
            throw new LocalException(e);
        }

        // post.disconnect();
    }

    public void addBinExt(String ext) throws AuthenticationException, UnableToContactServerException, ServerException, LocalException {
        HttpURLConnection post = createPost("addBinExt");

        try {
            OutputStreamWriter writer = new OutputStreamWriter(post.getOutputStream());
            writer.write(SO6_BIN_EXT);
            writer.write("=");
            writer.write(ext);
            writer.close();
        } catch (IOException e) {
            throw new LocalException(e);
        }

        // post.addParameter(SO6_BIN_EXT, ext);
        int statusCode = executePost(post);

        switch (statusCode) {
        case STATUS_CODE_OK:
            break;

        case STATUS_CODE_AUTH_ERROR:
            throw new AuthenticationException();

        case STATUS_CODE_SERVER_ERROR:default:
            throw new ServerException("Server error on addBinExt. Status code=" + statusCode);
        }

        // post.disconnect();
    }

    public void removeBinExt(String ext) throws AuthenticationException, UnableToContactServerException, ServerException, LocalException {
        HttpURLConnection post = createPost("removeBinExt");

        try {
            OutputStreamWriter writer = new OutputStreamWriter(post.getOutputStream());
            writer.write(SO6_BIN_EXT);
            writer.write("=");
            writer.write(ext);
            writer.close();
        } catch (IOException e) {
            throw new LocalException(e);
        }

        // post.addParameter(SO6_BIN_EXT, ext);
        int statusCode = executePost(post);

        switch (statusCode) {
        case STATUS_CODE_OK:
            break;

        case STATUS_CODE_AUTH_ERROR:
            throw new AuthenticationException();

        case STATUS_CODE_INVALIDE_TICKET:
        case STATUS_CODE_PATCH_NOT_FOUND:
        case STATUS_CODE_SERVER_ERROR:default:
            throw new ServerException("Server error on removeBinExt. Status code=" + statusCode);
        }

        // post.disconnect();
    }

    private String getResponseAsString(InputStream input)
        throws ServerException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        // return reader.readLine(); // assume response is a single-line
        // response ;d
        String s;
        StringBuffer buf = new StringBuffer();

        try {
            while ((s = reader.readLine()) != null) {
                buf.append(s);
                buf.append("\n");
            }

            return buf.toString();
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }

    public String getBinExt() throws AuthenticationException, LocalException, UnableToContactServerException, ServerException {
        HttpURLConnection post = createPost("getBinExt");
        int statusCode = executePost(post);

        switch (statusCode) {
        case STATUS_CODE_OK:

            String result;

            try {
                result = getResponseAsString(post.getInputStream());
            } catch (IOException e) {
                throw new LocalException(e);
            }

            // post.disconnect();
            return result;

        case STATUS_CODE_AUTH_ERROR:
            throw new AuthenticationException();

        case STATUS_CODE_INVALIDE_TICKET:
        case STATUS_CODE_PATCH_NOT_FOUND:
        case STATUS_CODE_SERVER_ERROR:default:
            throw new ServerException("Server error on getBinExt. Status code=" + statusCode);
        }
    }

    public long[][] listPatch() throws AuthenticationException, LocalException, UnableToContactServerException, ServerException {
        HttpURLConnection post = createPost("listPatch");
        int statusCode = executePost(post);

        switch (statusCode) {
        case STATUS_CODE_OK:

            String patchArray = null;

            try {
                patchArray = getResponseAsString(post.getInputStream());
            } catch (IOException e) {
                throw new LocalException(e);
            }

            // post.disconnect();
            StringTokenizer st = new StringTokenizer(patchArray, "\n");
            ArrayList patchs = new ArrayList();

            while (st.hasMoreTokens()) {
                patchs.add(st.nextToken());
            }

            long[][] result = new long[patchs.size()][3];

            for (int i = 0; i < result.length; i++) {
                result[i][0] = Long.parseLong(((String) patchs.get(i)).split(" ")[0]);
                result[i][1] = Long.parseLong(((String) patchs.get(i)).split(" ")[1]);
                result[i][2] = Long.parseLong(((String) patchs.get(i)).split(" ")[2]);
            }

            return result;

        case STATUS_CODE_AUTH_ERROR:
            throw new AuthenticationException();

        case STATUS_CODE_INVALIDE_TICKET:
        case STATUS_CODE_PATCH_NOT_FOUND:
        case STATUS_CODE_SERVER_ERROR:default:
            throw new ServerException("Server error on listPatch. Status code=" + statusCode);
        }
    }

    public String ping() throws ServerException, UnableToContactServerException, IOException {
        HttpURLConnection post = createPost("ping");
        int statusCode = executePost(post);

        switch (statusCode) {
        case STATUS_CODE_OK:

            String result = getResponseAsString(post.getInputStream());

            // post.disconnect();
            return result;

        case STATUS_CODE_AUTH_ERROR:
        case STATUS_CODE_INVALIDE_TICKET:
        case STATUS_CODE_PATCH_NOT_FOUND:
        case STATUS_CODE_SERVER_ERROR:default:
            throw new ServerException("Server error on ping. Status code=" + statusCode);
        }
    }

    public void createWorkspace(String workspaceId) throws AuthenticationException, ServerException, UnableToContactServerException {
        HttpURLConnection post = createPost("createWorkspace");
        post.addRequestProperty(SO6_WORKSPACE_ID, workspaceId);

        int statusCode = executePost(post);

        switch (statusCode) {
        case STATUS_CODE_OK:

            // post.disconnect();
            break;

        case STATUS_CODE_AUTH_ERROR:
            throw new AuthenticationException();

        case STATUS_CODE_SERVER_ERROR:default:
            throw new ServerException("Server error on createWorkspace. Status code=" + statusCode);
        }
    }

    public void removeWorkspace(String workspaceId) throws AuthenticationException, ServerException, UnableToContactServerException {
        HttpURLConnection post = createPost("removeWorkspace");
        post.addRequestProperty(SO6_WORKSPACE_ID, workspaceId);

        int statusCode = executePost(post);

        switch (statusCode) {
        case STATUS_CODE_OK:

            // post.disconnect();
            break;

        case STATUS_CODE_AUTH_ERROR:
            throw new AuthenticationException();

        case STATUS_CODE_SERVER_ERROR:default:
            throw new ServerException("Server error on removeWorkspace. Status code=" + statusCode);
        }
    }

    public void notifyWorkspaceConnections(String wsId, String[] queueIds)
        throws AuthenticationException, ServerException, UnableToContactServerException {
        // Not Yet implemented
        throw new ServerException("Not yet implemented (notifyWorkspaceConnections)");
    }

    public String addWsConnection(String wsId, String queueURI, String wscName, String wscPath)
        throws LocalException, AuthenticationException, ServerException, UnableToContactServerException {
        HttpURLConnection post = createPost("addWsConnection");
        post.addRequestProperty(SO6_WORKSPACE_ID, wsId);
        post.addRequestProperty(SO6_SYNCHRONIZER_URI, queueURI);
        post.addRequestProperty(SO6_WS_NAME, wscName);
        post.addRequestProperty(SO6_WSC_PATH, wscPath);

        int statusCode = executePost(post);

        switch (statusCode) {
        case STATUS_CODE_OK:

            try {
                return getResponseAsString(post.getInputStream());
            } catch (IOException e) {
                throw new LocalException(e);
            }

        // post.disconnect();
        case STATUS_CODE_AUTH_ERROR:
            throw new AuthenticationException();

        case STATUS_CODE_SERVER_ERROR:default:
            throw new ServerException("Server error on addWsConnection. Status code=" + statusCode);
        }
    }

    public void removeWsConnection(String wsId, String queueId)
        throws AuthenticationException, ServerException, UnableToContactServerException {
        HttpURLConnection post = createPost("removeWsConnection");
        post.addRequestProperty(SO6_WORKSPACE_ID, wsId);
        post.addRequestProperty(SO6_SYNCHRONIZER_URI, queueId);

        int statusCode = executePost(post);

        switch (statusCode) {
        case STATUS_CODE_OK:

            // post.disconnect();
            break;

        case STATUS_CODE_AUTH_ERROR:
            throw new AuthenticationException();

        case STATUS_CODE_SERVER_ERROR:default:
            throw new ServerException("Server error on removeWsConnection. Status code=" + statusCode);
        }
    }

    // DataflowReaderClientI
    public String getNetworkFromWorkspace(String wsId)
        throws LocalException, AuthenticationException, ServerException, UnableToContactServerException {
        HttpURLConnection post = createPost("getNetworkFromWorkspace");
        post.addRequestProperty(SO6_WORKSPACE_ID, wsId);

        int statusCode = executePost(post);

        switch (statusCode) {
        case STATUS_CODE_OK:

            try {
                File tmpFile = File.createTempFile("wsNetwork", null, new File(downloadDir));
                InputStream is = post.getInputStream();
                FileOutputStream fos = new FileOutputStream(tmpFile);
                int length;
                byte[] buffer = new byte[BUFFER_SIZE];

                while ((length = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, length);
                }

                fos.close();
                is.close();

                // post.disconnect();
                return tmpFile.getPath();
            } catch (Exception e) {
                throw new LocalException(e);
            }

        case STATUS_CODE_AUTH_ERROR:
            throw new AuthenticationException();

        case STATUS_CODE_SERVER_ERROR:default:
            throw new ServerException("Server error on getNetworkFromWorkspace. Status code=" + statusCode);
        }
    }

    public String getNetworkFromQueue(String queueId) throws LocalException, AuthenticationException, ServerException, UnableToContactServerException {
        HttpURLConnection post = createPost("getNetworkFromWorkspace");
        post.addRequestProperty(SO6_QUEUE_ID, queueId);

        int statusCode = executePost(post);

        switch (statusCode) {
        case STATUS_CODE_OK:

            try {
                File tmpFile = File.createTempFile("queueNetwork", null, new File(downloadDir));
                InputStream is = post.getInputStream();
                FileOutputStream fos = new FileOutputStream(tmpFile);
                int length;
                byte[] buffer = new byte[BUFFER_SIZE];

                while ((length = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, length);
                }

                fos.close();
                is.close();

                // post.disconnect();
                return tmpFile.getPath();
            } catch (Exception e) {
                throw new LocalException(e);
            }

        case STATUS_CODE_AUTH_ERROR:
            throw new AuthenticationException();

        case STATUS_CODE_SERVER_ERROR:default:
            throw new ServerException("Server error on getNetworkFromWorkspace. Status code=" + statusCode);
        }
    }

    // Workspace listener
    public void notifyQueue(long ticket) throws AuthenticationException, ServerException, UnableToContactServerException {
        HttpURLConnection post = createPost("notifyQueue");
        post.addRequestProperty(SO6_REPLICA_URI, replicaURI);
        post.addRequestProperty(SO6_REPLICA_TICKET, Long.toString(ticket));

        int statusCode = executePost(post);

        switch (statusCode) {
        case STATUS_CODE_OK:

            // post.disconnect();
            return;

        case STATUS_CODE_AUTH_ERROR:
            throw new AuthenticationException();

        case STATUS_CODE_SERVER_ERROR:default:
            throw new ServerException("Server error on notifyQueue. Status code=" + statusCode);
        }
    }

    public String getPatch(long fromTicket, long toTicket)
        throws PatchNotFoundException, LocalException, AuthenticationException, UnableToContactServerException, ServerException, ConnectionException {
        HttpURLConnection post = createPost("getPatch2");
        post.addRequestProperty(SO6_PATCH_FROM_TICKET, Long.toString(fromTicket));
        post.addRequestProperty(SO6_PATCH_TO_TICKET, Long.toString(toTicket));

        int statusCode = executePost(post);

        switch (statusCode) {
        case STATUS_CODE_OK:
            StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1, "");

            String patchName = post.getHeaderField("patchName");

            // TODO check
            String patchPathName = downloadDir + File.separator + System.getProperty("user.name") + "_" + patchName;
            MonitoredInputStream mis;

            try {
                mis = new MonitoredInputStream(post.getInputStream(), post.getContentLength());
            } catch (IOException e) {
                throw new ConnectionException(e);
            }

            mis.setComment("Download finished", "Downloading");

            try {
                FileOutputStream fos = new FileOutputStream(patchPathName);
                int length;
                byte[] buffer = new byte[BUFFER_SIZE];

                while ((length = mis.read(buffer)) != -1) {
                    fos.write(buffer, 0, length);
                }

                fos.close();
                mis.close();
            } catch (Exception e) {
                throw new LocalException(e);
            }

            StateMonitoring.getInstance().setXMLMonitoringEndSubCall();

            // post.disconnect();
            return patchPathName;

        case STATUS_CODE_AUTH_ERROR:
            throw new AuthenticationException();

        case STATUS_CODE_INVALIDE_TICKET:
        case STATUS_CODE_PATCH_NOT_FOUND:
            throw new PatchNotFoundException();

        case STATUS_CODE_SERVER_ERROR:default:
            throw new ServerException("Server error on getPatch(2). Status code=" + statusCode);
        }
    }

    public static String[] getInternalPropertyList() {
        return new String[] { SO6_QUEUE_ID, SO6_LOGIN, SO6_PASSWORD, SO6_SERVICE_URL, SO6_CONNECTION_ID, SO6_XML_DETECT };
    }

    // /////////////////////////////////////////
    public class HttpConnection {
        // Holds target port number for Connection.
        private int HTTP_PORT = 80;

        // Holds target url.
        private String resource;

        // Holds target server address.
        private String host;

        // Holds target file address.
        private String file;

        // Setting defalt HTTP request method to Get.
        private String method = "GET";

        // Holds body length of request.
        private String contentLen = "";

        // Setting default content Type to text/html.
        private String type = "text/html";
        private String contentType;

        // Socket object for connection.
        private Socket httpSocket = null;

        // Holds all HTTP headers that our class will initialize.
        private StringBuffer defaultHTTPHeaders;

        // Holds all HTTP headers that user will initialize.
        private StringBuffer userDefinedHTTPHeaders = new StringBuffer();
        private InputStream in = null;
        private OutputStream out = null;

        // Constructor with URL and Port number as argument.
        // It opens a socket connection at provided URL.
        public HttpConnection(URL url) throws UnknownHostException, IOException {
            if (url.getPort() > 0) {
                HTTP_PORT = url.getPort();
            }

            // Parses the passed URL e.g. "http://www.xyz.com/tariqspage/soap "
            // and extract Host address i.e. "www.xyz.com"
            // and File name i.e. "/tariqspage/soap" from URL.
            file = url.getPath();
            host = url.getHost();
            httpSocket = new Socket(host, HTTP_PORT);
            defaultHTTPHeaders = new StringBuffer();
        }

        // Returns input stream for received data.
        public InputStream getInputStream() throws IOException {
            return in = httpSocket.getInputStream();
        }

        // Returns output stream for data to be sent.
        public OutputStream getOutputStream() throws IOException {
            return out = httpSocket.getOutputStream();
        }

        // Set request method for http request.
        public void setRequestMethod(String method) {
            this.method = method;
        }

        // Set Content Type Header for request.
        public void setContentType(String type) {
            this.type = type;
        }

        // Sets content length of the request data.
        public void setContentLength(int length) {
            contentLen = "Content-length: " + String.valueOf(length) + "\r\n";
        }

        // Returs Content Type Header.
        public String getContentType() {
            return this.contentType;
        }

        // Returs Request Method Header.
        public String getRequestMethod() {
            return this.method;
        }

        public void setRequestProperty(String name, String value) {
            userDefinedHTTPHeaders.append(name + ": " + value + "\r\n");
        } // setRequestProperty()

        // Concatenating headers from buffer strings
        // into a single String and Returns it.
        public String getHeaders() {
            defaultHTTPHeaders.append(method + " " + file + " HTTP/1.1\r\n");
            defaultHTTPHeaders.append("Host: " + host + "\r\n");
            defaultHTTPHeaders.append("Content-type: " + type + "; charset=utf-8\r\n");
            defaultHTTPHeaders.append(contentLen);

            // Appending empty line at end of headers.
            userDefinedHTTPHeaders.append("\r\n");

            // Concatenating two string buffers containing headers
            // into single String named "Headers".
            String headers = defaultHTTPHeaders.toString();
            headers += userDefinedHTTPHeaders.toString();

            return headers;
        } // getHeaders()
    } // class
}
