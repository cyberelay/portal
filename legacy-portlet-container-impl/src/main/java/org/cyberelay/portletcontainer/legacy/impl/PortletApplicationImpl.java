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
package org.cyberelay.portletcontainer.legacy.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jetspeed.portlet.Portlet;
import org.apache.jetspeed.portlet.PortletApplicationSettings;
import org.apache.jetspeed.portlet.PortletConfig;
import org.apache.jetspeed.portlet.PortletContext;
import org.apache.jetspeed.portlet.PortletSettings;
import org.apache.jetspeed.portlet.UnavailableException;
import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portletcontainer.legacy.Constants;
import org.cyberelay.portletcontainer.legacy.PortletApplication;
import org.cyberelay.portletcontainer.legacy.PortletMethod;
import org.cyberelay.portletcontainer.legacy.PortletWindowEx;
import org.cyberelay.portletcontainer.legacy.descriptor.AbstractPortletDefinition;
import org.cyberelay.portletcontainer.legacy.descriptor.ConcretePortletApplicationDefinition;
import org.cyberelay.portletcontainer.legacy.descriptor.ConcretePortletDefinition;
import org.cyberelay.portletcontainer.legacy.descriptor.PortletApplicationDescriptor;
import org.cyberelay.portletcontainer.legacy.descriptor.parser.PortletApplicationDescriptorParser;
import org.cyberelay.portletcontainer.legacy.impl.invoker.PortletInvoker;
import org.xml.sax.SAXException;

/**
 * 
 * @author Roger Tang
 * 
 */
public class PortletApplicationImpl implements PortletApplication, Constants {
	private static final Logger LOG = LoggerFactory.getLogger(PortletApplicationImpl.class);

	private ServletContext servletContext;
	private PortletContext portletContext;

	private PortletApplicationDescriptor descriptor;

	private Map<String, PortletConfig> portletConfigMap;
	private Map<String, PortletSettings> portletSettingsMap;
	private Map<String, PortletApplicationSettings> portletApplicationSettingsMap;

	private Map<String, Portlet> abstractPortletInstanceMap;
	private Map<String, Portlet> concretePortletInstanceMap;

