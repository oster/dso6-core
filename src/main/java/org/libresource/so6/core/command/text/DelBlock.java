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

import jlibdiff.HunkDel;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.DBType;
import org.libresource.so6.core.engine.util.Base64;
import org.libresource.so6.core.engine.util.FileUtils;

public class DelBlock extends UpdateTextFile {
    private static final long serialVersionUID = 3;
    private int deletePoint = -1;
    private List<String> linesToRemove = null;

    public DelBlock(long ticket, String path, String wsName, long time, boolean conflict, int deletePoint, List<String> linesToRemove) {
        super(ticket, path, wsName, time, conflict, null);
        this.deletePoint = deletePoint;
        this.linesToRemove = linesToRemove;
    }

    public DelBlock(String path, WsConnection ws, int deletePoint, List<String> c) {
        super(path, ws);
        this.deletePoint = deletePoint;
        this.linesToRemove = new ArrayList<String>(c);
    }

    public DelBlock(String path, WsConnection ws, HunkDel hd) {
        super(path, ws);
        this.deletePoint = hd.getLD2(); // + 1;
        this.linesToRemove = new ArrayList<String>(hd.getOldContent());
    }

    public DelBlock(AddBlock a, WsConnection ws) {
        super(a.getPath(), ws);
        this.deletePoint = a.getInsertPoint();
        this.linesToRemove = new ArrayList<String>(a.getContent());
    }

    public int getSize() {
        return linesToRemove.size();
    }

    public void setDeletePoint(int deletePoint) {
        this.deletePoint = deletePoint;
    }

    public int getDeletePoint() {
        return this.deletePoint;
    }

    public void slide(int inc) {
        this.deletePoint += inc;
    }

    // size = size - x ; cut x first lines
    public void cutHeadTo(int x) {
        linesToRemove.subList(0, x).clear();
        this.deletePoint += x;
    }

    // size = size - x ; cut x end lines
    public void cutTailFrom(int x) {
        int t = linesToRemove.size();
        linesToRemove.subList(t - x, t).clear();
    }

    public void setOldContent(List<String> c) {
        this.linesToRemove = new ArrayList<String>(c);
    }

    public List<String> getOldContent() {
        return this.linesToRemove;
    }

    @Override
	public void execute(String dir, DBType dbt) throws Exception {
        doTheJobOnFile(dir + File.separator + path);
    }

    @Override
	public void doTheJobOnFile(String path) throws Exception {
        File tmpFile = File.createTempFile("DelBlock", null);
        File f = new File(path);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(f), Charset.forName(System.getProperty("file.encoding")));
        LineNumberReader input = new LineNumberReader(isr);
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(tmpFile), Charset.forName(System.getProperty("file.encoding")));
        PrintWriter output = new PrintWriter(osw);

        String tmpLine;

        if (f.length() != 0) {
            while ((input.getLineNumber() + 1) < this.deletePoint) {
                tmpLine = input.readLine();

                if (tmpLine == null) {
                    break;
                }

                output.println(tmpLine);
            }
        }

        for (int i = 0; i < linesToRemove.size(); i++) {
            if (input.readLine() == null) {
                break;
            }
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
        return "DelBlock(" + path + "," + getDeletePoint() + "," + getSize() + ")";
    }

    public String toStringVerbose() {
        return "DelBlock(" + path + "," + getDeletePoint() + "," + getSize() + "," + getOldContent() + ")";
    }

    @Override
	public boolean equals(Object o) {
        if (o instanceof DelBlock) {
            DelBlock a = (DelBlock) o;

            return path.equals(a.path) && (deletePoint == a.deletePoint) && linesToRemove.equals(a.linesToRemove);
        } else {
            return false;
        }
    }

    @Override
	public void toXML(Writer osw) throws IOException {
        super.toXML(osw);
        osw.write("<deletepoint>" + deletePoint + "</deletepoint>");
        osw.write("<linesToRemove>");

        Iterator<String> it = linesToRemove.iterator();

        while (it.hasNext()) {
            String line = (String) it.next();
            osw.write("<line>");
            osw.write(Base64.encodeBytes(line.getBytes("UTF-8")));
            osw.write("</line>");
        }

        osw.write("</linesToRemove>");
        osw.flush();
    }
}
