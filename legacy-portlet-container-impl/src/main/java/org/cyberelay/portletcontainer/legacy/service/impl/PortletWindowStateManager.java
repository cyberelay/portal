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

import javax.servlet.http.HttpSession;

import org.apache.jetspeed.portlet.AccessDeniedException;
import org.apache.jetspeed.portlet.PortletWindow.State;
import org.cyberelay.portletcontainer.legacy.Constants;
import org.cyberelay.portletcontainer.legacy.InvalidURLException;

class PortletWindowStateManager implements Constants {
//    private PortletWindowRunDataImpl runData;
    private boolean frozen = false;
    private State requestedState;
    private State currentState;

//    PortletWindowStateManager(PortletWindowRunDataImpl windowRunData) {
//        this.runData = windowRunData;
//        initRequestedState();
//        initCurrentState();
//    }

    State getRequestedState() {
        return this.requestedState;
    }

    void setRequestedState(State state) throws AccessDeniedException {
        if (frozen) {
            new AccessDeniedException();
        }

        this.requestedState = state;
    }

    State getCurrentState() {
        return currentState;
    }

    void freeze() {
        if (frozen) {
            return;
        }

        frozen = true;
        // if requestedState == null, current state will remain unchanged.
        if (requestedState == null || currentState.equals(requestedState)) {
            return;
        }

        Map states = getWindowStates();
        if (!State.NORMAL.equals(currentState)) {
            // remove old non-NORMAL window state from session.
//            states.remove(runData.getWindowDefinition().getIdentifier());
        }

        currentState = requestedState;
        if (!State.NORMAL.equals(currentState)) {
            // save current non-NORMAL state into session.
//            states.put(runData.getWindowDefinition().getIdentifier(), currentState);
        }
    }

    private void initRequestedState() {
//        try {
//            if(runData.isTargetPortletWindow()) {
//	            PortalRequestURL portalRequestURL = runData.getPortalRequestURL();
//	            this.requestedState = (portalRequestURL == null ? null : State.forIdentifier(portalRequestURL.getPortletWindowState()));
//            }
//        } catch (InvalidURLException e) {
//            this.requestedState = null;
//        }
    }

    private void initCurrentState() {
        Map states = getWindowStates();
        if (states != null) {
//            currentState = (State) states.get(runData.getWindowDefinition().getIdentifier());
        }
        if (currentState == null) {
            currentState = State.NORMAL;
        }
    }

    private Map getWindowStates() {
    	return null;
//        HttpSession portalSession = runData.getPortalSession();
//
//        Map result = (Map) portalSession.getAttribute(PORTLET_WINDOW_STATE);
//        if (result == null) {
//            result = new HashMap();
//            portalSession.setAttribute(PORTLET_WINDOW_STATE, result);
//        }
//
//        return result;
    }
}
