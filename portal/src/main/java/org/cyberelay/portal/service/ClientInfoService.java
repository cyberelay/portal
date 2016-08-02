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

package org.cyberelay.portal.service;

import javax.servlet.http.HttpServletRequest;

import org.cyberelay.portal.Client;
import org.cyberelay.portal.annotation.ServiceTag;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jul 21, 2007 
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 377 $
 * <li>Last Update Time: $Date: 2008-01-17 07:35:03 +0000 (Thu, 17 Jan 2008) $
 * </ul>
 * 
 */
@ServiceTag(defaultImpl = "org.cyberelay.portal.service.impl.ClientInfoServiceImpl")
public interface ClientInfoService extends PortalApplicationService {
	
	Client getClient(HttpServletRequest request);
	
}
