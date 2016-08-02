package org.cyberelay.portletcontainer.legacy.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.jetspeed.portlet.PortletData;

public class DataAttributeTag extends PortletTagSupport {

	public int doStartTag() throws JspException {
		try {
			PortletData data = getPortletRequest().getData();
			if (data != null) {
				if (name == null) {
					name = (String) pageContext.getAttribute("portletData");
				}
				if (name != null) {
					String iValue = (String) data.getAttribute(name);
					if (iValue != null) {
						pageContext.getOut().print(iValue);
					}
				}
			}
			name = null;
		} catch (IOException exc) {
			getPortletContext().getLog().error(
					"DataAttributeTag: doStartTag() Error writing to output stream: ", exc);
		}
		return SKIP_BODY;
	}

	public void setName(String aName) {
		name = aName;
	}

	private String name;
}
