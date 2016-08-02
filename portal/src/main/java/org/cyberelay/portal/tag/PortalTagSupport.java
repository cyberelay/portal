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

package org.cyberelay.portal.tag;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.jsp.tagext.TagSupport;

import org.cyberelay.portal.PageDefinition;
import org.cyberelay.portal.PortalApplication;
import org.cyberelay.portal.PortalConstants;
import org.cyberelay.portal.PortalContextEx;
import org.cyberelay.portal.PortletWindowDefinition;
import org.cyberelay.portal.ui.UIComponent;
import org.cyberelay.portal.util.PortalRequestUtil;
import org.cyberelay.portletcontainer.PortletContainer;

/**
 * @author Roger Tang
 * 
 */
public class PortalTagSupport extends TagSupport implements PortalConstants {

	private static final long serialVersionUID = -3675322021530417659L;

	protected PortalApplication getPortalApplication() {
		return PortalRequestUtil.getPortalApplication(getHttpRequest());
	}

	protected HttpServletRequest getHttpRequest() {
		return (HttpServletRequest) pageContext.getRequest();
	}

	protected HttpServletResponse getHttpResponse() {
		return (HttpServletResponse) pageContext.getResponse();
	}

	protected HttpServletResponse createIncludeResponse() {
		return new IncludeResponseWrapper(getHttpResponse(), pageContext.getOut());
	}

	protected PageDefinition getRequestingPage() {
		return PortalRequestUtil.getRequestPage(getHttpRequest());
	}

	protected PortletWindowDefinition getMaximizedPortletWindow() {
		return null;
	}

	protected PortalContextEx getPortalContext() {
		return getPortalApplication().getPortalContext();
	}

	protected PortletContainer getPortletContainer() {
		return PortalRequestUtil.getPortletContainer(getHttpRequest());
	}

	protected boolean isUserLoggedIn() {
		return PortalRequestUtil.isUserLoggedIn(getHttpRequest());
	}

	protected UIComponent getRenderingUIComponent() {
		return (UIComponent) getHttpRequest().getAttribute(PORTAL_RENDERING_COMPONENT);
	}

	protected Boolean booleanOf(String aValue) {
		Boolean result = null;
		if (aValue != null) {
			if (aValue == "true" || aValue == "yes") {
				result = Boolean.TRUE;
			} else if (aValue == "false" || aValue == "no") {
				result = Boolean.FALSE;
			} else if (aValue.equalsIgnoreCase("true") || aValue.equalsIgnoreCase("yes")) {
				result = Boolean.TRUE;
			} else if (aValue.equalsIgnoreCase("false") || aValue.equalsIgnoreCase("no")) {
				result = Boolean.FALSE;
			}
		}
		return result;
	}

	private static class IncludeResponseWrapper extends HttpServletResponseWrapper {
		private PrintWriter printWriter;

		public IncludeResponseWrapper(ServletResponse servletresponse, Writer writer) {
			super((HttpServletResponse) servletresponse);
			printWriter = new PrintWriter(writer);
		}

		public PrintWriter getWriter() throws IOException {
			return printWriter;
		}

		public ServletOutputStream getOutputStream() throws IOException {
			throw new IllegalStateException();
		}
	}

}
