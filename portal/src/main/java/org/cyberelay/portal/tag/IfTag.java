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

import org.cyberelay.portal.PageDefinition;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 650 $
 * <li>Last Update Time: $Date: 2008-03-14 15:06:57 +0000 (Fri, 14 Mar 2008) $
 * </ul>
 * 
 */
public class IfTag extends PortalTagSupport {

	private static final long serialVersionUID = -2005509713781955276L;

	private Boolean loggedIn;

	private Boolean pageActive;

	private Boolean portletMaximized;

	public int doStartTag() {
		int result = EVAL_BODY_INCLUDE;

		try {
			if (loggedIn != null) {
				if (loggedIn.booleanValue() != isUserLoggedIn()) {
					return SKIP_BODY;
				}
			}

			if (portletMaximized != null) {
				boolean hasMaximizedPortlet = (getMaximizedPortletWindow() != null);
				if (portletMaximized.booleanValue() != hasMaximizedPortlet) {
					return SKIP_BODY;
				}
			}

			if (pageActive != null) {
				PageDefinition page = getRequestingPage();
//				boolean active = (page != null && page.isActive());
//				if (pageActive.booleanValue() != active) {
//					return SKIP_BODY;
//				}
			}

		} catch (Throwable t) {
			// TODO
			t.printStackTrace();
		}
		return result;
	}

	/**
	 * Check if the specified item has been included in the given list.
	 * 
	 * @param list
	 * @param item
	 * @return
	 */
	protected boolean included(String list, String item) {
		int listLength = list.length();
		int currentOffset = 0;
		char trailing;
		do {
			int stringStartPos;
			char preceeding;
			do {
				if (currentOffset > listLength) {
					return false;
				}
				stringStartPos = list.indexOf(item, currentOffset);
				if (-1 == stringStartPos) {
					return false;
				}
				currentOffset = stringStartPos + 1;
				if (stringStartPos <= 0) {
					break;
				}
				preceeding = list.charAt(stringStartPos - 1);
			} while (preceeding != ',' && preceeding != ';');

			int endPos = stringStartPos + item.length();
			if (endPos >= list.length()) {
				break;
			}
			trailing = list.charAt(endPos);
		} while (trailing != ',' && trailing != ';');

		return true;
	}

	public final int doEndTag() {
		resetCustomAttributes();
		return EVAL_PAGE;
	}

	private void resetCustomAttributes() {
		loggedIn = null;
		portletMaximized = null;
	}

	public void setLoggedIn(String aLoggedIn) {
		loggedIn = booleanOf(aLoggedIn);
	}

	public void setPortletMaximized(String aPortletMaximized) {
		portletMaximized = booleanOf(aPortletMaximized);
	}

	public void setPageActive(String active) {
		pageActive = booleanOf(active);
	}
}