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

import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

/**
 * NavigationState is a set of navigational information about a specific portlet
 * window. These information include:
 * <ul>
 * <li> Portlet Window State
 * <li> Portlet Mode
 * <li> Private Render Parameters
 * <li> Public Render Parameters
 * <ul>
 * 
 * Navigation state should be available at server-side to serve portlet
 * rendering request as a supporting portlet window of portal request.
 * 
 * To serve portlet invocation request from portal application, portlet
 * container must construct varieties of
 * <code>PortletRequest/PortletResponse</code> objects, must For every portlet
 * window involved in current portal request, its initial navigational state is
 * based on the following data:
 * 
 * Portlet container is based on the following information to construct a given
 * portlet window's initial navigation state:
 * <ul>
 * <li> If the given portlet window is the target of current portal request.
 * <li> What is the type of portal request? Is the request a action request, a
 * render request or render request?
 * <li> Parameters of portal HTTP request.
 * <li> Properties of Portal request URL.
 * <li> Navigation state saved in previous portal request phase.
 * <ul>
 * 
 * During the portal request handling phase, portlet container portlet window's
 * navigation state may or may not be changed. After the portal request
 * concluded, it would be saved in user session for later use as the portlet
 * window's navigation state of next portal request phase may be based on
 * previous navigation state.
 * 
 * 
 * @author Roger Tang
 * 
 */
public interface NavigationState {

	PortletMode getPortletMode();

	void setPortletMode(PortletMode mode);

	WindowState getWindowState();

	void setWindowState(WindowState state);

	/**
	 * return an immutable map.
	 * 
	 * @return
	 */
	Map<String, String[]> getPrivateRenderParameterMap();

	/**
	 * return an immutable map.
	 * 
	 * @return
	 */
	Map<String, String[]> getPublicRenderParameterMap();

	void clearPrivateRenderParameters();

	void setPrivateRenderParameters(String key, String[] values);

	void setPublicRenderParameters(String key, String[] values);

	String getPrivateRenderParameter(String key);

	String getPublicRenderParameter(String key);

	String[] getPrivateRenderParameters(String key);

	String[] getPublicRenderParameters(String key);

	/**
	 * Save it for next rendering.
	 */
	void store();
}
