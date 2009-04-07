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

import fr.loria.ecoo.so6.xml.xydiff.XyDiff;

import jlibdiff.Diff;

import org.libresource.so6.core.Workspace;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.zip.ZipOutputStream;


public class FileUtils {
    /** Base tmp file directory */
    public final static String BASE_TMP_DIR = "so6.tmp";
    private static int number = 1;

    /**
     * Use for test
     */
    public static void createBinFile(String path, String name, String msg)
        throws Exception {
        editBinFile(path + File.separator + name, msg);
    }

    /**
     * Use for test
     */
    public static void editBinFile(String path, String msg)
        throws Exception {
        if (FileUtils.fileExists(path)) {
            FileUtils.remove(path);
        }

        FileOutputStream ostream = new FileOutputStream(path);

        for (int i = 127; i < 255; i++)
            ostream.write(i);

        ostream.write(msg.getBytes());
        ostream.flush();
        ostream.close();
    }

    /**
     * Use for test
     */
    public static void createTxtFile(String path, String name, String msg)
        throws Exception {
        editTxtFile(path + File.separator + name, msg);
    }

    public static boolean isLocked(File f) {
        return !f.renameTo(f);
    }

    public static boolean isLocked(String f) {
        return isLocked(new File(f));
    }

    /**
     * Use for test
     */
    public static void createXmlFile(String root, String path)
        throws Exception {
        editTxtFile(path, "<?xml version='1.0'?>\n<" + root + "/>");
    }

    /**
     * Use for test
     */
    public static void createXmlFile(String root, String path, String encoding)
        throws Exception {
        String xml = "<?xml version='1.0' encoding='" + encoding + "'?>\n<" + root + "/>";

        if (FileUtils.fileExists(path)) {
            FileUtils.remove(path);
        }

        FileOutputStream fos = new FileOutputStream(path);
        OutputStreamWriter ow = new OutputStreamWriter(fos, encoding);
        ow.write(xml);
        ow.close();
        fos.close();
    }

    /**
     * Use for test
     */
    public static void editTxtFile(String path, String msg)
        throws Exception {
        if (FileUtils.fileExists(path)) {
            FileUtils.remove(path);
        }

        FileOutputStream ostream = new FileOutputStream(path);
        PrintStream p = new PrintStream(ostream);
        p.print(msg);
        p.flush();
        p.close();
    }

    public static void editTxtFile3(String path, String msg)
        throws Exception {
        if (FileUtils.fileExists(path)) {
            FileUtils.remove(path);
        }

        FileOutputStream ostream = new FileOutputStream(path);
        PrintStream p = new PrintStream(ostream);
        p.print(msg.getBytes("ISO8859_1"));
        p.flush();
        p.close();
    }

    public static void editTxtFile2(String path, String msg)
        throws Exception {
        FileWriter ostream = new FileWriter(path, true);
        ostream.write(msg);
        ostream.flush();
        ostream.close();
    }

    /**
     * Use create a directory and throw an exception if the creation failed or
     * if the directory already exist
     */
    public static void createDir(String path) throws IOException {
        File d = new File(path);

        if (!(d.mkdirs())) {
            throw new IOException("cannot create dir:" + path);
        }
    }

    /**
     * Create a directory if needed and throw an exception if the creation
     * failed
     */
    public static void createDirIfNotExist(String path)
        throws IOException {
        File f = new File(path);

        if (f.exists() && (f.isDirectory())) {
            return;
        }

        if (f.exists() && (!f.isDirectory())) {
            throw new RuntimeException("Cannot create :" + path + " already exists as a file");
        }

        createDir(path);
    }

    /**
     * Create a tmp directory in the BASE_TMP_DIR
     */
    public static File createTmpDir() throws Exception {
        File f = new File(getBaseTmpPath() + File.separator + "SO6_" + System.currentTimeMillis());

        while (f.exists()) {
            f = new File(f.getPath() + "_" + number++);
        }

        if (!f.mkdirs()) {
            throw new Exception("cannot create tmpdir:" + f.getPath());
        }

        return f;
    }

    /**
     * Remove a path recursivley
     *
     * @param path
     * @throws Exception
     */
    public static void remove(String path, FilenameFilter ff, boolean deleteCurrentDir)
        throws Exception {
        File f = new File(path);

        if (f.exists()) {
            delrec(f, ff, deleteCurrentDir);
        }
    }

    public static void remove(String path) throws Exception {
        File f = new File(path);

        if (f.exists()) {
            delrec(f, null, true);
        }
    }

