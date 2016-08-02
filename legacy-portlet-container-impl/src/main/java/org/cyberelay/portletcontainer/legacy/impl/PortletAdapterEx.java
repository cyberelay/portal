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
package org.cyberelay.portletcontainer.legacy.impl;

import org.apache.jetspeed.portlet.PortletAdapter;
import org.apache.jetspeed.portlet.PortletSettings;

/**
 * This is a base portlet for Legacy Portlet. Every Legacy Portlet
 * implementation SHOULD directly or indirectly extend from this class.
 * 
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jan 21, 2008
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
public class PortletAdapterEx extends PortletAdapter {

	/**
	 * @deprecated
	 * @return
	 */
	public PortletSettings getPortletSettings() {
		throw new UnsupportedOperationException(
				"Retrieving PortletSettings from PortletAdpapter no longer suppored. "
						+ "Please use PortletRequest.getPortletSettings() instead.");
	}
}
