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

package org.cyberelay.portletcontainer.legacy.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.jetspeed.portlet.AccessDeniedException;
import org.apache.jetspeed.portlet.PortletWindow;
import org.cyberelay.portletcontainer.PortletContainer;
import org.cyberelay.portletcontainer.legacy.PortletWindowEx;
import org.cyberelay.portletcontainer.legacy.service.PortletNavigationStateService;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jan 18, 2008
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 678 $
 * <li>Last Update Time: $Date: 2008-03-21 03:31:15 +0000 (Fri, 21 Mar 2008) $
 * </ul>
 * 
 */
public class PortletWindowImpl implements PortletWindow {
	private PortletContainer portletContainer;
	private HttpServletRequest request;
	private PortletWindowEx windowEx;

	public PortletWindowImpl(HttpServletRequest request, PortletWindowEx window) {
//		this.portletContainer = (PortletContainer) request
//				.getAttribute(PortletContainer.PORTLET_CONTAINER);

		this.request = request;
		this.windowEx = window;
	}

	private PortletNavigationStateService getNavigationStateService() {
		return portletContainer.getService(PortletNavigationStateService.class);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletWindow#isDetached()
	 * @deprecated
	 */
	public boolean isDetached() {
		return PortletWindow.State.DETACHED.equals(getWindowState());
	}

	/**
	 * @deprecated
	 * @see org.apache.jetspeed.portlet.PortletWindow#isMaximized()
	 */
	public boolean isMaximized() {
		return PortletWindow.State.MAXIMIZED.equals(getWindowState());
	}

	/**
	 * @deprecated
	 * @see org.apache.jetspeed.portlet.PortletWindow#isMinimized()
	 */
	public boolean isMinimized() {
		return PortletWindow.State.MINIMIZED.equals(getWindowState());
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletWindow#getWindowState()
	 */
	public State getWindowState() {
		return getNavigationStateService().getWindowState(request, windowEx);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletWindow#setWindowState(org.apache.jetspeed.portlet.PortletWindow.State)
	 */
	public void setWindowState(State state) throws AccessDeniedException {
		getNavigationStateService().setWindowState(request, windowEx, state);
	}

}
