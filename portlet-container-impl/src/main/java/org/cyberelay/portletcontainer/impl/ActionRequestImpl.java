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

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.cyberelay.portletcontainer.PortletInvocationRequest;

/**
 * @author Roger Tang
 * 
 */
public class ActionRequestImpl extends PortletRequestImpl implements ActionRequest {

	public ActionRequestImpl(PortletInvocationRequest req, ActionResponse response) {
		super(req, response);
	}

	public InputStream getPortletInputStream() throws IOException {
		return getInputStream();
	}

	@Override
	public String getParameter(String name) {
		/*
		 * public render parameters take precedence of client request
		 * parameters.
		 */
		String value = getNavigationState().getPublicRenderParameter(name);
		return value == null ? super.getParameter(name) : value;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> result = new HashMap<String, String[]>();
		result.putAll(super.getParameterMap());
		result.putAll(getNavigationState().getPublicRenderParameterMap());
		return Collections.unmodifiableMap(result);
	}

	@Override
	public Enumeration<String> getParameterNames() {
		Set<String> result = new HashSet<String>();
		result.addAll(super.getParameterMap().keySet());
		result.addAll(getNavigationState().getPublicRenderParameterMap().keySet());
		return Collections.enumeration(result);
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] value = getNavigationState().getPublicRenderParameters(name);
		return value == null ? super.getParameterValues(name): value;
	}

	/**
	 * Portlet private render parameters should not be accessible via
	 * <code>ActionRequest</code> interface. The return map is an immutable
	 * empty map.
	 */
	public Map<String, String[]> getPrivateParameterMap() {
		return Collections.emptyMap();
	}

	@Override
	protected String getLifecyclePhase() {
		return ACTION_PHASE;
	}
}
