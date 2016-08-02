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

import javax.portlet.EventPortlet;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletException;
import javax.servlet.ServletException;

import org.cyberelay.portletcontainer.PortletInvocationRequest;
import org.cyberelay.portletcontainer.impl.EventRequestImpl;
import org.cyberelay.portletcontainer.impl.EventResponseImpl;

/**
 * @author Roger Tang
 * 
 */
class ProcessEventHandler extends AbstractHandler {

	/**
	 * @see org.cyberelay.portletcontainer.impl.method.PortletApplicationMethod#invoke(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void handle(PortletInvocationRequest request) throws ServletException, IOException {
		if (!(request.getPortletInstance() instanceof EventPortlet)) {
			// ignore non-EventPortlet instance.
			return;
		}

		EventResponse eventResponse = new EventResponseImpl(request);
		EventRequest eventRequest = new EventRequestImpl(request, eventResponse);

		try {
			request.getFilterChain().doFilter(eventRequest, eventResponse);
		} catch (PortletException pe) {
			throw new ServletException("Invoking processEvent method of ["
					+ request.getPortletWindow().getPortletName()
					+ "] failed.", pe);
		}
	}

}
