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

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletURL;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * @author Roger Tang
 * 
 */
public class PortletHelpTag extends PortletControlTag {
	private static final long serialVersionUID = -8658411962019924754L;
	
	private static final String PORTLET_HELP_URL = "portletHelpURL";

	public static class TEI extends TagExtraInfo {
		public VariableInfo[] getVariableInfo(TagData aTagData) {
			return (new VariableInfo[] { new VariableInfo(PORTLET_HELP_URL,
					PortletURL.class.getName(), true, VariableInfo.NESTED) });
		}
	}

	public int doStartTag() throws JspException {
		if (!isPortletModeSupported(PortletMode.HELP)) {
			return SKIP_BODY;
		}
		
		if (PortletMode.HELP.equals(getPortletMode())) {
			return SKIP_BODY;
		}

		PortletURL url = createRenderURL();
		try {
			url.setPortletMode(PortletMode.HELP);
		} catch (PortletModeException e) {
			throw new JspException(e);
		}
		
		pageContext.setAttribute(PORTLET_HELP_URL, url);

		return EVAL_BODY_INCLUDE;
	}

}
