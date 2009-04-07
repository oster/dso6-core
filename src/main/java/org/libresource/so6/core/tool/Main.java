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
package org.libresource.so6.core.tool;

import org.libresource.so6.core.Workspace;
import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.client.DummyClient;
import org.libresource.so6.core.exec.CreateDummyWorkspace;

import java.io.File;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


/**
 * @author molli
 */
public class Main {
    private String clientPath;
    private String[] dirs;

    private Main(String clientPath, String[] dirs) {
        this.clientPath = clientPath;
        this.dirs = dirs;
        Logger.getLogger("fileparser.log").setLevel(Level.OFF);

        Handler[] handlers = Logger.getLogger("").getHandlers();

        for (int index = 0; index < handlers.length; index++) {
            Logger.getLogger("").removeHandler(handlers[index]);
        }

        handlers = Logger.getLogger("ui.log").getHandlers();

        for (int index = 0; index < handlers.length; index++) {
            Logger.getLogger("ui.log").removeHandler(handlers[index]);
        }

        Logger.getLogger("ui.log").setLevel(Level.SEVERE);

        Handler ca = new ConsoleHandler();
        ca.setFormatter(new so6Formatter());
        Logger.getLogger("ui.log").addHandler(ca);
    }

    private void doit() throws Exception {
        DummyClient dc = new DummyClient(clientPath);
        CreateDummyWorkspace cdr;

        for (int i = 0; i < dirs.length; i++) {
            File f = new File(dirs[i] + File.separator + Workspace.SO6PREFIX + File.separator + "1" + File.separator + WsConnection.SO6_WSC_FILE);

            if (!f.exists()) {
                cdr = new CreateDummyWorkspace(dirs[i], clientPath, dirs[i]);
                cdr.execute();
            }
        }

        for (int i = 0; i < dirs.length; i++) {
            Logger.getLogger("ui.log").info("Synchronizing:" + dirs[i]);

            WsConnection ws = new WsConnection(dirs[i] + File.separator + Workspace.SO6PREFIX + File.separator + "1" + File.separator +
                    WsConnection.SO6_WSC_FILE);
            ws.update();
            ws.commit("sync " + dirs[i]);
        }

        for (int i = 0; i < (dirs.length - 1); i++) {
            Logger.getLogger("ui.log").info("Synchronizing:" + dirs[i]);

            WsConnection ws = new WsConnection(dirs[i] + File.separator + Workspace.SO6PREFIX + File.separator + "1" + File.separator +
                    WsConnection.SO6_WSC_FILE);
            ws.update();
            ws.commit("sync " + dirs[i]);
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            throw new Exception("Usage: <Op Queue dir> <dir1> <dir2>... <dirn>");
        }

        String[] dirs = new String[args.length - 1];

        for (int i = 1; i < args.length; i++) {
            dirs[i - 1] = new File(args[i]).getAbsolutePath();
        }

        Main m = new Main(args[0], dirs);
        m.doit();
    }

    public class so6Formatter extends Formatter {
        public String format(LogRecord record) {
            return record.getMessage() + "\n";
        }
    }
}
