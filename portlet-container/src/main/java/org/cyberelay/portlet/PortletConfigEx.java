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
package org.cyberelay.portlet;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.portlet.WindowState;
import javax.xml.namespace.QName;

/**
 * PortletConfigEx is an extension of <code>javax.portlet.PortletConfig</code>
 * to provide additional configuration information about the portlet defined in
 * portlet.xml file.
 * 
 * @author Roger Tang
 * 
 */
public interface PortletConfigEx extends PortletConfig {

	/**
	 * Check if the given name is a name of public render parameter supported by
	 * this portlet.
	 * 
	 * @param name
	 * @return
	 */
	boolean isSupportedPublicRenderParameter(String name);

	boolean isProcessableEventName(QName eventName);

	boolean isPublishableEventName(QName eventName);
	
	/**
	 * The returned PortletPreferences is based on the portlet preferences
	 * declared in portlet.xml It is a unmodifiable object. Any attempt to
	 * modify it has no effect.
	 * 
	 * @return
	 */
	PortletPreferences getInitPortletPreferences();

	/**
	 * 
	 * @return the preference validator declared in portlet.xml for this
	 *         portlet.
	 */
	PreferencesValidator getPreferenceValidator();

	/**
	 * 
	 * @return immutable set of accessible roles
	 */
	Set<String> getAccessibleRoles();

	/**
	 * Retrieves the portlet window states that portlet supported with
	 * "text/html" mime-type.
	 * 
	 * @return
	 */
	Enumeration<WindowState> getSupportedWindowStates();

	Enumeration<WindowState> getSupportedWindowStates(String mimeType);
	/**
	 * Retrieves the portlet states that portlet supported with "text/html"
	 * mime-type.
	 * 
	 * @return
	 */
	Enumeration<PortletMode> getSupportedPortletModes();

	Enumeration<PortletMode> getSupportedPortletModes(String mimeType);
	
	Enumeration<String> getSupportedMimeTypes();
	
	boolean isSupportedLocale(Locale locale);
	
	boolean isSupportedMimeType(String mimeType);

	/**
	 * Check if the given state is supported by this portlet in "text/html"
	 * mime-type.
	 * 
	 * @return
	 */
	boolean isSupportedWindowState(WindowState state);

	boolean isSupportedWindowState(WindowState state, String mimeType);
	
	boolean isSupportedPortletMode(PortletMode mode);

	boolean isSupportedPortletMode(PortletMode mode, String mimeType);

	/**
	 * Retrieves the duration of expiration in second.
	 * 
	 * @return
	 */
	int getDurationOfExpiration();

	String getCacheScope();
}
