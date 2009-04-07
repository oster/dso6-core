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

import fr.loria.ecoo.so6.xml.exception.InvalidNodePath;
import fr.loria.ecoo.so6.xml.exception.ParseException;
import fr.loria.ecoo.so6.xml.node.TreeNode;
import fr.loria.ecoo.so6.xml.util.XmlUtil;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.DBType;

import java.io.File;
import java.io.IOException;
import java.io.Writer;


/**
 * @author smack
 */
public class InsertNode extends UpdateXmlFile {
    private TreeNode nodeToInsert;

    public InsertNode(long ticket, String path, String wsName, long time, String nodePath, TreeNode nodeToInsert) {
        super(ticket, path, wsName, time, false, null);
        this.nodePath = nodePath;
        this.nodeToInsert = nodeToInsert;
    }

    public InsertNode(String path, WsConnection ws, String nodePath, TreeNode nodeToInsert) {
        super(path, ws);
        this.nodePath = nodePath;
        this.nodeToInsert = nodeToInsert;
    }

    public void execute(String dir, DBType dbt) throws IOException, InvalidNodePath, ParseException {
        File f = new File(dir + File.separator + path);

        if (!f.exists()) {
            throw new IOException("File does not exist :" + f);
        }

        doTheJobOnFile(f.getAbsolutePath());
    }

    public boolean equals(Object obj) {
        if (obj instanceof InsertNode) {
            return path.equals(((InsertNode) obj).path) && nodePath.equals(((InsertNode) obj).nodePath) &&
            ((InsertNode) obj).nodeToInsert.equalsContent(nodeToInsert);
        } else {
            return false;
        }
    }

    public void doTheJobOnFile(String xmlFilePath) throws IOException, InvalidNodePath, ParseException {
        XmlUtil.insertNode(xmlFilePath, nodePath, nodeToInsert);
    }

    public String toString() {
        return "InsertNode(" + nodePath + "," + nodeToInsert + ")";
    }

    public void toXML(Writer osw) throws IOException {
        super.toXML(osw);
        osw.write("<treeNode>");
        nodeToInsert.toBase64(osw);
        osw.write("</treeNode>");
    }
}
