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

import org.apache.jetspeed.portlet.AccessDeniedException;
import org.apache.jetspeed.portlet.PortletApplicationSettings;
import org.cyberelay.portletcontainer.legacy.PortletApplication;
import org.cyberelay.portletcontainer.legacy.descriptor.ConcretePortletApplicationDefinition;

/**
 * 
 * @author Roger Tang
 * 
 */
public class PortletApplicationSettingsImpl implements PortletApplicationSettings {
	private PortletApplication portletApplication;
	private ConcretePortletApplicationDefinition concretePortletApplicationDefinition;

	public PortletApplicationSettingsImpl(PortletApplication portletApplication,
			ConcretePortletApplicationDefinition appDef) {
		this.portletApplication = portletApplication;
		this.concretePortletApplicationDefinition = appDef;
	}

	/**
	 * Sets the attribute with the given name and value
	 */
	public void setAttribute(String name, String value) throws AccessDeniedException {
		throw new RuntimeException("Not supported operation.");
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletApplicationSettings#getAttribute(java.lang.String)
	 */
	public String getAttribute(String name) {
		return concretePortletApplicationDefinition.getParameter(name);
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletApplicationSettings#getAttributeNames()
	 */
	public Enumeration getAttributeNames() {
		return new Hashtable(concretePortletApplicationDefinition.getParameters()).keys();
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletApplicationSettings#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String name) throws AccessDeniedException {
		throw new RuntimeException("Not supported operation.");
	}

	public String getContextName() {
		return portletApplication.getContextPath();
	}

	/**
	 * @see org.apache.jetspeed.portlet.PortletApplicationSettings#store()
	 */
	public void store() throws AccessDeniedException, IOException {
		throw new RuntimeException("Not supported operation.");
	}

}
