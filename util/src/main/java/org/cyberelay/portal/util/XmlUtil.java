package org.cyberelay.portal.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
public class XmlUtil {
	private static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

	private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

	private static final String NO_NAMESPACE_SCHEMA_LOCATION = "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation";

	protected static final String VAL_UNDEFINED = "undefined";

	public static Document toDocument(String content)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		return content == null ? null : db.parse(new InputSource(
				new StringReader(content)));
	}

	public static Document toDocument(InputStream input)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		return input == null ? null : db.parse(input);
	}

	public static Node getNode(Node contextNode, String xpath)
			throws TransformerException {
		return PooledXPathAPI.selectSingleNode(contextNode, xpath);
	}

	public static NodeIterator getNodeIterator(Node contextNode, String xpath)
			throws TransformerException {
		return PooledXPathAPI.selectNodeIterator(contextNode, xpath);
	}

	public static NodeList getNodeList(Node contextNode, String xpath)
			throws TransformerException {
		return PooledXPathAPI.selectNodeList(contextNode, xpath);
	}

	public static String getNodeValue(Node node, String xpath)
			throws TransformerException {
		Node result = XmlUtil.getNode(node, xpath);
		return result == null ? null : result.getNodeValue();
	}

	public static void validate(String content, String schema)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(true);
		dbf.setNamespaceAware(true);
		dbf.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
		dbf.setAttribute(NO_NAMESPACE_SCHEMA_LOCATION, schema);
		DocumentBuilder db = dbf.newDocumentBuilder();
		db.setErrorHandler(new ErrorPrinter());
		db.parse(new InputSource(new StringReader(content)));
	}

	public static int getNodeValueAsInt(Node context, String xpath)
			throws TransformerException {
		return getNodeValueAsInt(context, xpath, Integer.MIN_VALUE);
	}

	public static int getNodeValueAsInt(Node context, String xpath,
			int defaultValue) throws TransformerException {
		String token = getNodeValue(context, xpath);
		try {
			return Integer.parseInt(token);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static boolean getNodeValueAsBoolean(Node context, String xpath)
			throws TransformerException {
		Boolean result = Boolean.valueOf(getNodeValue(context, xpath));
		return result != null && result.booleanValue();
	}

	public static Locale getNodeValueAsLocale(Node context, String xpath)
			throws TransformerException {
		String langugage = getNodeValue(context, xpath);
		return langugage == null ? null : new Locale(langugage, "");
	}

	private static class ErrorPrinter extends DefaultHandler {
		public void warning(SAXParseException x) throws SAXParseException {
			throw x;
		}

		public void error(SAXParseException x) throws SAXParseException {
			throw x;
		}

		public void fatalError(SAXParseException x) throws SAXParseException {
			throw x;
		}
	}

}