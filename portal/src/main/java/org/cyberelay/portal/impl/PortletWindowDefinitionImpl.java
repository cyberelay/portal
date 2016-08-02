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

import org.cyberelay.portal.PortletWindowDefinition;
import org.cyberelay.portal.util.Assert;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 668 $
 * <li>Last Update Time: $Date: 2008-03-18 14:18:57 +0000 (Tue, 18 Mar 2008) $
 * </ul>
 * 
 */
public class PortletWindowDefinitionImpl extends AbstractPortletWindowDefinition {

	private String pageUniqueID;

	private String uniqueID;

	private String portletName;

	private String portletContextPath;

	public PortletWindowDefinitionImpl(String uniqueID, String pageUniqueID) {
		this.uniqueID = uniqueID;
		this.pageUniqueID = pageUniqueID;
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public String getPageUniqueID() {
		return pageUniqueID;
	}

	public String getPortletContextPath() {
		return portletContextPath;
	}

	public String getPortletName() {
		return portletName;
	}

	public void setPortletName(String portletName) {
		Assert.hasText(portletName);
		this.portletName = portletName;
	}

	public void setPortletContextPath(String portletContextPath) {
		this.portletContextPath = portletContextPath;
	}

	public boolean equals(Object obj) {
		if (obj instanceof PortletWindowDefinition) {
			PortletWindowDefinition input = (PortletWindowDefinition) obj;

			return uniqueID.equals(input.getUniqueID())
					&& pageUniqueID.equals(input.getPageUniqueID());
		}

		return false;
	}

}
