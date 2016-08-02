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

import org.cyberelay.portal.PortalURL;
import org.cyberelay.portal.aggregation.RequestProcessNode;
import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portletcontainer.PortletContainerException;
import org.cyberelay.portletcontainer.PortletWindow;

/**
 * @author Roger Tang
 * 
 */
public class ResourcePhaseProcessNode extends GenericProcessNode implements RequestProcessNode {
	private static final Logger LOG = LoggerFactory.getLogger(ResourcePhaseProcessNode.class);

	/**
	 * @see org.cyberelay.portal.aggregation.RequestProcessNode#process(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (isResourceRequest(request)) {
			LOG.debug("Processing portlet resource request........");
			processResourceRequest(request, response);
			/* ignore the rest node traversing for portlet resource request. */
			LOG.debug("Processing portlet resource request........finished.");
			return;
		}

		processNext(request, response);
	}

	protected void processResourceRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PortletWindow targetWindow = getTargetPortletWindow(request);
		try {
			getPortletContainer().servePortletResource(targetWindow, request, response);
		} catch (PortletContainerException e) {
			throw new ServletException(e);
		}
	}

	protected boolean isResourceRequest(HttpServletRequest request) throws ServletException {
		PortalURL url = getPortalURL(request);
		return PortalURL.PORTLET_RESOURCE.equals(url.getType());
	}

}
