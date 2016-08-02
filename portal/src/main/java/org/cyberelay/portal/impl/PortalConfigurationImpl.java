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

package org.cyberelay.portal.impl;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.cyberelay.portal.PortalConfiguration;
import org.cyberelay.portal.PortalRuntimeException;
import org.cyberelay.portal.util.PropertiesUtil;

/**
 * @author Roger Tang
 * 
 */
public class PortalConfigurationImpl implements PortalConfiguration {
	/* Default configuration property file. */
	private static String DEFAULT_CONFIG_PROP = "org/cyberelay/portal/portal.properties";

	private static String CONFIG_PROPERTIES = "cyberelay-portal.properties";

	private static String CYBERELAY_HOME = "CYBERELAY_HOME";

	private static Properties DEFAULT_CONFIG;

	private static final Set<String> TRUE_SET = new HashSet<String>();

	private Properties configuration;

	private String homeDirectory;

	static {
		try {
			DEFAULT_CONFIG = PropertiesUtil.resourceToProperties(DEFAULT_CONFIG_PROP);
			TRUE_SET.add("true");
			TRUE_SET.add("t");
			TRUE_SET.add("yes");
			TRUE_SET.add("y");
		} catch (Exception e) {
			DEFAULT_CONFIG = new Properties();
		}
	}

	public PortalConfigurationImpl() {
		refresh();
	}

	public String getHomeDirectory() {
		return homeDirectory;
	}

	public String getValue(String key) {
		String result = configuration.getProperty(key);
		return result == null ? DEFAULT_CONFIG.getProperty(key) : result;
	}

	public boolean getValueAsBoolean(String key) {
		String value = getValue(key);
		return value != null && TRUE_SET.contains(value.toLowerCase());
	}

	public String[] getValues(String key) {
		Properties prop = DEFAULT_CONFIG;
		if (configuration.containsKey(key + "[0]")) {
			prop = configuration;
		}

		List<String> values = new ArrayList<String>();
		for (int i = 0; true; i++) {
			String value = prop.getProperty(key + "[" + i + "]");
			if (value == null) {
				break;
			}
			values.add(value);
		}
		return values.toArray(new String[values.size()]);
	}

	public Iterator<String> getKeys() {
		Set result = new HashSet();
		result.addAll(DEFAULT_CONFIG.keySet());
		result.addAll(configuration.keySet());

		return result.iterator();
	}

	public void refresh() {
		homeDirectory = resolveHomeDirectory();
		configuration = new Properties();
		configuration.putAll(loadProperties());
	}

	private Properties loadProperties() {
		Properties result = PropertiesUtil.resourceToProperties(CONFIG_PROPERTIES);
		return result == null ? new Properties() : result;
	}

	private String resolveHomeDirectory() {
		String dir = System.getProperty(CYBERELAY_HOME);
		if (dir != null) {
			return dir;
		}

		URL url = Thread.currentThread().getContextClassLoader().getResource(CONFIG_PROPERTIES);
		File configFile = new File(url.getPath());
		if (configFile.exists() && !configFile.isDirectory()) {
			return configFile.getParent();
		}

		throw new PortalRuntimeException("Cannot resolve Cyberelay Portal Server Home Directory!"
				+ "Please either set ["
				+ CYBERELAY_HOME
				+ "] system property, or add cyberelay home directory into classpath.");
	}

}
