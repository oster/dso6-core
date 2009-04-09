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
package org.libresource.so6.core.tf;

import fr.loria.ecoo.so6.xml.util.XmlUtil;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.command.Command;
import org.libresource.so6.core.command.Id;
import org.libresource.so6.core.command.NoOp;
import org.libresource.so6.core.command.xml.DeleteAttribute;
import org.libresource.so6.core.command.xml.DeleteNode;
import org.libresource.so6.core.command.xml.InsertAttribute;
import org.libresource.so6.core.command.xml.InsertNode;
import org.libresource.so6.core.command.xml.UpdateAttribute;
import org.libresource.so6.core.command.xml.UpdateXmlFile;
import org.libresource.so6.core.engine.util.ObjectCloner;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;


/**
 * @author Ecoo Team Loria
 */
public class XmlFileFunctions {
    private WsConnection ws;

    public XmlFileFunctions(WsConnection ws) {
        this.ws = ws;
    }

    public Command transp(UpdateXmlFile c1, UpdateXmlFile c2)
        throws Exception {
        Command res = null;
        Method m = null;

        try {
            // Make transformation only if needed
            if (c1.getPath().equals(c2.getPath())) {
                m = this.getClass().getMethod("tf", new Class[] { c1.getClass(), c2.getClass() });
            } else {
                return c1;
            }
        } catch (NoSuchMethodException e1) {
            try {
                m = this.getClass().getMethod("tf", new Class[] { c1.getClass().getSuperclass(), c2.getClass() });
            } catch (NoSuchMethodException e2) {
                try {
                    m = this.getClass().getMethod("tf", new Class[] { c1.getClass(), c2.getClass().getSuperclass() });
                } catch (NoSuchMethodException e3) {
                    m = this.getClass().getMethod("tf", new Class[] { c1.getClass().getSuperclass(), c2.getClass().getSuperclass() });
                }
            }
        }

        //System.out.println("Tf: " + c1 + "\n " + c2);
        //System.out.println("method: "+m);
        Command c = (Command) m.invoke(this, new Object[] { c1, c2 });

        //System.out.println("return: " + c);
        return c;
    }

    /*
     * Take care of XML commands - InsertNode - DeleteNode - UpdateNode -
     * MoveNode - InsertProcessingInstructionNode - InsertAttribute -
     * DeleteAttribute - UpdateAttribute - SetDocumentType - InsertSubTree
     */

    /**
     * InsertNode
     */
    public Command tf(InsertNode c1, InsertNode c2) throws Exception {
        String p1 = XmlUtil.getParentPath(c1.getNodePath());
        String p2 = XmlUtil.getParentPath(c2.getNodePath());
        int pos2 = getChildPos(c2.getNodePath());
        UpdateXmlFile clone = (UpdateXmlFile) ObjectCloner.deepCopy(c1);

        if (p1.equals(p2)) {
            int pos1 = getChildPos(c1.getNodePath());

            if (pos1 < pos2) {
                return c1;
            } else if (pos1 == pos2) {
                if (c1.getTicket() == -1) {
                    clone.setNodePath(setChildIndex(getDepth(clone.getNodePath()), clone.getNodePath(),
                            getChildIndex(getDepth(clone.getNodePath()), clone.getNodePath()) + 1));

                    return clone;
                } else {
                    // c1 remote command
                    return c1;
                }
            } else { // pos1 > pos2
                clone.setNodePath(setChildIndex(getDepth(clone.getNodePath()), clone.getNodePath(),
                        getChildIndex(getDepth(clone.getNodePath()), clone.getNodePath()) + 1));

                return clone;
            }
        } else if (subsume(c1, c2)) {
            int pos = getChildIndex(getDepth(c2.getNodePath()), clone.getNodePath());

            if (pos2 <= pos) {
                clone.setNodePath(setChildIndex(getDepth(c2.getNodePath()), clone.getNodePath(), pos + 1));

                return clone;
            }

            return c1;
        } else {
            return c1;
        }
    }

    public Command tf(InsertNode c1, DeleteNode c2) throws Exception {
        UpdateXmlFile clone = (UpdateXmlFile) ObjectCloner.deepCopy(c1);
        int pos2 = getChildPos(c2.getNodePath());

        if (childOf(c1, c2)) {
            return new NoOp(c1, ws);
        } else if (subsume(c1, c2)) {
            int pos = getChildIndex(getDepth(c2.getNodePath()), clone.getNodePath());

            if (pos2 < pos) {
                clone.setNodePath(setChildIndex(getDepth(c2.getNodePath()), clone.getNodePath(), pos - 1));

                return clone;
            }
        }

        return c1;
    }

