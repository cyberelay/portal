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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.apache.jetspeed.portlet.PortletSession;
import org.apache.jetspeed.portlet.User;
import org.cyberelay.portal.util.Assert;
import org.cyberelay.portal.util.StringUtil;
import org.cyberelay.portletcontainer.legacy.PortletWindowEx;

/**
 * 
 * @author Roger Tang
 * 
 *
 */
public class PortletSessionImpl implements PortletSession {
    private String namespace;
    private HttpSession wrapped;

    public PortletSessionImpl(HttpSession session, PortletWindowEx window) {
        Assert.notNull(session);
        Assert.notNull(window);
        
        this.wrapped = session;
        this.namespace = window.getUniqueID() + "#";
    }

    public HttpSession getWrapped() {
        return wrapped;
    }
    
    /**
     * @deprecated
     * @see org.apache.jetspeed.portlet.PortletSession#getUser()
     */
    public User getUser() {
    	//TODO
        return null;
    }

    /**
     * @see javax.servlet.http.HttpSession#getCreationTime()
     */
    public long getCreationTime() {
        return wrapped.getCreationTime();
    }

    /**
     * @see javax.servlet.http.HttpSession#getLastAccessedTime()
     */
    public long getLastAccessedTime() {
        return wrapped.getLastAccessedTime();
    }

    /**
     * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String, java.lang.Object)
     */
    public void setAttribute(String name, Object value) {
        wrapped.setAttribute(namespace + name, value);
    }

    public Object getAttribute(String name) {
        Object result = wrapped.getAttribute(namespace + name);        
        return result == null ? wrapped.getAttribute(name) : result;
    }

    public Enumeration getAttributeNames() {
        Vector result = new Vector();
        Enumeration names = wrapped.getAttributeNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            if(name.startsWith(namespace)) {
                name = StringUtil.replaceFirst(name, namespace, "");
            }
            result.add(name);
        }
        
        return result.elements();
    }

    public void removeAttribute(String name) {
        wrapped.removeAttribute(namespace + name);
    }

    public String getId() {
        return wrapped.getId();
    }

    /**
     * @see javax.servlet.http.HttpSession#getServletContext()
     */
    public ServletContext getServletContext() {
        return wrapped.getServletContext();
    }

    /**
     * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
     */
    public void setMaxInactiveInterval(int arg0) {
        wrapped.setMaxInactiveInterval(arg0);
    }

    /**
     * 
     * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
     */
    public int getMaxInactiveInterval() {
        return wrapped.getMaxInactiveInterval();
    }

    /**
     * @deprecated
     * @see javax.servlet.http.HttpSession#getSessionContext()
     */
    public HttpSessionContext getSessionContext() {
        return wrapped.getSessionContext();
    }

    /**
     * @deprecated
     * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
     */
    public Object getValue(String name) {
        return getAttribute(name);
    }

    /**
     * @deprecated
     * @see javax.servlet.http.HttpSession#getValueNames()
     */
    public String[] getValueNames() {
        List result = new ArrayList();
        Enumeration names = getAttributeNames();
        while (names.hasMoreElements()) {
            result.add(names.nextElement());            
        }
        
        return (String[]) result.toArray(new String[result.size()]);
    }

    /**
     * @deprecated
     * @see javax.servlet.http.HttpSession#putValue(java.lang.String, java.lang.Object)
     */
    public void putValue(String name, Object value) {
        setAttribute(name, value);
    }

    /**
     * @deprecated
     * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
     */
    public void removeValue(String name) {
        removeAttribute(name);
    }

    /**
     * 
     * @see javax.servlet.http.HttpSession#invalidate()
     */
    public void invalidate() {
        //wrapped.invalidate();
    }

    /**
     * 
     * @see javax.servlet.http.HttpSession#isNew()
     */
    public boolean isNew() {
        return wrapped.isNew();
    }

}
