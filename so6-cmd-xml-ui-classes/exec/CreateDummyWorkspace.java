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
package org.libresource.so6.core.exec;

import org.libresource.so6.core.client.DummyClient;

import java.io.File;


/**
 * @author smack
 */
public class CreateDummyWorkspace {
    private String path;
    private String qPath;
    private String wsName;

    public CreateDummyWorkspace(String basePath, String queuePath, String wsName) {
        this.path = basePath;
        this.qPath = queuePath;
        this.wsName = wsName;
    }

    public void execute() throws Exception {
        new CreateGenericWsConnection(path, wsName, CreateGenericWsConnection.FS_CLIENT_NAME, "", "", qPath, null).executeLocalCreation();
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: path qPath wsName");
            System.err.println(" (1) path: path to share");
            System.err.println(" (2) qPath: queue path of the dummy client");
            System.err.println(" (3) wsName: workspace name");
        } else {
            File queueBasePath = new File(args[1]);
            File patch = new File(queueBasePath, DummyClient.PATCHFILES);
            File cmds = new File(queueBasePath, DummyClient.CMDS);
            File attach = new File(queueBasePath, DummyClient.ATTACH);

            if (queueBasePath.exists() && patch.exists() && cmds.exists() && attach.exists()) {
                new CreateDummyWorkspace(args[0], args[1], args[2]).execute();
            } else {
                System.err.println("Invalide queue path...");
            }

            System.exit(0);
        }
    }
}
