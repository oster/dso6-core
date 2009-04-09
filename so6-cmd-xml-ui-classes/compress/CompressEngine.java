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
package org.libresource.so6.core.compress;

import org.libresource.so6.core.command.Command;
import org.libresource.so6.core.command.EmptyOp;
import org.libresource.so6.core.command.NeutralCommand;
import org.libresource.so6.core.command.UpdateFile;
import org.libresource.so6.core.command.fs.AddBinaryFile;
import org.libresource.so6.core.command.fs.AddDir;
import org.libresource.so6.core.command.fs.AddFile;
import org.libresource.so6.core.command.fs.Remove;
import org.libresource.so6.core.command.fs.Rename;
import org.libresource.so6.core.command.fs.UpdateBinaryFile;
import org.libresource.so6.core.command.text.AddBlock;
import org.libresource.so6.core.command.text.AddTxtFile;
import org.libresource.so6.core.command.text.DelBlock;
import org.libresource.so6.core.command.text.UpdateTextFile;
import org.libresource.so6.core.command.xml.AddXmlFile;
import org.libresource.so6.core.command.xml.DeleteAttribute;
import org.libresource.so6.core.command.xml.DeleteNode;
import org.libresource.so6.core.command.xml.InsertAttribute;
import org.libresource.so6.core.command.xml.InsertNode;
import org.libresource.so6.core.command.xml.UpdateAttribute;
import org.libresource.so6.core.command.xml.UpdateXmlFile;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;


/**
 * Describe class <code>CompressEngine</code> here. Responsability: Implements
 * transformation function for compression Collaboration: CompressUtil
 *
 * @author Smack
 * @version 1.0
 */
public class CompressEngine {
    public CompressEngine() {
    }

