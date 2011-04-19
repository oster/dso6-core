package fr.loria.ecoo.dso6.core;

import java.util.List;
import java.util.ArrayList;

import org.libresource.so6.core.engine.util.Base64;

/**
 *
 */
public class QPVArray implements java.io.Serializable {
	private ArrayList<QueuePropertyValue> ary = new ArrayList<QueuePropertyValue>();

	/**
	 *
	 */
	public QPVArray() {
	}

	/**
	 *
	 */
	public String toString() {
		return Base64.encodeObject(this);
	}

	/**
	 *
	 */
	public static QPVArray fromString(String str) {
		if(str == null)
			return null;

		return (QPVArray)Base64.decodeToObject(str);
	}

	/**
	 *
	 */
	public void add(QueuePropertyValue qpv) {
		if(qpv != null)
			ary.add(qpv);
	}

	/**
	 *
	 */
	public QueuePropertyValue get(int i) {
		return ary.get(i);
	}

	/**
	 *
	 */
	public int size() {
		return ary.size();
	}

	/**
	 *
	 */
	public String[] toStringArray() {
		String str[] = new String[ary.size()];

		for(int i = 0; i < ary.size(); ++i) {
			str[i] = ary.get(i).getPath() + " " + "[" + ary.get(i).getName() + "]";
		}

		return str;
	}
}
