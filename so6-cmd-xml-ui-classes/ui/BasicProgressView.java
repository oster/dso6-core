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
package org.libresource.so6.core.ui;

import org.libresource.so6.core.FileLockedException;
import org.libresource.so6.core.WorkspaceCorruptedException;
import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.client.AuthenticationException;
import org.libresource.so6.core.client.ClientI;
import org.libresource.so6.core.client.ClientIServletImpl;
import org.libresource.so6.core.client.LocalException;
import org.libresource.so6.core.client.ServerException;
import org.libresource.so6.core.client.WorkspaceListener;
import org.libresource.so6.core.engine.log.LogHandler;
import org.libresource.so6.core.engine.log.LogPrinter;
import org.libresource.so6.core.engine.log.monitoring.ProgressView;
import org.libresource.so6.core.exec.ui.tools.CheckWscParameters;
import org.libresource.so6.core.ui.util.Wizard;
import org.libresource.so6.core.ui.util.WizardComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;

import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * @author smack
 */
public class BasicProgressView extends JPanel implements WizardComponent, LogPrinter {
    private ProgressView view;
    private WsConnection wsc;
    private String wsPath;

    //
    protected JTextArea log = null;
    protected JFrame logFrame = null;
    private Wizard wizard;

    public BasicProgressView(String wsPath) throws Exception {
        super(new BorderLayout());
        this.wsPath = wsPath;
        wsc = new WsConnection(wsPath);

        /*
           // add the chek params and read password
           CheckWscParameters checkWscParameters = new CheckWscParameters(wsc.getProperty(ClientIServletImpl.SO6_SERVICE_URL),
                   wsc.getProperty(ClientI.SO6_QUEUE_ID), wsc.getProperty(wsc.PATH), wsc.getProperty(ClientI.SO6_LOGIN), wsc.getProperty(wsc.WS_NAME),
                   wsc.getProperty(wsc.SYNC_CLIENT_NAME), null);
           // get props
           Properties props = checkWscParameters.getWscProps();
           String serviceUrl = (String) props.get("serviceUrl");
           String basePath = (String) props.get("basePath");
           String wsName = (String) props.get("wsName");
           String queueId = (String) props.get("synchronizerURI");
           String login = (String) props.get("login");
           String password = (String) props.get("password");
           // save props
           Properties newProps = new Properties();
           newProps.put(ClientIServletImpl.SO6_SERVICE_URL, serviceUrl);
           newProps.put(ClientI.SO6_QUEUE_ID, queueId);
           newProps.put(wsc.PATH, basePath);
           newProps.put(ClientI.SO6_LOGIN, login);
           newProps.put(wsc.WS_NAME, wsName);
           newProps.put(ClientI.SO6_PASSWORD, password);
           wsc.updateProp(newProps);
           wsc.save();
         */
        //
        log = new JTextArea();
        log.setEditable(false);
        logFrame = new JFrame("Execution log");
        logFrame.getContentPane().add(new JScrollPane(log));
        logFrame.setSize(400, 200);
        logFrame.setLocation(((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - logFrame.getWidth()) / 2,
            (((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - getHeight()) / 2) + 110);

        //
        view = new ProgressView();
        add(view, BorderLayout.CENTER);
    }

    public void setStyle(Color back, Color forground) {
        view.setStyle(back, forground);
    }

    public void publish(LogRecord record) {
        printMessage(record.getMessage());
    }

    public void printMessage(String message) {
        log.append(message + "\n");
        log.setCaretPosition(log.getText().length());
    }

    public void printError(Exception e) {
        printMessage("###\n" + e.getMessage() + "\n###");

        StackTraceElement[] stack = e.getStackTrace();

        for (int i = 0; i < stack.length; i++) {
            printMessage("# " + stack[i].getClassName() + ": line " + stack[i].getLineNumber());
        }
    }

    public void initLog() {
        Handler[] handler = Logger.getLogger("ui.log").getHandlers();

        for (int i = 0; i < handler.length; i++) {
            Logger.getLogger("ui.log").removeHandler(handler[i]);
        }

        //
        Logger.getLogger("ui.log").setUseParentHandlers(false);
        Logger.getLogger("ui.log").addHandler(new LogHandler(this));
    }

    public void showLog() {
        logFrame.setVisible(true);
    }

    public void hideLog() {
        logFrame.setVisible(false);
    }

    public boolean isLogShow() {
        return logFrame.isVisible();
    }

    public void update() {
        initLog();

        try {
            wsc.getClient();
            wsc.update();

            if (wsc.getClient() instanceof WorkspaceListener) {
                ((WorkspaceListener) wsc.getClient()).notifyQueue(wsc.getNs());
            }
        } catch (AuthenticationException e) {
            String message = "Authentication error: Invalide login or password.";
            printMessage(message);
            printError(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, message);
        } catch (ServerException e) {
            String message = "Server error: " + e.getMessage();
            printMessage(message);
            printError(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, message);
        } catch (LocalException e) {
            String message = "Local error: " + e.getMessage();
            printMessage(message);
            printError(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, message);
        } catch (WorkspaceCorruptedException e) {
            String message = "<html>Your local workspace is corrupted. <br>Please restore it</html>";
            printMessage(message);
            printError(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, message);
        } catch (FileLockedException e) {
            String message = "<html>" + e.getMessage() + " <br>Please close the application that is locking this file</html>";
            printMessage(message);
            printError(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, message);
        } catch (Exception e) {
            String message = "Forget exception : " + e.getClass().getName();
            printMessage(message);
            printError(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, message);
        }

        printMessage("Update done");

        //		Print report
        if (wsc.getReport().length() > 0) {
            printMessage("\n*** Report ***");
            printMessage(wsc.getReport());
        }
    }

    public void commit(String comment) {
        initLog();

        try {
            wsc.getClient();
            wsc.commit(comment);

            if (wsc.getClient() instanceof WorkspaceListener) {
                ((WorkspaceListener) wsc.getClient()).notifyQueue(wsc.getNs());
            }
        } catch (AuthenticationException e) {
            String message = "Authentication error: Invalide login or password.";
            printMessage(message);
            printError(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, message);
        } catch (ServerException e) {
            String message = "Server error: " + e.getMessage();
            printMessage(message);
            printError(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, message);
        } catch (LocalException e) {
            String message = "Local error: " + e.getMessage();
            printMessage(message);
            printError(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, message);
        } catch (WorkspaceCorruptedException e) {
            String message = "<html>Your local workspace is corrupted. <br>Please restore it</html>";
            printMessage(message);
            printError(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, message);
        } catch (FileLockedException e) {
            String message = "<html>" + e.getMessage() + " <br>Please close the application that is locking this file</html>";
            printMessage(message);
            printError(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, message);
        } catch (Exception e) {
            String message = "Forget exception : " + e.getClass().getName();
            printMessage(message);
            printError(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, message);
        }

        printMessage("Commit done");

        // Print report
        if (wsc.getReport().length() > 0) {
            printMessage("\n*** Report ***");
            printMessage(wsc.getReport());
        }
    }

    public void sendCurrentCompressState() {
        initLog();

        try {
            wsc.getClient();
            wsc.sendCurrentCompressState();
        } catch (AuthenticationException e) {
            String message = "Authentication error: Invalide login or password.";
            printMessage(message);
            printError(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, message);
        } catch (ServerException e) {
            String message = "Server error: " + e.getMessage();
            printMessage(message);
            printError(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, message);
        } catch (LocalException e) {
            String message = "Local error: " + e.getMessage();
            printMessage(message);
            printError(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, message);
        } catch (WorkspaceCorruptedException e) {
            String message = "<html>Your local workspace is corrupted. <br>Please restore it</html>";
            printMessage(message);
            printError(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, message);
        } catch (FileLockedException e) {
            String message = "<html>" + e.getMessage() + " <br>Please close the application that is locking this file</html>";
            printMessage(message);
            printError(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, message);
        } catch (Exception e) {
            String message = "Forget exception : " + e.getClass().getName();
            printMessage(message);
            printError(e);
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, message);
        }

        printMessage("Compress done");

        // Print report
        if (wsc.getReport().length() > 0) {
            printMessage("\n*** Report ***");
            printMessage(wsc.getReport());
        }
    }

    public String getReport() {
        return wsc.getReport();
    }

    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }

    public Wizard getWizard() {
        return wizard;
    }
}
