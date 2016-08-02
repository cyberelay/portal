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

import javax.portlet.filter.FilterChain;

import org.cyberelay.portal.util.Assert;

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
class FilterChainDescription {
	private static final FilterChain FILTER_CHAIN_TAIL = new FilterChainTailNode();

	private String portletName;
	private FilterChain actionChain = FILTER_CHAIN_TAIL;
	private FilterChain eventChain = FILTER_CHAIN_TAIL;
	private FilterChain resourceChain = FILTER_CHAIN_TAIL;
	private FilterChain renderChain = FILTER_CHAIN_TAIL;

	public FilterChainDescription(String portletName, FilterDescription[] filters) {
		Assert.notNull(portletName, "portlet name cannot be null!");
		this.portletName = portletName;

		for (int i = (filters.length - 1); i > -1; i--) {
			applyFilter(filters[i]);
		}
	}

	private void applyFilter(FilterDescription filterDesc) {
		if (filterDesc.isApplicable(portletName)) {
			if (filterDesc.isActionFilter()) {
				actionChain = new FilterChainBodyNode(filterDesc.getFilter(), actionChain);
			}
			if (filterDesc.isEventFilter()) {
				eventChain = new FilterChainBodyNode(filterDesc.getFilter(), eventChain);
			}
			if (filterDesc.isResourceFilter()) {
				resourceChain = new FilterChainBodyNode(filterDesc.getFilter(), resourceChain);
			}
			if (filterDesc.isRenderFilter()) {
				renderChain = new FilterChainBodyNode(filterDesc.getFilter(), renderChain);
			}
		}
	}

	public String getPortletName() {
		return portletName;
	}

	public FilterChain getActionFilterChain() {
		return actionChain;
	}

	public FilterChain getEventFilterChain() {
		return eventChain;
	}

	public FilterChain getResourceFilterChain() {
		return resourceChain;
	}

	public FilterChain getRenderFilterChain() {
		return renderChain;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[" + portletName + "] Filter Chain = {\n");
		sb.append("---Action Chain = [").append(actionChain).append("]\n");
		sb.append("----Event Chain = [").append(eventChain).append("]\n");
		sb.append("-Resource Chain = [").append(resourceChain).append("]\n");
		sb.append("---Render Chain = [").append(renderChain).append("]\n");
		sb.append("}");
		return sb.toString();
	}
}
