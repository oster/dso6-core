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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jlibdiff.HunkAdd;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.DBType;
import org.libresource.so6.core.engine.util.Base64;
import org.libresource.so6.core.engine.util.FileUtils;


public class AddBlock extends UpdateTextFile {
    private static final long serialVersionUID = 3;
    private int insertPoint = -1;
    private List<String> linesToAdd = null;

    public AddBlock(long ticket, String path, String wsName, long time, boolean conflict, int insertPoint, List<String> linesToAdd) {
        super(ticket, path, wsName, time, conflict, null);
        this.insertPoint = insertPoint;
        this.linesToAdd = linesToAdd;
    }

    public AddBlock(String path, WsConnection ws, int insertPoint, List<String> c) {
        super(path, ws);
        this.insertPoint = insertPoint;
        this.linesToAdd = new ArrayList<String>(c);
    }

	public AddBlock(String path, WsConnection ws, HunkAdd ha) {
        super(path, ws);
        this.insertPoint = ha.getLD2(); //+1;
        this.linesToAdd = new ArrayList<String>(ha.getNewContent());
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

    public void setContent(List<String> c) {
        this.linesToAdd = new ArrayList<String>(c);
    }

    public List<String> getContent() {
        return this.linesToAdd;
    }

    @Override
	public void execute(String dir, DBType dbt) throws Exception {
        doTheJobOnFile(dir + File.separator + path);
    }

    @Override
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

        for (Iterator<String> i = linesToAdd.iterator(); i.hasNext();) {
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

    @Override
	public String toString() {
        return "AddBlock(" + path + "," + getInsertPoint() + "," + getSize() + ")";
    }

    public String toStringVerbose() {
        return "AddBlock(" + path + "," + getInsertPoint() + "," + getSize() + "," + getContent() + ")";
    }

    @Override
	public boolean equals(Object o) {
        if (o instanceof AddBlock) {
            AddBlock a = (AddBlock) o;
            boolean b = path.equals(a.path) && (insertPoint == a.insertPoint) && linesToAdd.equals(a.linesToAdd);

            return b;
        } else {
            return false;
        }
    }

    @Override
	public void toXML(Writer osw) throws IOException {
        super.toXML(osw);
        osw.write("<insertpoint>" + insertPoint + "</insertpoint>");
        osw.write("<contrib>");
        osw.write(Long.toString(getSize()));
        osw.write("</contrib>");
        osw.write("<linesToAdd>");

        Iterator<String> it = linesToAdd.iterator();

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
