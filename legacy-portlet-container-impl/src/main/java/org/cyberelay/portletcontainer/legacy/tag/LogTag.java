package org.cyberelay.portletcontainer.legacy.tag;

import org.apache.jetspeed.portlet.PortletConfig;
import org.apache.jetspeed.portlet.PortletContext;
import org.apache.jetspeed.portlet.PortletLog;

public class LogTag extends PortletTagSupport {

	public int doStartTag() {
		PortletConfig portletConfig = getPortletConfig();
		PortletContext portletContext = portletConfig.getContext();
		PortletLog portletLog = portletContext.getLog();
		if (level == null || level.equalsIgnoreCase("ERROR")) {
			portletLog.error(text);
		} else if (level.equalsIgnoreCase("WARN")) {
			portletLog.warn(text);
		} else if (level.equalsIgnoreCase("INFO")) {
			portletLog.info(text);
		} else if (level.equalsIgnoreCase("DEBUG")) {
			portletLog.debug(text);
		}

		return SKIP_BODY;
	}

	public void setText(String aText) {
		text = aText;
	}

	public void setLevel(String aLevel) {
		level = aLevel;
	}

	private String text;
	private String level;
}
