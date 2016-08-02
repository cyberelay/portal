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

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.cyberelay.portletcontainer.PortletContainer;
import org.cyberelay.portletcontainer.PortletContainerException;
import org.cyberelay.portletcontainer.PortletContainerService;
import org.cyberelay.portletcontainer.ServiceConfig;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author Roger Tang
 * 
 */
class XMLPortletContainerServiceInitializer {

	private PortletContainer container;

	private Map<Class, PortletContainerService> services;

	public XMLPortletContainerServiceInitializer(PortletContainer container)
			throws PortletContainerException {
		this.container = container;
		initPortletContainerServices();
	}

	/**
	 * 
	 * @see org.cyberelay.portletcontainer.service.PortletContainerServiceProvider#getService(java.lang.Class)
	 */
	public PortletContainerService getService(Class serviceInterface) {
		return services.get(serviceInterface);
	}

	protected void initPortletContainerServices() throws PortletContainerException {
		this.services = new HashMap<Class, PortletContainerService>();

		try {
			String containerDefPath = "org/cyberelay/portlet/container.xml";
			InputStream input =
					Thread.currentThread().getContextClassLoader().getResourceAsStream(
							containerDefPath);
			ContainerDefinitionHandler handler = new ContainerDefinitionHandler();
			XMLReader parser;
			parser = XMLReaderFactory.createXMLReader();
			parser.setContentHandler(handler);
			parser.parse(new InputSource(input));

			if (handler.getContainer().getServices() == null) {
				return;
			}
			List<ContainerDefinitionHandler.Service> services =
					handler.getContainer().getServices().getServices();
			for (Iterator<ContainerDefinitionHandler.Service> iter = services.iterator(); iter.hasNext();) {
				ContainerDefinitionHandler.Service service = iter.next();
				Class clazz =
						Thread.currentThread().getContextClassLoader().loadClass(
								service.getInterfase());
				PortletContainerService instance =
						(PortletContainerService) Thread.currentThread().getContextClassLoader().loadClass(
								service.getImplementation()).newInstance();
				ServiceConfig serviceCfg =
						new ServiceConfigImpl(service.getName(), container, service.getParameters());
				instance.init(serviceCfg);
				this.services.put(clazz, instance);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PortletContainerException(e);
		}
	}

}
