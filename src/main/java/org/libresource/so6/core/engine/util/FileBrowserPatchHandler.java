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

import org.libresource.so6.core.command.Command;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * @author smack
 */
public class FileBrowserPatchHandler {
    public static final String ATTACH_PROP_FILE = "attach.properties";
    private String baseWorkingDir;

    public FileBrowserPatchHandler(String baseWorkingDir) {
        this.baseWorkingDir = baseWorkingDir;
    }

    public void exportAttachementWithProperties(InputStream patch)
        throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(patch, new PropertyFileBrowser(baseWorkingDir));
    }

    public void exportAttachementWithHtmlIndex(boolean listOpName, InputStream patch)
        throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(patch, new HtmlFileBrowser(baseWorkingDir, listOpName));
    }

    public void exportAttachementWithProperties(File patch)
        throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(patch, new PropertyFileBrowser(baseWorkingDir));
    }

    public void exportAttachementWithHtmlIndex(boolean listOpName, File patch)
        throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(patch, new HtmlFileBrowser(baseWorkingDir, listOpName));
    }

    public static void main(String[] args) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        FileBrowserPatchHandler patchBrowser = new FileBrowserPatchHandler(args[1]);

        if ((args.length == 3) && args[2].equals("html")) {
            patchBrowser.exportAttachementWithHtmlIndex(true, new File(args[0]));
        } else {
            patchBrowser.exportAttachementWithProperties(new File(args[0]));
        }
    }

    public class PropertyFileBrowser extends BrowsePatchHandler {
        public PropertyFileBrowser(String workingDir) {
            super(workingDir);
        }

        public void endDocument() throws SAXException {
            // build html web page
            try {
                Properties props = new Properties();
                File f = new File(baseWorkingDir, ATTACH_PROP_FILE);
                FileOutputStream fos = new FileOutputStream(f);
                props.putAll(filesAttachement);
                props.store(fos, "Liste of the file attachement");
                fos.close();
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }

    public class HtmlFileBrowser extends BrowsePatchHandler {
        private boolean listOp;

        public HtmlFileBrowser(String workingDir, boolean listOp) {
            super(workingDir);
            this.listOp = listOp;
        }

        public void endDocument() throws SAXException {
            // build html web page
            try {
                File f = new File(baseWorkingDir, "index.html");
                FileWriter fw = new FileWriter(f);
                fw.write("<html><body><table>");

                int index = 0;
                Arrays.sort(pathList.toArray());

                for (Iterator i = pathList.iterator(); i.hasNext();) {
                    String localPath = (String) i.next();
                    fw.write("<tr bgcolor=\"#" + (((index++ % 2) == 0) ? "ccccee" : "eeeeee") + "\"><td>" + localPath + "</td><td>");

                    if (filesAttachement.get(localPath) != null) {
                        fw.write("<a href=\"" + filesAttachement.get(localPath) + "\">Download</a>");
                    }

                    fw.write("</td></tr>");

                    if (listOp) {
                        fw.write("<tr><td><ul>");

                        for (Iterator j = ((ArrayList) filesIndex.get(localPath)).iterator(); j.hasNext();) {
                            fw.write("<li>");
                            fw.write((String) j.next());
                            fw.write("</li>");
                        }

                        fw.write("</ul></td><td></td></tr>");
                    }
                }

                fw.write("</table></body></html>");
                fw.close();
            } catch (Exception e) {
                throw new SAXException(e);
            }
        }
    }

    abstract class BrowsePatchHandler extends DefaultHandler {
        // interesting tag name
        private final static String INFO_TAG = "name begin end class path comment";
        private FileOutputStream fos;
        protected File baseWorkingDir;
        private String tag;
        private StringBuffer buffer;
        private String className;
        private String path;

        // information to retreive
        private long fromTicket = -1;
        private long toTicket = -1;
        private String wsName;
        private String comment = "";
        protected Hashtable filesIndex;
        protected Hashtable filesAttachement;
        protected ArrayList pathList;

        public BrowsePatchHandler(String baseWorkingDir) {
            this.filesIndex = new Hashtable();
            this.filesAttachement = new Hashtable();
            this.baseWorkingDir = new File(baseWorkingDir);
            this.pathList = new ArrayList();
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

        public Hashtable getFileIndex() {
            return filesIndex;
        }

        public void characters(char[] buff, int offset, int len)
            throws SAXException {
            if (tag.equals(Command.ATTACHEMENT)) {
                buffer.append(buff, offset, len);

                int pos = -1;

                while ((pos = buffer.indexOf("\n")) != -1) {
                    while ((pos = buffer.indexOf("\n")) != -1) {
                        buffer.deleteCharAt(pos);
                    }

                    int nbCharToDecode = (buffer.length() / 4);

                    try {
                        fos.write(Base64.decode(buffer.substring(0, nbCharToDecode * 4)));
                        buffer.delete(0, nbCharToDecode * 4);
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
            } else if (INFO_TAG.indexOf(tag) != -1) {
                buffer.append(buff, offset, len);
            }
        }

        public void endElement(String namespaceuri, String sname, String qname)
            throws SAXException {
            if (qname.equals(Command.ATTACHEMENT)) {
                try {
                    fos.write(Base64.decode(buffer.toString()));
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            if (qname.equals("name")) {
                try {
                    wsName = new String(Base64.decode(buffer.toString()), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("UTF-8 encoding not supported");
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
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("UTF-8 encoding not supported");
                }
            }

            if (qname.equals("path")) {
                try {
                    path = new String(Base64.decode(buffer.toString()), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("UTF-8 encoding not supported");
                }

                if (!pathList.contains(path)) {
                    pathList.add(path);
                }

                ArrayList opList = (ArrayList) filesIndex.get(path);

                if (opList != null) {
                    opList.add(className);
                } else {
                    opList = new ArrayList();
                    opList.add(className);
                    filesIndex.put(path, opList);
                }
            }
        }

        public void startElement(String namespaceuri, String sname, String qname, Attributes attr)
            throws SAXException {
            tag = qname;
            buffer = new StringBuffer();

            if (qname.equals(Command.ATTACHEMENT)) {
                try {
                    File f = File.createTempFile("attach", null, baseWorkingDir);
                    fos = new FileOutputStream(f);
                    filesAttachement.put(path, f.getName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
