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
package org.libresource.so6.core.engine.log.monitoring;

import org.libresource.so6.core.engine.log.LogPrinter;
import org.libresource.so6.core.engine.log.MessageWriter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import java.util.Iterator;
import java.util.Vector;
import java.util.logging.LogRecord;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * @author smack
 */
public class XMLLogHandler extends DefaultHandler implements LogPrinter {
    private PipedOutputStream out;
    private PipedInputStream in;

    //
    private StringBuffer buffer;
    private String currentTag;
    private String rootName;
    private boolean isRoot = true;
    private boolean globalComment = false;

    //
    private TreeContext context;

    //
    private Vector messagesListeners;

    public XMLLogHandler() throws Exception {
        out = new PipedOutputStream();
        in = new PipedInputStream(out);
        context = new TreeContext();
        messagesListeners = new Vector();
        isRoot = true;
    }

    public void publish(LogRecord record) {
        try {
            out.write(record.getMessage().getBytes());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMessageListener(MessageWriter writer) {
        messagesListeners.add(writer);
    }

    public void removeMessageListener(MessageWriter writer) {
        messagesListeners.remove(writer);
    }

    public void printMessage(String message) {
        for (Iterator i = messagesListeners.iterator(); i.hasNext();) {
            ((MessageWriter) i.next()).printMessage(globalComment, message);
        }
    }

    public InputStream getInputStream() {
        return in;
    }

    public void characters(char[] buff, int offset, int len)
        throws SAXException {
        buffer.append(buff, offset, len);
    }

    public void endElement(String namespaceuri, String sname, String qname)
        throws SAXException {
        //System.out.println("endElement");
        if (qname.equals("COMMENT")) {
            printMessage(buffer.toString());
        }

        if (qname.equals("SUBCALL")) {
            context.endPart();
        }

        // Notify readers that the stream is finished.
        if (rootName.equals(qname)) {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void startElement(String namespaceuri, String sname, String qname, Attributes attr)
        throws SAXException {
        //System.out.println("startElement");
        currentTag = qname;
        buffer = new StringBuffer();

        //
        if (isRoot) {
            rootName = currentTag;

            //System.out.println("Root: " + rootName);
        }

        isRoot = false;

        // General elements
        if (qname.equals("COMMENT")) {
            if (attr.getValue("global") != null) {
                globalComment = Boolean.valueOf(attr.getValue("global")).booleanValue();
            } else {
                globalComment = false;
            }
        } else {
            globalComment = false;
        }

        if (qname.equals("SUBCALL")) {
            String nbCall = attr.getValue("nbCall");

            if (nbCall == null) {
                context.startPart();
            } else {
                context.startPart(Integer.parseInt(nbCall));
            }
        }

        if (qname.equals("STATE")) {
            double from = Double.parseDouble(attr.getValue("from"));
            double to = Double.parseDouble(attr.getValue("to"));
            double current = Double.parseDouble(attr.getValue("current"));
            context.setLocalState(from, to, current);
        }

        if (attr.getValue("comment") != null) {
            printMessage(attr.getValue("comment"));
        }
    }

    //
    public TreeContext getContext() {
        return context;
    }

    public static void main(String[] args) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        XMLLogHandler xmlLogger = new XMLLogHandler();
        saxParser.parse(args[0], xmlLogger);
    }
}
