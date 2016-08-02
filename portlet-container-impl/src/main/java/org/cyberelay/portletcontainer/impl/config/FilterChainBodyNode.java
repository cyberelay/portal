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
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

import org.cyberelay.portal.util.Assert;

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
class FilterChainBodyNode implements FilterChain {
	private PortletFilter filter;
	private FilterChain next;

	public FilterChainBodyNode(PortletFilter filter, FilterChain next) {
		Assert.notNull(filter, "PortletFilter cannot be NULL!");
		Assert.notNull(next, "BodyFilterChainNode must have a next node!");

		this.filter = filter;
		this.next = next;
	}

	/**
	 * @see javax.portlet.filter.FilterChain#doFilter(javax.portlet.ActionRequest,
	 *      javax.portlet.ActionResponse)
	 */
	public void doFilter(ActionRequest request, ActionResponse response)
			throws IOException, PortletException {
		if (filter instanceof ActionFilter) {
			((ActionFilter) filter).doFilter(request, response, next);
		} else {
			next.doFilter(request, response);
		}
	}

	/**
	 * 
	 * @see javax.portlet.filter.FilterChain#doFilter(javax.portlet.EventRequest,
	 *      javax.portlet.EventResponse)
	 */
	public void doFilter(EventRequest request, EventResponse response)
			throws IOException, PortletException {
		if (filter instanceof EventFilter) {
			((EventFilter) filter).doFilter(request, response, next);
		} else {
			next.doFilter(request, response);
		}
	}

	/**
	 * 
	 * @see javax.portlet.filter.FilterChain#doFilter(javax.portlet.RenderRequest,
	 *      javax.portlet.RenderResponse)
	 */
	public void doFilter(RenderRequest request, RenderResponse response)
			throws IOException, PortletException {
		if (filter instanceof RenderFilter) {
			((RenderFilter) filter).doFilter(request, response, next);
		} else {
			next.doFilter(request, response);
		}
	}

	/**
	 * 
	 * @see javax.portlet.filter.FilterChain#doFilter(javax.portlet.ResourceRequest,
	 *      javax.portlet.ResourceResponse)
	 */
	public void doFilter(ResourceRequest request, ResourceResponse response)
			throws IOException, PortletException {
		if (filter instanceof ResourceFilter) {
			((ResourceFilter) filter).doFilter(request, response, next);
		} else {
			next.doFilter(request, response);
		}
	}

	@Override
	public String toString() {
		return filter.getClass().getName() + ", " + next;
	}
}
