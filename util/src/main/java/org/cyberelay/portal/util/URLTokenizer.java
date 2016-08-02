package org.cyberelay.portal.util;

import java.util.StringTokenizer;

/**
 * @author Roger Tang
 * 
 */
public class URLTokenizer extends StringTokenizer {

	public URLTokenizer(String input, String delimiter) {
		super(getCorrectURL(input, delimiter), delimiter, true);
	}

	private static String getCorrectURL(String input, String delimiter) {
		if (input.startsWith(delimiter))
			input = input.substring(delimiter.length());
		if (!input.endsWith(delimiter) && input != "")
			input = input + delimiter;
		return input;
	}

	public int countTokens() {
		return super.countTokens() / 2;
	}

	public boolean hasMoreElements() {
		return hasMoreTokens();
	}

	public boolean hasMoreTokens() {
		return super.hasMoreTokens();
	}

	public Object nextElement() {
		return nextToken();
	}

	public String nextToken() {
		String s = super.nextToken();
		if (hasMoreTokens())
			super.nextToken();
		return s;
	}

	public String nextToken(String s) {
		throw new IllegalStateException("Method nextToken(delim) not supported.");
	}

}
