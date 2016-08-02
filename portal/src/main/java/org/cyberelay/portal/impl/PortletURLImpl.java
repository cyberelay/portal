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

import java.util.List;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletURL;
import javax.portlet.PortletURLGenerationListener;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import org.cyberelay.portal.PortalURL;

/**
 * @author Roger Tang
 * 
 */
class PortletURLImpl extends BaseURLImpl implements PortletURL {

	/**
	 * @param url
	 */
	public PortletURLImpl(PortalURL url, List<PortletURLGenerationListener> listeners) {
		super(url, listeners);
	}

	public PortletMode getPortletMode() {
		String mode = portalURL.getPortletMode();
		return mode == null ? null : new PortletMode(mode);
	}

	/**
	 * @see javax.portlet.PortletURL#getWindowState()
	 */
	public WindowState getWindowState() {
		String state = portalURL.getPortletWindowState();
		return state == null ? null : new WindowState(state);
	}

	/**
	 * @see javax.portlet.PortletURL#removePublicRenderParameter(java.lang.String)
	 */
	public void removePublicRenderParameter(String name) {
		// TODO Auto-generated method stub

	}

	public void setPortletMode(PortletMode portletMode) throws PortletModeException {
		portalURL.setPortletMode(portletMode.toString());
	}

	public void setWindowState(WindowState windowState) throws WindowStateException {
		portalURL.setPortletWindowState(windowState.toString());
	}

	@Override
	protected void invokeListener(PortletURLGenerationListener listener) {
		if(PortalURL.PORTLET_ACTION.equals(portalURL.getType())) {
			listener.filterActionURL(this);
		} else if(PortalURL.PORTLET_RENDER.equals(portalURL.getType())) {
			listener.filterRenderURL(this);
		}
	}
}
