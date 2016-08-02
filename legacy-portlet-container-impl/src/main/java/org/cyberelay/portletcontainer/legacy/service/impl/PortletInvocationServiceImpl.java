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
package org.cyberelay.portletcontainer.legacy.service.impl;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portletcontainer.AbstractPortletContainerService;
import org.cyberelay.portletcontainer.PortletContainer;
import org.cyberelay.portletcontainer.PortletWindow;
import org.cyberelay.portletcontainer.legacy.Constants;
import org.cyberelay.portletcontainer.legacy.PortletMethod;
import org.cyberelay.portletcontainer.legacy.service.PortletInvocationService;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jan 18, 2008
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
public class PortletInvocationServiceImpl extends AbstractPortletContainerService implements
		PortletInvocationService, Constants {
	private static final Logger LOG = LoggerFactory.getLogger(PortletInvocationServiceImpl.class);

	private static final String PORTLET_INVOCATION_SERVLET = "/pis";

	/**
	 * @throws IOException
	 * @throws ServletException
	 * @see org.apache.jetspeed.portlet.extension.service.PortletInvocationService#invokePortlet(org.apache.jetspeed.portlet.extension.LegacyPortlet.Method,
	 *      org.cyberelay.portletcontainer.PortletWindow,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void invokePortlet(PortletMethod method, PortletWindow window,
			HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Invoking Portlet... WINDOW = [" + window + "], METHOD = [" + method + "]");
		}

		try {
			request.setAttribute(PORTLET_WINDOW_EX, window);
			request.setAttribute(PORTLET_METHOD, method);

			ServletContext portletApplicationContext = getContainer(request)
					.getPortalServletContext().getContext(window.getPortletContextPath());
			portletApplicationContext.getRequestDispatcher(PORTLET_INVOCATION_SERVLET).include(
					request, response);
		} finally {
			request.removeAttribute(PORTLET_METHOD);
			request.removeAttribute(PORTLET_WINDOW_EX);
		}
	}

	protected PortletContainer getContainer(HttpServletRequest request) {
		return (PortletContainer) request.getAttribute(PortletContainer.PORTLET_CONTAINER);
	}
}
