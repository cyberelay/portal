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

package org.cyberelay.portletcontainer.tag;

import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * The class is copied from org.apache.pluto.tags.* and has been changed to fit
 * in cyberelay portlet container entity model.
 * 
 */
public class DefineObjectsTag extends PortletTagSupport {
	private static final long serialVersionUID = 5966007273510311721L;

	private void setAttribute(Object attribute, String attributeName) {
		if (pageContext.getAttribute(attributeName) == null) {
			pageContext.setAttribute(attributeName, attribute, PageContext.PAGE_SCOPE);
		}
	}

	/**
	 * Using portlet lifecycle phase to determine portlet request/reponse name.
	 * 
	 * @param request
	 * @param requestName
	 * @return
	 */
	private String getRequestResponseName(PortletRequest request, boolean requestName) {
		String phase = (String) request.getAttribute(PortletRequest.LIFECYCLE_PHASE);
		if (PortletRequest.ACTION_PHASE.equals(phase)) {
			return requestName ? "actionRequest" : "actionResponse";
		} else if (PortletRequest.RESOURCE_PHASE.equals(phase)) {
			return requestName ? "resourceRequest" : "resourceResponse";
		} else if (PortletRequest.RENDER_PHASE.equals(phase)) {
			return requestName ? "renderRequest" : "renderResponse";
		} else if (PortletRequest.EVENT_PHASE.equals(phase)) {
			return requestName ? "eventRequest" : "eventResponse";
		}
		return null;
	}

	/**
	 * Processes the <CODE>defineObjects</CODE> tag.
	 * 
	 * @return <CODE>SKIP_BODY</CODE>
	 */
	public int doStartTag() throws JspException {
		PortletRequest request = getPortletRequest();
		PortletResponse response = getPortletResponse();
		PortletConfig portletConfig = getPortletConfig();

		PortletSession portletSession = request.getPortletSession(false);
		Map<String, Object> portletSessionScope = null;
		if (portletSession != null) {
			portletSessionScope = (Map<String, Object>) portletSession.getAttributeMap();
		}

		PortletPreferences portletPreferences = request.getPreferences();
		Map<String, String[]> portletPreferencesValues = null;
		if (portletPreferences != null) {
			portletPreferencesValues = portletPreferences.getMap();
		}

		setAttribute(request, getRequestResponseName(request, true));
		setAttribute(response, getRequestResponseName(request, false));
		setAttribute(portletConfig, "portletConfig");
		setAttribute(portletSession, "portletSession");
		setAttribute(portletSessionScope, "portletSessionScope");
		setAttribute(portletPreferences, "portletPreferences");
		setAttribute(portletPreferencesValues, "portletPreferencesValues");

		return SKIP_BODY;
	}

	public static class TEI extends TagExtraInfo {

		public VariableInfo[] getVariableInfo(TagData tagData) {
			VariableInfo[] info =
					new VariableInfo[] {
							new VariableInfo(
									"renderRequest",
									"javax.portlet.RenderRequest",
									true,
									VariableInfo.AT_BEGIN),
							new VariableInfo(
									"renderResponse",
									"javax.portlet.RenderResponse",
									true,
									VariableInfo.AT_BEGIN),
							new VariableInfo(
									"resourceRequest",
									"javax.portlet.ResourceRequest",
									true,
									VariableInfo.AT_BEGIN),
							new VariableInfo(
									"resourceResponse",
									"javax.portlet.ResourceResponse",
									true,
									VariableInfo.AT_BEGIN),
							new VariableInfo(
									"actionRequest",
									"javax.portlet.ActionRequest",
									true,
									VariableInfo.AT_BEGIN),
							new VariableInfo(
									"actionResponse",
									"javax.portlet.ActionResponse",
									true,
									VariableInfo.AT_BEGIN),
							new VariableInfo(
									"eventRequest",
									"javax.portlet.EventRequest",
									true,
									VariableInfo.AT_BEGIN),
							new VariableInfo(
									"eventResponse",
									"javax.portlet.EventResponse",
									true,
									VariableInfo.AT_BEGIN),
							new VariableInfo(
									"portletConfig",
									"javax.portlet.PortletConfig",
									true,
									VariableInfo.AT_BEGIN),
							new VariableInfo(
									"portletSession",
									"javax.portlet.PortletSession",
									true,
									VariableInfo.AT_BEGIN),
							new VariableInfo(
									"portletSessionScope",
									"java.util.Map",
									true,
									VariableInfo.AT_BEGIN),
							new VariableInfo(
									"portletPreferences",
									"javax.portlet.PortletPreferences",
									true,
									VariableInfo.AT_BEGIN),
							new VariableInfo(
									"portletPreferencesValues",
									"java.util.Map",
									true,
									VariableInfo.AT_BEGIN),

					};

			return info;
		}
	}
}