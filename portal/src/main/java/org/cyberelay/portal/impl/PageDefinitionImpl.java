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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyberelay.portal.PageDefinition;
import org.cyberelay.portal.PortletWindowDefinition;
import org.cyberelay.portal.ui.ContainerPane;
import org.cyberelay.portal.ui.PortletPane;
import org.cyberelay.portal.ui.Theme;
import org.cyberelay.portal.ui.UIPane;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
public class PageDefinitionImpl implements PageDefinition {

	private String uniqueID;

	private Theme theme;

	private PortletWindowDefinition[] portletWindows;

	public PageDefinitionImpl(String uniqueID, Theme theme) {
		this.uniqueID = uniqueID;
		this.theme = theme;
		initPortletWindowDefinitions();
	}

	private void initPortletWindowDefinitions() {
		List<PortletWindowDefinition> windows = new ArrayList<PortletWindowDefinition>();
		extractPortletWindowDefinition(theme.getRootPane(), windows);
		portletWindows = windows.toArray(new PortletWindowDefinition[windows.size()]);
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public PortletWindowDefinition[] getPortletWindows() {
		return portletWindows;
	}

	public void render(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.theme.render(request, response);
	}
	
	public PortletWindowDefinition getPortletWindow(String windowUniqueID) {
		PortletWindowDefinition[] windows = getPortletWindows();
		for (PortletWindowDefinition window : windows) {
			if (window.getUniqueID().equals(windowUniqueID)) {
				return window;
			}
		}

		return null;
	}

	private void extractPortletWindowDefinition(UIPane pane, List<PortletWindowDefinition> windows) {
		if (pane instanceof PortletPane) {
			windows.add(((PortletPane) pane).getPortletWindowDefinition());
		} else if (pane instanceof ContainerPane) {
			Iterator<UIPane> children = ((ContainerPane) pane).children();
			while (children.hasNext()) {
				UIPane child = children.next();
				extractPortletWindowDefinition(child, windows);
			}
		}
	}
}
