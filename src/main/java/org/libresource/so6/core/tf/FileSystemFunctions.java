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
package org.libresource.so6.core.tf;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.command.Command;
import org.libresource.so6.core.command.Id;
import org.libresource.so6.core.command.Macro;
import org.libresource.so6.core.command.NoOp;
import org.libresource.so6.core.command.UpdateFile;
import org.libresource.so6.core.command.fs.AddBinaryFile;
import org.libresource.so6.core.command.fs.AddDir;
import org.libresource.so6.core.command.fs.AddFile;
import org.libresource.so6.core.command.fs.FsCommand;
import org.libresource.so6.core.command.fs.Remove;
import org.libresource.so6.core.command.fs.Rename;
import org.libresource.so6.core.command.fs.UpdateBinaryFile;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.engine.util.ObjectCloner;

import java.io.File;

import java.lang.reflect.Method;


/**
 * @author Ecoo Team Loria
 */
public class FileSystemFunctions {
    private WsConnection ws;

    public FileSystemFunctions(WsConnection ws) {
        this.ws = ws;
    }

    public Command transp(Command c1, FsCommand c2) throws Exception {
        Command res = null;
        Method m = null;

        try {
            //System.out.println("FS: " + c1.getClass() + " - " +
            // c2.getClass());
            m = this.getClass().getMethod("tf", new Class[] { c1.getClass(), c2.getClass() });
        } catch (NoSuchMethodException e1) {
            if ((c2 instanceof Remove) || (c2 instanceof Rename)) {
                try {
                    m = this.getClass().getMethod("tf", new Class[] { c1.getClass().getSuperclass(), c2.getClass() });
                } catch (NoSuchMethodException e2) {
                    m = this.getClass().getMethod("tf", new Class[] { c1.getClass().getSuperclass().getSuperclass(), c2.getClass() });
                }
            } else {
                try {
                    m = this.getClass().getMethod("tf", new Class[] { c1.getClass().getSuperclass(), c2.getClass() });
                } catch (NoSuchMethodException e2) {
                    try {
                        m = this.getClass().getMethod("tf", new Class[] { c1.getClass(), c2.getClass().getSuperclass() });
                    } catch (NoSuchMethodException e3) {
                        m = this.getClass().getMethod("tf", new Class[] { c1.getClass().getSuperclass(), c2.getClass().getSuperclass() });
                    }
                }
            }
        }

        return (Command) m.invoke(this, new Object[] { c1, c2 });
    }

    /*
     * Take care of FileSystem commands - AddFile - AddDir - Remove - Rename -
     * UpdateBinaryFile
     */

    /**
     * AddFile
     */
    public Command tf(AddFile c1, AddFile c2) throws Exception {
        if (c1.getPath().equals(c2.getPath())) {
            if (c1.getTicket() == -1) {
                Command cr = (Command) ObjectCloner.deepCopy(c1);
                cr.setPath(uniquePath(c1, c2));
                cr.setConflict(true);

                return cr;
            } else {
                Macro m = new Macro(c1, ws);
                m.setCommand(new Rename(c1.getPath(), ws, uniquePath(c1, c2)), 1);
                m.setCommand(c1, 2);
                c1.setConflict(true);

                return m;
            }
        }

        return c1;
    }

    public Command tf(AddFile c1, AddDir c2) throws Exception {
        if (c1.getPath().equals(c2.getPath())) {
            if (c1.getTicket() == -1) {
                Command cr = (Command) ObjectCloner.deepCopy(c1);
                cr.setPath(uniquePath(c1, c2));
                cr.setConflict(true);

                return cr;
            } else {
                Macro m = new Macro(c1, ws);
                m.setCommand(new Rename(c1.getPath(), ws, uniquePath(c1, c2)), 1);
                m.setCommand(c1, 2);
                c1.setConflict(true);

                return m;
            }
        }

        return c1;
    }

    public Command tf(AddFile c1, Remove c2) throws Exception {
        if (childOf(c1, c2)) {
            return new NoOp(c1, ws);
        }

        return c1;
    }

