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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * @author smack
 */
public class TextFieldView extends JPanel implements WizardComponent {
    private Wizard wizard;
    private JTextField txt;
    private JLabel title;

    public TextFieldView(String title_) {
        super(new BorderLayout());
        title = new JLabel(title_);
        txt = new JTextField();
        add(title, BorderLayout.CENTER);
        add(txt, BorderLayout.SOUTH);
    }

    public void setContent(String content) {
        txt.setText(content);
    }

    public String getContent() {
        return txt.getText();
    }

    public void setStyle(Color back, Color forground) {
        setBackground(back);
        txt.setBackground(forground);
        title.setBackground(back);
    }

    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }

    public Wizard getWizard() {
        return wizard;
    }
}
