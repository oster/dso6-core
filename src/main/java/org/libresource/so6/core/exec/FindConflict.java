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

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.engine.DBType;
import org.libresource.so6.core.tf.TextFileFunctions;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;


/**
 * The <code>FindConflict</code> class is used to search into a Workspace if
 * some files have conflict inside or if two version of a file are in conflict.
 * <p>
 * The result is presented in a list of file on the standard output.
 * <ul>
 * <li>When it's written conflict "on" file then it means thats a version
 * conflict.</li>
 * <li>When it's written conflict "in" file then it means there's conflicts
 * inside the text file.</li>
 * </ul>
 *
 * @author Smack
 * @version 1.0, 26/05/04
 * @see org.libresource.so6.core.exec.Main
 * @since JDK1.4
 */
public class FindConflict {
    /**
     * The <code>FindConflict</code> class is used to search into a Workspace
     * if some files have conflict inside or if two version of a file are in
     * conflict.
     * <p>
     * The result is presented in a list of file on the standard output.
     * <ul>
     * <li>When it's written conflict "on" file then it means thats a version
     * conflict.</li>
     * <li>When it's written conflict "in" file then it means there's conflicts
     * inside the text file.</li>
     * </ul>
     *
     * @param args
     *            <ul>
     *            <li>The WsConnection property file path
     *            (.so6/1/so6.properties)</li>
     *            </ul>
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: wscPath");
            System.err.println(" (1) wscPath: path of the file " + WsConnection.SO6_WSC_FILE);
        } else {
            String wsPath = args[0];
            System.out.println("Start searching conflict in workspace : " + wsPath);

            Collection c = searchConflict(wsPath);

            for (Iterator i = c.iterator(); i.hasNext();) {
                System.out.println(i.next());
            }
        }
    }

    /**
     * Search into the workspace of a connection the set of conflicts.
     *
     * @param wscPath
     * @return The set of ConflictFile
     * @throws Exception
     */
    public static Collection searchConflict(String wscPath)
        throws Exception {
        ArrayList result = new ArrayList();
        WsConnection ws = new WsConnection(wscPath);
        DBType dbType = ws.getDBType();
        dbType.updateFromWalk(ws.getPath(), ws.getXmlAutoDetection());

        Properties props = dbType.getDBTypeData();
        File f;

        for (Enumeration e = props.keys(); e.hasMoreElements();) {
            String path = (String) e.nextElement();
            f = new File(ws.getPath(), path);

            if (!f.exists()) {
                continue;
            }

            int type = Integer.parseInt(props.getProperty(path));

            switch (type) {
            case DBType.TYPE_UNKNOWN:

                // do nothing
                break;

            case DBType.TYPE_DIR:
            case DBType.TYPE_FILE_XML:
            case DBType.TYPE_FILE:
            case DBType.TYPE_FILE_BIN:

                // check file name
                if (path.indexOf("#") != -1) {
                    result.add(new ConflictFile(path, false));
                }

                break;

            case DBType.TYPE_FILE_TXT:

                //	check file name
                if (path.indexOf("#") != -1) {
                    result.add(new ConflictFile(path, false));
                }

                // check inside
                if (isFileInConflict(ws.getPath(), path)) {
                    result.add(new ConflictFile(path, true));
                }

                break;
            }
        }

        return result;
    }

    /**
     * Check inside a file if it contains at least a bloc of conflict.
     *
     * @param basePath
     * @param path
     * @return @throws
     *         Exception
     */
    public static boolean isFileInConflict(String basePath, String path)
        throws Exception {
        File f = new File(basePath + File.separator + path);

        if (!f.exists()) {
            //throw new Exception("Error the file " + f.getPath() + " does not
            // exist");
            return false;
        }

        if (f.length() == 0) {
            return false;
        }

        // check inside
        LineNumberReader input = null;

        try {
            input = new LineNumberReader(new FileReader(f));

            String startConflict;
            String firstBlock;
            String secondBlock;

            while ((startConflict = input.readLine()) != null) {
                if (startConflict.startsWith(TextFileFunctions.CONFLICT_BLOC_START)) {
                    // Maybe a conflict -> check further
                    while ((firstBlock = input.readLine()) != null) {
                        if (firstBlock.startsWith(TextFileFunctions.CONFLICT_BLOC_PADDING)) {
                            // inside the first part
                        } else if (firstBlock.startsWith(TextFileFunctions.CONFLICT_BLOC_SPLIT)) {
                            // end of the fisrt part
                            while ((secondBlock = input.readLine()) != null) {
                                if (secondBlock.startsWith(TextFileFunctions.CONFLICT_BLOC_PADDING)) {
                                    // inside the second part
                                } else if (secondBlock.startsWith(TextFileFunctions.CONFLICT_BLOC_END)) {
                                    // End of the conflict
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        } else {
                            return false;
                        }
                    }
                }
            }
        } finally {
            if (input != null) {
                input.close();
            }
        }

        return false;
    }

    public static class ConflictFile {
        private String path;
        private boolean inside;

        public ConflictFile(String path, boolean conflictInside) {
            this.path = path;
            this.inside = conflictInside;
        }

        public boolean isInside() {
            return inside;
        }

        public String getPath() {
            return path;
        }

        public void setInside(boolean b) {
            inside = b;
        }

        public void setPath(String string) {
            path = string;
        }

        public String toString() {
            return "Conflict " + (inside ? "in" : "on") + " file " + path;
        }
    }
}
