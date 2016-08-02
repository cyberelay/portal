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

package org.cyberelay.portletcontainer.handler;

import javax.servlet.ServletException;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 29, 2007 
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 496 $
 * <li>Last Update Time: $Date: 2008-02-12 14:17:01 +0000 (Tue, 12 Feb 2008) $
 * </ul>
 * 
 */
public class TargetPortletNotFoundException extends ServletException {

	public TargetPortletNotFoundException(String message) {
		super(message);
	}
}
