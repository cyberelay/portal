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

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portletcontainer.legacy.descriptor.PortletApplicationDescriptor;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

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
public class PortletApplicationDescriptorParser implements XmlConstants {
	private static final Logger LOG = LoggerFactory.getLogger(PortletApplicationDescriptorParser.class);

	protected static final String WEB_INF_PORTLET_XML = "/WEB-INF/legacy-portlet.xml";

	private String portletXmlPath;

	public PortletApplicationDescriptorParser(String portalXmlFilePath) {
		if (LOG.isDebugEnabled())
			LOG.debug("Portlet application descriptor path = [" + portalXmlFilePath + "]");

		this.portletXmlPath = portalXmlFilePath;
	}

	public PortletApplicationDescriptorParser(ServletContext context) {
		this(context.getRealPath(WEB_INF_PORTLET_XML));
	}

	public PortletApplicationDescriptor parse() throws SAXException, IOException {
		XMLReader parser = org.xml.sax.helpers.XMLReaderFactory.createXMLReader();
		InputSource in = new InputSource(portletXmlPath);
		PortletApplicationDescriptorImpl descriptor = new PortletApplicationDescriptorImpl();
		ContentHandler handler = new SAXPortletApplicationHandler(descriptor);
		parser.setContentHandler(handler);
		parser.parse(in);

		return descriptor;
	}

	private static class SAXPortletApplicationHandler extends DescriptorHandler {

		static final String PATH_ABS_PORTLET_APP = ELMT_PORTLET_APP_DEF
				+ getSplitStr()
				+ ELMT_PORTLET_APP;

		static final String PATH_ABS_PORTLET = PATH_ABS_PORTLET_APP + getSplitStr() + ELMT_PORTLET;

		static final String PATH_ABS_PORTLET_ALLOWS = PATH_ABS_PORTLET
				+ getSplitStr()
				+ ELMT_ALLOWS;

		static final String PATH_ABS_PORTLET_SUPPORT_MARKUP = PATH_ABS_PORTLET
				+ getSplitStr()
				+ ELMT_SUPPORTS
				+ getSplitStr()
				+ ELMT_MARKUP;

		static final String PATH_CON_PORTLET_APP = ELMT_PORTLET_APP_DEF
				+ getSplitStr()
				+ ELMT_CONCRETE_PORTLET_APP;

		static final String PATH_CON_PORTLET = PATH_CON_PORTLET_APP
				+ getSplitStr()
				+ ELMT_CONCRETE_PORTLET;

		static final String PATH_CON_PORTLET_LANG = PATH_CON_PORTLET
				+ getSplitStr()
				+ ELMT_LANGUAGE;

		private PortletApplicationDescriptorImpl descriptor;

		private String currentAppName = null;

		private AbstractPortletDefinitionImpl currentAbsPortletDef = null;

		private String markupName = null;

		private String paramName = null;

		private String paramValue = null;

		private ConcretePortletApplicationDefinitionImpl currentConPortletAppDef = null;

		private ConcretePortletDefinitionImpl currentConPortletDef = null;

		private String defaultLocale = null;

		private Locale langLocale = null;

		public SAXPortletApplicationHandler(PortletApplicationDescriptorImpl descriptor) {
			this.descriptor = descriptor;
		}

