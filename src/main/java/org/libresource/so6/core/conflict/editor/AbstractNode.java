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
package org.libresource.so6.core.conflict.editor;

import org.libresource.so6.core.tf.TextFileFunctions;

import java.awt.Color;

import java.io.Writer;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;


/**
 * @author smack
 */
public abstract class AbstractNode implements TreeNode {
    protected Vector children;
    protected AbstractNode parent;
    protected boolean enabled;
    private String origine;
    protected ArrayList lines;

    public AbstractNode() {
        this.children = new Vector();
        this.enabled = true;
        lines = new ArrayList();
    }

    public void appendLine(String line) {
        lines.add(line);
    }

    public Enumeration children() {
        return children.elements();
    }

    public TreeNode getChildAt(int childIndex) {
        return (TreeNode) children.get(childIndex);
    }

    public int getChildCount() {
        return children.size();
    }

    public int getIndex(TreeNode node) {
        return children.indexOf(node);
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(AbstractNode parent) {
        this.parent = parent;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public abstract boolean getAllowsChildren();

    public Color getColor() {
        if (isAdded() || isRemoved()) {
            if (isAdded()) {
                return new Color(200, 255, 200);
            } else {
                return new Color(255, 200, 200);
            }
        }

        if (this instanceof TextNode && (getParent() == null)) {
            return Color.white;
        }

        return new Color(200, 200, 255);
    }

    public boolean isAdded() {
        if (getParent() != null) {
            if (getParent() instanceof ConflictNode) {
                ConflictNode node = (ConflictNode) getParent();
                int childPos = node.getIndex(this);

                switch (node.getConflictType()) {
                case TextFileFunctions.ADD_ADD:
                    return true;

                case TextFileFunctions.ADD_REMOVE:
                    return childPos == 0;

                case TextFileFunctions.REMOVE_ADD:
                    return childPos == 1;
                }
            }
        }

        return false;
    }

    public boolean isRemoved() {
        if (getParent() != null) {
            if (getParent() instanceof ConflictNode) {
                ConflictNode node = (ConflictNode) getParent();
                int childPos = node.getIndex(this);

                switch (node.getConflictType()) {
                case TextFileFunctions.ADD_ADD:
                    return false;

                case TextFileFunctions.ADD_REMOVE:
                    return childPos == 1;

                case TextFileFunctions.REMOVE_ADD:
                    return childPos == 0;
                }
            }
        }

        return false;
    }

    public String getName() {
        return getClass().getName();
    }

    public void setChild(int childPos, AbstractNode node) {
        children.set(childPos, node);
        node.setParent(this);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public abstract void toDocument(Writer writer, int depth)
        throws Exception;

    protected void writePadding(Writer writer, int depth)
        throws Exception {
        for (int i = 0; i < depth; i++) {
            writer.write(TextFileFunctions.CONFLICT_BLOC_PADDING);
        }
    }

    public void setOrigine(String wsName) {
        origine = wsName;
    }

    public String getOrigine() {
        return origine;
    }

    public abstract void compute();

    public void updateNode(AbstractNode newNode) {
        ((AbstractNode) getParent()).setChild(getParent().getIndex(this), newNode);
    }
}
