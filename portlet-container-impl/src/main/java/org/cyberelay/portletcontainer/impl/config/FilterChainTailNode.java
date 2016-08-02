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

package org.cyberelay.portletcontainer.impl.config;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventPortlet;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceServingPortlet;
import javax.portlet.filter.FilterChain;

import org.cyberelay.portletcontainer.PortletContainerConstants;
import org.cyberelay.portletcontainer.PortletInvocationRequest;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Feb 13, 2008
 * <li>Last Editor: $Author:losingant $
 * <li>Current Revision: $Revision:587 $
 * <li>Last Update Time: $Date:2008-02-28 15:47:46 +0800 (Thu, 28 Feb 2008) $
 * </ul>
 * 
 */
class FilterChainTailNode implements FilterChain, PortletContainerConstants {

	/**
	 * @see javax.portlet.filter.FilterChain#doFilter(javax.portlet.ActionRequest,
	 *      javax.portlet.ActionResponse)
	 */
	public void doFilter(ActionRequest request, ActionResponse response)
			throws IOException, PortletException {
		getPortletInstance(request).processAction(request, response);
	}

	/**
	 * @see javax.portlet.filter.FilterChain#doFilter(javax.portlet.EventRequest,
	 *      javax.portlet.EventResponse)
	 */
	public void doFilter(EventRequest request, EventResponse response)
			throws IOException, PortletException {
		Portlet portlet = getPortletInstance(request);
		if(portlet instanceof EventPortlet){
			((EventPortlet)portlet).processEvent(request, response);
		}
	}

	/**
	 * @see javax.portlet.filter.FilterChain#doFilter(javax.portlet.RenderRequest,
	 *      javax.portlet.RenderResponse)
	 */
	public void doFilter(RenderRequest request, RenderResponse response)
			throws IOException, PortletException {
		getPortletInstance(request).render(request, response);
	}

	/**
	 * @see javax.portlet.filter.FilterChain#doFilter(javax.portlet.ResourceRequest,
	 *      javax.portlet.ResourceResponse)
	 */
	public void doFilter(ResourceRequest request, ResourceResponse response)
			throws IOException, PortletException {
		Portlet portlet = getPortletInstance(request);
		if(portlet instanceof ResourceServingPortlet){
			((ResourceServingPortlet)portlet).serveResource(request, response);
		}
	}

	private Portlet getPortletInstance(PortletRequest request) {
		return getInvocationRequest(request).getPortletInstance();
	}

	private PortletInvocationRequest getInvocationRequest(PortletRequest request) {
		return (PortletInvocationRequest) request.getAttribute(PORTLET_INVOCATION_REQUEST);
	}

	@Override
	public String toString() {
		return getClass().getName();
	}
}
