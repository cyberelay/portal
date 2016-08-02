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

import javax.portlet.Event;
import javax.portlet.PortalContext;
import javax.portlet.Portlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;
import javax.portlet.filter.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyberelay.portal.util.IteratorEnumeration;
import org.cyberelay.portlet.PortletConfigEx;
import org.cyberelay.portletcontainer.NavigationState;
import org.cyberelay.portletcontainer.PortletApplication;
import org.cyberelay.portletcontainer.PortletApplicationConfig;
import org.cyberelay.portletcontainer.PortletContainerConstants;
import org.cyberelay.portletcontainer.PortletInvocationRequest;
import org.cyberelay.portletcontainer.PortletWindow;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Feb 2, 2008
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 666 $
 * <li>Last Update Time: $Date: 2008-03-18 10:15:24 +0000 (Tue, 18 Mar 2008) $
 * </ul>
 * 
 */
public class PortletInvocationRequestImpl implements
		PortletInvocationRequest,
		PortletContainerConstants {

	private String invocationID;
	private PortletWindow portletWindow;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private PortletApplication application;

	public PortletInvocationRequestImpl(PortletApplication application, HttpServletRequest request,
			HttpServletResponse response) {
		this.application = application;
		this.request = request;
		this.response = response;

		invocationID = (String) request.getAttribute(INVOCATION_ID);
		portletWindow = (PortletWindow) request.getAttribute(PORTLET_WINDOW);
	}

	public HttpServletRequest getPortalRequest() {
		return request;
	}

	public HttpServletResponse getPortalResponse() {
		return response;
	}

	public Portlet getPortletInstance() {
		return getApplicationConfig().getPortlet(getPortletName());
	}

	public PortletWindow getPortletWindow() {
		return portletWindow;
	}

	public PortletContext getPortletContext() {
		return application.getPortletContext();
	}

	public ServletContext getServletContext() {
		return application.getServletContext();
	}

	public String getInvocationID() {
		return invocationID;
	}

	public boolean isForward() {
		// TODO FIXME
		return false;
	}

	public boolean isTargetPortlet() {
		return portletWindow.isTargetPortlet(request);
	}

	public boolean isPortletModeAllowed(PortletMode mode) {
		return portletWindow.isPortletModeAllowed(request, mode);
	}

	public boolean isWindowStateAllowed(WindowState state) {
		return portletWindow.isWindowStateAllowed(request, state);
	}

	public NavigationState getNavigationState() {
		return portletWindow.getNavigationState(request);
	}

	public PortletConfigEx getPortletConfig() {
		return getApplicationConfig().getPortletConfig(getPortletName());
	}

	private PortletApplicationConfig getApplicationConfig() {
		return application.getApplicationConfig();
	}

	private String getPortletName() {
		return portletWindow.getPortletName();
	}

	public PortletPreferences getPortletPreferences() {
		return portletWindow.getPortletPreferences(request);
	}

	public PortalContext getPortalContext() {
		return portletWindow.getPortalContext(request);
	}

	public String getClientAcceptedContentType() {
		return portletWindow.getClientAcceptedContentType(request);
	}

	public Enumeration<String> getClientAcceptedContentTypes() {
		return new IteratorEnumeration<String>(portletWindow.getClientAcceptedContentTypes(request));
	}

	public PortletURL createActionURL() {
		return portletWindow.createActionURL(request);
	}

	public PortletURL createRenderURL() {
		return portletWindow.createRenderURL(request);
	}

	public ResourceURL createResourceURL() {
		return portletWindow.createResourceURL(request);
	}

	public String getCacheability() {
		return portletWindow.getCacheability(request);
	}

	public String getResourceID() {
		return portletWindow.getResourceID(request);
	}

	public FilterChain getFilterChain() {
		if (ID_PROCESS_ACTION.equalsIgnoreCase(invocationID)) {
			return getApplicationConfig().getActionFilterChain(getPortletName());
		} else if (ID_PROCESS_EVENT.equalsIgnoreCase(invocationID)) {
			return getApplicationConfig().getEventFilterChain(getPortletName());
		} else if (ID_PROCESS_RESOURCE.equalsIgnoreCase(invocationID)) {
			return getApplicationConfig().getResourceFilterChain(getPortletName());
		} else if (ID_RENDER_HEADER.equalsIgnoreCase(invocationID)) {
			return getApplicationConfig().getRenderFilterChain(getPortletName());
		} else if (ID_RENDER_MARKUP.equalsIgnoreCase(invocationID)) {
			return getApplicationConfig().getRenderFilterChain(getPortletName());
		}

		return null;
	}

	public Event getEvent() {
		return getEventQueue().peek();
	}

	public void sendEvent(Event event) {
		getEventQueue().offer(event);
	}

	/* ==================================================== */

	private EventQueue getEventQueue() {
		EventQueue result = (EventQueue) request.getAttribute(EVENT_QUEUE);
		if (result == null) {
			result = new EventQueue();
			getPortalRequest().setAttribute(EVENT_QUEUE, result);
		}
		return result;
	}
}