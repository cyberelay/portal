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
package org.cyberelay.portal.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.cyberelay.portal.PageDefinition;
import org.cyberelay.portal.PortletWindowDefinition;
import org.cyberelay.portal.descriptor.model.ContainerPaneType;
import org.cyberelay.portal.descriptor.model.MarkupType;
import org.cyberelay.portal.descriptor.model.PageType;
import org.cyberelay.portal.descriptor.model.PortalDefType;
import org.cyberelay.portal.descriptor.model.PortletContextType;
import org.cyberelay.portal.descriptor.model.PortletPaneType;
import org.cyberelay.portal.descriptor.model.PortletWindowType;
import org.cyberelay.portal.descriptor.model.TemplateType;
import org.cyberelay.portal.impl.AbstractPortletWindowDefinition;
import org.cyberelay.portal.impl.PageDefinitionImpl;
import org.cyberelay.portal.impl.PortletWindowDefinitionImpl;
import org.cyberelay.portal.ui.impl.PortletPaneImpl;
import org.cyberelay.portal.ui.impl.Template;
import org.cyberelay.portal.ui.impl.ThemeImpl;
import org.cyberelay.portal.ui.impl.UIContainerImpl;
import org.cyberelay.portal.util.ThreadAttributesManager;
import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * @author Terry Li
 * 
 */
class PortalDefinitionParser {
	private static final Logger LOG = LoggerFactory.getLogger(PortalDefinitionParser.class);

	private Map<String, Template> templates;
	private Map<String, PortletContextType> portletContexts;
	private Map<String, PortletWindowType> portletWindows;

	private void parseSubordinate(PortalDefType portalDef) {
		templates = new HashMap<String, Template>();
		portletContexts = new HashMap<String, PortletContextType>();
		portletWindows = new HashMap<String, PortletWindowType>();

		for (TemplateType template : portalDef.getTemplate()) {
			Template templateImpl = new Template(template.getUniqueId());
			for (MarkupType markup : template.getMarkup()) {
				templateImpl.setPath(markup.getName(), markup.getPath());
			}
			templates.put(templateImpl.getUniqueName(), templateImpl);
		}

		for (PortletContextType portletContext : portalDef.getPortletContext()) {
			portletContexts.put(portletContext.getUniqueId(), portletContext);
		}

		for (PortletWindowType portletWindow : portalDef.getPortletWindow()) {
			portletWindows.put(portletWindow.getUniqueId(), portletWindow);
		}
	}

	public Map<String, PageDefinition> parse(File definitionFile) throws PortalDefinitionParsingException {
		InputStream input = null;
		try {
			input = new FileInputStream(definitionFile);
			return parse(input);
		} catch (FileNotFoundException e) {
			throw new PortalDefinitionParsingException("File [" + definitionFile.getAbsolutePath() + "] not found!", e);
		} catch (JAXBException e) {
			throw new PortalDefinitionParsingException("Fail to parse file [" + definitionFile.getAbsolutePath() + "]!", e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					LOG.error("File [" + definitionFile.getAbsolutePath() + "] close error!", e);
				}
			}
		}
	}

	/**
	 * 
	 * @param def
	 * @return
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 * @throws JAXBException
	 */
	public Map<String, PageDefinition> parse(InputStream input) throws JAXBException {		
		PortalDefType portalDef = unmarshal(input);

		parseSubordinate(portalDef);

		Map<String, PageDefinition> pages = new HashMap<String, PageDefinition>();
		for (PageType page : portalDef.getPage()) {
			ThemeImpl theme = new ThemeImpl(templates.get(page.getTheme().getTemplateRef()));
			theme.setRootPane(convertContainer(page.getTheme().getRootContainerPane(), page));						
			PageDefinitionImpl pageDefinition = new PageDefinitionImpl(page.getUniqueId(), theme);
			pages.put(page.getUniqueId(), pageDefinition);
		}
		
		return pages;
	}

	private PortalDefType unmarshal(InputStream input) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance("org.cyberelay.portal.descriptor.model");
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		JAXBElement element = (JAXBElement) unmarshaller.unmarshal(input);

		PortalDefType portalDef = (PortalDefType) element.getValue();
		return portalDef;
	}

	private UIContainerImpl convertContainer(ContainerPaneType container, PageType page) {
		UIContainerImpl containerImpl = new UIContainerImpl(templates.get(container.getTemplateRef()));
		containerImpl.setActive(Boolean.TRUE.equals(container.isActive()));
		if(container.getOrdinal() != null){
			containerImpl.setOrdinal(container.getOrdinal());
		}
		containerImpl.setWidth(container.getWidth());
		containerImpl.setOrientation(container.getOrientation());
		for (ContainerPaneType subContainer : container.getContainerPane()) {
			containerImpl.addChild(convertContainer(subContainer, page));
		}
		for (PortletPaneType portletPane : container.getPortletPane()) {
			containerImpl.addChild(convertPane(portletPane, page.getUniqueId()));
		}

		return containerImpl;
	}

	private PortletPaneImpl convertPane(PortletPaneType portletPane, String pageID) {
		if (portletPane.getPortletWindow() == null) {
			portletPane.setPortletWindow(portletWindows.get(portletPane.getPortletWindowRef().getRefId()));
		}
		PortletWindowDefinition window = createPortletWindow(portletPane, pageID);
		PortletPaneImpl paneImpl = new PortletPaneImpl(templates.get(portletPane.getTemplateRef()), window);
		return paneImpl;
	}

	private PortletWindowDefinitionImpl createPortletWindow(PortletPaneType portletPane,
			String pageID) {
		PortletWindowDefinitionImpl definitionImpl = new PortletWindowDefinitionImpl(portletPane.getPortletWindow()
				.getUniqueId(), pageID);
		definitionImpl.setPortletName(portletPane.getPortletWindow().getPortletName());
		definitionImpl.setPortletContextPath(portletContexts.get(portletPane.getPortletWindow().getPortletContextRef())
				.getContextPath());
		return definitionImpl;
	}

}
