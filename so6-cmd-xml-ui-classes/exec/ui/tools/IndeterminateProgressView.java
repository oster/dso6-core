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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


/**
 * @author smack
 */
public class IndeterminateProgressView extends JDialog {
    public IndeterminateProgressView(String title, String message) {
        super();
        setTitle(title);

        JProgressBar bar = new JProgressBar();
        bar.setIndeterminate(true);

        JLabel messageLabel = new JLabel(message);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel barPanel = new JPanel(new BorderLayout());
        barPanel.add(bar, BorderLayout.CENTER);
        barPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        barPanel.setBackground(Color.WHITE);
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(new GridLayout(2, 0));
        getContentPane().add(messageLabel);
        getContentPane().add(barPanel);
        pack();
        setLocation(((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - getWidth()) / 2,
            ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - getHeight()) / 2);
        setResizable(false);

        setVisible(true);
    }

    public static void main(String[] args) {
        new IndeterminateProgressView("Test", "Please wait during the download and the patch application...").addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
    }
}
