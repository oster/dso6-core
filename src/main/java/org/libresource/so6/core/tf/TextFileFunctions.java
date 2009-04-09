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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.command.Command;
import org.libresource.so6.core.command.Id;
import org.libresource.so6.core.command.Macro;
import org.libresource.so6.core.command.NoOp;
import org.libresource.so6.core.command.text.AddBlock;
import org.libresource.so6.core.command.text.DelBlock;
import org.libresource.so6.core.command.text.UpdateTextFile;
import org.libresource.so6.core.engine.util.ObjectCloner;

/**
 * @author Ecoo Team Loria
 */
public class TextFileFunctions {
	public final static String CONFLICT_BLOC_START = ">>>> CONFLICT >>>> ";
	public final static String CONFLICT_BLOC_END = "<<<< CONFLICT <<<<";
	public final static String CONFLICT_BLOC_SPLIT = "==== ";
	public final static String CONFLICT_BLOC_PADDING = ">";
	public final static String CONFLICT_BLOC_ORIGINE_SEPARATOR = " / ";
	public final static String CONFLICT_TYPE_ADD_ADD = "ADD - ADD";
	public final static String CONFLICT_TYPE_ADD_REMOVE = "ADD - REMOVE";
	public final static String CONFLICT_TYPE_REMOVE_ADD = "REMOVE - ADD";
	public final static int ADD_ADD = 1;
	public final static int ADD_REMOVE = 2;
	public final static int REMOVE_ADD = 3;
	private WsConnection ws;

	public TextFileFunctions(WsConnection ws) {
		this.ws = ws;
	}

	public static String[] getOrigineBlocNames(String splitLine) {
		return splitLine.substring(CONFLICT_BLOC_SPLIT.length()).split(CONFLICT_BLOC_ORIGINE_SEPARATOR);
	}

	public static int getConflictType(String firstConflictLine) {
		if (firstConflictLine.endsWith(CONFLICT_TYPE_ADD_ADD)) {
			return ADD_ADD;
		}

		if (firstConflictLine.endsWith(CONFLICT_TYPE_ADD_REMOVE)) {
			return ADD_REMOVE;
		}

		if (firstConflictLine.endsWith(CONFLICT_TYPE_REMOVE_ADD)) {
			return REMOVE_ADD;
		}

		return -1;
	}