    public Command tf(AddFile c1, Rename c2) throws Exception {
        if (childOf(c1, c2)) {
            Command cr = (Command) ObjectCloner.deepCopy(c1);
            cr.setPath(replacePath(c1.getPath(), c2.getNewPath()));

            return cr;
        }

        return c1;
    }

    public Command tf(AddFile c1, UpdateBinaryFile c2)
        throws Exception {
        return c1;
    }

    /**
     * AddDir
     */
    public Command tf(AddDir c1, AddFile c2) throws Exception {
        if (c1.getPath().equals(c2.getPath())) {
            if (c1.getTicket() == -1) {
                Command cr = (Command) ObjectCloner.deepCopy(c1);
                cr.setPath(uniquePath(c1, c2));
                cr.setConflict(true);

                return cr;
            } else {
                Macro m = new Macro(c1, ws);
                m.setCommand(new Rename(c1.getPath(), ws, uniquePath(c1, c2)), 1);
                m.setCommand(c1, 2);
                c1.setConflict(true);

                return m;
            }
        }

        return c1;
    }

    public Command tf(AddDir c1, AddDir c2) throws Exception {
        if (c1.getPath().equals(c2.getPath())) {
            if (c1.getTicket() == -1) {
                Command cr = (Command) ObjectCloner.deepCopy(c1);
                cr.setPath(uniquePath(c1, c2));
                cr.setConflict(true);

                return cr;
            } else {
                Macro m = new Macro(c1, ws);
                m.setCommand(new Rename(c1.getPath(), ws, uniquePath(c1, c2)), 1);
                m.setCommand(c1, 2);
                c1.setConflict(true);

                return m;
            }
        }

        return c1;
    }

    public Command tf(AddDir c1, Remove c2) throws Exception {
        if (childOf(c1, c2)) {
            return new NoOp(c1, ws);
        }

        return c1;
    }

    public Command tf(AddDir c1, Rename c2) throws Exception {
        if (childOf(c1, c2)) {
            Command cr = (Command) ObjectCloner.deepCopy(c1);
            cr.setPath(replacePath(c1.getPath(), c2.getNewPath()));

            return cr;
        }

        return c1;
    }

    public Command tf(AddDir c1, UpdateBinaryFile c2) throws Exception {
        return c1;
    }

    /**
     * Remove
     */
    public Command tf(Remove c1, AddFile c2) throws Exception {
        return c1;
    }

    public Command tf(Remove c1, AddDir c2) throws Exception {
        return c1;
    }

    public Command tf(Remove c1, Remove c2) throws Exception {
        if (c1.getPath().equals(c2.getPath())) {
            return new Id(c1, ws);
        } else if (childOf(c1, c2)) {
            return new NoOp(c1, ws);
        }

        return c1;
    }

    public Command tf(Remove c1, Rename c2) throws Exception {
        if (childOf(c1, c2)) {
            Command cr = (Command) ObjectCloner.deepCopy(c1);
            cr.setPath(replacePath(c1.getPath(), c2.getNewPath()));

            return cr;
        }

        return c1;
    }

    public Command tf(Remove c1, UpdateBinaryFile c2) throws Exception {
        return c1;
    }

    /**
     * Rename
     */
    public Command tf(Rename c1, AddFile c2) throws Exception {
        return c1;
    }

    public Command tf(Rename c1, AddDir c2) throws Exception {
        return c1;
    }

    public Command tf(Rename c1, Remove c2) throws Exception {
        if (childOf(c1, c2)) {
            return new NoOp(c1, ws);
        }

        return c1;
    }

    public Command tf(Rename c1, Rename c2) throws Exception {
        if (c1.getPath().equals(c2.getPath()) && c1.getNewPath().equals(c2.getNewPath())) {
            return new Id(c1, ws);
        }

        if (childOf(c1, c2)) {
            Rename cr = (Rename) ObjectCloner.deepCopy(c1);
            cr.setPath(replacePath(c1.getPath(), c2.getNewPath()));
            cr.setNewPath(replacePath(c1.getNewPath(), c2.getNewPath()));

            return cr;
        }

        return c1;
    }

    public Command tf(Rename c1, UpdateBinaryFile c2) throws Exception {
        return c1;
    }

    /**
     * UpdateBinaryFile
     */
    public Command tf(UpdateBinaryFile c1, AddFile c2)
        throws Exception {
        return c1;
    }

