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
import org.libresource.so6.core.engine.FileParser;


public class NoOp extends NeutralCommand {
    private static final long serialVersionUID = 3;
    private Command cmd;

    public NoOp(int ticket, String path, String wsName, long time, Command cmd) {
        super(ticket, path, wsName, time, false, null);
        this.cmd = cmd;
    }

    public NoOp(Command cmd, WsConnection ws) {
        super(cmd.getPath(), ws);
        this.cmd = cmd;
    }

    public String toString() {
        return "NoOp(" + cmd + ")";
    }

    public boolean equals(Object o) {
        if (o instanceof NoOp) {
            return cmd.equals(((NoOp) o).cmd);
        }

        return false;
    }

    public void execute(String dir, DBType dbt) throws Exception {
    }

    public void postSend(FileParser fp) throws Exception {
    }

    public Command getCommand() {
        return cmd;
    }

    public void setCommand(Command cmd) {
        this.cmd = cmd;
    }
}
