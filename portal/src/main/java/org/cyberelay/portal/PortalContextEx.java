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

package org.cyberelay.portal;

import java.util.Iterator;

import javax.portlet.PortalContext;
import javax.portlet.PortletMode;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;

/**
 * PortalContextEx is an extension of <code>javax.portlet.PortalContext</code>
 * to provide additional descriptive information about portal application.
 * 
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jul 21, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 666 $
 * <li>Last Update Time: $Date: 2008-03-18 10:15:24 +0000 (Tue, 18 Mar 2008) $
 * </ul>
 * 
 */
public interface PortalContextEx extends PortalContext {

	/**
	 * Check if the portal application supports the given portlet mode.
	 * 
	 * @param mode
	 *            Portlet mode to be checked.
	 * @return True if the portal application supports the given portlet mode.
	 */
	boolean isPortletModeSupported(PortletMode mode);

	/**
	 * Check if the portal application supports the given portlet window mode.
	 * 
	 * @param state
	 *            Portlet window state to be checked.
	 * @return True if the portal application supports the given portlet window
	 *         state.
	 */
	boolean isWindowStateSupported(WindowState state);

	/**
	 * Retrieve the context path of portal application. The returned value
	 * should start with a "/" but should not end with a "/" character
	 * (e.g."/portal")
	 * 
	 * @return
	 */
	String getContextPath();
}
