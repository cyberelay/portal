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

package org.cyberelay.portletcontainer.handler;

import java.util.HashMap;
import java.util.Map;

import org.cyberelay.portletcontainer.PortletInvocationRequest;

/**
 * @author Roger Tang
 * 
 */
public class RequestHandlerRegistry {
	private static RequestHandlerRegistry INSTANCE;

	private Map<String, PortletInvocationHandler> registry;

	private RequestHandlerRegistry() {
		this.registry = new HashMap<String, PortletInvocationHandler>();

		/* built-in portlet application request handlers. */
		register(PortletInvocationRequest.ID_RENDER_MARKUP, new RenderMarkupHandler());
		register(PortletInvocationRequest.ID_RENDER_HEADER, new RenderHeaderHandler());
		register(PortletInvocationRequest.ID_PROCESS_RESOURCE, new ProcessResourceHandler());
		register(PortletInvocationRequest.ID_PROCESS_ACTION, new ProcessActionHandler());
		register(PortletInvocationRequest.ID_PROCESS_EVENT, new ProcessEventHandler());
		register(PortletInvocationRequest.ID_LOGOUT, new LogoutHandler());
	}

	public static RequestHandlerRegistry getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RequestHandlerRegistry();
		}
		return INSTANCE;
	}

	protected void register(String requestID, PortletInvocationHandler handler) {
		registry.put(requestID, handler);
	}

	public PortletInvocationHandler getHandler(PortletInvocationRequest pApplicationRequest) {
		return registry.get(pApplicationRequest.getInvocationID());
	}

	protected void registerCustomizedHandlers() {
		// TODO
	}

}
