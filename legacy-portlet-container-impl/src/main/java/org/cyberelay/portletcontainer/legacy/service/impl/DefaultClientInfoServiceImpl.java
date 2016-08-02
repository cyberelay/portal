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

import javax.servlet.http.HttpServletRequest;

import org.apache.jetspeed.portlet.Capability;
import org.apache.jetspeed.portlet.Client;
import org.cyberelay.portletcontainer.AbstractPortletContainerService;
import org.cyberelay.portletcontainer.legacy.service.ClientInfoService;

public class DefaultClientInfoServiceImpl extends AbstractPortletContainerService implements
		ClientInfoService {
	private static final Client DEFAULT;

	static {
		ClientImpl client = new ClientImpl();
		client.setManufacturer("Microsoft");
		client.setMarkupName("html");
		client.setMimeType("text/html");
		client.setModel("");
		client.setUserAgent("IE");
		client.setVersion("6.0");
		client.addCapability(Capability.HTML_2_0);
		client.addCapability(Capability.HTML_3_0);
		client.addCapability(Capability.HTML_4_0);
		client.addCapability(Capability.HTML_CSS);
		client.addCapability(Capability.HTML_FRAME);
		client.addCapability(Capability.HTML_IFRAME);
		client.addCapability(Capability.HTML_JAVA);
		client.addCapability(Capability.HTML_JAVASCRIPT);
		client.addCapability(Capability.HTML_NESTED_TABLE);
		client.addCapability(Capability.FRAGMENT);
		client.addCapability(Capability.WML_1_1);
		client.addCapability(Capability.WML_1_2);
		client.addCapability(Capability.WML_TABLE);

		DEFAULT = client;
	}

	public Client getClient(HttpServletRequest request) {
		return DEFAULT;
	}

}
