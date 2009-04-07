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
import org.libresource.so6.core.ui.MultipleView;
import org.libresource.so6.core.ui.TextFieldView;
import org.libresource.so6.core.ui.TextView;
import org.libresource.so6.core.ui.util.StyledUI;
import org.libresource.so6.core.ui.util.Wizard;
import org.libresource.so6.core.ui.util.WizardListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * @author smack
 */
public class Rename extends JPanel implements WizardListener, StyledUI {
    private TextFieldView oldPath;
    private TextFieldView newPath;
    private MultipleView view;
    private TextView text;
    private String wscPath;

    // 
    private boolean jobDone = false;

    //
    private Wizard wizard;

    public Rename(String wscPath, String from, String to)
        throws Exception {
        this.wscPath = wscPath;

        // Make views
        view = new MultipleView("Rename");
        oldPath = new TextFieldView("Source file");
        newPath = new TextFieldView("Destination file");

        //
        if (from != null) {
            oldPath.setContent(from);
        }

        if (to != null) {
            newPath.setContent(to);
        }

        //
        view.addView(oldPath);
        view.addView(newPath);
        text = new TextView("Report");
        text.setContent("Rename currently working");
        text.setEditable(false);

        //
        wizard = new Wizard(this);
        wizard.addComponent(view);
        wizard.addComponent(text);
        wizard.renderView(0);
        wizard.renderButtons();

        //
        setLayout(new BorderLayout());
        add(wizard, BorderLayout.CENTER);
    }

    public void doTheJob() {
        try {
            if (!jobDone) {
                new org.libresource.so6.core.exec.Rename(wscPath, oldPath.getContent(), newPath.getContent());
                text.setContent("The file " + oldPath.getContent() + " has been renamed to " + newPath.getContent());
            }

            jobDone = true;
            wizard.renderButtons();
        } catch (Exception e) {
            e.printStackTrace();
            text.setContent("Error while trying to rename file " + oldPath.getContent() + " to " + newPath.getContent() + " : " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                "Error while trying to rename file " + oldPath.getContent() + " to " + newPath.getContent() + " : " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: wsPath");
            System.err.println(" (1) wsPath: path of the file " + WsConnection.SO6_WSC_FILE);
            System.exit(0);
        }

        String wscPath = args[0];
        String src = "";
        String dst = "";
        Rename rename = new Rename(wscPath, src, dst);
        rename.setStyle(Color.white, Color.decode("#eeeeee"));

        // Make frame
        JFrame f = new JFrame("Rename a file on " + wscPath);
        f.getContentPane().add(rename, BorderLayout.CENTER);
        f.setSize(400, 180);
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
        if (currentHistoryPosition == 1) {
            Thread thread = new Thread(new Runnable() {
                        public void run() {
                            doTheJob();
                        }
                    });
            thread.setPriority(Thread.NORM_PRIORITY);
            thread.start();
        } else {
            wizard.renderButtons();
        }

        return false;
    }

    public void setStyle(Color back, Color forground) {
        wizard.setStyle(back, forground);
    }

    public void setCloser(Closer closer) {
        wizard.setCloser(closer);
    }
}
