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

import javax.portlet.MimeResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.jsp.tagext.TagSupport;

import org.cyberelay.portletcontainer.PortletContainerConstants;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Aug 10, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 649 $
 * <li>Last Update Time: $Date: 2008-03-14 10:52:03 +0000 (Fri, 14 Mar 2008) $
 * </ul>
 * 
 */
public class PortletTagSupport extends TagSupport implements PortletContainerConstants {
	private static final long serialVersionUID = -4646449409754702372L;

	protected PortletRequest getPortletRequest() {
		return (PortletRequest) pageContext.getRequest().getAttribute(PORTLET_REQUEST);
	}

	protected PortletResponse getPortletResponse() {
		return (PortletResponse) pageContext.getRequest().getAttribute(PORTLET_RESPONSE);
	}

	protected PortletConfig getPortletConfig() {
		return (PortletConfig) pageContext.getRequest().getAttribute(PORTLET_CONFIG);
	}
	
	protected MimeResponse getMimeResponse() {
		return (MimeResponse) getPortletResponse();
	}
}
