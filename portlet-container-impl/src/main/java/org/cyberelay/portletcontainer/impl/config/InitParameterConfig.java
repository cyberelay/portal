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
package org.cyberelay.portletcontainer.impl.config;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portlet.descriptor.model.InitParamType;
import org.cyberelay.portlet.descriptor.model.PortletType;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Mar 13, 2008
 * <li>Last Editor: $Author$
 * <li>Current Revision: $Revision$
 * <li>Last Update Time: $Date$
 * </ul>
 * 
 */
class InitParameterConfig  extends AbstractConfig {
	private static final Logger LOG = LoggerFactory.getLogger(InitParameterConfig.class);

	private Map<String, String> parameters;

	public InitParameterConfig(PortletType portlet) {
		String portletName = portlet.getPortletName().getValue();

		parameters = new HashMap<String, String>();
		for (InitParamType param : portlet.getInitParam()) {
			String name = param.getName().getValue();
			String value = param.getValue().getValue();
			parameters.put(name, value);
			LOG.info("[" + portletName + "] initial parameter -> [" + name + " : " + value + "]");
		}
		LOG.info("[" + portletName + "] initial parameter = [" + parameters.size() + "]");
		parameters = Collections.unmodifiableMap(parameters);
	}

	public String getParameter(String name) {
		return parameters.get(name);
	}

	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(parameters.keySet());
	}
}
