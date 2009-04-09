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
package org.libresource.so6.core.exec;

import org.libresource.so6.core.StateMonitoring;
import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.log.LogUtils;
import org.libresource.so6.core.engine.util.Base64;
import org.libresource.so6.core.engine.util.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.util.logging.Logger;


/**
 * The <code>Rename</code> class is used to rename a previously syncronized
 * file.
 * <p>
 * Exemples :<br>
 * java -cp so6.jar Rename .so6/1/so6.properties src/Workspace.java
 * src/org/libresource/so6/Workspace.java
 *
 * @author Smack
 * @version 1.0, 26/05/04
 * @see org.libresource.so6.core.exec.Main
 * @since JDK1.4
 */
public class Rename {
    /**
     * Make the rename:
     * <ul>
     * <li>wscPath : Workspace connection property file path
     * (.so6/1/so6.properties)</li>
     * <li>srcRelativePath : path of the file to move</li>
     * <li>destRelativePath : destination path of the file to move</li>
     * </ul>
     *
     * @param wscPath :
     *            Workspace connection property file path
     *            (.so6/1/so6.properties)
     * @param from :
     *            path of the file to move
     * @param to :
     *            destination path of the file to move
     * @throws Exception
     */
    public Rename(String wscPath, String from, String to)
        throws Exception {
        LogUtils.removeAllHandlers(Logger.getLogger("ui.log"));
        LogUtils.removeAllHandlers(StateMonitoring.getInstance().getXMLMonitoringLogger());

        WsConnection ws = new WsConnection(wscPath);
        File src = new File(ws.getRefCopyPath() + File.separator + from);
        File dst = new File(ws.getRefCopyPath() + File.separator + to);

        if (!src.exists()) {
            throw new Exception("Source file does not exsit ! (" + from + ")");
        }

        if (dst.exists()) {
            throw new Exception("Destination file already exsit ! (" + to + ")");
        }

        //
        long ticket = ws.getNs() + 1;
        org.libresource.so6.core.command.fs.Rename cmd = new org.libresource.so6.core.command.fs.Rename(from, ws, to);
        cmd.setTicket(ticket);

        // send patch
        File dir = FileUtils.createTmpDir();
        File f = File.createTempFile("renamePatch", null, dir);
        OutputStreamWriter osw = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(f.getPath())));
        osw.write("<?xml version=\"1.0\"?>\n");
        osw.write("<patch>");
        osw.write("<name>" + Base64.encodeBytes(ws.getWsName().getBytes("UTF-8")) + "</name>");
        osw.write("<begin>" + ticket + "</begin>");
        osw.write("<end>" + ticket + "</end>");
        osw.write("<comment>" + Base64.encodeBytes("Rename".getBytes("UTF-8")) + "</comment>");
        osw.write("<command>");
        osw.write("<class>" + cmd.getClass().getName() + "</class>");
        cmd.toXML(osw);
        osw.write("</command>");
        osw.write("</patch>");
        osw.flush();
        osw.close();

        //
        /*
         * FileReader fr = new FileReader(f); LineNumberReader lnr = new
         * LineNumberReader(fr); String line = null; System.out.println(); while
         * ((line = lnr.readLine()) != null) System.out.println(line);
         * System.out.println(); fr.close();
         */
        //		
        ws.getClient().sendPatch(ticket, ticket, f.getPath(), true);
        System.out.println("Patch sent");

        //
        ws.update();
        System.out.println("Patch applied locally");
    }

    /**
     * Make the rename and exit !
     *
     * @param args
     *            <ul>
     *            <li>wscPath : Workspace connection property file path
     *            (.so6/1/so6.properties)</li>
     *            <li>srcRelativePath : path of the file to move</li>
     *            <li>destRelativePath : destination path of the file to move
     *            </li>
     *            </ul>
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: wscPath srcRelativePath destRelativePath");
            System.err.println(" (1) wscPath: Workspace path");
            System.err.println(" (2) srcRelativePath: path of the file to move");
            System.err.println(" (3) destRelativePath: destination path of the file to move");
        } else if (args.length == 3) {
            Rename mv = new Rename(args[0], args[1], args[2]);
            System.exit(0);
        }
    }
}
