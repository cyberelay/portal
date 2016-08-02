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

package org.cyberelay.portletcontainer.impl;

import javax.portlet.CacheControl;
import javax.portlet.MimeResponse;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jul 21, 2007 
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 498 $
 * <li>Last Update Time: $Date: 2008-02-12 14:21:51 +0000 (Tue, 12 Feb 2008) $
 * </ul>
 * 
 */
public class CacheControlImpl implements CacheControl {
	private MimeResponse response;
	
	public CacheControlImpl(MimeResponse response) {
		this.response = response;
	}

	public String getETag() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.portlet.CacheControl#getExpirationTime()
	 */
	public int getExpirationTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see javax.portlet.CacheControl#isPublicScope()
	 */
	public boolean isPublicScope() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.portlet.CacheControl#setETag(java.lang.String)
	 */
	public void setETag(String token) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.portlet.CacheControl#setExpirationTime(int)
	 */
	public void setExpirationTime(int time) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.portlet.CacheControl#setPublicScope(boolean)
	 */
	public void setPublicScope(boolean publicScope) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.portlet.CacheControl#setUseCachedContent(boolean)
	 */
	public void setUseCachedContent(boolean useCachedContent) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.portlet.CacheControl#useCachedContent()
	 */
	public boolean useCachedContent() {
		// TODO Auto-generated method stub
		return false;
	}

}
