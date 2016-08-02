package org.apache.jetspeed.portlet;

import java.util.HashMap;
import java.util.Map;

public class Capability {
	private static Map allCapabilities = new HashMap();

	public static final Capability FRAGMENT = new Capability(
			"Fragment Identifier",
			"FRAGMENT_IDENTIFIER",
			-1);
	public static final Capability HTML_2_0 = new Capability("HTML 2.0", "HTML_2_0", 1);
	public static final Capability HTML_3_0 = new Capability("HTML 3.0", "HTML_3_0", 2);
	public static final Capability HTML_3_2 = new Capability("HTML 3.2", "HTML_3_2", 3);
	public static final Capability HTML_4_0 = new Capability("HTML 4.0", "HTML_4_0", 4);
	public static final Capability HTML_JAVA = new Capability("HTML Java", "HTML_JAVA", 5);
	public static final Capability HTML_JAVASCRIPT = new Capability(
			"HTML JavaScript",
			"HTML_JAVASCRIPT",
			6);
	public static final Capability HTML_TABLE = new Capability(
			"HTML Table Support",
			"HTML_TABLE",
			7);
	public static final Capability HTML_NESTED_TABLE = new Capability(
			"HTML Nested Table Support",
			"HTML_NESTED_TABLE",
			8);
	public static final Capability HTML_FRAME = new Capability(
			"HTML Frames Support",
			"HTML_FRAME",
			9);
	public static final Capability HTML_IFRAME = new Capability(
			"HTML i-Frames Support",
			"HTML_IFRAME",
			10);
	public static final Capability HTML_CSS = new Capability("HTML CSS Support", "HTML_CSS", 11);
	public static final Capability WML_1_1 = new Capability("WML 1.1", "WML_1_1", 12);
	public static final Capability WML_1_2 = new Capability("WML 1.2", "WML_1_2", 13);
	public static final Capability WML_TABLE = new Capability("WML Table Support", "WML_TABLE", 14);

	private String identifier;
	private String key;
	private int value;

	public int hashCode() {
		return value;
	}

	public boolean equals(Object object) {
		if (object instanceof Capability)
			return getValue() == ((Capability) object).getValue();
		return false;
	}

	public String toString() {
		return identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getKey() {
		return key;
	}

	public static Capability forIdentifier(String identifier) {
		return (Capability) allCapabilities.get(identifier);
	}

	protected int getValue() {
		return value;
	}

	private Capability(String identifier, String key, int value) {
		this.identifier = identifier;
		this.value = value;
		this.key = key;
		allCapabilities.put(key, this);
	}

}
