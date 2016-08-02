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

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jetspeed.portlet.AccessDeniedException;
import org.apache.jetspeed.portlet.Client;
import org.apache.jetspeed.portlet.PortletData;
import org.apache.jetspeed.portlet.PortletRequest;
import org.apache.jetspeed.portlet.PortletSession;
import org.apache.jetspeed.portlet.PortletSettings;
import org.apache.jetspeed.portlet.PortletWindow;
import org.apache.jetspeed.portlet.User;
import org.apache.jetspeed.portlet.Portlet.Mode;
import org.apache.jetspeed.portlet.Portlet.ModeModifier;
import org.cyberelay.portal.util.Assert;
import org.cyberelay.portal.util.StringUtil;
import org.cyberelay.portletcontainer.PortletContainer;
import org.cyberelay.portletcontainer.legacy.Constants;
import org.cyberelay.portletcontainer.legacy.PortletApplication;
import org.cyberelay.portletcontainer.legacy.PortletWindowEx;
import org.cyberelay.portletcontainer.legacy.service.ClientInfoService;
import org.cyberelay.portletcontainer.legacy.service.PortletApplicationService;
import org.cyberelay.portletcontainer.legacy.service.PortletDataService;
import org.cyberelay.portletcontainer.legacy.service.PortletNavigationStateService;
import org.cyberelay.portletcontainer.legacy.service.UserInfoService;

/**
 * 
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jan 21, 2008
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 678 $
 * <li>Last Update Time: $Date: 2008-03-21 03:31:15 +0000 (Fri, 21 Mar 2008) $
 * </ul>
 * 
 */
