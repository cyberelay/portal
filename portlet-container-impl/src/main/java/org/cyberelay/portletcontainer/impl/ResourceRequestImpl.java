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
import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.cyberelay.portletcontainer.PortletInvocationRequest;

/**
 * @author Roger Tang
 * 
 */
public class ResourceRequestImpl extends PortletRequestImpl implements ResourceRequest {

	public ResourceRequestImpl(PortletInvocationRequest request, ResourceResponse response) {
		super(request, response);
	}

	/**
	 * 
	 * @see javax.portlet.ResourceRequest#getETag()
	 */
	public String getETag() {
		return getProperty(ETAG);
	}

	/**
	 * 
	 * @see javax.portlet.ResourceRequest#getResourceID()
	 */
	public String getResourceID() {
		return invocationRequest.getResourceID();
	}

	/**
	 * 
	 * @see javax.portlet.ClientDataRequest#getPortletInputStream()
	 */
	public InputStream getPortletInputStream() throws IOException {
		return getInputStream();
	}

	public String getCacheability() {
		return invocationRequest.getCacheability();
	}

	public Map<String, String[]> getPrivateRenderParameterMap() {
		return getNavigationState().getPrivateRenderParameterMap();
	}

	@Override
	protected String getLifecyclePhase() {
		return RESOURCE_PHASE;
	}
}
