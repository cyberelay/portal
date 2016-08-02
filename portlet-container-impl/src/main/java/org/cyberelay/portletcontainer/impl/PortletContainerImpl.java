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

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyberelay.portletcontainer.PortletApplicationRegistry;
import org.cyberelay.portletcontainer.PortletContainer;
import org.cyberelay.portletcontainer.PortletContainerConstants;
import org.cyberelay.portletcontainer.PortletContainerException;
import org.cyberelay.portletcontainer.PortletContainerService;
import org.cyberelay.portletcontainer.PortletInvocationRequest;
import org.cyberelay.portletcontainer.PortletWindow;
import org.cyberelay.portletcontainer.ServiceUnavailableException;
import org.cyberelay.portletcontainer.service.PortletContainerServiceProvider;
import org.cyberelay.portletcontainer.service.PortletEventService;
import org.cyberelay.portletcontainer.service.impl.ConfigurationPortletContainerServiceProvider;

/**
 * @author Roger Tang
 * 
 */
public class PortletContainerImpl implements PortletContainer, PortletContainerConstants {

	private static final String PORTLET_INVOCATION_ID = PortletInvocationRequest.INVOCATION_ID;

	private static final String ID_PROCESS_RESOURCE = PortletInvocationRequest.ID_PROCESS_RESOURCE;

	private static final String ID_PROCESS_ACTION = PortletInvocationRequest.ID_PROCESS_ACTION;

	private static final String ID_RENDER_MARKUP = PortletInvocationRequest.ID_RENDER_MARKUP;

	private static final String ID_RENDER_HEADER = PortletInvocationRequest.ID_RENDER_HEADER;

	private static final String ID_PROCESS_EVENT = PortletInvocationRequest.ID_PROCESS_EVENT;

	private static final String RENDER_HEADERS = PortletRequest.RENDER_HEADERS;

	private static final String INVOCATION_SERVLET_PATH = "/pis";

	private ServletContext portalServletContext;

	private PortletContainerServiceProvider serviceProvider;

	/**
	 * 
	 * @see org.cyberelay.portletcontainer.PortletContainer#destroy()
	 */
	public void destroy() throws PortletContainerException {
		if (serviceProvider != null) {
			serviceProvider.destroy();
			serviceProvider = null;
		}
	}

	public void init(ServletContext portalServletContext) throws PortletContainerException {
		this.portalServletContext = portalServletContext;
		serviceProvider = new ConfigurationPortletContainerServiceProvider(this);
	}

	/**
	 * 
	 * @see org.cyberelay.portletcontainer.PortletContainer#getService(java.lang.Class)
	 */
	public <T extends PortletContainerService> T getService(Class<T> serviceInterface)
			throws ServiceUnavailableException {
		return serviceProvider.getService(serviceInterface);
	}

	/**
	 * 
	 * @see org.cyberelay.portletcontainer.PortletContainer#getPortalServletContext()
	 */
	public ServletContext getPortalServletContext() {
		return portalServletContext;
	}

	public void processPortletAction(PortletWindow portletWindow, HttpServletRequest request,
			HttpServletResponse response) throws PortletContainerException {
		process(portletWindow, request, response, ID_PROCESS_ACTION, false);
		processEvents(request, response);
	}
	
	public void renderPortlet(PortletWindow portletWindow, HttpServletRequest request,
			HttpServletResponse response) throws PortletContainerException {
		String part = (String) request.getAttribute(PortletRequest.RENDER_PART);
		String invocationID = RENDER_HEADERS.equals(part) ? ID_RENDER_HEADER : ID_RENDER_MARKUP;
		process(portletWindow, request, response, invocationID, false);
	}

	public void servePortletResource(PortletWindow portletWindow, HttpServletRequest req,
			HttpServletResponse res) throws PortletContainerException {
		process(portletWindow, req, res, ID_PROCESS_RESOURCE, true);
	}

	
	protected void processEvents(HttpServletRequest request, HttpServletResponse response)
			throws PortletContainerException {
		EventQueue result = (EventQueue) request.getAttribute(EVENT_QUEUE);
		if (result == null || result.isEmpty()) {
			return;
		}
		while (!result.isEmpty()) {
			PortletWindow[] subscribers =
					getService(PortletEventService.class).getSubscribers(request, result.peek());
			for (PortletWindow window : subscribers) {
				process(window, request, response, ID_PROCESS_EVENT, false);
			}
			result.poll();
		}
	}

	protected void process(PortletWindow portletWindow, HttpServletRequest request,
			HttpServletResponse response, String invocationID, boolean forward)
			throws PortletContainerException {
		try {
			request.setAttribute(PORTLET_INVOCATION_ID, invocationID);
			request.setAttribute(PORTLET_WINDOW, portletWindow);

			if (forward) {
				getDispatcher(portletWindow).forward(request, response);
			} else {
				getDispatcher(portletWindow).include(request, response);
			}
		} catch (ServletException e) {
			throw new PortletContainerException(e);
		} catch (IOException e) {
			throw new PortletContainerException(e);
		} finally {
			request.removeAttribute(PORTLET_INVOCATION_ID);
			request.removeAttribute(PORTLET_WINDOW);
		}
	}

	private RequestDispatcher getDispatcher(PortletWindow portletWindow) {
		return PortletApplicationRegistry.getApplication(portletWindow).getServletContext().getRequestDispatcher(
				INVOCATION_SERVLET_PATH);
	}

}
