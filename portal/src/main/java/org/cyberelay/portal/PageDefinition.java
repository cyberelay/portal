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

package org.cyberelay.portal;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 620 $
 * <li>Last Update Time: $Date: 2008-03-04 09:39:32 +0000 (Tue, 04 Mar 2008) $
 * </ul>
 * 
 */
public interface PageDefinition {

	String getUniqueID();

	PortletWindowDefinition[] getPortletWindows();

	PortletWindowDefinition getPortletWindow(String windowUniqueID);

	/**
	 * Call page aggregation framework to render page.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void render(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;
}
