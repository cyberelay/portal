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

package org.cyberelay.portletcontainer;

import org.cyberelay.portal.util.Assert;

/**
 * A super class to facilitate implementations of a variety of specific portlet
 * container services.
 * 
 * @author Roger Tang
 * 
 */
public abstract class AbstractPortletContainerService implements PortletContainerService {
	private ServiceConfig config;

	/**
	 * @see org.cyberelay.portletcontainer.PortletContainerService#init(org.cyberelay.portletcontainer.PortletContainer)
	 */
	public final void init(ServiceConfig config) {
		if (this.config != null) {
			throw new ServiceInitializationException("Trying to initialize ["
					+ getServiceInfo()
					+ "] twice. "
					+ "Service cannot be initialized twice.");
		}
		Assert.notNull(config, "Service config cannot be null.");
		this.config = config;
		init();
	}

	protected void init() {
		// do nothing.
	}

	public String getServiceInfo() {
		String name = getServiceConfig().getServiceName();
		if (name == null || name.trim().length() == 0) {
			name = getClass().getName();
		}
		return name;
	}

	protected final ServiceConfig getServiceConfig() {
		if (config == null) {
			throw new ServiceInitializationException("Service hasn't been initialized.");
		}
		return config;
	}

	protected PortletContainer getPortletContainer() {
		return getServiceConfig().getPortletContainer();
	}

	/**
	 * Default implementation do nothing.
	 */
	public void destroy() throws PortletContainerServiceException {
		// do nothing.
	}

}
