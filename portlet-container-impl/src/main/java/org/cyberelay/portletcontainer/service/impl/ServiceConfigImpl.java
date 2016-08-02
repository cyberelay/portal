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
package org.cyberelay.portletcontainer.service.impl;

import java.util.Iterator;
import java.util.Map;

import org.cyberelay.portletcontainer.PortletContainer;
import org.cyberelay.portletcontainer.ServiceConfig;

/**
 * @author Terry Li
 * 
 */
class ServiceConfigImpl implements ServiceConfig {

	private String name;

	private PortletContainer container;

	private Map<String, String> initParameter;

	public ServiceConfigImpl(String name, PortletContainer container,
			Map<String, String> initParameter) {
		this.name = name;
		this.container = container;
		this.initParameter = initParameter;
	}

	public String getInitParameter(String name) {
		return initParameter.get(name);
	}

	public Iterator<String> getInitParameterNames() {
		return initParameter.keySet().iterator();
	}

	public PortletContainer getPortletContainer() {
		return container;
	}

	public String getServiceName() {
		return name;
	}

}
