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

package org.cyberelay.portal.aggregation.chain;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyberelay.portal.PageDefinition;
import org.cyberelay.portal.PortalApplication;
import org.cyberelay.portal.PortalConfiguration;
import org.cyberelay.portal.PortalConstants;
import org.cyberelay.portal.PortalURL;
import org.cyberelay.portal.PortletWindowDefinition;
import org.cyberelay.portal.aggregation.RequestProcessNode;
import org.cyberelay.portal.service.InvalidURLException;
import org.cyberelay.portal.util.PortalRequestUtil;
import org.cyberelay.portletcontainer.PortletContainer;

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
public abstract class GenericProcessNode implements RequestProcessNode, PortalConstants {
	private PortalApplication portal;

	private RequestProcessNode next;

	protected PortalApplication getPortalApplication() {
		return portal;
	}

	protected PortletContainer getPortletContainer() {
		return portal.getPortletContainer();
	}

	public final void init(PortalApplication portal) {
		this.portal = portal;

		init();
	}

	/**
	 * Facilitation method for subclass overriding.
	 */
	protected void init() {
		// TODO

	}

	public final RequestProcessNode getNext() {
		return this.next;
	}

	public final void setNext(RequestProcessNode next) {
		this.next = next;
	}

	protected final void processNext(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (getNext() != null) {
			getNext().process(request, response);
		}
	}

	protected PortalURL getPortalURL(HttpServletRequest request) throws InvalidURLException {
		return PortalRequestUtil.getRequestURL(request);
	}

	protected PortletWindowDefinition getTargetPortletWindow(HttpServletRequest request)
			throws InvalidURLException {
		for (PortletWindowDefinition window : getRequestingPage(request).getPortletWindows()) {
			if (window.isTargetPortlet(request)) {
				return window;
			}
		}

		return null;
	}

	protected PageDefinition getRequestingPage(HttpServletRequest request) {
		return PortalRequestUtil.getRequestPage(request);
	}

	protected PortalConfiguration getConfiguration() {
		return getPortalApplication().getConfiguration();
	}

}
