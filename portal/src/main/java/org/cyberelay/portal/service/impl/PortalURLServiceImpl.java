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

import javax.servlet.http.HttpServletRequest;

import org.cyberelay.portal.PortalURL;
import org.cyberelay.portal.service.PortalURLService;
import org.cyberelay.portal.service.ServiceInitializationException;

/**
 * @author Roger Tang
 * 
 */
public class PortalURLServiceImpl extends AbstractPortalApplicationService implements
		PortalURLService {
	private static final String PORTAL_REQUEST_PREFIX = "portal.request.prefix";
	private String portalURLPrefix;

	/**
	 * @see org.cyberelay.portal.service.PortalURLService#createPageURL(java.lang.String)
	 */
	public PortalURL createPageURL(String pageUniqueID) {
		PortalURL url = createPortalURL();
		url.setType(PortalURL.PAGE);
		url.setPageID(pageUniqueID);

		return url;
	}

	@Override
	protected void init() throws ServiceInitializationException {
		String prefix = getPortalApplication().getConfiguration().getValue(PORTAL_REQUEST_PREFIX);
		portalURLPrefix = getPortalApplication().getPortalContext().getContextPath() + prefix;
	}

	public PortalURL createPortalURL() {
		return new PortalURLImpl(portalURLPrefix);
	}

	/**
	 * @see org.cyberelay.portal.service.PortalURLService#parsePortalURL(javax.servlet.http.HttpServletRequest)
	 */
	public PortalURL parsePortalURL(HttpServletRequest request) {
		PortalURL result = new PortalURLImpl(getPortalURLPrefix(), request);
		result.freeze();
		return result;
	}

	protected String getPortalURLPrefix() {
		return portalURLPrefix;
	}
}
