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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.cyberelay.portletcontainer.legacy.descriptor.ConcretePortletApplicationDefinition;

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
class ConcretePortletApplicationDefinitionImpl implements ConcretePortletApplicationDefinition {

	private String uniqueName;
	private Map<String, String> parameters;

	public ConcretePortletApplicationDefinitionImpl(String uniqueName) {
		this.uniqueName = uniqueName;
		this.parameters = new HashMap<String, String>();
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public Map<String, String> getParameters() {
		return Collections.unmodifiableMap(parameters);
	}

	public String getParameter(String name) {
		return (String) parameters.get(name);
	}

	public void setParameter(String name, String value) {
		if (name != null) {
			parameters.put(name, value);
		}
	}

	public String toString() {
		return "unique name = [" + uniqueName + "], parameter size = [" + parameters.size() + "]";
	}
}