	public PortletApplicationImpl(ServletContext context) {
		this.servletContext = context;
		this.portletContext = new PortletContextImpl(context);

		try {
			this.descriptor = new PortletApplicationDescriptorParser(context).parse();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		portletConfigMap = new HashMap<String, PortletConfig>();
		portletSettingsMap = new HashMap<String, PortletSettings>();
		portletApplicationSettingsMap = new HashMap<String, PortletApplicationSettings>();

		abstractPortletInstanceMap = new HashMap<String, Portlet>();
		concretePortletInstanceMap = new HashMap<String, Portlet>();
	}

	public PortletApplicationDescriptor getDescriptor() {
		return descriptor;
	}

	/**
	 * @see org.cyberelay.portletcontainer.legacy.PortletApplication#getPortletConfig(java.lang.String)
	 */
	public synchronized PortletConfig getPortletConfig(String abstractPortletName) {
		PortletConfig result = portletConfigMap.get(abstractPortletName);
		if (result == null) {
			AbstractPortletDefinition definition =
					descriptor.getAbstractPortletDefinition(abstractPortletName);
			if (definition == null) {
				// TODO
				throw new RuntimeException(
						"No such abstract portlet defined. Abstract portlet name = ["
								+ abstractPortletName
								+ "]");
			}
			result = new PortletConfigImpl(this, definition);
			portletConfigMap.put(result.getName(), result);
		}
		return result;
	}

	/**
	 * @see org.cyberelay.portletcontainer.legacy.PortletApplication#getPortletSettings(java.lang.String)
	 */
	public PortletSettings getPortletSettings(String concretePortletName) {
		PortletSettings result = portletSettingsMap.get(concretePortletName);
		if (result == null) {
			ConcretePortletDefinition definition =
					descriptor.getConcretePortletDefinition(concretePortletName);
			if (definition == null) {
				throw new RuntimeException(
						"No such concrete portlet defined. Concrete portlet name = ["
								+ concretePortletName
								+ "]");
			}
			result = new PortletSettingsImpl(this, definition);
			portletSettingsMap.put(definition.getUniqueName(), result);
		}
		return result;
	}

	/**
	 * @see org.cyberelay.portletcontainer.legacy.PortletApplication#getPortletApplicationSettings()
	 */
	public synchronized PortletApplicationSettings getPortletApplicationSettings(
			String portletApplicationName) {
		PortletApplicationSettings result =
				portletApplicationSettingsMap.get(portletApplicationName);
		if (result == null) {
			ConcretePortletApplicationDefinition definition =
					descriptor.getConcretePortletApplicationDefinition(portletApplicationName);
			if (definition == null) {
				throw new RuntimeException(
						"No such portlet application defined. Portlet application name = ["
								+ portletApplicationName
								+ "]");
			}
			result = new PortletApplicationSettingsImpl(this, definition);
			portletApplicationSettingsMap.put(definition.getUniqueName(), result);
		}
		return result;
	}

	/**
	 * @throws UnavailableException
	 * @see org.cyberelay.portletcontainer.legacy.PortletApplication#getPortletInstance(org.apache.jetspeed.portlet.extension.PortletWindowDefinition)
	 */
	public synchronized Portlet getPortletInstance(String concretePortletName)
			throws UnavailableException {
		return getConcretePortletInstance(concretePortletName);
	}

	public synchronized Portlet getPortletInstance(PortletWindowEx portlet)
			throws UnavailableException {
		return getPortletInstance(portlet.getPortletName());
	}

	private Portlet getConcretePortletInstance(String concretePortletName)
			throws UnavailableException {
		ConcretePortletDefinition concretePortletDefinition =
				descriptor.getConcretePortletDefinition(concretePortletName);
		Portlet result = concretePortletInstanceMap.get(concretePortletDefinition.getUniqueName());
		if (result == null) {
			result = getAbstractPortletInstance(concretePortletName);
			result.initConcrete(getPortletSettings(concretePortletDefinition.getUniqueName()));
			concretePortletInstanceMap.put(concretePortletDefinition.getUniqueName(), result);
		}

		return result;
	}

	private Portlet getAbstractPortletInstance(String concretePortletName)
			throws UnavailableException {
		ConcretePortletDefinition concretePortletDefinition =
				descriptor.getConcretePortletDefinition(concretePortletName);

		if (LOG.isDebugEnabled())
			LOG.debug("concrete portlet definition = [" + concretePortletDefinition + "]");

		Portlet result =
				abstractPortletInstanceMap.get(concretePortletDefinition.getAbstractPortletName());
		if (result == null) {
			try {
				AbstractPortletDefinition abstractPortletDefinition =
						descriptor.getAbstractPortletDefinition(concretePortletDefinition.getAbstractPortletName());

				if (LOG.isDebugEnabled())
					LOG.debug("abstract portlet definition = [" + abstractPortletDefinition + "]");

				result = (Portlet) abstractPortletDefinition.getPortletClass().newInstance();
				result.init(getPortletConfig(abstractPortletDefinition.getUniqueName()));
				abstractPortletInstanceMap.put(abstractPortletDefinition.getUniqueName(), result);
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	/**
	 * @see org.cyberelay.portletcontainer.legacy.PortletApplication#getContextPath()
	 */
	public String getContextPath() {
		return servletContext.getServletContextName();
	}

	/**
	 * @see org.cyberelay.portletcontainer.legacy.PortletApplication#getServletContext()
	 */
	public ServletContext getServletContext() {
		return servletContext;
	}

	/**
	 * @see org.cyberelay.portletcontainer.legacy.PortletApplication#getPortletContext()
	 */
	public PortletContext getPortletContext() {
		return portletContext;
	}

	public void invokePortlet(PortletMethod method, PortletWindowEx window,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Invoking legacy portlet. method = ["
					+ method
					+ "], portlet window = ["
					+ window
					+ "]");
		}
		
		PortletInvoker.invoke(method, window, request, response);
	}

	/**
	 * @see org.cyberelay.portletcontainer.legacy.PortletApplication#destroy()
	 */
	public void destroy() {
		/* destroy all concrete portlet instances. */
		Iterator<String> concretePortletNames = concretePortletInstanceMap.keySet().iterator();
		while (concretePortletNames.hasNext()) {
			String name = concretePortletNames.next();
			Portlet concretePortlet = concretePortletInstanceMap.get(name);
			concretePortlet.destroyConcrete(getPortletSettings(name));
		}
		concretePortletInstanceMap.clear();

		/* destroy all abstract portlet instances. */
		Iterator<String> abstractPortletNames = abstractPortletInstanceMap.keySet().iterator();
		while (abstractPortletNames.hasNext()) {
			String name = abstractPortletNames.next();
			Portlet abstractPortlet = abstractPortletInstanceMap.get(name);
			abstractPortlet.destroy(getPortletConfig(name));
		}
		abstractPortletInstanceMap.clear();
	}

	public PortletConfig getPortletConfig(PortletWindowEx window) {
		return getPortletConfig(descriptor.getConcretePortletDefinition(window.getPortletName()).getAbstractPortletName());
	}
}
