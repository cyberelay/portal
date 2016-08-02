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

package org.cyberelay.portal;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.cyberelay.portal.service.AuthenticationService;
import org.cyberelay.portal.service.ClientInfoService;
import org.cyberelay.portal.service.PageDefinitionService;
import org.cyberelay.portal.service.PortalURLService;
import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
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
public class PortalRequestFilter implements Filter, PortalConstants {
	private static final Logger LOG = LoggerFactory.getLogger(PortalRequestFilter.class);

	private PortalApplication portal;

	public void destroy() {
	}

	/**
	 * populate built-in attributes into incoming request
	 * 
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Invoking PortalRequestFilter.doFilter()... ");
		}

		resp = new PortalHttpResponse(req, resp);
		req = new PortalHttpRequest(req, portal);

		chain.doFilter(req, resp);
	}

	/**
	 * Init portal application variable.
	 */
	public void init(FilterConfig config) throws ServletException {
		portal = (PortalApplication) config.getServletContext().getAttribute(PORTAL_APPLICATION);
	}

	private static class PortalHttpRequest extends HttpServletRequestWrapper {
		private HttpSession portalHttpSession;
		private PortalApplication portal;

		public PortalHttpRequest(ServletRequest request, PortalApplication portal) {
			super((HttpServletRequest) request);

			this.portal = portal;
			this.portalHttpSession = getSession(true);

			/*
			 * initialize built-in attributes which may be overridden by
			 * downstream request handling
			 */
			setAttribute(PORTAL_HTTP_REQUEST, this);
			setAttribute(PORTAL_REQUESTING_URL, getPortalURL());
			setAttribute(PORTAL_REQUESTING_CLIENT, getClient());
			setAttribute(PORTAL_REQUESTING_USER, getUser());
			setAttribute(PORTAL_REQUESTING_PAGE, getRequestingPage());
		}

		@Override
		public Object getAttribute(String name) {
			/* following attributes cannot be overridden. */
			if (PORTAL_APPLICATION.equals(name)) {
				return portal;
			} else if (PORTAL_HTTP_SESSION.equals(name)) {
				return portalHttpSession;
			} else if (PortletContainer.PORTLET_CONTAINER.equals(name)) {
				return portal.getPortletContainer();
			}

			return super.getAttribute(name);
		}

		private User getUser() {
			return portal.getService(AuthenticationService.class).getUser(this);
		}

		private Client getClient() {
			return portal.getService(ClientInfoService.class).getClient(this);
		}

		private PortalURL getPortalURL() {
			return portal.getService(PortalURLService.class).parsePortalURL(this);
		}

		private PageDefinition getRequestingPage() {
			return portal.getService(PageDefinitionService.class).getPage(this);
		}
	}

	private static class PortalHttpResponse extends HttpServletResponseWrapper {
		private ServletRequest request;

		public PortalHttpResponse(ServletRequest request, ServletResponse response) {
			super((HttpServletResponse) response);

			this.request = request;
			request.setAttribute(PORTAL_HTTP_RESPONSE, response);
		}

		@Override
		public void sendRedirect(String location) throws IOException {
			request.setAttribute(PORTAL_RESPONSE_REDIRECTED, "true");

			super.sendRedirect(location);
		}
	}
}
