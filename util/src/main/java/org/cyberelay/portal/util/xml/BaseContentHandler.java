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

package org.cyberelay.portal.util.xml;

import java.util.Iterator;
import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * @author Terry Li
 * 
 */
public abstract class BaseContentHandler implements ContentHandler {
	protected static final String SPLIT = "/";
	private LinkedList<String> elements = new LinkedList<String>();

	public String getCurrentFullPath() {
		if (elements.isEmpty()) {
			return SPLIT;
		}

		StringBuffer fullPath = new StringBuffer();
		Iterator<String> elements = this.elements.iterator();
		while (elements.hasNext()) {
			fullPath.append(SPLIT).append(elements.next());
		}

		return fullPath.toString();
	}

	public void setDocumentLocator(Locator locator) {
	}

	public void startDocument() throws SAXException {
	}

	public void endDocument() throws SAXException {
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
	}

	public void endPrefixMapping(String prefix) throws SAXException {
	}

	public final void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		elements.add(qName);

		doStartElement(uri, localName, qName, atts);
	}

	protected abstract void doStartElement(String uri, String localName,
			String qName, Attributes atts) throws SAXException;

	public final void endElement(String uri, String localName, String qName)
			throws SAXException {
		doEndElement(uri, localName, qName);

		elements.removeLast();
	}

	protected abstract void doEndElement(String uri, String localName,
			String qName) throws SAXException;

	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
	}

	public void skippedEntity(String name) throws SAXException {
	}

	/**
	 * @return Returns the splitStr.
	 */
	public String getSplitStr() {
		return SPLIT;
	}
}
