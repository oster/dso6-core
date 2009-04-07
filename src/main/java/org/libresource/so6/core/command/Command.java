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
package org.libresource.so6.core.command;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.DBType;
import org.libresource.so6.core.engine.util.Base64;
import org.libresource.so6.core.engine.util.ObjectCloner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;


abstract public class Command implements Serializable, Comparable {
    private static final long serialVersionUID = 3;
    public static final String ATTACHEMENT = "attachement";
    protected long ticket = -1;
    protected String path;
    protected boolean conflict = false;
    protected long time = -1;
    protected String wsName = "";
    protected String wspath;
    protected String attachement; // name of an attached file...

    public Command(String path, WsConnection ws) {
        this(path, ws.getWsName(), ws.getPath());
    }

    // Default constructor
    public Command(long ticket, String path, String wsName, long time, boolean conflict, String attachement) {
        this.ticket = ticket;
        this.path = path;
        this.wsName = wsName;
        this.time = time;
        this.conflict = conflict;
        this.attachement = attachement;
    }

    protected Command(String path, String wsName, String wspath) {
        this.wsName = wsName;
        this.wspath = wspath;
        this.time = System.currentTimeMillis();
        this.path = path.replaceAll("\\\\", "/");
    }

    // Comparable
    public int compareTo(Object o) {
        Command cmd = (Command) o;

        if (this.getTicket() < cmd.getTicket()) {
            return -1;
        }

        if (this.getTicket() == cmd.getTicket()) {
            return 0;
        }

        if (this.getTicket() > cmd.getTicket()) {
            return 1;
        }

        return 0;
    }

    public Command max(Command c) {
        if (this.getTicket() > c.getTicket()) {
            return this;
        } else {
            return c;
        }
    }

    public Command min(Command c) {
        if (this.getTicket() <= c.getTicket()) {
            return this;
        } else {
            return c;
        }
    }

    abstract public void execute(String dir, DBType dbt)
        throws Exception;

    abstract public boolean equals(Object obj);

    public Object clone() {
        Object result = null;

        try {
            result = ObjectCloner.deepCopy(this);
        } catch (Exception e) {
            e.printStackTrace();

            //System.exit(1);
        }

        return result;
    }

    public String toString() {
        String cmdName = getClass().getName();
        cmdName = cmdName.substring(cmdName.lastIndexOf(".") + 1);

        return "(" + this.getTicket() + ")." + cmdName;
    }

    // Common data
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTicket() {
        return ticket;
    }

    public void setTicket(long ticket) {
        this.ticket = ticket;
    }

    public boolean isConflict() {
        return conflict;
    }

    public void setConflict(boolean conflict) {
        this.conflict = conflict;
    }

    public String getWsName() {
        return wsName;
    }

    public void setWsName(String string) {
        wsName = string;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long l) {
        time = l;
    }

    // For attachement
    public String getAttachement() {
        return attachement;
    }

    public void setAttachement(String string) {
        attachement = string;
    }

    /**
     * @param osw
     */
    public void toXML(Writer osw) throws IOException {
        osw.write("<ticket>" + ticket + "</ticket>");
        osw.write("<from>" + Base64.encodeBytes(wsName.getBytes("UTF-8")) + "</from>");
        osw.write("<time>" + time + "</time>");
        osw.write("<path>" + Base64.encodeBytes(path.getBytes("UTF-8")) + "</path>");

        if (attachement != null) {
            osw.write("<" + ATTACHEMENT + ">");

            // with stream
            FileInputStream fis = new FileInputStream(attachement);
            byte[] b = new byte[3145728];
            int bytecount = 0;

            while ((bytecount = fis.read(b)) > 0) {
                osw.write(Base64.encodeBytes(b, 0, bytecount));
            }

            fis.close();
            osw.write("</" + ATTACHEMENT + ">");
            osw.flush();
        }
    }
}
