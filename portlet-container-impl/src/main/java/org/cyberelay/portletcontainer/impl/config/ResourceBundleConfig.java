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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portlet.descriptor.model.PortletInfoType;
import org.cyberelay.portlet.descriptor.model.PortletType;

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
class ResourceBundleConfig extends AbstractConfig {
	private static final Logger LOG = LoggerFactory.getLogger(ResourceBundleConfig.class);
	private static final Object[][] EMPTY_CONTENT = new Object[0][0];
	private static final String TITLE_KEY = "javax.portlet.title";
	private static final String SHORT_TITLE_KEY = "javax.portlet.short-title";
	private static final String KEYWORDS_KEY = "javax.portlet.keywords";

	private Map<Locale, ResourceBundle> bundles;
	private ResourceBundle defaultBundle;
	private String bundleName;

	public ResourceBundleConfig(PortletType portletType) {
		if (portletType.getResourceBundle() != null) {
			bundleName = portletType.getResourceBundle().getValue();
		}

		if (LOG.isInfoEnabled()) {
			String portletName = portletType.getPortletName().getValue();
			LOG.info("[" + portletName + "] defined resource bundle = [" + bundleName + "]");
		}

		bundles = new HashMap<Locale, ResourceBundle>();
		defaultBundle = new InlinePortletResourceBundle(portletType.getPortletInfo());
	}

	public ResourceBundle getBundle(Locale locale) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Resource Bundle [" + bundleName + " : " + locale + "] requested.");
		}

		if (bundles.containsKey(locale)) {
			return bundles.get(locale);
		}

		ResourceBundle bundle = null;
		try {
			if (bundleName != null) {
				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				bundle = ResourceBundle.getBundle(bundleName, locale, loader);
				bundle = new MergedPortletResourceBundle(defaultBundle, bundle);
			} else {
				bundle = defaultBundle;
			}
		} catch (MissingResourceException mre) {
			if (LOG.isErrorEnabled()) {
				LOG.error("Error to load resource bundle [" + bundleName + "]");
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("Using default bundle for locale (" + locale + ").");
			}
			bundle = defaultBundle;
		}
		bundles.put(locale, bundle);
		return bundle;
	}

	private static class MergedPortletResourceBundle extends ResourceBundle {
		private Map<String, Object> contents = new HashMap<String, Object>();

		public MergedPortletResourceBundle(ResourceBundle inlineBundle, ResourceBundle definedBundle) {
			putAll(inlineBundle);
			putAll(definedBundle);
		}

		protected Object handleGetObject(String key) {
			return contents.get(key);
		}

		public Enumeration<String> getKeys() {
			return Collections.enumeration(contents.keySet());
		}

		private void putAll(ResourceBundle bundle) {
			Enumeration<String> e = bundle.getKeys();
			while (e.hasMoreElements()) {
				String key = e.nextElement();
				contents.put(key, bundle.getString(key));
			}
		}
	}

	private static class InlinePortletResourceBundle extends ListResourceBundle {
		private Object[][] contents;

		public InlinePortletResourceBundle(PortletInfoType info) {
			if (info == null) {
				contents = EMPTY_CONTENT;
			} else {
				List<Object[]> arrayList = new ArrayList<Object[]>();
				if (info.getShortTitle() != null) {
					arrayList.add(new Object[] { TITLE_KEY, info.getShortTitle().getValue() });
				}
				if (info.getShortTitle() != null) {
					arrayList.add(new Object[] { SHORT_TITLE_KEY, info.getShortTitle().getValue() });
				}
				if (info.getKeywords() != null) {
					arrayList.add(new Object[] { KEYWORDS_KEY, info.getKeywords().getValue() });
				}
				contents = arrayList.toArray(new Object[arrayList.size()][]);
			}
		}

		protected Object[][] getContents() {
			return contents;
		}
	}

}