public class PortletRequestImpl extends HttpServletRequestWrapper implements
		PortletRequest,
		Constants {
	private HttpServletResponse response;
	private PortletApplication portletApplication;
	private PortletContainer container;
	private PortletWindowEx windowEx;
	private PortletSession portletSession;
	private PortletWindow window;
	private Map parameters;

	public PortletRequestImpl(HttpServletRequest request, HttpServletResponse response,
			PortletWindowEx window) {
		super(request);
		Assert.notNull(window, "Portlet Window cannot be null!");
//		container = (PortletContainer) request.getAttribute(PortletContainer.PORTLET_CONTAINER);
		portletApplication = container.getService(PortletApplicationService.class).getPortletApplication(
				window);
		Assert.notNull(portletApplication);
		this.windowEx = window;
		this.response = response;
	}

	private PortletNavigationStateService getNavigationStateService() {
		return container.getService(PortletNavigationStateService.class);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletRequest#getMode()
	 */
	public Mode getMode() {
		return getNavigationStateService().getMode(getHttpServletRequest(), windowEx);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletRequest#getPreviousMode()
	 */
	public Mode getPreviousMode() {
		return getNavigationStateService().getPreviousMode(getHttpServletRequest(), windowEx);
	}

	public void setModeModifier(ModeModifier modifier) throws AccessDeniedException {
		getNavigationStateService().setModeModifier(getHttpServletRequest(), windowEx, modifier);
	}

	public Client getClient() {
		return container.getService(ClientInfoService.class).getClient(getHttpServletRequest());
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletRequest#getWindow()
	 */
	public PortletWindow getWindow() {
		if (window == null) {
			window = new PortletWindowImpl(getHttpServletRequest(), windowEx);
		}

		return window;
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletRequest#getData()
	 */
	public PortletData getData() {
		return container.getService(PortletDataService.class).getPortletData(getUser(), windowEx);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletRequest#getPortletSession()
	 */
	public PortletSession getPortletSession() {
		return getPortletSession(true);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletRequest#getPortletSession(boolean)
	 */
	public PortletSession getPortletSession(boolean create) {
		if (portletSession != null) {
			return portletSession;
		}

		HttpSession httpSession = super.getSession(create);

		if (httpSession != null) {
			portletSession = new PortletSessionImpl(httpSession, windowEx);
		}

		return portletSession;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getSession()
	 */
	public HttpSession getSession() {
		return getPortletSession(true);
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getSession(boolean)
	 */
	public HttpSession getSession(boolean create) {
		return getPortletSession(create);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletRequest#getUser()
	 */
	public User getUser() {
		return container.getService(UserInfoService.class).getUser(getHttpServletRequest());
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletRequest#getPortletSettings()
	 */
	public PortletSettings getPortletSettings() {
		return portletApplication.getPortletSettings(windowEx.getPortletName());
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletRequest#invalidateCache()
	 */
	public void invalidateCache() {
		this.parameters = null;
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletRequest#getParameter(java.lang.String)
	 */
	public String getParameter(String name) {
		if (parameters == null) {
			initParameters();
		}
		String[] result = (String[]) parameters.get(name);

		return (result == null || result.length == 0) ? null : result[0];
	}

	private HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) getRequest();
	}

	private boolean isTargetPortletWindow() {
		// TODO
		return true;
	}

	private void initParameters() {
		this.parameters = new HashMap();
		Enumeration paramNames = getHttpServletRequest().getParameterNames();
		String shortName = null;
		String namespace = windowEx.getUniqueID();
		String namespacedName = null;

		if ("GET".equals(getMethod()) || ("POST".equals(getMethod()) && isTargetPortletWindow())) {
			while (paramNames.hasMoreElements()) {
				shortName = (String) paramNames.nextElement();
				namespacedName = namespace + shortName;

				if (shortName.startsWith(namespace)) {
					namespacedName = shortName;
					shortName = StringUtil.replaceFirst(shortName, namespace, "");
				}

				if (!parameters.containsKey(shortName)) {
					String[] value1 = getHttpServletRequest().getParameterValues(shortName);
					String[] value2 = getHttpServletRequest().getParameterValues(namespacedName);
					parameters.put(shortName, merge(value1, value2));
				}
			}
		}
	}

	private static String[] merge(String[] value1, String[] value2) {
		if (value1 == null && value2 == null) {
			return new String[0];
		} else if (value1 == null && value2 != null) {
			return value2;
		} else if (value1 != null && value2 == null) {
			return value1;
		} else {
			String[] result = new String[value1.length + value2.length];
			System.arraycopy(value1, 0, result, 0, value1.length);
			System.arraycopy(value2, 0, result, value1.length, value2.length);
			return result;
		}
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletRequest#getParameterMap()
	 */
	public Map getParameterMap() {
		if (parameters == null) {
			initParameters();
		}
		return Collections.unmodifiableMap(parameters);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletRequest#getParameterNames()
	 */
	public Enumeration getParameterNames() {
		if (parameters == null) {
			initParameters();
		}
		return new Vector(parameters.keySet()).elements();
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletRequest#getParameterValues(java.lang.String)
	 */
	public String[] getParameterValues(String name) {
		if (parameters == null) {
			initParameters();
		}
		String[] result = (String[]) parameters.get(name);
		return result == null ? new String[0] : result;
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletRequest#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String name) {
		/* below are buil-in attributes of portlet request. */
		if (PORTLET_WINDOW_EX.equals(name)) {
			return windowEx;
		} else if (PORTLET_REQUEST.equals(name)) {
			return this;
		} else if (PORTLET_SESSION.equals(name)) {
			return getPortletSession(false);
		} else if (PORTLET_SESSION_CREATE.equals(name)) {
			return getPortletSession(true);
		} else if (PORTLET_RESPONSE.equals(name)) {
			// TODO
			return null;
		} else if (PORTLET_APPLICATION.equals(name)) {
			return portletApplication;
		} else if (HTTP_REQUEST.equals(name)) {
			return getHttpServletRequest();
		} else if (HTTP_RESPONSE.equals(name)) {
			return response;
		} else if (HTTP_SESSION.equals(name)) {
			return getHttpServletRequest().getSession(false);
		} else if (HTTP_SESSION_CREATE.equals(name)) {
			return getHttpServletRequest().getSession(true);
		}

		Object result = super.getAttribute(toFullName(name));
		return result == null ? super.getAttribute(name) : result;
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletRequest#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	public void setAttribute(String name, Object value) {
		super.setAttribute(toFullName(name), value);
	}

	private String toFullName(String name) {
		return windowEx.getUniqueID() + name;
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletRequest#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String name) {
		super.removeAttribute(toFullName(name));
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletRequest#getAttributeNames()
	 */
	public Enumeration getAttributeNames() {
		Vector names = new Vector();
		Enumeration keys = super.getAttributeNames();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			if (key.startsWith(windowEx.getUniqueID())) {
				key = StringUtil.replaceFirst(key, windowEx.getUniqueID(), "");
			}
			names.add(key);
		}
		return names.elements();
	}
}
