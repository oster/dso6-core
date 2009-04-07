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
package org.libresource.so6.core.command.fs;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.DBType;
import org.libresource.so6.core.engine.util.Base64;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class Rename extends FsCommand {
    private static final long serialVersionUID = 3;
    protected String newpath;

    public Rename(long ticket, String path, String wsName, long time, boolean conflict, String attachement, String newPath) {
        super(ticket, path, wsName, time, conflict, attachement);
        this.newpath = newPath;
    }

    public Rename(String path, WsConnection ws, String newpath) {
        super(path, ws);
        this.newpath = newpath;
    }

    public String toString() {
        return "Rename(" + path + "," + newpath + ")";
    }

    public boolean equals(Object o) {
        if (o instanceof Rename) {
            Rename r = (Rename) o;

            return path.equals(r.path) && newpath.equals(r.newpath);
        } else {
            return false;
        }
    }

    public String getNewPath() {
        return newpath;
    }

    public void setNewPath(String path) {
        this.newpath = path;
    }

    public void execute(String dir, DBType dbt) throws Exception {
        File f = new File(dir + File.separator + path);
        File f2 = new File(dir + File.separator + newpath);

        if (!f.renameTo(f2)) {
            throw new Exception("Cannot rename " + f + " to " + f2);
        }

        int currenttype = dbt.getType(path);
        dbt.remove(path);
        dbt.add(newpath, currenttype);
    }

    public void toXML(OutputStreamWriter osw) throws IOException {
        super.toXML(osw);
        osw.write("<newpath>" + Base64.encodeBytes(newpath.getBytes("UTF-8")) + "</newpath>");
    }
}
