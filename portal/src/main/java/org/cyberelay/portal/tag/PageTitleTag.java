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

import java.util.Locale;

import javax.servlet.jsp.JspException;

/**
 * @author Roger Tang
 * 
 */
public class PageTitleTag extends PortalTagSupport {

	private static final long serialVersionUID = 6896292801714644752L;

	public PageTitleTag() {
	}

	public int doStartTag() throws JspException {
		int result = SKIP_BODY;
		try {
			Locale locale = pageContext.getRequest().getLocale();
//			pageContext.getOut().print(getRequestingPage().getTitle(locale));
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return result;
	}
}