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
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.text.JTextComponent;


/**
 * @author bort 7 mars 2003
 *
 */
public abstract class LsClassicPropertiesEditor extends JDialog implements ActionListener {
    private JPanel propertiesPanel;
    protected Hashtable propertiesEls = new Hashtable();
    protected Hashtable properties = new Hashtable();

    public LsClassicPropertiesEditor(String name) {
        setTitle(name);
        setModal(true);

        // properties
        propertiesPanel = new JPanel();
        propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.Y_AXIS));

        // Lay out the label and scroll pane from top to bottom.
        JPanel coolPane = new JPanel();
        coolPane.setLayout(new BoxLayout(coolPane, BoxLayout.Y_AXIS));
        coolPane.add(Box.createRigidArea(new Dimension(0, 2)));

        JPanel line = new JPanel();
        line.setBackground(new Color(47, 80, 119));
        line.setMaximumSize(new Dimension(1000, 1));
        line.setMinimumSize(new Dimension(1, 1));
        line.setPreferredSize(new Dimension(100, 1));
        coolPane.add(line);
        coolPane.add(Box.createRigidArea(new Dimension(0, 5)));
        coolPane.add(propertiesPanel);
        coolPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // buttons
        JButton cancelButton = new JButton("Cancel");
        JButton setButton = new JButton("Set");
        cancelButton.setForeground(Color.DARK_GRAY);
        cancelButton.setActionCommand("CANCEL");
        cancelButton.addActionListener(this);
        setButton.setActionCommand("SET");
        setButton.addActionListener(this);

        // Lay out the buttons from left to right.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 5));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(setButton);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(cancelButton);

        // Put everything together, using the content pane's BorderLayout.
        Container contentPane = getContentPane();
        contentPane.add(coolPane, BorderLayout.CENTER);
        contentPane.add(buttonPane, BorderLayout.SOUTH);

        setResizable(false);
    }

    public Hashtable editProperties() {
        properties = new Hashtable();

        propertiesPanel.add(Box.createVerticalGlue());
        pack();
        setLocation(((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - getWidth()) / 2,
            ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - getHeight()) / 2);
        setVisible(true);

        if (!properties.keys().hasMoreElements()) {
            return null;
        }

        return properties;
    }

    public void addTextPropertie(String propertyKey, String propertyName, String value, boolean focus) {
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(propertyName, JLabel.RIGHT);
        label.setMaximumSize(new Dimension(160, 20));
        label.setMinimumSize(new Dimension(160, 20));
        label.setPreferredSize(new Dimension(160, 20));

        JTextField field = new JTextField(value);
        field.setMaximumSize(new Dimension(800, 20));
        field.setMinimumSize(new Dimension(10, 20));
        field.setPreferredSize(new Dimension(250, 20));
        myPanel.add(label);
        myPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        myPanel.add(field);
        propertiesPanel.add(myPanel);
        propertiesPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        if (focus) {
            pack();
            field.requestFocus();
        }

        //
        propertiesEls.put(propertyKey, field);
    }

    public void addPasswordPropertie(String propertyKey, String propertyName, String value, boolean focus) {
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(propertyName, JLabel.RIGHT);
        label.setMaximumSize(new Dimension(160, 20));
        label.setMinimumSize(new Dimension(160, 20));
        label.setPreferredSize(new Dimension(160, 20));

        JPasswordField field = new JPasswordField(value);
        field.setMaximumSize(new Dimension(800, 20));
        field.setMinimumSize(new Dimension(10, 20));
        field.setPreferredSize(new Dimension(250, 20));
        myPanel.add(label);
        myPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        myPanel.add(field);
        propertiesPanel.add(myPanel);
        propertiesPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        if (focus) {
            pack();
            field.requestFocus();
        }

        //
        propertiesEls.put(propertyKey, field);
    }

    public void addPathPropertie(String propertyKey, String propertyName, String value, boolean focus) {
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(propertyName, JLabel.RIGHT);
        label.setMaximumSize(new Dimension(160, 20));
        label.setMinimumSize(new Dimension(160, 20));
        label.setPreferredSize(new Dimension(160, 20));

        PathChooser pc = new PathChooser(value);
        myPanel.add(label);
        myPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        myPanel.add(pc);
        propertiesPanel.add(myPanel);
        propertiesPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        if (focus) {
            pack();
            pc.requestFocus();
        }

        //
        propertiesEls.put(propertyKey, pc);
    }

    public void addDisabledPropertie(String propertyName, String value) {
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(propertyName, JLabel.RIGHT);
        label.setMaximumSize(new Dimension(80, 20));
        label.setMinimumSize(new Dimension(80, 20));
        label.setPreferredSize(new Dimension(80, 20));

        JTextField field = new JTextField(value);
        field.setEnabled(false);
        field.setMaximumSize(new Dimension(800, 20));
        field.setMinimumSize(new Dimension(10, 20));
        field.setPreferredSize(new Dimension(250, 20));
        myPanel.add(label);
        myPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        myPanel.add(field);
        propertiesPanel.add(myPanel);
        propertiesPanel.add(Box.createRigidArea(new Dimension(0, 3)));
    }

    public void addTextAreaPropertie(String propertyKey, String propertyName, String value, boolean focus) {
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(propertyName, JLabel.RIGHT);
        label.setVerticalAlignment(JLabel.TOP);
        label.setMaximumSize(new Dimension(80, 60));
        label.setMinimumSize(new Dimension(80, 60));
        label.setPreferredSize(new Dimension(80, 60));

        JTextArea field = new JTextArea(value);
        field.setLineWrap(true);

        JScrollPane scroll = new JScrollPane(field);
        scroll.setMaximumSize(new Dimension(800, 60));
        scroll.setMinimumSize(new Dimension(10, 60));
        scroll.setPreferredSize(new Dimension(250, 60));
        myPanel.add(label);
        myPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        myPanel.add(scroll);
        propertiesPanel.add(myPanel);
        propertiesPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        if (focus) {
            pack();
            field.requestFocus();
        }

        //
        propertiesEls.put(propertyKey, field);
    }

    public void addListPropertie(String propertyKey, String propertyName, Map choices) {
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(propertyName, JLabel.RIGHT);
        label.setVerticalAlignment(JLabel.TOP);
        label.setMaximumSize(new Dimension(80, 60));
        label.setMinimumSize(new Dimension(80, 60));
        label.setPreferredSize(new Dimension(80, 60));

        List list = new List(choices);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(list);
        scroll.setMaximumSize(new Dimension(800, 60));
        scroll.setMinimumSize(new Dimension(10, 60));
        scroll.setPreferredSize(new Dimension(250, 60));
        myPanel.add(label);
        myPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        myPanel.add(scroll);
        propertiesPanel.add(myPanel);
        propertiesPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        //
        propertiesEls.put(propertyKey, list);
    }

    public void addListPropertie(String propertyKey, String propertyName, Vector values, Vector keys) {
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.X_AXIS));

        JLabel label = new JLabel(propertyName, JLabel.RIGHT);
        label.setVerticalAlignment(JLabel.TOP);
        label.setMaximumSize(new Dimension(80, 60));
        label.setMinimumSize(new Dimension(80, 60));
        label.setPreferredSize(new Dimension(80, 60));

        List list = new List(values, keys);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(list);
        scroll.setMaximumSize(new Dimension(800, 60));
        scroll.setMinimumSize(new Dimension(10, 60));
        scroll.setPreferredSize(new Dimension(250, 60));
        myPanel.add(label);
        myPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        myPanel.add(scroll);
        propertiesPanel.add(myPanel);
        propertiesPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        //
        propertiesEls.put(propertyKey, list);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("CANCEL")) {
            this.dispose();
        }

        if (e.getActionCommand().equals("SET")) {
            set();
        }
    }

    private void set() {
        for (Enumeration e = propertiesEls.keys(); e.hasMoreElements();) {
            String key = e.nextElement() + "";
            Object c = propertiesEls.get(key);

            if (c instanceof JTextComponent) {
                properties.put(key, ((JTextComponent) c).getText());
            }

            if (c instanceof PathChooser) {
                String path = c + "";

                if (!new File(path).exists() || !new File(path).isDirectory()) {
                    properties = new Hashtable();
                    JOptionPane.showMessageDialog(this, "Path " + c + " does not exist or is not a directory", "Invalid path", JOptionPane.WARNING_MESSAGE);

                    return;
                }

                properties.put(key, path);
            }

            if (c instanceof JList) {
                String value = c.toString();

                if (value == null) {
                    properties = new Hashtable();
                    JOptionPane.showMessageDialog(this, "You should select a value in the list", "Warning", JOptionPane.WARNING_MESSAGE);

                    return;
                }

                properties.put(key, value);
            }
        }

        dispose();
    }

    class PathChooser extends JPanel implements ActionListener {
        private JButton button;
        private JTextField field;

        public PathChooser(String value) {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            field = new JTextField(value);
            field.setMaximumSize(new Dimension(800, 20));
            field.setMinimumSize(new Dimension(10, 20));
            field.setPreferredSize(new Dimension(200, 20));
            button = new JButton("Browse");
            button.setPreferredSize(new Dimension(80, 20));
            add(field);
            add(Box.createRigidArea(new Dimension(2, 0)));
            add(button);
            button.addActionListener(this);
        }

        public String getValue() {
            return field.getText();
        }

        /**
         * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser(new File(getValue()).getParent());
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnVal = chooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                field.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        }

        public String toString() {
            return getValue();
        }
    }

    class List extends JList {
        public List(Map choices) {
            super();

            // elements
            Set keys = choices.keySet();
            Vector items = new Vector();

            for (Iterator i = keys.iterator(); i.hasNext();) {
                String key = i.next() + "";
                ListItem item = new ListItem(choices.get(key) + "", key);
                items.add(item);
            }

            setListData(items);
        }

        public List(Vector values, Vector keys) {
            super();

            // elements
            Vector items = new Vector();

            for (int i = 0; i < values.size(); i++) {
                ListItem item = new ListItem(values.get(i) + "", keys.get(i) + "");
                items.add(item);
            }

            setListData(items);
        }

        /**
         * @see javax.swing.JList#getSelectedValue()
         */
        public Object getSelectedValue() {
            ListItem item = (ListItem) super.getSelectedValue();

            if (item == null) {
                return null;
            }

            return item.getValue();
        }

        public String toString() {
            String sv = (String) getSelectedValue();

            return sv;
        }

        class ListItem {
            private String label;
            private String value;

            public ListItem(String label, String value) {
                setLabel(label);
                setValue(value);
            }

            /**
             * Returns the label.
             *
             * @return String
             */
            public String getLabel() {
                return label;
            }

            /**
             * Returns the value.
             *
             * @return String
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the label.
             *
             * @param label
             *            The label to set
             */
            public void setLabel(String label) {
                this.label = label;
            }

            /**
             * Sets the value.
             *
             * @param value
             *            The value to set
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * @see java.lang.Object#toString()
             */
            public String toString() {
                return getLabel();
            }
        }
    }
}