    /*
     * c1 is the oldest Op c2 is the newest Op
     */
    public TranspResult transp(Command c1, Command c2)
        throws Exception {
        Command res = null;
        Method m = null;

        try {
            try {
                m = this.getClass().getMethod("transp", new Class[] { c1.getClass(), c2.getClass() });
            } catch (NoSuchMethodException e1) {
                //System.out.println("catch 1");
                try {
                    m = this.getClass().getMethod("transp", new Class[] { c1.getClass().getSuperclass(), c2.getClass() });
                } catch (NoSuchMethodException e2) {
                    //System.out.println("catch 2");
                    try {
                        m = this.getClass().getMethod("transp", new Class[] { c1.getClass(), c2.getClass().getSuperclass() });
                    } catch (NoSuchMethodException e3) {
                        //System.out.println("catch 3");
                        try {
                            m = this.getClass().getMethod("transp", new Class[] { c1.getClass().getSuperclass(), c2.getClass().getSuperclass() });
                        } catch (NoSuchMethodException e4) {
                            //System.out.println("catch 4");
                            try {
                                m = this.getClass().getMethod("transp",
                                        new Class[] { c1.getClass().getSuperclass().getSuperclass(), c2.getClass().getSuperclass() });
                            } catch (NoSuchMethodException e5) {
                                //System.out.println("catch 5");
                                try {
                                    m = this.getClass().getMethod("transp",
                                            new Class[] { c1.getClass().getSuperclass(), c2.getClass().getSuperclass().getSuperclass() });
                                } catch (NoSuchMethodException e6) {
                                    //System.out.println("catch 6");
                                    try {
                                        m = this.getClass().getMethod("transp", new Class[] { c1.getClass().getSuperclass().getSuperclass(), c2.getClass() });
                                    } catch (NoSuchMethodException e7) {
                                        //System.out.println("catch 7");
                                        try {
                                            m = this.getClass().getMethod("transp", new Class[] { c1.getClass().getSuperclass().getSuperclass(), c2.getClass() });
                                        } catch (NoSuchMethodException e8) {
                                            //System.out.println("catch 8");
                                            m = this.getClass().getMethod("transp",
                                                    new Class[] { c1.getClass().getSuperclass().getSuperclass(), c2.getClass().getSuperclass().getSuperclass() });
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            throw new Exception("No such methode : " + c1 + " / " + c2);
        }

        return (TranspResult) m.invoke(this, new Object[] { c1, c2 });
    }

    /*
     * ===========================================================
     */

    // path functions...
    public boolean childOf(Command c1, Command c2) {
        if (c1.getPath().startsWith(c2.getPath())) {
            return true;
        } else {
            return false;
        }
    }

    // getParentPath('/1/2/3/4') will return '/1/2/3'
    public String getParentPath(String path) {
        //System.out.println(path);
        int lastIndex = path.lastIndexOf("/");

        if (lastIndex == -1) {
            return "";
        }

        return path.substring(0, lastIndex + 1);
    }

    // getFSName('/1/2/3/4') will return '4'
    public String getFSName(String path) {
        //return path.substring(path.lastIndexOf(File.pathSeparatorChar)+1);
        return path.substring(path.lastIndexOf("/") + 1);
    }

    // replacePath('/1/2/3/4','/1/5') will return '/1/5/3/4'
    // replacePath('/1/2/3/4','/1/2#')
    // commonpath: /1/
    //  2/3/4
    // FSRenamed: 2#
    // result /1/ 2# 2/3/4
    public String replacePath(String pathToRename, String newPathToApply) {
        String commonPath = getParentPath(newPathToApply);
        assert pathToRename.startsWith(commonPath) : "bad replacement of path '" + pathToRename + "' with '" + newPathToApply + "'";

        String FSRenamed = newPathToApply.substring(commonPath.length());
        String unchangedTail = pathToRename.substring(commonPath.length());
        int index = unchangedTail.indexOf("/");

        if (index == -1) {
            unchangedTail = "";
        } else {
            unchangedTail = unchangedTail.substring(unchangedTail.indexOf("/"));
        }

        Logger.getLogger("divers").info("!!!replacePath:");
        Logger.getLogger("divers").info(" pathToRename:" + pathToRename);
        Logger.getLogger("divers").info(" newPathToApply:" + newPathToApply);
        Logger.getLogger("divers").info(" commonPath:" + commonPath);
        Logger.getLogger("divers").info(" FSRenamed:" + FSRenamed);
        Logger.getLogger("divers").info(" unchangedTail:" + unchangedTail);
        Logger.getLogger("divers").info(" return:" + commonPath + FSRenamed + unchangedTail);

        return commonPath + FSRenamed + unchangedTail;
    } // generate a unique path which must be the same for uniquePath(c1,c2)

    // and uniquePath(c2,c1)
    public String uniquePath(Command c1, Command c2) {
        long maxTicket = java.lang.Math.max(c1.getTicket(), c2.getTicket());

        return new String(c1.getPath() + "#" + maxTicket);
    }

    /*
     * =========================================== AddFile - AddFile - AddDir -
     * UpdateFile (UpdateBinaryFile / AddBlock - DelBlock) - EmptyOp - Id - NoOp -
     * Remove - Rename ============================================
     */

    // AddFile - Update
    public TranspResult transp(AddBinaryFile c1, UpdateBinaryFile c2)
        throws Exception {
        if (c1.getPath().equals(c2.getPath())) {
            c1.setAttachement(c2.getAttachement());

            return new TranspResult(c1, new EmptyOp(c2), false);
        }

        return new TranspResult(c2, c1, true);
    }

    // AddFile - AddFile
    public TranspResult transp(AddTxtFile c1, AddBlock c2)
        throws Exception {
        if (c1.getPath().equals(c2.getPath())) {
            c2.doTheJobOnFile(c1.getAttachement());

            return new TranspResult(c1, new EmptyOp(c2), false);
        }

        return new TranspResult(c2, c1, true);
    }

    public TranspResult transp(AddTxtFile c1, DelBlock c2)
        throws Exception {
        if (c1.getPath().equals(c2.getPath())) {
            c2.doTheJobOnFile(c1.getAttachement());

            return new TranspResult(c1, new EmptyOp(c2), false);
        }

        return new TranspResult(c2, c1, true);
    }

    // AddFile - AddFile
    public TranspResult transp(AddFile c1, AddFile c2) {
        return new TranspResult(c1, c2, false);
    }

    // AddFile - AddDir
    public TranspResult transp(AddFile c1, AddDir c2) {
        // put AddDir before AddFile
        return new TranspResult(c2, c1, true);
    }

    // AddFile - UpdateBinaryFile
    public TranspResult transp(AddFile c1, UpdateFile c2) {
        return new TranspResult(c2, c1, true);
    }

    public TranspResult transp(AddFile c1, UpdateBinaryFile c2) {
        return new TranspResult(c2, c1, true);
    }

    // AddFile - EmptyOp
    public TranspResult transp(AddFile c1, NeutralCommand c2) {
        return new TranspResult(c1, c2, false);
    }

    // AddFile - Remove
    public TranspResult transp(AddFile c1, Remove c2) {
        if (c1.getPath().equals(c2.getPath())) {
            // No need to create the file and to keep the remove
            return new TranspResult(new EmptyOp(c1), new EmptyOp(c2), false);
        }

        if (childOf(c1, c2)) {
            // Remove a parent dir
            return new TranspResult(c2, new EmptyOp(c1), true);
        }

        return new TranspResult(c2, c1, true);
    }

    // AddFile - Rename
    public TranspResult transp(AddFile c1, Rename c2) {
        if (c1.getPath().equals(c2.getPath())) {
            // Rename the AddFile and Delete the Rename
            c1.setPath(c2.getNewPath());

            return new TranspResult(c1, new EmptyOp(c2), false);
        }

        if (childOf(c1, c2)) {
            // rename the parent path concerned by the rename
            c1.setPath(replacePath(c1.getPath(), c2.getNewPath()));

            return new TranspResult(c2, c1, true);
        }

        return new TranspResult(c2, c1, true);
    }

    /*
     * =========================================== AddDir - AddDir - AddFile -
     * UpdateFile (UpdateBinaryFile / AddBlock - DelBlock) - NeutralOp - Remove -
     * Rename ============================================
     */

    // AddDir - AddDir
    public TranspResult transp(AddDir c1, AddDir c2) {
        return new TranspResult(c1, c2, false);
    }

    // AddDir - AddFile
    public TranspResult transp(AddDir c1, AddFile c2) {
        return new TranspResult(c1, c2, false);
    }

    // AddDir - UpdateBinaryFile
    public TranspResult transp(AddDir c1, UpdateFile c2) {
        // Should never happen
        return new TranspResult(c1, c2, false);
    }

    public TranspResult transp(AddDir c1, UpdateBinaryFile c2) {
        // Should never happen
        return new TranspResult(c1, c2, false);
    }

    public TranspResult transp(AddDir c1, UpdateTextFile c2) {
        // Should never happen
        return new TranspResult(c1, c2, false);
    }

    public TranspResult transp(AddDir c1, UpdateXmlFile c2) {
        // Should never happen
        return new TranspResult(c1, c2, false);
    }

    // AddDir - EmptyOp
    public TranspResult transp(AddDir c1, NeutralCommand c2) {
        return new TranspResult(c1, c2, false);
    }

    // AddDir - Remove
    public TranspResult transp(AddDir c1, Remove c2) {
        if (c1.getPath().equals(c2.getPath())) {
            // No need to create the dir and to keep the remove
            return new TranspResult(new EmptyOp(c1), new EmptyOp(c2), false);
        }

        if (childOf(c1, c2)) {
            // Remove a parent dir
            return new TranspResult(c2, new EmptyOp(c1), true);
        }

        return new TranspResult(c2, c1, true);
    }

    // AddDir - Rename
    public TranspResult transp(AddDir c1, Rename c2) {
        if (c1.getPath().equals(c2.getPath())) {
            // Rename the AddDir and Delete the Rename
            c1.setPath(c2.getNewPath());

            return new TranspResult(c1, new EmptyOp(c2), false);
        }

        if (childOf(c1, c2)) {
            // rename the parent path concerned by the rename
            c1.setPath(replacePath(c1.getPath(), c2.getNewPath()));

            return new TranspResult(c2, c1, true);
        }

        return new TranspResult(c2, c1, true);
    }

    /*
     * =========================================== UpdateBinaryFile - AddDir -
     * AddFile - AddBinaryFile - UpdateFile (UpdateBinaryFile / AddBlock -
     * DelBlock) - EmptyOp - Id - NoOp - Remove - Rename
     * ============================================
     */

    // UpdateBinaryFile - UpdateBinaryFile
    public TranspResult transp(UpdateBinaryFile c1, UpdateBinaryFile c2) {
        if (c1.getPath().equals(c2.getPath())) {
            return new TranspResult(c2, new EmptyOp(c1), true);
        }

        return new TranspResult(c2, c1, true);
    }

    public TranspResult transp(UpdateBinaryFile c1, UpdateFile c2) {
        if (c1.getPath().equals(c2.getPath())) {
            return new TranspResult(c2, new EmptyOp(c1), true);
        }

        return new TranspResult(c1, c2, false);
    }

    public TranspResult transp(UpdateBinaryFile c1, UpdateTextFile c2) {
        if (c1.getPath().equals(c2.getPath())) {
            return new TranspResult(c2, new EmptyOp(c1), true);
        }

        return new TranspResult(c1, c2, false);
    }

    public TranspResult transp(UpdateBinaryFile c1, UpdateXmlFile c2) {
        if (c1.getPath().equals(c2.getPath())) {
            return new TranspResult(c2, new EmptyOp(c1), true);
        }

        return new TranspResult(c1, c2, false);
    }

    public TranspResult transp(UpdateFile c1, UpdateBinaryFile c2) {
        if (c1.getPath().equals(c2.getPath())) {
            return new TranspResult(c2, new EmptyOp(c1), true);
        }

        return new TranspResult(c1, c2, false);
    }

    // UpdateBinaryFile - AddDir
    public TranspResult transp(UpdateFile c1, AddDir c2) {
        return new TranspResult(c2, c1, true);
    }

    // UpdateBinaryFile - AddDir
    public TranspResult transp(UpdateBinaryFile c1, AddDir c2) {
        return new TranspResult(c2, c1, true);
    }

    // UpdateBinaryFile - AddFile
    public TranspResult transp(UpdateFile c1, AddFile c2) {
        return new TranspResult(c2, c1, true);
    }

    // UpdateBinaryFile - AddFile
    public TranspResult transp(UpdateBinaryFile c1, AddFile c2) {
        return new TranspResult(c2, c1, true);
    }

    // UpdateBinaryFile - AddBinaryFile
    public TranspResult transp(UpdateFile c1, UpdateFile c2) {
        if (c1.getPath().equals(c2.getPath())) {
            return new TranspResult(c1, c2, false);
        }

        return new TranspResult(c2, c1, true);
    }

    // UpdateBinaryFile - EmptyOp
    public TranspResult transp(UpdateFile c1, NeutralCommand c2) {
        return new TranspResult(c1, c2, false);
    }

    // UpdateBinaryFile - EmptyOp
    public TranspResult transp(UpdateBinaryFile c1, NeutralCommand c2) {
        return new TranspResult(c1, c2, false);
    }

    // UpdateBinaryFile - Remove
    public TranspResult transp(UpdateFile c1, Remove c2) {
        if (c1.getPath().equals(c2.getPath())) {
            // No need to create the dir and to keep the remove
            return new TranspResult(c2, new EmptyOp(c1), true);
        }

        if (childOf(c1, c2)) {
            // Remove a parent dir
            return new TranspResult(c2, new EmptyOp(c1), true);
        }

        return new TranspResult(c2, c1, true);
    }

    // UpdateBinaryFile - Remove
    public TranspResult transp(UpdateBinaryFile c1, Remove c2) {
        if (c1.getPath().equals(c2.getPath())) {
            // No need to create the dir and to keep the remove
            return new TranspResult(c2, new EmptyOp(c1), true);
        }

        if (childOf(c1, c2)) {
            // Remove a parent dir
            return new TranspResult(c2, new EmptyOp(c1), true);
        }

        return new TranspResult(c2, c1, true);
    }

    // UpdateBinaryFile - Rename
    public TranspResult transp(UpdateBinaryFile c1, Rename c2) {
        if (c1.getPath().equals(c2.getPath())) {
            // Rename the UpdateBinaryFile and propagate the Rename
            c1.setPath(c2.getNewPath());

            return new TranspResult(c2, c1, true);
        }

        if (childOf(c1, c2)) {
            // rename the parent path concerned by the rename
            c1.setPath(replacePath(c1.getPath(), c2.getNewPath()));

            return new TranspResult(c2, c1, true);
        }

        return new TranspResult(c2, c1, true);
    }

    // UpdateBinaryFile - Rename
    public TranspResult transp(UpdateFile c1, Rename c2) {
        if (c1.getPath().equals(c2.getPath())) {
            // Rename the UpdateFile and propagate the Rename
            c1.setPath(c2.getNewPath());

            return new TranspResult(c2, c1, true);
        }

        if (childOf(c1, c2)) {
            // rename the parent path concerned by the rename
            c1.setPath(replacePath(c1.getPath(), c2.getNewPath()));

            return new TranspResult(c2, c1, true);
        }

        return new TranspResult(c2, c1, true);
    }

    /*
     * =========================================== AddBlock - AddDir - AddFile -
     * AddBinaryFile - UpdateFile (UpdateBinaryFile / AddBlock - DelBlock) -
     * EmptyOp - Id - NoOp - Remove - Rename
     * ============================================
     */

    // AddBlock - AddBlock
    public TranspResult transp(AddBlock c1, AddBlock c2) {
        if (c1.getPath().equals(c2.getPath())) {
            int s1 = c1.getInsertPoint();
            int l1 = c1.getSize();
            int s2 = c2.getInsertPoint();
            int l2 = c2.getSize();

            if ((s1 <= s2) && (s2 <= (s1 + l1))) {
                // s2 inside of s1
                Collection block = c1.getContent();
                Collection blockInside = c2.getContent();
                ArrayList newContent = new ArrayList();
                Iterator i = block.iterator();

                for (int b = 0; (b < (s2 - s1)) && i.hasNext(); b++) {
                    newContent.add(i.next());
                }

                for (Iterator j = blockInside.iterator(); j.hasNext();) {
                    newContent.add(j.next());
                }

                while (i.hasNext()) {
                    newContent.add(i.next());
                }

                c1.setContent(newContent);

                return new TranspResult(c1, new EmptyOp(c2), false);
            } else {
                // The two blocks are disjoint
                return new TranspResult(c1, c2, false);
            }
        }

        return new TranspResult(c2, c1, true);
    }

    // AddBlock - DelBlock
    public TranspResult transp(AddBlock c1, DelBlock c2) {
        if (c1.getPath().equals(c2.getPath())) {
            int s1 = c1.getInsertPoint();
            int l1 = c1.getSize();
            int s2 = c2.getDeletePoint();
            int l2 = c2.getSize();
            Object[] add = c1.getContent().toArray();
            Object[] delete = c2.getOldContent().toArray();
            Collection addContent = new ArrayList();
            Collection deleteContent = new ArrayList();

            if ((s1 <= s2) && (s2 < (s1 + l1))) {
                // s2 inside s1
                if (l2 > (l1 - (s2 - s1))) {
                    // The delete is bigger than the add
                    if (s1 == s2) {
                        // The delete remove the add
                        for (int i = add.length; i < delete.length; i++) {
                            deleteContent.add(delete[i]);
                        }

                        c2.setOldContent(deleteContent);

                        return new TranspResult(c2, new EmptyOp(c1), true);
                    } else {
                        // The delete remove the tail of the add
                        for (int i = 0; i < (s2 - s1); i++) {
                            addContent.add(add[i]);
                        }

                        for (int i = ((s1 + l1) - (s2 - s1)); i < delete.length; i++) {
                            deleteContent.add(delete[i]);
                        }

                        c1.setContent(addContent);
                        c2.setOldContent(deleteContent);
                        c2.setDeletePoint(s2 - c1.getSize());

                        return new TranspResult(c2, c1, true);
                    }
                } else {
                    // The delete is include in the add
                    for (int i = 0; i < add.length; i++) {
                        if (!(((s2 - s1) <= i) && (i < (s2 - s1 + l2)))) {
                            addContent.add(add[i]);
                        }
                    }

                    if (addContent.size() == 0) {
                        return new TranspResult(new EmptyOp(c1), new EmptyOp(c2), false);
                    }

                    c1.setContent(addContent);

                    return new TranspResult(c1, new EmptyOp(c2), false);
                }
            } else {
                // The two blocks are disjoint
                if (s1 < s2) {
                    // Add before del
                    c2.setDeletePoint(s2 - l1);
                }

                return new TranspResult(c2, c1, true);
            }
        }

        return new TranspResult(c2, c1, true);
    }

    /*
     * =========================================== DelBlock - AddDir - AddFile -
     * AddBinaryFile - UpdateFile (UpdateBinaryFile / AddBlock - DelBlock) -
     * EmptyOp - Id - NoOp - Remove - Rename
     * ============================================
     */

    // DelBlock - DelBlock
    public TranspResult transp(DelBlock c1, DelBlock c2) {
        int s1 = c1.getDeletePoint();
        int s2 = c2.getDeletePoint();

        if (c1.getPath().equals(c2.getPath())) {
            if (s1 == s2) {
                // 2 in 1
                Collection c = c1.getOldContent();
                c.add(c2.getOldContent());
                c1.setOldContent(c);

                return new TranspResult(c1, new EmptyOp(c2), true);
            } else {
                return new TranspResult(c1, c2, false);
            }
        }

        return new TranspResult(c2, c1, true);
    }

    // DelBlock - AddBlock
    public TranspResult transp(DelBlock c1, AddBlock c2) {
        return new TranspResult(c1, c2, false);
    }

    /*
     * =========================================== NeutralCommand - AddDir -
     * AddFile - AddBinaryFile - UpdateFile (UpdateBinaryFile / AddBlock -
     * DelBlock) - EmptyOp - Id - NoOp - Remove - Rename
     * ============================================
     */

    // EmptyOp - AddDir
    public TranspResult transp(NeutralCommand c1, AddDir c2) {
        return new TranspResult(c2, c1, true);
    }

    // EmptyOp - AddFile
    public TranspResult transp(NeutralCommand c1, AddFile c2) {
        return new TranspResult(c2, c1, true);
    }

    // EmptyOp - AddBinaryFile
    public TranspResult transp(NeutralCommand c1, AddBinaryFile c2) {
        return new TranspResult(c2, c1, true);
    }

    // EmptyOp - UpdateBinaryFile
    public TranspResult transp(NeutralCommand c1, UpdateBinaryFile c2) {
        return new TranspResult(c2, c1, true);
    }

    // EmptyOp - AddBlock
    public TranspResult transp(NeutralCommand c1, AddBlock c2) {
        return new TranspResult(c2, c1, true);
    }

    // EmptyOp - DelBlock
    public TranspResult transp(NeutralCommand c1, DelBlock c2) {
        return new TranspResult(c2, c1, true);
    }

    // NeutralCommand - NeutralCommand
    public TranspResult transp(NeutralCommand c1, NeutralCommand c2) {
        return new TranspResult(new EmptyOp(c2), new EmptyOp(c1), false);
    }

    // EmptyOp - Remove
    public TranspResult transp(NeutralCommand c1, Remove c2) {
        return new TranspResult(c2, c1, true);
    }

    // EmptyOp - Rename
    public TranspResult transp(NeutralCommand c1, Rename c2) {
        return new TranspResult(c2, c1, true);
    }

    // EmptyOp - InsertNode
    public TranspResult transp(NeutralCommand c1, InsertNode c2) {
        return new TranspResult(c2, c1, true);
    }

    // EmptyOp - DeleteNode
    public TranspResult transp(NeutralCommand c1, DeleteNode c2) {
        return new TranspResult(c2, c1, true);
    }

    // EmptyOp - InsertAttribute
    public TranspResult transp(NeutralCommand c1, InsertAttribute c2) {
        return new TranspResult(c2, c1, true);
    }

    // EmptyOp - DeleteAttribute
    public TranspResult transp(NeutralCommand c1, DeleteAttribute c2) {
        return new TranspResult(c2, c1, true);
    }

    // EmptyOp - UpdateAttribute
    public TranspResult transp(NeutralCommand c1, UpdateAttribute c2) {
        return new TranspResult(c2, c1, true);
    }

    /*
     * =========================================== Remove - AddDir - AddFile -
     * AddBinaryFile - UpdateFile (UpdateBinaryFile / AddBlock - DelBlock) -
     * EmptyOp - Id - NoOp - Remove - Rename
     * ============================================
     */

    // Remove - AddDir
    public TranspResult transp(Remove c1, AddDir c2) {
        return new TranspResult(c1, c2, false);
    }

    // Remove - AddFile
    public TranspResult transp(Remove c1, AddFile c2) {
        return new TranspResult(c1, c2, false);
    }

    // Remove - UpdateBinaryFile
    public TranspResult transp(Remove c1, UpdateFile c2) {
        return new TranspResult(c1, c2, false);
    }

    public TranspResult transp(Remove c1, UpdateTextFile c2) {
        return new TranspResult(c1, c2, false);
    }

    public TranspResult transp(Remove c1, UpdateXmlFile c2) {
        return new TranspResult(c1, c2, false);
    }

    // Remove - UpdateBinaryFile
    public TranspResult transp(Remove c1, UpdateBinaryFile c2) {
        return new TranspResult(c1, c2, false);
    }

    // Remove - EmptyOp
    public TranspResult transp(Remove c1, NeutralCommand c2) {
        return new TranspResult(c1, new EmptyOp(c2), false);
    }

    // Remove - Remove
    public TranspResult transp(Remove c1, Remove c2) {
        if (childOf(c1, c2)) {
            return new TranspResult(c2, new EmptyOp(c1), true);
        }

        return new TranspResult(c2, c1, true);
    }

    // Remove - Rename
    public TranspResult transp(Remove c1, Rename c2) {
        return new TranspResult(c1, c2, false);
    }

    /*
     * =========================================== Rename - AddDir - AddFile -
     * AddBinaryFile - UpdateFile (UpdateBinaryFile / AddBlock - DelBlock) -
     * EmptyOp - Id - NoOp - Remove - Rename
     * ============================================
     */

    // Rename - AddDir
    public TranspResult transp(Rename c1, AddDir c2) {
        return new TranspResult(c1, c2, false);
    }

    // Rename - AddFile
    public TranspResult transp(Rename c1, AddFile c2) {
        return new TranspResult(c1, c2, false);
    }

    // Rename - UpdateBinaryFile
    public TranspResult transp(Rename c1, UpdateFile c2) {
        return new TranspResult(c1, c2, false);
    }

    public TranspResult transp(Rename c1, UpdateTextFile c2) {
        return new TranspResult(c1, c2, false);
    }

    public TranspResult transp(Rename c1, UpdateXmlFile c2) {
        return new TranspResult(c1, c2, false);
    }

    // Rename - UpdateBinaryFile
    public TranspResult transp(Rename c1, UpdateBinaryFile c2) {
        return new TranspResult(c1, c2, false);
    }

    // Rename - EmptyOp
    public TranspResult transp(Rename c1, NeutralCommand c2) {
        return new TranspResult(c1, new EmptyOp(c2), false);
    }

    // Rename - Remove
    public TranspResult transp(Rename c1, Remove c2) {
        return new TranspResult(c1, c2, false);
    }

    // Rename - Rename
    public TranspResult transp(Rename c1, Rename c2) {
        if (c1.getNewPath().equals(c2.getPath())) {
            c2.setPath(c1.getPath());

            return new TranspResult(c2, new EmptyOp(c1), true);
        }

        return new TranspResult(c1, c2, false);
    }

    /*
     * =========================================== Rename - AddDir - AddFile -
     * AddBinaryFile - UpdateFile (UpdateBinaryFile / AddBlock - DelBlock) -
     * EmptyOp - Id - NoOp - Remove - Rename
     * ============================================
     */
    public TranspResult transp(AddXmlFile c1, DeleteAttribute c2)
        throws Exception {
        if (c1.getPath().equals(c2.getPath())) {
            c2.doTheJobOnFile(c1.getAttachement());

            return new TranspResult(c1, new EmptyOp(c2), false);
        }

        return new TranspResult(c2, c1, true);
    }

    public TranspResult transp(UpdateXmlFile c1, UpdateXmlFile c2)
        throws Exception {
        if (c1.getPath().equals(c2.getPath())) {
            return new TranspResult(c1, c2, false);
        }

        return new TranspResult(c2, c1, true);
    }

    public TranspResult transp(AddXmlFile c1, DeleteNode c2)
        throws Exception {
        if (c1.getPath().equals(c2.getPath())) {
            c2.doTheJobOnFile(c1.getAttachement());

            return new TranspResult(c1, new EmptyOp(c2), false);
        }

        return new TranspResult(c2, c1, true);
    }

    public TranspResult transp(AddXmlFile c1, InsertAttribute c2)
        throws Exception {
        if (c1.getPath().equals(c2.getPath())) {
            c2.doTheJobOnFile(c1.getAttachement());

            return new TranspResult(c1, new EmptyOp(c2), false);
        }

        return new TranspResult(c2, c1, true);
    }

    public TranspResult transp(AddXmlFile c1, UpdateAttribute c2)
        throws Exception {
        if (c1.getPath().equals(c2.getPath())) {
            c2.doTheJobOnFile(c1.getAttachement());

            return new TranspResult(c1, new EmptyOp(c2), false);
        }

        return new TranspResult(c2, c1, true);
    }

    public TranspResult transp(AddXmlFile c1, InsertNode c2)
        throws Exception {
        if (c1.getPath().equals(c2.getPath())) {
            c2.doTheJobOnFile(c1.getAttachement());

            return new TranspResult(c1, new EmptyOp(c2), false);
        }

        return new TranspResult(c2, c1, true);
    }

    // ============================================
    // Result class of the transposition
    // ============================================
    public class TranspResult {
        private boolean keepGoing = true;
        private Command cmd1 = null;
        private Command cmd2 = null;

        public TranspResult() {
        }

        public TranspResult(Command cmd1, Command cmd2, boolean keepGoing) {
            this.cmd1 = cmd1;
            this.cmd2 = cmd2;
            this.keepGoing = keepGoing;

            // Set the right ticket to the command
            long ticket1 = cmd1.getTicket();
            long ticket2 = cmd2.getTicket();

            if (ticket1 < ticket2) {
                // order ok
            } else {
                // change ticket
                cmd1.setTicket(ticket2);
                cmd2.setTicket(ticket1);
            }
        }

        public Command getCmd1() {
            return cmd1;
        }

        public Command getCmd2() {
            return cmd2;
        }

        public boolean isKeepGoing() {
            return keepGoing;
        }

        public void setCmd1(Command command) {
            cmd1 = command;
        }

        public void setCmd2(Command command) {
            cmd2 = command;
        }

        public void setKeepGoing(boolean b) {
            keepGoing = b;
        }
    }
}
