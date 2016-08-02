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

package org.cyberelay.portletcontainer.handler;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceServingPortlet;
import javax.servlet.ServletException;

import org.cyberelay.portletcontainer.PortletInvocationRequest;
import org.cyberelay.portletcontainer.impl.ResourceRequestImpl;
import org.cyberelay.portletcontainer.impl.ResourceResponseImpl;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 27, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 604 $
 * <li>Last Update Time: $Date: 2008-03-01 13:08:04 +0000 (Sat, 01 Mar 2008) $
 * </ul>
 * 
 */
class ProcessResourceHandler extends AbstractHandler {

	/**
	 * @see org.cyberelay.portletcontainer.impl.container.method.PortletApplicationMethod#invoke(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void handle(PortletInvocationRequest request) throws ServletException, IOException {
		if (!(request.getPortletInstance() instanceof ResourceServingPortlet)) {
			// ignore non-ResourceServingPortlet instance.
			return;
		}

		ResourceResponse resResponse = new ResourceResponseImpl(request);
		ResourceRequest resRequest = new ResourceRequestImpl(request, resResponse);

		try {
			request.getFilterChain().doFilter(resRequest, resResponse);
		} catch (PortletException pe) {
			throw new ServletException("Invoking serveResource method of ["
					+ request.getPortletWindow().getPortletName()
					+ "] failed.", pe);
		}
	}
}
