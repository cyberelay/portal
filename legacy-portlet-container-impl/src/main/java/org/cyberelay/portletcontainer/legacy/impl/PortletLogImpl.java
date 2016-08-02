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
package org.cyberelay.portletcontainer.legacy.impl;

import org.apache.jetspeed.portlet.PortletLog;
import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;

/**
 * 
 * @author Roger Tang
 * 
 */
public class PortletLogImpl implements PortletLog {
	private Logger log;
	
	public PortletLogImpl(String loggerName) {
		log = LoggerFactory.getLogger(loggerName);
	}
	
    /**
     * @see org.apache.jetspeed.portlet.PortletLog#isDebugEnabled()
     */
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    /**
     * @see org.apache.jetspeed.portlet.PortletLog#debug(java.lang.String)
     */
    public void debug(String arg0) {
    	log.debug(arg0);
    }

    /**
     * @see org.apache.jetspeed.portlet.PortletLog#isInfoEnabled()
     */
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    /**
     * @see org.apache.jetspeed.portlet.PortletLog#info(java.lang.String)
     */
    public void info(String arg0) {
    	log.info(arg0);
    }

    /**
     * @see org.apache.jetspeed.portlet.PortletLog#isWarnEnabled()
     */
    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    /**
     * @see org.apache.jetspeed.portlet.PortletLog#warn(java.lang.String)
     */
    public void warn(String arg0) {
    	log.warn(arg0);
    }

    /**
     * @see org.apache.jetspeed.portlet.PortletLog#isErrorEnabled()
     */
    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    /**
     * @see org.apache.jetspeed.portlet.PortletLog#error(java.lang.String)
     */
    public void error(String arg0) {
    	log.error(arg0);
    }

    /**
     * @see org.apache.jetspeed.portlet.PortletLog#error(java.lang.String, java.lang.Throwable)
     */
    public void error(String arg0, Throwable arg1) {
    	log.error(arg0, arg1);
    }

}
