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

import org.libresource.so6.core.StateMonitoring;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author smack
 */
public class MonitoredInputStream extends InputStream {
    private InputStream streamToMonitor;
    private long max;
    private long currentState = 1;
    private long mark = 0;
    private String finished = "Waiting for reply...";
    private String reading = "Sending";

    public MonitoredInputStream(InputStream streamToMonitor, long dataSize) {
        this.streamToMonitor = streamToMonitor;
        this.max = dataSize;
    }

    public void setComment(String finished, String reading) {
        this.reading = reading;
        this.finished = finished;
    }

    // inputstream
    public int read() throws IOException {
        currentState++;

        return streamToMonitor.read();
    }

    public int available() throws IOException {
        return streamToMonitor.available();
    }

    public void close() throws IOException {
        streamToMonitor.close();
    }

    public synchronized void mark(int readlimit) {
        mark = readlimit;
        streamToMonitor.mark(readlimit);
    }

    public boolean markSupported() {
        return streamToMonitor.markSupported();
    }

    public int read(byte[] b, int off, int len) throws IOException {
        currentState += len;
        setXMLLocalInfo();

        return streamToMonitor.read(b, off, len);
    }

    public int read(byte[] b) throws IOException {
        int length = streamToMonitor.read(b);
        currentState += length;
        setXMLLocalInfo();

        return length;
    }

    public synchronized void reset() throws IOException {
        currentState = mark;
        setXMLLocalInfo();
        streamToMonitor.reset();
    }

    public long skip(long n) throws IOException {
        return streamToMonitor.skip(n);
    }

    private void setXMLLocalInfo() {
        if (currentState >= max) {
            StateMonitoring.getInstance().setXMLMonitoringState(0, 1, 1, finished);
        } else {
            StateMonitoring.getInstance().setXMLMonitoringState(0, max, currentState, reading + " " + currentState + " / " + max);
        }
    }
}
