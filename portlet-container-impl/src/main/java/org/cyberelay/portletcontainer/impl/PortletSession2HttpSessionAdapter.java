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

import java.util.Enumeration;

import javax.portlet.PortletSession;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * @author Roger Tang
 * 
 */
class PortletSession2HttpSessionAdapter implements HttpSession {
	private static final int PORTLET_SCOPE = PortletSession.PORTLET_SCOPE;
	private static final int APP_SCOPE = PortletSession.APPLICATION_SCOPE;
	private PortletSession wrapped;

	public PortletSession2HttpSessionAdapter(PortletSession session) {
		this.wrapped = session;
	}

	public Object getAttribute(String name) {
		Object value = wrapped.getAttribute(name, PORTLET_SCOPE);
		return value == null ? wrapped.getAttribute(name, APP_SCOPE) : value;
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpSession#getAttributeNames()
	 */
	public Enumeration<String> getAttributeNames() {
		return wrapped.getAttributeNames(APP_SCOPE);
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpSession#getCreationTime()
	 */
	public long getCreationTime() {
		return wrapped.getCreationTime();
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpSession#getId()
	 */
	public String getId() {
		return wrapped.getId();
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpSession#getLastAccessedTime()
	 */
	public long getLastAccessedTime() {
		return wrapped.getLastAccessedTime();
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
	 */
	public int getMaxInactiveInterval() {
		return wrapped.getMaxInactiveInterval();
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpSession#getServletContext()
	 */
	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getSessionContext()
	 */
	public HttpSessionContext getSessionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
	 */
	public Object getValue(String name) {
		return getAttribute(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getValueNames()
	 */
	public String[] getValueNames() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpSession#invalidate()
	 */
	public void invalidate() {
		wrapped.invalidate();
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpSession#isNew()
	 */
	public boolean isNew() {
		return wrapped.isNew();
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpSession#putValue(java.lang.String,
	 *      java.lang.Object)
	 */
	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String name) {
		wrapped.removeAttribute(name, APP_SCOPE);
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
	 */
	public void removeValue(String name) {
		removeAttribute(name);
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	public void setAttribute(String name, Object value) {
		wrapped.setAttribute(name, value, APP_SCOPE);
	}

	/**
	 * 
	 * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
	 */
	public void setMaxInactiveInterval(int interval) {
		wrapped.setMaxInactiveInterval(interval);
	}

}
