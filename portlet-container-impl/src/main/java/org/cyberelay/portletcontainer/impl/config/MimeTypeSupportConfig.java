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

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import org.cyberelay.portlet.descriptor.model.PortletAppType;
import org.cyberelay.portlet.descriptor.model.PortletType;

/**
 * @author Roger Tang
 * 
 */
class MimeTypeSupportConfig extends AbstractConfig {
	private static final String TEXT_HTML = "text/html";
	private Map<String, MimeTypeSupport> mimeTypeSupports;

	public MimeTypeSupportConfig(PortletType portletType, PortletAppType app) {
		mimeTypeSupports = new HashMap<String, MimeTypeSupport>();
	}

	private static class MimeTypeSupport {
		String mimeType;
		Set<WindowState> supportedStates = new HashSet<WindowState>();
		Set<PortletMode> supportedModes = new HashSet<PortletMode>();

		public MimeTypeSupport(String mimeType) {
			this.mimeType = mimeType;
			/*
			 * For each MIME type, portlet should at least support the following
			 * states and modes
			 */
			supportedStates.add(WindowState.MAXIMIZED);
			supportedStates.add(WindowState.MINIMIZED);
			supportedStates.add(WindowState.NORMAL);
			supportedModes.add(PortletMode.VIEW);
		}
	}

	public boolean isSupportedMimeType(String mimeType) {
		return mimeTypeSupports.containsKey(mimeType);
	}

	public Enumeration<String> getSupportedMimeTypes() {
		return Collections.enumeration(mimeTypeSupports.keySet());
	}

	public Enumeration<PortletMode> getSupportedPortletModes(String mimeType) {
		return Collections.enumeration(mimeTypeSupports.get(mimeType).supportedModes);
	}

	public Enumeration<WindowState> getSupportedWindowStates(String mimeType) {
		return Collections.enumeration(mimeTypeSupports.get(mimeType).supportedStates);
	}

	public boolean isSupportedPortletMode(PortletMode mode, String mimeType) {
		if (isSupportedMimeType(mimeType)) {
			return mimeTypeSupports.get(mimeType).supportedModes.contains(mode);
		}
		return false;
	}

	public Enumeration<PortletMode> getSupportedPortletModes() {
		return getSupportedPortletModes(TEXT_HTML);
	}

	public Enumeration<WindowState> getSupportedWindowStates() {
		return getSupportedWindowStates(TEXT_HTML);
	}

	public boolean isSupportedPortletMode(PortletMode mode) {
		return isSupportedPortletMode(mode, TEXT_HTML);
	}

	public boolean isSupportedWindowState(WindowState state) {
		return isSupportedWindowState(state, TEXT_HTML);
	}

	public boolean isSupportedWindowState(WindowState state, String mimeType) {
		if (isSupportedMimeType(mimeType)) {
			return mimeTypeSupports.get(mimeType).supportedStates.contains(state);
		}
		return false;
	}
}
