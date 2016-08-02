package org.apache.jetspeed.portlet;

import java.io.IOException;

public interface PortletPageListener {

	void beginPage(PortletRequest request, PortletResponse response)
			throws PortletException, IOException;

	void endPage(PortletRequest request, PortletResponse response)
			throws PortletException, IOException;
}
