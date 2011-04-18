package fr.loria.ecoo.dso6.core;

import java.util.List;
import java.util.ArrayList;

/**
 *
 */
public class QPVArray {
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
		String str = "";

		for(QueuePropertyValue v : ary) {
			str = str + v.toString().replaceAll("@", "\\\\@") + "@";
		}

		return str.substring(0, str.length() - 1);
	}

	/**
	 *
	 */
	public static QPVArray fromString(String str) {
		if(str == null)
			return null;

		QPVArray ary = new QPVArray();

		int last_i = 0;
		for(int i = 1; i < str.length(); i++) {
			if((str.charAt(i) == '@' && str.charAt(i - 1) != '\\') || i == str.length() - 1) {
				ary.add(QueuePropertyValue.fromString(str.substring(last_i, i + ((i == str.length() - 1) ? 1 : 0)).replaceAll("\\\\@", "@")));
				last_i = i + 1;
			}
		}

		return ary;
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
			str[i] = ary.get(i).toString();
		}

		return str;
	}
}
