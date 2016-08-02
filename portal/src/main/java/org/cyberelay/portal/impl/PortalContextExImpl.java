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

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.cyberelay.portal.Client;
import org.cyberelay.portal.PortalApplication;
import org.cyberelay.portal.PortalConstants;
import org.cyberelay.portal.PortalContextEx;
import org.cyberelay.portal.util.ReflectionUtil;

/**
 * @author Roger Tang
 * 
 */
public class PortalContextExImpl implements PortalContextEx {
	private PortalApplication application;
	private Properties properties;
	private Set<PortletMode> supportedModes;
	private Set<WindowState> supportedStates;
	private String contextPath;

	public PortalContextExImpl(PortalApplication application) {
		this.application = application;

		properties = new Properties();

		supportedModes = new HashSet<PortletMode>();
		supportedModes.add(PortletMode.EDIT);
		supportedModes.add(PortletMode.HELP);
		supportedModes.add(PortletMode.HELP);

		supportedStates = new HashSet<WindowState>();
		supportedStates.add(WindowState.MAXIMIZED);
		supportedStates.add(WindowState.MINIMIZED);
		supportedStates.add(WindowState.NORMAL);

		contextPath = (String) ReflectionUtil.invoke(getServletContext(), "getContextPath");
	}

	public String getPortalInfo() {
		return "Cyberelay Portal Server V1.0";
	}

	public void setProperty(String name, String value) {
		properties.setProperty(name, value);
	}

	public String getProperty(String name) {
		return properties.getProperty(name);
	}

	public Enumeration<String> getPropertyNames() {
		return (Enumeration<String>) properties.propertyNames();
	}

	public Enumeration<PortletMode> getSupportedPortletModes() {
		return Collections.enumeration(supportedModes);
	}

	public Enumeration<WindowState> getSupportedWindowStates() {
		return Collections.enumeration(supportedStates);
	}

	public boolean isPortletModeSupported(PortletMode mode) {
		return supportedModes.contains(mode);
	}

	public boolean isWindowStateSupported(WindowState state) {
		return supportedStates.contains(state);
	}

	public String getClientAcceptedContentType(HttpServletRequest request) {
		return getClient(request).getAcceptedContentType();
	}

	public Iterator<String> getClientAcceptedContentTypes(HttpServletRequest request) {
		return getClient(request).getAcceptedContentTypes();
	}

	public String getContextPath() {
		return contextPath;
	}

	/* =============================================================================== */

	private ServletContext getServletContext() {
		return application.getPortalServletContext();
	}

	private Client getClient(HttpServletRequest request) {
		return (Client) request.getAttribute(PortalConstants.PORTAL_REQUESTING_CLIENT);
	}
}
