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

package org.cyberelay.portletcontainer.legacy.impl.invoker;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jetspeed.portlet.EventPhaseListener;
import org.apache.jetspeed.portlet.Portlet;
import org.apache.jetspeed.portlet.PortletRequest;
import org.apache.jetspeed.portlet.PortletResponse;
import org.cyberelay.portletcontainer.legacy.PortletMethod;
import org.cyberelay.portletcontainer.legacy.PortletWindowEx;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jan 19, 2008
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
class EventPhaseListenerInvoker extends PortletInvoker {

	/**
	 * @see org.cyberelay.portletcontainer.legacy.impl.invoker.PortletInvoker#invokeMethod(org.cyberelay.portletcontainer.legacy.PortletMethod,
	 *      org.cyberelay.portletcontainer.legacy.PortletWindowEx,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void invokeMethod(PortletMethod method, PortletWindowEx window,
			HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		PortletRequest portletRequest = getPortletRequest(request, response, window, true);
		PortletResponse portletResponse = getPortletResponse(request, response, window, true);

		Portlet portlet = getPortletInstance(window, request);

		if (portlet instanceof EventPhaseListener) {
			if (method == PortletMethod.BEGIN_EVENT_PHASE) {
				((EventPhaseListener) portlet).beginEventPhase(portletRequest);
			} else if (method == PortletMethod.END_EVENT_PHASE) {
				((EventPhaseListener) portlet).endEventPhase(portletRequest);
			}
		}
	}

}
