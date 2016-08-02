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

package org.cyberelay.portal.ui.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyberelay.portal.PortletWindowDefinition;
import org.cyberelay.portal.ui.PortletPane;
import org.cyberelay.portal.util.Assert;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 684 $
 * <li>Last Update Time: $Date: 2008-03-23 10:24:06 +0000 (Sun, 23 Mar 2008) $
 * </ul>
 * 
 */
public class PortletPaneImpl extends UIPaneImpl implements PortletPane {
	private PortletWindowDefinition portletWindow;

	public PortletPaneImpl(Template template, PortletWindowDefinition portletWindow) {
		super(template);

		Assert.notNull(portletWindow);
		this.portletWindow = portletWindow;
	}

	public PortletWindowDefinition getPortletWindowDefinition() {
		return this.portletWindow;
	}
	
	@Override
	public void render(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute(PORTAL_RENDERING_PORTLET_WINDOW, portletWindow);
		super.render(request, response);
	}

	public State getState(HttpServletRequest req) {
		//TODO
		return NORMAL;
	}
}
