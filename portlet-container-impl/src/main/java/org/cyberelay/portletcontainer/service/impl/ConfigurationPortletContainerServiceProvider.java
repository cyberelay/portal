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

package org.cyberelay.portletcontainer.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portletcontainer.PortletContainer;
import org.cyberelay.portletcontainer.PortletContainerException;
import org.cyberelay.portletcontainer.PortletContainerService;
import org.cyberelay.portletcontainer.PortletContainerServiceException;
import org.cyberelay.portletcontainer.ServiceUnavailableException;
import org.cyberelay.portletcontainer.service.PortletContainerServiceProvider;

/**
 * @author Roger Tang
 * 
 */
public class ConfigurationPortletContainerServiceProvider implements
		PortletContainerServiceProvider {
	private static final Logger LOG =
			LoggerFactory.getLogger(ConfigurationPortletContainerServiceProvider.class);

	private DefaultPortletContainerServiceProvider annotationProvider;
	private XMLPortletContainerServiceInitializer xmlProvider;

	private Map<Class, PortletContainerService> services =
			new HashMap<Class, PortletContainerService>();

	public ConfigurationPortletContainerServiceProvider(PortletContainer container)
			throws PortletContainerException {
		annotationProvider = new DefaultPortletContainerServiceProvider(container);
		try {
			xmlProvider = new XMLPortletContainerServiceInitializer(container);
		} catch (Exception e) {
			LOG.info("Cannot initialize container services from XML!", e);
		}
	}

	public void destroy() {
		for (PortletContainerService service : services.values()) {
			try {
				service.destroy();
			} catch (PortletContainerServiceException e) {
				LOG.error("Fail to destory [" + service.getServiceInfo() + "]!", e);
			}
		}
	}

	/**
	 * 
	 * @see org.cyberelay.portletcontainer.service.PortletContainerServiceProvider#getService(java.lang.Class)
	 */
	public <T extends PortletContainerService> T getService(Class<T> serviceInterface)
			throws ServiceUnavailableException {
		PortletContainerService service = services.get(serviceInterface);
		if (service != null) {
			return (T) service;
		}
		if (xmlProvider != null) {
			service = xmlProvider.getService(serviceInterface);
		}
		if (service == null) {
			service = annotationProvider.getService(serviceInterface);
		}
		if (service != null) {
			services.put(serviceInterface, service);
		}
		return (T) service;
	}
}
