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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.cyberelay.portal.Client;
import org.cyberelay.portal.service.ClientInfoService;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Aug 10, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
public class ClientInfoServiceImpl extends AbstractPortalApplicationService implements ClientInfoService {
	private static final Client DUMMY_CLIENT = new DummyClient();
		
	/**
	 * @see org.cyberelay.portal.service.ClientInfoService#getClient(javax.servlet.http.HttpServletRequest)
	 */
	public Client getClient(HttpServletRequest request) {
		return DUMMY_CLIENT;
	}

	private static class DummyClient implements Client {
		private List<String> contentTypes = new ArrayList<String>();
		
		public DummyClient() {
			contentTypes.add("html/text");
		}
		
		public String getAcceptedContentType() {
			return contentTypes.get(0);
		}
		
		public Iterator<String> getAcceptedContentTypes() {
			return contentTypes.iterator();
		}
		
		public String getAttribute(String name) {
			return null;
		}
		
		public Iterator<String> getAttributeNames() {
			return Collections.EMPTY_LIST.iterator();
		}
	}
}
