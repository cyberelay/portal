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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jetspeed.portlet.Portlet;
import org.apache.jetspeed.portlet.PortletRequest;
import org.apache.jetspeed.portlet.PortletResponse;
import org.apache.jetspeed.portlet.UnavailableException;
import org.cyberelay.portletcontainer.PortletContainer;
import org.cyberelay.portletcontainer.legacy.Constants;
import org.cyberelay.portletcontainer.legacy.PortletApplication;
import org.cyberelay.portletcontainer.legacy.PortletMethod;
import org.cyberelay.portletcontainer.legacy.PortletWindowEx;
import org.cyberelay.portletcontainer.legacy.impl.PortletRequestImpl;
import org.cyberelay.portletcontainer.legacy.impl.PortletResponseImpl;
import org.cyberelay.portletcontainer.legacy.service.PortletApplicationService;

/**
 * @author Roger Tang
 * 
 */
public abstract class PortletInvoker {
	//TODO use ConcurrentHashMap instead of HashMap
	private static final Map<PortletMethod, PortletInvoker> INVOKERS = new HashMap<PortletMethod, PortletInvoker>();

	static {
		INVOKERS.put(PortletMethod.ACTION_PERFORMED, new ActionListenerInvoker());
		INVOKERS.put(PortletMethod.SERVICE, new ServiceInvoker());
		INVOKERS.put(PortletMethod.BEGIN_EVENT_PHASE, new EventPhaseListenerInvoker());
		INVOKERS.put(PortletMethod.END_EVENT_PHASE, new EventPhaseListenerInvoker());
		INVOKERS.put(PortletMethod.BEGIN_PAGE, new PortletPageListenerInvoker());
		INVOKERS.put(PortletMethod.END_PAGE, new PortletPageListenerInvoker());
		INVOKERS.put(PortletMethod.MESSAGE_RECEIVED, new MessageListenerInvoker());
		INVOKERS.put(PortletMethod.LOGIN, new PortletSessionListenerInvoker());
		INVOKERS.put(PortletMethod.LOGOUT, new PortletSessionListenerInvoker());
		INVOKERS.put(PortletMethod.DO_TITLE, new PortletTitleListenerInvoker());
	}

	protected PortletInvoker() {

	}

	public static void invoke(PortletMethod method, PortletWindowEx window,
			HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		PortletInvoker invoker = INVOKERS.get(method);
		if (invoker != null) {
			invoker.invokeMethod(method, window, request, response);
		}
	}

	protected Portlet getPortletInstance(PortletWindowEx window, HttpServletRequest request)
			throws UnavailableException {
		PortletApplicationService portletApplicaitonService = getPortletContainer(request)
				.getService(PortletApplicationService.class);
		PortletApplication application = portletApplicaitonService.getPortletApplication(window);
		return application.getPortletInstance(window.getPortletName());
	}

	protected PortletContainer getPortletContainer(HttpServletRequest request) {
		return (PortletContainer) request.getAttribute(PortletContainer.PORTLET_CONTAINER);
	}

	protected PortletRequest getPortletRequest(HttpServletRequest req, HttpServletResponse res,
			PortletWindowEx window, boolean create) {
		String key = Constants.PORTLET_REQUEST + "." + window.getUniqueID();
		PortletRequest result = (PortletRequest) req.getAttribute(key);
		if (result != null) {
			return result;
		} else if (create) {
			result = new PortletRequestImpl(req, res, window);
			req.setAttribute(key, result);
		}
		return result;
	}

	protected PortletResponse getPortletResponse(HttpServletRequest req, HttpServletResponse res,
			PortletWindowEx window, boolean create) {
		String key = Constants.PORTLET_RESPONSE + "." + window.getUniqueID();
		PortletResponse result = (PortletResponse) req.getAttribute(key);
		if (result != null) {
			return result;
		} else if (create) {
			result = new PortletResponseImpl(req, res, window);
			req.setAttribute(key, result);
		}
		return result;
	}

	protected abstract void invokeMethod(PortletMethod method, PortletWindowEx window,
			HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException;
}
