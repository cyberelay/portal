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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletContext;
import javax.portlet.PortletSession;
import javax.portlet.PortletSessionUtil;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.cyberelay.portletcontainer.PortletInvocationRequest;

/**
 * @author Roger Tang
 * 
 */
public class PortletSessionImpl implements PortletSession, HttpSession {
	private static final String PORTLET_SCOPE_NAMESPACE = "javax.portlet.p.";

	private PortletContext portletContext;
	private HttpSession httpSession;
	private String namespace;

	public PortletSessionImpl(HttpSession session, PortletInvocationRequest applicationRequest) {
		this.httpSession = session;
		this.portletContext = applicationRequest.getPortletContext();
		this.namespace =
				PORTLET_SCOPE_NAMESPACE + applicationRequest.getPortletWindow().getUniqueID();
	}

	/**
	 * 
	 * @see javax.portlet.PortletSession#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String name) {
		return getAttribute(name, PORTLET_SCOPE);
	}

	/**
	 * 
	 * @see javax.portlet.PortletSession#getAttribute(java.lang.String, int)
	 */
	public Object getAttribute(String name, int scope) {
		if (PORTLET_SCOPE == scope) {
			return httpSession.getAttribute(namespace + "?" + name);
		} else if (scope == APPLICATION_SCOPE) {
			return httpSession.getAttribute(name);
		}

		throw new IllegalArgumentException("Invalid scope [" + scope + "].");
	}

	/**
	 * 
	 * @see javax.portlet.PortletSession#getAttributeNames()
	 */
	public Enumeration<String> getAttributeNames() {
		return getAttributeNames(PORTLET_SCOPE);
	}

	/**
	 * 
	 * @see javax.portlet.PortletSession#getAttributeNames(int)
	 */
	public Enumeration<String> getAttributeNames(int scope) {
		checkScope(scope);

		Enumeration<String> attrNames = httpSession.getAttributeNames();
		List<String> result = new LinkedList<String>();

		while (attrNames.hasMoreElements()) {
			String attrName = attrNames.nextElement();
			if (PORTLET_SCOPE == scope) {
				if (attrName.startsWith(namespace)) {
					result.add(PortletSessionUtil.decodeAttributeName(attrName));
				}
			} else {
				if (!attrName.startsWith(namespace)) {
					result.add(attrName);
				}
			}
		}

		return Collections.enumeration(result);
	}

	/**
	 * 
	 * @see javax.portlet.PortletSession#getCreationTime()
	 */
	public long getCreationTime() {
		return httpSession.getCreationTime();
	}

	/**
	 * 
	 * @see javax.portlet.PortletSession#getId()
	 */
	public String getId() {
		return httpSession.getId();
	}

	/**
	 * 
	 * @see javax.portlet.PortletSession#getLastAccessedTime()
	 */
	public long getLastAccessedTime() {
		return httpSession.getLastAccessedTime();
	}

	/**
	 * 
	 * @see javax.portlet.PortletSession#getMap()
	 */
	public Map<String, Object> getAttributeMap() {
		return getAttributeMap(PORTLET_SCOPE);
	}

	/**
	 * 
	 * @see javax.portlet.PortletSession#getMap(int)
	 */
	public Map<String, Object> getAttributeMap(int scope) {
		checkScope(scope);
		
		Enumeration<String> attrNames = getAttributeNames(scope);
		Map<String, Object> result = new HashMap<String, Object>();

		while (attrNames.hasMoreElements()) {
			String name = attrNames.nextElement();
			result.put(name, getAttribute(name, scope));
		}

		return Collections.unmodifiableMap(result);
	}

	/**
	 * 
	 * @see javax.portlet.PortletSession#getMaxInactiveInterval()
	 */
	public int getMaxInactiveInterval() {
		return httpSession.getMaxInactiveInterval();
	}

	/**
	 * 
	 * @see javax.portlet.PortletSession#getPortletContext()
	 */
	public PortletContext getPortletContext() {
		return portletContext;
	}

	/**
	 * 
	 * @see javax.portlet.PortletSession#invalidate()
	 */
	public void invalidate() {
		httpSession.invalidate();
	}

	/**
	 * 
	 * @see javax.portlet.PortletSession#isNew()
	 */
	public boolean isNew() {
		return httpSession.isNew();
	}

	/**
	 * 
	 * @see javax.portlet.PortletSession#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String name) {
		removeAttribute(name, PORTLET_SCOPE);
	}

	/**
	 * 
	 * @see javax.portlet.PortletSession#removeAttribute(java.lang.String, int)
	 */
	public void removeAttribute(String name, int scope) {
		checkScope(scope);

		if (PORTLET_SCOPE == scope) {
			httpSession.removeAttribute(namespace + "?" + name);
		} else if (scope == APPLICATION_SCOPE) {
			httpSession.removeAttribute(name);
		}
	}

	/**
	 * 
	 * @see javax.portlet.PortletSession#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	public void setAttribute(String name, Object value) {
		setAttribute(name, value, PORTLET_SCOPE);
	}

	/**
	 * 
	 * @see javax.portlet.PortletSession#setAttribute(java.lang.String,
	 *      java.lang.Object, int)
	 */
	public void setAttribute(String name, Object value, int scope) {
		checkScope(scope);

		if (PORTLET_SCOPE == scope) {
			httpSession.setAttribute(namespace + "?" + name, value);
		} else if (scope == APPLICATION_SCOPE) {
			httpSession.setAttribute(name, value);
		}
	}

	/**
	 * 
	 * @see javax.portlet.PortletSession#setMaxInactiveInterval(int)
	 */
	public void setMaxInactiveInterval(int interval) {
		httpSession.setMaxInactiveInterval(interval);
	}

	public ServletContext getServletContext() {
		return httpSession.getServletContext();
	}

	public HttpSessionContext getSessionContext() {
		return httpSession.getSessionContext();
	}

	public Object getValue(String name) {
		return getAttribute(name);
	}

	public String[] getValueNames() {
		Enumeration<String> attrNames = getAttributeNames();
		List<String> names = new ArrayList<String>();
		while (attrNames.hasMoreElements()) {
			names.add(attrNames.nextElement());
		}

		return names.toArray(new String[names.size()]);
	}

	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}

	public void removeValue(String name) {
		removeAttribute(name);
	}

	/* =============================================================== */

	private void checkScope(int scope) {
		if (scope != PORTLET_SCOPE || scope != APPLICATION_SCOPE) {
			throw new IllegalArgumentException("Invalid scope [" + scope + "].");
		}
	}
}
