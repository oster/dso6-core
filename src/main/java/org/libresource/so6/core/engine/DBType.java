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
package org.libresource.so6.core.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.libresource.so6.core.Workspace;
import org.libresource.so6.core.engine.util.FileUtils;


/**
 * @author molli
 */
public class DBType {
    public static boolean VALIDATE_PATH = false; // Pas si simple, il faut
                                                 // toucher au transformees...
    public static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_DIR = 0;
    public static final int TYPE_FILE = 1;
    public static final int TYPE_FILE_BIN = 2;
    public static final int TYPE_FILE_TXT = 3;
    public static final int TYPE_FILE_XML = 4;

    //private Hashtable ht = new Hashtable();
    private Properties props = new Properties();
    private String filePath;
    private List<String> binExt = new ArrayList<String>();

    public DBType(String filePath, String binExt) {
        this.filePath = filePath;
        updateBinExt(binExt);
        props.put("", new Integer(TYPE_DIR).toString()); //;-D
        load();
    }

    public void updateBinExt(String binExt) {
        this.binExt.clear();

        StringTokenizer t = new StringTokenizer(binExt, " \t");

        while (t.hasMoreTokens()) {
            this.binExt.add(t.nextToken());
        }
    }

    public void load() {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            props.load(fis);
            fis.close();
        } catch (FileNotFoundException f) {
            // Ok, no problem...
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            props.store(fos, "Types of files: unknown=-1 dir=0 file=1 binaryFile=2 txtFile=3 xmlFile=4");
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Properties getDBTypeData() {
        return (Properties) props.clone();
    }

    public String getPath() {
        return filePath;
    }

    public int getNbEntry() {
        return props.size();
    }

    public int computeType(File f, boolean allowXmlDetection)
        throws Exception {
        if (!(f.exists())) {
            throw new Exception("File not exists:" + f.getPath());
        }

        if (f.isDirectory()) {
            return TYPE_DIR;
        }

        if (f.isFile()) {
            if (f.length() == 0) {
                return TYPE_FILE_BIN;
            }

            if (isBinary(f.getName()) || !(FileUtils.isTextFile(f.getAbsolutePath()) || (allowXmlDetection && FileUtils.isXmlFile(f.getAbsolutePath())))) {
                return TYPE_FILE_BIN;
            } else {
                if (allowXmlDetection && FileUtils.isXmlFile(f.getAbsolutePath())) {
                    return TYPE_FILE_XML;
                } else {
                    return TYPE_FILE_TXT;
                }
            }
        } else {
            throw new Exception("unkown file type (dir or file) of file :" + f.getPath());
        }
    }

    // Check between DIR <-> FILE
    public boolean typeHasChanged(String path, File f)
        throws Exception {
        if (!(f.exists())) {
            throw new Exception("File not exists:" + f.getPath());
        }

        if (getType(path) == -1) {
            return true; // File not found in db type -> same as type has
                         // changed
        }

        if (f.isDirectory()) {
            return getType(path) > TYPE_DIR;
        }

        if (f.isFile()) {
            return getType(path) < TYPE_FILE;
        }

        throw new Exception("File is not a valide File");
    }

    public void add(String path_, int type) {
        String path = path_.replaceAll("\\\\", "/");
        String validatePath = path;

        if (VALIDATE_PATH) {
            validatePath = path.toLowerCase();

            if (props.get(validatePath) != null) {
                throw new RuntimeException("Try to synchronize two file with the same name. " + path + " <-> " + validatePath);
            }

            props.put(validatePath, new Integer(type).toString());
        } else {
            props.put(path, new Integer(type).toString());
        }
    }

    public void remove(String path) {
        if (VALIDATE_PATH) {
            props.remove(path.toLowerCase());
        } else {
            props.remove(path);
        }
    }

    public int getType(String path) {
        Object o = props.get(path);

        if (VALIDATE_PATH) {
            o = props.get(path.toLowerCase());
        }

        if (o == null) {
            return -1;
        } else {
            return Integer.parseInt((String) o);
        }
    }

    private boolean isBinary(String path) {
        if (path.lastIndexOf('.') == -1) {
            return false;
        }

        String ext = path.substring(path.lastIndexOf('.') + 1);

        return binExt.contains(ext);
    }

    public void updateFromDBType(DBType dbType) {
        props = dbType.getDBTypeData();
        save();
    }

    public void updateFromWalk(String basePath, boolean allowXmlDetection)
        throws Exception {
        walk(new File(basePath), new File(basePath), allowXmlDetection);
        save();
    }

    private void walk(File baseDir, File currentDir, boolean allowXmlDetection)
        throws Exception {
        String currentFile = currentDir.getPath();

        if (!baseDir.getPath().equals(currentDir.getPath())) {
            currentFile = currentDir.getPath().substring(baseDir.getPath().length() + 1);

            if (currentFile.endsWith(Workspace.SO6PREFIX)) {
                return;
            }
        } else {
            currentFile = "";
        }

        currentFile = currentFile.replaceAll("\\\\", "/");

        if (props.getProperty(currentFile) == null) {
            add(currentFile, computeType(currentDir, allowXmlDetection));
        }

        if (currentDir.isDirectory()) {
            File[] entries = currentDir.listFiles();

            for (int i = 0; i < entries.length; i++) {
                walk(baseDir, entries[i], allowXmlDetection);
            }
        }
    }
}
