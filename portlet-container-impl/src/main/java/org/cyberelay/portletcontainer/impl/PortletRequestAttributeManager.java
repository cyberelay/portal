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
import java.util.Vector;

import org.cyberelay.portal.util.StringUtil;
import org.cyberelay.portletcontainer.PortletInvocationRequest;

/**
 * @author Roger Tang
 * 
 */
class PortletRequestAttributeManager {
	private PortletInvocationRequest request;
	private String attributeNamePrefix;

	public PortletRequestAttributeManager(PortletInvocationRequest request) {
		this.request = request;
		attributeNamePrefix = request.getPortletWindow().getUniqueID() + ".";
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String name) {
		Object value = _getAttribute(encodeAttributeName(name));
		return value == null ? _getAttribute(name) : value;
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#getAttributeNames()
	 */
	public Enumeration<String> getAttributeNames() {
		Vector<String> result = new Vector<String>();
		Enumeration<String> names = _getAttributeNames();

		while (names.hasMoreElements()) {
			result.add(decodeAttributeName(names.nextElement()));
		}

		return result.elements();
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String name) {
		_removeAttribute(encodeAttributeName(name));
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequest#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	public void setAttribute(String name, Object o) {
		_setAttribute(encodeAttributeName(name), o);
	}

	/* =========================================================== */

	private String encodeAttributeName(String name) {
		if (name == null || isReservedName(name)) {
			return name;
		}

		return attributeNamePrefix + name;
	}

	private String decodeAttributeName(String name) {
		if (name == null || isReservedName(name)) {
			return name;
		}

		return name.startsWith(attributeNamePrefix) ? StringUtil.replaceFirst(
				name, attributeNamePrefix, "") : name;
	}

	private boolean isReservedName(String name) {
		return name != null
				&& (name.startsWith("javax.") || name.startsWith("java.") || name.startsWith("org.cyberelay."));
	}

	private Object _getAttribute(String name) {
		return request.getPortalRequest().getAttribute(name);
	}

	private void _setAttribute(String name, Object value) {
		request.getPortalRequest().setAttribute(name, value);
	}

	private void _removeAttribute(String name) {
		request.getPortalRequest().removeAttribute(name);
	}

	private Enumeration<String> _getAttributeNames() {
		return request.getPortalRequest().getAttributeNames();
	}
}
