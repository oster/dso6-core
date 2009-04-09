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

import org.libresource.so6.core.Workspace;
import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.client.DummyClient;
import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.exec.ui.ConflictEditor;
import org.libresource.so6.core.exec.ui.PatchInspector;

import java.io.File;

import java.util.ArrayList;
import java.util.Properties;


/**
 *
 * Allow the launch of most so6 applications.
 * <ul>
 * <li>Update local workspace</li>
 * <li>Commit local workspace</li>
 * <li>CreateWorkspace : create a dummy workspace</li>
 * <li>CreateQueue : create a dummy queue</li>
 * <li>Rename a file</li>
 * <li>FindConflict</li>
 * <li>ChangeFileType</li>
 * <li>ComputeLocalTypes</li>
 * <li>Restore</li>
 * <li>PartialCommit</li>
 * <li>AddWsConnection</li>
 * <li>ConflictEditor : Launch the conflict editor</li>
 * <li>HistoryViewer : Launch the history viewer</li>
 * <li>ApplyPatch :</li>
 * <li>PatchInspector :</li>
 * <li>AnonymousAccess :</li>
 * <li>Clean</li>
 * </ul>
 *
 * @author smack
 */
public class Main {
    private static String connection = "1";
    private static String wsBasePath = null;
    private static String wsPath = null;
    private static String noOp = null;
    private static final String[] COMMANDS = {
            "COMMIT", "UPDATE", "CREATEWORKSPACE", "CREATEQUEUE", "RENAME", "FINDCONFLICT", "CHANGETYPE", "COMPUTELOCALTYPES", "RESTORE", "PARTIALCOMMIT",
            "ADDWSCONNECTION", "CONFLICTEDITOR", "APPLYPATCH", "PATCHINSPECTOR", "SYNCHRONIZE", "ANONYMOUSACCESS", "CLEAN"
        };
    private static final int COMMIT = 0;
    private static final int UPDATE = 1;
    private static final int CREATE_WORKSPACE = 2;
    private static final int CREATE_SYNCHRONISER = 3;
    private static final int RENAME = 4;
    private static final int FIND_CONFLICT = 5;
    private static final int CHANGE_TYPE = 6;
    private static final int COMPUTE_LOCAL_TYPES = 7;
    private static final int RESTORE = 8;
    private static final int PARTIAL_COMMIT = 9;
    private static final int ADD_WS_CONNECTION = 10;
    private static final int CONFLICT_EDITOR = 11;
    private static final int HISTORY_VIEWER = 18; //12;
    private static final int APPLY_PATCH = 12; //13;
    private static final int PATCH_INSPECTOR = 13; //14;
    private static final int SYNCHRONIZE = 14; //15;
    private static final int CHECKOUT = 15; //16;
    private static final int CLEAN = 16; //17;
    private static final String HELP = "so6 command line help : \n" + " -h or --help : Get a general help on the so6 command lines\n" +
        " -w or --wsPath : The workspace base path\n" + " -n or --noOp : Simulate an action\n" +
        " -c or --connection : The connection name usually (If not specified, the default value will be 1)\n";
    private static final String HELP_COMMIT = "commit -w workspacepath [-c connection] \"comment\" \n" + "  Exemple:\n\n" +
        "    so6 commit -w . \"A little comment for the commit...\"\n" + "    so6 commit -w . -c 1 \"A little comment for the commit...\"\n";
    private static final String HELP_PARTIAL_COMMIT = "partialCommit -w workspacepath [-c connection] \"comment\" filterfile" + "  Exemple:\n\n" +
        "    so6 partialCommit -w . -c 1 \"A little comment for the commit...\" /myFilter.txt\n" +
        "    so6 partialCommit -w .  \"A little comment for the commit...\" /myFilter.txt\n";
    private static final String HELP_UPDATE = "update -w workspacepath [-c connection]\n" + " Exemple:\n\n" + "    so6 update -w .  -c 1\n";
    private static final String HELP_CREATE_WORKSPACE = "createWorkspace workspacepath queuepath workspacename\n" + "  Exemple:\n\n" +
        "    so6 createWorkspace ~/foo ~/queue1 \"seb loria\"\n";
    private static final String HELP_ADD_WS_CONNECTION = "addWsConnection -w workspacepath queuepath workspacename\n" + "  Exemple:\n\n" +
        "    so6 addWsConnection -w ~/foo ~/queue1 \"seb loria\"\n";
    private static final String HELP_CREATE_SYNCHRONISER = "createQueue queuepath\n" + "  Exemple:\n\n" + "    so6 createQueue ~/queue1 \n";
    private static final String HELP_RENAME = "rename -w workspacepath [-c connection] file1 file2\n" + "  Exemple:\n\n" +
        "    so6 rename  -w  .  dir1/dir2/a.txt  dir1/dir2/b.txt\n" + "    so6 rename  -w  .  -c 1  dir1/dir2/a.txt  dir1/dir2/b.txt\n";
    private static final String HELP_FIND_CONFLICT = "findConflict -w workspacepath [-c connection]\n" + "  Exemple:\n\n" + "    so6 findConflict -w . -c 1\n";
    private static final String HELP_CHANGE_TYPE = "changeType -w workspacepath [-c connection] filepath <BIN|TXT|XML>\n" + "  Exemple:\n\n" +
        "    so6 changeType -w . -c 1  dir1/dir2/b.txt BIN\n";
    private static final String HELP_COMPUTE_LOCAL_TYPES = "computeLocalTypes -w workspacepath [-c connection]\n" + "  Exemple:\n\n" +
        "    so6 computeLocalTypes -w . -c 1\n";
    private static final String HELP_RESTORE = "restore -w workspacepath [-c connection]\n" + "  Exemple:\n\n" + "    so6 restore -w . -c 1";
    private static final String HELP_CONFLICT_EDITOR = "conflictEditor filepath\n" + "  Exemple:\n\n" + "    so6 conflictEditor /a/b/build.txt" +
        WsConnection.SO6_WSC_FILE;
    private static final String HELP_HISTORY_VIEWER = "historyViewer -w workspacepath [-c connection]\n" + "  Exemple:\n\n" + "\n" +
        "    so6 historyViewer -w . /a/b/build.txt";
    private static final String HELP_STUDIO = "studio\n" + "  Exemple:\n\n" + "\n" + "    so6 studio";
    private static final String HELP_APPLY_PATCH = "applyPatch dirToPatch patch1 patch2 ...\n" + "  Exemple:\n\n" + "\n" +
        "    so6 applyPatch dirToPatch /tmp/patch.xml.1 /tmp/patch.xml.2";
    private static final String HELP_PATCH_INSPECTOR = "pacthInspector patchPath\n" + "  Exemple:\n\n" + "\n" + "    so6 patchInspector .so6/1/APPLIED/1.200";
    private static final String HELP_SYNCHRONIZE = "synchronize queuePath wsPath1 wsPath2 ...\n" + "  Exemple:\n\n" + "\n" +
        "    so6 synchronize /usr/dummyQueue1 /home/seb/so6 /home/seb/so6.tmp";
    private static final String HELP_CHECK_OUT = "anonymousAccess checkoutFilePath (lastTicket)\n" + "  Exemple:\n\n" + "\n" +
        "    so6 anonymousAccess /tmp/so6.checkout ";
    private static final String HELP_CLEAN = "clean (-w . -c 2) \n" + "  Exemple:\n\n" + "\n" + "    so6 clean (-w .) ";

