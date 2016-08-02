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

package org.cyberelay.portletcontainer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 29, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 652 $
 * <li>Last Update Time: $Date: 2008-03-15 02:53:49 +0000 (Sat, 15 Mar 2008) $
 * </ul>
 * 
 */
public class PortletApplicationRegistry {
	private static Map<String, PortletApplication> registry;

	static {
		registry = Collections.synchronizedMap(new HashMap<String, PortletApplication>());
	}

	private PortletApplicationRegistry() {

	}

	public static PortletApplication getApplication(PortletWindow window) {
		return getApplication(window.getPortletContextPath());
	}

	public static PortletApplication getApplication(String contextRootPath) {
		return registry.get(contextRootPath);
	}

	public static Iterator<PortletApplication> getApplications() {
		return registry.values().iterator();
	}

	static void register(PortletApplication application) {
		//TODO Enforce harder restriction upon invocation of this method.
		registry.put(application.getServletContext().getContextPath(), application);
	}

	static void unregister(PortletApplication application) {
		//TODO Enforce harder restriction upon invocation of this method.
		registry.remove(application.getServletContext().getContextPath());
	}

}
