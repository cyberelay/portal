package org.cyberelay.portal.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public abstract class StringUtil {

	private static final String[] EMPTY_STRING_ARRAY = new String[0];

	private static final String FOLDER_SEPARATOR = "/"; // folder separator

	private static final String WINDOWS_FOLDER_SEPARATOR = "\\"; // Windows

	private static final String TOP_PATH = ".."; // top folder

	private static final String CURRENT_PATH = "."; // current folder

	public static String arrayToString(Object[] objects) {
		if (objects == null) {
			return null;
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		for (int i = 0; i < objects.length; i++) {
			buffer.append(objects[i]);
			if (i != (objects.length - 1)) {
				buffer.append(", ");
			}
		}
		buffer.append("]");

		return buffer.toString();
	}

	/**
	 * Split a delimiter-seperated string to an array.
	 * 
	 * @param source
	 * @param delimiter
	 * @return if source is null, return an empty String array.
	 */
	public static String[] splitStringToArray(String source, String delimiter) {
		if (source == null) {
			return EMPTY_STRING_ARRAY;
		}
		if (delimiter == null) {
			return new String[] { source };
		}
		StringTokenizer st = new StringTokenizer(source, delimiter, true);
		List list = new ArrayList();
		while (st.hasMoreElements()) {
			String token = (String) st.nextToken().trim();
			if (token.equals(delimiter)) {
				token = null;
			} else {
				if (st.hasMoreElements()) {
					st.nextToken();
				}
			}
			list.add(token);
		}

		return (String[]) list.toArray(EMPTY_STRING_ARRAY);
	}

	/**
	 * Convert an array of String which likes "key=value" to a Map.
	 * 
	 * @param strings
	 * @return
	 */
	public static Map stringToMap(String[] strings) {
		Map result = new HashMap();
		for (int i = 0; i < strings.length; i++) {
			int pos = strings[i].indexOf('=');
			if (pos != -1 && pos != 0 && pos != strings[i].length()) {
				String key = strings[i].substring(0, pos);
				String value = strings[i].substring(pos + 1);
				result.put(key, value);
			}
		}

		return result;
	}

	public static String mapToString(Map map) {
		if (map == null) {
			return null;
		}

		StringBuffer sBuffer = new StringBuffer();
		Iterator keys = map.keySet().iterator();
		while (keys.hasNext()) {
			Object key = keys.next();
			sBuffer.append("\n").append(key).append(" = ").append(map.get(key));
		}
		return sBuffer.toString();
	}

	/**
	 * read the file content as a String with default character encoding.
	 * 
	 * @param fileName
	 * @return
	 */
	public static String fileToString(String fileName) {
		StringBuffer stringBuffer = new StringBuffer();

		try {
			char[] buffer = new char[1024];
			FileReader reader = new FileReader(fileName);
			int flag = -1;
			while ((flag = reader.read(buffer, 0, buffer.length)) != -1) {
				stringBuffer.append(buffer, 0, flag);
			}
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("[" + fileName + "] is not found.");
		} catch (IOException e) {
			throw new RuntimeException("Unexpected exception! caused by: " + e.getMessage(), e);
		}

		return stringBuffer.toString();
	}

	/**
	 * read the specified resource from classpath as a String with default
	 * character encoding. Make sure the specified resource presented in the
	 * current classpath, otherwise NullpointerException will be thrown out.
	 * 
	 * @param resourceName
	 *            name of resource.
	 * @return
	 */
	public static String resourceToString(String resourceName) {
		try {
			InputStream in = StringUtil.class.getClassLoader().getResourceAsStream(resourceName);
			InputStreamReader reader = new InputStreamReader(in);
			char[] buffer = new char[1024];
			StringBuffer template = new StringBuffer();
			int length = reader.read(buffer);
			while (length > 0) {
				template.append(buffer, 0, length);
				length = reader.read(buffer);
			}

			return template.toString();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unexpected exception! caused by: " + e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException("Unexpected exception! caused by: " + e.getMessage(), e);
		}
	}

	/**
	 * Check if a String has length.
	 * <p>
	 * 
	 * <pre>
	 * 
	 *  
	 *   
	 *    
	 *     
	 *      StringUtils.hasLength(null) = false
	 *      StringUtils.hasLength(&quot;&quot;) = false
	 *      StringUtils.hasLength(&quot; &quot;) = true
	 *      StringUtils.hasLength(&quot;Hello&quot;) = true
	 *      
	 *     
	 *    
	 *   
	 *  
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is not null and has length
	 */
	public static boolean hasLength(String str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * Check if a String has text. More specifically, returns <code>true</code>
	 * if the string not <code>null<code>, it's <code>length is > 0</code>, and
	 * it has at least one non-whitespace character.
	 * <p><pre>
	 * 
	 *  
	 *   
	 *    
	 *     
	 *      StringUtils.hasText(null) = false
	 *      StringUtils.hasText(&quot;&quot;) = false
	 *      StringUtils.hasText(&quot; &quot;) = false
	 *      StringUtils.hasText(&quot;12345&quot;) = true
	 *      StringUtils.hasText(&quot; 12345 &quot;) = true
	 *      
	 *     
	 *    
	 *   
	 *  
	 * </pre>
	 * @param input the String to check, may be null
	 * @return <code>true</code> if the String is not null, length > 0,
	 *         and not whitespace only
	 * @see java.lang.Character#isWhitespace
	 */
	public static boolean hasText(String input) {
		int strLen;
		if (input == null || (strLen = input.length()) == 0) {
			return false;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(input.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Trim leading whitespace from the given String.
	 * 
	 * @param input
	 *            the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimLeadingWhitespace(String input) {
		if (input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer(input);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
			buf.deleteCharAt(0);
		}
		return buf.toString();
	}

	/**
	 * Trim trailing whitespace from the given String.
	 * 
	 * @param input
	 *            the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimTrailingWhitespace(String input) {
		if (input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer(input);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * Test if the given String starts with the specified prefix, ignoring
	 * upper/lower case.
	 * 
	 * @param str
	 *            the String to check
	 * @param prefix
	 *            the prefix to look for
	 * @see java.lang.String#startsWith
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null) {
			return false;
		}
		if (str.startsWith(prefix)) {
			return true;
		}
		if (str.length() < prefix.length()) {
			return false;
		}
		String lcStr = str.substring(0, prefix.length()).toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}

	/**
	 * Count the occurrences of the substring in string s.
	 * 
	 * @param str
	 *            string to search in. Return 0 if this is null.
	 * @param sub
	 *            string to search for. Return 0 if this is null.
	 */
	public static int countOccurrencesOf(String str, String sub) {
		if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
			return 0;
		}
		int count = 0, pos = 0, idx = 0;
		while ((idx = str.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
	}

	/**
	 * Replace the first occurrence of the specified substring in a string with
	 * another specified substring
	 * 
	 * @param source
	 *            string
	 * @param seceder
	 *            substring for being replaced
	 * @param successor
	 *            substring for replacing
	 * @return
	 */
	public static String replaceFirst(String source, String seceder, String successor) {
		if (source == null) {
			return source;
		}
		int index = source.indexOf(seceder);
		if (index == -1) {
			return source;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(source.substring(0, index));
		sb.append(successor);
		sb.append(source.substring(index + seceder.length()));
		return sb.toString();
	}

	/**
	 * Replace all occurences of a substring within a string with another
	 * string.
	 * 
	 * @param input
	 *            String to examine
	 * @param oldPattern
	 *            String to replace
	 * @param newPattern
	 *            String to insert
	 * @return a String with the replacements
	 */
	public static String replaceAll(String input, String oldPattern, String newPattern) {
		if (input == null) {
			return null;
		}
		if (oldPattern == null || newPattern == null) {
			return input;
		}

		StringBuffer sbuf = new StringBuffer();
		// output StringBuffer we'll build up
		int pos = 0; // our position in the old string
		int index = input.indexOf(oldPattern);
		// the index of an occurrence we've found, or -1
		int patLen = oldPattern.length();
		while (index >= 0) {
			sbuf.append(input.substring(pos, index));
			sbuf.append(newPattern);
			pos = index + patLen;
			index = input.indexOf(oldPattern, pos);
		}
		sbuf.append(input.substring(pos));

		// remember to append any characters to the right of a match
		return sbuf.toString();
	}

	/**
	 * Delete all occurrences of the given substring.
	 * 
	 * @param pattern
	 *            the pattern to delete all occurrences of
	 */
	public static String delete(String input, String pattern) {
		return replaceAll(input, pattern, "");
	}

	/**
	 * Delete any character in a given string.
	 * 
	 * @param charsToDelete
	 *            a set of characters to delete. E.g. "az\n" will delete 'a's,
	 *            'z's and new lines.
	 */
	public static String deleteAny(String inString, String charsToDelete) {
		if (inString == null || charsToDelete == null) {
			return inString;
		}
		StringBuffer out = new StringBuffer();
		for (int i = 0; i < inString.length(); i++) {
			char c = inString.charAt(i);
			if (charsToDelete.indexOf(c) == -1) {
				out.append(c);
			}
		}
		return out.toString();
	}

	// ---------------------------------------------------------------------
	// Convenience methods for working with formatted Strings
	// ---------------------------------------------------------------------

	/**
	 * Unqualify a string qualified by a '.' dot character. For example,
	 * "this.name.is.qualified", returns "qualified".
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 */
	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, '.');
	}

	/**
	 * Unqualify a string qualified by a separator character. For example,
	 * "this:name:is:qualified" returns "qualified" if using a ':' separator.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @param separator
	 *            the separator
	 */
	public static String unqualify(String qualifiedName, char separator) {
		return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
	}

	/**
	 * Capitalize a <code>String</code>, changing the first letter to upper
	 * case as per {@link Character#toUpperCase(char)}. No other letters are
	 * changed.
	 * 
	 * @param str
	 *            the String to capitalize, may be null
	 * @return the capitalized String, <code>null</code> if null
	 */
	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}

	/**
	 * Uncapitalize a <code>String</code>, changing the first letter to lower
	 * case as per {@link Character#toLowerCase(char)}. No other letters are
	 * changed.
	 * 
	 * @param str
	 *            the String to uncapitalize, may be null
	 * @return the uncapitalized String, <code>null</code> if null
	 */
	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}

	private static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str.length());
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		} else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		buf.append(str.substring(1));
		return buf.toString();
	}

	/**
	 * Extract the filename from the given path, e.g. "mypath/myfile.txt" ->
	 * "myfile.txt".
	 * 
	 * @param path
	 *            the file path
	 * @return the extracted filename
	 */
	public static String getFilename(String path) {
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
	}

	/**
	 * Apply the given relative path to the given path, assuming standard Java
	 * folder separation (i.e. "/" separators);
	 * 
	 * @param path
	 *            the path to start from (usually a full file path)
	 * @param relativePath
	 *            the relative path to apply (relative to the full file path
	 *            above)
	 * @return the full file path that results from applying the relative path
	 */
	public static String applyRelativePath(String path, String relativePath) {
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		if (separatorIndex != -1) {
			String newPath = path.substring(0, separatorIndex);
			if (!relativePath.startsWith("/")) {
				newPath += "/";
			}
			return newPath + relativePath;
		}
		return relativePath;
	}

	/**
	 * Normalize the path by suppressing sequences like "path/.." and inner
	 * simple dots folders.
	 * <p>
	 * The result is convenient for path comparison. For other uses, notice that
	 * Windows separators ("\") are replaced by simple dashes.
	 * 
	 * @param path
	 *            the original path
	 * @return the normalized path
	 */
	public static String cleanPath(String path) {
		String pathToUse = replaceAll(path, WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);
		String[] pathArray = delimitedListToStringArray(pathToUse, FOLDER_SEPARATOR);
		List pathElements = new LinkedList();
		int tops = 0;
		for (int i = pathArray.length - 1; i >= 0; i--) {
			if (CURRENT_PATH.equals(pathArray[i])) {
				// do nothing
			} else if (TOP_PATH.equals(pathArray[i])) {
				tops++;
			} else {
				if (tops > 0) {
					tops--;
				} else {
					pathElements.add(0, pathArray[i]);
				}
			}
		}
		return collectionToDelimitedString(pathElements, FOLDER_SEPARATOR);
	}

	/**
	 * Compare two paths after normalization of them.
	 * 
	 * @param path1
	 *            First path for comparizon
	 * @param path2
	 *            Second path for comparizon
	 * @return True if the two paths are equivalent after normalization
	 */
	public static boolean pathEquals(String path1, String path2) {
		return cleanPath(path1).equals(cleanPath(path2));
	}

	/**
	 * Parse the given locale string into a <code>java.util.Locale</code>.
	 * This is the inverse operation of Locale's <code>toString</code>.
	 * 
	 * @param localeString
	 *            the locale string, following <code>java.util.Locale</code>'s
	 *            toString format ("en", "en_UK", etc). Also accepts spaces as
	 *            separators, as alternative to underscores.
	 * @return a corresponding Locale instance
	 */
	public static Locale parseLocaleString(String localeString) {
		String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
		String language = parts.length > 0 ? parts[0] : "";
		String country = parts.length > 1 ? parts[1] : "";
		String variant = parts.length > 2 ? parts[2] : "";
		return (language.length() > 0 ? new Locale(language, country, variant) : null);
	}

	// ---------------------------------------------------------------------
	// Convenience methods for working with String arrays
	// ---------------------------------------------------------------------

	/**
	 * Append the given String to the given String array, returning a new array
	 * consisting of the input array contents plus the given String.
	 * 
	 * @param arr
	 *            the array to append to
	 * @param str
	 *            the String to be appended
	 * @return the new array
	 */
	public static String[] addToArray(String[] arr, String str) {
		String[] newArr = new String[arr.length + 1];
		System.arraycopy(arr, 0, newArr, 0, arr.length);
		newArr[arr.length] = str;
		return newArr;
	}

	/**
	 * Append the second string array to the first string array. The resulting
	 * array consists of both array contents. The values of first array take the
	 * front places. If (array1 == null || array1.isEmpty()), return array2; if
	 * (array2 == null || array2.isEmpty()), return array1.
	 * 
	 * @param array1
	 *            the first string array.
	 * @param array2
	 *            the second string array.
	 * @return if array2 is null or empty, the returned object is array1
	 */
	public static String[] addToArray(String[] array1, String[] array2) {
		if (array1 == null || array1.length == 0) {
			return array2;
		}
		if (array2 == null || array2.length == 0) {
			return array1;
		}
		String[] newArr = new String[array1.length + array2.length];
		System.arraycopy(array1, 0, newArr, 0, array1.length);
		System.arraycopy(array2, 0, newArr, array1.length, array2.length);
		return newArr;
	}

	/**
	 * Turn given source String array into sorted array.
	 * 
	 * @param source
	 *            the source array
	 * @return the sorted array (never null)
	 */
	public static String[] sortStringArray(String[] source) {
		if (source == null) {
			return new String[0];
		}
		Arrays.sort(source);
		return source;
	}

	/**
	 * Split a String at the first occurrence of the delimiter. Does not include
	 * the delimiter in the result.
	 * 
	 * @param source
	 *            the string to split
	 * @param delimiter
	 *            to split the string up with
	 * @return a two element array with index 0 being before the delimiter, and
	 *         index 1 being after the delimiter (neither element includes the
	 *         delimiter); or null if the delimiter wasn't found in the given
	 *         input String
	 */
	public static String[] split(String source, String delimiter) {
		Assert.hasLength(source, "Cannot split a null or empty string");
		Assert.hasLength(delimiter, "Cannot use a null or empty delimiter to split a string");
		int offset = source.indexOf(delimiter);
		if (offset < 0) {
			return null;
		}
		String beforeDelimiter = source.substring(0, offset);
		String afterDelimiter = source.substring(offset + delimiter.length());
		return new String[] { beforeDelimiter, afterDelimiter };
	}

	/**
	 * Take an array Strings and split each element based on the given
	 * delimiter. A <code>Properties</code> instance is then generated, with
	 * the left of the delimiter providing the key, and the right of the
	 * delimiter providing the value.
	 * <p>
	 * Will trim both the key and value before adding them to the
	 * <code>Properties</code> instance.
	 * 
	 * @param array
	 *            the array to process
	 * @param delimiter
	 *            to split each element using (typically the equals symbol)
	 * @return a <code>Properties</code> instance representing the array
	 *         contents, or <code>null</code> if the array to process was null
	 *         or empty
	 */
	public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter) {
		return splitArrayElementsIntoProperties(array, delimiter, null);
	}

	/**
	 * Take an array Strings and split each element based on the given
	 * delimiter. A <code>Properties</code> instance is then generated, with
	 * the left of the delimiter providing the key, and the right of the
	 * delimiter providing the value.
	 * <p>
	 * Will trim both the key and value before adding them to the
	 * <code>Properties</code> instance.
	 * 
	 * @param array
	 *            the array to process
	 * @param delimiter
	 *            to split each element using (typically the equals symbol)
	 * @param charsToDelete
	 *            one or more characters to remove from each element prior to
	 *            attempting the split operation (typically the quotation mark
	 *            symbol), or <code>null</code> if no removal should occur
	 * @return a <code>Properties</code> instance representing the array
	 *         contents, or <code>null</code> if the array to process was null
	 *         or empty
	 */
	public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter,
			String charsToDelete) {

		if (array == null || array.length == 0) {
			return null;
		}

		Properties result = new Properties();
		for (int i = 0; i < array.length; i++) {
			String element = array[i];
			if (charsToDelete != null) {
				element = deleteAny(array[i], charsToDelete);
			}
			String[] splittedElement = split(element, delimiter);
			if (splittedElement == null) {
				continue;
			}
			result.setProperty(splittedElement[0].trim(), splittedElement[1].trim());
		}
		return result;
	}

	/**
	 * Tokenize the given String into a String array via a StringTokenizer.
	 * Trims tokens and omits empty tokens.
	 * <p>
	 * The given delimiters string is supposed to consist of any number of
	 * delimiter characters. Each of those characters can be used to separate
	 * tokens. A delimiter is always a single character; for multi-character
	 * delimiters, consider using <code>delimitedListToStringArray</code>
	 * 
	 * @param str
	 *            the String to tokenize
	 * @param delimiters
	 *            the delimiter characters, assembled as String (each of those
	 *            characters is individually considered as delimiter).
	 * @return an array of the tokens
	 * @see java.util.StringTokenizer
	 * @see java.lang.String#trim
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters) {
		return tokenizeToStringArray(str, delimiters, true, true);
	}

	/**
	 * Tokenize the given String into a String array via a StringTokenizer.
	 * <p>
	 * The given delimiters string is supposed to consist of any number of
	 * delimiter characters. Each of those characters can be used to separate
	 * tokens. A delimiter is always a single character; for multi-character
	 * delimiters, consider using <code>delimitedListToStringArray</code>
	 * 
	 * @param str
	 *            the String to tokenize
	 * @param delimiters
	 *            the delimiter characters, assembled as String (each of those
	 *            characters is individually considered as delimiter)
	 * @param trimTokens
	 *            trim the tokens via String's <code>trim</code>
	 * @param ignoreEmptyTokens
	 *            omit empty tokens from the result array (only applies to
	 *            tokens that are empty after trimming; StringTokenizer will not
	 *            consider subsequent delimiters as token in the first place).
	 * @return an array of the tokens
	 * @see java.util.StringTokenizer
	 * @see java.lang.String#trim
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens,
			boolean ignoreEmptyTokens) {

		StringTokenizer st = new StringTokenizer(str, delimiters);
		List tokens = new ArrayList();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() > 0) {
				tokens.add(token);
			}
		}
		return (String[]) tokens.toArray(new String[tokens.size()]);
	}

	/**
	 * Take a String which is a delimited list and convert it to a String array.
	 * <p>
	 * A single delimiter can consists of more than one character: It will still
	 * be considered as single delimiter string, rather than as bunch of
	 * potential delimiter characters - in contrast to
	 * <code>tokenizeToStringArray</code>.
	 * 
	 * @param str
	 *            the input String
	 * @param delimiter
	 *            the delimiter between elements (this is a single delimiter,
	 *            rather than a bunch individual delimiter characters)
	 * @return an array of the tokens in the list
	 * @see #tokenizeToStringArray
	 */
	public static String[] delimitedListToStringArray(String str, String delimiter) {
		if (str == null) {
			return new String[0];
		}
		if (delimiter == null) {
			return new String[] { str };
		}

		List result = new ArrayList();
		int pos = 0;
		int delPos = 0;
		while ((delPos = str.indexOf(delimiter, pos)) != -1) {
			result.add(str.substring(pos, delPos));
			pos = delPos + delimiter.length();
		}
		if (str.length() > 0 && pos <= str.length()) {
			// Add rest of String, but not in case of empty input.
			result.add(str.substring(pos));
		}

		return (String[]) result.toArray(new String[result.size()]);
	}

	/**
	 * Convert a CSV list into an array of Strings.
	 * 
	 * @param str
	 *            CSV list
	 * @return an array of Strings, or the empty array if s is null
	 */
	public static String[] commaDelimitedListToStringArray(String str) {
		return delimitedListToStringArray(str, ",");
	}

	/**
	 * Convenience method to convert a CSV string list to a set. Note that this
	 * will suppress duplicates.
	 * 
	 * @param str
	 *            CSV String
	 * @return a Set of String entries in the list
	 */
	public static Set commaDelimitedListToSet(String str) {
		Set set = new TreeSet();
		String[] tokens = commaDelimitedListToStringArray(str);
		for (int i = 0; i < tokens.length; i++) {
			set.add(tokens[i]);
		}
		return set;
	}

	/**
	 * Convenience method to return a String array as a delimited (e.g. CSV)
	 * String. E.g. useful for toString() implementations.
	 * 
	 * @param arr
	 *            array to display. Elements may be of any type (toString will
	 *            be called on each element).
	 * @param delim
	 *            delimiter to use (probably a ",")
	 */
	public static String arrayToDelimitedString(Object[] arr, String delim) {
		if (arr == null) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	/**
	 * Convenience method to return a Collection as a delimited (e.g. CSV)
	 * String. E.g. useful for toString() implementations.
	 * 
	 * @param coll
	 *            Collection to display
	 * @param delim
	 *            delimiter to use (probably a ",")
	 * @param prefix
	 *            string to start each element with
	 * @param suffix
	 *            string to end each element with
	 */
	public static String collectionToDelimitedString(Collection coll, String delim, String prefix,
			String suffix) {
		if (coll == null) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		Iterator it = coll.iterator();
		int i = 0;
		while (it.hasNext()) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(prefix).append(it.next()).append(suffix);
			i++;
		}
		return sb.toString();
	}

	/**
	 * Convenience method to return a Collection as a delimited (e.g. CSV)
	 * String. E.g. useful for toString() implementations.
	 * 
	 * @param coll
	 *            Collection to display
	 * @param delim
	 *            delimiter to use (probably a ",")
	 */
	public static String collectionToDelimitedString(Collection coll, String delim) {
		return collectionToDelimitedString(coll, delim, "", "");
	}

	/**
	 * Convenience method to return a String array as a CSV String. E.g. useful
	 * for toString() implementations.
	 * 
	 * @param arr
	 *            array to display. Elements may be of any type (toString will
	 *            be called on each element).
	 */
	public static String arrayToCommaDelimitedString(Object[] arr) {
		return arrayToDelimitedString(arr, ",");
	}

	/**
	 * Convenience method to return a Collection as a CSV String. E.g. useful
	 * for toString() implementations.
	 * 
	 * @param coll
	 *            Collection to display
	 */
	public static String collectionToCommaDelimitedString(Collection coll) {
		return collectionToDelimitedString(coll, ",");
	}

}