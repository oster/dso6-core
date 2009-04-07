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
package org.libresource.so6.core.engine;

import org.libresource.so6.core.command.Command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.ListIterator;


/**
 * @author molli
 */
public class OpVectorFsImpl implements OpVector, Serializable {
    private String root; // where to put commands...
    private ArrayList paths = new ArrayList();
    private long fromTicket;
    private long toTicket;

    public OpVectorFsImpl(String root) {
        this.root = root;
        this.fromTicket = -1;
        this.toTicket = -1;
    }

    public void clear() throws Exception {
        paths.clear();
        this.fromTicket = -1;
        this.toTicket = -1;
    }

    public void load() {
        int pos = 0;
        Command cmd = getCommand(0);

        if (cmd == null) {
            return;
        }

        long ticket = cmd.getTicket();
        fromTicket = ticket;

        while ((cmd = getCommand(pos++)) != null) {
            if (cmd.getTicket() == ticket++) {
                paths.add(cmd.getPath());
                toTicket = ticket - 1;
            } else {
                return;
            }
        }
    }

    public void add(Command cmd) throws Exception {
        try {
            FileOutputStream fo = new FileOutputStream(root + File.separator + paths.size());
            ObjectOutputStream so = new ObjectOutputStream(fo);
            so.writeObject(cmd);
            so.flush();
            so.close();
            paths.add(cmd.getPath());

            if (fromTicket == -1) {
                fromTicket = cmd.getTicket();
            }

            toTicket = cmd.getTicket();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(Command cmd, int pos) {
        try {
            FileOutputStream fo = new FileOutputStream(root + File.separator + pos);
            ObjectOutputStream so = new ObjectOutputStream(fo);
            so.writeObject(cmd);
            so.flush();
            so.close();

            //oids.add(pos, cmd.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int size() throws Exception {
        return paths.size();
    }

    public Command getCommand(int pos) {
        Command cmd = null;
        File f = new File(root + File.separator + pos);

        if (!(f.exists())) {
            return null;
        }

        try {
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream si = new ObjectInputStream(fi);
            cmd = (Command) si.readObject();
            si.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //		if (cmd.getAttachement() != null) {
        //			String tmppath = File.createTempFile("grouss", "grouss").getPath();
        //			FileUtil.copy(cmd.getAttachement(), tmppath);
        //			cmd.setAttachement(tmppath);
        //		}
        return cmd;
    }

    public void close() throws Exception {
    }

    public ListIterator getCommands() throws Exception {
        return new CmdIterator();
    }

    public long getFromTicket() {
        return fromTicket;
    }

    public long getToTicket() {
        return toTicket;
    }

    public ArrayList getPathList() throws Exception {
        return paths;
    }

    class CmdIterator implements ListIterator {
        private int i = 0;

        CmdIterator() {
        }

        public void close() {
        }

        public Object next() {
            Command cmd = null;

            if (i < paths.size()) {
                cmd = getCommand(i);
                i++;

                return cmd;
            }

            return null;
        }

        public void set(Object c) {
            OpVectorFsImpl.this.update((Command) c, i - 1);
        }

        public void remove() {
            throw new RuntimeException("methode remove not implemented");
        }

        public boolean hasNext() {
            return (getCommand(i) != null);
        }

        public int nextIndex() {
            return i;
        }

        public int previousIndex() {
            return i - 1;
        }

        public boolean hasPrevious() {
            return i > 0;
        }

        public Object previous() {
            return getCommand(previousIndex());
        }

        public void add(Object o) {
            throw new RuntimeException("methode remove not implemented");
        }
    }
}
