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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.io.Writer;

import java.util.Iterator;


/**
 * @author smack
 */
public class DocumentNode extends AbstractNode {
    public DocumentNode() {
        super();
    }

    public boolean getAllowsChildren() {
        return true;
    }

    public Color getColor() {
        return new Color(200, 200, 200);
    }

    public String getName() {
        return "Document";
    }

    public void toDocument(Writer writer, int depth) throws Exception {
        for (Iterator i = children.iterator(); i.hasNext();) {
            AbstractNode node = (AbstractNode) i.next();
            node.toDocument(writer, depth);
        }
    }

    public void load(String srcFile) throws Exception {
        File f = new File(srcFile);

        if (!f.exists()) {
            throw new Exception("Error the file " + f.getPath() + " does not exist");
        }

        if (f.length() == 0) {
            return;
        }

        // check inside
        LineNumberReader input = null;

        try {
            input = new LineNumberReader(new FileReader(f));

            String line;
            AbstractNode currentNode = new TextNode();
            boolean needToAppend = false;

            while ((line = input.readLine()) != null) {
                if (line.startsWith(TextFileFunctions.CONFLICT_BLOC_START)) {
                    if (needToAppend) {
                        children.add(currentNode);
                    }

                    currentNode = new ConflictNode();
                    currentNode.appendLine(line);
                    needToAppend = false;
                } else if (line.startsWith(TextFileFunctions.CONFLICT_BLOC_END)) {
                    currentNode.appendLine(line);

                    if (needToAppend) {
                        children.add(currentNode);
                    }

                    currentNode = new TextNode();
                    needToAppend = false;
                } else {
                    currentNode.appendLine(line);
                    needToAppend = true;
                }
            }

            if (needToAppend) {
                children.add(currentNode);
            }
        } finally {
            if (input != null) {
                input.close();
            }
        }

        compute();
    }

    public void save(String destFile) throws Exception {
        FileWriter writer = new FileWriter(destFile);
        toDocument(writer, 0);
        writer.close();
    }

    public void compute() {
        // Broadcast the child computation
        for (Iterator i = children.iterator(); i.hasNext();) {
            ((AbstractNode) i.next()).compute();
        }
    }

    public String toString() {
        return "Document";
    }
}
