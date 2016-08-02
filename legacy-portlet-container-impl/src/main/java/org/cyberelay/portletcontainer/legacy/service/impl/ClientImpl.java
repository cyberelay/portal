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

import java.util.HashSet;
import java.util.Set;

import org.apache.jetspeed.portlet.Capability;
import org.apache.jetspeed.portlet.Client;

class ClientImpl implements Client {
    private String manufacturer;
    private String model;
    private String version;
    private String userAgent;
    private String mimeType;
    private String markupName;
    private Set<Capability> capabilities = new HashSet<Capability>();

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setMarkupName(String markupName) {
        this.markupName = markupName;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void addCapability(String capabilityIdentifier) {
        addCapability(Capability.forIdentifier(capabilityIdentifier));
    }
    
    public void addCapability(Capability capability) {
        if(capability != null) {
            capabilities.add(capability);
        }
    }
    
    public void removeCapability(Capability capability) {
        if(capability != null && capability != null) {
            capabilities.remove(capability);
        }
    }
    
    public boolean isCapableOf(Capability capability) {
        return (capabilities != null) && capabilities.contains(capability);
    }

    public boolean isCapableOf(Capability[] capabilities) {
    	for (Capability capability : capabilities) {			
            if(!isCapableOf(capability)) {
                return false;
            }
        }
        return true;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getMarkupName() {
        return markupName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getModel() {
        return model;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getVersion() {
        return version;
    }
}
