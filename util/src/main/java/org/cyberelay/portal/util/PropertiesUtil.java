package org.cyberelay.portal.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * @author Roger Tang
 * 
 */
public class PropertiesUtil {
	private PropertiesUtil() {
		// disregard it. singleton pattern.
	}

	/**
	 * read the specified resource from classpath as properties with ISO-8859-1
	 * encoding. Make sure the specified resource presented in the current
	 * classpath, otherwise NullpointerException will be thrown out.
	 * 
	 * @param resourceName
	 *            name of resource.
	 * @return
	 */
	public static Properties resourceToProperties(String resourceName) {
		try {
			InputStream in = PropertiesUtil.class.getClassLoader()
					.getResourceAsStream(resourceName);
			if (in == null) {
				return null;
			}
			Properties result = new Properties();
			result.load(in);
			return result;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unexpected exception! caused by: "
					+ e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException("Unexpected exception! caused by: "
					+ e.getMessage(), e);
		}
	}

	public static Properties fileToProperties(String fileName) {
		try {
			InputStream in = new FileInputStream(fileName);
			if (in == null) {
				return null;
			}
			Properties result = new Properties();
			result.load(in);
			return result;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unexpected exception! caused by: "
					+ e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException("Unexpected exception! caused by: "
					+ e.getMessage(), e);
		}
	}

	public static void saveToFile(Properties prop, String fileName) {
		try {
			FileOutputStream fileOutput = new FileOutputStream(fileName);
			OutputStreamWriter writer = new OutputStreamWriter(fileOutput,
					"ISO-8859-1");
			Enumeration propNames = prop.propertyNames();
			List keys = new ArrayList();
			while (propNames.hasMoreElements()) {
				keys.add(propNames.nextElement());
			}
			Collections.sort(keys);
			for (int i = 0; i < keys.size(); i++) {
				String key = (String) keys.get(i);
				writer.write(key);
				writer.write(" = ");
				writer.write(prop.getProperty(key));
				writer.write("\n");
			}
			writer.flush();
			writer.close();
			fileOutput.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException("Unexpected exception! caused by: "
					+ e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		Properties first = fileToProperties(args[0]);
		Properties second = fileToProperties(args[1]);
		second.putAll(first);
		saveToFile(second, args[2]);
	}
}
