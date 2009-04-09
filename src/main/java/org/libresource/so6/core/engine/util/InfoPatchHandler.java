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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * @author smack
 */
public class InfoPatchHandler extends DefaultHandler {
    // interesting tag name
    private final static String INFO_TAG = "name begin end class path comment contrib";
    private String tag;
    private StringBuffer buffer;
    private String className;
    private String path;

    // information to retrieve
    private long fromTicket = -1;
    private long toTicket = -1;
    private String wsName;
    private String comment = "";
    private Map<String, List<String>> filesIndex;
    private long nbLine;

    public InfoPatchHandler() {
        filesIndex = new Hashtable<String, List<String>>();
        nbLine = 0;
    }

    public long getFromTicket() {
        return fromTicket;
    }

    public long getToTicket() {
        return toTicket;
    }

    public String getWsName() {
        return wsName;
    }

    public String getComment() {
        return comment;
    }

    public Map<String, List<String>> getFileIndex() {
        return filesIndex;
    }

    public long getNbLines() {
        return nbLine;
    }

    @Override
	public void characters(char[] buff, int offset, int len)
        throws SAXException {
        if (INFO_TAG.indexOf(tag) != -1) {
            buffer.append(buff, offset, len);
        }
    }

    @Override
	public void endElement(String namespaceuri, String sname, String qname)
        throws SAXException {
        if (qname.equals("name")) {
            try {
                wsName = new String(Base64.decode(buffer.toString()), "UTF-8");
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        if (qname.equals("begin")) {
            fromTicket = Long.parseLong(buffer.toString());
        }

        if (qname.equals("end")) {
            toTicket = Long.parseLong(buffer.toString());
        }

        if (qname.equals("class")) {
            className = buffer.toString();
        }

        if (qname.equals("comment")) {
            try {
                comment = new String(Base64.decode(buffer.toString()), "UTF-8");
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        if (qname.equals("contrib")) {
            nbLine += Long.parseLong(buffer.toString());
        }

        if (qname.equals("path")) {
            try {
                path = new String(Base64.decode(buffer.toString()), "UTF-8");
            } catch (Exception e) {
                // TODO: handle exception
            }

            List<String> opList = filesIndex.get(path);

            if (opList != null) {
                opList.add(className);
            } else {
                opList = new ArrayList<String>();
                opList.add(className);
                filesIndex.put(path, opList);
            }
        }
    }

    @Override
	public void startElement(String namespaceuri, String sname, String qname, Attributes attr)
        throws SAXException {
        tag = qname;
        buffer = new StringBuffer();
    }

    public static void main(String[] args) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        InfoPatchHandler infoPatch = new InfoPatchHandler();

        saxParser.parse(args[0], infoPatch);
        System.out.println("From ticket: " + infoPatch.getFromTicket());
        System.out.println("To ticket: " + infoPatch.getToTicket());
        System.out.println("WsName: " + infoPatch.getWsName());
        System.out.println("Comment: " + infoPatch.getComment());
        System.out.println("NbLine: " + infoPatch.getNbLines());
    }
}
