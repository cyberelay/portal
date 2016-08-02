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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;

import org.cyberelay.portal.util.ObjectInstantiationException;
import org.cyberelay.portal.util.ReflectionUtil;
import org.cyberelay.portlet.descriptor.model.FilterMappingType;
import org.cyberelay.portlet.descriptor.model.FilterType;
import org.cyberelay.portlet.descriptor.model.InitParamType;
import org.cyberelay.portlet.descriptor.model.PortletAppType;
import org.cyberelay.portlet.descriptor.model.PortletNameType;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Feb 27, 2008
 * <li>Last Editor: $Author$
 * <li>Current Revision: $Revision$
 * <li>Last Update Time: $Date$
 * </ul>
 * 
 */
class FilterDescription {
	private static final String ACTION_PHASE = "ACTION_PHASE";
	private static final String EVENT_PHASE = "EVENT_PHASE";
	private static final String RENDER_PHASE = "RENDER_PHASE";
	private static final String RESOURCE_PHASE = "RESOURCE_PHASE";

	private PortletFilter filter;
	private boolean actionFilter = false;
	private boolean eventFilter = false;
	private boolean resourceFilter = false;
	private boolean renderFilter = false;
	private Set<String> applicablePortlets;

	public FilterDescription(FilterType filterType, PortletAppType app, PortletContext context)
			throws PortletException {
		this.filter = createFilter(filterType);
		this.filter.init(createConfig(filterType, context));

		for (String lifecycle : filterType.getLifecycle()) {
			if (ACTION_PHASE.equalsIgnoreCase(lifecycle)) {
				actionFilter = true;
			}
			if (EVENT_PHASE.equalsIgnoreCase(lifecycle)) {
				eventFilter = true;
			}
			if (RENDER_PHASE.equalsIgnoreCase(lifecycle)) {
				renderFilter = true;
			}
			if (RESOURCE_PHASE.equalsIgnoreCase(lifecycle)) {
				resourceFilter = true;
			}
		}

		this.applicablePortlets = new HashSet<String>();
		for (FilterMappingType mapping : app.getFilterMapping()) {
			if (filterType.getFilterName().equals(mapping.getFilterName())) {
				for (PortletNameType portletName : mapping.getPortletName()) {
					applicablePortlets.add(portletName.getValue());
				}
			}
		}
	}

	public PortletFilter getFilter() {
		return filter;
	}

	/**
	 * Check if this filter is applicable to the given portlet.
	 * 
	 * @param portletName
	 *            name of portlet.
	 * @return
	 */
	public boolean isApplicable(String portletName) {
		if (applicablePortlets == null || portletName == null) {
			return false;
		} else if (applicablePortlets.contains("*")) {
			return true;
		} else if (applicablePortlets.contains(portletName)) {
			return true;
		}
		// TODO FIXME
		return false;
	}

	public boolean isActionFilter() {
		return actionFilter;
	}

	public boolean isEventFilter() {
		return eventFilter;
	}

	public boolean isRenderFilter() {
		return renderFilter;
	}

	public boolean isResourceFilter() {
		return resourceFilter;
	}

	@Override
	public String toString() {
		return "filter class = "
				+ filter.getClass()
				+ ", action filter = "
				+ actionFilter
				+ ", event filter = "
				+ eventFilter
				+ ", render filter = "
				+ renderFilter
				+ ", resource filter = "
				+ resourceFilter;
	}

	/* ======================================================================= */

	private FilterConfig createConfig(FilterType filter, PortletContext context) {
		FilterConfigImpl result = new FilterConfigImpl(filter.getFilterName(), context);
		if (filter.getInitParam() != null) {
			for (InitParamType param : filter.getInitParam()) {
				result.setParameter(param.getName().getValue(), param.getValue().getValue());
			}
		}

		return result;
	}

	private PortletFilter createFilter(FilterType filter)
			throws PortletFilterInstantiationException {
		try {
			return (PortletFilter) ReflectionUtil.newInstance(filter.getFilterClass());
		} catch (ObjectInstantiationException e) {
			throw new PortletFilterInstantiationException(e);
		}
	}

	/* ======================================================================= */
	
	private static class FilterConfigImpl implements FilterConfig {
		private String name;
		private PortletContext context;
		private Map<String, String> parameters;

		public FilterConfigImpl(String name, PortletContext context) {
			this.name = name;
			this.context = context;
			this.parameters = new HashMap<String, String>();
		}

		public String getFilterName() {
			return name;
		}

		public String getInitParameter(String name) {
			return parameters.get(name);
		}

		public void setParameter(String name, String value) {
			parameters.put(name, value);
		}

		public Enumeration<String> getInitParameterNames() {
			return Collections.enumeration(parameters.keySet());
		}

		public PortletContext getPortletContext() {
			return context;
		}

	}

}
