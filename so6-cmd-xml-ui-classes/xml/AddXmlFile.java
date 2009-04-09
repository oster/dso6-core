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
package org.libresource.so6.core.command.xml;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.command.fs.AddFile;
import org.libresource.so6.core.engine.DBType;
import org.libresource.so6.core.engine.util.FileUtils;

import java.io.File;


/**
 * @author smack
 */
public class AddXmlFile extends AddFile {
    private static final long serialVersionUID = 3;

    public AddXmlFile(long ticket, String path, String wsName, long time, String attachement) {
        super(ticket, path, wsName, time, false, null);
        this.attachement = attachement;
    }

    public AddXmlFile(String path, WsConnection ws) {
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

    public AddXmlFile(String path, WsConnection ws, String attachement) {
        super(path, ws);
        this.setAttachement(attachement);
    }

    public AddXmlFile(String path, WsConnection ws, File tmpDestDir) {
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

    //
    public String toString() {
        return "AddXmlFile(" + path + ")";
    }

    //
    public void execute(String dir, DBType dbt) throws Exception {
        File f = new File(dir + File.separator + path);

        if (f.exists()) {
            throw new Exception("File already exist :" + f);
        }

        doTheJobOnFile(f.getAbsolutePath());
        dbt.add(path, DBType.TYPE_FILE_XML);
    }

    //
    public boolean equals(Object obj) {
        if (obj instanceof AddXmlFile) {
            return path.equals(((AddXmlFile) obj).path);
        } else {
            return false;
        }
    }

    public void doTheJobOnFile(String xmlFilePath) throws Exception {
        FileUtils.copy(this.getAttachement(), xmlFilePath);
    }
}
