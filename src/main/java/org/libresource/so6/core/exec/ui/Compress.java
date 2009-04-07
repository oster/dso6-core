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
package org.libresource.so6.core.exec.ui;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.interfaces.Closer;
import org.libresource.so6.core.ui.BasicMenu;
import org.libresource.so6.core.ui.BasicProgressView;
import org.libresource.so6.core.ui.TextView;
import org.libresource.so6.core.ui.util.StyledUI;
import org.libresource.so6.core.ui.util.Wizard;
import org.libresource.so6.core.ui.util.WizardComponent;
import org.libresource.so6.core.ui.util.WizardListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * @author smack
 */
public class Compress extends JPanel implements WizardListener, StyledUI {
    private BasicMenu menuBar;

    //
    private BasicProgressView progressView;
    private TextView reportView;

    // 
    private boolean jobDone = false;

    //
    private Wizard wizard;

    public Compress(String wscPath) throws Exception {
        // Make views
        progressView = new BasicProgressView(wscPath);
        reportView = new TextView("Report");
        reportView.setEditable(false);

        // Make menu
        menuBar = new BasicMenu();
        menuBar.setProgressView(progressView);

        //
        wizard = new Wizard(this);
        wizard.setMenuBar(menuBar);
        wizard.addComponent(progressView);
        wizard.addComponent(reportView);
        wizard.renderView(0);

        //
        setLayout(new BorderLayout());
        add(wizard, BorderLayout.CENTER);
    }

    public BasicMenu getMenu() {
        return menuBar;
    }

    public WizardComponent getProgressView() {
        return progressView;
    }

    public void doTheJob() {
        if (!jobDone) {
            progressView.sendCurrentCompressState();
        }

        jobDone = true;
        progressView.getWizard().renderButtons();
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: wsPath");
            System.err.println(" (1) wsPath : path of the file " + WsConnection.SO6_WSC_FILE);
            System.exit(0);
        }

        String wscPath = args[0];
        Compress compress = new Compress(wscPath);
        compress.setStyle(Color.white, Color.decode("#eeeeee"));

        // Make frame
        JFrame f = new JFrame("Compress the connection : " + wscPath);
        f.getContentPane().add(compress, BorderLayout.CENTER);
        f.setSize(400, 215);
        f.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
        f.setLocation(((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - f.getWidth()) / 2,
            ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - f.getHeight()) / 2);
        f.setVisible(true);
    }

    public boolean setCurrentPosition(int currentHistoryPosition) {
        // ask comment -> commit <-> report
        if (currentHistoryPosition == 0) {
            Thread thread = new Thread(new Runnable() {
                        public void run() {
                            doTheJob();
                        }
                    });
            thread.setPriority(Thread.NORM_PRIORITY);
            thread.start();
        } else {
            reportView.getWizard().renderButtons();
        }

        return true;
    }

    public void setStyle(Color back, Color forground) {
        wizard.setStyle(back, forground);
    }

    public void setCloser(Closer closer) {
        wizard.setCloser(closer);
        menuBar.setCloser(closer);
    }
}
