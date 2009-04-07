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

import jlibdiff.HunkAdd;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.DBType;
import org.libresource.so6.core.engine.util.Base64;
import org.libresource.so6.core.engine.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberInputStream;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class AddBlock extends UpdateTextFile {
    private static final long serialVersionUID = 3;
    private int insertPoint = -1;
    private ArrayList linesToAdd = null;

    public AddBlock(long ticket, String path, String wsName, long time, boolean conflict, int insertPoint, ArrayList linesToAdd) {
        super(ticket, path, wsName, time, conflict, null);
        this.insertPoint = insertPoint;
        this.linesToAdd = linesToAdd;
    }

    public AddBlock(String path, WsConnection ws, int insertPoint, Collection c) {
        super(path, ws);
        this.insertPoint = insertPoint;
        this.linesToAdd = new ArrayList(c);
    }

    public AddBlock(String path, WsConnection ws, HunkAdd ha) {
        super(path, ws);
        this.insertPoint = ha.getLD2(); //+1;
        this.linesToAdd = new ArrayList(ha.getNewContent());
    }

    public int getSize() {
        return linesToAdd.size();
    }

    public void setInsertPoint(int insertPoint) {
        this.insertPoint = insertPoint;
    }

    public int getInsertPoint() {
        return this.insertPoint;
    }

    public void slide(int inc) {
        this.insertPoint += inc;
    }

    public void setContent(Collection c) {
        this.linesToAdd = new ArrayList(c);
    }

    public Collection getContent() {
        return this.linesToAdd;
    }

    public void execute(String dir, DBType dbt) throws Exception {
        //for(Iterator i =linesToAdd.iterator();i.hasNext();)
        //    System.out.println(i.next());
        doTheJobOnFile(dir + File.separator + path);
    }

    public void doTheJobOnFile(String path) throws Exception {
        File tmpFile = File.createTempFile("AddBlock", null);
        File f = new File(path);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(f), Charset.forName(System.getProperty("file.encoding")));
        LineNumberReader input = new LineNumberReader(isr);
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(tmpFile), Charset.forName(System.getProperty("file.encoding")));
        PrintWriter output = new PrintWriter(osw);

        String tmpLine;

        if (f.length() != 0) {
            while ((input.getLineNumber() + 1) < this.insertPoint) {
                tmpLine = input.readLine();

                if (tmpLine == null) {
                    break;
                }

                output.println(tmpLine);
            }
        }

        for (Iterator i = linesToAdd.iterator(); i.hasNext();) {
            output.println((String) i.next());
        }

        String line = null;

        while ((line = input.readLine()) != null) {
            output.println(line);
        }

        input.close();
        output.close();
        FileUtils.copy(tmpFile.getPath(), path);

        if (!tmpFile.delete()) {
            tmpFile.deleteOnExit();
        }
    }

    public String toString() {
        return "AddBlock(" + path + "," + getInsertPoint() + "," + getSize() + ")";
    }

    public String toStringVerbose() {
        return "AddBlock(" + path + "," + getInsertPoint() + "," + getSize() + "," + getContent() + ")";
    }

    public boolean equals(Object o) {
        if (o instanceof AddBlock) {
            AddBlock a = (AddBlock) o;
            boolean b = path.equals(a.path) && (insertPoint == a.insertPoint) && linesToAdd.equals(a.linesToAdd);

            //			String msg =
            //				""
            //					+ "\n AddBlock Equals ["
            //					+ Boolean.toString(b)
            //					+ "]"
            //					+ "\n this"
            //					+ "\n path:"
            //					+ path
            //					+ "\n insertPoint:"
            //					+ insertPoint
            //					+ "\n linesToAdd:"
            //					+ linesToAdd.toString()
            //					+ "\n param"
            //					+ "\n path:"
            //					+ a.path
            //					+ "\n insertPoint:"
            //					+ a.insertPoint
            //					+ "\n linesToAdd:"
            //					+ a.linesToAdd.toString()
            //					+ "\n";
            //			Logger.getLogger("arf").info(msg);
            return b;
        } else {
            return false;
        }
    }

    public void toXML(Writer osw) throws IOException {
        super.toXML(osw);
        osw.write("<insertpoint>" + insertPoint + "</insertpoint>");
        osw.write("<contrib>");
        osw.write(Long.toString(getSize()));
        osw.write("</contrib>");
        osw.write("<linesToAdd>");

        Iterator it = linesToAdd.iterator();

        while (it.hasNext()) {
            String line = (String) it.next();
            osw.write("<line>");
            osw.write(Base64.encodeBytes(line.getBytes("UTF-8")));
            osw.write("</line>");
        }

        osw.write("</linesToAdd>");
        osw.flush();
    }
}