    public Command tf(UpdateBinaryFile c1, AddDir c2) throws Exception {
        return c1;
    }

    public Command tf(UpdateBinaryFile c1, Remove c2) throws Exception {
        if (childOf(c1, c2)) {
            return new NoOp(c1, ws);
        }

        return c1;
    }

    public Command tf(UpdateBinaryFile c1, Rename c2) throws Exception {
        if (childOf(c1, c2)) {
            Command cr = (Command) ObjectCloner.deepCopy(c1);
            cr.setPath(replacePath(c1.getPath(), c2.getNewPath()));

            return cr;
        }

        return c1;
    }

    public Command tf(UpdateBinaryFile c1, UpdateBinaryFile c2)
        throws Exception {
        if (c1.getPath().equals(c2.getPath())) {
            if (FileUtils.compareBinFile(new File(c1.getAttachement()), new File(c2.getAttachement()))) {
                return new Id(c2, ws);
            }

            // Conflict between the 2 ops
            if (c1.getTicket() == -1) {
                // c1 is the local op
                Command cmd = new AddBinaryFile(uniquePath(c1, c2), ws, c1.getAttachement());

                return cmd;
            } else {
                // c1 is the remote op
                Macro m = new Macro(c1, ws);
                Macro m1 = new Macro(m, ws);
                m.setCommand(new Rename(c1.getPath(), ws, uniquePath(c1, c2)), 1);
                m.setCommand(m1, 2);
                m1.setCommand(new AddBinaryFile(c1.getPath(), ws), 1);
                m1.setCommand(c1, 2);
                c1.setConflict(true);

                return m;
            }
        }

        return c1;
    }

    /**
     * Generic
     */
    public Command tf(UpdateFile c1, Remove c2) throws Exception {
        if (childOf(c1, c2)) {
            //System.out.println(" child");
            return new NoOp(c1, ws);
        }

        return c1;
    }

    public Command tf(UpdateFile c1, Rename c2) throws Exception {
        if (childOf(c1, c2)) {
            Command cr = (Command) ObjectCloner.deepCopy(c1);
            cr.setPath(replacePath(c1.getPath(), c2.getNewPath()));

            return cr;
        }

        return c1;
    }

    /**
     * File System Util
     */
    public boolean childOf(Command c1, Command c2) {
        return c1.getPath().startsWith(c2.getPath());
    }

    // getParentPath('/1/2/3/4') will return '/1/2/3'
    public static String getParentPath(String path) {
        //System.out.println(path);
        int lastIndex = path.lastIndexOf("/");

        if (lastIndex == -1) {
            return "";
        }

        return path.substring(0, lastIndex + 1);
    }

    // getFSName('/1/2/3/4') will return '4'
    public String getFSName(String path) {
        //return path.substring(path.lastIndexOf(File.pathSeparatorChar)+1);
        return path.substring(path.lastIndexOf("/") + 1);
    }

    // replacePath('/1/2/3/4','/1/5') will return '/1/5/3/4'
    // replacePath('/1/2/3/4','/1/2#')
    // commonpath: /1/
    //  2/3/4
    // FSRenamed: 2#
    // result /1/ 2# 2/3/4
    public String replacePath(String pathToRename, String newPathToApply) {
        String commonPath = getParentPath(newPathToApply);
        assert pathToRename.startsWith(commonPath) : "bad replacement of path '" + pathToRename + "' with '" + newPathToApply + "'";

        String FSRenamed = newPathToApply.substring(commonPath.length());
        String unchangedTail = pathToRename.substring(commonPath.length());
        int index = unchangedTail.indexOf("/");

        if (index == -1) {
            unchangedTail = "";
        } else {
            unchangedTail = unchangedTail.substring(unchangedTail.indexOf("/"));
        }

        return commonPath + FSRenamed + unchangedTail;
    }

    // generate a unique path which must be the same for uniquePath(c1,c2) and
    // uniquePath(c2,c1)
    public String uniquePath(Command c1, Command c2) {
        long maxTicket = java.lang.Math.max(c1.getTicket(), c2.getTicket());

        return new String(c1.getPath() + "#" + maxTicket);
    }
}