    public Command tf(InsertNode c1, InsertAttribute c2)
        throws Exception {
        return c1;
    }

    public Command tf(InsertNode c1, DeleteAttribute c2)
        throws Exception {
        return c1;
    }

    public Command tf(InsertNode c1, UpdateAttribute c2)
        throws Exception {
        return c1;
    }

    /**
     * DeleteNode
     */
    public Command tf(DeleteNode c1, InsertNode c2) throws Exception {
        UpdateXmlFile clone = (UpdateXmlFile) ObjectCloner.deepCopy(c1);
        int pos2 = getChildPos(c2.getNodePath());

        if (subsume(c1, c2)) {
            int pos = getChildIndex(getDepth(c2.getNodePath()), clone.getNodePath());

            if (pos2 <= pos) {
                clone.setNodePath(setChildIndex(getDepth(c2.getNodePath()), clone.getNodePath(), pos + 1));

                return clone;
            }
        }

        return c1;
    }

    public Command tf(DeleteNode c1, DeleteNode c2) throws Exception {
        UpdateXmlFile clone = (UpdateXmlFile) ObjectCloner.deepCopy(c1);
        int pos2 = getChildPos(c2.getNodePath());

        if (childOf(c1, c2)) {
            return new NoOp(c1, ws);
        } else if (subsume(c1, c2)) {
            int pos = getChildIndex(getDepth(c2.getNodePath()), clone.getNodePath());

            if (pos2 < pos) {
                clone.setNodePath(setChildIndex(getDepth(c2.getNodePath()), clone.getNodePath(), pos - 1));

                return clone;
            }
        }

        return c1;
    }

    public Command tf(DeleteNode c1, InsertAttribute c2)
        throws Exception {
        UpdateXmlFile clone = (UpdateXmlFile) ObjectCloner.deepCopy(c1);
        int pos2 = getChildPos(c2.getNodePath());

        if (subsume(c1, c2)) {
            int pos = getChildIndex(getDepth(c2.getNodePath()), clone.getNodePath());

            if (pos2 <= pos) {
                clone.setNodePath(setChildIndex(getDepth(c2.getNodePath()), clone.getNodePath(), pos + 1));

                return clone;
            }
        }

        return c1;
    }

    public Command tf(DeleteNode c1, DeleteAttribute c2)
        throws Exception {
        UpdateXmlFile clone = (UpdateXmlFile) ObjectCloner.deepCopy(c1);
        int pos2 = getChildPos(c2.getNodePath());

        if (subsume(c1, c2)) {
            int pos = getChildIndex(getDepth(c2.getNodePath()), clone.getNodePath());

            if (pos2 <= pos) {
                clone.setNodePath(setChildIndex(getDepth(c2.getNodePath()), clone.getNodePath(), pos + 1));

                return clone;
            }
        }

        return c1;
    }

    public Command tf(DeleteNode c1, UpdateAttribute c2)
        throws Exception {
        UpdateXmlFile clone = (UpdateXmlFile) ObjectCloner.deepCopy(c1);
        int pos2 = getChildPos(c2.getNodePath());

        if (subsume(c1, c2)) {
            int pos = getChildIndex(getDepth(c2.getNodePath()), clone.getNodePath());

            if (pos2 <= pos) {
                clone.setNodePath(setChildIndex(getDepth(c2.getNodePath()), clone.getNodePath(), pos + 1));

                return clone;
            }
        }

        return c1;
    }

    /**
     * InsertAttribute
     */
    public Command tf(InsertAttribute c1, InsertNode c2)
        throws Exception {
        UpdateXmlFile clone = (UpdateXmlFile) ObjectCloner.deepCopy(c1);
        int pos2 = getChildPos(c2.getNodePath());

        if (subsume(c1, c2)) {
            int pos = getChildIndex(getDepth(c2.getNodePath()), clone.getNodePath());

            if (pos2 <= pos) {
                clone.setNodePath(setChildIndex(getDepth(c2.getNodePath()), clone.getNodePath(), pos + 1));

                return clone;
            }
        }

        return c1;
    }

