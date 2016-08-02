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

import org.cyberelay.portal.annotation.ServiceParamTag;
import org.cyberelay.portal.annotation.ServiceTag;
import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portletcontainer.PortletContainer;
import org.cyberelay.portletcontainer.PortletContainerService;
import org.cyberelay.portletcontainer.ServiceConfig;
import org.cyberelay.portletcontainer.ServiceUnavailableException;

/**
 * @author Terry Li
 * 
 */
public class DefaultPortletContainerServiceProvider {
	private static final Logger LOG =
			LoggerFactory.getLogger(DefaultPortletContainerServiceProvider.class);

	private PortletContainer container;

	private Map<Class, PortletContainerService> singletonServices;

	public DefaultPortletContainerServiceProvider(PortletContainer container) {
		this.container = container;
		this.singletonServices = new HashMap<Class, PortletContainerService>();
	}

	public <T extends PortletContainerService> T getService(Class<T> serviceInterface) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Retrieving service [" + serviceInterface.getName() + "]...");
		}
		ServiceTag serviceTag = (ServiceTag) serviceInterface.getAnnotation(ServiceTag.class);
		if (serviceTag.singleton()) {
			PortletContainerService service = singletonServices.get(serviceInterface);
			if (service == null) {
				service = newInstance(serviceTag);
				singletonServices.put(serviceInterface, service);
			}

			return (T) service;
		}

		return (T) newInstance(serviceTag);
	}

	private PortletContainerService newInstance(ServiceTag serviceTag) {
		PortletContainerService service = null;
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			Class defaultImpl = classLoader.loadClass(serviceTag.defaultImpl());
			service = (PortletContainerService) defaultImpl.newInstance();
			service.init(newServiceConfig(serviceTag));
		} catch (ClassNotFoundException e) {
			throw new ServiceUnavailableException(
					"Cannot found default container service implementation class: ["
							+ serviceTag.defaultImpl()
							+ "]",
					e);
		} catch (InstantiationException e) {
			throw new ServiceUnavailableException(
					"Default service implementation cannot be instantiated. ["
							+ serviceTag.defaultImpl()
							+ "]",
					e);
		} catch (IllegalAccessException e) {
			throw new ServiceUnavailableException(
					"Unexpected exception during instantiated service object. ["
							+ serviceTag.defaultImpl()
							+ "]",
					e);
		}
		return service;
	}

	private ServiceConfig newServiceConfig(ServiceTag tag) {
		Map<String, String> params = new HashMap<String, String>();
		for (ServiceParamTag serviceParamTag : tag.parameters()) {
			params.put(serviceParamTag.name(), serviceParamTag.value());
		}

		return new ServiceConfigImpl(tag.name(), container, params);
	}

}
