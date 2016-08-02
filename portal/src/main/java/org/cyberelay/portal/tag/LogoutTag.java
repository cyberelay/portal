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

import javax.portlet.PortletURL;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

import org.cyberelay.portal.PortalURL;
import org.cyberelay.portal.service.PortalURLService;

/**
 * @author Roger Tang
 * 
 */
public class LogoutTag extends PortalTagSupport {

	private static final long serialVersionUID = -1731987239597718387L;

	private static final String LOGOUT_PAGE_NAME = "page.logout.id";

	private static final String LOGOUT_URL = "logoutURL";

	public static class TEI extends TagExtraInfo {
		public VariableInfo[] getVariableInfo(TagData aTagData) {
			return (new VariableInfo[] { new VariableInfo(
					LOGOUT_URL,
					PortletURL.class.getName(),
					true,
					VariableInfo.NESTED) });
		}
	}

	public int doStartTag() throws JspException {
		String logoutPageName =
				getPortalApplication().getConfiguration().getValue(LOGOUT_PAGE_NAME);
		PortalURL url =
				getPortalApplication().getService(PortalURLService.class).createPageURL(
						logoutPageName);
		pageContext.setAttribute(LOGOUT_URL, url);

		return EVAL_BODY_INCLUDE;
	}

}
