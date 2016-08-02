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

import java.util.Enumeration;
import java.util.Map;

import javax.portlet.PortalContext;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.WindowState;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.cyberelay.portletcontainer.NavigationState;
import org.cyberelay.portletcontainer.PortletContainerConstants;
import org.cyberelay.portletcontainer.PortletInvocationRequest;
import org.cyberelay.portletcontainer.PortletWindow;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jul 1, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 666 $
 * <li>Last Update Time: $Date: 2008-03-18 10:15:24 +0000 (Tue, 18 Mar 2008) $
 * </ul>
 * 
 */
abstract class PortletRequestImpl extends HttpServletRequestWrapper implements
		PortletRequest,
		PortletContainerConstants {
	protected PortletInvocationRequest invocationRequest;

	private PortletRequestAttributeManager attributeManager;
	private PortletSession portletSession;
	private PortletResponse portletResponse;

	public PortletRequestImpl(PortletInvocationRequest request, PortletResponse response) {
		super(request.getPortalRequest());
		invocationRequest = request;
		attributeManager = new PortletRequestAttributeManager(request);
		portletResponse = response;
	}

	protected HttpServletRequest getPortalRequest() {
		return invocationRequest.getPortalRequest();
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String name) {
		/* built-in attributes. */
		if (PORTLET_WINDOW.equals(name)) {
			return invocationRequest.getPortletWindow();
		} else if (PORTLET_CONTEXT.equals(name)) {
			return getPortletContext();
		} else if (PORTLET_INVOCATION_REQUEST.equals(name)) {
			return invocationRequest;
		} else if (PORTLET_CONFIG.equals(name)) {
			return invocationRequest.getPortletConfig();
		} else if (PORTLET_REQUEST.equals(name)) {
			return this;
		} else if (PORTLET_RESPONSE.equals(name)) {
			return portletResponse;
		} else if (HTTP_REQUEST.equals(name)) {
			return this;
		} else if (HTTP_RESPONSE.equals(name)) {
			return portletResponse;
		} else if (LIFECYCLE_PHASE.equals(name)) {
			return getLifecyclePhase();
		}

		return attributeManager.getAttribute(name);
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#getAttributeNames()
	 */
	public Enumeration<String> getAttributeNames() {
		return attributeManager.getAttributeNames();
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String name) {
		attributeManager.removeAttribute(name);
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	public void setAttribute(String name, Object o) {
		attributeManager.setAttribute(name, o);
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#getPortalContext()
	 */
	public PortalContext getPortalContext() {
		return invocationRequest.getPortalContext();
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#getPortletMode()
	 */
	public PortletMode getPortletMode() {
		return getNavigationState().getPortletMode();
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#getPortletSession()
	 */
	public PortletSession getPortletSession() {
		return getPortletSession(true);
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#getPortletSession(boolean)
	 */
	public PortletSession getPortletSession(boolean create) {
		if (portletSession != null) {
			return portletSession;
		}

		HttpSession httpSession = getPortalRequest().getSession(create);
		if (httpSession == null) {
			return null;
		}
		portletSession = new PortletSessionImpl(httpSession, invocationRequest);
		return portletSession;
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#getPreferences()
	 */
	public PortletPreferences getPreferences() {
		return invocationRequest.getPortletPreferences();
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#getProperties(java.lang.String)
	 */
	public Enumeration<String> getProperties(String name) {
		return getPortalRequest().getHeaders(name);
	}

	/**
	 * In order to serve portal specific properties, portal application should
	 * inject those properties as request headers in portal HTTP request.
	 * 
	 * For <code>PortletRequest.getProperty()</code> method call, it always be
	 * delegated to portal request's <code>getHeader()</code> method.
	 * 
	 * @see javax.portlet.PortletRequest#getProperty(java.lang.String)
	 */
	public String getProperty(String name) {
		return getPortalRequest().getHeader(name);
	}

	/**
	 * In order to serve portal specific properties, portal application should
	 * inject those properties as request headers in portal HTTP request.
	 * 
	 * For <code>PortletRequest.getPropertyNames()</code> method call, it
	 * always be delegated to portal request's <code>getHeaderNames()</code>
	 * method.
	 * 
	 * @see javax.portlet.PortletRequest#getPropertyNames()
	 */
	public Enumeration<String> getPropertyNames() {
		return getPortalRequest().getHeaderNames();
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#getResponseContentType()
	 */
	public String getResponseContentType() {
		return invocationRequest.getClientAcceptedContentType();
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#getResponseContentTypes()
	 */
	public Enumeration<String> getResponseContentTypes() {
		return invocationRequest.getClientAcceptedContentTypes();
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#getWindowId()
	 */
	public String getWindowID() {
		return getPortletWindow().getUniqueID();
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#getWindowState()
	 */
	public WindowState getWindowState() {
		return getNavigationState().getWindowState();
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#isPortletModeAllowed(javax.portlet.PortletMode)
	 */
	public boolean isPortletModeAllowed(PortletMode mode) {
		return invocationRequest.isPortletModeAllowed(mode);
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#isWindowStateAllowed(javax.portlet.WindowState)
	 */
	public boolean isWindowStateAllowed(WindowState state) {
		return invocationRequest.isWindowStateAllowed(state);
	}

	@Override
	public boolean isUserInRole(String role) {
		// TODO FIXME
		return super.isUserInRole(role);
	}

	@Override
	public String getRequestedSessionId() {
		// TODO FIXME
		return super.getRequestedSessionId();
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		// TODO FIXME
		return super.isRequestedSessionIdValid();
	}

	/**
	 * The logic embedded in this method slightly differs from
	 * <code>HttpServletRequest.getContextPath()</code>. The returned value
	 * of this method strictly equals to
	 * <code>ServletContext.getContextPath()</code> of portlet application.
	 * 
	 */
	public String getContextPath() {
		return getServletContext().getContextPath();
	}

	public Map<String, String[]> getPublicParameterMap() {
		return getNavigationState().getPublicRenderParameterMap();
	}

	public Map<String, String[]> getPrivateParameterMap() {
		return getNavigationState().getPrivateRenderParameterMap();
	}

	/* =================================================================== */

	protected PortletWindow getPortletWindow() {
		return invocationRequest.getPortletWindow();
	}

	/**
	 * Retrieves the servlet context of the portlet application in which the
	 * invoked portlet is resided.
	 * 
	 * @return
	 */
	protected ServletContext getServletContext() {
		return invocationRequest.getServletContext();
	}

	protected PortletContext getPortletContext() {
		return invocationRequest.getPortletContext();
	}

	protected NavigationState getNavigationState() {
		return invocationRequest.getNavigationState();
	}

	protected abstract String getLifecyclePhase();
}
