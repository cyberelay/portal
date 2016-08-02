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

package org.cyberelay.portal.ui.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyberelay.portal.util.Assert;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 663 $
 * <li>Last Update Time: $Date: 2008-03-18 06:22:19 +0000 (Tue, 18 Mar 2008) $
 * </ul>
 * 
 */
public class Template {
	private String uniqueName;

	private Map<String, String> paths;

	public Template(String name) {
		Assert.notNull(name);
		this.uniqueName = name;
		this.paths = new HashMap<String, String>();
	}

	public String getPath(String markup) {
		return paths.get(markup);
	}

	public void setPath(String markup, String path) {
		paths.put(markup, path);
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void render(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(getTemplatePath(request)).include(request, response);
	}

	private String getTemplatePath(HttpServletRequest request) {
		// TODO resolve the template path here.
		return paths.get("html");
	}
}
