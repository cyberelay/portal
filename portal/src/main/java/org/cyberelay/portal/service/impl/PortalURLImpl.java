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

package org.cyberelay.portal.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.cyberelay.portal.FrozenURLException;
import org.cyberelay.portal.PortalURL;
import org.cyberelay.portal.util.StringUtil;
import org.cyberelay.portal.util.URLTokenizer;
import org.cyberelay.portal.util.URLUTF8Encoder;

/**
 * @author Roger Tang
 * 
 * "/portal/page /ut=<URL_TYPE> /pg=<PAGE_ID> /pw=<PORTLET_WIN_ID> /pm=<PORTLET_MODE>
 * /ws=<WINDOW_STATE> /ri=<RESOURCE_ID> /as=<ACTION_STRING>
 * 
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 625 $
 * <li>Last Update Time: $Date: 2008-03-07 04:36:51 +0000 (Fri, 07 Mar 2008) $
 * </ul>
 * 
 */
class PortalURLImpl implements PortalURL {

	private static final Set<String> ALL_URL_TYPES = new HashSet<String>();

	private static final Set<String> STANDARD_PROPERTIES = new HashSet<String>();

	private static final String SLASH = "/";

	private static final String EQUALS = "=";

	private static final String AND = "&";

	private static final String QUESTION_MARK = "?";

	static {
		ALL_URL_TYPES.add(PAGE);
		ALL_URL_TYPES.add(PORTLET_ACTION);
		ALL_URL_TYPES.add(PORTLET_RENDER);
		ALL_URL_TYPES.add(PORTLET_RESOURCE);
		ALL_URL_TYPES.add(LOGOUT);

		STANDARD_PROPERTIES.add(PROP_ACTION_STRTING);
		STANDARD_PROPERTIES.add(PROP_PAGE);
		STANDARD_PROPERTIES.add(PROP_PORTLET_MODE);
		STANDARD_PROPERTIES.add(PROP_PORTLET_WINDOW);
		STANDARD_PROPERTIES.add(PROP_RESOURCE_ID);
		STANDARD_PROPERTIES.add(PROP_URL_TYPE);
		STANDARD_PROPERTIES.add(PROP_WINDOW_STATE);
	}

	private String urlPrefix;

	private boolean frozen = false;

	private Map<String, String[]> properties;

	private Map<String, String[]> parameters;

	public PortalURLImpl(String urlPrefix) {
		this.urlPrefix = urlPrefix;
		this.properties = new HashMap<String, String[]>();
		this.parameters = new HashMap<String, String[]>();
	}

	public PortalURLImpl(String urlPrefix, HttpServletRequest request) {
		this.urlPrefix = urlPrefix;
		populateProperties(request);
		populateQueryParameters(request);
	}

	private void populateQueryParameters(HttpServletRequest request) {
		parameters = new HashMap<String, String[]>();
		String queryString = request.getQueryString();
		if (queryString == null || queryString.length() == 0) {
			return;
		}
		if (queryString.startsWith(QUESTION_MARK)) {
			queryString = queryString.substring(1);
		}
		String[] nameValuePairs = queryString.split(AND);
		for (String nameValuePair : nameValuePairs) {
			if (nameValuePair == null || nameValuePair.length() == 0) {
				continue;
			}
			String[] pair = nameValuePair.split(EQUALS);
			if (pair.length >= 2) {
				String name = URLUTF8Encoder.decode(pair[0]);
				String value = URLUTF8Encoder.decode(pair[1]);

				String[] values = parameters.get(name);
				if (values == null) {
					values = new String[] { value };
				} else {
					values = StringUtil.addToArray(values, value);
				}
				parameters.put(name, values);
			}
		}
	}

	/**
	 * 
	 * @param request
	 */
	private void populateProperties(HttpServletRequest request) {
		properties = new HashMap<String, String[]>();

		String requestURI = request.getRequestURI();
		if (requestURI.startsWith(urlPrefix)) {
			requestURI = StringUtil.replaceFirst(requestURI, urlPrefix, "");
		}

		URLTokenizer tokenizer = new URLTokenizer(requestURI, SLASH);
		while (tokenizer.hasMoreTokens()) {
			String[] strArray = tokenizer.nextToken().split(EQUALS);

			String name = URLUTF8Encoder.decode(strArray[0]);
			String[] values = new String[strArray.length - 1];
			for (int i = 0; i < values.length; i++) {
				values[i] = URLUTF8Encoder.decode(strArray[i + 1]);
			}

			properties.put(name, values);
		}
	}

	public String getPrefix() {
		return urlPrefix;
	}

	public String getProperty(String name) {
		String[] values = getProperties(name);
		return (values == null || values.length == 0) ? null : values[0];
	}

	public void addProperty(String name, String value) throws FrozenURLException {
		checkFrozen();

		String[] values = getProperties(name);
		if (values == null) {
			setProperty(name, value);
		} else {
			setProperty(name, StringUtil.addToArray(values, value));
		}
	}

	public void setProperty(String name, String value) throws FrozenURLException {
		checkFrozen();

		if (value == null) {
			properties.remove(name);
		} else {
			properties.put(name, new String[] { value });
		}
	}

	public String[] getProperties(String name) {
		return properties.get(name);
	}

