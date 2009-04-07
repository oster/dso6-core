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

import org.libresource.so6.core.StateMonitoring;
import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.client.WorkspaceListener;
import org.libresource.so6.core.engine.log.LogHandler;
import org.libresource.so6.core.engine.log.LogPrinter;
import org.libresource.so6.core.engine.log.LogUtils;
import org.libresource.so6.core.engine.log.monitoring.XMLLogHandler;
import org.libresource.so6.core.engine.log.monitoring.XMLMonitoringThread;
import org.libresource.so6.core.engine.log.monitoring.XMLProgressView;
import org.libresource.so6.core.ui.util.Wizard;
import org.libresource.so6.core.ui.util.WizardComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;

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
public class BasicProgressViewXML extends JPanel implements WizardComponent, LogPrinter {
    private XMLProgressView view;
    private WsConnection wsc;
    private String wsPath;

    //
    protected JTextArea log = null;
    protected JFrame logFrame = null;
    private Wizard wizard;

    public BasicProgressViewXML(String wsPath) throws Exception {
        super(new BorderLayout());
        this.wsPath = wsPath;
        wsc = new WsConnection(wsPath);

        final XMLLogHandler xmlLogHandler = new XMLLogHandler();

        //
        log = new JTextArea();
        log.setEditable(false);
        logFrame = new JFrame("Execution log");
        logFrame.getContentPane().add(new JScrollPane(log));
        logFrame.setSize(400, 200);
        logFrame.setLocation(((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - logFrame.getWidth()) / 2,
            (((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - getHeight()) / 2) + 110);

        //
        view = new XMLProgressView(xmlLogHandler);
        add(view, BorderLayout.CENTER);

        // init output
        LogUtils.removeAllHandlers(Logger.getLogger("ui.log"));
        LogUtils.removeAllHandlers(StateMonitoring.getInstance().getXMLMonitoringLogger());

        XMLMonitoringThread monitoringThread = new XMLMonitoringThread(xmlLogHandler);
        monitoringThread.start();
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

            try {
                wsc.update();

                if (wsc.getClient() instanceof WorkspaceListener) {
                    ((WorkspaceListener) wsc.getClient()).notifyQueue(wsc.getNs());
                }
            } catch (Exception e) {
                String message = "Error: while trying to update local workspace.";
                printMessage(message);
                printError(e);
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, message);
            }
        } catch (Exception e) {
            String message = "Error: Unable to instantiate client for synchronization";
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

            try {
                wsc.commit(comment);

                if (wsc.getClient() instanceof WorkspaceListener) {
                    ((WorkspaceListener) wsc.getClient()).notifyQueue(wsc.getNs());
                }
            } catch (Exception e) {
                String message = "Error: while trying to commit.";
                printMessage(message);
                printError(e);
                JOptionPane.showMessageDialog(this, message);
                e.printStackTrace();
            }
        } catch (Exception e) {
            String message = "Error: Unable to instantiate client for synchronization";
            printMessage(message);
            printError(e);
            JOptionPane.showMessageDialog(this, message);
            e.printStackTrace();
        }

        printMessage("Commit done");

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
