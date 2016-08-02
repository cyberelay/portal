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

package org.cyberelay.portal.ui.impl;

import org.cyberelay.portal.ui.UIPane;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jul 9, 2007
 * <li>Last Editor: $Author: jxszlh $
 * <li>Current Revision: $Revision: 527 $
 * <li>Last Update Time: $Date: 2008-02-22 02:24:01 +0000 (Fri, 22 Feb 2008) $
 * </ul>
 * 
 */
public abstract class UIPaneImpl extends AbstractUIComponent implements UIPane, Comparable {
	private int ordinal;
	private boolean active;
	private String width;

	public UIPaneImpl(Template template) {
		super(template);
	}

	/**
	 * 
	 * @see org.cyberelay.portal.ui.UIPane#getOrdinal()
	 */
	public int getOrdinal() {
		return ordinal;
	}

	/**
	 * 
	 * @see org.cyberelay.portal.ui.UIPane#getWidth()
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * 
	 * @see org.cyberelay.portal.ui.UIPane#isActive()
	 */
	public boolean isActive() {
		return active;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public int compareTo(Object o) {
		int result = 0;
		if (o instanceof UIPane) {
			result = getOrdinal() - ((UIPane) o).getOrdinal();
		}

		return result == 0 ? (hashCode() - o.hashCode()) : result;
	}
}
