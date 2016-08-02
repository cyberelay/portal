package org.cyberelay.portletcontainer.legacy.tag;

import java.io.IOException;

import org.apache.jetspeed.portlet.PortletURI;

public class CreateReturnURITag extends PortletBodyTagSupport {

	public int doStartTag() {
		pageContext.setAttribute("portletURI", getPortletResponse().createReturnURI());
		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag() {
		try {
			PortletURI portletURI = (PortletURI) pageContext.getAttribute("portletURI");
			if (portletURI != null) {
				pageContext.getOut().print(portletURI.toString());
			}
			pageContext.removeAttribute("portletURI");
		} catch (IOException exc) {
			getPortletContext().getLog().error(
					"CreateReturnURITag: doStartTag() Error writing to output stream: ", exc);
		}
		return EVAL_PAGE;
	}

}
