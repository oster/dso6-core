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


/**
 * @author smack
 */
public class UniqueId {
    private static final String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static String convert(long value) {
        StringBuffer buffer = new StringBuffer();
        long currentValue = value;

        do {
            buffer.append(base.charAt((int) (currentValue % base.length())));
        } while ((currentValue = currentValue / ((long) base.length())) != 0);

        return buffer.toString();
    }

    /**
     * Return a String that should represent an unique identifier.
     *
     * @return
     */
    public static String getUniqueId() {
        StringBuffer id = new StringBuffer();
        id.append(convert(System.currentTimeMillis()));
        id.append("_");
        id.append(convert(getNbOpInMs()));

        for (int i = 0; i < 4; i++) {
            id.append("_");
            id.append(convert((long) (Math.random() * Long.MAX_VALUE)));
        }

        return id.toString();
    }

    private static long getNbOpInMs() {
        long time = System.currentTimeMillis();
        long nb = 0;

        while (time == System.currentTimeMillis()) {
            nb++;
        }

        return nb;
    }
}
