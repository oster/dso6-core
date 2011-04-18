package fr.loria.ecoo.dso6.core;

public class Checkout extends Update {
	public Checkout(String endpointURI, String queueId, String capabilityId) {
		super(endpointURI, queueId, capabilityId, true);
	}

	public static void main(String[] args) {
		// first-param : endPointUI
		// first-param : queueId
		// second-param : capabilityId

		Checkout action = new Checkout(args[0], args[1], args[2]);

		//String testEndPointURI = "http://localhost:8888/dso6/";
		//String testQueueId = "86b4b7ad1343fedf8f02fa5451edf487";
		//String testUpdateCapabilityId = "e4ec4e3a370e74538ad396ab67339555";
		//String testCommitCapabilityId = "f2972864531caaa41aae857f4a60b75f";
		//Update action = new Update(testEndPointURI, testQueueId, testUpdateCapabilityId);
		action.perform();
	}
}

