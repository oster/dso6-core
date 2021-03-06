
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
package org.libresource.so6.core.engine;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import jlibdiff.Diff;
import jlibdiff.Hunk;
import jlibdiff.HunkAdd;
import jlibdiff.HunkChange;
import jlibdiff.HunkDel;

import org.libresource.so6.core.FileLockedException;
import org.libresource.so6.core.ApplicationStatus;
import org.libresource.so6.core.Workspace;
import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.command.Command;
import org.libresource.so6.core.command.fs.AddBinaryFile;
import org.libresource.so6.core.command.fs.AddDir;
import org.libresource.so6.core.command.fs.Remove;
import org.libresource.so6.core.command.fs.UpdateBinaryFile;
import org.libresource.so6.core.command.text.AddBlock;
import org.libresource.so6.core.command.text.AddTxtFile;
import org.libresource.so6.core.command.text.DelBlock;
import org.libresource.so6.core.engine.util.FileUtils;
import fr.loria.ecoo.dso6.core.InfoWindow;

/**
 * FileParser : Detect file modification...
 * 
 * Responsibility : - This is a simple modification detector for a file system.
 * computes the vector of operations that make the transition from the last
 * state (stored in a local db) to the actual state. - FileParser has the
 * Responsibility to manage DBMarks (update it during synchronization action)
 * 
 * Collaboration: - give its operation to the operation integrator component.
 * 
 * @author molli
 */
public class FileParser {
	private WsConnection ws;
	private FilterIgnoreFile fif;
	private int walked = 0;
	private long size = 0;
	private WsConnection workspace;
	private int nbFileToWalk;

	public FileParser(WsConnection ws) throws Exception {
		this.ws = ws;

		String dir = FileUtils.createTmpDir().getPath();
		File f = new File(dir);
		f.mkdir();
	}

	public WsConnection getWorkspace() {
		return workspace;
	}

	public void setWorkspace(WsConnection workspace) {
		this.workspace = workspace;
	}

	private String getRelPath(String path) {
		String p1 = (new File(ws.getPath())).getAbsolutePath();
		String p2 = ((new File(path))).getAbsolutePath();
		assert (p2.startsWith(p1)) : "(" + p2 + ") does not start with (" + p1 + ")";

		// +1 to remove the first / of the command path
		if (p1.equals(p2)) {
			return "";
		} else {
			if (p2.startsWith(p1)) {
				return p2.substring(p1.length() + 1).replaceAll("\\\\", "/");
			}

			return path.replaceAll("\\\\", "/");
		}
	}

	public void compute(OpVector log) throws Exception {
		// StateMonitoring.getInstance().setXMLMonitoringStartSubCall(4, "");
		// StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1,
		// "Check number of file to walk");
		ApplicationStatus.getInstance().subTaskStarted(ApplicationStatus.SubTask.WALKING_FOR_TYPE_DETECTION);
		ws.getDBType().updateFromWalk(ws.getPath(), ws.getXmlAutoDetection());
		nbFileToWalk = ws.getDBType().getNbEntry();

		List<String> walkedlist = new ArrayList<String>();
		fif = new FilterIgnoreFile(ws);
		ApplicationStatus.getInstance().subTaskTerminated(ApplicationStatus.SubTask.WALKING_FOR_TYPE_DETECTION);
		// StateMonitoring.getInstance().setXMLMonitoringEndSubCall();

		// StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1,
		// "Check local operation");
		ApplicationStatus.getInstance().subTaskStarted(ApplicationStatus.SubTask.WALKING_FOR_CHANGE_DETECTION);
		File root = new File(ws.getPath());

		// check if important data is locked
		File[] children = root.listFiles();

		if (FileUtils.isLocked(ws.getRefCopyPath())) {
			throw new FileLockedException(ws.getRefCopyPath());
		}

		for (int i = 0; i < children.length; i++) {
			if (children[i].getName().equals(Workspace.SO6PREFIX)) {
				continue;
			}

			if (FileUtils.isLocked(children[i])) {
				throw new FileLockedException(children[i]);
			}
		}

		// Start walking to detect local op
		this.walk(root, walkedlist, log);
		// StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
		ApplicationStatus.getInstance().subTaskTerminated(
				ApplicationStatus.SubTask.WALKING_FOR_CHANGE_DETECTION);

		// StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1,
		// "Detecting local remove");
		ApplicationStatus.getInstance().subTaskStarted(ApplicationStatus.SubTask.DETECTING_REMOVE);
		List<String> removed = detectRemoved(walkedlist);
		ApplicationStatus.getInstance().subTaskTerminated(ApplicationStatus.SubTask.DETECTING_REMOVE);

		for (int i = 0; i < removed.size(); i++) {
			Command cmd = new Remove(removed.get(i), ws);
			log.add(cmd);
		}
		// StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
		// StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
	}

