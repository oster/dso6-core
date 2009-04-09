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
package org.libresource.so6.core.engine.log.monitoring;

import org.libresource.so6.core.StateMonitoring;

import java.awt.Color;
import java.awt.Dimension;

import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;


/**
 * @author smack
 */
public class ProgressView extends JPanel implements Observer {
    private JProgressBar localBar;
    private JProgressBar globalBar;
    private JLabel localText;
    private JLabel globalText;
    private TitledBorder localBorder;
    private TitledBorder globalBorder;
    private JPanel lineLocal;
    private JPanel lineGlobal;
    private JPanel localView;
    private JPanel globalView;

    public ProgressView() {
        localBar = StateMonitoring.getInstance().getContext().getLocalProgressBar();
        globalBar = StateMonitoring.getInstance().getContext().getGlobalProgressBar();

        //
        localText = new JLabel("local");
        localText.setMaximumSize(new Dimension(500, 500));
        localText.setMinimumSize(new Dimension(5, 5));
        globalText = new JLabel("global");
        globalText.setMaximumSize(new Dimension(500, 500));
        globalText.setMinimumSize(new Dimension(5, 5));

        // 
        lineLocal = new JPanel();

        BoxLayout layoutLineLocal = new BoxLayout(lineLocal, BoxLayout.X_AXIS);
        lineLocal.setLayout(layoutLineLocal);
        lineLocal.add(localText);
        lineLocal.add(Box.createHorizontalGlue());
        lineGlobal = new JPanel();

        BoxLayout layoutLineGlobal = new BoxLayout(lineGlobal, BoxLayout.X_AXIS);
        lineGlobal.setLayout(layoutLineGlobal);
        lineGlobal.add(globalText);
        lineGlobal.add(Box.createHorizontalGlue());

        //
        localBorder = BorderFactory.createTitledBorder("Local action");
        globalBorder = BorderFactory.createTitledBorder("Global action");

        //
        localView = new JPanel();

        BoxLayout layoutLocal = new BoxLayout(localView, BoxLayout.Y_AXIS);
        localView.setLayout(layoutLocal);
        localView.setBorder(localBorder);
        localView.add(lineLocal);
        localView.add(localBar);

        //
        globalView = new JPanel();

        BoxLayout layoutGlobal = new BoxLayout(globalView, BoxLayout.Y_AXIS);
        globalView.setLayout(layoutGlobal);
        globalView.setBorder(globalBorder);
        globalView.add(lineGlobal);
        globalView.add(globalBar);

        //
        //setBorder(new EmptyBorder(5, 5, 5, 5));
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        add(localView);
        add(globalView);

        //
        setMaximumSize(new Dimension(200, 200));
        setMinimumSize(new Dimension(200, 200));

        //
        StateMonitoring.getInstance().addObserver(this);
    }

    public void setBackground(Color color) {
        if (localText != null) {
            localText.setBackground(color);
        }

        if (globalText != null) {
            globalText.setBackground(color);
        }

        if (localView != null) {
            localView.setBackground(color);
        }

        if (globalView != null) {
            globalView.setBackground(color);
        }

        if (lineLocal != null) {
            lineLocal.setBackground(color);
        }

        if (lineGlobal != null) {
            lineGlobal.setBackground(color);
        }
    }

    public void setStyle(Color back, Color forground) {
        setBackground(back);
        lineLocal.setBackground(back);
        lineGlobal.setBackground(back);
        localView.setBackground(back);
        globalView.setBackground(back);
    }

    public void update(Observable arg0, Object arg1) {
        globalText.setText(StateMonitoring.getInstance().getCurrentGlobalMessage());
        localText.setText(StateMonitoring.getInstance().getCurrentLocalMessage());
    }
}
