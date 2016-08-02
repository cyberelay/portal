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
package org.cyberelay.portal.impl;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.portlet.BaseURL;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURLGenerationListener;

import org.cyberelay.portal.PortalURL;

/**
 * @author Roger Tang
 * 
 */
abstract class BaseURLImpl implements BaseURL {
	protected PortalURL portalURL;
	protected List<PortletURLGenerationListener> listeners;

	public BaseURLImpl(PortalURL url, List<PortletURLGenerationListener> listeners) {
		this.listeners = listeners;
		portalURL = url;
	}

	/**
	 * @see javax.portlet.BaseURL#getParameterMap()
	 */
	public Map<String, String[]> getParameterMap() {
		return portalURL.getQueryParameters();
	}

	/**
	 * @see javax.portlet.BaseURL#setParameter(java.lang.String,
	 *      java.lang.String)
	 */
	public void setParameter(String name, String value) {
		portalURL.setQueryParameter(name, value);
	}

	/**
	 * @see javax.portlet.BaseURL#setParameter(java.lang.String,
	 *      java.lang.String[])
	 */
	public void setParameter(String name, String[] values) {
		portalURL.setQueryParameter(name, values);
	}

	/**
	 * @see javax.portlet.BaseURL#setParameters(java.util.Map)
	 */
	public void setParameters(Map<String, String[]> parameters) {
		for (String key : parameters.keySet()) {
			setParameter(key, parameters.get(key));
		}
	}

	/**
	 * @see javax.portlet.BaseURL#setSecure(boolean)
	 */
	public void setSecure(boolean secure) throws PortletSecurityException {
		throw new PortletSecurityException("Not supported.");
	}

	/**
	 * @see javax.portlet.BaseURL#write(java.io.Writer)
	 */
	public void write(Writer out) throws IOException {
		write(out, true);
	}

	/**
	 * @see javax.portlet.BaseURL#write(java.io.Writer, boolean)
	 */
	public void write(Writer out, boolean escapeXML) throws IOException {
		//TODO
		out.write(toString());
	}

	public String toString() {
		invokeListeners();
		return portalURL.toString();
	}

	public void addProperty(String key, String value) {
		portalURL.addProperty(key, value);
	}

	public void setProperty(String key, String value) {
		portalURL.setProperty(key, value);
	}

	private void invokeListeners() {
		if (listeners == null || listeners.size() == 0) {
			return;
		}
		
		for (PortletURLGenerationListener listener : listeners) {
			invokeListener(listener);
		}
	}
	
	protected abstract void invokeListener(PortletURLGenerationListener listener);
}
