package org.cyberelay.portal.util;

import java.util.Hashtable;
import java.util.LinkedList;

import javax.xml.transform.TransformerException;

import org.apache.xpath.CachedXPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

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
public class PooledXPathAPI {

	private static final int DEFAULT_POOLSIZE = 20;

	private static int capacity;

	private static Hashtable apis;

	private static LinkedList counts;

	static {
		capacity = DEFAULT_POOLSIZE;
		apis = new Hashtable(capacity * 2);
		counts = new LinkedList();
	}

	public static Node selectSingleNode(Node node, String path)
			throws TransformerException {
		if (node == null)
			return null;
		else if (node instanceof Document) // if node is instance of Document,
			// node.getOwnerDocument() returns null
			return getXPathAPI((Document) node).selectSingleNode(node, path);
		else
			return getXPathAPI(node.getOwnerDocument()).selectSingleNode(node,
					path);
	}

	public static NodeList selectNodeList(Node node, String path)
			throws TransformerException {
		if (node == null)
			return null;
		else if (node instanceof Document) // if node is instance of Document,
			// node.getOwnerDocument() returns null
			return getXPathAPI((Document) node).selectNodeList(node, path);
		else
			return getXPathAPI(node.getOwnerDocument()).selectNodeList(node,
					path);
	}

	public static NodeIterator selectNodeIterator(Node node, String path)
			throws TransformerException {
		if (node == null)
			return null;
		else if (node instanceof Document) // if node is instance of Document,
			// node.getOwnerDocument() returns null
			return getXPathAPI((Document) node).selectNodeIterator(node, path);
		else
			return getXPathAPI(node.getOwnerDocument()).selectNodeIterator(
					node, path);
	}

	private static synchronized CachedXPathAPI getXPathAPI(Document doc) {
		if (apis.containsKey(doc)) {
			// move the hitted one to head
			counts.remove(doc);
			counts.add(0, doc);
		} else {
			// remove the last one if exceeds pool size
			if (apis.size() > capacity) {
				apis.remove(counts.removeLast());
			}
			apis.put(doc, new CachedXPathAPI());
			counts.add(0, doc);
		}
		return (CachedXPathAPI) apis.get(doc);
	}

}
