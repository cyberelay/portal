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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.filter.FilterChain;

import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portlet.descriptor.model.FilterType;
import org.cyberelay.portlet.descriptor.model.PortletAppType;
import org.cyberelay.portlet.descriptor.model.PortletType;

/**
 * @author Roger Tang
 * 
 */
class FilterChainFactory {
	private static Logger LOG = LoggerFactory.getLogger(FilterChainFactory.class);

	private FilterDescription[] filters;
	private Map<String, FilterChainDescription> chains;

	FilterChainFactory(PortletAppType app, PortletContext context) throws PortletException {
		LOG.info("Parsing portlet filter chain...");
		initFilters(app, context);
		initFilterChains(app);
	}

	public FilterChain getActionFilterChain(String portletName) {
		return chains.get(portletName).getActionFilterChain();
	}

	public FilterChain getEventFilterChain(String portletName) {
		return chains.get(portletName).getEventFilterChain();
	}

	public FilterChain getRenderFilterChain(String portletName) {
		return chains.get(portletName).getRenderFilterChain();
	}

	public FilterChain getResourceFilterChain(String portletName) {
		return chains.get(portletName).getResourceFilterChain();
	}

	public void destroy() {
		for (FilterDescription filter : filters) {
			filter.getFilter().destroy();
		}
	}

	/* ========================================================================= */

	private String[] getPortletNames(PortletAppType portletApp) {
		List<String> result = new ArrayList<String>();
		for (PortletType portlet : portletApp.getPortlet()) {
			result.add(portlet.getPortletName().getValue());
		}
		return result.toArray(new String[result.size()]);
	}

	private void initFilters(PortletAppType app, PortletContext context) throws PortletException {
		List<FilterDescription> filterList = new ArrayList<FilterDescription>();
		for (FilterType filter : app.getFilter()) {
			FilterDescription filterDesc = new FilterDescription(filter, app, context);
			filterList.add(filterDesc);
			LOG.info("Defined portlet filter -> [" + filterDesc + "]");
		}
		filters = filterList.toArray(new FilterDescription[filterList.size()]);
		LOG.info("Defined portlet filter = [" + filters.length + "]");
	}

	private void initFilterChains(PortletAppType app) {
		this.chains = new HashMap<String, FilterChainDescription>();
		String[] portletNames = getPortletNames(app);
		for (String portletName : portletNames) {
			FilterChainDescription chain = new FilterChainDescription(portletName, filters);
			chains.put(portletName, chain);
			LOG.info("[" + portletName + "] defined Filter Chain: \n" + chain);
		}
	}
}