    private static void delrec(File f, FilenameFilter ff, boolean deleteCurrentDir)
        throws java.io.IOException {
        if (f.isDirectory()) {
            File[] fs = (ff == null) ? f.listFiles() : f.listFiles(ff);

            for (int i = 0; i < fs.length; i++) {
                delrec(fs[i], ff, true);
            }
        }

        if (deleteCurrentDir) {
            if (!(f.delete())) {
                throw new java.io.IOException("cannot delete " + f.getPath());
            }
        }
    }

    /**
     * Compare texte files
     *
     * @param file1
     * @param file2
     * @return @throws
     *         Exception
     */
    public static boolean compareTxtFile(String file1, String file2)
        throws Exception {
        Diff d = new Diff();
        d.diffFile(file1, file2);

        //return d.areEquals();
        return (d.getHunkCount() == 0);
    }

    /**
     * Compare binary files
     *
     * @param file1
     * @param file2
     * @return
     */
    public static boolean compareBinFile(String file1, String file2) {
        return FileUtils.compareBinFile(new File(file1), new File(file2));
    }

    /**
     * Compare binary files
     *
     * @param file1
     * @param file2
     * @return
     */
    public static boolean compareBinFile(File file1, File file2) {
        if (file1.length() != file2.length()) {
            return false;
        }

        FileInputStream f1 = null;
        FileInputStream f2 = null;
        byte[] buffer1 = new byte[102400];
        byte[] buffer2 = new byte[102400];

        for (int i = 0; i < buffer1.length; i++) {
            buffer1[i] = 0;
            buffer2[i] = 0;
        }

        try {
            f1 = new FileInputStream(file1);
            f2 = new FileInputStream(file2);

            int length = f1.read(buffer1);
            f2.read(buffer2);

            while (length != -1) {
                if (!Arrays.equals(buffer1, buffer2)) {
                    f1.close();
                    f2.close();

                    return false;
                } else {
                    length = f1.read(buffer1);
                    f2.read(buffer2);
                }
            }

            f1.close();
            f2.close();
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        } finally {
            file1 = null;
            file2 = null;
        }

        return true;
    }