    public Command tf(InsertAttribute c1, DeleteNode c2)
        throws Exception {
        UpdateXmlFile clone = (UpdateXmlFile) ObjectCloner.deepCopy(c1);
        int pos2 = getChildPos(c2.getNodePath());

        if (childOf(c1, c2)) {
            return new NoOp(c1, ws);
        } else if (subsume(c1, c2)) {
            int pos = getChildIndex(getDepth(c2.getNodePath()), clone.getNodePath());

            if (pos2 < pos) {
                clone.setNodePath(setChildIndex(getDepth(c2.getNodePath()), clone.getNodePath(), pos - 1));

                return clone;
            }
        }

        return c1;
    }

    public Command tf(InsertAttribute c1, InsertAttribute c2)
        throws Exception {
        if (c1.getNodePath().equals(c2.getNodePath()) && c1.getAttributeName().equals(c2.getAttributeName())) {
            if (c1.getAttributeValue().equals(c2.getAttributeValue())) {
                return new Id(c1, ws);
            } else {
                //InsertAttribute clone = (InsertAttribute)
                // ObjectCloner.deepCopy(c1);
                String newvalue = null;
                UpdateAttribute clone = new UpdateAttribute(c1.getTicket(), c1.getPath(), c1.getWsName(), c1.getTime(), c1.getNodePath(),
                        c1.getAttributeName(), c1.getAttributeValue(), newvalue);

                if (c1.getTicket() == -1) {
                    newvalue = c2.getAttributeValue() + "|" + c1.getAttributeValue();
                } else {
                    newvalue = c1.getAttributeValue() + "|" + c2.getAttributeValue();
                }

                clone.setNewValue(newvalue);

                //clone.setAttributeValue(newvalue);
                return clone;
            }
        } else {
            return c1;
        }
    }

    public Command tf(InsertAttribute c1, DeleteAttribute c2)
        throws Exception {
        return c1;
    }

    public Command tf(InsertAttribute c1, UpdateAttribute c2)
        throws Exception {
        return c1;
    }

    /**
     * DeleteAttribute
     */
    public Command tf(DeleteAttribute c1, InsertNode c2)
        throws Exception {
        UpdateXmlFile clone = (UpdateXmlFile) ObjectCloner.deepCopy(c1);
        int pos2 = getChildPos(c2.getNodePath());

        if (subsume(c1, c2)) {
            int pos = getChildIndex(getDepth(c2.getNodePath()), clone.getNodePath());

            if (pos2 <= pos) {
                clone.setNodePath(setChildIndex(getDepth(c2.getNodePath()), clone.getNodePath(), pos + 1));

                return clone;
            }
        }

        return c1;
    }

    public Command tf(DeleteAttribute c1, DeleteNode c2)
        throws Exception {
        UpdateXmlFile clone = (UpdateXmlFile) ObjectCloner.deepCopy(c1);
        int pos2 = getChildPos(c2.getNodePath());

        if (childOf(c1, c2)) {
            return new NoOp(c1, ws);
        } else if (subsume(c1, c2)) {
            int pos = getChildIndex(getDepth(c2.getNodePath()), clone.getNodePath());

            if (pos2 < pos) {
                clone.setNodePath(setChildIndex(getDepth(c2.getNodePath()), clone.getNodePath(), pos - 1));

                return clone;
            }
        }

        return c1;
    }

    public Command tf(DeleteAttribute c1, InsertAttribute c2)
        throws Exception {
        return c1;
    }

    public Command tf(DeleteAttribute c1, DeleteAttribute c2)
        throws Exception {
        if (c1.getNodePath().equals(c2.getNodePath()) && c1.getAttributeName().equals(c2.getAttributeName())) {
            return new Id(c1, ws);
        } else {
            return c1;
        }
    }

    public Command tf(DeleteAttribute c1, UpdateAttribute c2)
        throws Exception {
        return c1;
    }

    /**
     * UpdateAttribute
     */
    public Command tf(UpdateAttribute c1, InsertNode c2)
        throws Exception {
        UpdateXmlFile clone = (UpdateXmlFile) ObjectCloner.deepCopy(c1);
        int pos2 = getChildPos(c2.getNodePath());

        if (subsume(c1, c2)) {
            int pos = getChildIndex(getDepth(c2.getNodePath()), clone.getNodePath());

            if (pos2 <= pos) {
                clone.setNodePath(setChildIndex(getDepth(c2.getNodePath()), clone.getNodePath(), pos + 1));

                return clone;
            }
        }

        return c1;
    }

