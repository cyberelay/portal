package org.cyberelay.portletcontainer.legacy.tag;

import java.io.IOException;

import org.apache.jetspeed.portlet.PortletResponse;
import org.apache.jetspeed.portlet.PortletURI;
import org.apache.jetspeed.portlet.PortletWindow;

public class CreateURITag extends PortletBodyTagSupport {

	public int doStartTag() {
		PortletResponse portletResponse = getPortletResponse();
		try {
			PortletURI portletURI;
			if (this.state == null) {
				portletURI = portletResponse.createURI();
			} else {
				PortletWindow.State state = PortletWindow.State.forIdentifier(this.state);
				if (state == null) {
					portletURI = portletResponse.createURI();
				} else {
					portletURI = portletResponse.createURI(state);
				}
			}
			pageContext.setAttribute("portletURI", portletURI);
		} catch (Throwable t) {
			getPortletContext().getLog().error(
					"CreateURITag: doStartTag() Error with state parameter: ", t);
		}
		this.state = null;
		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag() {
		try {
			PortletURI portletURI = (PortletURI) super.pageContext.getAttribute("portletURI");
			if (portletURI != null) {
				pageContext.getOut().print(portletURI.toString());
			}
			pageContext.removeAttribute("portletURI");
		} catch (IOException exc) {
			getPortletContext().getLog().error(
					"CreateURITag: doStartTag() Error writing to output stream: ", exc);
		}
		return EVAL_PAGE;
	}

	public void setState(String aState) {
		state = aState;
	}

	private String state;
}
