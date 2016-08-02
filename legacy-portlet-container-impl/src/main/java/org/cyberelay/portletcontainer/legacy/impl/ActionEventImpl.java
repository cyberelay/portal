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
package org.cyberelay.portletcontainer.legacy.impl;

import org.apache.jetspeed.portlet.DefaultPortletAction;
import org.apache.jetspeed.portlet.Portlet;
import org.apache.jetspeed.portlet.PortletAction;
import org.apache.jetspeed.portlet.PortletRequest;
import org.apache.jetspeed.portlet.event.ActionEvent;

public class ActionEventImpl implements ActionEvent {
    private Portlet portlet;
    private PortletRequest request;
    private String actionString;

    public ActionEventImpl(String actionString, Portlet portlet, PortletRequest request) {
        this.actionString = actionString;
        this.portlet = portlet;
        this.request = request;
    }

    /**
     * @deprecated
     * @see org.apache.jetspeed.portlet.event.ActionEvent#getAction()
     */
    public PortletAction getAction() {
        return new DefaultPortletAction(actionString);
    }

    /**
     * @see org.apache.jetspeed.portlet.event.ActionEvent#getActionString()
     */
    public String getActionString() {
        return actionString;
    }

    /**
     * @see org.apache.jetspeed.portlet.event.Event#getRequest()
     */
    public PortletRequest getRequest() {
        return request;
    }

    /**
     * @deprecated
     * @see org.apache.jetspeed.portlet.event.Event#getPortlet()
     */
    public Portlet getPortlet() {
        return portlet;
    }

}
