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

package org.cyberelay.portal.tag;

import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import org.cyberelay.portal.PortletWindowDefinition;
import org.cyberelay.portal.ui.PortletPane;
import org.cyberelay.portal.ui.UIComponent;
import org.cyberelay.portlet.PortletConfigEx;
import org.cyberelay.portletcontainer.NavigationState;
import org.cyberelay.portletcontainer.PortletApplication;
import org.cyberelay.portletcontainer.PortletApplicationRegistry;

/**
 * @author Roger Tang
 * 
 */
abstract class PortletControlTag extends PortalTagSupport {

	private static final long serialVersionUID = 1291018346963898967L;

	protected PortletWindowDefinition getRenderingPortletWindow() {
		UIComponent component = getRenderingUIComponent();
		if (component instanceof PortletPane) {
			return ((PortletPane) component).getPortletWindowDefinition();
		}
		
		return (PortletWindowDefinition) getHttpRequest().getAttribute(PORTAL_RENDERING_PORTLET_WINDOW);
	}

	protected PortletConfigEx getPortletConfig() {
		PortletWindowDefinition window = getRenderingPortletWindow();

		if (window != null) {
			PortletApplication app = PortletApplicationRegistry.getApplication(window);
			return app.getApplicationConfig().getPortletConfig(window.getPortletName());
		}

		return null;
	}

	protected NavigationState getNavigationState() {
		return getRenderingPortletWindow().getNavigationState(getHttpRequest());
	}

	protected boolean isPortletModeSupported(PortletMode mode) {
		return getRenderingPortletWindow().isPortletModeAllowed(getHttpRequest(), mode);
	}

	protected PortletMode getPortletMode() {
		return getNavigationState().getPortletMode();
	}

	protected boolean isWindowStateSupported(WindowState state) {
		return getRenderingPortletWindow().isWindowStateAllowed(getHttpRequest(), state);
	}

	protected WindowState getWindowState() {
		return getNavigationState().getWindowState();
	}

	protected PortletURL createRenderURL() {
		return getRenderingPortletWindow().createRenderURL(getHttpRequest());
	}

}
