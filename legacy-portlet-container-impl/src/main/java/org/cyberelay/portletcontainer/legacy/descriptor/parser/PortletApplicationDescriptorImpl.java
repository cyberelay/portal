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
package org.cyberelay.portletcontainer.legacy.descriptor.parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.cyberelay.portal.util.Assert;
import org.cyberelay.portletcontainer.legacy.descriptor.AbstractPortletApplicationDefinition;
import org.cyberelay.portletcontainer.legacy.descriptor.AbstractPortletDefinition;
import org.cyberelay.portletcontainer.legacy.descriptor.ConcretePortletApplicationDefinition;
import org.cyberelay.portletcontainer.legacy.descriptor.ConcretePortletDefinition;
import org.cyberelay.portletcontainer.legacy.descriptor.PortletApplicationDescriptor;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jan 18, 2008
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
class PortletApplicationDescriptorImpl implements PortletApplicationDescriptor {
	private Map<String, AbstractPortletDefinition> abstractPortletDefinitions;
	private Map<String, ConcretePortletDefinition> concretePortletDefinitions;
	private Map<String, ConcretePortletApplicationDefinition> concretePortletApplicationDefinitions;
	private AbstractPortletApplicationDefinition abstractPortletApplicationDefinition;

	public PortletApplicationDescriptorImpl() {
		abstractPortletDefinitions = new HashMap<String, AbstractPortletDefinition>();
		concretePortletDefinitions = new HashMap<String, ConcretePortletDefinition>();
		concretePortletApplicationDefinitions = new HashMap<String, ConcretePortletApplicationDefinition>();

		abstractPortletApplicationDefinition = null;
	}

	public AbstractPortletDefinition getAbstractPortletDefinition(String abstractPortletName) {
		return abstractPortletDefinitions.get(abstractPortletName);
	}

	public ConcretePortletApplicationDefinition getConcretePortletApplicationDefinition(
			String concretePortletApplicationName) {
		return concretePortletApplicationDefinitions.get(concretePortletApplicationName);
	}

	public ConcretePortletDefinition getConcretePortletDefinition(String concretePortletName) {
		return concretePortletDefinitions.get(concretePortletName);
	}

	public AbstractPortletApplicationDefinition getAbstractPortletApplicationDefinition() {
		return abstractPortletApplicationDefinition;
	}

	public Iterator<String> getAbstractPortletUniqueNames() {
		return abstractPortletDefinitions.keySet().iterator();
	}

	public Iterator<String> getConcretePortletUniqueNames() {
		return concretePortletDefinitions.keySet().iterator();
	}

	public Iterator<String> getConcretePortletApplicationUniqueNames() {
		return concretePortletApplicationDefinitions.keySet().iterator();
	}

	void addAbstractPortletDefinition(AbstractPortletDefinition definition) {
		Assert.notNull(definition);
		abstractPortletDefinitions.put(definition.getUniqueName(), definition);
	}

	void addConcretePortletDefinition(ConcretePortletDefinition definition) {
		Assert.notNull(definition);
		concretePortletDefinitions.put(definition.getUniqueName(), definition);
	}

	void addConcretePortletApplicationDefinition(ConcretePortletApplicationDefinition definition) {
		Assert.notNull(definition);
		concretePortletApplicationDefinitions.put(definition.getUniqueName(), definition);
	}

	void setAbstractPortletApplicationDefinition(AbstractPortletApplicationDefinition definition) {
		Assert.notNull(definition);
		this.abstractPortletApplicationDefinition = definition;
	}
}
