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
package org.cyberelay.portletcontainer.impl;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;

import org.cyberelay.portal.util.Assert;
import org.cyberelay.portletcontainer.PortletApplication;
import org.cyberelay.portletcontainer.PortletApplicationConfig;
import org.cyberelay.portletcontainer.PortletContainerConstants;
import org.cyberelay.portletcontainer.PortletContainerRuntimeException;
import org.cyberelay.portletcontainer.impl.config.PortletApplicationConfigImpl;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jul 12, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 634 $
 * <li>Last Update Time: $Date: 2008-03-11 10:08:09 +0000 (Tue, 11 Mar 2008) $
 * </ul>
 * 
 */
public class PortletApplicationImpl implements PortletApplication, PortletContainerConstants {
	private ServletContext servletContext;
	private PortletContext portletContext;
	private PortletApplicationConfig config;

	public PortletApplicationImpl(ServletContext servletContext) {
		Assert.notNull(servletContext, "Portlet application servlet context cannot be NULL!");

		this.servletContext = servletContext;
		this.portletContext = new PortletContextImpl(this);

		initApplicationConfig();
	}

	private void initApplicationConfig() {
		try {
			config = new PortletApplicationConfigImpl(portletContext);
		} catch (PortletException e) {
			throw new PortletContainerRuntimeException("failed to load /WEB-INF/portlet.xml!", e);
		}
	}

	/**
	 * @see org.cyberelay.portletcontainer.PortletApplication#getServletContext()
	 */
	public ServletContext getServletContext() {
		return servletContext;
	}

	public void destroy() {
		config.destroy();
	}

	public PortletContext getPortletContext() {
		return portletContext;
	}

	public PortletApplicationConfig getApplicationConfig() {
		return config;
	}
}
