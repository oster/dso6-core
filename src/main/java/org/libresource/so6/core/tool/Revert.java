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
package org.libresource.so6.core.tool;

import org.libresource.so6.core.Workspace;
import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.FileParser;
import org.libresource.so6.core.engine.util.FileUtils;

import java.io.File;


/**
 * @author molli
 */
public class Revert {
    private WsConnection ws;
    private FileParser fp;

    public Revert(WsConnection ws) throws Exception {
        this.ws = ws;
        this.fp = new FileParser(ws);
    }

    // path is a relative path;
    public void revertFile(String path) throws Exception {
        String copyref = ws.getRefCopyPath() + File.separator + path;

        if (!new File(copyref).exists()) {
            new File(path).delete();

            return;
        }

        FileUtils.copy(copyref, ws.getPath() + File.separator + path);
    }

    public void revertDir(String path) throws Exception {
        File f = new File(ws.getPath() + File.separator + path);

        if (!(f.isDirectory())) {
            return;
        }

        // ignore dataFiles !!
        if (f.getName().equals(Workspace.SO6PREFIX)) {
            return;
        }

        String[] list = f.list();

        for (int i = 0; i < list.length; i++) {
            File fl = new File(path + File.separator + list[i]);

            if (fl.isFile()) {
                try {
                    revertFile(fl.getPath());
                } catch (Exception e) {
                }
            }

            if (fl.isDirectory()) {
                revertDir(fl.getPath());
            }
        }
    }

    public void revert(String path) throws Exception {
        File f = new File(ws.getPath(), path);
        File refFile = new File(ws.getRefCopyPath(), path);

        if (!refFile.exists()) {
            f.delete();

            return;
        } else {
            if (!f.exists()) {
                // Check type
                if (refFile.isDirectory()) {
                    f.mkdirs();
                }

                if (refFile.isFile()) {
                    if (!f.getParentFile().exists()) {
                        f.getParentFile().mkdirs();
                    }

                    f.createNewFile();
                }
            }

            if (f.isFile()) {
                revertFile(f.getPath());
            }

            if (f.isDirectory()) {
                revertDir(f.getPath());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            throw new Exception("Usage: <so6 property file> [path1] ... [pathn]");
        }

        WsConnection ws = new WsConnection(args[0]);
        Revert rv = new Revert(ws);

        if (args.length > 1) {
            for (int i = 1; i < args.length; i++) {
                File f = new File(args[i]);

                if (!(f.exists())) {
                    throw new Exception(args[i] + " not exists");
                }

                rv.revert(f.getAbsolutePath());
            }
        } else {
            rv.revert(ws.getPath());
        }
    }
}
