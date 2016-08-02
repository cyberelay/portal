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

import org.apache.jetspeed.portlet.Portlet;
import org.apache.jetspeed.portlet.PortletMessage;
import org.apache.jetspeed.portlet.PortletRequest;
import org.apache.jetspeed.portlet.event.MessageEvent;

/**
 * 
 * @author Roger Tang
 * 
 */
class MessageEventImpl implements MessageEvent {
    private Portlet portlet;
    private PortletMessage message;
    private PortletRequest request;
    
    public MessageEventImpl(Portlet portlet, PortletMessage message, PortletRequest request) {
        this.portlet = portlet;
        this.message = message;
        this.request = request;
    }
    
    /**
     * @see org.apache.jetspeed.portlet.event.MessageEvent#getMessage()
     */
    public PortletMessage getMessage() {
        return message;
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
