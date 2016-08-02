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
import java.util.Map;

import javax.portlet.PortletException;
import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portlet.descriptor.model.EventDefinitionReferenceType;
import org.cyberelay.portlet.descriptor.model.EventDefinitionType;
import org.cyberelay.portlet.descriptor.model.PortletAppType;
import org.cyberelay.portlet.descriptor.model.PortletType;
import org.cyberelay.portletcontainer.PortletContainerInfo;

/**
 * @author Roger Tang
 * 
 */
class EventSupportConfig extends AbstractConfig {
	private static final Logger LOG = LoggerFactory.getLogger(EventSupportConfig.class);

	/* name-type pair of event definition. */
	private Map<QName, String> processableEventQNames;
	private Map<QName, String> publishableEventQNames;

	public EventSupportConfig(PortletType portlet, PortletAppType app) throws PortletException {
		String portletName = getPortletName(portlet);
		String defaultNamespace = getDefaultNamespace(app);

		processableEventQNames = new HashMap<QName, String>();
		for (EventDefinitionReferenceType ref : portlet.getSupportedProcessingEvent()) {
			QName eventName = getQName(ref, defaultNamespace);
			String eventType = getEventType(eventName, app, defaultNamespace);
			processableEventQNames.put(eventName, eventType);

			LOG.info("["
					+ portletName
					+ "] processing event: [QName = "
					+ eventName
					+ ", type = "
					+ eventType
					+ "]");
		}
		LOG.info("["
				+ portletName
				+ "] processing event count = ["
				+ processableEventQNames.size()
				+ "]");

		publishableEventQNames = new HashMap<QName, String>();
		for (EventDefinitionReferenceType ref : portlet.getSupportedPublishingEvent()) {
			QName eventName = getQName(ref, defaultNamespace);
			if (PortletContainerInfo.isContainerEventName(eventName)) {
				/* portlet should not publish container event. */
				continue;
			}
			String eventType = getEventType(eventName, app, defaultNamespace);
			publishableEventQNames.put(eventName, eventType);

			LOG.info("["
					+ portletName
					+ "] publishing event: [QName = "
					+ eventName
					+ ", type = "
					+ eventType
					+ "]");
		}
		LOG.info("["
				+ portletName
				+ "] publishing event count = ["
				+ publishableEventQNames.size()
				+ "]");
	}

	public Enumeration<QName> getProcessingEventQNames() {
		return Collections.enumeration(processableEventQNames.keySet());
	}

	public Enumeration<QName> getPublishingEventQNames() {
		return Collections.enumeration(publishableEventQNames.keySet());
	}

	public boolean isProcessableEventName(QName eventName) {
		return processableEventQNames.containsKey(eventName);
	}

	public boolean isPublishableEventName(QName eventName) {
		return publishableEventQNames.containsKey(eventName);
	}
	
	/* ================================================================================== */

	private String getEventType(QName name, PortletAppType app, String defaultNamespace)
			throws PortletEventDefinitionException {
		if (PortletContainerInfo.isContainerEventName(name)) {
			return PortletContainerInfo.getContainerEventType(name);
		}

		for (EventDefinitionType eventDef : app.getEventDefinition()) {
			if (name.equals(getQName(eventDef, defaultNamespace))) {
				return eventDef.getValueType();
			}
		}

		throw new PortletEventDefinitionException("No such event defined! name = [" + name + "]");
	}

	private QName getQName(EventDefinitionReferenceType ref, String defaultNamespace) {
		QName name = ref.getQname();
		return (name == null) ? new QName(defaultNamespace, ref.getName()) : name;
	}

	private QName getQName(EventDefinitionType eventDef, String defaultNamespace) {
		QName name = eventDef.getQname();
		return (name == null) ? new QName(defaultNamespace, eventDef.getName()) : name;
	}
}
