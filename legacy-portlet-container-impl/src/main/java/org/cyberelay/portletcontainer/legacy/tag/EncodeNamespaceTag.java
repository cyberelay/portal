package org.cyberelay.portletcontainer.legacy.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.jetspeed.portlet.PortletResponse;

public class EncodeNamespaceTag extends PortletTagSupport {

	public int doStartTag() throws JspException {
		PortletResponse portletResponse = getPortletResponse();
		try {
			pageContext.getOut().print(portletResponse.encodeNamespace(value));
		} catch (IOException exc) {
			getPortletContext().getLog().error(
					"EncodeNamespaceTag: doStartTag() Error writing to output stream: ", exc);
		}
		return SKIP_BODY;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String value;
}
