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

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jul 7, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 684 $
 * <li>Last Update Time: $Date: 2008-03-23 10:24:06 +0000 (Sun, 23 Mar 2008) $
 * </ul>
 * 
 */
public interface PortalConstants {

	String PORTAL_CONTEXT = "org.cyberelay.portal.context";

	String PORTAL_APPLICATION = "org.cyberelay.portal.application";

	String PORTAL_REQUESTING_PAGE = "org.cyberelay.portal.requesting.page";

	String PORTAL_REQUESTING_URL = "org.cyberelay.portal.requesting.url";

	String PORTAL_REQUESTING_USER = "org.cyberelay.portal.requesting.user";

	String PORTAL_REQUESTING_CLIENT = "org.cyberelay.portal.requesting.client";

	String PORTAL_RENDERING_COMPONENT = "org.cyberelay.portal.rendering.uicomponent";

	String PORTAL_RENDERING_PORTLET_WINDOW = "org.cyberelay.portal.rendering.portlet.window";

	String TARGET_PORTLET_WINDOW = "org.cyberelay.portal.request.target.window";

	String PORTAL_HTTP_SESSION = "org.cyberelay.portal.http.session";

	String PORTAL_HTTP_REQUEST = "org.cyberelay.portal.http.request";

	String PORTAL_HTTP_RESPONSE = "org.cyberelay.portal.http.response";
	
	String PORTAL_RESPONSE_REDIRECTED = "org.cyberelay.portal.http.response.redirected";
}
