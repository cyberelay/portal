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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portletcontainer.handler.PortletInvocationHandler;
import org.cyberelay.portletcontainer.handler.RequestHandlerRegistry;
import org.cyberelay.portletcontainer.impl.PortletInvocationRequestImpl;

/**
 * <code>PortletApplicationRequestHandlingServlet</code> receives and handles
 * portlet application request from portal
 * 
 * It is a servlet component running in portlet application.
 * 
 * @author Roger Tang
 * 
 */
public class PortletInvocationServlet extends HttpServlet implements PortletContainerConstants {
	private static final Logger LOG = LoggerFactory.getLogger(PortletInvocationServlet.class);

	private RequestHandlerRegistry handlerRegistry;
	private PortletApplication application;

	@Override
	public void init() throws ServletException {
		super.init();

		handlerRegistry = RequestHandlerRegistry.getInstance();
		application = (PortletApplication) getServletContext().getAttribute(PORTLET_APPLICATION);
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PortletInvocationRequest invocationRequest =
				new PortletInvocationRequestImpl(application, request, response);

		if (LOG.isDebugEnabled()) {
			LOG.debug("portlet invocation request received. "
					+ "invocation ID = ["
					+ invocationRequest.getInvocationID()
					+ "]. portlet = ["
					+ invocationRequest.getPortletWindow());
		}

		PortletInvocationHandler handler = handlerRegistry.getHandler(invocationRequest);

		if (handler == null) {
			// if
		}

		handler.handle(invocationRequest);
	}
}
