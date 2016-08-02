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

/**
 * @author Roger Tang
 * 
 *
 */
class AbstractPortletApplicationDefinitionImpl implements AbstractPortletApplicationDefinition {

	private String uniqueName;
	private Map<String, String> parameters;

	public AbstractPortletApplicationDefinitionImpl(String uniqueName) {
		Assert.hasText(uniqueName);
		this.uniqueName = uniqueName;
		this.parameters = new HashMap<String, String>();
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public String getParameter(String name) {
		return (String) parameters.get(name);
	}

	public Iterator<String> getParameterNames() {
		return parameters.keySet().iterator();
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
