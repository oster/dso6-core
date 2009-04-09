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

import java.util.ArrayList;
import java.util.List;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.DBType;


public class Macro extends Command {
    private static final long serialVersionUID = 3;
    protected Command[] cmds = new Command[2];

    public Macro(Command cmd, WsConnection ws) {
        super(cmd.getPath(), ws);
        setTicket(cmd.getTicket());
    }

    @Override
	public String toString() {
        return new String("Macro(" + path + "," + cmds[0] + "," + cmds[1] + ")");
    }

    public void setTicket(int ticket) {
        if (this.getTicket() == -1) {
            super.setTicket(ticket);
        }

        Command cmd1 = cmds[0];
        Command cmd2 = cmds[1];

        if (cmd1 instanceof Macro) {
            ((Macro) cmd1).setTicket(ticket);
        } else {
            if (cmd1.getTicket() == -1) {
                cmd1.setTicket(ticket);
            }
        }

        if (cmd2 instanceof Macro) {
            ((Macro) cmd2).setTicket(ticket);
        } else {
            if (cmd2.getTicket() == -1) {
                cmd2.setTicket(ticket);
            }
        }
    }

    public Command getCommand(int index) {
        assert (index > 0) && (index <= 2) : "index of must be 1 or 2";

        return cmds[index - 1];
    }

    public void setCommand(Command cmd, int index) {
        assert (index > 0) && (index <= 2) : "index of must be 1 or 2";
        this.cmds[index - 1] = cmd;
    }

    public List<Command> getCommands() {
        List<Command> v = new ArrayList<Command>();
        Command cmd1 = getCommand(1);
        Command cmd2 = getCommand(2);

        if (cmd1 instanceof Macro) {
            v.addAll(((Macro) cmd1).getCommands());
        } else {
            v.add(cmd1);
        }

        if (cmd2 instanceof Macro) {
            v.addAll(((Macro) cmd2).getCommands());
        } else {
            v.add(cmd2);
        }

        return v;
    }

    @Override
	public boolean equals(Object o) {
        if (o instanceof Macro) {
            Macro m = (Macro) o;
            boolean b = path.equals(m.path);

            for (int i = 0; i < cmds.length; i++) {
                b = b && (cmds[i] != null) && (m.cmds[i] != null) && cmds[i].equals(m.cmds[i]);
            }

            return b;
        } else {
            return false;
        }
    }

    @Override
	public void execute(String dir, DBType dbt) throws Exception {
        for (int i = 0; i < cmds.length; i++) {
            if (cmds[i] != null) {
                cmds[i].execute(dir, dbt);
            }
        }
    }
}
