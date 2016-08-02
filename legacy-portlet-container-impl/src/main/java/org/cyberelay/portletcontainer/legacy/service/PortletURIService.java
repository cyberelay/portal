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

package org.cyberelay.portletcontainer.legacy.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.jetspeed.portlet.PortletURI;
import org.apache.jetspeed.portlet.Portlet.Mode;
import org.apache.jetspeed.portlet.PortletWindow.State;
import org.cyberelay.portletcontainer.PortletContainerService;
import org.cyberelay.portletcontainer.legacy.PortletWindowEx;

/**
 * @author Roger Tang
 * 
 */
public interface PortletURIService extends PortletContainerService {

	PortletURI createPortletURI(HttpServletRequest request, PortletWindowEx window, State state,
			Mode mode);
}
