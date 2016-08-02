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

import org.cyberelay.portal.PortalApplication;
import org.cyberelay.portal.PortalConstants;
import org.cyberelay.portal.service.PortalApplicationService;
import org.cyberelay.portal.service.ServiceInitializationException;
import org.cyberelay.portal.util.Assert;
import org.cyberelay.portletcontainer.PortletContainer;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jul 10, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
public abstract class AbstractPortalApplicationService implements
		PortalApplicationService,
		PortalConstants {
	
	private PortalApplication portal;

	/**
	 * @see org.cyberelay.portal.service.PortalApplicationService#init(org.cyberelay.portal.PortalApplication)
	 */
	public final void init(PortalApplication portal) throws ServiceInitializationException {
		Assert.notNull(portal, "Initializing service with NULL portal application.");
		this.portal = portal;
		init();
	}

	protected PortalApplication getPortalApplication() {
		return portal;
	}

	protected PortletContainer getPortletContainer() {
		return getPortalApplication().getPortletContainer();
	}

	protected void init() throws ServiceInitializationException {
		// To be overridden by subclasses.
	}

	public void destory() {
		// To be overridden by subclasses.
	}
}
