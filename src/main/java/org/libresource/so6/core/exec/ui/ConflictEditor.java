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

import org.libresource.so6.core.conflict.editor.AbstractNode;
import org.libresource.so6.core.conflict.editor.DocumentNode;
import org.libresource.so6.core.conflict.editor.TextNode;
import org.libresource.so6.core.conflict.editor.ui.DefaultDocumentView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.io.FileWriter;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;


/**
 * @author smack
 */
public class ConflictEditor extends JPanel implements ActionListener {
    private String fileName;
    private MyJTree conflictTree;
    private JScrollPane scrollView;
    private JPopupMenu popup;
    private DefaultTreeModel conflictTreeModel;
    private DocumentNode root;
    private DefaultDocumentView docView;
    Rectangle rect = new Rectangle(0, 10, 100, 100);

    public ConflictEditor(String fileName) throws Exception {
        this.fileName = fileName;
        root = new DocumentNode();
        root.load(fileName);

        // Menu
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem save = new JMenuItem("save current file as");
        save.setActionCommand("SAVE");
        save.addActionListener(this);

        JMenuItem exit = new JMenuItem("Quit");
        exit.setActionCommand("EXIT");
        exit.addActionListener(this);
        file.add(save);
        file.addSeparator();
        file.add(exit);
        menu.add(file);

        //
        popup = new JPopupMenu();

        JMenuItem view = new JMenuItem("View");
        view.setActionCommand("VIEW");
        view.addActionListener(this);

        //popup.add(view);
        JMenuItem edit = new JMenuItem("Edit");
        edit.setActionCommand("EDIT");
        edit.addActionListener(this);

        //popup.add(edit);
        //popup.addSeparator();
        JMenuItem delete = new JMenuItem("Hide / Show");
        delete.setActionCommand("SHOW-HIDE");
        delete.addActionListener(this);
        popup.add(delete);

        //
        conflictTreeModel = new DefaultTreeModel(root);
        conflictTree = new MyJTree(conflictTreeModel);
        conflictTree.setCellRenderer(new ConflictTreeRenderer());
        conflictTree.addTreeSelectionListener(new ConflictTreeListener());
        conflictTree.addMouseListener(new PopupListener());

        //
        docView = new DefaultDocumentView(root);
        scrollView = new JScrollPane(docView);

        //
        setLayout(new BorderLayout());
        add(menu, BorderLayout.NORTH);
        add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(conflictTree), scrollView), BorderLayout.CENTER);

        //
        ToolTipManager.sharedInstance().registerComponent(conflictTree);
        ToolTipManager.sharedInstance().setInitialDelay(0);
    }

    public static void main(String[] args) throws Exception {
        JFrame f = new JFrame("Conflict editor : " + args[0]);
        ConflictEditor ce = new ConflictEditor(args[0]);
        f.getContentPane().add(ce);
        f.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
        f.setSize(400, 400);
        f.setLocation(((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - f.getWidth()) / 2,
            ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - f.getHeight()) / 2);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        AbstractNode node = null;

        if (conflictTree.getSelectionPath() != null) {
            node = ((AbstractNode) conflictTree.getSelectionPath().getLastPathComponent());
        }

        //System.out.println(command);
        if (command.equals("SHOW-HIDE")) {
            node.setEnabled(!node.isEnabled());
            docView.update(true);
            docView.selectNode(node);
            docView.update(false);

            int viewY = docView.getNodePosition(node);
            scrollView.getViewport().setViewPosition(new Point(0, viewY));
        }

        if (command.equals("SAVE")) {
            try {
                JFileChooser chooser = new JFileChooser(new File(fileName).getParentFile());

                if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File output = chooser.getSelectedFile();
                    FileWriter fw = new FileWriter(output);
                    root.toDocument(fw, 0);
                    fw.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, e1.getMessage());
            }
        }

        if (command.equals("EXIT")) {
            System.exit(0);
        }
    }

    public class ConflictTreeRenderer extends JLabel implements TreeCellRenderer {
        public ConflictTreeRenderer() {
            setOpaque(true);
            setBorder(BorderFactory.createLineBorder(Color.white, 2));
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            setBackground(selected ? Color.lightGray : Color.white);
            setBorder(BorderFactory.createLineBorder(selected ? Color.blue : Color.white, 2));

            //if (value instanceof HTNode) {
            //    setBackground(((HTNode) value).getColor());
            //}
            if (!((AbstractNode) value).isEnabled()) {
                setBackground(Color.black);
            }

            if (value instanceof TextNode) {
                TextNode node = (TextNode) value;

                if (node.getParent() == null) {
                    setText("...");
                } else {
                    setText(node.getHTMLText());
                }
            } else {
                setText(value.toString());
            }

            return this;
        }
    }

    public class ConflictTreeListener implements TreeSelectionListener {
        public void valueChanged(TreeSelectionEvent e) {
            AbstractNode node = (AbstractNode) e.getPath().getLastPathComponent();
            docView.selectNode(node);

            int viewY = docView.getNodePosition(node);
            scrollView.getViewport().setViewPosition(new Point(0, viewY));
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
            if ((conflictTree.getSelectionPath() != null) && e.isPopupTrigger() && (conflictTree.getSelectionPath().getLastPathComponent() != null)) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    /*
       public void showHyperTree() {
           JFrame f = new JFrame("tree");
           f.getContentPane().add(new HyperTree(root).getView());
           f.setSize(400, 400);
           f.setVisible(true);
       }
     */
    public class MyJTree extends JTree {
        public MyJTree(DefaultTreeModel model) {
            super(model);
        }

        public String getToolTipText(MouseEvent event) {
            int x = event.getX();
            int y = event.getY();
            TreePath path = getPathForLocation(x, y);

            if ((path != null) && path.getLastPathComponent() instanceof AbstractNode) {
                return ((AbstractNode) path.getLastPathComponent()).getOrigine();
            }

            return null;
        }
    }
}
