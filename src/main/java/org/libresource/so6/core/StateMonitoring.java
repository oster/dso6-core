package org.libresource.so6.core;

import java.util.Observer;

public class StateMonitoring {

	private static StateMonitoring instance = new StateMonitoring();

	public static StateMonitoring getInstance() {
		return StateMonitoring.instance;
	}
	
	public void setXMLMonitoringStartSubCall(int i, String string) {
	}

	public void setXMLMonitoringEndSubCall() {
	}

	public void setXMLMonitoringStartAction(String string) {
	}

	public void setXMLMonitoringEndAction(String string) {
	}

	public void setXMLMonitoringComment(String string) {
	}

	public void setXMLMonitoringComment(boolean b, String string) {
	}

	public void setXMLMonitoringState(int i, int nbPatchToDownload, int currentPatchNumber, String string) {
	}

	public void setXMLMonitoringState(long fromTicket, long toTicket, long ticket, String string) {
	}

	public void setXMLMonitoringStartCriticalPart() {
	}

	public int getCurrentCriticalPartNumber() {
		return 0;
	}

	public void addObserver(Observer threadKiller) {
	}

}
