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
import org.libresource.so6.core.engine.util.FileUtils;

import java.io.File;
import java.io.Serializable;


public class UpdateBinaryFile extends FsCommand implements Serializable {
    private static final long serialVersionUID = 3;

    public UpdateBinaryFile(long ticket, String path, String wsName, long time, boolean conflict, String attachement) {
        super(ticket, path, wsName, time, conflict, attachement);
    }

    public UpdateBinaryFile(String path, WsConnection ws) {
        super(path, ws);

        String tmppath;

        try {
            tmppath = File.createTempFile("grouss", null).getPath();

            File f = new File(tmppath);
            f.deleteOnExit();
            FileUtils.copy(ws.getPath() + File.separator + path, tmppath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.setAttachement(tmppath);
    }

    public UpdateBinaryFile(String path, WsConnection ws, File tmpDestDir) {
        super(path, ws);

        String tmppath;

        try {
            tmppath = File.createTempFile("attach", null, tmpDestDir).getPath();

            File f = new File(tmppath);
            FileUtils.copy(ws.getPath() + File.separator + path, tmppath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.setAttachement(tmppath);
    }

    public boolean equals(Object o) {
        if (o instanceof UpdateBinaryFile) {
            UpdateBinaryFile ubf = (UpdateBinaryFile) o;

            if (path.equals(ubf.path)) {
                if (getTicket() == ubf.getTicket()) {
                    return true;
                }

                if ((getTicket() == -1) || (ubf.getTicket() == -1)) {
                    return FileUtils.compareBinFile(new File(this.getAttachement()), new File(ubf.getAttachement()));
                }

                return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public String toString() {
        return "UpdateBinaryFile(" + path + ")";
    }

    public void execute(String dir, DBType dbt) throws Exception {
        FileUtils.copy(this.getAttachement(), dir + File.separator + path);
    }
}
