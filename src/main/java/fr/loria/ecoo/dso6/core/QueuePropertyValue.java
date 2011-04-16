package fr.loria.ecoo.dso6.core;

/**
 *
 */
public class QueuePropertyValue {
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
		return name.replaceAll(":", "\\\\:") + ":" + path;
	}

	/**
	 *
	 */
	public static QueuePropertyValue fromString(String str) {
		if(str == null)
			return null;

		for(int i = 1; i < str.length(); i++)
			if(str.charAt(i) == ':' && str.charAt(i - 1) != '\\')
				return new QueuePropertyValue(str.substring(0, i).replaceAll("\\:", ":"), str.substring(i + 1));

		// invalid string
		return null;
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