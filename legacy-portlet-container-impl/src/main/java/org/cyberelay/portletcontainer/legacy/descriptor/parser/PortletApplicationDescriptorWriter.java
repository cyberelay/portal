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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.jetspeed.portlet.PortletWindow;
import org.apache.jetspeed.portlet.Portlet.Mode;
import org.cyberelay.portletcontainer.legacy.descriptor.AbstractPortletDefinition;
import org.cyberelay.portletcontainer.legacy.descriptor.ConcretePortletApplicationDefinition;
import org.cyberelay.portletcontainer.legacy.descriptor.ConcretePortletDefinition;
import org.cyberelay.portletcontainer.legacy.descriptor.PortletApplicationDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

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
public class PortletApplicationDescriptorWriter extends XmlWriter {
	private PortletApplicationDescriptor descriptor;

	public PortletApplicationDescriptorWriter(PortletApplicationDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	protected Document buildDocument() throws ParserConfigurationException {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

		/* 'portlet-app-def' element: root */
		Element appDef = document.createElement(ELMT_PORTLET_APP_DEF);
		document.appendChild(appDef);

		/* 'portlet-app' element: abstract portlet application definition. */
		Node absApp = buildAbstractApplicationNode(document);
		appDef.appendChild(absApp);

		/* 'concrete-portlet-app' concrete portlet application definition. */
		Map crtAppNodes = new HashMap();
		Iterator names = descriptor.getConcretePortletApplicationUniqueNames();
		while (names.hasNext()) {
			String crtAppName = (String) names.next();
			Node crtApp = toNode(
					document, descriptor.getConcretePortletApplicationDefinition(crtAppName));
			appDef.appendChild(crtApp);
			crtAppNodes.put(crtAppName, crtApp);
		}

		/* 'concrete-portlet' element: concrete portlet definition. */
		Iterator crtPortletNames = descriptor.getConcretePortletUniqueNames();
		while (crtPortletNames.hasNext()) {
			ConcretePortletDefinition definition = descriptor.getConcretePortletDefinition((String) crtPortletNames.next());
			Node crtApp = (Node) crtAppNodes.get(definition.getConcretePortletApplicationName());
			if (crtApp != null) {
				crtApp.appendChild(toNode(document, definition));
			}
		}

		return document;
	}

	private Node buildAbstractApplicationNode(Document document) {
		/* 'portlet-app' element: abstract portlet application definition. */
		Element absApp = document.createElement(ELMT_PORTLET_APP);
		absApp.setAttribute(
				ATTR_UNIQUENAME,
				descriptor.getAbstractPortletApplicationDefinition().getUniqueName());

		/* 'portlet' elements. abstract portlet definition. */
		Iterator names = descriptor.getAbstractPortletUniqueNames();
		while (names.hasNext()) {
			absApp.appendChild(toNode(
					document, descriptor.getAbstractPortletDefinition((String) names.next())));
		}

		return absApp;
	}

	private Node toNode(Document document, AbstractPortletDefinition definition) {
		System.out.println("abstract portlet definition = {" + definition + "}");

		/* 'portlet' element */
		Element portlet = document.createElement(ELMT_PORTLET);
		portlet.setAttribute(ATTR_UNIQUENAME, definition.getUniqueName());
		portlet.setAttribute(ATTR_CLASS, definition.getPortletClassName());

		/* 'allows' element */
		Element allows = document.createElement(ELMT_ALLOWS);
		PortletWindow.State[] states = definition.getAllowedWindowStates();
		for (int j = 0; states != null && j < states.length; j++) {
			allows.appendChild(document.createElement(states[j].toString().toLowerCase()));
		}
		portlet.appendChild(allows);

		/* 'supports' element */
		Element supports = document.createElement(ELMT_SUPPORTS);
		String[] markups = definition.getSupportedMarkups();
		for (int i = 0; i < markups.length; i++) {
			Element markup = document.createElement(ELMT_MARKUP);
			markup.setAttribute(ATTR_NAME, markups[i]);
			supports.appendChild(markup);

			Mode[] modes = definition.getSupportedModes(markups[i]);
			for (int k = 0; k < modes.length; k++) {
				markup.appendChild(document.createElement(modes[k].toString().toLowerCase()));
			}
		}
		portlet.appendChild(supports);

		/* 'init-param' element */
		Map parameters = definition.getParameters();
		String[] keys = (parameters == null ? new String[0]
				: (String[]) parameters.keySet().toArray(new String[parameters.size()]));
		for (int j = 0; j < keys.length; j++) {
			Element initParam = document.createElement(ELMT_INIT_PARAM);

			Element paramName = document.createElement(ELMT_PARAM_NAME);
			setElementText(paramName, keys[j]);
			initParam.appendChild(paramName);

			Element paramValue = document.createElement(ELMT_PARAM_VALUE);
			setElementText(paramValue, (String) parameters.get(keys[j]));
			initParam.appendChild(paramValue);

			portlet.appendChild(initParam);
		}

		return portlet;
	}

	private Node toNode(Document document, ConcretePortletApplicationDefinition definition) {
		/* 'concrete-portlet-app' element */
		Element crtApp = document.createElement(ELMT_CONCRETE_PORTLET_APP);
		crtApp.setAttribute(ATTR_UNIQUENAME, definition.getUniqueName());

		/* 'context-param' element */
		Map params = definition.getParameters();
		String[] keys = (params == null ? new String[0] : (String[]) params.keySet().toArray(
				new String[params.size()]));
		for (int i = 0; i < keys.length; i++) {
			/* 'context-param' */
			Element contextParam = document.createElement(ELMT_CONTEXT_PARAM);
			crtApp.appendChild(contextParam);

			/* 'param-name' */
			Element paramName = document.createElement(ELMT_PARAM_NAME);
			setElementText(paramName, keys[i]);
			contextParam.appendChild(paramName);

			/* 'param-value' */
			Element paramValue = document.createElement(ELMT_PARAM_VALUE);
			setElementText(paramValue, definition.getParameter(keys[i]));
			contextParam.appendChild(paramValue);
		}

		return crtApp;
	}

	private Node toNode(Document document, ConcretePortletDefinition definition) {
		System.out.println("concrete portlet definition = {" + definition + "}");
		/* 'concrete-portlet' element */
		Element crtPortlet = document.createElement(ELMT_CONCRETE_PORTLET);
		crtPortlet.setAttribute(ATTR_HREF, definition.getAbstractPortletName());
		crtPortlet.setAttribute(ATTR_UNIQUENAME, definition.getUniqueName());

		/* 'default-locale' element */
		Element defaultLocale = document.createElement(ELMT_DEFAULT_LOCALE);
		setElementText(defaultLocale, definition.getDefaultLocale().getLanguage());
		crtPortlet.appendChild(defaultLocale);

		/* 'language' element */
		ConcretePortletDefinition.LocaleData[] data = definition.getLocaleData();
		for (int i = 0; i < data.length; i++) {
			Element language = document.createElement(ELMT_LANGUAGE);
			language.setAttribute(ATTR_LOCALE, data[i].getLocale().getLanguage());
			crtPortlet.appendChild(language);

			/* 'title' element. */
			Element title = document.createElement(ELMT_TITLE);
			setElementText(title, data[i].getTitle());
			language.appendChild(title);

			/* 'title-short' element. */
			Element shortTitle = document.createElement(ELMT_TITLE_SHORT);
			setElementText(shortTitle, data[i].getShortTitle());
			language.appendChild(shortTitle);

			/* 'description' element. */
			Element description = document.createElement(ELMT_DESCRIPTION);
			setElementText(description, data[i].getDescription());
			language.appendChild(description);
		}

		/* 'config-param' element */
		Iterator paramKeys = definition.getParameters().keySet().iterator();
		while (paramKeys.hasNext()) {
			String key = (String) paramKeys.next();
			Element configParam = document.createElement(ELMT_CONFIG_PARAM);
			crtPortlet.appendChild(configParam);

			Element paramName = document.createElement(ELMT_PARAM_NAME);
			setElementText(paramName, key);
			configParam.appendChild(paramName);

			Element paramValue = document.createElement(ELMT_PARAM_VALUE);
			setElementText(paramValue, definition.getParameter(key));
			configParam.appendChild(paramValue);
		}

		return crtPortlet;
	}
}
