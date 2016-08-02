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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.jetspeed.portlet.Client;
import org.apache.jetspeed.portlet.Portlet.Mode;
import org.apache.jetspeed.portlet.PortletWindow.State;
import org.cyberelay.portletcontainer.legacy.descriptor.AbstractPortletDefinition;

/**
 * @author Roger Tang
 * 
 *
 */
class AbstractPortletDefinitionImpl implements AbstractPortletDefinition {
	private String uniqueName;
	private String abstractPortletApplicationName;
	private String portletClassName;
	private Class portletClass;
	private Map parameters;
	private Map markupModeSetMap;
	private Set allowedStates;

	public AbstractPortletDefinitionImpl(String name) {
		this.uniqueName = name;
		this.parameters = new HashMap();
		this.markupModeSetMap = new HashMap();
		this.allowedStates = new HashSet();
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public Class getPortletClass() {
		if (portletClass == null) {
			try {
				portletClass = Thread.currentThread().getContextClassLoader().loadClass(
						portletClassName);
			} catch (ClassNotFoundException e) {
				System.out.println("portlet class name = [" + portletClassName + "]");
				e.printStackTrace();
			} catch (NoClassDefFoundError e) {
				System.out.println("NoClassDefFoundError. portlet class name = ["
						+ portletClassName
						+ "]");
				throw e;
			}
		}
		return portletClass;
	}

	public String getPortletClassName() {
		return portletClassName;
	}

	public void setAbstractPortletApplicationName(String abstractPortletApplicationName) {
		this.abstractPortletApplicationName = abstractPortletApplicationName;
	}

	public void setPortletClass(String className) {
		portletClassName = className;
	}

	public String getAbstractPortetApplicationName() {
		return abstractPortletApplicationName;
	}

	public String getParameter(String name) {
		return (String) parameters.get(name);
	}

	public void setParameter(String name, String value) {
		if (name != null) {
			parameters.put(name, value);
		}
	}

	public Map getParameters() {
		return parameters;
	}

	public void setStateAllowed(String state, boolean allowed) {
		State windowState = State.forIdentifier(state);
		if (windowState == null) {
			return;
		}

		if (allowed) {
			allowedStates.add(windowState);
		} else {
			allowedStates.remove(windowState);
		}
	}

	public void setModeSupported(String markupName, String mode, boolean supported) {
		Mode windowMode = Mode.forIdentifier(mode);
		if (markupName == null || mode == null) {
			return;
		}

		Set modes = (Set) markupModeSetMap.get(markupName);
		if (modes == null) {
			modes = new HashSet();
			markupModeSetMap.put(markupName, modes);
		}

		if (supported) {
			modes.add(windowMode);
		} else {
			modes.remove(windowMode);
		}
	}

	public String[] getSupportedMarkups() {
		return (String[]) markupModeSetMap.keySet().toArray(new String[markupModeSetMap.size()]);
	}

	public Mode[] getSupportedModes(String markup) {
		Set result = ((Set) markupModeSetMap.get(markup));
		return result == null ? new Mode[0] : (Mode[]) result.toArray(new Mode[result.size()]);
	}

	/**
	 * @see org.cyberelay.portletcontainer.legacy.descriptor.AbstractPortletDefinition#getAllowedWindowStates()
	 */
	public State[] getAllowedWindowStates() {
		return (State[]) allowedStates.toArray(new State[allowedStates.size()]);
	}

	/**
	 * @see org.cyberelay.portletcontainer.legacy.descriptor.AbstractPortletDefinition#getSupportedPortletModes(org.apache.jetspeed.portlet.Client)
	 */
	public Mode[] getSupportedPortletModes(Client client) {
		Set modes = getSupportedModes(client);
		return modes == null ? new Mode[0] : (Mode[]) modes.toArray(new Mode[modes.size()]);
	}

	/**
	 * @see org.cyberelay.portletcontainer.legacy.descriptor.AbstractPortletDefinition#isStateAllowed(org.apache.jetspeed.portlet.PortletWindow.State)
	 */
	public boolean isStateAllowed(State state) {
		return allowedStates.contains(state);
	}

	protected Set getSupportedModes(Client client) {
		return (Set) markupModeSetMap.get(client.getMarkupName().toLowerCase());
	}

	/**
	 * @see org.cyberelay.portletcontainer.legacy.descriptor.AbstractPortletDefinition#isModeSupported(org.apache.jetspeed.portlet.Client,
	 *      org.apache.jetspeed.portlet.Portlet.Mode)
	 */
	public boolean isModeSupported(Client client, Mode mode) {
		if (client == null) {
			return false;
		}
		Set modes = getSupportedModes(client);
		return modes != null && modes.contains(mode);
	}

	public String toString() {
		return "unique name = ["
				+ uniqueName
				+ "], abstract portlet application = ["
				+ abstractPortletApplicationName
				+ "], portlet class = ["
				+ portletClassName
				+ "], parameter size = ["
				+ parameters.size()
				+ "]";
	}
}
