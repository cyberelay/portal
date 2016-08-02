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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ClientDataRequest;
import javax.portlet.PortletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portletcontainer.PortletContainerConstants;

/**
 * @author Roger Tang
 * 
 */
class PortletRequest2ServletRequestAdapter extends HttpServletRequestWrapper implements
		PortletContainerConstants {
	private static final Logger LOG =
			LoggerFactory.getLogger(PortletRequest2ServletRequestAdapter.class);

	private PortletRequest wrapped;
	private boolean include;
	private String lifecyclePhase;

	public PortletRequest2ServletRequestAdapter(PortletRequest request, boolean include) {
		super(toHttpRequest(request));

		this.wrapped = request;
		this.include = include;
		this.lifecyclePhase = (String) request.getAttribute(PortletRequest.LIFECYCLE_PHASE);
	}

	private static HttpServletRequest toHttpRequest(PortletRequest request) {
		if (request instanceof HttpServletRequest) {
			return (HttpServletRequest) request;
		}

		return (HttpServletRequest) request.getAttribute(HTTP_REQUEST);
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getAuthType()
	 */
	public String getAuthType() {
		return wrapped.getAuthType();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getContextPath()
	 */
	public String getContextPath() {
		return wrapped.getContextPath();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getCookies()
	 */
	public Cookie[] getCookies() {
		return wrapped.getCookies();
	}

	public long getDateHeader(String name) {
		//TODO ????? call getProperties?????
		return Long.parseLong(wrapped.getProperty(name));
	}
	
	/**
	 * @see javax.servlet.http.HttpServletRequest#getHeader(java.lang.String)
	 */
	public String getHeader(String name) {
		return wrapped.getProperty(name);
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getHeaderNames()
	 */
	public Enumeration<String> getHeaderNames() {
		return wrapped.getPropertyNames();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest#getHeaders(java.lang.String)
	 */
	public Enumeration<String> getHeaders(String name) {
		return wrapped.getProperties(name);
	}
	
	public int getIntHeader(String name) {
		return Integer.parseInt(wrapped.getProperty(name));
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpServletRequest#getRemoteUser()
	 */
	public String getRemoteUser() {
		return wrapped.getRemoteUser();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletRequest#getRequestedSessionId()
	 */
	public String getRequestedSessionId() {
		return wrapped.getRequestedSessionId();
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpServletRequest#getUserPrincipal()
	 */
	public Principal getUserPrincipal() {
		return wrapped.getUserPrincipal();
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdValid()
	 */
	public boolean isRequestedSessionIdValid() {
		return wrapped.isRequestedSessionIdValid();
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpServletRequest#isUserInRole(java.lang.String)
	 */
	public boolean isUserInRole(String role) {
		return wrapped.isUserInRole(role);
	}

	/**
	 * 
	 * @see javax.servlet.ServletRequest#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String name) {
		return wrapped.getAttribute(name);
	}

	/**
	 * 
	 * @see javax.servlet.ServletRequest#getAttributeNames()
	 */
	public Enumeration getAttributeNames() {
		return wrapped.getAttributeNames();
	}

	/**
	 * 
	 * @see javax.servlet.ServletRequest#getLocale()
	 */
	public Locale getLocale() {
		return wrapped.getLocale();
	}

	/**
	 * 
	 * @see javax.servlet.ServletRequest#getLocales()
	 */
	public Enumeration getLocales() {
		return wrapped.getLocales();
	}

	/**
	 * 
	 * @see javax.servlet.ServletRequest#getParameter(java.lang.String)
	 */
	public String getParameter(String name) {
		return wrapped.getParameter(name);
	}

	/**
	 * 
	 * @see javax.servlet.ServletRequest#getParameterMap()
	 */
	public Map<String, String[]> getParameterMap() {
		return wrapped.getParameterMap();
	}

	/**
	 * 
	 * @see javax.servlet.ServletRequest#getParameterNames()
	 */
	public Enumeration<String> getParameterNames() {
		return wrapped.getParameterNames();
	}

	/**
	 * 
	 * @see javax.servlet.ServletRequest#getParameterValues(java.lang.String)
	 */
	public String[] getParameterValues(String name) {
		return wrapped.getParameterValues(name);
	}

	/**
	 * 
	 * @see javax.servlet.ServletRequest#getReader()
	 */
	public BufferedReader getReader() throws IOException {
		if (wrapped instanceof ClientDataRequest) {
			return ((ClientDataRequest) wrapped).getReader();
		}

		return super.getReader();
	}

	/**
	 * 
	 * @see javax.servlet.ServletRequest#getScheme()
	 */
	public String getScheme() {
		return wrapped.getScheme();
	}

	/**
	 * 
	 * @see javax.servlet.ServletRequest#getServerName()
	 */
	public String getServerName() {
		return wrapped.getServerName();
	}

	/**
	 * 
	 * @see javax.servlet.ServletRequest#getServerPort()
	 */
	public int getServerPort() {
		return wrapped.getServerPort();
	}

	/**
	 * 
	 * @see javax.servlet.ServletRequest#isSecure()
	 */
	public boolean isSecure() {
		return wrapped.isSecure();
	}

	/**
	 * @see javax.servlet.ServletRequest#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String name) {
		wrapped.removeAttribute(name);
	}

	/**
	 * @see javax.servlet.ServletRequest#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	public void setAttribute(String name, Object value) {
		LOG.debug("setting attribute. name = [" + name + "], value = [" + value + "]");
		wrapped.setAttribute(name, value);
	}

	/**
	 * @see javax.servlet.ServletRequest#setCharacterEncoding(java.lang.String)
	 */
	public void setCharacterEncoding(String enc) throws UnsupportedEncodingException {
		if (wrapped instanceof ClientDataRequest) {
			((ClientDataRequest) wrapped).setCharacterEncoding(enc);
			return;
		}

		throw new UnsupportedOperationException();
	}

}
