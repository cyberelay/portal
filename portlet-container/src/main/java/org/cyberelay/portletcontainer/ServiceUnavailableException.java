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
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 431 $
 * <li>Last Update Time: $Date: 2008-02-02 08:14:51 +0000 (Sat, 02 Feb 2008) $
 * </ul>
 * 
 */
public class ServiceUnavailableException extends PortletContainerServiceException {

	/**
	 * Constructs a new ServiceUnavailableException. This exception will have no
	 * message and no root cause.
	 */
	public ServiceUnavailableException() {

	}

	/**
	 * Constructs a new ServiceUnavailableException with the given message.
	 * 
	 * @param text
	 *            the message explaining the exception occurance
	 */
	public ServiceUnavailableException(String text) {
		super(text);
	}

	/**
	 * Constructs a new ServiceUnavailableException with the given message and
	 * root cause.
	 * 
	 * @param text
	 *            the message explaining the exception occurance
	 * @param cause
	 *            the root cause of the is exception
	 */
	public ServiceUnavailableException(String text, Throwable cause) {
		super(text);
	}

	/**
	 * Constructs a new ServiceUnavailableException with the given root cause.
	 * 
	 * @param cause
	 *            the root cause
	 */
	public ServiceUnavailableException(Throwable cause) {
		super(cause);
	}

}