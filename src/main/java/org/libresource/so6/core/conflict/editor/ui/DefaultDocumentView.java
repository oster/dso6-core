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
package org.libresource.so6.core.conflict.editor.ui;

import org.libresource.so6.core.conflict.editor.AbstractNode;
import org.libresource.so6.core.conflict.editor.ConflictNode;
import org.libresource.so6.core.conflict.editor.DocumentNode;
import org.libresource.so6.core.conflict.editor.TextNode;

import java.awt.Color;

import java.io.StringWriter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


/**
 * @author smack
 */
public class DefaultDocumentView extends JPanel {
    private DocumentNode root;
    private ArrayList rootViews;
    private Color txtColor = Color.white;
    private Color conflictColor = new Color(255, 200, 200);
    private Color selectedColor = new Color(200, 200, 255);
    private int selectedChild = -1;

    public DefaultDocumentView(DocumentNode root) {
        this.root = root;
        this.rootViews = new ArrayList();

        //
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);

        for (Enumeration e = root.children(); e.hasMoreElements();) {
            AbstractNode node = (AbstractNode) e.nextElement();
            JComponent component = makeNodeView(node);
            rootViews.add(component);
            add(component);
        }

        add(Box.createVerticalBox());
    }

    public int getNodePosition(AbstractNode node) {
        int childNumber = childNumber(node);
        double result = 0;

        for (int i = 0; i < childNumber; i++) {
            result += ((JComponent) rootViews.get(i)).getSize().getHeight();
        }

        //System.out.println(result);
        return (int) result;
    }

    private int childNumber(AbstractNode node) {
        AbstractNode parent = (AbstractNode) node.getParent();
        AbstractNode firstChild = node;

        while ((parent != null) && !(parent instanceof DocumentNode)) {
            firstChild = (AbstractNode) firstChild.getParent();
            parent = (AbstractNode) firstChild.getParent();
        }

        return root.getIndex(firstChild);
    }

    public void selectNode(AbstractNode node) {
        selectedChild = childNumber(node);
        update(false);
    }

    private JComponent makeNodeView(AbstractNode node) {
        if (node instanceof ConflictNode || node instanceof TextNode) {
            return new DefaultNodeView(node);
        }

        return new JLabel("Undefined view for node type: " + node.getClass().getName());
    }

    public void update(boolean contentUpdate) {
        int nb = 0;

        for (Iterator i = rootViews.iterator(); i.hasNext();) {
            DefaultNodeView view = (DefaultNodeView) i.next();
            view.update(contentUpdate);

            if (nb++ == selectedChild) {
                view.setBorder(BorderFactory.createLineBorder(selectedColor, 2));
            } else {
                view.setBorder(BorderFactory.createEmptyBorder());
            }
        }
    }

    public class DefaultNodeView extends JTextArea {
        private AbstractNode node;

        public DefaultNodeView(AbstractNode node) {
            this.node = node;
            setEditable(false);
            update(true);
        }

        public void update() {
            update(false);
        }

        public void update(boolean deepUpdate) {
            if (deepUpdate) {
                StringWriter writer = new StringWriter();

                try {
                    node.toDocument(writer, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!writer.getBuffer().toString().equals(getText())) {
                    setText(writer.getBuffer().toString());
                }
            }

            if (node instanceof ConflictNode) {
                setBackground(conflictColor);
            }

            if (node instanceof TextNode) {
                setBackground(txtColor);
            }
        }
    }
}
