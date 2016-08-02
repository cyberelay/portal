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

import javax.xml.XMLConstants;

import org.cyberelay.portlet.descriptor.model.PortletAppType;
import org.cyberelay.portlet.descriptor.model.PortletType;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Mar 14, 2008
 * <li>Last Editor: $Author$
 * <li>Current Revision: $Revision$
 * <li>Last Update Time: $Date$
 * </ul>
 * 
 */
abstract class AbstractConfig {

	protected String getDefaultNamespace(PortletAppType app) {
		String namespace = app.getDefaultNamespace();
		return namespace == null ? XMLConstants.NULL_NS_URI : namespace;
	}

	protected String getPortletName(PortletType portlet) {
		return portlet.getPortletName().getValue();
	}
}
