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

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * PortletContainer is a component residing in portal application which acts
 * as a broker between portal and portlet application. It's the responsibility
 * of portal application to create portlet container and manage its life cycle.
 * As a broker, PortletContainer's main responsibilities include:
 * 
 * <li> Bootstrap and manage <code>PortletContainerServices</code>;
 * <li> Invoke the portlets running in portlet applications;
 * 
 * PortletContainerService can be used to extend the functionalities provided by
 * container
 * 
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 617 $
 * <li>Last Update Time: $Date: 2008-03-03 10:04:07 +0000 (Mon, 03 Mar 2008) $
 * </ul>
 * 
 */
public interface PortletContainer {

	/**
	 * Used to retrieve an PortletContainer instance from portal request by
	 * calling <code>HttpServletRequest.getAttribute()</code> method.
	 * <p>
	 * The value is
	 * <code>org.cyberelay.portlet.container</code>.
	 * 
	 */
	String PORTLET_CONTAINER = "org.cyberelay.portlet.container";

	/**
	 * Shuts down the container. After calling this method it is no longer valid
	 * to call any method on this object.
	 * 
	 * @throws PortletContainerException
	 *             if an error occurs while shutting down the container
	 */
	void destroy() throws PortletContainerException;

	/**
	 * Init this portlet container with the servlet context of portal
	 * application.
	 * 
	 * @param portalServletContext
	 * @throws PortletContainerException
	 */
	void init(ServletContext portalServletContext) throws PortletContainerException;

	/**
	 * Retrieve the portal servlet context.
	 * 
	 * @return portal servlet context.
	 */
	ServletContext getPortalServletContext();

	/**
	 * Invoke corresponding <code>portlet.performAction()</code> defined in
	 * portlet application
	 * 
	 * @param portletWindow
	 *            portlet to be invoked.
	 * @param request
	 *            The portal request received by portal application
	 * @param response
	 *            The portal response received by portal application.
	 * @throws PortletContainerException
	 * @throws ServletException
	 * @throws IOException
	 */
	void processPortletAction(PortletWindow portletWindow, HttpServletRequest request,
			HttpServletResponse response)
			throws PortletContainerException, ServletException, IOException;

	void renderPortlet(PortletWindow portletWindow, HttpServletRequest request,
			HttpServletResponse response)
			throws PortletContainerException, ServletException, IOException;

	void servePortletResource(PortletWindow portletWindow, HttpServletRequest request,
			HttpServletResponse response)
			throws PortletContainerException, ServletException, IOException;

	/**
	 * 
	 * @see PortletContainerService
	 * @param serviceInterface
	 * @return
	 * @throws ServiceUnavailableException
	 */
	<T extends PortletContainerService> T getService(Class<T> serviceInterface)
			throws ServiceUnavailableException;
}
