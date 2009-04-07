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

import java.io.IOException;
import java.io.OutputStream;


/**
 * @author smack
 */
public class MonitoredOutputStream extends OutputStream {
    private OutputStream streamToMonitor;
    private long max;
    private long currentState = 0;
    private long mark = 0;

    public MonitoredOutputStream(OutputStream streamToMonitor, long dataSize) {
        this.streamToMonitor = streamToMonitor;
        this.max = dataSize;
    }

    // OutputStream
    public void write(int b) throws IOException {
        streamToMonitor.write(b);
        currentState++;
    }

    public void close() throws IOException {
        streamToMonitor.close();
    }

    public void flush() throws IOException {
        streamToMonitor.flush();
    }

    public void write(byte[] b, int off, int len) throws IOException {
        streamToMonitor.write(b, off, len);
        currentState += len;
    }

    public void write(byte[] b) throws IOException {
        streamToMonitor.write(b);
        currentState += b.length;
    }
}
