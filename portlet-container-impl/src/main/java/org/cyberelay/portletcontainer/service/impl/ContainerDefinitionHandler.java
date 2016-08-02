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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cyberelay.portal.util.xml.BaseContentHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * @author Terry Li
 * 
 */
public class ContainerDefinitionHandler extends BaseContentHandler {

	private final static String CONTAINER = "container";

	private final static String SERVICES = "services";

	private final static String SERVICE = "service";

	private final static String INTERFACE = "interface";

	private final static String IMPLEMENTATION = "implementation";

	private final static String NAME = "name";

	private final static String PARAMETERS = "parameters";

	private final static String PARAMETER = "parameter";

	private final static String VALUE = "value";

	private final static String PATH_CONTAINER = SPLIT + CONTAINER;

	private final static String PATH_SERVICES = PATH_CONTAINER + SPLIT
			+ SERVICES;

	private final static String PATH_SERVICE = PATH_SERVICES + SPLIT + SERVICE;

	private final static String PATH_NAME = PATH_SERVICE + SPLIT + NAME;

	private final static String PATH_PARAMETERS = PATH_SERVICE + SPLIT
			+ PARAMETERS;

	private final static String PATH_PARAMETER = PATH_PARAMETERS + SPLIT
			+ PARAMETER;

	private Container container = null;

	private Services services = null;

	private List<Service> serviceList = null;

	private Service service = null;

	private Map<String, String> parameters = null;

	@Override
	protected void doStartElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		if (PATH_CONTAINER.equals(getCurrentFullPath())) {
			container = new Container();
		}
		if (PATH_SERVICES.equals(getCurrentFullPath())) {
			services = new Services();
			serviceList = new ArrayList<Service>();
		}
		if (PATH_SERVICE.equals(getCurrentFullPath())) {
			service = new Service();
			service.setInterfase(atts.getValue(INTERFACE));
			service.setImplementation(atts.getValue(IMPLEMENTATION));
		}
		if (PATH_PARAMETERS.equals(getCurrentFullPath())) {
			parameters = new HashMap<String, String>();
		}
		if (PATH_PARAMETER.equals(getCurrentFullPath())) {
			parameters.put(atts.getValue(NAME), atts.getValue(VALUE));
		}
	}

	@Override
	protected void doEndElement(String uri, String localName, String qName)
			throws SAXException {
		if (PATH_PARAMETERS.equals(getCurrentFullPath())) {
			service.setParameters(parameters);
			parameters = null;
		}
		if (PATH_SERVICE.equals(getCurrentFullPath())) {
			serviceList.add(service);
			service = null;
		}
		if (PATH_SERVICES.equals(getCurrentFullPath())) {
			services.setServices(serviceList);
			serviceList = null;
		}
		if (PATH_CONTAINER.equals(getCurrentFullPath())) {
			container.setServices(services);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (PATH_NAME.equals(getCurrentFullPath())) {
			service.setName(new String(ch, start, length));
		}

		super.characters(ch, start, length);
	}

	public Container getContainer() {
		return this.container;
	}

	static class Service {
		private String interfase;

		private String implementation;

		private String name;

		private Map<String, String> parameters;

		public String getImplementation() {
			return implementation;
		}

		public void setImplementation(String implementation) {
			this.implementation = implementation;
		}

		public String getInterfase() {
			return interfase;
		}

		public void setInterfase(String interfase) {
			this.interfase = interfase;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Map<String, String> getParameters() {
			return parameters;
		}

		public void setParameters(Map<String, String> parameters) {
			this.parameters = parameters;
		}

	}

	static class Services {
		private List<Service> services = new ArrayList<Service>();

		public List<Service> getServices() {
			return services;
		}

		public void setServices(List<Service> services) {
			this.services = services;
		}

	}

	static class Container {
		private Services services;

		public Services getServices() {
			return services;
		}

		public void setServices(Services services) {
			this.services = services;
		}

	}
}
