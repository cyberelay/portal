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
package org.cyberelay.portal.service.impl;

import java.util.Collections;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyberelay.portal.User;
import org.cyberelay.portal.service.AuthenticationService;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jul 11, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 577 $
 * <li>Last Update Time: $Date: 2008-02-26 14:40:54 +0000 (Tue, 26 Feb 2008) $
 * </ul>
 * 
 */
public class AuthenticationServiceImpl extends AbstractPortalApplicationService implements
		AuthenticationService {
	private static final User ANONYMOUS = new UserImpl("anonymous");

	public boolean doAuthentication(HttpServletRequest request, HttpServletResponse response) {
		/* default implementation does nothing. */
		return true;
	}

	public boolean isUserLoggedIn(HttpServletRequest request) {
		return false;
	}

	public User getUser(HttpServletRequest request) {
		//TODO 
		return ANONYMOUS;
	}

	private static class UserImpl implements User {
		private String id;
		private String loginName;

		private UserImpl(String id) {
			this.id = id;
			this.loginName = id;
		}

		public Object getAttribute(String name) {
			return null;
		}

		public Iterator<String> getAttributeNames() {
			return Collections.EMPTY_LIST.iterator();
		}

		public String getID() {
			return id;
		}

		public String getLoginName() {
			return loginName;
		}

		public boolean hasRole(String role) {
			return false;
		}

	}
}
