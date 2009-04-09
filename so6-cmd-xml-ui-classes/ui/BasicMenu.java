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

import org.libresource.so6.core.interfaces.Closer;
import org.libresource.so6.core.ui.util.StyledUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


/**
 * @author smack
 */
public class BasicMenu extends JMenuBar implements StyledUI, ActionListener {
    private ArrayList components;
    private BasicProgressView view;
    private Closer closer;

    public BasicMenu() {
        super();
        components = new ArrayList();

        JMenu menu = new JMenu("File");
        JMenuItem item = new JMenuItem("Quit");
        item.setActionCommand("EXIT");
        item.addActionListener(this);
        components.add(item);
        menu.add(item);
        components.add(menu);
        add(menu);
        menu = new JMenu("View");
        item = new JMenuItem("Show/Hide log");
        item.setActionCommand("LOG");
        item.addActionListener(this);
        components.add(item);
        menu.add(item);
        components.add(menu);
        add(menu);
    }

    public void setCloser(Closer closer) {
        this.closer = closer;
    }

    public void setProgressView(BasicProgressView view) {
        this.view = view;
    }

    public void setStyle(Color back, Color forground) {
        setBackground(back);

        for (Iterator i = components.iterator(); i.hasNext();) {
            ((Component) i.next()).setBackground(back);
        }
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("EXIT")) {
            if (closer != null) {
                closer.exit();
            } else {
                System.exit(0);
            }
        } else if (command.equals("LOG")) {
            if (view.isLogShow()) {
                view.hideLog();
            } else {
                view.showLog();
            }
        }
    }
}
