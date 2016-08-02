package org.apache.jetspeed.portlet;

import java.io.IOException;

public interface PortletTitleListener {

	void doTitle(PortletRequest request, PortletResponse response)
			throws PortletException, IOException;

}
