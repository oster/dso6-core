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
/*
 * Created on 12 f�vr. 2004
 */
package org.libresource.so6.core.engine;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.command.Command;
import org.libresource.so6.core.command.NeutralCommand;
import org.libresource.so6.core.command.fs.AddBinaryFile;
import org.libresource.so6.core.command.fs.AddDir;
import org.libresource.so6.core.command.fs.Remove;
import org.libresource.so6.core.command.fs.Rename;
import org.libresource.so6.core.command.fs.UpdateBinaryFile;
import org.libresource.so6.core.command.text.AddBlock;
import org.libresource.so6.core.command.text.AddTxtFile;
import org.libresource.so6.core.command.text.DelBlock;
import org.libresource.so6.core.engine.util.Base64;
import org.libresource.so6.core.engine.util.FileUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * @author molli
 */
public class PatchFile {
    private String patchfile;
    //private OpVector locals;
    private File baseAttachDir;

    public PatchFile(String patchfile) {
        this.patchfile = patchfile;
    }

    public static void makePatch(OpVector opv, OutputStreamWriter osw, List<String> patchFilter, long fromTicket, long toTicket, String origin, String comment)
        throws Exception {
        osw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        osw.write("<patch>");
        osw.write("<name>" + Base64.encodeBytes(origin.getBytes("UTF-8")) + "</name>");
        osw.write("<begin>" + fromTicket + "</begin>");
        osw.write("<end>" + toTicket + "</end>");

        if (comment != null) {
            osw.write("<comment>" + Base64.encodeBytes(comment.getBytes("UTF-8")) + "</comment>");
        }

        long ticket = fromTicket;
        ListIterator<Command> iterator = opv.getCommands();
        Command cmd = null;

        while ((cmd = (Command) iterator.next()) != null) {
            if (cmd instanceof NeutralCommand) {
                continue;
            }

            if ((patchFilter != null) && !patchFilter.contains(cmd.getPath())) {
                continue;
            }

            StateMonitoring.getInstance().setXMLMonitoringState(fromTicket, toTicket, ticket, "");
            cmd.setTicket(ticket);
            ticket++;
            iterator.set(cmd);
            osw.write("<command>");
            osw.write("<class>" + cmd.getClass().getName() + "</class>");
            cmd.toXML(osw);
            osw.write("</command>");
        }

        osw.write("</patch>");
        osw.flush();
    }

    public void patch(String dir, DBType dbt) throws Exception {
        baseAttachDir = FileUtils.createTmpDir();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(new File(patchfile), new ApplyPatchHandler(dir, dbt));
        dbt.save();
    }

    public void merge(WsConnection ws, OpVector locals)
        throws Exception {
        baseAttachDir = new File(ws.getMergedAttachPath());

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(new File(patchfile), new MergePatchHandler(ws, locals));
        ws.getDBType().save();
    }

    public void buildOpVector(OpVector vector, String baseAttachDir, List<String> filter)
        throws Exception {
        this.baseAttachDir = new File(baseAttachDir);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(new File(patchfile), new BuildOpVectorPatchHandler(vector, filter));
    }

    public void buildOpVector(InputStream in, OpVector vector, String baseAttachDir, List<String> filter)
        throws Exception {
        this.baseAttachDir = new File(baseAttachDir);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(in, new BuildOpVectorPatchHandler(vector, filter));
    }

    private boolean checkCommandType(String cmdFromPatch, String realCmd) {
        return cmdFromPatch.substring(cmdFromPatch.lastIndexOf(".")).equals(realCmd.substring(realCmd.lastIndexOf(".")));
    }

    public class ApplyPatchHandler extends PatchHandler {
        private String dir;
        private DBType dbt;

        ApplyPatchHandler(String dir, DBType dbt) {
            this.dir = dir;
            this.dbt = dbt;
        }

