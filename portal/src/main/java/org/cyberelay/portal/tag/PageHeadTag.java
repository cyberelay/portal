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

import javax.servlet.jsp.JspException;

/**
 * @author Roger Tang
 * 
 */
public class PageHeadTag extends PortalTagSupport {

	private static final long serialVersionUID = 7814912801179274998L;

	public int doStartTag() throws JspException {
		/**
		try {
			PageDefinition page = getRequestingPage();
			PortletWindowDefinition[] windows = page.getPortletWindows();
			for (PortletWindowDefinition window : windows) {
				invokePortlet(getHttpRequest(), getHttpResponse(),
						PortletApplicationRequest.ID_RENDER_HEADER, window);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
						*/

		return SKIP_BODY;
	}

}
