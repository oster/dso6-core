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
import org.libresource.so6.core.tf.TextFileFunctions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;


/**
 * @author smack
 */
public class BoxDocumentView extends JPanel {
    private DocumentNode root;
    private ArrayList rootViews;
    private Color txtColor = Color.white;
    private Color conflictColor = new Color(255, 200, 200);

    public BoxDocumentView(DocumentNode root) {
        this.root = root;
        this.rootViews = new ArrayList();

        //
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);

        for (Enumeration e = root.children(); e.hasMoreElements();) {
            AbstractNode node = (AbstractNode) e.nextElement();
            JComponent component = makeNodeView(node);
            add(component);
        }

        add(Box.createVerticalBox());
    }

    private JComponent makeNodeView(AbstractNode node) {
        if (node instanceof ConflictNode) {
            return new ConflictNodeView((ConflictNode) node);
        } else if (node instanceof TextNode) {
            return new TextNodeView((TextNode) node);
        }

        return new JLabel("Undefined view for node type: " + node.getClass().getName());
    }

    public void update() {
        for (Iterator i = rootViews.iterator(); i.hasNext();) {
            ((AbstractNodeView) i.next()).repaint();
        }
    }

    //
    public abstract class AbstractNodeView extends JPanel implements MouseListener, ActionListener {
        private final int borderSize = 1;
        private Color DEFAULT_COLOR = new Color(200, 200, 255);
        private Color SELECTED_COLOR = new Color(100, 100, 255);
        private Color ADD_COLOR = new Color(200, 255, 200);
        private Color REMOVE_COLOR = new Color(255, 200, 200);
        private JPanel content;
        protected AbstractNode node;
        private JPopupMenu popup;

        public AbstractNodeView(AbstractNode node) {
            this.node = node;
            setLayout(new BorderLayout());
            content = new JPanel();
            setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
            add(content, BorderLayout.CENTER);
            addMouseListener(this);

            // popup
            popup = new JPopupMenu();

            JMenuItem view = new JMenuItem("Enabled");
            view.setActionCommand("VIEW");
            view.addActionListener(this);
            popup.add(view);
            addMouseListener(new PopupListener());
        }

        public void setTitle(String title) {
        }

        public JPanel getContent() {
            return content;
        }

        public void setBackgroundColor(Color color) {
            if (content != null) {
                content.setBackground(color);
                setBackground(color);
            }
        }

        public boolean isSelected() {
            //			if (root != null)
            //			return root.isSelected(this);
            return false;
        }

        public void repaint() {
            if (isSelected()) {
                setBackgroundColor(SELECTED_COLOR);
            } else {
                if (node != null) {
                    if (node.isAdded() || node.isRemoved()) {
                        if (node.isAdded()) {
                            setBackgroundColor(ADD_COLOR);
                        } else {
                            setBackgroundColor(REMOVE_COLOR);
                        }
                    } else {
                        if (node instanceof TextNode) {
                            setBackgroundColor(Color.WHITE);
                        } else {
                            setBackgroundColor(DEFAULT_COLOR);
                        }
                    }
                }
            }

            //
            super.repaint();
        }

        public void mouseClicked(MouseEvent e) {
            //	root.setSelectedView(this);
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("VIEW")) {
                node.setEnabled(!node.isEnabled());

                //root.setSelectedView(this);
            }
        }

        class PopupListener extends MouseAdapter {
            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }

            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }

            private void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        }
    }

    public class TextNodeView extends AbstractNodeView {
        private JTextPane text;

        public TextNodeView(TextNode node) {
            super(node);

            String txt = "";

            if (node.isAdded() || node.isRemoved()) {
                if (node.isAdded()) {
                    txt += "Added by ";
                } else {
                    txt += "Removed by ";
                }
            }

            if (node.getOrigine() != null) {
                txt += node.getOrigine();
            }

            setTitle(txt);

            StyledDocument doc = new DefaultStyledDocument();
            text = new JTextPane(doc);

            try {
                doc.insertString(0, node.getText(), new SimpleAttributeSet());
            } catch (BadLocationException e) {
                e.printStackTrace();
            }

            getContent().setLayout(new BorderLayout());
            getContent().add(text, BorderLayout.CENTER);
            text.addMouseListener(this);
            text.setEditable(false);
        }

        public void repaint() {
            if (text != null) {
                text.setVisible(node.isEnabled());
            }

            super.repaint();
        }

        public void setBackgroundColor(Color color) {
            super.setBackgroundColor(color);

            /*
             * if (text != null) text.setBackground(color);
             */
        }
    }

    public class ConflictNodeView extends AbstractNodeView {
        private JLabel split;

        public ConflictNodeView(ConflictNode node) {
            super(node);
            setBorder(BorderFactory.createEmptyBorder(2, 15, 2, 2));

            switch (node.getConflictType()) {
            case TextFileFunctions.ADD_ADD:
                setTitle("Conflict : ADD / ADD");

                break;

            case TextFileFunctions.ADD_REMOVE:
                setTitle("Conflict : ADD / REMOVE");

                break;

            case TextFileFunctions.REMOVE_ADD:
                setTitle("Conflict : REMOVE / ADD");

                break;
            }

            AbstractNode child1 = ((AbstractNode) node.getChildAt(0));
            AbstractNode child2 = ((AbstractNode) node.getChildAt(1));
            split = new JLabel(child1.getOrigine() + " / " + child2.getOrigine());
            split.setOpaque(true);

            BoxLayout layout = new BoxLayout(getContent(), BoxLayout.Y_AXIS);
            getContent().setLayout(layout);

            //getContent().add(child1.createView(root));
            //getContent().add(split);
            //getContent().add(child2.createView(root));
        }

        public void setBackgroundColor(Color color) {
            super.setBackgroundColor(color);

            if (split != null) {
                split.setBackground(color);
            }
        }
    }
}
