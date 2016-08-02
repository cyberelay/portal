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
package org.cyberelay.portletcontainer.legacy;

import java.util.HashMap;
import java.util.Map;

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
public class PortletMethod {
	private static final Map<String, PortletMethod> ALL_METHODS = new HashMap<String, PortletMethod>();

	public static final PortletMethod ACTION_PERFORMED = new PortletMethod(1, "actionPerformed");

	public static final PortletMethod BEGIN_PAGE = new PortletMethod(3, "beginPage");

	public static final PortletMethod END_PAGE = new PortletMethod(5, "endPage");

	public static final PortletMethod BEGIN_EVENT_PHASE = new PortletMethod(7, "beginEventPhase");

	public static final PortletMethod END_EVENT_PHASE = new PortletMethod(9, "endEventPhase");

	public static final PortletMethod LOGIN = new PortletMethod(11, "login");

	public static final PortletMethod LOGOUT = new PortletMethod(13, "logout");

	public static final PortletMethod DO_TITLE = new PortletMethod(15, "doTitle");

	public static final PortletMethod MESSAGE_RECEIVED = new PortletMethod(17, "messageReceived");

	public static final PortletMethod SERVICE = new PortletMethod(19, "service");

	private int id;
	private String name;

	private PortletMethod(int id, String name) {
		this.id = id;
		this.name = name;

		ALL_METHODS.put(name.toLowerCase(), this);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static PortletMethod parse(int id) {
		switch (id) {
		case 1:
			return ACTION_PERFORMED;
		case 3:
			return BEGIN_PAGE;
		case 5:
			return END_PAGE;
		case 7:
			return BEGIN_EVENT_PHASE;
		case 9:
			return END_EVENT_PHASE;
		case 11:
			return LOGIN;
		case 13:
			return LOGOUT;
		case 15:
			return DO_TITLE;
		case 17:
			return MESSAGE_RECEIVED;
		case 19:
			return SERVICE;
		default:
			return null;
		}
	}

	public static PortletMethod parse(String method) {
		if (method == null) {
			return null;
		}

		return (PortletMethod) ALL_METHODS.get(method.toLowerCase());
	}

	public String toString() {
		return name;
	}

	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj == this) {
				return true;
			} else if (obj instanceof PortletMethod) {
				return ((PortletMethod) obj).getId() == id;
			}
		}
		return false;
	}

	public int hashCode() {
		return id;
	}
}