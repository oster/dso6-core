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
package org.libresource.so6.core.tf;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.command.Command;
import org.libresource.so6.core.command.Macro;
import org.libresource.so6.core.command.NeutralCommand;
import org.libresource.so6.core.command.fs.FsCommand;
import org.libresource.so6.core.command.fs.Remove;
import org.libresource.so6.core.command.fs.Rename;
import org.libresource.so6.core.command.text.UpdateTextFile;
import org.libresource.so6.core.command.xml.UpdateXmlFile;
import org.libresource.so6.core.engine.util.ObjectCloner;

import java.lang.reflect.Method;


/**
 * @author Ecoo Team Loria
 *
 * Describe class <code>TransformationFunctions</code> here. Responsability:
 * Implements transformation function Collaboration: Log (merge)
 * @author <a href="mailto:molli@loria.fr">Pascal Molli </a>, <a
 *         href="mailto:oster@loria.fr">Gerald Oster </a>, <a
 *         href="mailto:jourdain@loria.fr">S�bastien Jourdain </a>
 * @version 1.0
 */
public class TransformationFunctions {
    protected WsConnection ws;
    private FileSystemFunctions fsTF;
    private TextFileFunctions txtTF;
    private XmlFileFunctions xmlTF;

    public TransformationFunctions(WsConnection ws) {
        this.ws = ws;
        this.fsTF = new FileSystemFunctions(ws);
        this.txtTF = new TextFileFunctions(ws);
        this.xmlTF = new XmlFileFunctions(ws);
    }

    /**
     * c2 is the remote Command, c1 is the local command (already executed)
     */
    public Command transp(Command c1, Command c2) throws Exception {
        Command res = null;
        Method m = null;

        //System.out.println("tf( " + c1 + " , " + c2 + " )");
        try {
            if ((c1 instanceof NeutralCommand) || (c2 instanceof NeutralCommand)) {
                return c1;
            } else if ((c1 instanceof Macro) || (c2 instanceof Macro)) {
                if (c1 instanceof Macro) {
                    // tf(x.seq, op) = tf(x, op).tf(seq, tf(op, x))
                    Command cmd1 = transp(((Macro) c1).getCommand(1), c2);
                    Command cmd2 = transp(((Macro) c1).getCommand(2), transp(c2, ((Macro) c1).getCommand(1)));
                    Macro cr = (Macro) ObjectCloner.deepCopy(c1);
                    cr.setCommand(cmd1, 1);
                    cr.setCommand(cmd2, 2);

                    return cr;
                } else {
                    // tf(op, x.seq) = tf(tf(op, x), seq)
                    return transp(transp(c1, ((Macro) c2).getCommand(1)), ((Macro) c2).getCommand(2));
                }
            } else if ((c1 instanceof FsCommand) || (c2 instanceof Remove) || (c2 instanceof Rename)) {
                if (c2 instanceof FsCommand) {
                    return fsTF.transp(c1, (FsCommand) c2);
                } else {
                    return c1;
                }
            } else if (c1 instanceof UpdateTextFile) {
                if (c2 instanceof UpdateTextFile) {
                    return txtTF.transp((UpdateTextFile) c1, (UpdateTextFile) c2);
                } else {
                    return c1;
                }
            } else if (c1 instanceof UpdateXmlFile) {
                if (c2 instanceof UpdateXmlFile) {
                    return xmlTF.transp((UpdateXmlFile) c1, (UpdateXmlFile) c2);
                } else {
                    return c1;
                }
            } else {
                throw new Exception("Unable to find transformation function for:\n " + c1 + " - " + c2);
            }
        } catch (NoSuchMethodException e) {
            throw new Exception("Fail to find transformation for:\n " + c1 + " - " + c2);
        }
    }
}
