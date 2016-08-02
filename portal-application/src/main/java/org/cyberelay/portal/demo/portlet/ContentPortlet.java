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

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.cyberelay.portal.demo.model.Candidate;
import org.cyberelay.portal.demo.model.CandidateStore;

/**
 * @author Roger Tang
 * 
 */
public class ContentPortlet extends BasePortlet {

	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		String candidate = request.getParameter(PUB_PARAM_CANDIDATE);
		Candidate selected = CandidateStore.getInstance().getCandidate(candidate);
		request.setAttribute("candidate", selected);

		getRequestDispatcher("/demo/jsp/content.jsp").include(request, response);
	}

	protected void doEdit(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		getRequestDispatcher("/demo/jsp/contentEdit.jsp").include(request, response);
	}

	protected String getTitle(RenderRequest request) {
		return "Bio";
	}
}
