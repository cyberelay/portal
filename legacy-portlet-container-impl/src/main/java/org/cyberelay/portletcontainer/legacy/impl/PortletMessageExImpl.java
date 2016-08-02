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

import java.util.HashSet;
import java.util.Set;

import org.cyberelay.portletcontainer.legacy.PortletMessageEx;
import org.cyberelay.portletcontainer.legacy.PortletWindowEx;

/**
 * 
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jan 21, 2008
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
public class PortletMessageExImpl implements PortletMessageEx {
    private PortletWindowEx sourceWindow;
    private Object content = null;
    private Set<String> targetPortlets = new HashSet<String>();

    public PortletMessageExImpl(PortletWindowEx source, Object content, String[] targetPortlets) {
        this.sourceWindow = source;
        this.content = content;

        if (targetPortlets != null) {
        	for (String portletName : targetPortlets) {
                this.targetPortlets.add(portletName);				
			}
        }
    }
    
    /**
     * @see org.apache.jetspeed.portlet.extension.message.Message#getContent()
     */
    public Object getContent() {
        return content;
    }

    /**
     * @see org.apache.jetspeed.portlet.extension.message.Message#isTargetPortlet(org.apache.jetspeed.portlet.extension.PortletWindowDefinition)
     */
    public boolean isTargetPortlet(PortletWindowEx portletWindow) {
        if (portletWindow == null || sourceWindow == null || sourceWindow.equals(portletWindow))
            return false;

        return targetPortlets.contains(portletWindow.getPortletName());
    }

}
