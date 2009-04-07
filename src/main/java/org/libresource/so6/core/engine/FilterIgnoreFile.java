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
 * Created on 16 f�vr. 2004
 */
package org.libresource.so6.core.engine;

import org.libresource.so6.core.Workspace;
import org.libresource.so6.core.WsConnection;

import java.io.File;
import java.io.FilenameFilter;

import java.util.logging.Logger;


/**
 * @author molli
 */
public class FilterIgnoreFile implements FilenameFilter {
    private Ignore ignore;
    private Ignore ignorePerso;

    public FilterIgnoreFile(WsConnection ws) {
        ignore = new Ignore(ws.getPath(), ws.getPath() + File.separatorChar + Ignore.ignoreFile);
        ignorePerso = new Ignore(ws.getPath(), ws.getDataPath() + File.separatorChar + Ignore.ignoreFile);
    }

    public boolean accept(File dir, String name) {
        if (name.equals(Workspace.SO6PREFIX)) {
            return false;
        }

        if (name.length() > 255) {
            Logger.getLogger("ui.log").warning("refusing (>255):" + name);

            return false;
        }

        //File f=new File(dir+File.separator+name);
        //if (f.isFile() && (f.length()==0)) {
        //	Logger.getLogger("ui.log").warning("refusing (size=0):" +
        // dir.getPath() + ":" + name);
        //	return false;
        //}
        if (ignore.match(dir, name)) {
            Logger.getLogger("ui.log").warning("ignoring:" + dir.getPath() + ":" + name);

            return false;
        }

        if (ignorePerso.match(dir, name)) {
            Logger.getLogger("ui.log").warning("ignoring:" + dir.getPath() + ":" + name);

            return false;
        }

        return true;
    }
}
