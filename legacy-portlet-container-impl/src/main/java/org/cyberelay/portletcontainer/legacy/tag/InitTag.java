package org.cyberelay.portletcontainer.legacy.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

import org.apache.jetspeed.portlet.PortletConfig;
import org.apache.jetspeed.portlet.PortletRequest;
import org.apache.jetspeed.portlet.PortletResponse;

public class InitTag extends PortletTagSupport {
	public static class TEI extends TagExtraInfo {
		public VariableInfo[] getVariableInfo(TagData tagData) {
			VariableInfo info[] = {
					new VariableInfo("portletRequest", PortletRequest.class.getName(), true,
							VariableInfo.AT_BEGIN),
					new VariableInfo("portletResponse", PortletResponse.class.getName(), true,
							VariableInfo.AT_BEGIN),
					new VariableInfo("portletConfig", PortletConfig.class.getName(), true,
							VariableInfo.AT_BEGIN) };
			return info;
		}
	}

	public InitTag() {
	}

	public int doStartTag() throws JspException {
		PortletRequest portletRequest = getPortletRequest();
		PortletResponse portletResponse = getPortletResponse();
		PortletConfig portletConfig = getPortletConfig();

		if (pageContext.getAttribute("portletRequest") == null)
			pageContext.setAttribute("portletRequest", portletRequest, 1);
		if (pageContext.getAttribute("portletResponse") == null)
			pageContext.setAttribute("portletResponse", portletResponse, 1);
		if (pageContext.getAttribute("portletConfig") == null)
			pageContext.setAttribute("portletConfig", portletConfig, 1);

		return SKIP_BODY;
	}
}