	public void compute(OpVector log, InfoWindow iw) throws Exception {
		// StateMonitoring.getInstance().setXMLMonitoringStartSubCall(4, "");
		// StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1,
		// "Check number of file to walk");
		ApplicationStatus.getInstance().subTaskStarted(ApplicationStatus.SubTask.WALKING_FOR_TYPE_DETECTION);
		ws.getDBType().updateFromWalk(ws.getPath(), ws.getXmlAutoDetection());
		nbFileToWalk = ws.getDBType().getNbEntry();

		List<String> walkedlist = new ArrayList<String>();
		fif = new FilterIgnoreFile(ws);
		ApplicationStatus.getInstance().subTaskTerminated(ApplicationStatus.SubTask.WALKING_FOR_TYPE_DETECTION);
		iw.updateProgressBarUnderStep(40);
		// StateMonitoring.getInstance().setXMLMonitoringEndSubCall();

		// StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1,
		// "Check local operation");
		ApplicationStatus.getInstance().subTaskStarted(ApplicationStatus.SubTask.WALKING_FOR_CHANGE_DETECTION);
		File root = new File(ws.getPath());

		// check if important data is locked
		File[] children = root.listFiles();

		if (FileUtils.isLocked(ws.getRefCopyPath())) {
			throw new FileLockedException(ws.getRefCopyPath());
		}

		for (int i = 0; i < children.length; i++) {
			if (children[i].getName().equals(Workspace.SO6PREFIX)) {
				continue;
			}

			if (FileUtils.isLocked(children[i])) {
				throw new FileLockedException(children[i]);
			}
		}

		// Start walking to detect local op
		this.walk(root, walkedlist, log);
		// StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
		ApplicationStatus.getInstance().subTaskTerminated(
				ApplicationStatus.SubTask.WALKING_FOR_CHANGE_DETECTION);
		iw.updateProgressBarUnderStep(80);
		// StateMonitoring.getInstance().setXMLMonitoringStartSubCall(1,
		// "Detecting local remove");
		ApplicationStatus.getInstance().subTaskStarted(ApplicationStatus.SubTask.DETECTING_REMOVE);
		List<String> removed = detectRemoved(walkedlist);
		ApplicationStatus.getInstance().subTaskTerminated(ApplicationStatus.SubTask.DETECTING_REMOVE);

		for (int i = 0; i < removed.size(); i++) {
			Command cmd = new Remove(removed.get(i), ws);
			log.add(cmd);
		}
		// StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
		// StateMonitoring.getInstance().setXMLMonitoringEndSubCall();
		iw.updateProgressBarUnderStep(100);
	}

	private List<String> detectRemoved(List<String> l) {
		List<String> result = new ArrayList<String>();
		Iterator<String> li = ws.getRefCopy().getElements();
		int entriesCount = ws.getRefCopy().getDBType().getNbEntry();
		int current = 0;

		while (li.hasNext()) {
			current++;
			// StateMonitoring.getInstance().setXMLMonitoringState(0, to,
			// current, "");
			ApplicationStatus.getInstance().taskOnProgress(0, entriesCount, current);

			String path = li.next();

			if (!(l.contains(path))) {
				result.add(path);
				// StateMonitoring.getInstance().setXMLMonitoringComment(true,
				// "Find local remove: " + path);
			}
		}

		Collections.sort(result);
		Collections.reverse(result);

		return result;
	}

	public void diff(String path, OpVector log) throws IOException, SQLException, Exception {
		if (new File(ws.getPath() + File.separator + path).isDirectory()) {
			return;
		}

		if (!(ws.getRefCopy().exists(path))) {
			// New File
			throw new RuntimeException("Should never happen");
		}

		// File previously synchronized
		String lastFile = ws.getRefCopy().getPath(path);
		String newFile = ws.getPath() + File.separator + path;

		/*
		 * TODO: Reintegrate XML support as a plugin feature? if
		 * (ws.getRefCopy().getType(path) == DBType.TYPE_FILE_XML) { // Manage
		 * XML XyDiff d = new XyDiff(lastFile, newFile); Collection cmds =
		 * XmlUtil.convertToSo6Commands(ws, path, d.diff().getXMLCommand());
		 * 
		 * //System.out.println("--> xml cmds"); for (Iterator i =
		 * cmds.iterator(); i.hasNext();) { Command cmd = (Command) i.next();
		 * 
		 * //System.out.println(cmd.toString()); log.add(cmd); }
		 * 
		 * //System.out.println("<-- xml cmds"); } else {
		 */
		// Manage Txt
		Diff d = new Diff();
		d.diffFile(lastFile, newFile);

		for (Iterator<Hunk> i = d.iterator(); i.hasNext();) {
			Hunk h = i.next();

			if (h instanceof HunkAdd) {
				Command cmd = new AddBlock(path, ws, (HunkAdd) h);
				log.add(cmd);
			} else if (h instanceof HunkDel) {
				Command cmd = new DelBlock(path, ws, (HunkDel) h);
				log.add(cmd);
			} else if (h instanceof HunkChange) {
				throw new RuntimeException("HunkChange should not be detected, check the jlibdiff configuration");

				// HunkChange hc = (HunkChange) h;
				// Logger.getLogger("fileparser.log").info(
				// "hunkChange(ld1:" + hc.getLD1() + ",lf1:" + hc.getLF1() +
				// ",ld2:" + hc.getLD2() + ",lf2:" + hc.getLF2() + ")");
				// HunkDel hd = hc.getHunkDel();
				// HunkAdd ha = hc.getHunkAdd();
				// Command cmd = new DelBlock(path, ws, ((HunkChange)
				// h).getHunkDel());
				// log.add(cmd);
				// cmd = new AddBlock(path, ws, ((HunkChange)
				// h).getHunkAdd());
				// log.add(cmd);
			}
			// }
		}

		System.gc();
	}

