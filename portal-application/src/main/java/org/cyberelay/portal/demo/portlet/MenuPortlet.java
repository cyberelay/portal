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

package org.cyberelay.portal.demo.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Aug 14, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 648 $
 * <li>Last Update Time: $Date: 2008-03-14 07:28:53 +0000 (Fri, 14 Mar 2008) $
 * </ul>
 * 
 */
public class MenuPortlet extends BasePortlet {

	public void processAction(ActionRequest request, ActionResponse response)
			throws PortletException, IOException {
		response.setRenderParameter(PUB_PARAM_CANDIDATE, request.getParameter(PUB_PARAM_CANDIDATE));
	}

	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		getRequestDispatcher("/demo/jsp/menu.jsp").include(request, response);
	}

	protected void doEdit(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		getRequestDispatcher("/demo/jsp/menuEdit.jsp").include(request, response);
	}

	@Override
	protected String getTitle(RenderRequest request) {
		return "List of Developers";
	}

	@Override
	protected void doHeaders(RenderRequest request, RenderResponse response) {
	}
}