    public Command tf(UpdateAttribute c1, DeleteNode c2)
        throws Exception {
        UpdateXmlFile clone = (UpdateXmlFile) ObjectCloner.deepCopy(c1);
        int pos2 = getChildPos(c2.getNodePath());

        if (childOf(c1, c2)) {
            return new NoOp(c1, ws);
        } else if (subsume(c1, c2)) {
            int pos = getChildIndex(getDepth(c2.getNodePath()), clone.getNodePath());

            if (pos2 < pos) {
                clone.setNodePath(setChildIndex(getDepth(c2.getNodePath()), clone.getNodePath(), pos - 1));

                return clone;
            }
        }

        return c1;
    }

    public Command tf(UpdateAttribute c1, InsertAttribute c2)
        throws Exception {
        return c1;
    }

    public Command tf(UpdateAttribute c1, DeleteAttribute c2)
        throws Exception {
        if (c1.getNodePath().equals(c2.getNodePath()) && c1.getAttributeName().equals(c2.getAttributeName())) {
            return new NoOp(c1, ws);
        } else {
            return c1;
        }
    }

    public Command tf(UpdateAttribute c1, UpdateAttribute c2)
        throws Exception {
        if (c1.getNodePath().equals(c2.getNodePath()) && c1.getAttributeName().equals(c2.getAttributeName())) {
            if (c1.getNewValue().equals(c2.getNewValue())) {
                return new Id(c1, ws);
            } else {
                UpdateAttribute clone = (UpdateAttribute) ObjectCloner.deepCopy(c1);
                String newvalue;

                // peut etre qu'on devrait mettre a jour la oldvalue aussi ???
                if (c1.getTicket() == -1) {
                    newvalue = c2.getNewValue() + "|" + c1.getNewValue();
                } else {
                    newvalue = c1.getNewValue() + "|" + c2.getNewValue();
                }

                clone.setNewValue(newvalue);

                return clone;
            }
        } else {
            return c1;
        }
    }

    /**
     * XML Util
     */
    public boolean childOf(UpdateXmlFile c1, UpdateXmlFile c2) {
        return c1.getNodePath().startsWith(c2.getNodePath());
    }

    // le path de C1 est subsum? par le path de C2
    // parent de C2 est un prefix du parent de C1
    public boolean subsume(UpdateXmlFile c1, UpdateXmlFile c2) {
        String c1Path = XmlUtil.getParentPath(c1.getNodePath());
        String c2ParentPath = XmlUtil.getParentPath(c2.getNodePath());

        //System.out.println("c1:" + c1.getNodePath());
        //System.out.println("c2:" + c2.getNodePath());
        //System.out.println("subsume(c1,c2) [c2 plus petit c1]:" +
        // c1Path.startsWith(c2ParentPath));
        return c1Path.startsWith(c2ParentPath);
    }

    public int getChildPos(String path) {
        int index = path.lastIndexOf(":");

        if (index == -1) {
            return Integer.parseInt(path);
        }

        return Integer.parseInt(path.substring(index + 1));
    }

    public int getChildIndex(int depth, String path) {
        // add for report
        if (path.lastIndexOf(":") == -1) {
            return new Integer(path).intValue();
        }

        //
        return Integer.parseInt((String) convertPath(path).get(depth));
    }

    public String setChildIndex(int depth, String path, int newChildIndex) {
        ArrayList result = convertPath(path);

        // add for report
        if (result.size() == 1) {
            return "0";
        }

        //
        result.set(depth, "" + newChildIndex);

        return convertPath(result);
    }

    public int getDepth(String path) {
        return convertPath(path).size() - 1;
    }

    public String convertPath(ArrayList path) {
        StringBuffer buf = new StringBuffer();

        for (Iterator i = path.iterator(); i.hasNext();) {
            buf.append(i.next());

            if (i.hasNext()) {
                buf.append(":");
            }
        }

        return buf.toString();
    }

    public ArrayList convertPath(String path) {
        ArrayList result = new ArrayList();
        StringTokenizer st = new StringTokenizer(path, ":");

        while (st.hasMoreTokens()) {
            result.add(st.nextToken());
        }

        return result;
    }

    public String[] cutHead(String nodePath) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(nodePath.substring(0, nodePath.indexOf(":")));
        buffer.append(" ");
        buffer.append(nodePath.substring(nodePath.indexOf(":") + 1));

        return buffer.toString().split(" ");
    }

    public String incHeadPos(String nodePath) {
        String[] path = cutHead(nodePath);

        return (Integer.parseInt(path[0]) + 1) + path[1];
    }

    public String decHeadPos(String nodePath) {
        String[] path = cutHead(nodePath);

        return (Integer.parseInt(path[0]) - 1) + path[1];
    }
}
