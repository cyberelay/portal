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
import java.util.HashSet;
import java.util.Set;

import javax.portlet.PortletException;
import javax.xml.namespace.QName;

import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portlet.descriptor.model.PortletAppType;
import org.cyberelay.portlet.descriptor.model.PortletType;
import org.cyberelay.portlet.descriptor.model.PublicRenderParameterType;

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
class PublicParameterConfig extends AbstractConfig {
	private static final Logger LOG = LoggerFactory.getLogger(PublicParameterConfig.class);
	private Set<String> parameterNames;

	public PublicParameterConfig(PortletType portlet, PortletAppType app) throws PortletException {
		parameterNames = new HashSet<String>();

		String portletName = getPortletName(portlet);
		for (String identifier : portlet.getSupportedPublicRenderParameter()) {
			QName name = resolvePublicParameterQName(identifier, app);
			parameterNames.add(name.toString());
			LOG.info("[" + portletName + "] public render parameter -> [" + name + "]");
		}
		parameterNames = Collections.unmodifiableSet(parameterNames);
		if (LOG.isInfoEnabled()) {
			int size = parameterNames.size();
			LOG.info("[" + portletName + "] defined public render parameter = [" + size + "]");
		}
	}

	private QName resolvePublicParameterQName(String identifier, PortletAppType app)
			throws PublicParameterDefinitionException {
		String defaultNamespace = getDefaultNamespace(app);
		for (PublicRenderParameterType paramType : app.getPublicRenderParameter()) {
			if (identifier.equals(paramType.getIdentifier())) {
				QName name = paramType.getQname();
				if (name != null) {
					return name;
				} else if (paramType.getName() != null) {
					return new QName(defaultNamespace, paramType.getName());
				}
			}
		}

		throw new PublicParameterDefinitionException(
				"No public render parameter defined! Identifier = [" + identifier + "]");
	}

	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(parameterNames);
	}

	public boolean isSupportedParameter(String name) {
		return parameterNames.contains(name);
	}
}
