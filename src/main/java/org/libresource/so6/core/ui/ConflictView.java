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

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.exec.FindConflict;
import org.libresource.so6.core.interfaces.ConflictViewer;
import org.libresource.so6.core.ui.util.Wizard;
import org.libresource.so6.core.ui.util.WizardComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.File;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * @author smack
 */
public class ConflictView extends JPanel implements WizardComponent, MouseListener {
    private JList list;
    private ArrayList listData;
    private LocalListModel listModel;
    private WsConnection wsc;
    private Wizard wizard;
    private String wscPath;
    private JLabel title;
    private ConflictViewer conflictViewer;

    public ConflictView(String wscPath) throws Exception {
        super(new BorderLayout());
        this.wscPath = wscPath;
        listModel = new LocalListModel();
        list = new JList(listModel);
        title = new JLabel("Files in conflict");
        add(new JScrollPane(list), BorderLayout.CENTER);
        add(title, BorderLayout.NORTH);
        list.addMouseListener(this);
        listData = new ArrayList();
        wsc = new WsConnection(wscPath);
        listData.addAll(FindConflict.searchConflict(wscPath));
    }

    public void search() throws Exception {
        listData.clear();
        listData.addAll(FindConflict.searchConflict(wscPath));
        listModel.update();
    }

    public void setConflictViewer(ConflictViewer conflictViewer) {
        this.conflictViewer = conflictViewer;
    }

    public void setStyle(Color back, Color forground) {
        list.setBackground(forground);
        title.setBackground(back);
        setBackground(back);
    }

    public void mouseClicked(MouseEvent e) {
        if ((e.getClickCount() == 2) && (list.getSelectedIndex() != -1)) {
            try {
                conflictViewer.showConflictEditor(wsc.getPath() + File.separator + ((FindConflict.ConflictFile) list.getSelectedValue()).getPath());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }

    public Wizard getWizard() {
        return wizard;
    }

    public class LocalListModel extends AbstractListModel {
        public void update() {
            fireContentsChanged(this, 0, getSize());
        }

        public Object getElementAt(int index) {
            return listData.get(index);
        }

        public int getSize() {
            return listData.size();
        }
    }
}
