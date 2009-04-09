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
/*
 * Created on 13 f�vr. 2004
 */
package org.libresource.so6.core.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.libresource.so6.core.WsConnection;


/**
 * @author molli
 */
public class RefCopy {
    private WsConnection ws;
    private DBType dbt;

    public RefCopy(WsConnection ws) {
        this.ws = ws;
        dbt = new DBType(ws.getDBTypePath() + File.separator + "refcopy.dbtype", ws.getLastBinExt());
    }

    public void patch(String patchfile) throws Exception {
        PatchFile pf = new PatchFile(patchfile);
        pf.patch(ws.getRefCopyPath(), dbt);
    }

    public DBType getDBType() {
        return dbt;
    }

    private static void walk(String dir, String root, List<String> result) {
        File f = new File(dir);

        if (f.isFile()) {
            result.add(f.getPath().substring(root.length()).replaceAll("\\\\", "/"));
        }

        if (f.isDirectory()) {
            if (!(dir.equals(root))) {
                result.add(f.getPath().substring(root.length()).replaceAll("\\\\", "/"));
            }

            String[] entries = f.list();

            for (int i = 0; i < entries.length; i++) {
                walk(dir + File.separator + entries[i], root, result);
            }
        }
    }

    public Iterator<String> getElements() {
        List<String> v = new ArrayList<String>();
        walk(ws.getRefCopyPath() + "/", ws.getRefCopyPath() + "/", v);

        return v.iterator();
    }

    public boolean exists(String path) {
        File f = new File(ws.getRefCopyPath() + File.separator + path);

        return f.exists();
    }

    public int getType(String path) {
        return dbt.getType(path);
    }

    public String getPath(String path) {
        return ws.getRefCopyPath() + File.separator + path;
    }

    public void build() {
    }
}
