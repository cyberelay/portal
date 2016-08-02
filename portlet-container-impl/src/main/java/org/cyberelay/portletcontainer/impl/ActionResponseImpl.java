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
import java.io.Serializable;
import java.util.Map;

import javax.portlet.ActionResponse;
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
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 613 $
 * <li>Last Update Time: $Date: 2008-03-03 03:41:46 +0000 (Mon, 03 Mar 2008) $
 * </ul>
 * 
 */
public class ActionResponseImpl extends PortletResponseImpl implements ActionResponse {

	public ActionResponseImpl(PortletInvocationRequest applicationRequest) {
		super(applicationRequest);
	}

	/**
	 * 
	 * @see javax.portlet.StateAwareResponse#getPortletMode()
	 */
	public PortletMode getPortletMode() {
		return getNavigationState().getPortletMode();
	}

	/**
	 * 
	 * @see javax.portlet.StateAwareResponse#getRenderParameterMap()
	 */
	public Map<String, String[]> getRenderParameterMap() {
		return getNavigationState().getPublicRenderParameterMap();
	}

	/**
	 * 
	 * @see javax.portlet.StateAwareResponse#getWindowState()
	 */
	public WindowState getWindowState() {
		return getNavigationState().getWindowState();
	}

	/**
	 * 
	 * @see javax.portlet.StateAwareResponse#setEvent(javax.xml.namespace.QName,
	 *      java.lang.Object)
	 */
	public void setEvent(QName name, Serializable value) {
		invocationRequest.sendEvent(new EventImpl(name, value));
	}

	/**
	 * 
	 * @see javax.portlet.StateAwareResponse#setEvent(java.lang.String,
	 *      java.lang.Object)
	 */
	public void setEvent(String name, Serializable value) {
		setEvent(new QName(getDefaultNamespace(), name), value);
	}

	/**
	 * 
	 * @see javax.portlet.StateAwareResponse#setPortletMode(javax.portlet.PortletMode)
	 */
	public void setPortletMode(PortletMode mode) throws PortletModeException {
		getNavigationState().setPortletMode(mode);
	}

	/**
	 * 
	 * @see javax.portlet.StateAwareResponse#setRenderParameter(java.lang.String,
	 *      java.lang.String)
	 */
	public void setRenderParameter(String key, String value) {
		setRenderParameter(key, new String[] { value });
	}

	/**
	 * 
	 * @see javax.portlet.StateAwareResponse#setRenderParameter(java.lang.String,
	 *      java.lang.String[])
	 */
	public void setRenderParameter(String key, String[] values) {
		getNavigationState().setPrivateRenderParameters(key, values);
	}

	/**
	 * 
	 * @see javax.portlet.StateAwareResponse#setRenderParameters(java.util.Map)
	 */
	public void setRenderParameters(Map<String, String[]> parameters) {
//		getNavigationState().setRenderParameters(parameters);
	}

	/**
	 * 
	 * @see javax.portlet.StateAwareResponse#setWindowState(javax.portlet.WindowState)
	 */
	public void setWindowState(WindowState windowState) throws WindowStateException {
		getNavigationState().setWindowState(windowState);
	}

	/* ===================================================== */

	protected String getDefaultNamespace() {
		return getPortletConfig().getDefaultNamespace();
	}

	public void sendRedirect(String location, String renderUrlParamName) throws IOException {
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
