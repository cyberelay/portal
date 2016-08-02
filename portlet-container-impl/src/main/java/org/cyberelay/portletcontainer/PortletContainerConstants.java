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

package org.cyberelay.portletcontainer;


/**
 * @author Roger Tang
 * 
 */
public interface PortletContainerConstants {

	/* ========================================================================================= */
	/* Portlet container related constants. */

	String PORTLET_APPLICATION = "org.cyberelay.portlet.application";

	String PORTLET_INVOCATION_REQUEST = "org.cyberelay.portlet.invocation.request";

	String PORTLET_CONTEXT = "org.cyberelay.portlet.context";

	String PORTLET_WINDOW = "org.cyberelay.portlet.container.portlet-window";

	String PORTLET_EVENT = "org.cyberelay.portlet.container.portlet-event";
	
	String EVENT_QUEUE = "org.cyberelay.portlet.container.event-queue";
	
	/* ========================================================================================= */

	String PORTLET_REQUEST = "javax.portlet.request";

	String PORTLET_RESPONSE = "javax.portlet.response";

	String PORTLET_CONFIG = "javax.portlet.config";

	String HTTP_REQUEST = "org.cyberelay.portal.http.request";

	String HTTP_RESPONSE = "org.cyberelay.portal.http.response";

}
