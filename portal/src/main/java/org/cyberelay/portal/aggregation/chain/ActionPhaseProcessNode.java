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

import org.cyberelay.portal.util.PortalRequestUtil;
import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portletcontainer.PortletContainerException;
import org.cyberelay.portletcontainer.PortletWindow;

/**
 * @author Roger Tang
 * 
 */
public class ActionPhaseProcessNode extends GenericProcessNode {
	private static final Logger LOG = LoggerFactory.getLogger(ActionPhaseProcessNode.class);

	public void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (PortalRequestUtil.isPortletActionRequest(request)) {
			PortletWindow targetWindow = getTargetPortletWindow(request);
			invokePortletAction(request, response, targetWindow);
			if (PortalRequestUtil.isRedirected(request)) {
				/* ignore the rest node traversing for redirected action request */
				return;
			}
		}

		processNext(request, response);
	}

	private void invokePortletAction(HttpServletRequest request, HttpServletResponse response,
			PortletWindow targetWindow) throws ServletException, IOException {
		try {
			if(LOG.isDebugEnabled()) {
				LOG.debug("Invoking portlet action... target window = [" + targetWindow + "]");
			}
			
			getPortletContainer().processPortletAction(targetWindow, request, response);
		} catch (PortletContainerException e) {
			throw new ServletException(e);
		}
	}
}
