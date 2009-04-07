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
package org.libresource.so6.core.exec.test;

import org.libresource.so6.core.engine.util.FileUtils;
import org.libresource.so6.core.test.util.TestUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;


public class Base64 {
    public static void main(String[] args) throws Exception {
        String dir = FileUtils.createTmpDir().getPath();
        TestUtil.createBinaryFile(dir + "/srcFile.bin", 1024 * 1000000);

        FileInputStream fis = new FileInputStream(dir + "/srcFile.bin");
        FileOutputStream fos = new FileOutputStream(dir + "/encode.txt");
        OutputStreamWriter writer = new OutputStreamWriter(fos);
        byte[] buffer = new byte[1024 * 3];
        int length;

        while ((length = fis.read(buffer)) != -1) {
            writer.write(org.libresource.so6.core.engine.util.Base64.encodeBytes(buffer, 0, length));
        }

        writer.close();
    }
}
