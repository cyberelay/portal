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

package org.cyberelay.portal.util;

import javax.servlet.http.HttpServletRequest;

import org.cyberelay.portal.Client;
import org.cyberelay.portal.PageDefinition;
import org.cyberelay.portal.PortalApplication;
import org.cyberelay.portal.PortalConstants;
import org.cyberelay.portal.PortalRuntimeException;
import org.cyberelay.portal.PortalURL;
import org.cyberelay.portal.User;
import org.cyberelay.portal.service.AuthenticationService;
import org.cyberelay.portletcontainer.PortletContainer;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Mar 21, 2008
 * <li>Last Editor: $Author$
 * <li>Current Revision: $Revision$
 * <li>Last Update Time: $Date$
 * </ul>
 * 
 */
public final class PortalRequestUtil implements PortalConstants {

	private PortalRequestUtil() {
		// disregard it.
	}

	/**
	 * Only is the given request a portal page request this method would return
	 * a non-null portal URL.
	 * 
	 * @param request
	 * @return
	 */
	public static PortalURL getRequestURL(HttpServletRequest request) {
		return (PortalURL) request.getAttribute(PORTAL_REQUESTING_URL);
	}

	/**
	 * Only is the given request a portal page request this method would return
	 * a non-null page.
	 * 
	 * @param request
	 * @return
	 */
	public static PageDefinition getRequestPage(HttpServletRequest request) {
		return (PageDefinition) request.getAttribute(PORTAL_REQUESTING_PAGE);
	}

	public static User getUser(HttpServletRequest request) {
		return (User) request.getAttribute(PORTAL_REQUESTING_USER);
	}

	public static boolean isUserLoggedIn(HttpServletRequest request) {
		return getAuthenticationService(request).isUserLoggedIn(request);
	}

	/**
	 * Checks if the request has been redirected.
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isRedirected(HttpServletRequest request) {
		return "true".equalsIgnoreCase((String)request.getAttribute(PORTAL_RESPONSE_REDIRECTED));
	}

	public static Client getClient(HttpServletRequest request) {
		return (Client) request.getAttribute(PORTAL_REQUESTING_CLIENT);
	}

	public static boolean isPortletActionRequest(HttpServletRequest request) {
		PortalURL url = getRequestURL(request);
		return url != null && PortalURL.PORTLET_ACTION.equals(url.getType());
	}
	
	public static PortalApplication getPortalApplication(HttpServletRequest request) {
		PortalApplication portal = (PortalApplication) request.getAttribute(PORTAL_APPLICATION);

		if (portal == null) {
			throw new PortalRuntimeException("Cannot determine portal application. "
					+ "Make sure PortalRequestFilter has been properly configured in web.xml.");
		}

		return portal;
	}

	public static PortletContainer getPortletContainer(HttpServletRequest request) {
		return getPortalApplication(request).getPortletContainer();
	}

	/* =========================================================================================== */

	private static AuthenticationService getAuthenticationService(HttpServletRequest request) {
		return getPortalApplication(request).getService(AuthenticationService.class);
	}

}
