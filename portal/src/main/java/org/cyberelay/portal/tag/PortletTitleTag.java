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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * @author Roger Tang
 * 
 */
public class PortletTitleTag extends PortletControlTag {
	private static final long serialVersionUID = -3472688293458412804L;

	private String id;

	public static class TEI extends TagExtraInfo {
		public VariableInfo[] getVariableInfo(TagData aTagData) {
			String id = aTagData.getAttributeString("id");
			if (id != null) {
				return new VariableInfo[] { new VariableInfo(
						id,
						"java.lang.String",
						true,
						VariableInfo.AT_BEGIN) };
			}
			return new VariableInfo[0];
		}
	}

	public PortletTitleTag() {
	}

	public int doStartTag() throws JspException {
		int result = SKIP_BODY;
		try {
			// PortletWindowRunData windowRunData =
			// getRenderingPortletWindowRunData();
			// if (windowRunData != null) {
			// pageContext.getOut()
			// .print(
			// windowRunData.getConcretePortletDefinition()
			// .getTitle());
			// }
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return result;
	}

	public void resetCustomAttributes() {
		id = null;
	}

	public void setId(String aID) {
		id = aID.trim();
		if (id.length() == 0)
			throw new IllegalArgumentException(
					"PortletTitleTag: Attribute \"id\" cannot be an empty string.");
	}
}