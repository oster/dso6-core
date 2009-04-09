package fr.loria.ecoo.dso6.core;

import java.io.IOException;

@SuppressWarnings("serial")
public class NotYetCheckedOutException extends Exception {

	public NotYetCheckedOutException() {
		super();
	}
	
	public NotYetCheckedOutException(IOException ex) {
		super(ex);
	}

}
