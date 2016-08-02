package org.cyberelay.portletcontainer.legacy.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.jetspeed.portlet.PortletResponse;

public class EncodeURITag extends PortletTagSupport {

	public int doStartTag() throws JspException {
		PortletResponse portletResponse = getPortletResponse();
		try {
			pageContext.getOut().print(portletResponse.encodeURI(path));
		} catch (IOException exc) {
			getPortletContext().getLog().error(
					"EncodeURITag: doStartTag() Error writing to output stream: ", exc);
		}
		return 0;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private String path;
}
