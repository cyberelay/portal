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

import org.cyberelay.portal.service.AuthorizationService;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 562 $
 * <li>Last Update Time: $Date: 2008-02-25 09:38:55 +0000 (Mon, 25 Feb 2008) $
 * </ul>
 * 
 */
public class AuthorizationNode extends GenericProcessNode {

	public void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (getAuthorizationService().doAuthorization(request, response)) {
			processNext(request, response);
		}
	}

	private AuthorizationService getAuthorizationService() {
		return getPortalApplication().getService(AuthorizationService.class);
	}
}
