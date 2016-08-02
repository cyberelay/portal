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

package org.cyberelay.portal.aggregation.chain;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyberelay.portal.PageDefinition;
import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;

/**
 * @author Roger Tang
 * 
 */
public class RenderPhaseProcessNode extends GenericProcessNode {
	private static final Logger LOG = LoggerFactory.getLogger(RenderPhaseProcessNode.class);

	public void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PageDefinition page = getRequestingPage(request);

		LOG.debug("start to render page.............");

		setContentType(response);
		setNoCache(response);
		page.render(request, response);

		LOG.debug("start to render page.............done");

		processNext(request, response);
	}

	protected void setNoCache(HttpServletResponse res) {
		res.setHeader("pragma", "no-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setHeader("Expires", "0");
	}

	protected void setContentType(HttpServletResponse res) {
		res.setContentType("text/html; charset=UTF-8");
	}
}
