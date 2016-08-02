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
package org.cyberelay.portletcontainer.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.Event;
import javax.servlet.http.HttpServletRequest;

import org.cyberelay.portal.PageDefinition;
import org.cyberelay.portal.PortalConstants;
import org.cyberelay.portal.PortletWindowDefinition;
import org.cyberelay.portlet.PortletConfigEx;
import org.cyberelay.portletcontainer.AbstractPortletContainerService;
import org.cyberelay.portletcontainer.PortletApplicationRegistry;
import org.cyberelay.portletcontainer.PortletWindow;
import org.cyberelay.portletcontainer.service.PortletEventService;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Mar 3, 2008
 * <li>Last Editor: $Author$
 * <li>Current Revision: $Revision$
 * <li>Last Update Time: $Date$
 * </ul>
 * 
 */
public class PortletEventServiceImpl extends AbstractPortletContainerService implements
		PortletEventService,
		PortalConstants {

	/**
	 * @see org.cyberelay.portletcontainer.service.PortletEventService#getSubscribers(javax.servlet.http.HttpServletRequest,
	 *      javax.portlet.Event)
	 */
	public PortletWindow[] getSubscribers(HttpServletRequest request, Event event) {
		PageDefinition page = (PageDefinition) request.getAttribute(PORTAL_REQUESTING_PAGE);
		List<PortletWindowDefinition> windows = new ArrayList<PortletWindowDefinition>();

		for (PortletWindowDefinition window : page.getPortletWindows()) {
			if(getPortletConfig(window).isProcessableEventName(event.getQName())) {
				windows.add(window);
			}
		}
		
		return windows.toArray(new PortletWindowDefinition[windows.size()]);
	}

	private PortletConfigEx getPortletConfig(PortletWindowDefinition window) {
		return PortletApplicationRegistry.getApplication(
				window.getPortletContextPath()).getApplicationConfig().getPortletConfig(
				window.getPortletName());
	}
}
