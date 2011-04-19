package fr.loria.ecoo.dso6.core;

import org.libresource.so6.core.engine.util.Base64;

/**
 *
 */
public class QueuePropertyValue implements java.io.Serializable {
	private String name;
	private String path;

	/**
	 *
	 */
	public QueuePropertyValue(String name, String path) {
		this.name = name == null ? "" : name;
		this.path = path == null ? "" : path;
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
	public static QueuePropertyValue fromString(String str) {
		if(str == null)
			return null;

		return (QueuePropertyValue)Base64.decodeToObject(str);
	}

	/**
	 *
	 */
	public String getName() {
		return this.name;
	}

	/**
	 *
	 */
	public String getPath() {
		return this.path;
	}
}