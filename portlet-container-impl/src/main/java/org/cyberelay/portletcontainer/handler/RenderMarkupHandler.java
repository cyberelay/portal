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
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.ServletException;

import org.cyberelay.portletcontainer.PortletInvocationRequest;
import org.cyberelay.portletcontainer.impl.RenderRequestImpl;
import org.cyberelay.portletcontainer.impl.RenderResponseImpl;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 27, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 627 $
 * <li>Last Update Time: $Date: 2008-03-08 06:57:00 +0000 (Sat, 08 Mar 2008) $
 * </ul>
 * 
 */
class RenderMarkupHandler extends AbstractHandler {

	/**
	 * @see org.cyberelay.portletcontainer.impl.application.request.PortletApplicationMethod#invoke(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void handle(PortletInvocationRequest request) throws ServletException, IOException {
		RenderResponse renderResponse = new RenderResponseImpl(request);
		RenderRequest renderRequest = createRenderRequest(request, renderResponse);
		try {
			request.getFilterChain().doFilter(renderRequest, renderResponse);

			// Once portlet rendering concluded, current navigation state should
			// be saved for next portlet rendering's use.
			request.getNavigationState().store();
		} catch (PortletException pe) {
			throw new ServletException("Invoking render method of ["
					+ request.getPortletWindow().getPortletName()
					+ "] failed.", pe);
		}
	}

	protected RenderRequest createRenderRequest(PortletInvocationRequest request,
			RenderResponse response) {
		RenderRequest req = new RenderRequestImpl(request, response);
		req.setAttribute(RenderRequest.RENDER_PART, RenderRequest.RENDER_MARKUP);
		return req;
	}
}
