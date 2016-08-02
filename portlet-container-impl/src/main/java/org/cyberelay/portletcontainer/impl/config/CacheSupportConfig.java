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

import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portlet.descriptor.model.PortletType;

/**
 * @author Roger Tang
 * 
 */
class CacheSupportConfig extends AbstractConfig {
	private static final Logger LOG = LoggerFactory.getLogger(CacheSupportConfig.class);

	private int expiration = -1;
	private String cacheScope;

	public CacheSupportConfig(PortletType portlet) {
		String portletName = getPortletName(portlet);

		if (portlet.getExpirationCache() != null) {
			expiration = portlet.getExpirationCache().getValue();
		}
		if (portlet.getCacheScope() != null) {
			cacheScope = portlet.getCacheScope().getValue();
		}

		LOG.info("["
				+ portletName
				+ "] cache setting -> [expiration: "
				+ expiration
				+ ", cache scope: "
				+ cacheScope
				+ "]");
	}

	public String getCacheScope() {
		return cacheScope;
	}

	public int getDurationOfExpiration() {
		return expiration;
	}
}
