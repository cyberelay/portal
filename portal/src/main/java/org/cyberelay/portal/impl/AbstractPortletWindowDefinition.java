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

import java.util.Iterator;
import java.util.List;

import javax.portlet.PortalContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.PortletURLGenerationListener;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;

import org.cyberelay.portal.Client;
import org.cyberelay.portal.PortalApplication;
import org.cyberelay.portal.PortalConstants;
import org.cyberelay.portal.PortalContextEx;
import org.cyberelay.portal.PortalURL;
import org.cyberelay.portal.PortletWindowDefinition;
import org.cyberelay.portal.service.PortalURLService;
import org.cyberelay.portal.util.PortalRequestUtil;
import org.cyberelay.portlet.PortletConfigEx;
import org.cyberelay.portletcontainer.NavigationState;
import org.cyberelay.portletcontainer.PortletApplication;
import org.cyberelay.portletcontainer.PortletApplicationConfig;
import org.cyberelay.portletcontainer.PortletApplicationRegistry;

/**
 * @author Roger Tang
 * 
 */
public abstract class AbstractPortletWindowDefinition implements
		PortletWindowDefinition,
		PortalConstants {

	private static final String STATE_KEY_PREFIX = "org.cyberelay.portlet.navigation.state.";

	public boolean isTargetPortlet(HttpServletRequest request) {
		PortalURL url = PortalRequestUtil.getRequestURL(request);
		return url != null && getUniqueID().equals(url.getPortletWindowID());
	}

	public PortletPreferences getPortletPreferences(HttpServletRequest request) {
		return getPortletConfig().getInitPortletPreferences();
	}

	public PortalContext getPortalContext(HttpServletRequest request) {
		return _getPortalContext(request);
	}

	public NavigationState getNavigationState(HttpServletRequest request) {
		String key = STATE_KEY_PREFIX + getUniqueID();
		NavigationState result = (NavigationState) request.getAttribute(key);
		if (result == null) {
			result = new NavigationStateImpl(request, this);
			request.setAttribute(key, result);
		}
		return result;
	}

	public String getClientAcceptedContentType(HttpServletRequest request) {
		return PortalRequestUtil.getClient(request).getAcceptedContentType();
	}

	public Iterator<String> getClientAcceptedContentTypes(HttpServletRequest request) {
		return PortalRequestUtil.getClient(request).getAcceptedContentTypes();
	}

	public PortletURL createActionURL(HttpServletRequest request) throws IllegalStateException {
		PortalURL url = createPortalURL(request);
		url.setType(PortalURL.PORTLET_ACTION);
		return new PortletURLImpl(url, getListeners());
	}

	public PortletURL createRenderURL(HttpServletRequest request) throws IllegalStateException {
		PortalURL url = createPortalURL(request);
		url.setType(PortalURL.PORTLET_RENDER);
		return new PortletURLImpl(url, getListeners());
	}

	public ResourceURL createResourceURL(HttpServletRequest request) throws IllegalStateException {
		PortalURL url = createPortalURL(request);
		url.setType(PortalURL.PORTLET_RESOURCE);
		return new ResourceURLImpl(url, getListeners());
	}

	public String getCacheability(HttpServletRequest request) {
		return PortalRequestUtil.getRequestURL(request).getProperty(ResourceURLImpl.CACHE);
	}

	public String getResourceID(HttpServletRequest request) {
		return PortalRequestUtil.getRequestURL(request).getResourceID();
	}

	public boolean isPortletModeAllowed(HttpServletRequest request, PortletMode mode) {
		return _getPortalContext(request).isPortletModeSupported(mode)
				&& getPortletConfig().isSupportedPortletMode(mode);
	}

	public boolean isWindowStateAllowed(HttpServletRequest request, WindowState state) {
		return _getPortalContext(request).isWindowStateSupported(state)
				&& getPortletConfig().isSupportedWindowState(state);
	}

	public PortletConfigEx getPortletConfig() {
		return getPortletApplicationConfig().getPortletConfig(getPortletName());
	}

	public String toString() {
		return "[PageID = "
				+ getPageUniqueID()
				+ ", WindowID = "
				+ getUniqueID()
				+ ", PortletName = "
				+ getPortletName()
				+ ", PortletContextPath = "
				+ getPortletContextPath()
				+ "]";
	}

	/**
	 * The method is supposed to be override by subclass.
	 * 
	 * @param url
	 */
	protected void setCustomProperties(PortalURL url) {

	}

	/* =============================================================================== */

	private PortalContextEx _getPortalContext(HttpServletRequest request) {
		return PortalRequestUtil.getPortalApplication(request).getPortalContext();
	}

	protected PortalURL createPortalURL(HttpServletRequest request) {
		PortalURL url =
				PortalRequestUtil.getPortalApplication(request).getService(PortalURLService.class).createPortalURL();
		url.setPortletWindowID(getUniqueID());
		url.setPageID(getPageUniqueID());

		setCustomProperties(url);

		return url;
	}

	private List<PortletURLGenerationListener> getListeners() {
		return getPortletApplication().getApplicationConfig().getListeners();
	}

	private PortletApplication getPortletApplication() {
		return PortletApplicationRegistry.getApplication(this);
	}

	private PortletApplicationConfig getPortletApplicationConfig() {
		return getPortletApplication().getApplicationConfig();
	}
}
