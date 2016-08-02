package org.apache.jetspeed.portlet;

import javax.servlet.ServletConfig;

public interface PortletConfig extends ServletConfig {

	String getName();

	boolean supports(PortletWindow.State state);

	boolean supports(Portlet.Mode mode, Client client);

	PortletContext getContext();
}
