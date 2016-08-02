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
package org.cyberelay.portletcontainer.legacy;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.cyberelay.portletcontainer.legacy.impl.PortletApplicationImpl;

/**
 * 
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jan 21, 2008
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
public class PortletApplicationBootstrapper implements ServletContextListener, Constants {

    /**
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        System.out.println("initializing portlet application context [" + servletContext.getServletContextName() +"]....");
        PortletApplication portletApplicationContext = new PortletApplicationImpl(servletContext);
        servletContext.setAttribute(PORTLET_APPLICATION, portletApplicationContext);
        System.out.println("initializing portlet application context [" + servletContext.getServletContextName() +"]....done.");
    }

    /**
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        System.out.println("destorying portlet application context [" + servletContext.getServletContextName() +"]....");
        PortletApplication portletApplicationContext = (PortletApplication)servletContext.getAttribute(PORTLET_APPLICATION);
        if(portletApplicationContext != null) {
            portletApplicationContext.destroy();
        }
        System.out.println("destroying portlet application context [" + servletContext.getServletContextName() +"]....done.");
    }
}
