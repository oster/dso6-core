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
import org.libresource.so6.core.command.UpdateFile;

import java.io.IOException;
import java.io.Writer;


/**
 * @author smack
 */
public abstract class UpdateXmlFile extends UpdateFile {
    protected String nodePath = "-1";

    public UpdateXmlFile(String path, WsConnection ws) {
        super(path, ws);
    }

    public UpdateXmlFile(long ticket, String path, String wsName, long time, boolean conflict, String attachement) {
        super(ticket, path, wsName, time, conflict, attachement);
    }

    //
    public abstract void doTheJobOnFile(String xmlFilePath)
        throws Exception;

    public String getNodePath() {
        return this.nodePath;
    }

    public void setNodePath(String newNodePath) {
        this.nodePath = newNodePath;
    }

    public void toXML(Writer osw) throws IOException {
        super.toXML(osw);
        osw.write("<xmlNodePath>" + nodePath + "</xmlNodePath>");
    }
}
