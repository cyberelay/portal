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

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import javax.portlet.CacheControl;
import javax.portlet.PortletMode;
import javax.portlet.RenderResponse;

import org.cyberelay.portletcontainer.PortletInvocationRequest;

/**
 * @author Roger Tang
 * 
 */
public class RenderResponseImpl extends PortletResponseImpl implements
		RenderResponse {

	public RenderResponseImpl(PortletInvocationRequest applicationRequest) {
		super(applicationRequest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.portlet.RenderResponse#setNextPossiblePortletModes(java.util.Enumeration)
	 */
	public void setNextPossiblePortletModes(
			Collection<PortletMode> portletModes) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.portlet.RenderResponse#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.portlet.MimeResponse#getCacheControl()
	 */
	public CacheControl getCacheControl() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.portlet.MimeResponse#getPortletOutputStream()
	 */
	public OutputStream getPortletOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
