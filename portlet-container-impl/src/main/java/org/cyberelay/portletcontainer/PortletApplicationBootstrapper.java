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

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;
import org.cyberelay.portletcontainer.impl.PortletApplicationImpl;

/**
 * @author Roger Tang
 * 
 */
public class PortletApplicationBootstrapper implements
		ServletContextListener,
		PortletContainerConstants {
	
	private static final Logger LOG = LoggerFactory.getLogger(PortletApplicationBootstrapper.class);

	/**
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		String contextName = servletContext.getServletContextName();

		LOG.info("Initializing portlet application [" + contextName + "]....");

		PortletApplication application = new PortletApplicationImpl(servletContext);
		servletContext.setAttribute(PORTLET_APPLICATION, application);
		PortletApplicationRegistry.register(application);

		LOG.info("Portlet application [" + contextName + "] initialized.");
	}

	/**
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		String contextName = context.getServletContextName();

		LOG.info("destroying portlet application [" + contextName + "]....");

		PortletApplication app = (PortletApplication) context.getAttribute(PORTLET_APPLICATION);
		PortletApplicationRegistry.unregister(app);
		app.destroy();

		LOG.info("Portlet application [" + contextName + "] destroyed.");
	}
}
