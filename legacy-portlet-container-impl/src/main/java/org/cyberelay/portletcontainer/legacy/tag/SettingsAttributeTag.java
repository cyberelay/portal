package org.cyberelay.portletcontainer.legacy.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.jetspeed.portlet.PortletSettings;

public class SettingsAttributeTag extends PortletTagSupport {

	public int doStartTag() throws JspException {
		try {
			PortletSettings settings = getPortletRequest().getPortletSettings();
			if (settings != null) {
				if (name == null) {
					name = (String) pageContext.getAttribute("portletSettings");
				}
				if (name != null) {
					String iValue = settings.getAttribute(name);
					if (iValue != null) {
						pageContext.getOut().print(iValue);
					}
				}
			}
			name = null;
		} catch (IOException exc) {
			getPortletContext().getLog().error(
					"SettingsAttributeTag: doStartTag() Error writing to output stream: ", exc);
		}
		return SKIP_BODY;
	}

	public void setName(String aName) {
		name = aName;
	}

	private String name;
}