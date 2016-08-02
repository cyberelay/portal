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

import javax.portlet.PortletContext;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.cyberelay.portlet.PortletConfigEx;
import org.cyberelay.portletcontainer.NavigationState;
import org.cyberelay.portletcontainer.PortletContainerConstants;
import org.cyberelay.portletcontainer.PortletInvocationRequest;
import org.cyberelay.portletcontainer.PortletWindow;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

/**
 * @author Roger Tang
 * 
 */
abstract class PortletResponseImpl extends HttpServletResponseWrapper implements
		PortletResponse,
		PortletContainerConstants {
	protected PortletInvocationRequest invocationRequest;

	public PortletResponseImpl(PortletInvocationRequest request) {
		super(request.getPortalResponse());

		this.invocationRequest = request;
	}

	/**
	 * 
	 * @see javax.portlet.PortletResponse#addProperty(java.lang.String,
	 *      java.lang.String)
	 */
	public void addProperty(String key, String value) {
		getHttpResponse().addHeader(key, value);
	}

	/**
	 * 
	 * @see javax.portlet.PortletResponse#addProperty(javax.servlet.http.Cookie)
	 */
	public void addProperty(Cookie cookie) {
		getHttpResponse().addCookie(cookie);
	}

	public PortletURL createActionURL() throws IllegalStateException {
		return invocationRequest.createActionURL();
	}

	public PortletURL createRenderURL() throws IllegalStateException {
		return invocationRequest.createRenderURL();
	}

	protected HttpServletRequest getHttpRequest() {
		return invocationRequest.getPortalRequest();
	}

	/**
	 * 
	 * @see javax.portlet.PortletResponse#createResourceURL()
	 */
	public ResourceURL createResourceURL() throws IllegalStateException {
		return invocationRequest.createResourceURL();
	}

	/**
	 * 
	 * @see javax.portlet.PortletResponse#getNamespace()
	 */
	public String getNamespace() {
		throw new RuntimeException("Not implemented yet.");
	}

	/**
	 * 
	 * @see javax.portlet.PortletResponse#setProperty(java.lang.String,
	 *      java.lang.String)
	 */
	public void setProperty(String key, String value) {
		getHttpResponse().setHeader(key, value);
	}

	public void addProperty(String key, Element element) {
		// TODO Auto-generated method stub

	}

	public Element createElement(String tagName) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	/* ============================================================= */

	protected HttpServletResponse getHttpResponse() {
		return invocationRequest.getPortalResponse();
	}

	protected PortletWindow getPortletWindow() {
		return invocationRequest.getPortletWindow();
	}

	protected PortletConfigEx getPortletConfig() {
		return invocationRequest.getPortletConfig();
	}

	protected ServletContext getServletContext() {
		return invocationRequest.getServletContext();
	}

	protected PortletContext getPortletContext() {
		return invocationRequest.getPortletContext();
	}

	protected PortletInvocationRequest getInvocationRequest() {
		return invocationRequest;
	}

	protected NavigationState getNavigationState() {
		return invocationRequest.getNavigationState();
	}
}
