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
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.cyberelay.portal.impl.PortalApplicationImpl;
import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portletcontainer.PortletContainerException;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 611 $
 * <li>Last Update Time: $Date: 2008-03-02 14:49:21 +0000 (Sun, 02 Mar 2008) $
 * </ul>
 * 
 */
public class PortalApplicationBootstrapper implements ServletContextListener, PortalConstants {
	private static final Logger LOG = LoggerFactory.getLogger(PortalApplicationBootstrapper.class);

	/**
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		String contextName = servletContext.getServletContextName();
		LOG.info("Initializing portal application [" + contextName + "]....");
		try {
			PortalApplication portal = new PortalApplicationImpl(servletContext);
			servletContext.setAttribute(PORTAL_APPLICATION, portal);
			LOG.info("Portal application [" + contextName + "] initialized!");
		} catch (PortletContainerException e) {
			LOG.debug("Portal application [" + contextName + "] cannot be initialized", e);
			throw new PortalApplicationFatalException("Cannot bootstrap portal application", e);
		}
	}

	/**
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		String contextName = context.getServletContextName();
		LOG.info("Destroying portal application [" + contextName + "]....");
		PortalApplication portal = (PortalApplication) context.getAttribute(PORTAL_APPLICATION);
		if (portal != null) {
			portal.destory();
		}
		context.removeAttribute(PORTAL_APPLICATION);
		LOG.info("Portal application [" + contextName + "] destroyed!");
	}

}