		public void doStartElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {

			if (getCurrentFullPath().equals(PATH_ABS_PORTLET_APP)) {
				currentAppName = attributes.getValue(ATTR_UNIQUENAME);
				descriptor.setAbstractPortletApplicationDefinition(new AbstractPortletApplicationDefinitionImpl(
						attributes.getValue(ATTR_UNIQUENAME)));
			}

			if (getCurrentFullPath().equals(PATH_ABS_PORTLET)) {
				currentAbsPortletDef = new AbstractPortletDefinitionImpl(
						attributes.getValue(ATTR_UNIQUENAME));
				currentAbsPortletDef.setPortletClass(attributes.getValue(ATTR_CLASS));
				currentAbsPortletDef.setAbstractPortletApplicationName(currentAppName);
				descriptor.addAbstractPortletDefinition(currentAbsPortletDef);
			}

			if (getCurrentFullPath().startsWith(PATH_ABS_PORTLET_ALLOWS)) {
				if (!qName.equals(ELMT_ALLOWS)) {
					currentAbsPortletDef.setStateAllowed(qName, true);
				}
			}

			if (getCurrentFullPath().equals(PATH_ABS_PORTLET_SUPPORT_MARKUP)) {
				markupName = attributes.getValue(ATTR_NAME);
			}

			if (getCurrentFullPath().startsWith(PATH_ABS_PORTLET_SUPPORT_MARKUP)) {
				if (!qName.equals(ELMT_MARKUP)) {
					currentAbsPortletDef.setModeSupported(markupName, qName, true);
				}
			}

			if (getCurrentFullPath().equals(PATH_CON_PORTLET_APP)) {
				currentAppName = attributes.getValue(ATTR_UNIQUENAME);
				currentConPortletAppDef = new ConcretePortletApplicationDefinitionImpl(
						currentAppName);
				descriptor.addConcretePortletApplicationDefinition(currentConPortletAppDef);
			}

			if (getCurrentFullPath().equals(PATH_CON_PORTLET)) {
				currentConPortletDef = new ConcretePortletDefinitionImpl(
						attributes.getValue(ATTR_UNIQUENAME));

				currentConPortletDef.setConcretePortletApplicationName(currentAppName);
				currentConPortletDef.setAbstractPortletName(attributes.getValue(ATTR_HREF));

				descriptor.addConcretePortletDefinition(currentConPortletDef);
			}

			if (getCurrentFullPath().equals(PATH_CON_PORTLET_LANG)) {
				String locale = attributes.getValue(ATTR_LOCALE);
				langLocale = (locale == null ? null : new Locale(locale, ""));
			}

		}

		public void doEndElement(String uri, String localName, String qName) throws SAXException {
			if (getCurrentFullPath().equals(
					(ELMT_PORTLET_APP_DEF
							+ getSplitStr()
							+ ELMT_PORTLET_APP
							+ getSplitStr()
							+ ELMT_PORTLET
							+ getSplitStr() + ELMT_INIT_PARAM))) {
				currentAbsPortletDef.setParameter(paramName, paramValue);
			}

			if (getCurrentFullPath().equals(
					(ELMT_PORTLET_APP_DEF
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET_APP
							+ getSplitStr() + ELMT_CONTEXT_PARAM))) {
				currentConPortletAppDef.setParameter(paramName, paramValue);
			}

			if (getCurrentFullPath().equals(
					(ELMT_PORTLET_APP_DEF
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET_APP
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET
							+ getSplitStr() + ELMT_DEFAULT_LOCALE))) {
				Locale locale = (defaultLocale == null ? null : new Locale(defaultLocale, ""));
				currentConPortletDef.setDefaultLocale(locale);
				defaultLocale = null;
			}

			if (getCurrentFullPath().equals(
					(ELMT_PORTLET_APP_DEF
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET_APP
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET
							+ getSplitStr() + ELMT_LANGUAGE))) {
				langLocale = null;
			}

			if (getCurrentFullPath().equals(
					(ELMT_PORTLET_APP_DEF
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET_APP
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET
							+ getSplitStr() + ELMT_CONFIG_PARAM).intern())) {
				currentConPortletDef.setParameter(paramName, paramValue);
			}

		}

