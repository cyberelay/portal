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
package org.cyberelay.portletcontainer.legacy.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.jetspeed.portlet.AccessDeniedException;
import org.apache.jetspeed.portlet.Client;
import org.apache.jetspeed.portlet.DefaultPortletMessage;
import org.apache.jetspeed.portlet.PortletContext;
import org.apache.jetspeed.portlet.PortletException;
import org.apache.jetspeed.portlet.PortletLog;
import org.apache.jetspeed.portlet.PortletMessage;
import org.apache.jetspeed.portlet.PortletRequest;
import org.apache.jetspeed.portlet.PortletResponse;
import org.apache.jetspeed.portlet.service.PortletService;
import org.apache.jetspeed.portlet.service.PortletServiceNotFoundException;
import org.apache.jetspeed.portlet.service.PortletServiceUnavailableException;
import org.cyberelay.portal.util.Assert;
import org.cyberelay.portletcontainer.legacy.Constants;

/**
 * 
 * @author Roger Tang
 * 
 */
public class PortletContextImpl implements PortletContext, Constants {
	private ServletContext servletContext;
	private PortletLog log;

	public PortletContextImpl(ServletContext servletContext) {
		Assert.notNull(servletContext);
		this.servletContext = servletContext;
		this.log = new PortletLogImpl(servletContext.getContextPath());
	}

	/**
	 * @see javax.servlet.ServletContext#getInitParameter(java.lang.String)
	 */
	public String getInitParameter(String name) {
		return servletContext.getInitParameter(name);
	}

	/**
	 * @see javax.servlet.ServletContext#getInitParameterNames()
	 */
	public Enumeration getInitParameterNames() {
		return servletContext.getInitParameterNames();
	}

	/**
	 * @see javax.servlet.ServletContext#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	public void setAttribute(String name, Object value) {
		servletContext.setAttribute(name, value);
	}

	/**
	 * @see javax.servlet.ServletContext#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String name) {
		return servletContext.getAttribute(name);
	}

	/**
	 * @see javax.servlet.ServletContext#getAttributeNames()
	 */
	public Enumeration getAttributeNames() {
		return servletContext.getAttributeNames();
	}

	/**
	 * @see javax.servlet.ServletContext#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String name) {
		servletContext.removeAttribute(name);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletContext#include(java.lang.String,
	 *      org.apache.jetspeed.portlet.PortletRequest,
	 *      org.apache.jetspeed.portlet.PortletResponse)
	 */
	public void include(String resourcePath, PortletRequest req, PortletResponse res)
			throws PortletException, IOException {
		try {
			System.out.println("resource path = [" + resourcePath + "]");
			servletContext.getRequestDispatcher(resourcePath).include(
					req, res);
		} catch (ServletException e) {
			throw new PortletException(e);
		}
	}

