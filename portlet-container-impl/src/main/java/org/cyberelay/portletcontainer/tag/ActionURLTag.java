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

package org.cyberelay.portletcontainer.tag;

import javax.portlet.PortletURL;

/**
 * The class is copied from org.apache.pluto.tags.* and has been changed to fit
 * in cyberelay portlet container entity model.
 * 
 */
public class ActionURLTag extends PortletURLTag {
	private static final long serialVersionUID = 391692381347918159L;

	/**
	 * Creates an action PortletURL
	 * 
	 * @return PortletURL
	 */
	@Override
	protected PortletURL createPortletUrl() {
		return getMimeResponse().createActionURL();
	}

}
