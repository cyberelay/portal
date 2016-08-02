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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cyberelay.portal.util.StringUtil;
import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portlet.descriptor.model.ContainerRuntimeOptionType;
import org.cyberelay.portlet.descriptor.model.PortletAppType;
import org.cyberelay.portlet.descriptor.model.PortletType;
import org.cyberelay.portlet.descriptor.model.ValueType;
import org.cyberelay.portletcontainer.PortletContainerInfo;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Mar 11, 2008
 * <li>Last Editor: $Author$
 * <li>Current Revision: $Revision$
 * <li>Last Update Time: $Date$
 * </ul>
 * 
 */
class RuntimeOptionConfig extends AbstractConfig {
	private static final Logger LOG = LoggerFactory.getLogger(RuntimeOptionConfig.class);
	private Map<String, String[]> options;

	public RuntimeOptionConfig(PortletType portlet, PortletAppType app) {
		options = new HashMap<String, String[]>();
		dump(app.getContainerRuntimeOption());
		dump(portlet.getContainerRuntimeOption());

		if (LOG.isInfoEnabled()) {
			String portletName = portlet.getPortletName().getValue();
			LOG.info("[" + portletName + "] runtime options = {\n" + optionsToString() + "\n}");
		}
	}

	private void dump(List<ContainerRuntimeOptionType> optionTypes) {
		for (ContainerRuntimeOptionType optionType : optionTypes) {
			String name = optionType.getName().getValue();
			if (PortletContainerInfo.isRuntimeOptionSupported(name)) {
				List<String> values = new ArrayList<String>();
				for (ValueType valueType : optionType.getValue()) {
					String value = valueType.getValue();
					values.add(value);
				}
				options.put(name, values.toArray(new String[values.size()]));
			}
		}

		options = Collections.unmodifiableMap(options);
	}

	private String optionsToString() {
		StringBuilder sb = new StringBuilder();
		boolean newLine = false;
		for (String key : options.keySet()) {
			if (newLine) {
				sb.append("\n");
			}
			sb.append("  ").append(key).append("=");
			sb.append(StringUtil.arrayToString(options.get(key)));
			newLine = true;
		}
		return sb.toString();
	}

	public Map<String, String[]> getRuntimeOptions() {
		return options;
	}
}
