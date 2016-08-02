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
package org.cyberelay.portal.impl;

import java.util.List;

import javax.portlet.PortletURLGenerationListener;
import javax.portlet.ResourceURL;

import org.cyberelay.portal.PortalURL;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Feb 29, 2008
 * <li>Last Editor: $Author$
 * <li>Current Revision: $Revision$
 * <li>Last Update Time: $Date$
 * </ul>
 * 
 */
class ResourceURLImpl extends BaseURLImpl implements ResourceURL {
	public static final String CACHE = "cache";

	/**
	 * @param url
	 */
	public ResourceURLImpl(PortalURL url, List<PortletURLGenerationListener> listeners) {
		super(url, listeners);
		portalURL.setType(PortalURL.PORTLET_RESOURCE);
	}

	public String getCacheability() {
		return portalURL.getProperty(CACHE);
	}

	public void setCacheability(String cacheLevel) {
		portalURL.setProperty(CACHE, cacheLevel);
	}

	public void setResourceID(String resourceID) {
		portalURL.setResourceID(resourceID);
	}

	protected void invokeListener(PortletURLGenerationListener listener) {
		listener.filterResourceURL(this);
	}
}
