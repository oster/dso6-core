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
package org.libresource.so6.core.report;

import org.libresource.so6.core.command.Command;
import org.libresource.so6.core.command.Macro;
import org.libresource.so6.core.command.UpdateFile;
import org.libresource.so6.core.command.fs.AddDir;
import org.libresource.so6.core.command.fs.AddFile;
import org.libresource.so6.core.command.fs.Remove;
import org.libresource.so6.core.command.fs.Rename;
import org.libresource.so6.core.command.fs.UpdateBinaryFile;
import org.libresource.so6.core.engine.OpVectorFsImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ListIterator;


/**
 * @author smack
 */
public class CVSReportMaker {
    //
    private OpVectorFsImpl opVector;
    private Hashtable indexTable;
    private long from;
    private long to;

    public CVSReportMaker(String opVerctorFsImplBasePath) {
        opVector = new OpVectorFsImpl(opVerctorFsImplBasePath);
        opVector.load();
        from = opVector.getFromTicket();
        to = opVector.getToTicket();
        indexTable = new Hashtable();
    }

    public void buildIndexTable() throws Exception {
        Command cmd = null;
        ListIterator iterator = opVector.getCommands();

        while ((cmd = (Command) iterator.next()) != null) {
            String path = cmd.getPath();
            FileMetaData metaData = (FileMetaData) indexTable.get(path);

            if (metaData == null) {
                metaData = new FileMetaData(path);
            }

            metaData.applyCmd(cmd);
            indexTable.put(path, metaData);
        }
    }

    public void writeReport(OutputStream out) throws IOException {
        if (from == 0) {
            return;
        }

        OutputStreamWriter ostw = new OutputStreamWriter(out);
        ostw.write("---\n");

        for (Enumeration e = indexTable.keys(); e.hasMoreElements();) {
            String path = (String) e.nextElement();
            FileMetaData data = (FileMetaData) indexTable.get(path);

            // print
            ostw.write(data.toString() + "\n");
        }

        ostw.write("---\n");
        ostw.flush();
    }

    public String getReport() throws IOException {
        if (from == 0) {
            return "";
        }

        StringBuffer buffer = new StringBuffer();
        buffer.append("---\n");

        for (Enumeration e = indexTable.keys(); e.hasMoreElements();) {
            String path = (String) e.nextElement();
            FileMetaData data = (FileMetaData) indexTable.get(path);

            // print
            buffer.append(data.toString() + "\n");
        }

        buffer.append("---\n");

        return buffer.toString();
    }

    public long getFromTicket() {
        return from;
    }

    public long getToTicket() {
        return to;
    }

    public class FileMetaData {
        private final static String CREATE = "A";
        private final static String MOVE = "Moved";
        private final static String DELETE = "R";
        private final static String UPDATE = "U";
        private final static String CONFLICT = "C";
        private final static String CHANGE_TYPE = "Type changed";

        //
        private boolean conflict = false;
        private String path;
        private ArrayList cmds;

        FileMetaData(String path) {
            this.path = path;
            cmds = new ArrayList();
        }

        public void applyCmd(Command cmd) {
            if (cmd instanceof Macro) {
                this.applyCmd(((Macro) cmd).getCommand(1));
                this.applyCmd(((Macro) cmd).getCommand(2));
            } else {
                if (cmd.isConflict()) {
                    conflict = true;
                }

                if (cmd instanceof UpdateFile || cmd instanceof UpdateBinaryFile) {
                    cmds.add(UPDATE);
                }

                if (cmd instanceof Remove) {
                    cmds.add(DELETE);
                }

                if (cmd instanceof AddFile) {
                    cmds.add(CREATE);
                }

                if (cmd instanceof AddDir) {
                    cmds.add(CREATE);
                }

                if (cmd instanceof Rename) {
                    cmds.add(MOVE);
                }
            }
        }

        public ArrayList getCmdsList() {
            return cmds;
        }

        public String getType() {
            if (cmds.contains(MOVE) && cmds.contains(CREATE)) {
                return CREATE;
            } else if (cmds.contains(DELETE) && cmds.contains(CREATE)) {
                return CHANGE_TYPE;
            } else if (cmds.contains(DELETE)) {
                return DELETE;
            } else if (cmds.contains(UPDATE)) {
                return UPDATE;
            } else if (cmds.contains(CREATE)) {
                return CREATE;
            } else if (cmds.contains(MOVE)) {
                return MOVE;
            }

            return "";
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();

            if (conflict) {
                buffer.append(CONFLICT);
            } else {
                buffer.append(getType());
            }

            buffer.append(" ");
            buffer.append(path);

            return buffer.toString();
        }
    }
}
