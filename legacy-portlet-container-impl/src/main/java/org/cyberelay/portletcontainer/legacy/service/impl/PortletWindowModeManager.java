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

package org.cyberelay.portletcontainer.legacy.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.jetspeed.portlet.AccessDeniedException;
import org.apache.jetspeed.portlet.Portlet.Mode;
import org.apache.jetspeed.portlet.Portlet.ModeModifier;
import org.cyberelay.portal.util.Assert;
import org.cyberelay.portletcontainer.legacy.Constants;
import org.cyberelay.portletcontainer.legacy.InvalidURLException;

class PortletWindowModeManager implements Constants {
//	private PortletWindowRunDataImpl runData;

	private ModeModifier modeModifier = ModeModifier.REQUESTED;
	private boolean frozen = false;
	private Mode requestedMode;
	private Mode currentMode;
	private Mode previousMode;

//	PortletWindowModeManager(PortletWindowRunDataImpl windowRunData) {
//		this.runData = windowRunData;
//		initWindowModes();
//	}

	Mode getCurrentMode() {
		return currentMode;
	}

	Mode getPreviousMode() {
		return previousMode;
	}

	Mode getRequestedMode() {
		return requestedMode;
	}

	void setModeModifier(ModeModifier modifier) throws AccessDeniedException {
		Assert.notNull(modifier);

		if (frozen) {
			new AccessDeniedException();
		}

		if (ModeModifier.REQUESTED.equals(modifier)) {
			return;
		}

		this.modeModifier = modifier;
	}

	void freeze() {
		if (frozen) {
			return;
		}

		frozen = true;

		if (ModeModifier.CURRENT.equals(modeModifier)) {
			// if mode modifier is CURRENT, current mode will remain unchanged.
			return;
		} else if (ModeModifier.REQUESTED.equals(modeModifier)) {
			if (requestedMode == null || currentMode.equals(requestedMode)) {
				// if requested mode is null or same as current mode, no need to
				// do anything.
				return;
			}
			removeModeFromSession(PORTLET_WINDOW_MODE);
			removeModeFromSession(PORTLET_WINDOW_PREVIOUS_MODE);
			previousMode = currentMode;
			currentMode = requestedMode;
			if (!currentMode.equals(Mode.VIEW)) {
				saveModeIntoSession(PORTLET_WINDOW_MODE, currentMode);
			}
			if (previousMode != null) {
				saveModeIntoSession(PORTLET_WINDOW_PREVIOUS_MODE, previousMode);
			}
		} else if (ModeModifier.PREVIOUS.equals(modeModifier)) {
			removeModeFromSession(PORTLET_WINDOW_MODE);
			removeModeFromSession(PORTLET_WINDOW_PREVIOUS_MODE);
			currentMode = previousMode;
			previousMode = null;
			if (!currentMode.equals(Mode.VIEW)) {
				saveModeIntoSession(PORTLET_WINDOW_MODE, currentMode);
			}
		}
	}

	private void initWindowModes() {
		this.requestedMode = getOriginalRequestedMode();
		this.currentMode = (Mode) getModeFromSession(PORTLET_WINDOW_MODE, Mode.VIEW);
		this.previousMode = (Mode) getModeFromSession(PORTLET_WINDOW_PREVIOUS_MODE, null);
	}

	private Mode getOriginalRequestedMode() {
//		try {
//			if (runData.isTargetPortletWindow()) {
//				PortalRequestURL portalRequestURL = runData.getPortalRequestURL();
//				return portalRequestURL == null ? null : Mode.forIdentifier(portalRequestURL
//						.getPortletMode());
//			}
			return null;
//		} catch (InvalidURLException e) {
//			return null;
//		}
	}

	private void saveModeIntoSession(String key, Mode mode) {
//		Map modes = (Map) runData.getPortalSession().getAttribute(key);
//		if (modes == null) {
//			modes = new HashMap();
//			runData.getPortalSession().setAttribute(key, modes);
//		}
//		modes.put(runData.getWindowDefinition().getIdentifier(), mode);
	}

	private void removeModeFromSession(String key) {
//		Map modes = (Map) runData.getPortalSession().getAttribute(key);
//		if (modes != null && key != null) {
//			modes.remove(runData.getWindowDefinition().getIdentifier());
//		}
	}

	private Mode getModeFromSession(String key, Mode defaultValue) {
		Mode result = null;
//		Map modes = (Map) runData.getPortalSession().getAttribute(key);
//		if (modes != null) {
//			result = (Mode) modes.get(runData.getWindowDefinition().getIdentifier());
//		}

		return result == null ? defaultValue : result;
	}
}
