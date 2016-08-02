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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.Portlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.portlet.WindowState;
import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import org.cyberelay.portal.util.ObjectInstantiationException;
import org.cyberelay.portal.util.ReflectionUtil;
import org.cyberelay.portlet.PortletConfigEx;
import org.cyberelay.portlet.descriptor.model.PortletAppType;
import org.cyberelay.portlet.descriptor.model.PortletType;
import org.cyberelay.portletcontainer.impl.PortletInstantiationException;

/**
 * @author Roger Tang
 * 
 */
class PortletFactory {
	private Map<String, PortletConfigEx> configs;
	private Map<String, Portlet> portlets;

	PortletFactory(PortletAppType app, PortletContext context) throws PortletException {
		configs = new HashMap<String, PortletConfigEx>();
		portlets = new HashMap<String, Portlet>();

		for (PortletType portletType : app.getPortlet()) {
			Portlet portlet = createPortlet(portletType);
			PortletConfigEx config = new PortletConfigImpl(portletType, app, context);
			portlet.init(config);
			configs.put(config.getPortletName(), config);
			portlets.put(config.getPortletName(), portlet);
		}
	}

	public PortletConfigEx getConfig(String portletName) {
		return configs.get(portletName);
	}

	public Portlet getPortlet(String portletName) {
		return portlets.get(portletName);
	}

	public Iterator<String> getPortletNames() {
		return portlets.keySet().iterator();
	}

	public void destroy() {
		for (Portlet portlet : portlets.values()) {
			portlet.destroy();
		}
	}

	/* ================================================================================= */

	private Portlet createPortlet(PortletType portlet) throws PortletInstantiationException {
		String clazz = portlet.getPortletClass();
		try {
			return (Portlet) ReflectionUtil.newInstance(clazz);
		} catch (ObjectInstantiationException e) {
			throw new PortletInstantiationException(e);
		}
	}

	/* ================================================================================= */

	private static class PortletConfigImpl implements PortletConfigEx {
		private PortletContext context;
		private String defaultNamespace;
		private String portletName;

		private CacheSupportConfig cacheSupportConfig;
		private PublicParameterConfig publicParameterConfig;
		private LocaleSupportConfig localeSupportConfig;
		private MimeTypeSupportConfig mimeTypeSupportConfig;
		private ResourceBundleConfig bundleConfig;
		private EventSupportConfig eventSupportConfig;
		private RuntimeOptionConfig optionConfig;
		private PortletPreferencesConfig preferencesConfig;
		private InitParameterConfig initParameterConfig;
		private SecuritySupportConfig securitySupportConfig;

		public PortletConfigImpl(PortletType portlet, PortletAppType app, PortletContext context)
				throws PortletException {
			this.context = context;

			portletName = portlet.getPortletName().getValue();
			defaultNamespace = app.getDefaultNamespace();
			if (defaultNamespace == null) {
				defaultNamespace = XMLConstants.NULL_NS_URI;
			}

			bundleConfig = new ResourceBundleConfig(portlet);
			optionConfig = new RuntimeOptionConfig(portlet, app);
			mimeTypeSupportConfig = new MimeTypeSupportConfig(portlet, app);
			localeSupportConfig = new LocaleSupportConfig(portlet);
			eventSupportConfig = new EventSupportConfig(portlet, app);
			publicParameterConfig = new PublicParameterConfig(portlet, app);
			preferencesConfig = new PortletPreferencesConfig(portlet);
			cacheSupportConfig = new CacheSupportConfig(portlet);
			initParameterConfig = new InitParameterConfig(portlet);
			securitySupportConfig = new SecuritySupportConfig(portlet);
		}

		public PortletContext getPortletContext() {
			return context;
		}

		public String getPortletName() {
			return portletName;
		}

		public String getDefaultNamespace() {
			return defaultNamespace;
		}

		public String getInitParameter(String name) {
			return initParameterConfig.getParameter(name);
		}

		public Map<String, String[]> getContainerRuntimeOptions() {
			return optionConfig.getRuntimeOptions();
		}

		public Enumeration<String> getInitParameterNames() {
			return initParameterConfig.getParameterNames();
		}

		public Enumeration<QName> getProcessingEventQNames() {
			return eventSupportConfig.getProcessingEventQNames();
		}

		public Enumeration<String> getPublicRenderParameterNames() {
			return publicParameterConfig.getParameterNames();
		}

		public Enumeration<QName> getPublishingEventQNames() {
			return eventSupportConfig.getPublishingEventQNames();
		}

		public ResourceBundle getResourceBundle(Locale locale) {
			return bundleConfig.getBundle(locale);
		}

		public Enumeration<Locale> getSupportedLocales() {
			return localeSupportConfig.getSupportedLocales();
		}

		public boolean isSupportedLocale(Locale locale) {
			return localeSupportConfig.isLocaleSupported(locale);
		}

		public Set<String> getAccessibleRoles() {
			return securitySupportConfig.getAccessibleRoles();
		}

		public String getCacheScope() {
			return cacheSupportConfig.getCacheScope();
		}

		public int getDurationOfExpiration() {
			return cacheSupportConfig.getDurationOfExpiration();
		}

		public PortletPreferences getInitPortletPreferences() {
			return preferencesConfig.getInitPreferences();
		}

		public PreferencesValidator getPreferenceValidator() {
			return preferencesConfig.getValidator();
		}

		public Enumeration<PortletMode> getSupportedPortletModes() {
			return mimeTypeSupportConfig.getSupportedPortletModes();
		}

		public Enumeration<String> getSupportedMimeTypes() {
			return mimeTypeSupportConfig.getSupportedMimeTypes();
		}

		public Enumeration<PortletMode> getSupportedPortletModes(String mimeType) {
			return mimeTypeSupportConfig.getSupportedPortletModes(mimeType);
		}

		public Enumeration<WindowState> getSupportedWindowStates(String mimeType) {
			return mimeTypeSupportConfig.getSupportedWindowStates(mimeType);
		}

		public boolean isSupportedPortletMode(PortletMode mode, String mimeType) {
			return mimeTypeSupportConfig.isSupportedPortletMode(mode, mimeType);
		}

		public boolean isSupportedWindowState(WindowState state, String mimeType) {
			return mimeTypeSupportConfig.isSupportedWindowState(state, mimeType);
		}

		public Enumeration<WindowState> getSupportedWindowStates() {
			return mimeTypeSupportConfig.getSupportedWindowStates();
		}

		public boolean isSupportedPortletMode(PortletMode mode) {
			return mimeTypeSupportConfig.isSupportedPortletMode(mode);
		}

		public boolean isProcessableEventName(QName eventName) {
			return eventSupportConfig.isProcessableEventName(eventName);
		}

		public boolean isPublishableEventName(QName eventName) {
			return eventSupportConfig.isPublishableEventName(eventName);
		}

		public boolean isSupportedPublicRenderParameter(String name) {
			return publicParameterConfig.isSupportedParameter(name);
		}

		public boolean isSupportedWindowState(WindowState state) {
			return mimeTypeSupportConfig.isSupportedWindowState(state);
		}

		public boolean isSupportedMimeType(String mimeType) {
			return mimeTypeSupportConfig.isSupportedMimeType(mimeType);
		}
	}
}
