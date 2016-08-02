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
package org.cyberelay.portletcontainer.impl;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;

import org.cyberelay.portletcontainer.PortletInvocationRequest;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 625 $
 * <li>Last Update Time: $Date: 2008-03-07 04:36:51 +0000 (Fri, 07 Mar 2008) $
 * </ul>
 * 
 */
public class EventRequestImpl extends PortletRequestImpl implements EventRequest {

	public EventRequestImpl(PortletInvocationRequest request, EventResponse response) {
		super(request, response);
	}

	/**
	 * 
	 * @see javax.portlet.EventRequest#getEvent()
	 */
	public Event getEvent() {
		return invocationRequest.getEvent();
	}

	@Override
	public String getParameter(String name) {
		String value = getNavigationState().getPrivateRenderParameter(name);
		return value == null ? getNavigationState().getPublicRenderParameter(name) : value;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> result = new HashMap<String, String[]>();
		result.putAll(getNavigationState().getPublicRenderParameterMap());
		result.putAll(getNavigationState().getPrivateRenderParameterMap());
		return Collections.unmodifiableMap(result);
	}

	@Override
	public Enumeration<String> getParameterNames() {
		Set<String> result = new HashSet<String>();
		result.addAll(getNavigationState().getPublicRenderParameterMap().keySet());
		result.addAll(getNavigationState().getPrivateRenderParameterMap().keySet());
		return Collections.enumeration(result);
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] result = getNavigationState().getPrivateRenderParameters(name);
		return result == null ? getNavigationState().getPublicRenderParameters(name) : result;
	}

	public Map<String, String[]> getPrivateParameterMap() {
		return getNavigationState().getPrivateRenderParameterMap();
	}

	public Map<String, String[]> getPublicParameterMap() {
		return getNavigationState().getPublicRenderParameterMap();
	}

	@Override
	protected String getLifecyclePhase() {
		return EVENT_PHASE;
	}
}
