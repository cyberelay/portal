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
package org.cyberelay.portletcontainer.legacy;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyberelay.portal.util.Assert;
import org.cyberelay.portletcontainer.PortletWindow;

/**
 * PortletInvocationServlet accepts request from <code>PortalApplication</code>
 * and invokes corresponding portlet.
 * 
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jan 21, 2008
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 668 $
 * <li>Last Update Time: $Date: 2008-03-18 14:18:57 +0000 (Tue, 18 Mar 2008) $
 * </ul>
 * 
 */
public class PortletInvocationServlet extends HttpServlet implements Constants {

	private PortletApplication portletApplication;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		portletApplication = (PortletApplication) config.getServletContext().getAttribute(
				PORTLET_APPLICATION);

		Assert.notNull(portletApplication);
	}

	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException,
			IOException {
		portletApplication.invokePortlet(getMethod(req), getPortletWindow(req), req, res);
	}

	protected PortletMethod getMethod(HttpServletRequest req) {
		return (PortletMethod) req.getAttribute(PORTLET_METHOD);
	}

	protected PortletWindowEx getPortletWindow(HttpServletRequest req) {
		PortletWindow original = (PortletWindow) req.getAttribute(PORTLET_WINDOW_EX);
		return null;
	}

}
