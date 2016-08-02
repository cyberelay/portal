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

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jetspeed.portlet.Portlet;
import org.apache.jetspeed.portlet.PortletApplicationSettings;
import org.apache.jetspeed.portlet.PortletConfig;
import org.apache.jetspeed.portlet.PortletContext;
import org.apache.jetspeed.portlet.PortletSettings;
import org.apache.jetspeed.portlet.UnavailableException;
import org.cyberelay.portletcontainer.legacy.descriptor.PortletApplicationDescriptor;

/**
 *
 * @author Roger Tang
 *
 */
public interface PortletApplication {

	String getContextPath();

	PortletApplicationDescriptor getDescriptor();
	
	/**
	 * The returned instance MUST have been initialized. It means:
	 * <li> <code>Portlet.init(PortletConfig)</code> has been invoked on the
	 * returning instance</li>
	 * <li> <code>Portlet.initConcrete(PortletSettings)</code> has been invoked on the
	 * returning instance</li>
	 */
	Portlet getPortletInstance(String concretePortletName) throws UnavailableException;

	PortletSettings getPortletSettings(String concretePortletName);

	PortletConfig getPortletConfig(String abstractPortletName);

	PortletConfig getPortletConfig(PortletWindowEx window);

	PortletApplicationSettings getPortletApplicationSettings(String concretePortletApplicationName);

	ServletContext getServletContext();

	PortletContext getPortletContext();

	void invokePortlet(PortletMethod method, PortletWindowEx window, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

	/**
	 * All LegacyPortlet instances must be properly destroyed.
	 * 
	 */
	void destroy();
}
