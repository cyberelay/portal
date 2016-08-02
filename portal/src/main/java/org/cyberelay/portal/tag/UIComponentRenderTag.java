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

package org.cyberelay.portal.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.cyberelay.portal.ui.UIComponent;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 664 $
 * <li>Last Update Time: $Date: 2008-03-18 06:25:27 +0000 (Tue, 18 Mar 2008) $
 * </ul>
 * 
 */
public class UIComponentRenderTag extends PortalTagSupport {

	private static final long serialVersionUID = -5102287776728880322L;
	
	private UIComponent component;
	
	public int doStartTag() throws JspException {
		try {
			getComponent().render(getHttpRequest(), createIncludeResponse());
		} catch (IOException exc) {
			throw new JspException(exc);
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return SKIP_BODY;
	}

	public void setComponent(UIComponent component) {
		this.component = component;
	}
	
	protected UIComponent getComponent() {
		return this.component;
	}
}
