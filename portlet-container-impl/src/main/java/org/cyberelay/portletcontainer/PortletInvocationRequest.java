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

import org.cyberelay.portlet.PortletConfigEx;

/**
 * @author Roger Tang
 * 
 */
public interface PortletInvocationRequest {

	String INVOCATION_ID = "org.cyberelay.portlet.invocation.id";

	String ID_RENDER_MARKUP = "render.markup";

	String ID_RENDER_HEADER = "render.header";

	String ID_PROCESS_RESOURCE = "serve-resource";

	String ID_PROCESS_ACTION = "portlet.action";

	String ID_PROCESS_EVENT = "portlet.event";

	String ID_LOGOUT = "logout";

	String getInvocationID();

	/**
	 * 
	 * @see org.cyberelay.portletcontainer.impl.PortletApplicationRequest#getPortletWindow()
	 */
	PortletWindow getPortletWindow();

	/**
	 * Retrieve the HTTP request dispatched by portal application.
	 * 
	 * @return
	 */
	HttpServletRequest getPortalRequest();

	/**
	 * Retrieve the HTTP response dispatched by portal application.
	 * 
	 * @return
	 */
	HttpServletResponse getPortalResponse();

	NavigationState getNavigationState();

	/**
	 * Check if this invocation request is dispatched by Portal Application
	 * via request-forwarding
	 * 
	 * @return
	 */
	boolean isForward();

	/**
	 * Check if this is the target portlet window of portal request.
	 * 
	 * @return
	 */
	boolean isTargetPortlet();
	
	boolean isPortletModeAllowed(PortletMode mode);
	
	boolean isWindowStateAllowed(WindowState state);

	Portlet getPortletInstance();

	/**
	 * Retrieves the servlet context of portlet application.
	 * @return
	 */
	ServletContext getServletContext();

	PortletContext getPortletContext();

	PortletConfigEx getPortletConfig();

	PortletPreferences getPortletPreferences();

	PortalContext getPortalContext();

	FilterChain getFilterChain();

	PortletURL createRenderURL();

	PortletURL createActionURL();

	ResourceURL createResourceURL();

	String getClientAcceptedContentType();

	Enumeration<String> getClientAcceptedContentTypes();

	Event getEvent();

	void sendEvent(Event event);

	String getResourceID();
	
	String getCacheability();

}
