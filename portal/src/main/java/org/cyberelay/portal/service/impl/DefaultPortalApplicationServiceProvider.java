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

import java.util.HashMap;
import java.util.Map;

import org.cyberelay.portal.PortalApplication;
import org.cyberelay.portal.annotation.ServiceTag;
import org.cyberelay.portal.service.NoSuchServiceException;
import org.cyberelay.portal.service.PortalApplicationService;
import org.cyberelay.portal.service.PortalApplicationServiceProvider;
import org.cyberelay.portal.service.ServiceInitializationException;
import org.cyberelay.portal.service.ServiceUnavailableException;
import org.cyberelay.portal.util.ObjectInstantiationException;
import org.cyberelay.portal.util.ReflectionUtil;
import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jul 11, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 655 $
 * <li>Last Update Time: $Date: 2008-03-17 09:26:12 +0000 (Mon, 17 Mar 2008) $
 * </ul>
 * 
 */
public class DefaultPortalApplicationServiceProvider implements PortalApplicationServiceProvider {
	private static final Logger LOG =
			LoggerFactory.getLogger(DefaultPortalApplicationServiceProvider.class);

	private PortalApplication portal;

	private Map<Class, PortalApplicationService> singletonServices;

	public DefaultPortalApplicationServiceProvider(PortalApplication portal) {
		this.portal = portal;
		this.singletonServices = new HashMap<Class, PortalApplicationService>();
	}

	protected <T extends PortalApplicationService> PortalApplicationService newInstance(
			ServiceTag serviceTag) {
		PortalApplicationService service = null;
		String clazz = serviceTag.defaultImpl();
		try {
			service = (PortalApplicationService) ReflectionUtil.newInstance(clazz);
			service.init(portal);
		} catch (ObjectInstantiationException e) {
			throw new ServiceUnavailableException(
					"Cannot create service object! Implementation class = [" + clazz + "]",
					e);
		} catch (ServiceInitializationException e) {
			throw new ServiceUnavailableException("Service unavailable. [" + clazz + "]", e);
		}

		return service;
	}

	/**
	 * @see org.cyberelay.portal.service.PortalApplicationServiceProvider#getService(java.lang.Class)
	 */
	public <T extends PortalApplicationService> T getService(Class<T> serviceInterface)
			throws NoSuchServiceException, ServiceUnavailableException {
		ServiceTag serviceTag = (ServiceTag) serviceInterface.getAnnotation(ServiceTag.class);
		if (serviceTag.singleton()) {
			PortalApplicationService service =
					(PortalApplicationService) singletonServices.get(serviceInterface);
			if (service == null) {
				service = newInstance(serviceTag);
				singletonServices.put(serviceInterface, service);
			}

			return (T) service;
		}

		return (T) newInstance(serviceTag);
	}

	public void destroy() {
		for (PortalApplicationService service : this.singletonServices.values()) {
			service.destory();
		}
	}
}
