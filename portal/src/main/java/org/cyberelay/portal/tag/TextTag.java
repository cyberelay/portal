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

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
public class TextTag extends PortalTagSupport {
	private String bundle;

	private String key;

	private String text;

	private List<String> params;

	public int doStartTag() {
		try {
			Locale locale = getHttpRequest().getLocale();
			ResourceBundle resourceBundle = ResourceBundle.getBundle(bundle, locale);
			if (key == null || resourceBundle == null) {
				text = key;
			} else {
				text = resourceBundle.getString(key);
			}
			pageContext.getOut().print(text);
		} catch (MissingResourceException exc) {
			// TODO
			exc.printStackTrace();
		} catch (Throwable t) {
			// TODO
			t.printStackTrace();
		}
		return SKIP_BODY;
	}

	public void setBundle(String aBundle) {
		bundle = aBundle;
	}

	public void setKey(String aKey) {
		key = aKey;
	}

	void addParam(String param) {
		if (params == null) {
			params = new LinkedList<String>();
		}
		params.add(param);
	}
}