	// traverse the file system starting from f // and build the vector of
	// operations
	private void walk(File f, List<String> wl, OpVector log) throws java.io.IOException, SQLException, Exception {
		if (!(f.exists())) {
			throw new java.io.IOException(f.getPath() + " not exists");
		}

		if (!(f.isDirectory()) && !(f.isFile())) {
			Logger.getLogger("ui.log").warning("unmanaged file type:" + f.getPath());

			return;
		}

		// ignore dataFiles !!
		if (f.getName().equals(Workspace.SO6PREFIX)) {
			return;
		}

		String relpath = getRelPath(f.getPath()); // get the relative path...
		Logger.getLogger("ui.log").severe("examining:" + relpath);
		wl.add(relpath);
		walked++;
		size = size + f.length();

		// monitoring xml
		// StateMonitoring.getInstance().setXMLMonitoringState(0, nbFileToWalk,
		// walked, "walked... (" + relpath + ")");
		ApplicationStatus.getInstance().taskOnProgress(0, nbFileToWalk, walked);

		boolean filePreviouslySynchronized = ws.getRefCopy().exists(relpath);
		boolean typeHasChangedDirToFile = ws.getDBType().typeHasChanged(relpath, f);
		boolean fileTypeHasChanged = ws.getRefCopy().getType(relpath) != ws.getDBType().getType(relpath);
		int fileType = -1;

		if ((!filePreviouslySynchronized) || typeHasChangedDirToFile || fileTypeHasChanged) { // new

			// file... or type has changed
			if (filePreviouslySynchronized && (typeHasChangedDirToFile || fileTypeHasChanged)) { // Type

				// has
				// changed
				log.add(new Remove(relpath, ws));

				if (typeHasChangedDirToFile) {
					ws.getDBType().remove(relpath);
				} else {
					fileType = ws.getDBType().getType(relpath);
					ws.getDBType().remove(relpath);
				}
			}

			if (fileType == -1) {
				// Try to set it from the local db type
				fileType = ws.getDBType().getType(relpath);
			}

			if (fileType == -1) {
				// compute the type
				fileType = ws.getDBType().computeType(f, ws.getXmlAutoDetection());
			}

			switch (fileType) {
			case DBType.TYPE_DIR:
				log.add(new AddDir(relpath, ws));
				ws.getDBType().add(relpath, DBType.TYPE_DIR);

				break;

			case DBType.TYPE_FILE_BIN:
				log.add(new AddBinaryFile(relpath, ws, new File(ws.getAttachFilePath())));
				ws.getDBType().add(relpath, DBType.TYPE_FILE_BIN);

				break;

			case DBType.TYPE_FILE_TXT:
				log.add(new AddTxtFile(relpath, ws, new File(ws.getAttachFilePath())));
				ws.getDBType().add(relpath, DBType.TYPE_FILE_TXT);

				break;
			/*
			 * TODO: Reintegrate XML support as a plugin feature? case
			 * DBType.TYPE_FILE_XML: ws.getDBType().add(relpath,
			 * DBType.TYPE_FILE_XML);
			 * 
			 * // Manage Xml String newFile = ws.getPath() + File.separator +
			 * relpath;
			 * 
			 * // normalize the document //XmlUtil.normalizeDocument(newFile);
			 * log.add(new AddXmlFile(relpath, ws, new
			 * File(ws.getAttachFilePath())));
			 * 
			 * break;
			 */
			default:
				throw new Exception("unmanaged db type: " + fileType + " file: " + f.getAbsolutePath());
			}
		} else { // previously synchronized file

			// Type has not changed, just update...
			switch (ws.getRefCopy().getType(relpath)) {
			case DBType.TYPE_DIR:
				break;

			case DBType.TYPE_FILE_BIN:

				if (!FileUtils.compareBinFile(f, new File(ws.getRefCopy().getPath(relpath)))) {
					log.add(new UpdateBinaryFile(relpath, ws, new File(ws.getAttachFilePath())));
				}

				break;

			case DBType.TYPE_FILE_TXT:
			case DBType.TYPE_FILE_XML:

				if (!FileUtils.compareBinFile(f, new File(ws.getRefCopy().getPath(relpath)))) {
					this.diff(relpath, log);
				}

				break;

			default:
				throw new Exception("Unmanaged type : " + ws.getRefCopy().getType(relpath));
			}
		}

		// recurse walking to seek children changes
		if (f.isDirectory()) { // recurse walking to detect new children

			File[] subs = f.listFiles(fif); // filtered !!

			for (int i = 0; i < subs.length; i++) {
				walk(subs[i], wl, log);
			}
		}
	}
}
