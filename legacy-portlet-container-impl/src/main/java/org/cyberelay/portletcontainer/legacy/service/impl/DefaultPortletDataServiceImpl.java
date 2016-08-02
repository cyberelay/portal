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
package org.cyberelay.portletcontainer.legacy.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.jetspeed.portlet.PortletData;
import org.apache.jetspeed.portlet.User;
import org.cyberelay.portletcontainer.AbstractPortletContainerService;
import org.cyberelay.portletcontainer.legacy.PortletWindowEx;
import org.cyberelay.portletcontainer.legacy.service.PortletDataService;

public class DefaultPortletDataServiceImpl extends AbstractPortletContainerService implements
		PortletDataService {
	
	private Map<String, PortletData> portletDataMap;

	public DefaultPortletDataServiceImpl() {
		portletDataMap = new HashMap<String, PortletData>();
	}

	public PortletData getPortletData(User user, PortletWindowEx window) {
		if (user == null || window == null) {
			return null;
		}

		String key = getKey(user, window);
		PortletData data = (PortletData) portletDataMap.get(key);
		if (data == null) {
			data = new PortletDataImpl();
			portletDataMap.put(key, data);
		}

		return data;
	}

	private String getKey(User user, PortletWindowEx window) {
		return user.getUserID() + "." + window.getUniqueID();
	}

}
