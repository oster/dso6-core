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
package org.libresource.so6.core;

import org.libresource.so6.core.engine.log.monitoring.TreeContext;
import org.libresource.so6.core.engine.util.XmlUtil;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author smack
 */
public class StateMonitoring extends Observable {
    private static StateMonitoring sm = null;
    private TreeContext context;
    private String globalComment;
    private String localComment;
    private int criticalPart;

    private StateMonitoring() {
        context = new TreeContext();
        globalComment = "";
        localComment = "";
        criticalPart = 0;
    }

    public static StateMonitoring getInstance() {
        if (sm == null) {
            sm = new StateMonitoring();
        }

        return sm;
    }

    public TreeContext getContext() {
        return context;
    }

    public String getCurrentGlobalMessage() {
        return globalComment;
    }

    public String getCurrentLocalMessage() {
        return localComment;
    }

    public Logger getXMLMonitoringLogger() {
        return Logger.getLogger("so6.monitoring.xml");
    }

    public void setXMLMonitoringStartCriticalPart() {
        criticalPart++;
        setChanged();
        notifyObservers();
        StateMonitoring.getInstance().getXMLMonitoringLogger().log(Level.SEVERE, "<CRITICAL_PART/>");
    }

    public void setXMLMonitoringStartAction(String action) {
        criticalPart = 0;
        setChanged();
        StateMonitoring.getInstance().getXMLMonitoringLogger().log(Level.SEVERE, "<" + action + ">");
        notifyObservers();
    }

    public void setXMLMonitoringEndAction(String action) {
        StateMonitoring.getInstance().getXMLMonitoringLogger().log(Level.SEVERE, "</" + action + ">");
        setChanged();
        notifyObservers();
    }

    public void setXMLMonitoringStartSubCall(int nbCall, String message) {
        context.startPart(nbCall);

        if ((message != null) && (message.length() > 0)) {
            getXMLMonitoringLogger().log(Level.SEVERE, "<SUBCALL nbCall=\"" + nbCall + "\" comment=\"" + XmlUtil.replaceInvalideXmlChar(message) + "\">");
            globalComment = message;
        } else {
            getXMLMonitoringLogger().log(Level.SEVERE, "<SUBCALL nbCall=\"" + nbCall + "\">");
        }

        setChanged();
        notifyObservers();
    }

    public void setXMLMonitoringEndSubCall() {
        context.endPart();
        getXMLMonitoringLogger().log(Level.SEVERE, "</SUBCALL>");
    }

    public void setXMLMonitoringComment(String message) {
        setXMLMonitoringComment(true, message);
    }

    public void setXMLMonitoringComment(boolean global, String message) {
        if (global) {
            globalComment = message;
        } else {
            localComment = message;
        }

        getXMLMonitoringLogger().log(Level.SEVERE, "<COMMENT global=\"" + global + "\">" + XmlUtil.replaceInvalideXmlChar(message) + "</COMMENT>");
        setChanged();
        notifyObservers();
    }

    public void setXMLMonitoringState(long from, long to, long current, String message) {
        context.setLocalState(from, to, current);

        if ((message != null) && (message.length() > 0)) {
            getXMLMonitoringLogger().log(Level.SEVERE,
                "<STATE from=\"" + from + "\" to=\"" + to + "\" current=\"" + current + "\" comment=\"" + XmlUtil.replaceInvalideXmlChar(message) + "\"/>");
            localComment = message;
        } else {
            getXMLMonitoringLogger().log(Level.SEVERE, "<STATE from=\"" + from + "\" to=\"" + to + "\" current=\"" + current + "\"/>");
        }

        setChanged();
        notifyObservers();
    }

    public int getCurrentCriticalPartNumber() {
        return criticalPart;
    }
}
