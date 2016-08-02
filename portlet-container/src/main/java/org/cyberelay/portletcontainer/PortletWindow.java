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

import java.util.Iterator;

import javax.portlet.PortalContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;

import org.cyberelay.portlet.PortletConfigEx;

/**
 * An abstraction of portlet window embedded in a portal page.
 * 
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 677 $
 * <li>Last Update Time: $Date: 2008-03-20 15:22:28 +0000 (Thu, 20 Mar 2008) $
 * </ul>
 * 
 */
public interface PortletWindow {

	/**
	 * Retrieve portlet window unique ID
	 * 
	 * @return portlet window unique ID
	 */
	String getUniqueID();

	/**
	 * Retrieve the context path of corresponding portlet application
	 * 
	 * @return
	 */
	String getPortletContextPath();

	/**
	 * 
	 * @return name of portlet
	 */
	String getPortletName();

	/**
	 * Check if this is the target portlet window of the given portal request.
	 * 
	 * @param request
	 *            portal HTTP request.
	 * @return
	 */
	boolean isTargetPortlet(HttpServletRequest request);

	/**
	 * Creates a portlet URL targeting this portlet window. If no portlet mode,
	 * window state or security modifier is set in the PortletURL the current
	 * values are preserved. If a request is triggered by the PortletURL, it
	 * results in a render request.
	 * <p>
	 * The returned URL can be further extended by adding portlet-specific
	 * parameters and portlet modes and window states.
	 * <p>
	 * The created URL will per default not contain any parameters of the
	 * current render request.
	 * 
	 * @return a portlet render URL
	 * @throws java.lang.IllegalStateException
	 *             if the cacheability level of the resource URL triggering this
	 *             <code>serveResource</code> call, or one of the parent
	 *             calls, have defined a stricter cachability level.
	 */
	PortletURL createRenderURL(HttpServletRequest request) throws IllegalStateException;

	/**
	 * Creates a portlet URL targeting the portlet. If no portlet mode, window
	 * state or security modifier is set in the PortletURL the current values
	 * are preserved. If a request is triggered by the PortletURL, it results in
	 * an action request.
	 * <p>
	 * The returned URL can be further extended by adding portlet-specific
	 * parameters and portlet modes and window states.
	 * <p>
	 * The created URL will per default not contain any parameters of the
	 * current render request.
	 * 
	 * @return a portlet action URL
	 * @throws java.lang.IllegalStateException
	 *             if the cacheability level of the resource URL triggering this
	 *             <code>serveResource</code> call, or one of the parent
	 *             calls, have defined a stricter cachability level.
	 */
	PortletURL createActionURL(HttpServletRequest request) throws IllegalStateException;

	/**
	 * Creates a portlet URL targeting the portlet. If no security modifier is
	 * set in the PortletURL the current values are preserved. The current
	 * render parameters, portlet mode and window state are preserved.
	 * <p>
	 * If a request is triggered by the PortletURL, it results in a serve
	 * resource request of the <code>ResouceServingPortlet</code> interface.
	 * <p>
	 * The returned URL can be further extended by adding portlet-specific
	 * parameters .
	 * <p>
	 * The created URL will per default contain the current render request and
	 * supports markup that contain unrestricted <code>ResourceURLs</code>,
	 * <code>PortletURLs</code>, and <code>FragmentURLs</code>. This URL
	 * is equivalent to a resource URL created with
	 * <code>createResourceURL(PAGE)</code>.
	 * 
	 * @return a portlet resource URL
	 * @throws java.lang.IllegalStateException
	 *             if the cacheability level of the resource URL triggering this
	 *             <code>serveResource</code> call, or one of the parent
	 *             calls, have defined a stricter cachability level.
	 */
	ResourceURL createResourceURL(HttpServletRequest request) throws IllegalStateException;

	/**
	 * If this is the target portlet window and the given request is of portlet
	 * resource request type, the returned value would be the requesting
	 * resource ID, otherwise the method would return null.
	 * 
	 * @param request
	 * @return
	 */
	String getResourceID(HttpServletRequest request);

	/**
	 * If this is the target portlet window and the given request is of portlet
	 * resource request type, the returned value would be the cacheability of
	 * request, otherwise the method would return null.
	 * 
	 * @param request
	 * @return
	 */
	String getCacheability(HttpServletRequest request);

	/**
	 * Retrieves the navigation state of this portlet window.
	 * 
	 * @param request
	 * @return
	 */
	NavigationState getNavigationState(HttpServletRequest request);

	/**
	 * Retrieves the portlet preferences object associated with this portlet
	 * window
	 * 
	 * @param request
	 * @return
	 */
	PortletPreferences getPortletPreferences(HttpServletRequest request);

	PortalContext getPortalContext(HttpServletRequest request);

	/**
	 * Retrieve portlet config object associated with this portlet window.
	 * 
	 * @return
	 */
	PortletConfigEx getPortletConfig();

	/**
	 * Retrieve the client's preferred content type for the given portal
	 * request.
	 * 
	 * @param request
	 *            Portal HTTP request which may be the one originally received
	 *            by AggregationServlet or a request wrapper object that wraps
	 *            the original one.
	 * @return
	 */
	String getClientAcceptedContentType(HttpServletRequest request);

	/**
	 * Retrieves a list of content types that client supports for the given
	 * portal request.
	 * 
	 * @param request
	 * @return
	 */
	Iterator<String> getClientAcceptedContentTypes(HttpServletRequest request);

	/**
	 * Checks if the given mode is allowed for this portlet window
	 * 
	 * @param mode
	 * @return
	 */
	boolean isPortletModeAllowed(HttpServletRequest request, PortletMode mode);

	/**
	 * Checks if the given window state is allowed for this portlet window
	 * 
	 * @param state
	 * @return
	 */
	boolean isWindowStateAllowed(HttpServletRequest request, WindowState state);
}
