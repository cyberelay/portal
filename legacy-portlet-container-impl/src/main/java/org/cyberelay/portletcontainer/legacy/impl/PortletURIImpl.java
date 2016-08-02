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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.jetspeed.portlet.DefaultPortletAction;
import org.apache.jetspeed.portlet.PortletAction;
import org.apache.jetspeed.portlet.PortletURI;
import org.apache.jetspeed.portlet.Portlet.Mode;
import org.apache.jetspeed.portlet.PortletWindow.State;
import org.cyberelay.portal.util.URLUTF8Encoder;

/**
 * 
 * @author Roger Tang
 * 
 *
 */
public class PortletURIImpl implements PortletURI {
    private String uriPrefix;
    private String pageName;
    private String windowName;
    private String action;
    private State state;
    private Mode mode;
    private Map parameters;

    public PortletURIImpl(String uriPrefix, String pageName, String portletWindowName) {
        this.uriPrefix = uriPrefix;
        this.pageName = pageName;
        this.windowName = portletWindowName;
    }

    /**
     * @see org.apache.jetspeed.portlet.PortletURI#addParameter(java.lang.String, java.lang.String)
     */
    public void addParameter(String name, String value) {
        if (parameters == null) {
            parameters = new HashMap();
        }
        if (name != null && value != null) {
            parameters.put(name, value);
        } else if (name != null) {
            parameters.remove(name);
        }
    }

    /**
     * @deprecated
     * @see org.apache.jetspeed.portlet.PortletURI#addAction(org.apache.jetspeed.portlet.PortletAction)
     */
    public void addAction(PortletAction portletaction) {
        if(portletaction instanceof DefaultPortletAction) {
            DefaultPortletAction action = (DefaultPortletAction)portletaction;
            addAction(action.getName());
            Map params = action.getParameters();
            if(params != null) {
	            Iterator paramKeys = params.keySet().iterator();
	            while(paramKeys.hasNext()) {
	                String key = (String)paramKeys.next();
	                Object value = params.get(key);
	                addParameter(key, value == null ? "" : value.toString());
	            }
            }
            return;
        }
        
        throw new RuntimeException("Not supported operation! Use addAction(String action) instead.");
    }

    /**
     * @see org.apache.jetspeed.portlet.PortletURI#addAction(java.lang.String)
     */
    public void addAction(String action) {
        this.action = action;
    }
    
    public void setMode(Mode mode) {
        this.mode = mode;
    }
    
    public void setState(State state) {
        this.state = state;
    }

    /**
     * @see org.apache.jetspeed.portlet.PortletURI#toString()
     */
    public String toString() {
        StringBuffer result = new StringBuffer();
        if(uriPrefix != null) {
            result.append(uriPrefix);
        }
        
        if(pageName != null) {
            result.append("/").append(URLUTF8Encoder.encode(pageName));
        }
        if(windowName != null) {
            result.append("/").append(URLUTF8Encoder.encode(windowName));
            result.append("/ver=1.0");
            if(action != null) {
                result.append("/action=").append(URLUTF8Encoder.encode(action));
            }
            if(mode != null) {
                result.append("/mode=").append(mode.toString());
            }
            if(state != null) {
                result.append("/state=").append(state.toString());
            }
        }
        if(parameters != null && parameters.size() > 0) {
            result.append("?");
            Iterator iterator = parameters.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                result.append(URLUTF8Encoder.encode(key)).append("=").append(URLUTF8Encoder.encode((String)parameters.get(key)));
                if(iterator.hasNext()) {
                    result.append("&");
                }
            }
        }
        
        return result.toString();
    }
}
