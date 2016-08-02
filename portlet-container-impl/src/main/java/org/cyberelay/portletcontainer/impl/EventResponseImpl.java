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

import java.io.Serializable;
import java.util.Map;

import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.xml.namespace.QName;

import org.cyberelay.portletcontainer.PortletInvocationRequest;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

/**
 * @author Roger Tang
 * 
 */
public class EventResponseImpl extends PortletResponseImpl implements
		EventResponse {

	public EventResponseImpl(PortletInvocationRequest applicationRequest) {
		super(applicationRequest);
	}

	/**
	 * 
	 * @see javax.portlet.EventResponse#setRenderParameters(javax.portlet.EventRequest)
	 */
	public void setRenderParameters(EventRequest request) {
		PortletInvocationRequest invReq =
				(PortletInvocationRequest) request.getAttribute(PORTLET_INVOCATION_REQUEST);
		if(invReq != null) {
			invReq.getNavigationState().store();
		}
	}

	/**
	 * 
	 * @see javax.portlet.StateAwareResponse#getPortletMode()
	 */
	public PortletMode getPortletMode() {
		return invocationRequest.getNavigationState().getPortletMode();
	}

	/**
	 * 
	 * @see javax.portlet.StateAwareResponse#getRenderParameterMap()
	 */
	public Map<String, String[]> getRenderParameterMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.portlet.StateAwareResponse#getWindowState()
	 */
	public WindowState getWindowState() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.portlet.StateAwareResponse#setEvent(javax.xml.namespace.QName,
	 *      java.lang.Object)
	 */
	public void setEvent(QName name, Serializable value) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.portlet.StateAwareResponse#setEvent(java.lang.String,
	 *      java.lang.Object)
	 */
	public void setEvent(String name, Serializable value) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.portlet.StateAwareResponse#setPortletMode(javax.portlet.PortletMode)
	 */
	public void setPortletMode(PortletMode portletMode)
			throws PortletModeException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.portlet.StateAwareResponse#setRenderParameter(java.lang.String,
	 *      java.lang.String)
	 */
	public void setRenderParameter(String key, String value) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.portlet.StateAwareResponse#setRenderParameter(java.lang.String,
	 *      java.lang.String[])
	 */
	public void setRenderParameter(String key, String[] values) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.portlet.StateAwareResponse#setRenderParameters(java.util.Map)
	 */
	public void setRenderParameters(Map<String, String[]> parameters) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.portlet.StateAwareResponse#setWindowState(javax.portlet.WindowState)
	 */
	public void setWindowState(WindowState windowState)
			throws WindowStateException {
		// TODO Auto-generated method stub

	}

	public void removePublicRenderParameter(String name) {
		// TODO Auto-generated method stub
		
	}

	public Element createElement(String tagName) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

}
