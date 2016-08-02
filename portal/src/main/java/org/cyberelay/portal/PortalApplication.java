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

package org.cyberelay.portal;

import javax.servlet.ServletContext;

import org.cyberelay.portal.service.PortalApplicationService;
import org.cyberelay.portletcontainer.PortletContainer;

/**
 * PortalApplication is an abstraction of a concept commonly referred as
 * <i>Portal</i> as defined in Portlet Specification 2.0
 * 
 * <blockquote> A portal is a web based application that –commonly- provides
 * personalization, 5 authentication, content aggregation from different sources
 * and hosts the presentation layer of information systems. Aggregation is the
 * action of integrating content from different sources within a web page. A
 * portal may have sophisticated personalization features to provide customized
 * content to users. Portal pages may have different set of portlets creating
 * content for different users. </blockquote>
 * 
 * Above functionalities are provided by
 * {@link org.cyberelay.portal.service.PortalApplicationService  PortalApplicationService}
 * 
 * PortalApplication provides a runtime environment for PortletContainer.
 * 
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jul 6, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 666 $
 * <li>Last Update Time: $Date: 2008-03-18 10:15:24 +0000 (Tue, 18 Mar 2008) $
 * </ul>
 * 
 */
public interface PortalApplication {

	/**
	 * Retrieve the PortletContainer instance managed by this PortalApplication
	 * 
	 * @return PortletContainer instance managed by this PortalApplication
	 */
	PortletContainer getPortletContainer();

	/**
	 * Retrieve the configuration of this portal application
	 * 
	 * @return
	 */
	PortalConfiguration getConfiguration();

	/**
	 * Retrieve the servlet context of this portal application
	 * 
	 * @return
	 */
	ServletContext getPortalServletContext();

	/**
	 * Retrieve the portal context associated with this portal application
	 * 
	 * @return
	 */
	PortalContextEx getPortalContext();

	/**
	 * Retrieve a ready-to-use PortalApplicationService instance managed by this
	 * portal application
	 * 
	 * @param <T>
	 * @param serviceInterface
	 * @return
	 */
	<T extends PortalApplicationService> T getService(Class<T> serviceInterface);

	void destory();
}
