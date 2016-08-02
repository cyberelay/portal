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

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * @author Roger Tang
 * 
 */
public class ConstantsTag extends PortalTagSupport {
	private static final long serialVersionUID = 97971493800407397L;

	private static final String PORTAL_ROOT_DIR = "portalBaseDir";

	private static final String PORTAL_HTML_DIR = "portalHtmlDir";

	private static final String PORTAL_IMAGE_DIR = "portalImageDir";

	private static final String PORTAL_CSS_DIR = "portalCssDir";

	public static class TEI extends TagExtraInfo {
		public VariableInfo[] getVariableInfo(TagData aTagData) {
			return new VariableInfo[] {
					new VariableInfo(
							PORTAL_ROOT_DIR,
							"java.lang.String",
							true,
							VariableInfo.AT_BEGIN),
					new VariableInfo(
							PORTAL_HTML_DIR,
							"java.lang.String",
							true,
							VariableInfo.AT_BEGIN),
					new VariableInfo(
							PORTAL_CSS_DIR,
							"java.lang.String",
							true,
							VariableInfo.AT_BEGIN),
					new VariableInfo(
							PORTAL_IMAGE_DIR,
							"java.lang.String",
							true,
							VariableInfo.AT_BEGIN) };
		}
	}

	public int doStartTag() {
		try {
			String baseURL = getPortalApplication().getPortalContext().getContextPath();
			pageContext.setAttribute(PORTAL_ROOT_DIR, baseURL);
			pageContext.setAttribute(PORTAL_HTML_DIR, baseURL + "/WEB/html");
			pageContext.setAttribute(PORTAL_IMAGE_DIR, baseURL + "/WEB/images");
			pageContext.setAttribute(PORTAL_CSS_DIR, baseURL + "/WEB/css");
		} catch (Throwable t) {

		}
		return SKIP_BODY;
	}
}
