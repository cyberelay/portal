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
package org.cyberelay.portletcontainer.impl.config;

import javax.portlet.PortletException;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Feb 27, 2008
 * <li>Last Editor: $Author$
 * <li>Current Revision: $Revision$
 * <li>Last Update Time: $Date$
 * </ul>
 * 
 */
public class PortletFilterInstantiationException extends PortletException {

	/**
	 * 
	 */
	public PortletFilterInstantiationException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param text
	 */
	public PortletFilterInstantiationException(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param text
	 * @param cause
	 */
	public PortletFilterInstantiationException(String text, Throwable cause) {
		super(text, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public PortletFilterInstantiationException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
