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
package org.libresource.so6.core.engine.log.monitoring;

import java.util.ArrayList;
import java.util.Observable;

import javax.swing.JProgressBar;


/**
 * @author smack
 */
public class TreeContext extends Observable {
    private double globalState = 0;
    private double localState = 0;
    private double lastGlobalState = 0;

    //
    private JProgressBar globalProgress;
    private JProgressBar localProgress;

    //
    private ArrayList treePartPathMax;
    private ArrayList treePartPathCurrent;

    public TreeContext() {
        treePartPathMax = new ArrayList();
        treePartPathCurrent = new ArrayList();
    }

    public void startPart(int nbPart) {
        treePartPathMax.add(new Integer(nbPart));
        treePartPathCurrent.add(new Integer(0));
        setLocalState(-1);
    }

    public void startPart() {
        startPart(1);
    }

    public void endPart() {
        treePartPathMax.remove(treePartPathMax.size() - 1);
        treePartPathCurrent.remove(treePartPathCurrent.size() - 1);

        if (treePartPathCurrent.size() > 0) {
            Integer value = ((Integer) treePartPathCurrent.remove(treePartPathCurrent.size() - 1));
            value = new Integer(value.intValue() + 1);
            treePartPathCurrent.add(value);

            //
            setGlobalState(computeGlobalState());
            lastGlobalState = globalState;
        } else {
            setGlobalState(100);
            setLocalState(100);
        }
    }

    public void setLocalState(double from, double to, double current) {
        double pourcent = (current - from + 1) / (to - from + 1);
        double ref = 100;

        // ref
        int nbElements = treePartPathMax.size();

        for (int i = 0; i < nbElements; i++) {
            int valueMax = ((Integer) treePartPathMax.get(i)).intValue();
            int valueCurrent = ((Integer) treePartPathCurrent.get(i)).intValue();
            ref /= valueMax;
        }

        //System.out.println("ref= " + ref);
        //
        setGlobalState(lastGlobalState + (ref * pourcent));
        setLocalState(100 * pourcent);
    }

    private double computeGlobalState() {
        double result = 0;
        double ref = 100;
        int nbElements = treePartPathMax.size();

        for (int i = 0; i < nbElements; i++) {
            int valueMax = ((Integer) treePartPathMax.get(i)).intValue();
            int valueCurrent = ((Integer) treePartPathCurrent.get(i)).intValue();
            ref /= valueMax;
            result += (ref * valueCurrent);
        }

        return result;
    }

    //
    public double getGlobalState() {
        return globalState;
    }

    public double getLocalState() {
        return localState;
    }

    public String toString() {
        return "Global: " + globalState + " / Local: " + localState;
    }

    //
    private void setGlobalState(double value) {
        globalState = value;

        if (globalProgress != null) {
            globalProgress.setValue((int) (10 * globalState));
        }

        //
        setChanged();
        notifyObservers();
    }

    private void setLocalState(double value) {
        localState = value;

        if (localProgress != null) {
            if (value == -1) {
                localProgress.setIndeterminate(true);
            } else {
                localProgress.setIndeterminate(false);
                localProgress.setValue((int) (10 * localState));
            }
        }

        //
        setChanged();
        notifyObservers();
    }

    //
    public JProgressBar getGlobalProgressBar() {
        if (globalProgress == null) {
            globalProgress = new JProgressBar(0, 1000);
            setGlobalState(globalState);
        }

        return globalProgress;
    }

    public JProgressBar getLocalProgressBar() {
        if (localProgress == null) {
            localProgress = new JProgressBar(0, 1000);
            setLocalState(localState);
        }

        return localProgress;
    }
}
