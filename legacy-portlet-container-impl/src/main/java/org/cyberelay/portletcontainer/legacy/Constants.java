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

package org.cyberelay.portletcontainer.legacy;

/**
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
public interface Constants {
    
    /* HTTP-related constants. */
    
    String HTTP_REQUEST = "org.cyberelay.portletcontainer.legacy.http.request";
    
    String HTTP_RESPONSE = "org.cyberelay.portletcontainer.legacy.http.response";
    
    String HTTP_SESSION = "org.cyberelay.portletcontainer.legacy.http.session";
    
    String HTTP_SESSION_CREATE = "org.cyberelay.portletcontainer.legacy.http.session.create";

    /* portlet application constants. */
    
    String PORTLET_APPLICATION = "org.cyberelay.portletcontainer.legacy.portlet.application.context";
    
    String PORTLET_METHOD = "org.cyberelay.portletcontainer.legacy.portlet.method";

    String PORTLET_REQUEST = "org.cyberelay.portletcontainer.legacy.portlet.request";

    String PORTLET_RESPONSE = "org.cyberelay.portletcontainer.legacy.portlet.response";
    
    String PORTLET_SESSION = "org.cyberelay.portletcontainer.legacy.portlet.session";
    
    String PORTLET_SESSION_CREATE = "org.cyberelay.portletcontainer.legacy.portlet.session.create";
    
    String PORTLET_MESSAGE_QUEUE = "org.cyberelay.portletcontainer.legacy.portlet.message.queue";

    String PORTLET_MESSAGE = "org.cyberelay.portletcontainer.legacy.portlet.message";

    String PORTLET_WINDOW_EX = "org.cyberelay.portletcontainer.legacy.portlet.windowEx";
    
    String PORTLET_WINDOW_MODE = "org.cyberelay.portletcontainer.legacy.portlet.window.mode";

    String PORTLET_WINDOW_PREVIOUS_MODE = "org.cyberelay.portletcontainer.legacy.portlet.window.previous_mode";
    
    String PORTLET_WINDOW_STATE = "org.cyberelay.portletcontainer.legacy.portlet.window.state";

    /* portal constants */
    String PORTAL_CONTEXT = "org.cyberelay.portletcontainer.legacy.portal.context";

    String PORTAL_REQUEST_URL = "org.cyberelay.portletcontainer.legacy.portal.request.url";

    String PORTAL_REQUEST_CLIENT = "org.cyberelay.portletcontainer.legacy.portal.request.client";
    
    String PORTAL_REQUEST_USER = "org.cyberelay.portletcontainer.legacy.portal.request.user";
    
    String PORTAL_REQUESTING_PAGE = "org.cyberelay.portletcontainer.legacy.portal.requesting.page";

    String PORTAL_RENDERING_COMPONENT = "org.cyberelay.portletcontainer.legacy.portal.rendering.component";
    
    String PORTAL_HTTP_RESPONSE = HTTP_RESPONSE;

    String PORTAL_HTTP_REQUEST = HTTP_REQUEST;

}
