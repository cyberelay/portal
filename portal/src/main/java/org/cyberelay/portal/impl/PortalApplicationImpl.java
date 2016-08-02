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

package org.cyberelay.portal.impl;

import javax.servlet.ServletContext;

import org.cyberelay.portal.PortalApplication;
import org.cyberelay.portal.PortalConfiguration;
import org.cyberelay.portal.PortalConstants;
import org.cyberelay.portal.PortalContextEx;
import org.cyberelay.portal.service.PortalApplicationService;
import org.cyberelay.portal.service.PortalApplicationServiceProvider;
import org.cyberelay.portal.service.impl.DefaultPortalApplicationServiceProvider;
import org.cyberelay.portal.util.Assert;
import org.cyberelay.portal.util.ObjectInstantiationException;
import org.cyberelay.portal.util.ReflectionUtil;
import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portletcontainer.PortletContainer;
import org.cyberelay.portletcontainer.PortletContainerException;

/**
 * @author Roger Tang
 * 
 */
public class PortalApplicationImpl implements PortalApplication, PortalConstants {

	private static final String PORTLETCONTAINER_CLASS = "portlet.container.class";

	private static Logger LOG = LoggerFactory.getLogger(PortalApplicationImpl.class);

	private ServletContext servletContext;

	private PortalConfiguration configuration;

	private PortletContainer container;

	private PortalApplicationServiceProvider serviceProvider;

	private PortalContextEx portalContext;

	public PortalApplicationImpl(ServletContext context) throws PortletContainerException {
		Assert.notNull(context);

		servletContext = context;
		configuration = new PortalConfigurationImpl();
		serviceProvider = new DefaultPortalApplicationServiceProvider(this);
		portalContext = new PortalContextExImpl(this);

		initPortletContainer();
	}

	private void initPortletContainer() throws PortletContainerException {
		String className = configuration.getValue(PORTLETCONTAINER_CLASS);
		LOG.info(PORTLETCONTAINER_CLASS + " = [" + className + "]");
		LOG.info("Initializing portlet container [" + className + "]...");
		try {
			container = (PortletContainer) ReflectionUtil.newInstance(className);
			container.init(servletContext);
		} catch (ObjectInstantiationException e) {
			throw new PortletContainerException("Cannot instantiate portlet container!", e);
		}
		LOG.info("Portlet container [" + className + "] initialized.");
	}

	public ServletContext getPortalServletContext() {
		return servletContext;
	}

	public void destory() {
		serviceProvider.destroy();

		try {
			container.destroy();
		} catch (PortletContainerException e) {
			LOG.fatal("Destory portlet container failed!", e);
		}
	}

	public PortletContainer getPortletContainer() {
		return container;
	}

	public PortalConfiguration getConfiguration() {
		return configuration;
	}

	public PortalContextEx getPortalContext() {
		return portalContext;
	}

	public <T extends PortalApplicationService> T getService(Class<T> serviceInterface) {
		return serviceProvider.getService(serviceInterface);
	}
}
