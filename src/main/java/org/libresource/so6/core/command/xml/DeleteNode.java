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
public class DeleteNode extends UpdateXmlFile {
    private TreeNode nodeToDelete;

    public DeleteNode(long ticket, String path, String wsName, long time, String nodePath, TreeNode nodeToDelete) {
        super(ticket, path, wsName, time, false, null);
        this.nodePath = nodePath;
        this.nodeToDelete = nodeToDelete;
    }

    public DeleteNode(String path, WsConnection ws, String nodePath, TreeNode nodeToDelete) {
        super(path, ws);
        this.nodePath = nodePath;
        this.nodeToDelete = nodeToDelete;
    }

    public void execute(String dir, DBType dbt) throws IOException, InvalidNodePath, ParseException {
        File f = new File(dir + File.separator + path);

        if (!f.exists()) {
            throw new IOException("File does not exist :" + f);
        }

        doTheJobOnFile(f.getAbsolutePath());
    }

    public boolean equals(Object obj) {
        if (obj instanceof DeleteNode) {
            return path.equals(((DeleteNode) obj).path) && nodePath.equals(((DeleteNode) obj).nodePath) &&
            ((DeleteNode) obj).nodeToDelete.equalsContent(nodeToDelete);
        } else {
            return false;
        }
    }

    public void doTheJobOnFile(String xmlFilePath) throws IOException, InvalidNodePath, ParseException {
        XmlUtil.deleteNode(xmlFilePath, nodePath);
    }

    public String toString() {
        return "DeleteNode(" + nodePath + "," + nodeToDelete + ")";
    }

    public void toXML(Writer osw) throws IOException {
        super.toXML(osw);
        osw.write("<treeNode>");
        nodeToDelete.toBase64(osw);
        osw.write("</treeNode>");
    }
}
