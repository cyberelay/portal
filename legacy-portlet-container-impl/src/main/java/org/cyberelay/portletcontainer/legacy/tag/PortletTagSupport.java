package org.cyberelay.portletcontainer.legacy.tag;

import javax.servlet.jsp.tagext.TagSupport;

import org.apache.jetspeed.portlet.PortletConfig;
import org.apache.jetspeed.portlet.PortletContext;
import org.apache.jetspeed.portlet.PortletRequest;
import org.apache.jetspeed.portlet.PortletResponse;
import org.cyberelay.portletcontainer.PortletContainer;
import org.cyberelay.portletcontainer.legacy.Constants;
import org.cyberelay.portletcontainer.legacy.PortletApplication;
import org.cyberelay.portletcontainer.legacy.PortletWindowEx;

public class PortletTagSupport extends TagSupport implements Constants {
	protected PortletRequest getPortletRequest() {
		return (PortletRequest) pageContext.getRequest().getAttribute(PORTLET_REQUEST);
	}

	protected PortletResponse getPortletResponse() {
		return (PortletResponse) pageContext.getRequest().getAttribute(PORTLET_RESPONSE);
	}

	protected PortletContext getPortletContext() {
		return getPortletApplication().getPortletContext();
	}

	protected PortletApplication getPortletApplication() {
		return (PortletApplication) pageContext.getServletContext().getAttribute(
				PORTLET_APPLICATION);
	}

	protected PortletConfig getPortletConfig() {
		return getPortletApplication().getPortletConfig(getPortletWindowEx());
	}

	protected PortletContainer getPortletContainer() {
		return (PortletContainer) pageContext.getRequest().getAttribute(
				PortletContainer.PORTLET_CONTAINER);
	}

	protected PortletWindowEx getPortletWindowEx() {
		return (PortletWindowEx) getPortletRequest().getAttribute(PORTLET_WINDOW_EX);
	}
}