    public static boolean askForHelp(String[] param) {
        for (int i = 0; i < param.length; i++) {
            if (param[i].equals("-h") || param[i].equals("--help")) {
                return true;
            }
        }

        return false;
    }

    private static Object[] setOption(String[] param) {
        //		System.out.println("INPUT\n");
        //		for (int i = 0; i < param.length; i++) {
        //			System.out.println(param[i]);
        //		}
        //		System.out.println("\nOUTPUT\n");
        ArrayList result = new ArrayList();

        for (int i = 1; i < param.length; i++) {
            if (param[i].equals("-n") || param[i].equals("--noOp")) {
                if (param.length < (i + 2)) {
                    System.err.println("Option -n / -noOp require a directory path behind");
                    System.exit(-1);
                }

                noOp = param[i + 1];
                i++;

                continue;
            }

            if (param[i].equals("-w") || param[i].equals("--wsPath")) {
                if (param.length < (i + 2)) {
                    System.err.println("Option -w / -wsPath require a workspace base path behind");
                    System.exit(-1);
                }

                wsBasePath = param[i + 1];
                i++;

                continue;
            }

            if (param[i].equals("-c") || param[i].equals("--connection")) {
                if (param.length < (i + 2)) {
                    System.err.println("Option -c / -connection require a number behind (by default it's 1)");
                    System.exit(-1);
                }

                connection = param[i + 1];
                i++;

                continue;
            }

            // Standard param
            result.add(param[i]);
        }

        //		System.out.println("wsPath: " + wsPath);
        //		System.out.println("noOp: " + noOp);
        //		System.out.println("Params: ");
        //		for (int i = 0; i < result.size(); i++) {
        //			System.out.print(result.get(i) + " ");
        //		}
        //		System.out.println();
        if (wsBasePath != null) {
            wsPath = wsBasePath + File.separator + Workspace.SO6PREFIX + File.separator + connection + File.separator + WsConnection.SO6_WSC_FILE;
        }

        return result.toArray();
    }

