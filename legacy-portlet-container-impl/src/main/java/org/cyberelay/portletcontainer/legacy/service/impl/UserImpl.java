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

import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.jetspeed.portlet.User;

class UserImpl implements User {
    private Hashtable attributes = new Hashtable();
    
    public static final String ATTR_ID = "id";
    public static final String ATTR_USER_ID = "userId";
    public static final String ATTR_GIVEN_NAME = "givenName";
    public static final String ATTR_FAMILY_NAME = "familyName";
    public static final String ATTR_FULL_NAME = "fullName";
    public static final String ATTR_NICK_NAME = "nickName";
    public static final String ATTR_LAST_LOGIN_TIME = "lastLoginTime";

    public String getID() {
        return (String)getAttribute(ATTR_ID);
    }

    public String getUserID() {
        return (String)getAttribute(ATTR_USER_ID);
    }

    public String getGivenName() {
        return (String)getAttribute(ATTR_USER_ID);
    }

    public String getFamilyName() {
        return (String)getAttribute(ATTR_USER_ID);
    }

    public String getFullName() {
        return (String)getAttribute(ATTR_USER_ID);
    }

    public String getNickName() {
        return (String)getAttribute(ATTR_USER_ID);
    }

    public long getLastLoginTime() {
        Timestamp loginTime = (Timestamp)getAttribute(ATTR_LAST_LOGIN_TIME);
        return loginTime == null ? Long.MIN_VALUE : loginTime.getTime();
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public Enumeration getAttributeNames() {
        return attributes.keys();
    }

    public void setUserID(String value) {
        setAttribute(ATTR_USER_ID, value);
    }

    public void setGivenName(String value) {
        setAttribute(ATTR_GIVEN_NAME, value);
    }

    public void setFamilyName(String value) {
        setAttribute(ATTR_FAMILY_NAME, value);
    }

    public void setNickName(String value) {
        setAttribute(ATTR_NICK_NAME, value);
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }
 
    public boolean isModifiable() {
        throw new RuntimeException("Not implemented yet.");
    }

}
