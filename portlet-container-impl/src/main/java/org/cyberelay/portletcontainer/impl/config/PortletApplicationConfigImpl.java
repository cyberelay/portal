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

package org.cyberelay.portletcontainer.impl.config;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.portlet.Portlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletURLGenerationListener;
import javax.portlet.filter.FilterChain;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portlet.PortletConfigEx;
import org.cyberelay.portlet.descriptor.model.PortletAppType;
import org.cyberelay.portletcontainer.PortletApplicationConfig;

/**
 * @author Roger Tang
 * 
 */
public class PortletApplicationConfigImpl implements PortletApplicationConfig {
	private static String DESCRIPTOR_PACKAGE_NAME = "org.cyberelay.portlet.descriptor.model";
	private static Logger LOG = LoggerFactory.getLogger(PortletApplicationConfigImpl.class);

	private PortletAppType descriptor;

	private FilterChainFactory filterChainFactory;
	private PortletFactory portletFactory;
	private ListenerConfig listenerConfig;
	private UserAttributeConfig userAttributeConfig;

	public PortletApplicationConfigImpl(PortletContext context) throws PortletException {
		LOG.info("parsing [" + context.getPortletContextName() + "] portlet.xml...");

		descriptor = getDescriptor(context);
		listenerConfig = new ListenerConfig(descriptor);
		userAttributeConfig = new UserAttributeConfig(descriptor);
		portletFactory = new PortletFactory(descriptor, context);
		filterChainFactory = new FilterChainFactory(descriptor, context);

		LOG.info("parsing [" + context.getPortletContextName() + "] portlet.xml...Done");
	}

	/* ======================================================================================= */

	private PortletAppType getDescriptor(PortletContext context) throws PortletException {
		try {
			InputStream input = context.getResourceAsStream("/WEB-INF/portlet.xml");
			JAXBContext jContext = JAXBContext.newInstance(DESCRIPTOR_PACKAGE_NAME);
			Unmarshaller unmarshaller = jContext.createUnmarshaller();
			return((JAXBElement<PortletAppType>) unmarshaller.unmarshal(input)).getValue();
		} catch (JAXBException e) {
			throw new PortletException("Failed to parse /WEB-INF/portlet.xml file.", e);
		}
	}

	/* ======================================================================================= */

	public FilterChain getActionFilterChain(String portletName) {
		return filterChainFactory.getActionFilterChain(portletName);
	}

	public FilterChain getEventFilterChain(String portletName) {
		return filterChainFactory.getEventFilterChain(portletName);
	}

	public FilterChain getRenderFilterChain(String portletName) {
		return filterChainFactory.getRenderFilterChain(portletName);
	}

	public FilterChain getResourceFilterChain(String portletName) {
		return filterChainFactory.getResourceFilterChain(portletName);
	}

	public Set<String> getUserAttributeNames() {
		return userAttributeConfig.getUserAttributeNames();
	}

	public void destroy() {
		filterChainFactory.destroy();
		portletFactory.destroy();
	}

	public List<PortletURLGenerationListener> getListeners() {
		return listenerConfig.getListeners();
	}

	public Portlet getPortlet(String portletName) {
		return portletFactory.getPortlet(portletName);
	}

	public PortletConfigEx getPortletConfig(String portletName) {
		return portletFactory.getConfig(portletName);
	}

	public Iterator<String> getPortletNames() {
		return portletFactory.getPortletNames();
	}

}
