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
import org.cyberelay.portal.PortalException;
import org.cyberelay.portal.service.PageDefinitionService;
import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;

/**
 * RequestPreprocessNode provides the following functionalities:
 * <ul>
 * <li> wrapping the incoming request/response to provide additional
 * functionalities with which original request/response objects cannot supply;
 * <li> parsing/validating the incoming request URL
 * <li> logging out portal application if the incoming request is a logout
 * request.
 * <li> resolving request target page. If not such page found,
 * PageNotFoundException would be throw.
 * <li> refreshing portal application configuration if configuration
 * auto-refresh enabled;
 * <li> refreshing portal page definition cache if cache auto-refresh enabled;
 * 
 * @author Roger Tang
 * 
 */
public class PreparationProcessNode extends GenericProcessNode {
	private static final Logger LOG = LoggerFactory.getLogger(PreparationProcessNode.class);

	private static final String KEY_PAGE_DEF_AUTO_REFRESH = "page.definition.auto-refresh";

	private static final String KEY_CONFIG_AUTO_REFRESH = "configuration.auto-refresh";

	private static final String LOGOUT_PAGE_NAME = "logout.page.id";

	private String logoutPageName;

	private boolean pageDefAutoRefresh = false;
	private boolean configAutoRefresh = false;

	@Override
	public void init() {
		pageDefAutoRefresh = getConfiguration().getValueAsBoolean(KEY_PAGE_DEF_AUTO_REFRESH);
		configAutoRefresh = getConfiguration().getValueAsBoolean(KEY_CONFIG_AUTO_REFRESH);
	}

	public void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (logout(request, response)) {
			return;
		}

		refreshConfiguration();
		refreshPageDefinition();

		if (resolvePage(request, response)) {
			processNext(request, response);
		}
	}

	protected boolean resolvePage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PageDefinition page = getRequestingPage(request);

		if (page == null) {
			// TODO elegantly handle PageNotFoundException.
			response.getWriter().write("<p>Page not found!</p>");
			response.getWriter().write("Please check the following URL is correct.");
			response.getWriter().write("<p>Requesting Portal URL: " + getPortalURL(request) + "</p>");
			response.getWriter().flush();
			return false;
		}

		return true;
	}

	protected void refreshConfiguration() {
		if (configAutoRefresh) {
			getConfiguration().refresh();
		}
	}

	protected void refreshPageDefinition() throws ServletException {
		if (pageDefAutoRefresh) {
			try {
				getPortalApplication().getService(PageDefinitionService.class).refresh();
			} catch (PortalException e) {
				throw new ServletException("Fail to refresh page definition!", e);
			}
		}
	}

	/**
	 * Inquire if the incoming request is a logout request.
	 * 
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	protected boolean isLogoutRequest(HttpServletRequest request) throws ServletException {
		if (logoutPageName == null) {
			logoutPageName = getConfiguration().getValue(LOGOUT_PAGE_NAME);
		}

		return logoutPageName.equals(getPortalURL(request).getPageID());
	}

	/**
	 * Return true if the incoming request is a logout request and user has been
	 * successfully logout all portal/portlet applications. Return false
	 * otherwise.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	protected boolean logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (isLogoutRequest(request)) {
			LOG.debug("Trying to logout.........");
			// TODO call portlet container and logout all portlet applications
			response.sendRedirect("/");
			LOG.debug("User logout finished.");
			return true;
		}

		return false;
	}
}
