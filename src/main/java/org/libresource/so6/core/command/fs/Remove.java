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
import org.libresource.so6.core.engine.util.RmDir;

import java.io.File;


public class Remove extends FsCommand {
    private static final long serialVersionUID = 3;

    public Remove(long ticket, String path, String wsName, long time, boolean conflict, String attachement) {
        super(ticket, path, wsName, time, conflict, attachement);
    }

    public Remove(String path, WsConnection ws) {
        super(path, ws);
    }

    public String toString() {
        return "Remove(" + path + ")";
    }

    public boolean equals(Object o) {
        if (o instanceof Remove) {
            return path.equals(((Remove) o).path);
        } else {
            return false;
        }
    }

    public void execute(String dir, DBType dbt) throws Exception {
        File f = new File(dir + File.separator + path);

        /*
         * if (!f.delete()) { throw new Exception("Unable to remove path " +
         * ws.getPath() + File.separator + path); }
         */
        RmDir rmd = new RmDir(dir + File.separator + path);
        rmd.execute();
        dbt.remove(path);
    }
}