        @Override
		public void doit(Command cmd) {
            try {
                StateMonitoring.getInstance().setXMLMonitoringState(fromTicket, toTicket, ticket, "Apply patch(" + cmd + ")");
                Logger.getLogger("ui.log").info("executing:" + cmd);
                cmd.execute(dir, dbt);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public class MergePatchHandler extends PatchHandler {
        private OpVector locals;
        private WsConnection ws;

        MergePatchHandler(WsConnection ws, OpVector locals) {
            this.locals = locals;
            this.ws = ws;
        }

        @Override
		public void doit(Command cmd) {
            try {
                StateMonitoring.getInstance().setXMLMonitoringState(fromTicket, toTicket, ticket, "Merging patch(" + cmd + ")");
                ws.merge(cmd, locals);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public class BuildOpVectorPatchHandler extends PatchHandler {
        private OpVector vector;
        //private WsConnection ws;
        private List<String> filter;

        BuildOpVectorPatchHandler(OpVector vector, List<String> filter) {
            this.vector = vector;
            this.filter = filter;
        }

        public void setFilter(List<String> filter) {
            this.filter = filter;
        }

        @Override
		public void doit(Command cmd) {
            try {
                if (filter == null) {
                    vector.add(cmd);
                } else {
                    if (filter.contains(cmd.getPath())) {
                        vector.add(cmd);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    abstract class PatchHandler extends DefaultHandler {
        private String tag;
        private StringBuffer buffer;
        private FileOutputStream fos;
        private Command cmd;
        private List<String> alist;

        // for patch
        protected long fromTicket = -1;
        protected long toTicket = -1;
        private String className;
        private String filename;
        protected long ticket;
        private String path;
        private String newPath;
        private String wsName;
        private Long time;

        // For txt cmds
        private Integer insertPoint;
        private Integer deletePoint;

        // For xml cmds
        //private String xmlNodePath;
        //private String xmlAttributeName;
        //private String xmlAttributeValue;
        //private String xmlOldAttributeValue;
        //private String attributeName;
        //private TreeNode treeNode;

        PatchHandler() {
        }

        @Override
		public void characters(char[] buff, int offset, int len)
            throws SAXException {
            // TODO improve base64 use
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
            } else {
                buffer.append(buff, offset, len);
            }
        }

        @Override
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

            if (qname.equals("begin")) {
                fromTicket = Long.parseLong(buffer.toString());
            }

            if (qname.equals("end")) {
                toTicket = Long.parseLong(buffer.toString());
            }

            if (qname.equals("class")) {
            	className = buffer.toString();
            }

            if (qname.equals("ticket")) {
                ticket = Long.parseLong(buffer.toString());
            }

            if (qname.equals("from")) {
                try {
                    wsName = new String(Base64.decode(buffer.toString()), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO: handle exception
                }
            }

            if (qname.equals("newpath")) {
                try {
                    newPath = new String(Base64.decode(buffer.toString()), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO: handle exception
                }
            }

            if (qname.equals("time")) {
                time = new Long(buffer.toString());
            }

            if (qname.equals("path")) {
                try {
                    path = new String(Base64.decode(buffer.toString()), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO: handle exception
                }
            }

            if (qname.equals("insertpoint")) {
                insertPoint = new Integer(buffer.toString());
            }

            if (qname.equals("deletepoint")) {
                deletePoint = new Integer(buffer.toString());
            }

            if (qname.equals("line")) {
                try {
                    String s = new String(Base64.decode(buffer.toString()), "UTF-8");
                    alist.add(s);
                } catch (UnsupportedEncodingException e) {
                    // TODO: handle exception
                }
            }
            
/* TODO: Reintegrate XML support as a plugin feature?

            // For XML
            if (qname.equals("treeNode")) {
                int pos = -1;

                while ((pos = buffer.indexOf("\n")) != -1) {
                    buffer.deleteCharAt(pos);

                    //System.out.println("Remove invalide base64 char");
                }

                try {
                    treeNode = XmlUtil.importNode(buffer.toString());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            if (qname.equals("xmlNodePath")) {
                xmlNodePath = buffer.toString();
            }

            if (qname.equals("xmlAttributeName")) {
                try {
                    xmlAttributeName = new String(Base64.decode(buffer.toString()), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                }
            }

            if (qname.equals("xmlAttributeValue")) {
                try {
                    xmlAttributeValue = new String(Base64.decode(buffer.toString()), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                }
            }

            if (qname.equals("xmlOldAttributeValue")) {
                try {
                    xmlOldAttributeValue = new String(Base64.decode(buffer.toString()), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                }
            }
*/

            // Build real command
            if (qname.equals("command")) {
                if (checkCommandType(className, Rename.class.getName())) {
                    cmd = new Rename(ticket, path, wsName, time.longValue(), false, null, newPath);
                }

                if (checkCommandType(className, AddDir.class.getName())) {
                    cmd = new AddDir(ticket, path, wsName, time.longValue(), false, null);
                }

                if (checkCommandType(className, AddTxtFile.class.getName())) {
                    cmd = new AddTxtFile(ticket, path, wsName, time.longValue(), false, filename);
                }

                if (checkCommandType(className, Remove.class.getName())) {
                    cmd = new Remove(ticket, path, wsName, time.longValue(), false, null);
                }

                if (checkCommandType(className, AddBlock.class.getName())) {
                    cmd = new AddBlock(ticket, path, wsName, time.longValue(), false, insertPoint.intValue(), alist);
                }

                if (checkCommandType(className, DelBlock.class.getName())) {
                    cmd = new DelBlock(ticket, path, wsName, time.longValue(), false, deletePoint.intValue(), alist);
                }

                if (checkCommandType(className, AddBinaryFile.class.getName())) {
                    cmd = new AddBinaryFile(ticket, path, wsName, time.longValue(), false, filename);
                }

                if (checkCommandType(className, UpdateBinaryFile.class.getName())) {
                    cmd = new UpdateBinaryFile(ticket, path, wsName, time.longValue(), false, filename);
                }

/* TODO: Reintegrate XML support as a plugin feature?
                // XML
                if (checkCommandType(className, AddXmlFile.class.getName())) {
                    cmd = new AddXmlFile(ticket, path, wsName, time.longValue(), filename);
                }

                if (checkCommandType(className, DeleteAttribute.class.getName())) {
                    cmd = new DeleteAttribute(ticket, path, wsName, time.longValue(), xmlNodePath, xmlAttributeName);
                }

                if (checkCommandType(className, DeleteNode.class.getName())) {
                    cmd = new DeleteNode(ticket, path, wsName, time.longValue(), xmlNodePath, treeNode);
                }

                if (checkCommandType(className, InsertAttribute.class.getName())) {
                    cmd = new InsertAttribute(ticket, path, wsName, time.longValue(), xmlNodePath, xmlAttributeName, xmlAttributeValue);
                }

                if (checkCommandType(className, InsertNode.class.getName())) {
                    cmd = new InsertNode(ticket, path, wsName, time.longValue(), xmlNodePath, treeNode);
                }

                if (checkCommandType(className, UpdateAttribute.class.getName())) {
                    cmd = new UpdateAttribute(ticket, path, wsName, time.longValue(), xmlNodePath, xmlAttributeName, xmlOldAttributeValue, xmlAttributeValue);
                }
*/
                if (cmd != null) {
                    doit(cmd);
                    cmd = null;
                    alist = null;
                } else {
                    throw new RuntimeException("Class " + classname + " unmanaged");
                }
            }
        }

        public abstract void doit(Command cmd);

        @Override
		public void startElement(String namespaceuri, String sname, String qname, Attributes attr)
            throws SAXException {
            tag = qname;
            buffer = new StringBuffer();

            if (qname.equals("linesToAdd") || qname.equals("linesToRemove")) {
                alist = new ArrayList<String>();
            }

            if (qname.equals(Command.ATTACHEMENT)) {
                try {
                    File f = File.createTempFile("attach", null, baseAttachDir);
                    fos = new FileOutputStream(f);
                    filename = f.getPath();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