	/**
	 * @see javax.servlet.ServletContext#getResourceAsStream(java.lang.String)
	 */
	public InputStream getResourceAsStream(String path) {
		String realpath = servletContext.getRealPath(path);
		try {
			return new FileInputStream(realpath);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletContext#getResourceAsStream(java.lang.String,
	 *      org.apache.jetspeed.portlet.Client, java.util.Locale)
	 */
	public InputStream getResourceAsStream(String arg0, Client client, Locale locale) {
		throw new RuntimeException("Not implemented yet.");
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletContext#getText(java.lang.String,
	 *      java.lang.String, java.util.Locale)
	 */
	public String getText(String bundle, String key, Locale arg2) {
		// TODO
		return key;
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletContext#send(java.lang.String,
	 *      org.apache.jetspeed.portlet.PortletMessage,
	 *      org.apache.jetspeed.portlet.PortletRequest)
	 */
	public void send(String portletName, PortletMessage message, PortletRequest request)
			throws AccessDeniedException {
		_send(portletName, message);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletContext#send(java.lang.String,
	 *      org.apache.jetspeed.portlet.PortletMessage)
	 */
	public void send(String portletName, PortletMessage message) throws AccessDeniedException {
		_send(portletName, message);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletContext#send(java.lang.String,
	 *      org.apache.jetspeed.portlet.DefaultPortletMessage)
	 */
	public void send(String portletName, DefaultPortletMessage message)
			throws AccessDeniedException {
		_send(portletName, message);
	}

	protected void _send(String portletName, PortletMessage message) throws AccessDeniedException {
		/*
		PortletWindowRunData source = (PortletWindowRunData) ThreadAttributesManager.getAttribute(PORTLET_WINDOW_RUN_DATA);
		if (source == null) {
			throw new AccessDeniedException();
		}
		PortletMessageExtQueue queue = (PortletMessageExtQueue) ThreadAttributesManager.getAttribute(PORTLET_MESSAGE_QUEUE);
		if (queue == null) {
			queue = new PortletMessageExtQueueImpl();
			ThreadAttributesManager.setAttribute(PORTLET_MESSAGE_QUEUE, queue);
		}
		String[] targets = (portletName == null ? new String[0] : new String[] { portletName });
		queue.push(new PortletMessageExtImpl(source.getWindowDefinition(), message, targets));
		*/
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletContext#getService(java.lang.Class)
	 */
	public PortletService getService(Class clazz)
			throws PortletServiceUnavailableException, PortletServiceNotFoundException {
		throw new RuntimeException("Not supported operation.");
	}

	/**
	 * @see javax.servlet.ServletContext#getMajorVersion()
	 */
	public int getMajorVersion() {
		return 1;
	}

	/**
	 * @see javax.servlet.ServletContext#getMinorVersion()
	 */
	public int getMinorVersion() {
		return 0;
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletContext#getContainerInfo()
	 */
	public String getContainerInfo() {
		return "Developed by Cyberelay.";
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletContext#getLog()
	 */
	public PortletLog getLog() {
		return log;
	}

	/**
	 * @see javax.servlet.ServletContext#getContext(java.lang.String)
	 */
	public ServletContext getContext(String name) {
		return servletContext.getContext(name);
	}

	/**
	 * @see javax.servlet.ServletContext#getMimeType(java.lang.String)
	 */
	public String getMimeType(String arg0) {
		return servletContext.getMimeType(arg0);
	}

	/**
	 * @see javax.servlet.ServletContext#getResourcePaths(java.lang.String)
	 */
	public Set getResourcePaths(String arg0) {
		return servletContext.getResourcePaths(arg0);
	}

	/**
	 * @see javax.servlet.ServletContext#getResource(java.lang.String)
	 */
	public URL getResource(String arg0) throws MalformedURLException {
		return servletContext.getResource(arg0);
	}

	/**
	 * @see javax.servlet.ServletContext#getRequestDispatcher(java.lang.String)
	 */
	public RequestDispatcher getRequestDispatcher(String path) {
		return servletContext.getRequestDispatcher(path);
	}

	/**
	 * @see javax.servlet.ServletContext#getNamedDispatcher(java.lang.String)
	 */
	public RequestDispatcher getNamedDispatcher(String path) {
		return servletContext.getNamedDispatcher(path);
	}

	/**
	 * @deprecated
	 * @see javax.servlet.ServletContext#getServlet(java.lang.String)
	 */
	public Servlet getServlet(String arg0) throws ServletException {
		return servletContext.getServlet(arg0);
	}

	/**
	 * @deprecated
	 * @see javax.servlet.ServletContext#getServlets()
	 */
	public Enumeration getServlets() {
		return servletContext.getServlets();
	}

	/**
	 * @deprecated
	 * @see javax.servlet.ServletContext#getServletNames()
	 */
	public Enumeration getServletNames() {
		return servletContext.getServletNames();
	}

	/**
	 * @see javax.servlet.ServletContext#log(java.lang.String)
	 */
	public void log(String arg0) {
		servletContext.log(arg0);
	}

	/**
	 * @deprecated
	 * @see javax.servlet.ServletContext#log(java.lang.Exception,
	 *      java.lang.String)
	 */
	public void log(Exception arg0, String arg1) {
		servletContext.log(arg0, arg1);
	}

	/**
	 * @see javax.servlet.ServletContext#log(java.lang.String,
	 *      java.lang.Throwable)
	 */
	public void log(String arg0, Throwable arg1) {
		servletContext.log(arg0, arg1);
	}

	/**
	 * @see javax.servlet.ServletContext#getRealPath(java.lang.String)
	 */
	public String getRealPath(String arg0) {
		return servletContext.getRealPath(arg0);
	}

	/**
	 * @see javax.servlet.ServletContext#getServerInfo()
	 */
	public String getServerInfo() {
		return servletContext.getServerInfo();
	}

	/**
	 * @see javax.servlet.ServletContext#getServletContextName()
	 */
	public String getServletContextName() {
		return servletContext.getServletContextName();
	}

	public String getContextPath() {
		return servletContext.getContextPath();
	}

}
