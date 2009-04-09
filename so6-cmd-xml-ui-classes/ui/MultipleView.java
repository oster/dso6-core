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

import org.libresource.so6.core.ui.util.Wizard;
import org.libresource.so6.core.ui.util.WizardComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


/**
 * @author smack
 */
public class MultipleView extends JPanel implements WizardComponent {
    private Wizard wizard;
    private JPanel content;
    private ArrayList views;

    public MultipleView(String title_) {
        super(new BorderLayout());
        content = new JPanel();

        BoxLayout layout = new BoxLayout(content, BoxLayout.Y_AXIS);
        content.setLayout(layout);
        add(content, BorderLayout.CENTER);
        views = new ArrayList();
        setBorder(BorderFactory.createTitledBorder(LineBorder.createGrayLineBorder(), title_));
    }

    public void addView(WizardComponent view) {
        views.add(view);
        content.add((Component) view);
    }

    public void setStyle(Color back, Color forground) {
        setBackground(back);

        for (Iterator i = views.iterator(); i.hasNext();) {
            ((WizardComponent) i.next()).setStyle(back, forground);
        }
    }

    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }

    public Wizard getWizard() {
        return wizard;
    }
}
