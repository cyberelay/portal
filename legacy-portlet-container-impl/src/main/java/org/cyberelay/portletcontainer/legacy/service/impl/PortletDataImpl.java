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

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.jetspeed.portlet.AccessDeniedException;
import org.apache.jetspeed.portlet.PortletData;

class PortletDataImpl implements PortletData {
    private Hashtable attributes = new Hashtable();
    
    /**
     * @see org.apache.jetspeed.portlet.PortletData#setAttribute(java.lang.String, java.lang.Object)
     */
    public void setAttribute(String name, Object value) throws AccessDeniedException {
        attributes.put(name, value);
    }

    /**
     * @see org.apache.jetspeed.portlet.PortletData#getAttribute(java.lang.String)
     */
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * @see org.apache.jetspeed.portlet.PortletData#getAttributeNames()
     */
    public Enumeration getAttributeNames() {
        return attributes.keys();
    }

    /**
     * @see org.apache.jetspeed.portlet.PortletData#removeAttribute(java.lang.String)
     */
    public void removeAttribute(String name) throws AccessDeniedException {
        attributes.remove(name);
    }

    /**
     * @see org.apache.jetspeed.portlet.PortletData#removeAllAttributes()
     */
    public void removeAllAttributes() throws AccessDeniedException {
        attributes.clear();
    }

    /**
     * @see org.apache.jetspeed.portlet.PortletData#store()
     */
    public void store() throws AccessDeniedException, IOException {
        //do nothing.        
    }

}
