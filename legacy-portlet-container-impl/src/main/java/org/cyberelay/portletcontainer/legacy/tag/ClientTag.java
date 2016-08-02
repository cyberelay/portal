package org.cyberelay.portletcontainer.legacy.tag;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

import org.apache.jetspeed.portlet.Client;

public class ClientTag extends PortletTagSupport {
	public static class TEI extends TagExtraInfo {
		public VariableInfo[] getVariableInfo(TagData tagData) {
			return new VariableInfo[] { new VariableInfo("client", Client.class.getName(), true,
					VariableInfo.AT_BEGIN) };
		}
	}

	public int doStartTag() {
		if (pageContext.getAttribute("client") == null) {
			pageContext.setAttribute("client", getPortletRequest().getClient());
		}

		return SKIP_BODY;
	}
}
