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
import java.util.Locale;
import java.util.Set;

import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portlet.descriptor.model.PortletType;
import org.cyberelay.portlet.descriptor.model.SupportedLocaleType;

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
class LocaleSupportConfig  extends AbstractConfig {
	private static Logger LOG = LoggerFactory.getLogger(LocaleSupportConfig.class);
	private static final Locale DEFAULT = new Locale("en");
	private Set<Locale> supportedLocales;

	public LocaleSupportConfig(PortletType portlet) {
		String portletName = portlet.getPortletName().getValue();
		supportedLocales = new HashSet<Locale>();
		
		/* always support default locale */
		supportedLocales.add(DEFAULT);
		LOG.info("["+ portletName + "] supported locale -> [" + DEFAULT + "]");
		
		for (SupportedLocaleType locale : portlet.getSupportedLocale()) {
			supportedLocales.add(new Locale(locale.getValue()));
			LOG.info("["+ portletName + "] supported locale -> [" + locale.getValue() + "]");
		}
		LOG.info("["+ portletName + "] supported locale = [" + supportedLocales.size() + "]");

		supportedLocales = Collections.unmodifiableSet(supportedLocales);
	}

	public Enumeration<Locale> getSupportedLocales() {
		return Collections.enumeration(supportedLocales);
	}

	public boolean isLocaleSupported(Locale locale) {
		return supportedLocales.contains(locale);
	}
}
