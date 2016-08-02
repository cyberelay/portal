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
import java.util.List;

import javax.portlet.PortletURLGenerationListener;

import org.cyberelay.portal.util.ObjectInstantiationException;
import org.cyberelay.portal.util.ReflectionUtil;
import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portlet.descriptor.model.ListenerType;
import org.cyberelay.portlet.descriptor.model.PortletAppType;

/**
 * @author Roger Tang
 * 
 */
class ListenerConfig  extends AbstractConfig {
	private static Logger LOG = LoggerFactory.getLogger(ListenerConfig.class);

	private List<PortletURLGenerationListener> listeners;

	public ListenerConfig(PortletAppType app) throws ListenerInstantiationException {
		String appId = app.getId();
		listeners = new ArrayList<PortletURLGenerationListener>();

		for (ListenerType listener : app.getListener()) {
			listeners.add(toListener(listener.getListenerClass()));
		}

		/* convert list to unmodifiable. */
		listeners = Collections.unmodifiableList(listeners);
		LOG.info("[" + appId + "] defined portlet URL listeners = [" + listeners.size() + "]");
	}

	private PortletURLGenerationListener toListener(String clazz)
			throws ListenerInstantiationException {
		try {
			LOG.info("Initializing PortletURLGenerationListener [" + clazz + "]...");
			return (PortletURLGenerationListener) ReflectionUtil.newInstance(clazz);
		} catch (ObjectInstantiationException e) {
			throw new ListenerInstantiationException(e);
		}
	}

	public List<PortletURLGenerationListener> getListeners() {
		return listeners;
	}
}
