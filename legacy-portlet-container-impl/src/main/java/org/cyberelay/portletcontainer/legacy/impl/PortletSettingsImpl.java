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
package org.cyberelay.portletcontainer.legacy.impl;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;

import org.apache.jetspeed.portlet.AccessDeniedException;
import org.apache.jetspeed.portlet.Client;
import org.apache.jetspeed.portlet.PortletApplicationSettings;
import org.apache.jetspeed.portlet.PortletSettings;
import org.cyberelay.portal.util.Assert;
import org.cyberelay.portletcontainer.legacy.PortletApplication;
import org.cyberelay.portletcontainer.legacy.descriptor.ConcretePortletDefinition;

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
public class PortletSettingsImpl implements PortletSettings {
	private PortletApplication portletApplication;
	private ConcretePortletDefinition concretePortletDefinition;

	public PortletSettingsImpl(PortletApplication portletApplication,
			ConcretePortletDefinition concretePortletDefinition) {
		Assert.notNull(concretePortletDefinition);
		Assert.notNull(portletApplication);
		this.portletApplication = portletApplication;
		this.concretePortletDefinition = concretePortletDefinition;
	}

	/**
	 * Returns the name of the concrete portlet
	 */
	public String getName() {
		return concretePortletDefinition.getUniqueName();
	}

	public void setName(String name) {
		throw new RuntimeException("Not supported operation!");
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletSettings#getDefaultLocale()
	 */
	public Locale getDefaultLocale() {
		return concretePortletDefinition.getDefaultLocale();
	}

	public void setDefaultLocale(Locale locale) {
		throw new RuntimeException("Not supported operation!");
	}

	public String getTitle(Locale locale, Client client) {
		return concretePortletDefinition.getTitle(locale);
	}

	public void setAttribute(String name, String value) throws AccessDeniedException {
		throw new RuntimeException("Not supported operation!");
	}

	public String getAttribute(String name) {
		return concretePortletDefinition.getParameter(name);
	}

	public Enumeration getAttributeNames() {
		return new Hashtable(concretePortletDefinition.getParameters()).keys();
	}

	public void removeAttribute(String name) throws AccessDeniedException {
		throw new RuntimeException("Not supported operation!");
	}

	public void store() throws AccessDeniedException, IOException {
		throw new RuntimeException("Not supported operation!");
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletSettings#getApplicationSettings()
	 */
	public PortletApplicationSettings getApplicationSettings() {
		return portletApplication.getPortletApplicationSettings(concretePortletDefinition.getConcretePortletApplicationName());
	}

}
