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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

import org.cyberelay.portletcontainer.PortletApplication;
import org.cyberelay.portletcontainer.PortletContainerInfo;

/**
 * @author Roger Tang
 * 
 */
public class PortletContextImpl implements PortletContext {
	private PortletApplication application;

	public PortletContextImpl(PortletApplication application) {
		this.application = application;
	}

	/**
	 * @see javax.portlet.PortletContext#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String name) {
		return getServletContext().getAttribute(name);
	}

	/**
	 * @see javax.portlet.PortletContext#getAttributeNames()
	 */
	public Enumeration<String> getAttributeNames() {
		return getServletContext().getAttributeNames();
	}

	/**
	 * @see javax.portlet.PortletContext#getInitParameter(java.lang.String)
	 */
	public String getInitParameter(String name) {
		return getServletContext().getInitParameter(name);
	}

	/**
	 * @see javax.portlet.PortletContext#getInitParameterNames()
	 */
	public Enumeration<String> getInitParameterNames() {
		return getServletContext().getInitParameterNames();
	}

	/**
	 * @see javax.portlet.PortletContext#getMajorVersion()
	 */
	public int getMajorVersion() {
		return PortletContainerInfo.getSupportedMajorPortletVersion();
	}

	/**
	 * @see javax.portlet.PortletContext#getMimeType(java.lang.String)
	 */
	public String getMimeType(String file) {
		return getServletContext().getMimeType(file);
	}

	/**
	 * @see javax.portlet.PortletContext#getMinorVersion()
	 */
	public int getMinorVersion() {
		return PortletContainerInfo.getSupportedMinorPortletVersion();
	}

	/**
	 * @see javax.portlet.PortletContext#getNamedDispatcher(java.lang.String)
	 */
	public PortletRequestDispatcher getNamedDispatcher(String name) {
		RequestDispatcher dispatcher = getServletContext().getNamedDispatcher(name);
		return dispatcher == null ? null : new PortletRequestDispatcherImpl(dispatcher);
	}

	/**
	 * @see javax.portlet.PortletContext#getPortletContextName()
	 */
	public String getPortletContextName() {
		return getServletContext().getServletContextName();
	}

	/**
	 * @see javax.portlet.PortletContext#getRealPath(java.lang.String)
	 */
	public String getRealPath(String path) {
		return getServletContext().getRealPath(path);
	}

	/**
	 * @see javax.portlet.PortletContext#getRequestDispatcher(java.lang.String)
	 */
	public PortletRequestDispatcher getRequestDispatcher(String path) {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(path);
		return dispatcher == null ? null : new PortletRequestDispatcherImpl(dispatcher);
	}

	/**
	 * @see javax.portlet.PortletContext#getResource(java.lang.String)
	 */
	public URL getResource(String path) throws MalformedURLException {
		return getServletContext().getResource(path);
	}

	/**
	 * @see javax.portlet.PortletContext#getResourceAsStream(java.lang.String)
	 */
	public InputStream getResourceAsStream(String path) {
		return getServletContext().getResourceAsStream(path);
	}

	/**
	 * @see javax.portlet.PortletContext#getResourcePaths(java.lang.String)
	 */
	public Set getResourcePaths(String path) {
		return getServletContext().getResourcePaths(path);
	}

	/**
	 * @see javax.portlet.PortletContext#getServerInfo()
	 */
	public String getServerInfo() {
		return PortletContainerInfo.getInfo();
	}

	/**
	 * @see javax.portlet.PortletContext#log(java.lang.String)
	 */
	public void log(String msg) {
		getServletContext().log(msg);
	}

	/**
	 * @see javax.portlet.PortletContext#log(java.lang.String,
	 *      java.lang.Throwable)
	 */
	public void log(String message, Throwable throwable) {
		getServletContext().log(message, throwable);
	}

	/**
	 * @see javax.portlet.PortletContext#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String name) {
		getServletContext().removeAttribute(name);
	}

	/**
	 * @see javax.portlet.PortletContext#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	public void setAttribute(String name, Object object) {
		getServletContext().setAttribute(name, object);
	}

	/* ============================================================= */

	protected ServletContext getServletContext() {
		return application.getServletContext();
	}

	public Enumeration<String> getContainerRuntimeOptions() {
		return PortletContainerInfo.getSupportedRuntimeOptions();
	}

}
