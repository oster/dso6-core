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

import java.io.Writer;

import java.util.Iterator;


/**
 * @author smack
 */
public class ConflictNode extends AbstractNode {
    private int conflictType = -1;

    public ConflictNode() {
        super();
        children.add(new TextNode());
        children.add(new TextNode());
    }

    public int getConflictType() {
        return conflictType;
    }

    public void setConflictType(int conflictType) {
        this.conflictType = conflictType;
    }

    public boolean getAllowsChildren() {
        return true;
    }

    public void toDocument(Writer writer, int depth) throws Exception {
        // init
        AbstractNode child1 = (AbstractNode) children.get(0);
        AbstractNode child2 = (AbstractNode) children.get(1);

        // Start the conflict bloc
        if (enabled) {
            writePadding(writer, depth);
            writer.write(TextFileFunctions.CONFLICT_BLOC_START);

            switch (conflictType) {
            case TextFileFunctions.ADD_ADD:
                writer.write(TextFileFunctions.CONFLICT_TYPE_ADD_ADD);

                break;

            case TextFileFunctions.ADD_REMOVE:
                writer.write(TextFileFunctions.CONFLICT_TYPE_ADD_REMOVE);

                break;

            case TextFileFunctions.REMOVE_ADD:
                writer.write(TextFileFunctions.CONFLICT_TYPE_REMOVE_ADD);

                break;
            }

            writer.write("\n");
        }

        // write first child
        child1.toDocument(writer, enabled ? (depth + 1) : depth);

        if (child1.isEnabled()) {
            writer.write("\n");
        }

        // write split child
        if (enabled) {
            writer.write(TextFileFunctions.CONFLICT_BLOC_SPLIT + child1.getOrigine() + TextFileFunctions.CONFLICT_BLOC_ORIGINE_SEPARATOR + child2.getOrigine());
            writer.write("\n");
        }

        // write second child
        child2.toDocument(writer, enabled ? (depth + 1) : depth);

        if (enabled) {
            if (child2.isEnabled()) {
                writer.write("\n");
            }

            //			End the conflict bloc
            writePadding(writer, depth);
            writer.write(TextFileFunctions.CONFLICT_BLOC_END);
        }

        writer.flush();
    }

    public void compute() {
        if (lines != null) {
            // check if conflict or Txt node
            if (!((String) lines.get(0)).startsWith(TextFileFunctions.CONFLICT_BLOC_START)) {
                TextNode node = new TextNode();

                for (Iterator i = lines.iterator(); i.hasNext();) {
                    node.appendLine((String) i.next());
                }

                node.setOrigine(getOrigine());
                updateNode(node);
            } else {
                // Compute children
                String line;
                AbstractNode currentNode = null;

                for (Iterator i = lines.iterator(); i.hasNext();) {
                    line = (String) i.next();

                    if (line.startsWith(TextFileFunctions.CONFLICT_BLOC_START)) {
                        setConflictType(TextFileFunctions.getConflictType(line));
                        currentNode = new ConflictNode();
                        setChild(0, currentNode);
                    } else if (line.startsWith(TextFileFunctions.CONFLICT_BLOC_END)) {
                        // end op the conflict
                        ((AbstractNode) getChildAt(0)).compute();
                        ((AbstractNode) getChildAt(1)).compute();
                    } else if (line.startsWith(TextFileFunctions.CONFLICT_BLOC_SPLIT)) {
                        currentNode = new ConflictNode();
                        setChild(1, currentNode);

                        String[] origine = TextFileFunctions.getOrigineBlocNames(line);
                        ((AbstractNode) getChildAt(0)).setOrigine(origine[0]);
                        ((AbstractNode) getChildAt(1)).setOrigine(origine[1]);
                    } else {
                        currentNode.appendLine(line.substring(1));
                    }

                    //
                }
            }
        }

        lines = null;
    }

    public String toString() {
        switch (getConflictType()) {
        case TextFileFunctions.ADD_ADD:
            return "Conflict Add/Add";

        case TextFileFunctions.ADD_REMOVE:
            return "Conflict Add/Remove";

        case TextFileFunctions.REMOVE_ADD:
            return "Conflict Remove/Add";
        }

        return "?";
    }
}
