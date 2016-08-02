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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyberelay.portal.PageDefinition;
import org.cyberelay.portal.PortalApplication;
import org.cyberelay.portal.PortalConstants;
import org.cyberelay.portal.PortalURL;
import org.cyberelay.portal.PortletWindowDefinition;

/**
 * Solo Portlet Page has only one portlet window embedded.
 * 
 * @author Roger Tang
 * 
 */
public class SoloPortletPageDefinition implements PageDefinition {

	private static final String PORTLET_CONTEXT_PATH = "portlet.ctx.path";

	private static final String PORTLET_NAME = "portlet.name";

	private static final String SOLO_PORTLET_PAGE_ID = "solo.portlet.page";

	private static final String PAGE_TEMPLATE = "solo.portlet.page.template";

	private SoloPortletWindowDefinition solo;
	private String portletContextPath;
	private String portletWindowId;
	private String portletName;

	public static boolean isSoloPortletPageURL(PortalURL url) {
		return SOLO_PORTLET_PAGE_ID.equals(url.getPageID());
	}

	public SoloPortletPageDefinition(String portletContextPath, String portletName) {
		this.portletContextPath = portletContextPath;
		this.portletName = portletName;
		this.portletWindowId = "p" + (portletContextPath + portletName).hashCode();
		solo = new SoloPortletWindowDefinition();
	}

	public SoloPortletPageDefinition(PortalURL url) {
		this.portletContextPath = url.getProperty(PORTLET_CONTEXT_PATH);
		this.portletName = url.getProperty(PORTLET_NAME);
		solo = new SoloPortletWindowDefinition();
	}

	public PortletWindowDefinition getPortletWindow() {
		return solo;
	}

	public PortletWindowDefinition getPortletWindow(String windowUniqueID) {
		return solo;
	}

	public PortalURL createPortalURL(HttpServletRequest request) {
		PortalURL url = solo.createPortalURL(request);
		url.setType(PortalURL.PAGE);
		return url;
	}
	
	public PortletWindowDefinition[] getPortletWindows() {
		return new PortletWindowDefinition[] { solo };
	}

	public String getUniqueID() {
		return SOLO_PORTLET_PAGE_ID;
	}

	public void render(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute(PortalConstants.PORTAL_RENDERING_PORTLET_WINDOW, solo);
		String templatePath = getPortal(request).getConfiguration().getValue(PAGE_TEMPLATE);

		request.getRequestDispatcher(templatePath).include(request, response);
	}

	private PortalApplication getPortal(HttpServletRequest request) {
		return (PortalApplication) request.getAttribute(PortalConstants.PORTAL_APPLICATION);
	}

	private class SoloPortletWindowDefinition extends AbstractPortletWindowDefinition {

		public String getPageUniqueID() {
			return SOLO_PORTLET_PAGE_ID;
		}

		public String getPortletContextPath() {
			return portletContextPath;
		}

		public String getPortletName() {
			return portletName;
		}

		public String getUniqueID() {
			return portletWindowId;
		}

		@Override
		protected void setCustomProperties(PortalURL url) {
			url.setProperty(PORTLET_CONTEXT_PATH, portletContextPath);
			url.setProperty(PORTLET_NAME, portletName);
		}
	}
}
