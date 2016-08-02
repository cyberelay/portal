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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

import org.cyberelay.portal.util.ObjectInstantiationException;
import org.cyberelay.portal.util.ReflectionUtil;
import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portlet.descriptor.model.PortletPreferencesType;
import org.cyberelay.portlet.descriptor.model.PortletType;
import org.cyberelay.portlet.descriptor.model.PreferenceType;
import org.cyberelay.portlet.descriptor.model.ReadOnlyType;
import org.cyberelay.portlet.descriptor.model.ValueType;

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
class PortletPreferencesConfig extends AbstractConfig {
	private static final Logger LOG = LoggerFactory.getLogger(PortletPreferencesConfig.class);
	private PortletPreferences preferences;
	private PreferencesValidator validator;

	public PortletPreferencesConfig(PortletType portlet) throws PortletException {
		String portletName = portlet.getPortletName().getValue();

		preferences = new InitPortletPreferences(portlet);
		PortletPreferencesType prefsType = portlet.getPortletPreferences();
		String clazz = null;
		if (prefsType != null && prefsType.getPreferencesValidator() != null) {
			clazz = prefsType.getPreferencesValidator();
			validator = newValidator(clazz);
		}
		LOG.info("[" + portletName + "] preference validator = [" + clazz + "]");
	}

	private PreferencesValidator newValidator(String clazz) throws PortletException {
		try {
			return (PreferencesValidator) ReflectionUtil.newInstance(clazz);
		} catch (ObjectInstantiationException e) {
			throw new PortletException("Create validator [" + clazz + "] error!", e);
		}
	}

	public PortletPreferences getInitPreferences() {
		return preferences;
	}

	public PreferencesValidator getValidator() {
		return validator;
	}

	private static class InitPortletPreferences implements PortletPreferences {
		private Map<String, String[]> preferences;
		private Set<String> readOnlys;

		public InitPortletPreferences(PortletType portlet) {
			preferences = new HashMap<String, String[]>();
			readOnlys = new HashSet<String>();

			PortletPreferencesType prefsType = portlet.getPortletPreferences();
			if (prefsType != null) {
				for (PreferenceType pref : prefsType.getPreference()) {
					preferences.put(pref.getName().getValue(), getValues(pref));
					if (ReadOnlyType.TRUE.equals(pref.getReadOnly())) {
						readOnlys.add(pref.getName().getValue());
					}
				}
			}

			preferences = Collections.unmodifiableMap(preferences);
		}

		private String[] getValues(PreferenceType pref) {
			List<String> values = new ArrayList<String>();
			for (ValueType valueType : pref.getValue()) {
				values.add(valueType.getValue());
			}
			return values.toArray(new String[values.size()]);
		}

		public Map<String, String[]> getMap() {
			return preferences;
		}

		public Enumeration<String> getNames() {
			return Collections.enumeration(preferences.keySet());
		}

		public String getValue(String key, String def) {
			String[] values = preferences.get(key);
			return (values == null || values.length == 0) ? def : values[0];
		}

		public String[] getValues(String key, String[] def) {
			String[] values = preferences.get(key);
			return values == null ? def : values;
		}

		public boolean isReadOnly(String key) {
			return readOnlys != null && readOnlys.contains(key);
		}

		public void reset(String key) throws ReadOnlyException {
			LOG.warn("reset() has no effect upon initial preferences!");
		}

		/**
		 * Initial portlet preferences cannot be modified.
		 */
		public void setValue(String key, String value) throws ReadOnlyException {
			LOG.warn("setValue() has no effect upon initial preferences! No value changed.");
		}

		/**
		 * Initial portlet preferences cannot be modified.
		 */
		public void setValues(String key, String[] values) throws ReadOnlyException {
			LOG.warn("setValues() has no effect upon initial preferences! No value changed.");
		}

		/**
		 * Initial portlet preferences cannot be modified.
		 */
		public void store() throws IOException, ValidatorException {
			LOG.warn("store() has no effect upon initial preferences! No value changed.");
		}

	}
}
