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
package org.cyberelay.portletcontainer.impl.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portlet.descriptor.model.PortletAppType;
import org.cyberelay.portlet.descriptor.model.UserAttributeType;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Mar 13, 2008
 * <li>Last Editor: $Author$
 * <li>Current Revision: $Revision$
 * <li>Last Update Time: $Date$
 * </ul>
 * 
 */
class UserAttributeConfig extends AbstractConfig {
	private static Logger LOG = LoggerFactory.getLogger(UserAttributeConfig.class);
	private Set<String> userAttributes;

	public UserAttributeConfig(PortletAppType app) {
		String appId = app.getId();

		userAttributes = new HashSet<String>();
		for (UserAttributeType attrType : app.getUserAttribute()) {
			userAttributes.add(attrType.getName().getValue());
			LOG.info("User Attribute: " + attrType.getName().getValue());
		}
		userAttributes = Collections.unmodifiableSet(userAttributes);
		
		LOG.info("[" + appId + "] defined user attribute = [" + userAttributes.size() + "]");
	}

	public Set<String> getUserAttributeNames() {
		return userAttributes;
	}
}
