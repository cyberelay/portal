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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.jetspeed.portlet.Portlet;
import org.apache.jetspeed.portlet.PortletResponse;
import org.apache.jetspeed.portlet.PortletURI;
import org.apache.jetspeed.portlet.PortletWindow.State;
import org.cyberelay.portal.util.Assert;
import org.cyberelay.portletcontainer.PortletContainer;
import org.cyberelay.portletcontainer.legacy.Constants;
import org.cyberelay.portletcontainer.legacy.PortletWindowEx;
import org.cyberelay.portletcontainer.legacy.service.PortletNavigationStateService;
import org.cyberelay.portletcontainer.legacy.service.PortletURIService;

/**
 * 
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jan 21, 2008
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 678 $
 * <li>Last Update Time: $Date: 2008-03-21 03:31:15 +0000 (Fri, 21 Mar 2008) $
 * </ul>
 * 
 */
public class PortletResponseImpl extends HttpServletResponseWrapper implements PortletResponse {
	private PortletContainer portletContainer;
	private HttpServletRequest request;
	private PortletWindowEx windowEx;

	public PortletResponseImpl(HttpServletRequest request, HttpServletResponse response,
			PortletWindowEx window) {
		super(response);

		Assert.notNull(window, "Portlet window cannot be null!");
		this.windowEx = window;
		this.request = request;
//		this.portletContainer = (PortletContainer) request
//				.getAttribute(PortletContainer.PORTLET_CONTAINER);

		request.setAttribute(Constants.PORTLET_RESPONSE + "." + window.getUniqueID(), this);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletResponse#getContentType()
	 */
	public String getContentType() {
		return "html/text";
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletResponse#getCharacterSet()
	 */
	public String getCharacterSet() {
		return super.getCharacterEncoding();
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletResponse#createURI()
	 */
	public PortletURI createURI() {
		return createURI(null, null);
	}

	private PortletNavigationStateService getNavigationStateService() {
		return portletContainer.getService(PortletNavigationStateService.class);
	}

	private PortletURIService getPortletURIService() {
		return portletContainer.getService(PortletURIService.class);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletResponse#createURI(org.apache.jetspeed.portlet.PortletWindow.State)
	 */
	public PortletURI createURI(State state) {
		return createURI(null, state);
	}

	protected PortletURI createURI(Portlet.Mode mode, State state) {
		if (mode == null) {
			mode = getNavigationStateService().getMode(request, windowEx);
		}

		if (state == null) {
			state = getNavigationStateService().getWindowState(request, windowEx);
		}

		return getPortletURIService().createPortletURI(request, windowEx, state, mode);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletResponse#createReturnURI()
	 */
	public PortletURI createReturnURI() {
		return createURI(getNavigationStateService().getPreviousMode(request, windowEx), null);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletResponse#encodeURI(java.lang.String)
	 */
	public String encodeURI(String url) {
		return encodeURL(url);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletResponse#encodeNamespace(java.lang.String)
	 */
	public String encodeNamespace(String name) {
		if (name == null) {
			return name;
		}
		return windowEx.getUniqueID() + "_" + name;
	}

}