	public void setProperty(String name, String[] value) throws FrozenURLException {
		checkFrozen();

		if (value == null) {
			properties.remove(name);
		} else {
			properties.put(name, value);
		}
	}

	public void freeze() {
		frozen = true;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public String getType() {
		return getProperty(PROP_URL_TYPE);
	}

	public void setType(String type) {
		setProperty(PROP_URL_TYPE, type);
	}

	public String getPageID() {
		return getProperty(PROP_PAGE);
	}

	public void setPageID(String pageName) {
		setProperty(PROP_PAGE, pageName);
	}

	public String getPortletWindowID() {
		return getProperty(PROP_PORTLET_WINDOW);
	}

	public void setPortletWindowID(String windowName) {
		setProperty(PROP_PORTLET_WINDOW, windowName);
	}

	public String getPortletMode() {
		return getProperty(PROP_PORTLET_MODE);
	}

	public void setPortletMode(String mode) {
		setProperty(PROP_PORTLET_MODE, mode);
	}

	public String getPortletWindowState() {
		return getProperty(PROP_WINDOW_STATE);
	}

	public void setPortletWindowState(String state) {
		setProperty(PROP_WINDOW_STATE, state);
	}

	public String getResourceID() {
		return getProperty(PROP_RESOURCE_ID);
	}

	public void setResourceID(String resourceID) {
		setProperty(PROP_RESOURCE_ID, resourceID);
	}

	public String getActionString() {
		return getProperty(PROP_ACTION_STRTING);
	}

	public void setActionString(String actionString) {
		setProperty(PROP_ACTION_STRTING, actionString);
	}

	private void checkFrozen() {
		if (frozen) {
			throw new FrozenURLException("Frozen PortalURL! Properties cannot be changed!");
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(urlPrefix);
		appendStandardProperties(sb);
		appendNonstandardProperties(sb);
		appendQueryParameters(sb);

		return sb.toString();
	}

	private void appendStandardProperties(StringBuffer sb) {
		appendProperty(sb, PROP_URL_TYPE);
		appendProperty(sb, PROP_PAGE);
		appendProperty(sb, PROP_PORTLET_WINDOW);
		appendProperty(sb, PROP_PORTLET_MODE);
		appendProperty(sb, PROP_WINDOW_STATE);
		appendProperty(sb, PROP_RESOURCE_ID);
		appendProperty(sb, PROP_ACTION_STRTING);
	}

	private void appendNonstandardProperties(StringBuffer sb) {
		for (String property : properties.keySet()) {
			if (!STANDARD_PROPERTIES.contains(property)) {
				appendProperty(sb, property);
			}
		}
	}

	private void appendProperty(StringBuffer sb, String propertyName) {
		String[] values = getProperties(propertyName);
		if (values == null || values.length == 0) {
			return;
		}

		sb.append(SLASH).append(URLUTF8Encoder.encode(propertyName));
		for (String value : values) {
			sb.append(EQUALS).append(URLUTF8Encoder.encode(value));
		}
	}

	private void appendQueryParameters(StringBuffer sb) {
		if (parameters == null || parameters.isEmpty()) {
			return;
		}
		int index = 0;
		sb.append(QUESTION_MARK);
		for (String name : parameters.keySet()) {
			String[] values = parameters.get(name);
			for (String value : values) {
				if (index != 0) {
					sb.append(AND);
				}
				sb.append(URLUTF8Encoder.encode(name));
				sb.append(EQUALS);
				sb.append(URLUTF8Encoder.encode(value));
				index++;
			}
		}
	}

	/**
	 * 
	 * @see org.cyberelay.portal.PortalURL#getQueryParameterNames()
	 */
	public Iterator<String> getQueryParameterNames() {
		return parameters.keySet().iterator();
	}

	/**
	 * 
	 * @see org.cyberelay.portal.PortalURL#getQueryParameters(java.lang.String)
	 */
	public String[] getQueryParameters(String name) {
		return parameters.get(name);
	}

	public Map<String, String[]> getQueryParameters() {
		return Collections.unmodifiableMap(parameters);
	}

	/**
	 * 
	 * @see org.cyberelay.portal.PortalURL#setQueryParameter(java.lang.String,
	 *      java.lang.String)
	 */
	public void setQueryParameter(String name, String value) throws FrozenURLException {
		parameters.put(name, new String[] { value });
	}

	/**
	 * 
	 * @see org.cyberelay.portal.PortalURL#setQueryParameter(java.lang.String,
	 *      java.lang.String[])
	 */
	public void setQueryParameter(String name, String[] values) throws FrozenURLException {
		parameters.put(name, values);
	}

	/**
	 * 
	 * @see org.cyberelay.portal.PortalURL#getQueryParameter(java.lang.String)
	 */
	public String getQueryParameter(String name) {
		if ((parameters.get(name) != null) && (parameters.get(name).length > 0)) {
			return parameters.get(name)[0];
		}
		return null;
	}

	public static void main(String[] args) {
		PortalURL url = new PortalURLImpl("/portal/page");
		url.setActionString("my action string");
		url.setPageID("my page@%%%");
		url.setPortletMode("view");
		url.setProperty("myprop", new String[] { "value1", " ", "value 3" });
		url.setQueryParameter("my param", new String[] { "Mon/1", "Tue/2", "Wes/3" });
		System.out.println("URL = " + url);
	}
}
