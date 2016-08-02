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

import javax.portlet.PortletContext;
import javax.servlet.ServletContext;


/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jul 15, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 575 $
 * <li>Last Update Time: $Date: 2008-02-26 10:28:50 +0000 (Tue, 26 Feb 2008) $
 * </ul>
 * 
 */
public interface PortletApplication {

	/**
	 * Portlet application should be properly destroyed.
	 */
	void destroy();
	
	PortletApplicationConfig getApplicationConfig();

	/**
	 * Retrieve the portlet context of this portlet application.
	 * 
	 */
	PortletContext getPortletContext();
	
	/**
	 * Retrieve the servlet context of this portlet application.
	 * 
	 * @return
	 */
	ServletContext getServletContext();
}