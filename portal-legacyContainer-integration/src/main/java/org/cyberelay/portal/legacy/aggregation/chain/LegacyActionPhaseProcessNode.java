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
package org.cyberelay.portal.legacy.aggregation.chain;

import java.io.IOException;

import javax.portlet.PortalContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyberelay.portal.PageDefinition;
import org.cyberelay.portal.PortalURL;
import org.cyberelay.portal.PortletWindowDefinition;
import org.cyberelay.portal.aggregation.chain.GenericProcessNode;
import org.cyberelay.portal.service.InvalidURLException;
import org.cyberelay.portal.util.ThreadAttributesManager;
import org.cyberelay.portletcontainer.legacy.PortletMessageEx;
import org.cyberelay.portletcontainer.legacy.PortletMessageExQueue;
import org.cyberelay.portletcontainer.legacy.PortletMethod;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
class LegacyActionPhaseProcessNode extends GenericProcessNode {

	public void process(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		PortalURL url = getPortalURL(req);
		PageDefinition page = getRequestingPage(req);

		PortletWindowDefinition actionPortlet = getActionPortletWindow(url, page);

		if (actionPortlet != null) {
			invokePortlet(req, res, page, PortletMethod.BEGIN_EVENT_PHASE);
			invokePortlet(req, res, actionPortlet, PortletMethod.ACTION_PERFORMED);
			handleMessages(req, res, page);
			invokePortlet(req, res, page, PortletMethod.END_EVENT_PHASE);
		}

		freezeWindows(req, res, page);

		processNext(req, res);
	}

	private void invokePortlet(HttpServletRequest req, HttpServletResponse res,
			PortletWindowDefinition actionPortlet, PortletMethod method) {
		// TODO Auto-generated method stub

	}

	private void invokePortlet(HttpServletRequest req, HttpServletResponse res,
			PageDefinition page, PortletMethod method) {
		// TODO Auto-generated method stub

	}

	private PortletWindowDefinition getActionPortletWindow(PortalURL url, PageDefinition page)
			throws InvalidURLException {
		if (PortalURL.PORTLET_ACTION.equals(url.getType())) {
			page.getPortletWindow(url.getPortletWindowID());
		}

		return null;
	}

	protected void handleMessages(HttpServletRequest req, HttpServletResponse res,
			PageDefinition page) throws ServletException, IOException {
		/*
		PortletMessageExQueue queue =
				(PortletMessageExQueue) ThreadAttributesManager.getAttribute(PORTLET_MESSAGE_QUEUE);
		System.out.println("queue = " + queue);
		while (queue == null || queue.isEmpty()) {
			System.out.println("No message in queue!");
			return;
		}
		PortletWindowDefinition[] portlets = page.getPortletWindows();
		do {
			PortletMessageEx message = queue.pop();
			for (int i = 0; portlets != null && i < portlets.length; i++) {
				if (message.isTargetPortlet(portlets[i])) {
					ThreadAttributesManager.setAttribute(PORTLET_MESSAGE, message);
					portalContext.invokePortlet(
							req, res, portlets[i], LegacyPortlet.MESSAGE_RECEIVED);
					ThreadAttributesManager.removeAttribute(PORTLET_MESSAGE);
				}
			}
		} while (!queue.isEmpty());
		*/
	}

	protected void freezeWindows(HttpServletRequest req, HttpServletResponse res,
			PageDefinition page) throws ServletException, IOException {
		/*
		PortletWindowDefinition[] portlets = page.getPortletWindows();
		for (int i = 0; portlets != null && i < portlets.length; i++) {
			portalContext.getPortletWindowRunData(req, portlets[i]).freezeWindowMode();
			portalContext.getPortletWindowRunData(req, portlets[i]).freezeWindowState();
		}
		*/
	}
}
