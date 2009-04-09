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
package org.libresource.so6.core.ui.util;

import org.libresource.so6.core.interfaces.Closer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * @author smack
 */
public class Wizard extends JPanel implements ActionListener, StyledUI {
    private ArrayList componentStream;
    private int componentNumber;

    //
    private JPanel content;
    private JPanel buttonsLine;
    private JButton next;
    private JButton previous;
    private JButton cancel;

    //
    private Component menu;
    private int min;
    private WizardListener listener;
    private Closer closer;

    public Wizard(WizardListener listener) {
        this.listener = listener;
        componentStream = new ArrayList();
        componentNumber = 0;
        min = 0;

        // init ui
        content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonsLine = new JPanel();

        BoxLayout layout = new BoxLayout(buttonsLine, BoxLayout.X_AXIS);
        buttonsLine.setLayout(layout);
        buttonsLine.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        //
        next = new JButton("Next");
        next.setActionCommand("NEXT");
        next.addActionListener(this);
        previous = new JButton("Previous");
        previous.setActionCommand("PREVIOUS");
        previous.addActionListener(this);
        cancel = new JButton("Cancel");
        cancel.setActionCommand("CANCEL");
        cancel.addActionListener(this);

        //
        buttonsLine.add(Box.createHorizontalGlue());
        buttonsLine.add(cancel);
        buttonsLine.add(Box.createRigidArea(new Dimension(5, 5)));
        buttonsLine.add(previous);
        buttonsLine.add(Box.createRigidArea(new Dimension(5, 5)));
        buttonsLine.add(next);

        //
        setLayout(new BorderLayout());
        add(content, BorderLayout.CENTER);
        add(buttonsLine, BorderLayout.SOUTH);
    }

    public void setStyle(Color background, Color foreground) {
        cancel.setBackground(foreground);
        previous.setBackground(foreground);
        next.setBackground(foreground);

        //
        content.setBackground(background);
        buttonsLine.setBackground(background);

        //
        if (menu != null) {
            ((StyledUI) menu).setStyle(background, foreground);
        }

        //
        for (Iterator i = componentStream.iterator(); i.hasNext();) {
            ((StyledUI) i.next()).setStyle(background, foreground);
        }
    }

    public void setCloser(Closer closer) {
        this.closer = closer;
    }

    public void setMenuBar(StyledUI menu_) {
        if (menu != null) {
            remove(menu);
        }

        menu = (Component) menu_;
        add(menu, BorderLayout.NORTH);
    }

    public void addComponent(WizardComponent component) {
        componentStream.add(component);
        component.setWizard(this);
    }

    public void removeAllComponent() {
        componentStream.clear();
        componentNumber = 0;
    }

    public void previousView() {
        componentNumber--;
        renderView(+1);
    }

    public void nextView() {
        componentNumber++;
        renderView(-1);
    }

    public void renderView(final int inc) {
        if (componentStream.get(componentNumber) != null) {
            content.remove((Component) componentStream.get(componentNumber + inc));
            content.add((Component) componentStream.get(componentNumber), BorderLayout.CENTER);
            content.validate();
            content.repaint();
            previous.setEnabled(false);
            next.setEnabled(false);
            repaint();

            Thread currentThread = new Thread(new Runnable() {
                        public void run() {
                            if (!listener.setCurrentPosition(componentNumber) && (inc == -1)) {
                                min++;
                            }
                        }
                    });
            currentThread.setPriority(Thread.MIN_PRIORITY);
            currentThread.start();
        }
    }

    public void renderButtons() {
        if (componentNumber > min) {
            previous.setEnabled(true);
        } else {
            previous.setEnabled(false);
        }

        if (componentNumber < (componentStream.size() - 1)) {
            next.setEnabled(true);
            next.setText("Next");
            next.setActionCommand("NEXT");
        } else {
            next.setEnabled(true);
            next.setText("Done");
            next.setActionCommand("EXIT");
        }

        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("PREVIOUS")) {
            previous.setEnabled(false);
            previousView();
        } else if (command.equals("NEXT")) {
            next.setEnabled(false);
            nextView();
        } else if (command.equals("EXIT")) {
            listener.setCurrentPosition(componentNumber + 1);

            if (closer != null) {
                closer.exit();
            } else {
                System.exit(0);
            }
        } else if (command.equals("CANCEL")) {
            if (closer != null) {
                closer.exit();
            } else {
                System.exit(0);
            }
        }
    }
}
