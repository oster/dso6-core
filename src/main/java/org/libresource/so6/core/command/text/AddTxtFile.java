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
package org.libresource.so6.core.command.text;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.command.fs.AddFile;
import org.libresource.so6.core.engine.DBType;
import org.libresource.so6.core.engine.util.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;


public class AddTxtFile extends AddFile {
    private static final long serialVersionUID = 3;
    private int nbLines;

    public AddTxtFile(long ticket, String path, String wsName, long time, boolean conflict, String attachement) {
        super(ticket, path, wsName, time, conflict, attachement);

        try {
            nbLines = countNbLine(attachement);
        } catch (Exception e) {
            nbLines = 0;
        }
    }

    public AddTxtFile(String path, WsConnection ws) {
        super(path, ws);

        String tmppath;

        try {
            tmppath = File.createTempFile("txtFile", null).getPath();

            File f = new File(tmppath);
            f.deleteOnExit();
            FileUtils.copy(ws.getPath() + File.separator + path, tmppath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.setAttachement(tmppath);

        try {
            nbLines = countNbLine(attachement);
        } catch (Exception e) {
            nbLines = 0;
        }
    }

    public AddTxtFile(String path, WsConnection ws, String attachement) {
        super(path, ws);
        this.setAttachement(attachement);

        try {
            nbLines = countNbLine(attachement);
        } catch (Exception e) {
            nbLines = 0;
        }
    }

    public AddTxtFile(String path, WsConnection ws, File tmpDestDir) {
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

        try {
            nbLines = countNbLine(attachement);
        } catch (Exception e) {
            nbLines = 0;
        }
    }

    public String toString() {
        return "AddTxtFile(" + path + ")";
    }

    public boolean equals(Object o) {
        if (o instanceof AddTxtFile) {
            return path.equals(((AddTxtFile) o).path);
        } else {
            return false;
        }
    }

    public int getSize() {
        return nbLines;
    }

    public void execute(String dir, DBType dbt) throws Exception {
        FileUtils.copy(this.getAttachement(), dir + File.separator + path);

        /*
         * File f = new File(dir + File.separator + path); if (f.exists()) throw
         * new Exception("File already exist :" + f); if (!f.createNewFile()) {
         * throw new Exception("Cannot create :" + f); }
         */
        dbt.add(path, DBType.TYPE_FILE_TXT);
    }

    public static int countNbLine(String filePath) throws Exception {
        FileReader fr = new FileReader(filePath);
        LineNumberReader lnr = new LineNumberReader(fr);

        while (lnr.readLine() != null)
            ;

        fr.close();

        return lnr.getLineNumber();
    }

    public void toXML(OutputStreamWriter osw) throws IOException {
        super.toXML(osw);
        osw.write("<contrib>");
        osw.write(Long.toString(getSize()));
        osw.write("</contrib>");
    }
}
