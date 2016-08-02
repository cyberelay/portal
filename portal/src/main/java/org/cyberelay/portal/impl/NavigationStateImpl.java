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

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.cyberelay.portal.PortalApplication;
import org.cyberelay.portal.PortalConstants;
import org.cyberelay.portal.PortalContextEx;
import org.cyberelay.portal.PortalURL;
import org.cyberelay.portlet.PortletConfigEx;
import org.cyberelay.portletcontainer.NavigationState;
import org.cyberelay.portletcontainer.PortletWindow;

/**
 * 
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Feb 22, 2008
 * <li>Last Editor: $Author:losingant $
 * <li>Current Revision: $Revision:541 $
 * <li>Last Update Time: $Date:2008-02-25 11:42:40 +0800 (Mon, 25 Feb 2008) $
 * </ul>
 * 
 */
class NavigationStateImpl implements NavigationState, PortalConstants {
	private static final String[] EMPTY_STRINGS = new String[0];
	private static final String PRIVATE_STATE_KEY_PREFIX = "org.cyberelay.portlet.private.state.";
	private static final String PUBLIC_STATE_KEY = "org.cyberelay.portlet.public.state";

	private HttpSession portalSession;
	private PortletConfigEx config;
	private PortalContextEx portalContext;
	private String privateStateKey;
	private PublicState publicState;
	private PrivateState privateState;

	public NavigationStateImpl(HttpServletRequest request, PortletWindow window) {
		config = window.getPortletConfig();
		portalContext = getPortalContext(request);
		portalSession = (HttpSession) request.getAttribute(PORTAL_HTTP_SESSION);
		privateStateKey = PRIVATE_STATE_KEY_PREFIX + window.getUniqueID();

		initPublicState(request, window);
		initPrivateState(request, window);
	}

	private void initPublicState(HttpServletRequest request, PortletWindow window) {
		publicState = getSharedPublicState();

		if (window.isTargetPortlet(request)) {
			Enumeration<String> names = config.getPublicRenderParameterNames();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				String[] values = request.getParameterValues(name);
				// TODO Is portlet action/resource request eligible to have
				// public render parameters?
				if (values != null) {
					publicState.parameters.put(name, values);
				}
			}
		}
	}

	private void initPrivateState(HttpServletRequest request, PortletWindow window) {
		privateState = new PrivateState();

		PortalURL url = (PortalURL) request.getAttribute(PORTAL_REQUESTING_URL);
		boolean actionURL = isActionURL(url);
		boolean renderURL = isRenderURL(url);
		if (window.isTargetPortlet(request)) {
			if (actionURL || renderURL) {
				if (url.getPortletMode() != null) {
					privateState.portletMode = new PortletMode(url.getPortletMode());
				}
				if (url.getPortletWindowState() != null) {
					privateState.windowState = new WindowState(url.getPortletWindowState());
				}
				if (renderURL) {
					privateState.parameters.putAll(request.getParameterMap());
				}
			}
		} else {
			PrivateState previous = getPrivateState();
			if (previous != null && privateState != previous) {
				privateState.portletMode = previous.portletMode;
				privateState.windowState = previous.windowState;
				privateState.parameters.putAll(previous.parameters);
			}
		}
	}

	private boolean isActionURL(PortalURL url) {
		return PortalURL.PORTLET_ACTION.equals(url.getType());
	}

	private boolean isRenderURL(PortalURL url) {
		return PortalURL.PORTLET_RENDER.equals(url.getType());
	}

	private PortalContextEx getPortalContext(HttpServletRequest request) {
		PortalApplication app = (PortalApplication) request.getAttribute(PORTAL_APPLICATION);
		return app.getPortalContext();
	}

	public boolean isPortletModeAllowed(PortletMode mode) {
		return portalContext.isPortletModeSupported(mode) && config.isSupportedPortletMode(mode);
	}

	public boolean isWindowStateAllowed(WindowState state) {
		return portalContext.isWindowStateSupported(state) && config.isSupportedWindowState(state);
	}

	public PortletMode getPortletMode() {
		return privateState.portletMode;
	}

	public String getPrivateRenderParameter(String key) {
		String[] values = getPrivateRenderParameters(key);
		return (values == null || values.length == 0) ? null : values[0];
	}

	public Map<String, String[]> getPrivateRenderParameterMap() {
		return Collections.unmodifiableMap(privateState.parameters);
	}

	public void clearPrivateRenderParameters() {
		privateState.parameters.clear();
	}

	public String[] getPrivateRenderParameters(String key) {
		String[] values = privateState.parameters.get(key);
		return values == null ? EMPTY_STRINGS : values;
	}

	public String getPublicRenderParameter(String key) {
		String[] values = getPublicRenderParameters(key);
		return (values == null || values.length == 0) ? null : values[0];
	}

	public Map<String, String[]> getPublicRenderParameterMap() {
		Map<String, String[]> result = new HashMap<String, String[]>();
		Enumeration<String> keys = config.getPublicRenderParameterNames();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if (publicState.parameters.containsKey(key)) {
				result.put(key, getPublicRenderParameters(key));
			}
		}
		return Collections.unmodifiableMap(result);
	}

	public String[] getPublicRenderParameters(String key) {
		if (!config.isSupportedPublicRenderParameter(key)) {
			return null;
		}
		return publicState.parameters.get(key);
	}

	public WindowState getWindowState() {
		return privateState.windowState;
	}

	public void setPortletMode(PortletMode mode) {
		privateState.portletMode = mode;
	}

	public void setPrivateRenderParameters(String key, String[] values) {
		privateState.parameters.put(key, values);
	}

	public void setPublicRenderParameters(String key, String[] values) {
		if (config.isSupportedPublicRenderParameter(key)) {
			if (values == null) {
				publicState.parameters.remove(key);
			} else {
				publicState.parameters.put(key, values);
			}
		}
	}

	public void setWindowState(WindowState state) {
		privateState.windowState = state;
	}

	public void store() {
		portalSession.setAttribute(privateStateKey, privateState);
		portalSession.setAttribute(PUBLIC_STATE_KEY, publicState);
	}

	/* ================================================================== */

	private PrivateState getPrivateState() {
		return (PrivateState) portalSession.getAttribute(privateStateKey);
	}

	private PublicState getSharedPublicState() {
		PublicState shared = (PublicState) portalSession.getAttribute(PUBLIC_STATE_KEY);
		if (shared == null) {
			shared = new PublicState();
			portalSession.setAttribute(PUBLIC_STATE_KEY, shared);
		}
		return shared;
	}

	private static class PublicState {
		Map<String, String[]> parameters = new HashMap<String, String[]>();
	}

	private static class PrivateState {
		Map<String, String[]> parameters = new HashMap<String, String[]>();
		PortletMode portletMode = PortletMode.VIEW;
		WindowState windowState = WindowState.NORMAL;
	}
}