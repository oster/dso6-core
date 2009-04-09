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
package org.libresource.so6.core.engine.util;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.command.xml.DeleteAttribute;
import org.libresource.so6.core.command.xml.DeleteNode;
import org.libresource.so6.core.command.xml.InsertAttribute;
import org.libresource.so6.core.command.xml.InsertNode;
import org.libresource.so6.core.command.xml.UpdateAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


/**
 * @author smack
 */
public class XmlUtil {
    private static final String VALID_CHAR = " ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789/\"\'*-+.,;:!#{}[]()|`\\ _=?";

    public static String replaceInvalideXmlChar(String src) {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < src.length(); i++) {
            if (VALID_CHAR.indexOf(src.charAt(i)) != -1) {
                result.append(src.charAt(i));
            } else {
                result.append("?");
            }
        }

        return result.toString();
    }

    public static Collection convertToSo6Commands(WsConnection wsc, String path, Collection xyDiffCmds) {
        // TEMPO
        // Replace the move command by delete/insert

        /*
         * Vector tempo = new Vector(); for (Iterator i = xyDiffCmds.iterator();
         * i.hasNext();) { XMLCommand cmd = (XMLCommand) i.next(); if
         * (cmd.getType() == XMLCommand.MOVE_NODE) {
         * fr.loria.ecoo.so6.xml.xydiff.MoveNode m =
         * (fr.loria.ecoo.so6.xml.xydiff.MoveNode) cmd; tempo.addElement(new
         * fr.loria.ecoo.so6.xml.xydiff.DeleteNode(m.getSourcePath(),
         * m.getMovedNode())); tempo.addElement(new
         * fr.loria.ecoo.so6.xml.xydiff.InsertNode(m.getDestPath(),
         * m.getMovedNode())); } else { tempo.addElement(cmd); } }
         */

        // Sort the commands
        //Object[] cmds = xyDiffCmds.toArray();
        //Object[] cmds = tempo.toArray();
        //Arrays.sort(cmds);
        //
        ArrayList result = new ArrayList();

        for (Iterator i = xyDiffCmds.iterator(); i.hasNext();) {
            //for (int i = 0; i < cmds.length; i++) {
            Object xyCmd = i.next();

            //Object xyCmd = cmds[i];
            if (xyCmd instanceof fr.loria.ecoo.so6.xml.xydiff.InsertNode) {
                fr.loria.ecoo.so6.xml.xydiff.InsertNode in = (fr.loria.ecoo.so6.xml.xydiff.InsertNode) xyCmd;
                result.add(new InsertNode(path, wsc, in.getNodePath(), in.getNode()));
            } else if (xyCmd instanceof fr.loria.ecoo.so6.xml.xydiff.DeleteNode) {
                fr.loria.ecoo.so6.xml.xydiff.DeleteNode dn = (fr.loria.ecoo.so6.xml.xydiff.DeleteNode) xyCmd;
                result.add(new DeleteNode(path, wsc, dn.getNodePath(), dn.getNode()));
            } else if (xyCmd instanceof fr.loria.ecoo.so6.xml.xydiff.InsertAttribute) {
                fr.loria.ecoo.so6.xml.xydiff.InsertAttribute ia = (fr.loria.ecoo.so6.xml.xydiff.InsertAttribute) xyCmd;
                result.add(new InsertAttribute(path, wsc, ia.getNodePath(), ia.getName(), ia.getValue()));
            } else if (xyCmd instanceof fr.loria.ecoo.so6.xml.xydiff.DeleteAttribute) {
                fr.loria.ecoo.so6.xml.xydiff.DeleteAttribute da = (fr.loria.ecoo.so6.xml.xydiff.DeleteAttribute) xyCmd;
                result.add(new DeleteAttribute(path, wsc, da.getNodePath(), da.getName()));
            } else if (xyCmd instanceof fr.loria.ecoo.so6.xml.xydiff.UpdateAttribute) {
                fr.loria.ecoo.so6.xml.xydiff.UpdateAttribute ua = (fr.loria.ecoo.so6.xml.xydiff.UpdateAttribute) xyCmd;
                result.add(new UpdateAttribute(path, wsc, ua.getNodePath(), ua.getAttributeName(), ua.getOldValue(), ua.getNewValue()));
            }
        }

        return result;
    }
}
