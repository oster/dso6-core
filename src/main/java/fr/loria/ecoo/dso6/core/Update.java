package fr.loria.ecoo.dso6.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.swing.*;

import org.libresource.so6.core.Workspace;
import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.client.ClientI;

public class Update {

	private static String QUEUES_PROPERTIES_PATH = System.getProperty("user.home") + File.separator
			+ ".dso6-queues.properties";
	private static String CLIENT_CLASSNAME = "fr.loria.ecoo.dso6.core.BasicClientImpl";

	private Properties clientProperties;

	public Update(String endpointURI, String queueId, String capabilityId) {
		this.clientProperties = new Properties();
		this.clientProperties.put(BasicClientImpl.DSO6_ENDPOINT_URI, endpointURI);
		this.clientProperties.put(ClientI.SO6_QUEUE_ID, queueId);
		this.clientProperties.put(BasicClientImpl.DSO6_UPDATE_CAPABILITY, capabilityId);
		this.clientProperties.put(ClientI.SO6_XML_DETECT, "false");
	}

	public void perform() {
		try {
			UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch(Throwable t) {
		}

		try {
			String basePath = "", name = "";
			Properties queuesDatabase = loadQueuesDatabase();
			String queueId = this.clientProperties.getProperty(ClientI.SO6_QUEUE_ID);
			CheckoutWindow aw = null;

			if(queuesDatabase.containsKey(queueId)) {
				// if queue contained in queues database
				QueuePropertyValue qpv = QueuePropertyValue.fromString(queuesDatabase.getProperty(queueId));

				basePath = qpv.getPath();
				name = qpv.getName();
			} else {
				// otherwise, ask the user and save queues database
				aw = new CheckoutWindow();
					synchronized(aw.lock) {
					aw.lock.wait();
				}
				if(aw.flag) {
					name = aw.author.getText();
					basePath = aw.path.getText();

					queuesDatabase.put(queueId, new QueuePropertyValue(name, basePath).toString());
					storeQueuesDatabase(queuesDatabase);
				}
			}

			if(aw == null || aw.flag) {
				Workspace ws = null;
				try {
					ws = new Workspace(basePath);
				} catch (IOException ex) {
					ws = Workspace.createWorkspace(basePath);
					ws.createConnection(clientProperties, CLIENT_CLASSNAME, name);
				}
				WsConnection wsc = ws.getConnection(null);
				InfoWindow iw = new InfoWindow();
				iw.report.setText("Update in progress...");
				wsc.update(name, iw);
				System.out.println(wsc.getReport());
				iw.report.setText("Update done.\n" + wsc.getReport());
				iw.enableClose();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void storeQueuesDatabase(Properties queuesDatabase) throws FileNotFoundException, IOException {
		OutputStream fos;
		fos = new FileOutputStream(QUEUES_PROPERTIES_PATH);
		queuesDatabase.store(fos, " DooSo6 Queues Database");
	}

	public static Properties loadQueuesDatabase() throws FileNotFoundException, IOException {
		Properties queuesDatabase = new Properties();
		File queuesPropertiesFile = new File(QUEUES_PROPERTIES_PATH);
		if (queuesPropertiesFile.exists()) {
			InputStream fis;
			fis = new FileInputStream(queuesPropertiesFile);
			queuesDatabase.load(fis);
			fis.close();
		}
		return queuesDatabase;
	}

	/*
	public static boolean workspaceExist(String basePath) {
		try {
			new Workspace(basePath);
		} catch (IOException ex) {
			return false;
		}
		return true;
	}
	*/

	public static void main(String[] args) {
		// first-param : endPointUI
		// first-param : queueId
		// second-param : capabilityId

		Update action = new Update(args[0], args[1], args[2]);

		//String testEndPointURI = "http://localhost:8888/dso6/";
		//String testQueueId = "86b4b7ad1343fedf8f02fa5451edf487";
		//String testUpdateCapabilityId = "e4ec4e3a370e74538ad396ab67339555";
		//String testCommitCapabilityId = "f2972864531caaa41aae857f4a60b75f";
		//Update action = new Update(testEndPointURI, testQueueId, testUpdateCapabilityId);
		action.perform();
	}

}
