package fr.loria.ecoo.dso6.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.libresource.so6.core.Workspace;
import org.libresource.so6.core.WsConnection;
import org.libresource.so6.core.client.ClientI;

public class Commit {

	private static String QUEUES_PROPERTIES_PATH = System.getProperty("user.home") + File.separator
	+ ".dso6-queues.properties";

	private Properties clientProperties;

	public Commit(String endpointURI, String queueId, String capabilityId) {
		this.clientProperties = new Properties();
		this.clientProperties.put(BasicClientImpl.DSO6_ENDPOINT_URI, endpointURI);
		this.clientProperties.put(ClientI.SO6_QUEUE_ID, queueId);
		this.clientProperties.put(BasicClientImpl.DSO6_COMMIT_CAPABILITY, capabilityId);
		//this.clientProperties.put(ClientI.SO6_XML_DETECT, "false");
	}
	
	
	public void perform() {
		try {
			// init basePath
			String basePath;
			Properties queuesDatabase = loadQueuesDatabase();
			String queueId = this.clientProperties.getProperty(ClientI.SO6_QUEUE_ID);
			if (queuesDatabase.containsKey(queueId)) {
				basePath = queuesDatabase.getProperty(queueId);
			} else {
				throw new NotYetCheckedOut();
			}

			Workspace ws = null;
			WsConnection wsc = null;
			try {
				ws = new Workspace(basePath);
				wsc = ws.getConnection(null);
				
				if (wsc.getProperty(BasicClientImpl.DSO6_COMMIT_CAPABILITY) == null) {
					wsc.setProperty(BasicClientImpl.DSO6_COMMIT_CAPABILITY, this.clientProperties.getProperty(BasicClientImpl.DSO6_COMMIT_CAPABILITY));
				}
				
				// ask the user for commit message
				wsc.commit("HERE IS THE COMMIT MESSAGE");
			} catch (IOException ex) {
				throw new NotYetCheckedOut(ex);
			}
			System.out.println(wsc.getReport());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotYetCheckedOut e) {
			e.printStackTrace();			
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public static void main(String[] args) {
		// first-param : endPointUI
		// first-param : queueId
		// second-param : capabilityId
    		
		Commit action = new Commit(args[0], args[1], args[2]);
		
		//String testEndPointURI = "http://localhost:8888/dso6/";
		//String testQueueId = "86b4b7ad1343fedf8f02fa5451edf487";
		//String testUpdateCapabilityId = "e4ec4e3a370e74538ad396ab67339555";
		//String testCommitCapabilityId = "f2972864531caaa41aae857f4a60b75f";	
		//Commit action = new Commit(testEndPointURI, testQueueId, testCommitCapabilityId);
		action.perform();
	}

}
