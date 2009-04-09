package fr.loria.ecoo.dso6.core;

import java.io.IOException;

@SuppressWarnings("serial")
public class NotYetCheckedOut extends Exception {

	public NotYetCheckedOut() {
		super();
	}
	
	public NotYetCheckedOut(IOException ex) {
		super(ex);
	}

}