		public void characters(char[] ch, int start, int length) throws SAXException {
			super.characters(ch, start, length);

			if (getCurrentFullPath().equals(
					(ELMT_PORTLET_APP_DEF
							+ getSplitStr()
							+ ELMT_PORTLET_APP
							+ getSplitStr()
							+ ELMT_PORTLET
							+ getSplitStr()
							+ ELMT_INIT_PARAM
							+ getSplitStr() + ELMT_PARAM_NAME).intern())) {
				paramName = new String(ch, start, length);
			}

			if (getCurrentFullPath().equals(
					(ELMT_PORTLET_APP_DEF
							+ getSplitStr()
							+ ELMT_PORTLET_APP
							+ getSplitStr()
							+ ELMT_PORTLET
							+ getSplitStr()
							+ ELMT_INIT_PARAM
							+ getSplitStr() + ELMT_PARAM_VALUE).intern())) {
				paramValue = new String(ch, start, length);
			}

			if (getCurrentFullPath().equals(
					(ELMT_PORTLET_APP_DEF
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET_APP
							+ getSplitStr()
							+ ELMT_CONTEXT_PARAM
							+ getSplitStr() + ELMT_PARAM_NAME).intern())) {
				paramName = new String(ch, start, length);
			}

			if (getCurrentFullPath().equals(
					(ELMT_PORTLET_APP_DEF
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET_APP
							+ getSplitStr()
							+ ELMT_CONTEXT_PARAM
							+ getSplitStr() + ELMT_PARAM_VALUE).intern())) {
				paramValue = new String(ch, start, length);
			}

			if (getCurrentFullPath().equals(
					(ELMT_PORTLET_APP_DEF
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET_APP
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET
							+ getSplitStr() + ELMT_DEFAULT_LOCALE).intern())) {
				defaultLocale = new String(ch, start, length);
			}

			if (getCurrentFullPath().equals(
					(ELMT_PORTLET_APP_DEF
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET_APP
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET
							+ getSplitStr()
							+ ELMT_LANGUAGE
							+ getSplitStr() + ELMT_TITLE).intern())) {
				currentConPortletDef.setTitle(langLocale, new String(ch, start, length));
			}

			if (getCurrentFullPath().equals(
					(ELMT_PORTLET_APP_DEF
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET_APP
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET
							+ getSplitStr()
							+ ELMT_LANGUAGE
							+ getSplitStr() + ELMT_TITLE_SHORT).intern())) {
				currentConPortletDef.setShortTitle(langLocale, new String(ch, start, length));
			}

			if (getCurrentFullPath().equals(
					(ELMT_PORTLET_APP_DEF
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET_APP
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET
							+ getSplitStr()
							+ ELMT_LANGUAGE
							+ getSplitStr() + ELMT_DESCRIPTION).intern())) {
				currentConPortletDef.setDescription(langLocale, new String(ch, start, length));
			}

			if (getCurrentFullPath().equals(
					(ELMT_PORTLET_APP_DEF
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET_APP
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET
							+ getSplitStr()
							+ ELMT_CONFIG_PARAM
							+ getSplitStr() + ELMT_PARAM_NAME).intern())) {
				paramName = new String(ch, start, length);
			}

			if (getCurrentFullPath().equals(
					(ELMT_PORTLET_APP_DEF
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET_APP
							+ getSplitStr()
							+ ELMT_CONCRETE_PORTLET
							+ getSplitStr()
							+ ELMT_CONFIG_PARAM
							+ getSplitStr() + ELMT_PARAM_VALUE).intern())) {
				paramValue = new String(ch, start, length);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(".............start:" + System.currentTimeMillis());
		long time = System.currentTimeMillis();
		PortletApplicationDescriptorParser descriptor = new PortletApplicationDescriptorParser(
				"my-portlet.xml");
		System.out.println(".............end:" + System.currentTimeMillis());
		System.out.println("time elapsed = " + (System.currentTimeMillis() - time));
		System.out.println(descriptor.parse());

		PortletApplicationDescriptorWriter writer = new PortletApplicationDescriptorWriter(
				descriptor.parse());
		writer.write("my-portal1.xml");
		System.out.println("parsing normalized page definitions.......done.");
	}
}
