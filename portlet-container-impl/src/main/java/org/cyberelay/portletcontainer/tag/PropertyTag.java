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
package org.cyberelay.portletcontainer.tag;

import javax.servlet.jsp.JspException;

/**
 * The class is copied from org.apache.pluto.tags.* and has been changed to fit
 * in cyberelay portlet container entity model.
 * 
 * A tag handler for the <CODE>property</CODE> tag. Defines a propery that can
 * be added to a <CODE>actionURL</CODE>, a <CODE>resourceURL</CODE> or a
 * <CODE>renderURL</CODE> <BR>
 * The following attributes are mandatory:
 * <UL>
 * <LI><CODE>name</CODE>
 * <LI><CODE>value</CODE>
 * </UL>
 * 
 * @version 2.0
 */
public class PropertyTag extends PortletTagSupport {
	private static final long serialVersionUID = 3849870122181798227L;

	private String name;
	private String value;

	/**
	 * Processes the <CODE>param</CODE> tag.
	 * 
	 * @return <CODE>SKIP_BODY</CODE>
	 */
	public int doStartTag() throws JspException {
		BaseURLTag urlTag = (BaseURLTag) findAncestorWithClass(this, BaseURLTag.class);

		if (urlTag == null) {
			throw new JspException(
					"the 'param' Tag must have actionURL, renderURL or resourceURL as a parent");
		}

		urlTag.addProperty(getName(), getValue());

		return SKIP_BODY;
	}

	/**
	 * Returns the name.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the value.
	 * 
	 * @return String
	 */
	public String getValue() throws JspException {
		if (value == null) {
			value = "";
		}
		return value;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            The value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
