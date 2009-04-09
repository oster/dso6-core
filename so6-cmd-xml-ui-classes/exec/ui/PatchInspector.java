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

import org.libresource.so6.core.command.Command;
import org.libresource.so6.core.command.text.AddBlock;
import org.libresource.so6.core.command.text.AddTxtFile;
import org.libresource.so6.core.command.text.DelBlock;
import org.libresource.so6.core.command.xml.AddXmlFile;
import org.libresource.so6.core.engine.OpVectorFsImpl;
import org.libresource.so6.core.engine.PatchFile;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.engine.util.InfoPatchHandler;
import org.libresource.so6.core.ui.util.StyledUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.io.FileReader;

import java.net.URL;

import java.util.Iterator;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * @author smack
 */
public class PatchInspector extends JPanel implements ListSelectionListener, StyledUI {
    private File workingDir;
    private File cmds;
    private File attach;
    private OpVectorFsImpl opVector;

    // UI
    private CmdAdapter adapter;
    private JList cmdsList;
    private JTextArea content;
    private JSplitPane split;
    private JLabel infos;

    public PatchInspector() throws Exception {
        // init
        workingDir = FileUtils.createTmpDir();
        cmds = new File(workingDir, "cmds");
        cmds.mkdirs();
        attach = new File(workingDir, "attach");
        attach.mkdirs();
        opVector = new OpVectorFsImpl(cmds.getPath());

        // ui
        infos = new JLabel();
        adapter = new CmdAdapter();
        cmdsList = new JList(adapter);
        cmdsList.addListSelectionListener(this);
        cmdsList.setCellRenderer(new Renderer());
        content = new JTextArea();
        content.setEditable(false);
        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(cmdsList), new JScrollPane(content));
        setLayout(new BorderLayout());
        add(infos, BorderLayout.NORTH);
        add(split, BorderLayout.CENTER);
    }

    public void setSplitLocation(int location) {
        split.setDividerLocation(location);
    }

    public void setSplitLocation(double pourcent) {
        split.setDividerLocation(pourcent);
    }

    public void load(URL url) throws Exception {
        PatchFile pf = new PatchFile(url.getFile());
        pf.buildOpVector(url.openStream(), opVector, attach.getPath(), null);
        adapter.update();
        retreivePatchInfo(url.toString());
    }

    public void load(String fileName) throws Exception {
        PatchFile pf = new PatchFile(fileName);
        pf.buildOpVector(opVector, attach.getPath(), null);
        adapter.update();
        retreivePatchInfo(fileName);
    }

    private void retreivePatchInfo(String uri) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        InfoPatchHandler infoPatch = new InfoPatchHandler();
        saxParser.parse(new File(uri), infoPatch);

        StringBuffer output = new StringBuffer();

        //"" + infoPatch.getComment() + "/" + infoPatch.getFromTicket()+ "/" +
        // infoPatch.getToTicket()+ "/" + infoPatch.getWsName()
        output.append("<html><table>");
        output.append("<tr><td>From ticket</td><td><td>:</td>");
        output.append(Long.toString(infoPatch.getFromTicket()));
        output.append("</td></tr>");
        output.append("<tr><td>To ticket</td><td><td>:</td>");
        output.append(Long.toString(infoPatch.getToTicket()));
        output.append("</td></tr>");
        output.append("<tr><td>Origine</td><td><td>:</td>");
        output.append(infoPatch.getWsName());
        output.append("</td></tr>");
        output.append("<tr><td>Comment</td><td><td>:</td>");
        output.append(infoPatch.getComment());
        output.append("</td></tr>");
        output.append("</table></html>");
        infos.setText(output.toString());
    }

    public static void main(String[] args) throws Exception {
        File file = new File(args[0]);
        JFrame f = new JFrame("Patch inspector: " + args[0]);
        PatchInspector pi = new PatchInspector();

        if (file.exists()) {
            pi.load(file.getPath());
        } else {
            pi.load(new URL(args[0]));
        }

        f.getContentPane().setLayout(new BorderLayout());
        f.getContentPane().add(pi, BorderLayout.CENTER);
        f.setSize(400, 400);
        pi.setSplitLocation(200);
        f.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
        f.setVisible(true);
    }

    public void valueChanged(ListSelectionEvent e) {
        Command cmd = (Command) cmdsList.getSelectedValue();
        content.setText("");

        if (cmd instanceof AddTxtFile || cmd instanceof AddXmlFile) {
            try {
                FileReader fr = new FileReader(cmd.getAttachement());
                char[] buffer = new char[1024];
                int length = -1;

                while ((length = fr.read(buffer)) != -1) {
                    content.append(new String(buffer, 0, length));
                }
            } catch (Exception e1) {
                content.setText("Error while trying to load command " + cmd);
            }
        } else if (cmd instanceof AddBlock) {
            AddBlock ab = (AddBlock) cmd;

            for (Iterator i = ab.getContent().iterator(); i.hasNext();) {
                content.append((String) i.next());
            }
        } else if (cmd instanceof DelBlock) {
            DelBlock ab = (DelBlock) cmd;

            for (Iterator i = ab.getOldContent().iterator(); i.hasNext();) {
                content.append((String) i.next());
            }
        } else {
            content.setText("No preview available");
        }
    }

    public void setStyle(Color back, Color forground) {
    }

    public class CmdAdapter extends AbstractListModel {
        public void update() {
            fireContentsChanged(this, 0, getSize());
        }

        public int getSize() {
            try {
                return opVector.size();
            } catch (Exception e) {
                return 0;
            }
        }

        public Object getElementAt(int index) {
            return opVector.getCommand(index);
        }
    }

    private class Renderer extends JLabel implements ListCellRenderer {
        public Renderer() {
            setOpaque(true);
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Command cmd = (Command) value;
            setText(cmd.getTicket() + " : " + cmd.toString());
            setBackground(isSelected ? Color.decode("#aaaaee") : Color.white);

            return this;
        }
    }
}
