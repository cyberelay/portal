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
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.cyberelay.portletcontainer.PortletContainerConstants;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Aug 6, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 603 $
 * <li>Last Update Time: $Date: 2008-03-01 12:34:16 +0000 (Sat, 01 Mar 2008) $
 * </ul>
 * 
 */
class PortletResponse2ServletResponseAdapter extends HttpServletResponseWrapper implements
		PortletContainerConstants {
	private PortletResponse wrapped;
	private boolean include;
	private String lifecyclePhase;

	public PortletResponse2ServletResponseAdapter(PortletRequest req, PortletResponse resp,
			boolean include) {
		super(getHttpResponse(req, resp));

		this.wrapped = resp;
		this.include = include;
		this.lifecyclePhase = (String) req.getAttribute(PortletRequest.LIFECYCLE_PHASE);
	}

	private static HttpServletResponse getHttpResponse(PortletRequest req, PortletResponse resp) {
		if(resp instanceof HttpServletResponse) {
			return (HttpServletResponse) resp;
		}
		return (HttpServletResponse) req.getAttribute(HTTP_RESPONSE);
	}

	/**
	 * This method does not perform any operation according to JSR286
	 */
	public void addCookie(Cookie cookie) {
	}

	/**
	 * This method does not perform any operation according to JSR286
	 */
	public void addDateHeader(String name, long value) {
	}

	/**
	 * This method does not perform any operation according to JSR286
	 */
	public void addHeader(String name, String value) {
	}

	/**
	 * This method does not perform any operation according to JSR286
	 */
	public void addIntHeader(String name, int value) {
	}

	/**
	 * This method always return false according to JSR286
	 */
	public boolean containsHeader(String name) {
		return false;
	}

	/**
	 * This method always return null according to JSR286
	 */
	public String encodeRedirectURL(String path) {
		return null;
	}

	/**
	 * This method always return null according to JSR286
	 */
	public String encodeRedirectUrl(String path) {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#encodeURL(java.lang.String)
	 */
	public String encodeURL(String path) {
		return wrapped.encodeURL(path);
	}

	/**
	 * @see javax.servlet.http.HttpServletResponse#encodeUrl(java.lang.String)
	 */
	public String encodeUrl(String path) {
		return encodeURL(path);
	}

	/**
	 * This method does not perform any operation according to JSR286
	 */
	public void sendError(int arg0) throws IOException {
	}

	/**
	 * This method does not perform any operation according to JSR286
	 */
	public void sendError(int arg0, String arg1) throws IOException {
	}

	/**
	 * This method does not perform any operation according to JSR286
	 */
	public void sendRedirect(String arg0) throws IOException {
	}

	/**
	 * This method does not perform any operation according to JSR286
	 */
	public void setDateHeader(String arg0, long arg1) {
	}

	/**
	 * This method does not perform any operation according to JSR286
	 */
	public void setHeader(String arg0, String arg1) {
	}

	/**
	 * This method does not perform any operation according to JSR286
	 */
	public void setIntHeader(String arg0, int arg1) {
	}

	/**
	 * This method does not perform any operation according to JSR286
	 */
	public void setStatus(int arg0) {
	}

	/**
	 * This method does not perform any operation according to JSR286
	 */
	public void setStatus(int arg0, String arg1) {
	}

	/**
	 * @see javax.servlet.ServletResponse#getBufferSize()
	 */
	public int getBufferSize() {
		return 0;
	}

	/**
	 * 
	 * @see javax.servlet.ServletResponse#isCommitted()
	 */
	public boolean isCommitted() {
		// PLT.19.3.3
		return true;
	}

	/**
	 * 
	 * @see javax.servlet.ServletResponse#reset()
	 */
	public void reset() {
	}


}
