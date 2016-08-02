/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cyberelay.portletcontainer.legacy.descriptor.parser;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * @author Terry Li
 * 
 * <ul>
 * <li>Creation Date: Jan 18, 2008
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
public abstract class DescriptorHandler implements ContentHandler {
	private static final String SLASH = "/";
	private static final int MAX_LAYER = 1000;

	private String[] elementPaths;
	private int currentIndex = -1;

	/**
	 * @return Returns the elementPaths.
	 */
	public String[] getElementPaths() {
		return elementPaths;
	}

	/**
	 * @return .
	 */
	public String getCurrentFullPath() {
		if (elementPaths == null) {
			return null;
		}
		if (elementPaths.length == 0) {
			return "";
		}
		StringBuffer fullPath = new StringBuffer();
		for (int i = 0; i <= currentIndex; i++) {
			if (i > 0) {
				fullPath.append(SLASH);
			}
			fullPath.append(elementPaths[i]);
		}
		return fullPath.toString();
	}

	public void setDocumentLocator(Locator locator) {

	}

	public void startDocument() throws SAXException {
		elementPaths = new String[MAX_LAYER];

	}

	public void endDocument() throws SAXException {
		elementPaths = null;

	}

	public void startPrefixMapping(String prefix, String uri) throws SAXException {

	}

	public void endPrefixMapping(String prefix) throws SAXException {

	}

	public void startElement(String uri, String localName, String qName, Attributes atts)
			throws SAXException {
		if (currentIndex == MAX_LAYER - 1) {
			throw new SAXException("out of the max layers ! now current index = " + currentIndex);
		}
		currentIndex++;
		elementPaths[currentIndex] = qName;

		doStartElement(uri, localName, qName, atts);
	}

	protected abstract void doStartElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException;

	public void endElement(String uri, String localName, String qName) throws SAXException {
		doEndElement(uri, localName, qName);

		if (currentIndex == -1) {
			throw new SAXException("out of the max layers ! now current index = " + currentIndex);
		}
		currentIndex--;
	}

	protected abstract void doEndElement(String uri, String localName, String qName)
			throws SAXException;

	public void characters(char[] ch, int start, int length) throws SAXException {

	}

	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
	}

	public void processingInstruction(String target, String data) throws SAXException {

	}

	public void skippedEntity(String name) throws SAXException {

	}

	/**
	 * @return Returns the splitStr.
	 */
	protected static String getSplitStr() {
		return SLASH;
	}
}