	public Command transp(UpdateTextFile c1, UpdateTextFile c2) throws Exception {
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
					m = this.getClass().getMethod("tf",
							new Class[] { c1.getClass().getSuperclass(), c2.getClass().getSuperclass() });
				}
			}
		}

		res = (Command) m.invoke(this, new Object[] { c1, c2 });

		return res;
	}

	/*
	 * Take care of Text commands - AddBlock - DelBlock
	 */

	/**
	 * AddBlock
	 */
	public Command tf(AddBlock c1, AddBlock c2) throws Exception {
		int s1 = c1.getInsertPoint();
		//int l1 = c1.getSize();
		int s2 = c2.getInsertPoint();
		int l2 = c2.getSize();

		if (s1 < s2) {
			return c1;
		} else {
			// s1 >= s2
			if (s1 > s2) {
				AddBlock c = (AddBlock) ObjectCloner.deepCopy(c1);
				c.slide(l2);

				return c;
			} else {
				// s1 == s2
				if (c1.equals(c2)) {
					return new Id(c1, ws);
				} else {
					// o1 == o2

					// generate DelBlock (to remove block added before)
					DelBlock d = new DelBlock(c2, ws);

					// generate AddBlock (add block merging addblocks from c1 and c2);
					List<String> contents = new ArrayList<String>();
					AddBlock cBefore = (AddBlock) c1.max(c2);
					AddBlock cAfter = (AddBlock) c1.min(c2);
					assert (!cBefore.equals(cAfter)) : "wizzz";
					contents.add(CONFLICT_BLOC_START + CONFLICT_TYPE_ADD_ADD); // +
					// "\n");

					for (Iterator<String> i = cBefore.getContent().iterator(); i.hasNext();) {
						contents.add(CONFLICT_BLOC_PADDING + ((String) i.next()));
					}

					contents.add(CONFLICT_BLOC_SPLIT + cBefore.getWsName() + CONFLICT_BLOC_ORIGINE_SEPARATOR
							+ cAfter.getWsName()); // +
					// "\n");

					for (Iterator<String> i = cAfter.getContent().iterator(); i.hasNext();) {
						contents.add(CONFLICT_BLOC_PADDING + ((String) i.next()));
					}

					contents.add(CONFLICT_BLOC_END); // + "\n");

					AddBlock a = new AddBlock(c1.getPath(), ws, c1.getInsertPoint(), contents);
					a.setConflict(true);

					Macro m = new Macro(c1, ws);
					m.setCommand(d, 1);
					m.setCommand(a, 2);

					return m;
				}
			}
		}
	}

	public Command tf(AddBlock c1, DelBlock c2) throws Exception {
		int s1 = c1.getInsertPoint();
		int s2 = c2.getDeletePoint();
		int e2 = (s2 + c2.getSize()) - 1;

		if (s1 <= s2) { // PATCH

			return c1;
		} else { // s1>=s2

			if (s1 > e2) {
				AddBlock clone = (AddBlock) ObjectCloner.deepCopy(c1);
				clone.slide(-c2.getSize());

				return clone;
			} else { // s1>=s2 && s1<=e2

				// conflit (le point d'insertion a ete efface)
				// generate HunkDel (remove block added before)
				List<String> contents = new ArrayList<String>();
				contents.add(CONFLICT_BLOC_START + CONFLICT_TYPE_REMOVE_ADD); // +
				// "\n");

				for (Iterator<String> i = c2.getOldContent().iterator(); i.hasNext();) {
					contents.add(CONFLICT_BLOC_PADDING + ((String) i.next()));
				}

				contents.add(CONFLICT_BLOC_SPLIT + c2.getWsName() + CONFLICT_BLOC_ORIGINE_SEPARATOR + c1.getWsName()); // +
				// "\n");

				for (Iterator<String> i = c1.getContent().iterator(); i.hasNext();) {
					contents.add(CONFLICT_BLOC_PADDING + ((String) i.next()));
				}

				contents.add(CONFLICT_BLOC_END); // + "\n");

				AddBlock a = new AddBlock(c2.getPath(), ws, c2.getDeletePoint(), contents);

				// -1 ???
				a.setTicket(c1.getTicket());
				a.setConflict(true);

				return a;
			}
		}
	}

	/**
	 * DelBlock
	 */
	public Command tf(DelBlock c1, DelBlock c2) throws Exception {
		int s1 = c1.getDeletePoint();
		int e1 = (s1 + c1.getSize()) - 1;
		int s2 = c2.getDeletePoint();
		int e2 = (s2 + c2.getSize()) - 1;
		DelBlock clone = (DelBlock) ObjectCloner.deepCopy(c1);

		if (s1 < s2) { // block de c1 devant celui de c2

			if (e1 < s2) { // blocks disjoints

				// return DelBlock[s1;e1]
				return c1;
			} else { // e1 >= s2

				if (e1 < e2) {
					// return DelBlock[s1;s2[
					clone.cutTailFrom(e1 - s2 + 1);

					return clone;
				} else { // e1 >= e2

					// return DelBlock[s1;s2[;DelBlock]e2;e1] (slide le deuxieme
					// block)
					clone.cutTailFrom(e1 - s2 + 1);

					if (e1 == e2) {
						return clone;
					} else {
						DelBlock clone2 = (DelBlock) ObjectCloner.deepCopy(c1);
						clone2.cutHeadTo(s2 - s1);
						clone2.cutTailFrom(e1 - e2);
						clone2.slide(-(s2 - s1));
						assert (clone2.getSize() == c2.getSize()) : "die bitch...\n" + "clone2:" + clone2.toString()
								+ "\n" + "c2:" + c2.toString();

						Command cmd = null;

						if (clone2.getOldContent().equals(c2.getOldContent())) {
							cmd = new Id(clone2, ws);
						} else {
							cmd = new NoOp(clone2, ws);
						}

						DelBlock clone3 = (DelBlock) ObjectCloner.deepCopy(c1);
						clone3.cutHeadTo(e2 - s1 + 1);
						clone3.slide(-(e2 - s1 + 1));

						Macro m1 = new Macro(c1, ws);
						m1.setCommand(cmd, 1);
						m1.setCommand(clone3, 2);

						Macro m = new Macro(c1, ws);
						m.setCommand(clone, 1);
						m.setCommand(m1, 2);

						return m;
					}
				}
			}
		} else { // s1 >= s2

			if (s1 > e2) {
				// // block de c2 devant celui de c1 et blocks disjoints
				// return DelBlock[s1+c2.getSize();e1+c2.getSize()];
				clone.slide(-c2.getSize());

				return clone;
			} else { // s1 <= e2

				if (e1 < e2) {
					// return DelBlock[] = NoOp();
					DelBlock clone2 = (DelBlock) ObjectCloner.deepCopy(c2);
					clone2.cutHeadTo(s1 - s2);
					clone2.cutTailFrom(e2 - e1);
					clone2.slide(-(s1 - s2));
					assert (clone2.getSize() == c1.getSize()) : "turlu...\n" + "clone2:" + clone2.toString() + "\n"
							+ "c1:" + c1.toString();

					if (clone2.getOldContent().equals(c1.getOldContent())) {
						return new Id(clone2, ws);
					} else {
						return new NoOp(clone2, ws);
					}

					// return new NoOp(c1);
				} else { // e1 >= e2

					if (e1 == e2) { // c1 equals c2

						return new Id(c1, ws);
					} else { // e1>e2

						// return DelBlock[e2;e1]
						clone.cutHeadTo(e2 - s1 + 1);

						// clone.slide(-c2.getSize()+(e2-s1+1));
						clone.slide(-c2.getSize());

						return clone;
					}
				}
			}
		}
	}

	public Command tf(DelBlock c1, AddBlock c2) throws Exception {
		int s1 = c1.getDeletePoint();
		int e1 = (s1 + c1.getSize()) - 1;
		int s2 = c2.getInsertPoint();

		if (s1 >= s2) {
			DelBlock clone = (DelBlock) ObjectCloner.deepCopy(c1);
			clone.slide(c2.getSize());

			return clone;
		} else { // s1<=s2

			if (e1 < s2) {
				return c1;
			} else {
				// conflit (le point d'insertion aurait pu etre efface)
				// generate DelBlock (to remove block added before)
				DelBlock d = new DelBlock(c2, ws);

				// conflit (le point d'insertion a ete efface)
				List<String> contents = new ArrayList<String>();
				contents.add(CONFLICT_BLOC_START + CONFLICT_TYPE_REMOVE_ADD); // +
				// "\n");

				for (Iterator<String> i = c1.getOldContent().iterator(); i.hasNext();) {
					contents.add(CONFLICT_BLOC_PADDING + ((String) i.next()));
				}

				contents.add(CONFLICT_BLOC_SPLIT + c1.getWsName() + CONFLICT_BLOC_ORIGINE_SEPARATOR + c2.getWsName()); // +
				// "\n");

				for (Iterator<String> i = c2.getContent().iterator(); i.hasNext();) {
					contents.add(CONFLICT_BLOC_PADDING + ((String) i.next()));
				}

				contents.add(CONFLICT_BLOC_END); // + "\n");

				AddBlock a = new AddBlock(c2.getPath(), ws, c1.getDeletePoint(), contents);
				a.setConflict(true);

				Macro m = new Macro(c1, ws);
				Macro m2 = new Macro(c1, ws);
				m.setCommand(m2, 1);
				m2.setCommand(d, 1);
				m2.setCommand(c1, 2);
				m.setCommand(a, 2);
				m.setTicket(c1.getTicket());

				return m;
			}
		}
	}
}
