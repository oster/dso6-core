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

import fr.loria.ecoo.so6.xml.exception.AttributeAlreadyExist;
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
public class InsertAttribute extends UpdateXmlFile {
    private String attrName;
    private String attrValue;

    public InsertAttribute(long ticket, String path, String wsName, long time, String nodePath, String attrName, String attrValue) {
        super(ticket, path, wsName, time, false, null);
        this.nodePath = nodePath;
        this.attrName = attrName;
        this.attrValue = attrValue;
    }

    public InsertAttribute(String path, WsConnection ws, String nodePath, String attrName, String attrValue) {
        super(path, ws);
        this.nodePath = nodePath;
        this.attrName = attrName;
        this.attrValue = attrValue;
    }

    public void execute(String dir, DBType dbt) throws IOException, InvalidNodePath, AttributeAlreadyExist, ParseException, AttributeNotAllowed {
        File f = new File(dir + File.separator + path);

        if (!f.exists()) {
            throw new IOException("File does not exist :" + f);
        }

        doTheJobOnFile(f.getAbsolutePath());
    }

    public boolean equals(Object obj) {
        if (obj instanceof InsertAttribute) {
            return path.equals(((InsertAttribute) obj).path) && nodePath.equals(((InsertAttribute) obj).nodePath) &&
            ((InsertAttribute) obj).attrName.equals(attrName) && ((InsertAttribute) obj).attrValue.equals(attrValue);
        } else {
            return false;
        }
    }

    public void doTheJobOnFile(String xmlFilePath) throws IOException, InvalidNodePath, AttributeAlreadyExist, ParseException, AttributeNotAllowed {
        XmlUtil.setAttribute(xmlFilePath, nodePath, attrName, attrValue, true);
    }

    public String toString() {
        return "InsertAttribute(" + nodePath + "," + attrName + "," + attrValue + ")";
    }

    public void toXML(Writer osw) throws IOException {
        super.toXML(osw);
        osw.write("<xmlAttributeName>" + Base64.encodeBytes(attrName.getBytes("UTF-8")) + "</xmlAttributeName>");
        osw.write("<xmlAttributeValue>" + Base64.encodeBytes(attrValue.getBytes("UTF-8")) + "</xmlAttributeValue>");
        osw.flush();
    }

    public String getAttributeValue() {
        return attrValue;
    }

    public String getAttributeName() {
        return attrName;
    }

    public void setAttributeName(String s) {
        this.attrName = s;
    }

    public void setAttributeValue(String s) {
        this.attrValue = s;
    }
}