    /**
     * Compare the content of directory recursively with a binary file
     * comparaison
     *
     * @param dir1
     * @param dir2
     * @return @throws
     *         Exception
     */
    public static boolean compareDir(String dir1, String dir2)
        throws Exception {
        File d1 = new File(dir1);
        File d2 = new File(dir2);
        String[] ldir1 = d1.list();
        String[] ldir2 = d2.list();

        if ((ldir1 == null) || (ldir2 == null)) {
            throw new Exception("cannot get list for dir1 or dir2 " + dir1 + "," + dir2);
        }

        Arrays.sort(ldir1);
        Arrays.sort(ldir2);

        if (Arrays.equals(ldir1, ldir2)) {
            for (int i = 0; i < ldir1.length; i++) {
                File f1 = new File(d1.getAbsolutePath() + "/" + ldir1[i]);
                File f2 = new File(d2.getAbsolutePath() + "/" + ldir2[i]);

                if (f1.isDirectory() && f2.isDirectory()) {
                    if (!ldir1[i].equals(Workspace.SO6PREFIX)) {
                        if (!compareDir(f1.getAbsolutePath(), f2.getAbsolutePath())) {
                            return false;
                        }
                    }
                } else if (f1.isFile() && f2.isFile()) {
                    if (!compareBinFile(f1, f2)) {
                        return false;
                    }
                } else if ((f1.isDirectory() && f2.isFile()) || (f1.isFile() && f2.isDirectory())) {
                    return false;
                } else {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * List file in one String with a ";" as a file separator.
     *
     * @param dir
     * @return @throws
     *         Exception
     */
    public static String list(String dir) throws Exception {
        File d = new File(dir);
        String[] ldir = d.list();

        if (ldir == null) {
            throw new Exception("cannot get list for " + dir);
        }

        String buff = new String();

        for (int i = 0; i < ldir.length; i++) {
            buff = buff + ldir[i] + ";";
        }

        return buff;
    }

    /**
     * Check if path exist
     *
     * @param file
     * @return @throws
     *         Exception
     */
    public static boolean fileExists(String file) throws Exception {
        return (new File(file)).exists();
    }

    /**
     * Check if the specified path is a directory
     *
     * @param dir
     * @return @throws
     *         Exception
     */
    public static boolean isDirectory(String dir) throws Exception {
        return (new File(dir)).isDirectory();
    }

    /**
     * Check if the specified path is a file
     *
     * @param file
     * @return @throws
     *         Exception
     */
    public static boolean isFile(String file) throws Exception {
        return (new File(file)).isFile();
    }

    /**
     * Walk a directoy recursively and add the file path in the specified
     * vector.
     *
     * @param dir
     *            base path for the walk
     * @param result
     *            vector of the file path
     */
    public static void walk(String dir, Vector result) {
        File f = new File(dir);

        if (f.isFile() || f.isDirectory()) {
            result.add(f.getPath());
        }

        if (f.isDirectory()) {
            String[] entries = f.list();

            for (int i = 0; i < entries.length; i++) {
                walk(dir + File.separator + entries[i], result);
            }
        }
    }

    /**
     * Copy the content of the input file path to the output file path.
     *
     * @param input
     * @param output
     * @throws Exception
     */
    public static void copy(String input, String output)
        throws Exception {
        //System.out.println("copy: " + input +" -> "+ output);
        FileInputStream fis = new FileInputStream(input);
        FileOutputStream fos = new FileOutputStream(output);
        byte[] buffer = new byte[1024];
        int length;

        while ((length = fis.read(buffer)) != -1) {
            fos.write(buffer, 0, length);
        }

        fos.close();
        fis.close();
    }

    /**
     * Copy the content of the input file to the output file .
     *
     * @param input
     * @param output
     * @throws Exception
     */
    public static void copy(File src, File dst) throws Exception {
        if (src.isDirectory()) {
            createDirIfNotExist(dst.getPath());

            File[] files = src.listFiles();

            for (int i = 0; i < files.length; i++) {
                copy(files[i], new File(dst, files[i].getName()));
            }
        } else {
            copy(src.getPath(), dst.getPath());
        }
    }

    /**
     * Write the content of the file in the ZipOutputStream.
     *
     * @param fileName
     * @param out
     * @throws Exception
     */
    public static void writeFileInZip(String fileName, ZipOutputStream out)
        throws Exception {
        FileInputStream fis = new FileInputStream(fileName);
        byte[] data = new byte[1024];
        int byteCount;

        while ((byteCount = fis.read(data, 0, 1024)) > -1) {
            out.write(data, 0, byteCount);
        }

        out.flush();
    }

    /**
     * Convert path in order to replace the dependant file separator to the
     * generic one "/"
     *
     * @param path
     * @return
     */
    public static String convertPath(String path) {
        return path.replaceAll("\\", "/");
    }

    /**
     * Check the first Mega byte of the file in order to determine if it's a
     * text file or not...
     *
     * @param filePath
     * @return @throws
     *         Exception
     */
    public static boolean isTextFile(String filePath) throws Exception {
        final int bufferSize = 1024;
        File f = new File(filePath);

        if (f.exists() && f.isFile()) {
            FileInputStream fis = new FileInputStream(f);
            byte[] data = new byte[bufferSize];
            int byteCount = fis.read(data, 0, bufferSize);
            fis.close();

            int non_text = 0;

            for (int i = 0; i < byteCount; i++) {
                byte a = data[i];

                if ((a < 8) || ((a > 13) && (a < 32)) || (a > 126)) {
                    non_text++;
                }
            }

            // Check pourcent
            // System.out.println(non_text + " / " + byteCount + " -> " +
            // ((non_text * 100) / byteCount));
            return (((non_text * 100) / byteCount) <= 20);
        }

        throw new Exception("Error: it's not a file");
    }

    /**
     * Check if it's an XML file or not.
     *
     * @param filePath
     * @return @throws
     *         Exception
     */
    public static boolean isXmlFile(String filePath) throws Exception {
        File f = new File(filePath);

        if (!f.exists()) {
            throw new Exception("Error the file " + f.getPath() + " does not exist");
        }

        if (f.length() == 0) {
            return false;
        }

        // check inside
        LineNumberReader input = null;

        try {
            input = new LineNumberReader(new FileReader(f));

            String line = input.readLine();

            if (line != null) {
                // (Change this in order to make a regexp instead... )
                return (line.startsWith("<?xml ")) || (line.startsWith("<office:document ")); // <?xml

                // version="1.0"?>
            }
        } finally {
            if (input != null) {
                input.close();
            }
        }

        return false;
    }

    /**
     * Return the base tmp path
     *
     * @return
     */
    public static String getBaseTmpPath() {
        return System.getProperty("java.io.tmpdir") + File.separator + BASE_TMP_DIR + "_" + System.getProperty("user.name");
    }

    /**
     * Clean all the files and directory stored in the base tmp path
     *
     * @throws Exception
     */
    public static void cleanSo6TmpFiles() throws Exception {
        remove(getBaseTmpPath());
    }

    public static boolean compareXmlFile(String file1, String file2)
        throws Exception {
        XyDiff d = new XyDiff(file1, file2);
        Collection cmds = d.diff().getXMLCommand();

        for (Iterator i = cmds.iterator(); i.hasNext();) {
            System.out.println(i.next());
        }

        return cmds.size() == 0;
    }
}
