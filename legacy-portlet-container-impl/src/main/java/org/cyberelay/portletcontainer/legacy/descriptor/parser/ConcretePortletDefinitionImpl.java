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
package org.cyberelay.portletcontainer.legacy.descriptor.parser;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.cyberelay.portletcontainer.legacy.descriptor.ConcretePortletDefinition;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jan 18, 2008
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
class ConcretePortletDefinitionImpl implements ConcretePortletDefinition {
	private String uniqueName;
	private String abstractPortletName;
	private String concretePortletApplicationName;
	private Map<String, String> parameters;
	private Map localeDataMap;
	private Locale defaultLocale;

	public ConcretePortletDefinitionImpl(String name) {
		this.uniqueName = name;

		this.localeDataMap = new HashMap();
		this.parameters = new HashMap<String, String>();
	}

	public String getAbstractPortletName() {
		return abstractPortletName;
	}

	public String getConcretePortletApplicationName() {
		return concretePortletApplicationName;
	}

	public String getParameter(String name) {
		return (String) parameters.get(name);
	}

	public Locale[] getSupportedLocales() {
		return (Locale[]) localeDataMap.keySet().toArray(new Locale[localeDataMap.size()]);
	}

	public Locale getDefaultLocale() {
		return defaultLocale;
	}

	public String getShortTitle(Locale locale) {
		LocaleData info = getLocaleData(locale, false);
		return info == null ? null : info.getShortTitle();
	}

	public Map getParameters() {
		return parameters;
	}

	public void setAbstractPortletName(String abstractPortletName) {
		this.abstractPortletName = abstractPortletName;
	}

	public void setConcretePortletApplicationName(String concretePortletApplicationName) {
		this.concretePortletApplicationName = concretePortletApplicationName;
	}

	public void setParameter(String name, String value) {
		if (name != null) {
			parameters.put(name, value);
		}
	}

	public void setDefaultLocale(Locale defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

	public void setShortTitle(Locale locale, String shortTitle) {
		getLocaleData(locale, true).setShortTitle(shortTitle);
	}

	public void setDescription(Locale locale, String description) {
		getLocaleData(locale, true).setDescription(description);
	}

	public String getTitle(Locale locale) {
		LocaleData info = getLocaleData(locale);
		return info == null ? null : info.getTitle();
	}

	public String getTitle() {
		return getTitle(getDefaultLocale());
	}

	public String getShortTitle() {
		return getShortTitle(getDefaultLocale());
	}

	public String getDescription() {
		return getDescription(getDefaultLocale());
	}

	public String getDescription(Locale locale) {
		LocaleData info = getLocaleData(locale, false);
		return info == null ? null : info.getDescription();
	}

	public void setTitle(Locale locale, String title) {
		getLocaleData(locale, true).setTitle(title);
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public String toString() {
		return "unique name = ["
				+ uniqueName
				+ "], abstract portlet name = ["
				+ abstractPortletName
				+ "], concrete portlet application = ["
				+ concretePortletApplicationName
				+ "], parameter size = ["
				+ parameters.size()
				+ "]";
	}

	public LocaleData getLocaleData(Locale locale) {
		return getLocaleData(locale, false);
	}

	public LocaleData[] getLocaleData() {
		return (LocaleData[]) localeDataMap.values().toArray(new LocaleData[localeDataMap.size()]);
	}

	public LocaleData getLocaleData(Locale locale, boolean create) {
		LocaleData result = (LocaleData) localeDataMap.get(locale);

		if (result == null && create) {
			result = new LocaleData(locale);
			localeDataMap.put(locale, result);
		}

		return result;
	}

}
