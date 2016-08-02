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

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Roger Tang
 * 
 */
public class PortletRequestDispatcherImpl implements PortletRequestDispatcher {
	private RequestDispatcher dispatcher;

	public PortletRequestDispatcherImpl(RequestDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequestDispatcher#forward(javax.portlet.PortletRequest,
	 *      javax.portlet.PortletResponse)
	 */
	public void forward(PortletRequest req, PortletResponse resp)
			throws PortletException, IOException {
		try {
			dispatcher.forward(toHttpRequest(req, false), toHttpResponse(req, resp, false));
		} catch (ServletException e) {
			throw new PortletException(e);
		}
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequestDispatcher#include(javax.portlet.RenderRequest,
	 *      javax.portlet.RenderResponse)
	 */
	public void include(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		internalInclude(request, response);
	}

	/**
	 * 
	 * @see javax.portlet.PortletRequestDispatcher#include(javax.portlet.PortletRequest,
	 *      javax.portlet.PortletResponse)
	 */
	public void include(PortletRequest request, PortletResponse response)
			throws PortletException, IOException {
		internalInclude(request, response);
	}

	private void internalInclude(PortletRequest req, PortletResponse resp)
			throws PortletException, IOException {
		try {
			dispatcher.include(toHttpRequest(req, true), toHttpResponse(req, resp, true));
		} catch (ServletException e) {
			throw new PortletException(e);
		}
	}

	private HttpServletRequest toHttpRequest(PortletRequest request, boolean include) {
		return new PortletRequest2ServletRequestAdapter(request, include);
	}

	private HttpServletResponse toHttpResponse(PortletRequest req, PortletResponse resp,
			boolean include) {
		return new PortletResponse2ServletResponseAdapter(req, resp, include);
	}
}
