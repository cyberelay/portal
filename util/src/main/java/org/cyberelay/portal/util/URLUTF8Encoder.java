package org.cyberelay.portal.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.util.BitSet;

import sun.security.action.GetPropertyAction;

public class URLUTF8Encoder {
	private static final String UTF8 = "UTF8";

	protected URLUTF8Encoder() {
	}

	public static String encode(String s) {
		try {
			return encode(s, UTF8);
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}

	public static String decode(String s) {
		try {
			return decode(s, UTF8);
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}

	static BitSet dontNeedEncoding;

	static final int caseDiff = ('a' - 'A');

	static String dfltEncName = null;

	static {

		/*
		 * The list of characters that are not encoded has been determined as
		 * follows:
		 * 
		 * RFC 2396 states: ----- Data characters that are allowed in a URI but
		 * do not have a reserved purpose are called unreserved. These include
		 * upper and lower case letters, decimal digits, and a limited set of
		 * punctuation marks and symbols.
		 * 
		 * unreserved = alphanum | mark
		 * 
		 * mark = "-" | "_" | "." | "!" | "~" | "*" | "'" | "(" | ")"
		 * 
		 * Unreserved characters can be escaped without changing the semantics
		 * of the URI, but this should not be done unless the URI is being used
		 * in a context that does not allow the unescaped character to appear.
		 * -----
		 * 
		 * It appears that both Netscape and Internet Explorer escape all
		 * special characters from this list with the exception of "-", "_",
		 * ".", "*". While it is not clear why they are escaping the other
		 * characters, perhaps it is safest to assume that there might be
		 * contexts in which the others are unsafe if not escaped. Therefore, we
		 * will use the same list. It is also noteworthy that this is consistent
		 * with O'Reilly's "HTML: The Definitive Guide" (page 164).
		 * 
		 * As a last note, Intenet Explorer does not encode the "@" character
		 * which is clearly not unreserved according to the RFC. We are being
		 * consistent with the RFC in this matter, as is Netscape.
		 * 
		 */

		dontNeedEncoding = new BitSet(256);
		int i;
		for (i = 'a'; i <= 'z'; i++) {
			dontNeedEncoding.set(i);
		}
		for (i = 'A'; i <= 'Z'; i++) {
			dontNeedEncoding.set(i);
		}
		for (i = '0'; i <= '9'; i++) {
			dontNeedEncoding.set(i);
		}
		dontNeedEncoding.set(' '); /*
									 * encoding a space to a + is done in the
									 * encode() method
									 */
		dontNeedEncoding.set('-');
		dontNeedEncoding.set('_');
		dontNeedEncoding.set('.');
		dontNeedEncoding.set('*');

		dfltEncName = (String) AccessController
				.doPrivileged(new GetPropertyAction("file.encoding"));
	}

	/**
	 * Decodes a <code>application/x-www-form-urlencoded</code> string using a
	 * specific encoding scheme. The supplied encoding is used to determine what
	 * characters are represented by any consecutive sequences of the form "<code>%<i>xy</i></code>".
	 * <p>
	 * <em><strong>Note:</strong> The <a href=
	 * "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
	 * World Wide Web Consortium Recommendation</a> states that
	 * UTF-8 should be used. Not doing so may introduce
	 * incompatibilites.</em>
	 * 
	 * @param s
	 *            the <code>String</code> to decode
	 * @param enc
	 *            The name of a supported <a
	 *            href="../lang/package-summary.html#charenc">character encoding
	 *            </a>.
	 * @return the newly decoded <code>String</code>
	 * @exception UnsupportedEncodingException
	 *                If character encoding needs to be consulted, but named
	 *                character encoding is not supported
	 */
	public static String decode(String s, String enc)
			throws UnsupportedEncodingException {

		boolean needToChange = false;
		int numChars = s.length();
		StringBuffer sb = new StringBuffer(numChars > 500 ? numChars / 2
				: numChars);
		int i = 0;

		if (enc.length() == 0) {
			throw new UnsupportedEncodingException(
					"URLDecoder: empty string enc parameter");
		}

		char c;
		byte[] bytes = null;
		while (i < numChars) {
			c = s.charAt(i);
			switch (c) {
			case '+':
				sb.append(' ');
				i++;
				needToChange = true;
				break;
			case '%':
				/*
				 * Starting with this instance of %, process all consecutive
				 * substrings of the form %xy. Each substring %xy will yield a
				 * byte. Convert all consecutive bytes obtained this way to
				 * whatever character(s) they represent in the provided
				 * encoding.
				 */

				try {

					// (numChars-i)/3 is an upper bound for the number
					// of remaining bytes
					if (bytes == null)
						bytes = new byte[(numChars - i) / 3];
					int pos = 0;

					while (((i + 2) < numChars) && (c == '%')) {
						bytes[pos++] = (byte) Integer.parseInt(s.substring(
								i + 1, i + 3), 16);
						i += 3;
						if (i < numChars)
							c = s.charAt(i);
					}

					// A trailing, incomplete byte encoding such as
					// "%x" will cause an exception to be thrown

					if ((i < numChars) && (c == '%'))
						throw new IllegalArgumentException(
								"URLDecoder: Incomplete trailing escape (%) pattern");

					sb.append(new String(bytes, 0, pos, enc));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException(
							"URLDecoder: Illegal hex characters in escape (%) pattern - "
									+ e.getMessage());
				}
				needToChange = true;
				break;
			default:
				sb.append(c);
				i++;
				break;
			}
		}

		return (needToChange ? sb.toString() : s);
	}

	/**
	 * Translates a string into <code>application/x-www-form-urlencoded</code>
	 * format using a specific encoding scheme. This method uses the supplied
	 * encoding scheme to obtain the bytes for unsafe characters.
	 * <p>
	 * <em><strong>Note:</strong> The <a href=
	 * "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
	 * World Wide Web Consortium Recommendation</a> states that
	 * UTF-8 should be used. Not doing so may introduce
	 * incompatibilites.</em>
	 * 
	 * @param s
	 *            <code>String</code> to be translated.
	 * @param enc
	 *            The name of a supported <a
	 *            href="../lang/package-summary.html#charenc">character encoding
	 *            </a>.
	 * @return the translated <code>String</code>.
	 * @exception UnsupportedEncodingException
	 *                If the named encoding is not supported
	 */
	public static String encode(String s, String enc)
			throws UnsupportedEncodingException {

		boolean needToChange = false;
		boolean wroteUnencodedChar = false;
		int maxBytesPerChar = 10; // rather arbitrary limit, but safe for now
		StringBuffer out = new StringBuffer(s.length());
		ByteArrayOutputStream buf = new ByteArrayOutputStream(maxBytesPerChar);

		OutputStreamWriter writer = new OutputStreamWriter(buf, enc);

		for (int i = 0; i < s.length(); i++) {
			int c = (int) s.charAt(i);
			if (dontNeedEncoding.get(c)) {
				if (c == ' ') {
					c = '+';
					needToChange = true;
				}
				out.append((char) c);
				wroteUnencodedChar = true;
			} else {
				// convert to external encoding before hex conversion
				try {
					if (wroteUnencodedChar) { // Fix for 4407610
						writer = new OutputStreamWriter(buf, enc);
						wroteUnencodedChar = false;
					}
					writer.write(c);
					/*
					 * If this character represents the start of a Unicode
					 * surrogate pair, then pass in two characters. It's not
					 * clear what should be done if a bytes reserved in the
					 * surrogate pairs range occurs outside of a legal surrogate
					 * pair. For now, just treat it as if it were any other
					 * character.
					 */
					if (c >= 0xD800 && c <= 0xDBFF) {
						if ((i + 1) < s.length()) {
							int d = (int) s.charAt(i + 1);
							if (d >= 0xDC00 && d <= 0xDFFF) {
								writer.write(d);
								i++;
							}
						}
					}
					writer.flush();
				} catch (IOException e) {
					buf.reset();
					continue;
				}
				byte[] ba = buf.toByteArray();
				for (int j = 0; j < ba.length; j++) {
					out.append('%');
					char ch = Character.forDigit((ba[j] >> 4) & 0xF, 16);
					// converting to use uppercase letter as part of
					// the hex value if ch is a letter.
					if (Character.isLetter(ch)) {
						ch -= caseDiff;
					}
					out.append(ch);
					ch = Character.forDigit(ba[j] & 0xF, 16);
					if (Character.isLetter(ch)) {
						ch -= caseDiff;
					}
					out.append(ch);
				}
				buf.reset();
				needToChange = true;
			}
		}

		return (needToChange ? out.toString() : s);
	}
}