    private static int getAction(String[] param) {
        if (param.length == 0) {
            return -1;
        }

        for (int i = 0; i < COMMANDS.length; i++) {
            if (COMMANDS[i].equals(param[0].toUpperCase())) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Allow the launch of most so6 applications.
     * <ul>
     * <li>Update local workspace</li>
     * <li>Commit local workspace</li>
     * <li>CreateWorkspace : create a dummy workspace</li>
     * <li>CreateQueue : create a dummy queue</li>
     * <li>Rename a file</li>
     * <li>FindConflict</li>
     * <li>ChangeFileType</li>
     * <li>ComputeLocalTypes</li>
     * <li>Restore</li>
     * <li>PartialCommit</li>
     * <li>AddWsConnection</li>
     * <li>ConflictEditor : Launch the conflict editor</li>
     * <li>HistoryViewer : Launch the history viewer</li>
     * <li>ApplyPatch :</li>
     * <li>PatchInspector</li>
     * <li>AnonymousAccess</li>
     * <li>Clean</li>
     * </ul>
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int action = getAction(args);

        if (askForHelp(args) || (action == -1)) {
            if (action == -1) {
                // Global help
                System.out.println(HELP);
                System.out.println("Type so6 <ACTION> --help for detailed help");

                for (int i = 0; i < COMMANDS.length; i++) {
                    System.out.println("  * " + COMMANDS[i].toLowerCase());
                }
            } else {
                // Local help
                System.err.println(HELP);

                switch (action) {
                case COMMIT:
                    System.err.println(HELP_COMMIT);

                    break;

                case PARTIAL_COMMIT:
                    System.err.println(PARTIAL_COMMIT);

                    break;

                case CHANGE_TYPE:
                    System.err.println(HELP_CHANGE_TYPE);

                    break;

                case COMPUTE_LOCAL_TYPES:
                    System.err.println(HELP_COMPUTE_LOCAL_TYPES);

                    break;

                case CREATE_WORKSPACE:
                    System.err.println(HELP_CREATE_WORKSPACE);

                    break;

                case ADD_WS_CONNECTION:
                    System.err.println(HELP_ADD_WS_CONNECTION);

                    break;

                case CREATE_SYNCHRONISER:
                    System.err.println(HELP_CREATE_SYNCHRONISER);

                    break;

                case FIND_CONFLICT:
                    System.err.println(HELP_FIND_CONFLICT);

                    break;

                case RENAME:
                    System.err.println(HELP_RENAME);

                    break;

                case UPDATE:
                    System.err.println(HELP_UPDATE);

                    break;

                case RESTORE:
                    System.err.println(HELP_RESTORE);

                    break;

                case CONFLICT_EDITOR:
                    System.err.println(HELP_CONFLICT_EDITOR);

                    break;

                case HISTORY_VIEWER:
                    System.err.println(HELP_HISTORY_VIEWER);

                    break;

                case APPLY_PATCH:
                    System.err.println(HELP_APPLY_PATCH);

                    break;

                case PATCH_INSPECTOR:
                    System.err.println(HELP_PATCH_INSPECTOR);

                    break;

                case SYNCHRONIZE:
                    System.err.println(HELP_SYNCHRONIZE);

                    break;

                case CHECKOUT:
                    System.err.println(HELP_CHECK_OUT);

                    break;

                case CLEAN:
                    System.err.println(HELP_CLEAN);

                    break;
                }
            }
        } else {
            // init global options
            Object[] generalParam = setOption(args);

            // Do the job...
            switch (action) {
            case COMMIT:

                if ((generalParam.length == 1) && (wsPath != null)) {
                    Commit commit = new Commit(wsPath, (String) generalParam[0]);

                    if (noOp != null) {
                        commit.simulate(noOp);
                    } else {
                        commit.execute();
                    }
                } else {
                    System.err.println(HELP);
                    System.err.println(HELP_COMMIT);
                }

                break;

            case PARTIAL_COMMIT:

                if ((generalParam.length == 2) && (wsPath != null)) {
                    PartialCommit pcommit = new PartialCommit(wsPath, (String) generalParam[0], (String) generalParam[1]);

                    if (noOp != null) {
                        pcommit.simulate(noOp);
                    } else {
                        pcommit.execute();
                    }
                } else {
                    System.err.println(HELP);
                    System.err.println(HELP_PARTIAL_COMMIT);
                }

                break;

            case UPDATE:

                if ((generalParam.length == 0) && (wsPath != null)) {
                    Update update = new Update(wsPath);

                    if (noOp != null) {
                        update.simulate(noOp);
                    } else {
                        update.execute();
                    }
                } else {
                    System.err.println(HELP);
                    System.err.println(HELP_UPDATE);
                }

                break;

            case CHANGE_TYPE:

                if ((generalParam.length == 2) && (wsPath != null)) {
                    ChangeType.main(new String[] { wsPath, (String) generalParam[0], (String) generalParam[1] });
                } else {
                    System.err.println(HELP);
                    System.err.println(HELP_CHANGE_TYPE);
                }

                break;

            case COMPUTE_LOCAL_TYPES:

                if ((generalParam.length == 0) && (wsPath != null)) {
                    UpdateLocalDBType.main(new String[] { wsPath });
                } else {
                    System.err.println(HELP);
                    System.err.println(HELP_COMPUTE_LOCAL_TYPES);
                }

                break;

            case CREATE_WORKSPACE:

                if (generalParam.length == 3) {
                    new CreateDummyWorkspace((String) generalParam[0], (String) generalParam[1], (String) generalParam[2]).execute();
                } else {
                    System.err.println(HELP);
                    System.err.println(HELP_CREATE_WORKSPACE);
                }

                break;

            case ADD_WS_CONNECTION:

                if (generalParam.length == 3) {
                    Workspace ws = new Workspace((String) generalParam[0]);
                    Properties props = new Properties();
                    props.setProperty(DummyClient.SO6_QUEUE_ID, (String) generalParam[1]);
                    ws.createConnection(props, DummyClient.class.getName(), (String) generalParam[2]);
                } else {
                    System.err.println(HELP);
                    System.err.println(HELP_ADD_WS_CONNECTION);
                }

                break;

            case CREATE_SYNCHRONISER:

                if (generalParam.length == 1) {
                    CreateDummySynchroniser.main(new String[] { (String) generalParam[0] });
                } else {
                    System.err.println(HELP);
                    System.err.println(HELP_CREATE_SYNCHRONISER);
                }

                break;

            case FIND_CONFLICT:

                if ((generalParam.length == 0) && (wsPath != null)) {
                    FindConflict.main(new String[] { wsPath });
                } else {
                    System.err.println(HELP);
                    System.err.println(HELP_FIND_CONFLICT);
                }

                break;

            case RENAME:

                if ((generalParam.length == 2) && (wsPath != null)) {
                    Rename.main(new String[] { wsPath, (String) generalParam[0], (String) generalParam[1] });
                } else {
                    System.err.println(HELP);
                    System.err.println(HELP_RENAME);
                }

                break;

            case RESTORE:

                if ((generalParam.length == 0) && (wsPath != null)) {
                    Restore.main(new String[] { wsPath });
                } else {
                    System.err.println(HELP);
                    System.err.println(HELP_RESTORE);
                }

                break;

            case CONFLICT_EDITOR:

                if (generalParam.length == 1) {
                    ConflictEditor.main(new String[] { (String) generalParam[0] });
                } else {
                    System.err.println(HELP);
                    System.err.println(HELP_CONFLICT_EDITOR);
                }

                break;

            case APPLY_PATCH:

                if ((generalParam.length > 1) && (wsPath == null)) {
                    String[] param = new String[generalParam.length];

                    for (int i = 0; i < param.length; i++) {
                        param[i] = (String) generalParam[i];
                    }

                    ApplyPatch.main(param);
                } else {
                    System.err.println(HELP);
                    System.err.println(HELP_APPLY_PATCH);
                }

                break;

            case PATCH_INSPECTOR:

                if ((generalParam.length == 1) && (wsPath == null)) {
                    PatchInspector.main(new String[] { (String) generalParam[0] });
                } else {
                    System.err.println(HELP);
                    System.err.println(HELP_PATCH_INSPECTOR);
                }

                break;

            case SYNCHRONIZE:

                if ((generalParam.length > 1) && (wsPath == null)) {
                    String[] param = new String[generalParam.length];

                    for (int i = 0; i < param.length; i++) {
                        param[i] = (String) generalParam[i];
                    }

                    org.libresource.so6.core.tool.Main.main(param);
                } else {
                    System.err.println(HELP);
                    System.err.println(HELP_SYNCHRONIZE);
                }

                break;

            case CHECKOUT:

                if ((generalParam.length > 1) && (wsPath == null)) {
                    String[] param = new String[generalParam.length];

                    for (int i = 0; i < param.length; i++) {
                        param[i] = (String) generalParam[i];
                    }

                    AnonymousAccess.main(param);
                } else {
                    System.err.println(HELP);
                    System.err.println(HELP_CHECK_OUT);
                }

                break;

            case CLEAN:
                FileUtils.cleanSo6TmpFiles();

                if (wsPath != null) {
                    WsConnection wsc = new WsConnection(wsPath);
                    wsc.removedOldMergedOp();
                }

                break;
            }
        }
    }
}
