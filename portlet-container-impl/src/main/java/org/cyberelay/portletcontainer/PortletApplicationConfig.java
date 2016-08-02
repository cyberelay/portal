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

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.portlet.Portlet;
import javax.portlet.PortletURLGenerationListener;
import javax.portlet.filter.FilterChain;

import org.cyberelay.portlet.PortletConfigEx;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jan 18, 2008
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 633 $
 * <li>Last Update Time: $Date: 2008-03-11 07:39:59 +0000 (Tue, 11 Mar 2008) $
 * </ul>
 * 
 */
public interface PortletApplicationConfig {

	void destroy();

	Iterator<String> getPortletNames();

	/**
	 * Retrieves the user attribute names that portlet application required.
	 * 
	 * @return a unmodifiable set.
	 */
	Set<String> getUserAttributeNames();

	/**
	 * return a unmodifiable List.
	 * 
	 * @return
	 */
	List<PortletURLGenerationListener> getListeners();

	PortletConfigEx getPortletConfig(String portletName);

	/**
	 * Retrieved portlet should be properly initialized. It means
	 * <code>Portlet.init(PortletConfig config)</code> method has been called.
	 * 
	 * @param portletName
	 * @return
	 */
	Portlet getPortlet(String portletName);

	FilterChain getActionFilterChain(String portletName);

	FilterChain getRenderFilterChain(String portletName);

	FilterChain getResourceFilterChain(String portletName);

	FilterChain getEventFilterChain(String portletName);
}
