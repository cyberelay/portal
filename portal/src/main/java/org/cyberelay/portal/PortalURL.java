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
import java.util.Map;

/**
 * A portal URL is composed of three parts.
 * <ul>
 * <li> 1. URL prefix. It is usually composed with portal application context
 * path plus servlet name which would be mapped to aggregation servlet 
 * (e.g. "/<PORTATL_CONTEXT_ROOT>/<AGGREGATION_SERVLET_NAME>").
 * For Cyberelay portal server, the default URL prefix is "/portal/page"
 * 
 * <li> 2. URL name-property pairs. It usually shows up just behind prefix like 
 * "/<PROPERTY_NAME1>=<VALUE1>=<VALUE2>[/<PROPERTY_NAME2>=<VALUE1>]".
 * Cyberelay portal define a set of standard properties to support as below:
 * 
 * <li> 3. Query parameter part.
 * </ul>
 * 
 * @author Roger Tang
 * 
 *
 */
public interface PortalURL {

	/*----- name of types ------------------*/

	String PAGE = "pag";

	String PORTLET_ACTION = "act";

	String PORTLET_RESOURCE = "res";

	String PORTLET_RENDER = "rnd";
	
	String LOGOUT = "logout";

	/*---- name of standard properties ----------*/

	String PROP_URL_TYPE = "ut";

	String PROP_PAGE = "pg";

	String PROP_PORTLET_WINDOW = "pw";

	String PROP_PORTLET_MODE = "pm";

	String PROP_WINDOW_STATE = "ws";

	String PROP_RESOURCE_ID = "ri";

	String PROP_ACTION_STRTING = "as";

	/*--------------------------------------------*/

	String getPrefix();
	
	String getProperty(String name);
	
	String[] getProperties(String name);
	
	void addProperty(String name, String value) throws FrozenURLException;

	void setProperty(String name, String value) throws FrozenURLException;
	
	void setProperty(String name, String[] value) throws FrozenURLException;

	/**
	 * return requesting page unique name.
	 * 
	 * @return
	 */
	String getPageID();

	void setPageID(String pageID) throws FrozenURLException;

	/**
	 * return requesting target portlet window unique name.
	 * 
	 * @return
	 */
	String getPortletWindowID();

	void setPortletWindowID(String name) throws FrozenURLException;

	/**
	 * return requesting mode of target portlet window.
	 * 
	 * @return
	 */
	String getPortletMode();

	void setPortletMode(String mode) throws FrozenURLException;

	/**
	 * return requesting state of target portlet window.
	 * 
	 * @return
	 */
	String getPortletWindowState();

	void setPortletWindowState(String state) throws FrozenURLException;

	String getType();

	void setType(String urlType) throws FrozenURLException;

	/**
	 * return action string of target portlet.
	 * 
	 * @return
	 */
	String getActionString();

	void setActionString(String actionString) throws FrozenURLException;

	/**
	 * 
	 * @return request resource ID
	 */
	String getResourceID();

	void setResourceID(String resourceID) throws FrozenURLException;

	/**
	 * 
	 * @param name
	 * @return
	 */
	String getQueryParameter(String name);

	String[] getQueryParameters(String name);

	Iterator<String> getQueryParameterNames();
	
	/**
	 * return an immutable map.
	 * @return
	 */
	Map<String, String[]> getQueryParameters();

	void setQueryParameter(String name, String value) throws FrozenURLException;

	void setQueryParameter(String name, String[] values) throws FrozenURLException;

	/**
	 * Freeze property values. It is a one-way action. Once called, its properties can not be
	 * changed any more.
	 * 
	 * Trying to set property value after this method invocation will lead to
	 * <code>FrozenURLException</code>
	 */
	void freeze();

	boolean isFrozen();

	String toString();
}