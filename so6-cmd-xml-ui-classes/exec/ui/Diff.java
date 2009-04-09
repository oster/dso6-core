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

import jlibdiff.vdiff.VDiff;

import org.libresource.so6.core.interfaces.Closer;
import org.libresource.so6.core.ui.MultipleView;
import org.libresource.so6.core.ui.TextFieldView;
import org.libresource.so6.core.ui.util.StyledUI;
import org.libresource.so6.core.ui.util.Wizard;
import org.libresource.so6.core.ui.util.WizardListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * @author smack
 */
public class Diff extends JPanel implements WizardListener, StyledUI {
    private TextFieldView oldPath;
    private TextFieldView newPath;
    private MultipleView view;
    private String wscPath;

    //
    private boolean jobDone = false;

    //
    private Wizard wizard;

    public Diff(String from, String to) throws Exception {
        // Make views
        view = new MultipleView("View diff between");
        oldPath = new TextFieldView("file");
        newPath = new TextFieldView("file");

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

        //
        wizard = new Wizard(this);
        wizard.addComponent(view);
        wizard.renderView(0);
        wizard.renderButtons();

        //
        setLayout(new BorderLayout());
        add(wizard, BorderLayout.CENTER);
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 0) {
            System.exit(0);
        }

        String src = "";
        String dst = "";
        Diff diff = new Diff(src, dst);
        diff.setStyle(Color.white, Color.decode("#eeeeee"));

        // Make frame
        JFrame f = new JFrame("Compute diff on text files");
        f.getContentPane().add(diff, BorderLayout.CENTER);
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
            File src = new File(oldPath.getContent());
            File dst = new File(newPath.getContent());

            if (src.exists() && src.isFile() && dst.exists() && dst.isFile()) {
                String[] param = new String[] { oldPath.getContent(), newPath.getContent() };
                JFrame f = new VDiff(oldPath.getContent(), newPath.getContent());

                for (int i = 0; i < f.getWindowListeners().length; i++)
                    f.removeWindowListener(f.getWindowListeners()[i]);

                f.setVisible(true);
            }
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
