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
package org.cyberelay.portletcontainer.legacy.impl;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletContext;

import org.apache.jetspeed.portlet.Client;
import org.apache.jetspeed.portlet.PortletConfig;
import org.apache.jetspeed.portlet.PortletContext;
import org.apache.jetspeed.portlet.Portlet.Mode;
import org.apache.jetspeed.portlet.PortletWindow.State;
import org.cyberelay.portal.util.Assert;
import org.cyberelay.portletcontainer.legacy.PortletApplication;
import org.cyberelay.portletcontainer.legacy.descriptor.AbstractPortletDefinition;

/**
 * 
 * @author Roger Tang
 * 
 */
public class PortletConfigImpl implements PortletConfig {
	private PortletApplication portletApplication;
	private AbstractPortletDefinition abstractPortletDefinition;

	public PortletConfigImpl(PortletApplication portletApplication,
			AbstractPortletDefinition abstractPortletDefinition) {
		Assert.notNull(portletApplication);
		Assert.notNull(abstractPortletDefinition);

		this.portletApplication = portletApplication;
		this.abstractPortletDefinition = abstractPortletDefinition;
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletConfig#getName()
	 */
	public String getName() {
		return abstractPortletDefinition.getUniqueName();
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletConfig#supports(org.apache.jetspeed.portlet.PortletWindow.State)
	 */
	public boolean supports(State state) {
		return abstractPortletDefinition.isStateAllowed(state);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletConfig#supports(org.apache.jetspeed.portlet.Portlet.Mode,
	 *      org.apache.jetspeed.portlet.Client)
	 */
	public boolean supports(Mode mode, Client client) {
		return abstractPortletDefinition.isModeSupported(client, mode);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletConfig#getContext()
	 */
	public PortletContext getContext() {
		return portletApplication.getPortletContext();
	}

	/**
	 * @see javax.servlet.ServletConfig#getServletName()
	 */
	public String getServletName() {
		return getName();
	}

	/**
	 * @see javax.servlet.ServletConfig#getServletContext()
	 */
	public ServletContext getServletContext() {
		return getContext();
	}

	/**
	 * @see javax.servlet.ServletConfig#getInitParameter(java.lang.String)
	 */
	public String getInitParameter(String parameterName) {
		return abstractPortletDefinition.getParameter(parameterName);
	}

	/**
	 * @see javax.servlet.ServletConfig#getInitParameterNames()
	 */
	public Enumeration getInitParameterNames() {
		return new Hashtable(abstractPortletDefinition.getParameters()).keys();
	}

}
