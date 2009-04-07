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

import fr.loria.ecoo.so6.xml.exception.AttributeNotAllowed;
import fr.loria.ecoo.so6.xml.exception.InvalidNodePath;
import fr.loria.ecoo.so6.xml.exception.ParseException;
import fr.loria.ecoo.so6.xml.util.XmlUtil;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.DBType;
import org.libresource.so6.core.engine.util.Base64;

import java.io.File;
import java.io.IOException;
import java.io.Writer;


/**
 * @author smack
 */
public class UpdateAttribute extends UpdateXmlFile {
    private String attrName;
    private String oldAttrValue;
    private String newAttrValue;

    public UpdateAttribute(long ticket, String path, String wsName, long time, String nodePath, String attrName, String oldAttrValue, String newAttrValue) {
        super(ticket, path, wsName, time, false, null);
        this.nodePath = nodePath;
        this.attrName = attrName;
        this.oldAttrValue = oldAttrValue;
        this.newAttrValue = newAttrValue;
    }

    public UpdateAttribute(String path, WsConnection ws, String nodePath, String attrName, String oldAttrValue, String newAttrValue) {
        super(path, ws);
        this.nodePath = nodePath;
        this.attrName = attrName;
        this.oldAttrValue = oldAttrValue;
        this.newAttrValue = newAttrValue;
    }

    public void execute(String dir, DBType dbt) throws IOException, InvalidNodePath, ParseException, AttributeNotAllowed {
        File f = new File(dir + File.separator + path);

        if (!f.exists()) {
            throw new IOException("File does not exist :" + f);
        }

        doTheJobOnFile(f.getAbsolutePath());
    }

    public boolean equals(Object obj) {
        if (obj instanceof UpdateAttribute) {
            return path.equals(((UpdateAttribute) obj).path) && nodePath.equals(((UpdateAttribute) obj).nodePath) &&
            ((UpdateAttribute) obj).attrName.equals(attrName) && ((UpdateAttribute) obj).oldAttrValue.equals(oldAttrValue) &&
            ((UpdateAttribute) obj).newAttrValue.equals(newAttrValue);
        } else {
            return false;
        }
    }

    public void doTheJobOnFile(String xmlFilePath) throws IOException, InvalidNodePath, ParseException, AttributeNotAllowed {
        XmlUtil.setAttribute(xmlFilePath, nodePath, attrName, newAttrValue);
    }

    public String toString() {
        return "UpdateAttribute(" + nodePath + "," + attrName + "," + oldAttrValue + "," + newAttrValue + ")";
    }

    public void toXML(Writer osw) throws IOException {
        super.toXML(osw);
        osw.write("<xmlAttributeName>" + Base64.encodeBytes(attrName.getBytes("UTF-8")) + "</xmlAttributeName>");
        osw.write("<xmlOldAttributeValue>" + Base64.encodeBytes(oldAttrValue.getBytes("UTF-8")) + "</xmlOldAttributeValue>");
        osw.write("<xmlAttributeValue>" + Base64.encodeBytes(newAttrValue.getBytes("UTF-8")) + "</xmlAttributeValue>");
        osw.flush();
    }

    public String getAttributeName() {
        return attrName;
    }

    public void setAttributeName(String s) {
        this.attrName = s;
    }

    public String getNewValue() {
        return newAttrValue;
    }

    public void setNewValue(String s) {
        this.newAttrValue = s;
    }

    public String getOldValue() {
        return oldAttrValue;
    }

    public void setOldValue(String s) {
        this.oldAttrValue = s;
    }
}
