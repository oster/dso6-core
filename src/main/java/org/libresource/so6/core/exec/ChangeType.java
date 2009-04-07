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
import org.libresource.so6.core.command.Command;
import org.libresource.so6.core.command.fs.AddBinaryFile;
import org.libresource.so6.core.command.fs.Remove;
import org.libresource.so6.core.command.text.AddTxtFile;
import org.libresource.so6.core.command.xml.AddXmlFile;
import org.libresource.so6.core.engine.log.LogUtils;
import org.libresource.so6.core.engine.util.Base64;
import org.libresource.so6.core.engine.util.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.util.logging.Logger;


/**
 * The <code>ChangeType</code> class is used to change the type of a
 * previously syncronized file.
 * <p>
 * For exemple, an XML file has been detected as a text file but as it's an
 * important file, you prefere to synchronize it as a binary file to prevent
 * inside merge. Then you call this class to do so.
 * <p>
 * Some Exemples :
 * <ul>
 * <li>java -cp so6.jar ChangeType .so6/1/so6.properties build.xml BIN</li>
 * <li>java -cp so6.jar ChangeType .so6/1/so6.properties build.xml XML</li>
 * <li>java -cp so6.jar ChangeType .so6/1/so6.properties build.xml TXT</li>
 * </ul>
 *
 * @author Smack
 * @version 1.0, 26/05/04
 * @see org.libresource.so6.core.exec.Main
 * @since JDK1.4
 */
public class ChangeType {
    public final static String SUPPORTED_TYPE = "BIN TXT XML";

    public ChangeType(String wsPath, String filePath, String fileType)
        throws Exception {
        LogUtils.removeAllHandlers(Logger.getLogger("ui.log"));
        LogUtils.removeAllHandlers(StateMonitoring.getInstance().getXMLMonitoringLogger());

        if (SUPPORTED_TYPE.indexOf(fileType.toUpperCase()) == -1) {
            throw new Exception("Invalide type declaration: " + fileType + " is not a valide type. (" + SUPPORTED_TYPE + ")");
        }

        WsConnection ws = new WsConnection(wsPath);
        File file = new File(ws.getRefCopyPath() + File.separator + filePath);

        if (!file.exists()) {
            throw new Exception("File does not exist ! (" + filePath + ")");
        }

        Command cmd1;
        Command cmd2 = null;

        //
        long ticket = ws.getNs() + 1;
        cmd1 = new Remove(filePath, ws);
        cmd1.setTicket(ticket);
        ticket++;

        if (fileType.toUpperCase().equals("BIN")) {
            cmd2 = new AddBinaryFile(filePath, ws);
            cmd2.setTicket(ticket);
        }

        if (fileType.toUpperCase().equals("TXT")) {
            cmd2 = new AddTxtFile(filePath, ws);
            cmd2.setTicket(ticket);
        }

        if (fileType.toUpperCase().equals("XML")) {
            cmd2 = new AddXmlFile(filePath, ws);
            cmd2.setTicket(ticket);
        }

        // send patch
        File dir = FileUtils.createTmpDir();
        File f = File.createTempFile("changeTypePatch", null, dir);
        OutputStreamWriter osw = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(f.getPath())));
        osw.write("<?xml version=\"1.0\"?>\n");
        osw.write("<patch>");
        osw.write("<name>" + Base64.encodeBytes(ws.getWsName().getBytes("UTF-8")) + "</name>");
        osw.write("<begin>" + (ticket - 1) + "</begin>");
        osw.write("<end>" + ticket + "</end>");
        osw.write("<comment>" + Base64.encodeBytes("Change Type".getBytes("UTF-8")) + "</comment>");
        osw.write("<command>");
        osw.write("<class>" + cmd1.getClass().getName() + "</class>");
        cmd1.toXML(osw);
        osw.write("</command>");
        osw.write("<command>");
        osw.write("<class>" + cmd2.getClass().getName() + "</class>");
        cmd2.toXML(osw);
        osw.write("</command>");
        osw.write("</patch>");
        osw.flush();
        osw.close();

        //
        //		FileReader fr = new FileReader(f);
        //		LineNumberReader lnr = new LineNumberReader(fr);
        //		String line = null;
        //		System.out.println();
        //		while ((line = lnr.readLine()) != null)
        //			System.out.println(line);
        //		System.out.println();
        //		fr.close();
        //		
        ws.getClient().sendPatch(ticket - 1, ticket, f.getPath(), true);
        System.out.println("Patch sent");

        //
        ws.update();
        System.out.println("Patch applied localy");
    }

    /**
     * The <code>ChangeType</code> class is used to change the type of a
     * previously syncronized file.
     * <p>
     * For exemple, an XML file has been detected as a text file but as it's an
     * important file, you prefere to synchronize it as a binary file to prevent
     * inside merge. Then you call this class to do so.
     * <p>
     * Some Exemples :
     * <ul>
     * <li>java -cp so6.jar ChangeType .so6/1/so6.properties build.xml BIN
     * </li>
     * <li>java -cp so6.jar ChangeType .so6/1/so6.properties build.xml XML
     * </li>
     * <li>java -cp so6.jar ChangeType .so6/1/so6.properties build.xml TXT
     * </li>
     * </ul>
     *
     * @param args
     *            <ul>
     *            <li>Path of the WsConnection property file
     *            (.so6/1/so6.properties)</li>
     *            <li>Relative file path that we want to change the type
     *            (src/fr/ecoo/so6/Workspace.java )</li>
     *            <li>The file type (TXT-BIN-XML)</li>
     *            </ul>
     *
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: wscPath filePath type");
            System.err.println(" (1) wscPath: Workspace connection property file path");
            System.err.println(" (2) filePath: relative file path");
            System.err.println(" (3) type: BIN TXT XML");
        } else if (args.length == 3) {
            ChangeType ct = new ChangeType(args[0], args[1], args[2]);
            System.exit(0);
        }
    }
}
