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
package org.libresource.so6.core.engine.log;

import org.libresource.so6.core.engine.log.monitoring.TreeContext;

import java.util.Observable;
import java.util.Observer;


/**
 * @author smack
 */
public class StandardOutputWriter implements MessageWriter, Observer {
    private TreeContext context;
    private String previousMessage;
    private final String PROGRESS = "\\|/-";
    private int progress = 1;
    private int lastGlobal = 0;
    private int lastLocal = 0;
    private int global = 0;
    private int local = 0;

    public StandardOutputWriter(TreeContext context) {
        this.context = context;
        context.addObserver(this);
        previousMessage = "";
        lastGlobal = (int) context.getGlobalState();
        lastLocal = (int) context.getLocalState();
        new Thread() {
                public void run() {
                    try {
                        sleep(200);

                        if (lastLocal == -1) {
                            printMessage(false, previousMessage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
    }

    public void printMessage(boolean global, String message) {
        if (global) {
            if (previousMessage.length() > 0) {
                System.out.print("\r" + getProgressState());
            }

            System.out.println("\n" + message);
        } else {
            if (message.length() > 0) {
                // clean output
                System.out.print("\r");

                int nbChar = previousMessage.length();

                for (int i = 0; i < (nbChar + getProgressState().length()); i++) {
                    System.out.print(" ");
                }

                //
                previousMessage = message;
                System.out.print("\r" + getProgressState() + previousMessage);
            }
        }
    }

    private String getProgressState() {
        StringBuffer buffer = new StringBuffer();
        global = (int) context.getGlobalState();
        local = (int) context.getLocalState();
        buffer.append(" ");
        buffer.append(global);
        buffer.append(" % | ");

        for (int i = 0; buffer.length() < 9; i++) {
            buffer.insert(0, " ");
        }

        buffer.insert(0, "[");

        int pos = buffer.length();

        if (local == -1) {
            buffer.append(PROGRESS.charAt(progress++ % 4));
        } else {
            buffer.append(local);
        }

        buffer.append(" % ");

        for (int i = 0; buffer.length() < 16; i++) {
            buffer.insert(pos, " ");
        }

        buffer.append("] : ");

        //
        lastGlobal = global;
        lastLocal = local;

        return buffer.toString();
    }

    public void update(Observable o, Object arg) {
        if ((lastGlobal != global) || (lastLocal != local)) {
            printMessage(false, previousMessage);
        }
    }
}
