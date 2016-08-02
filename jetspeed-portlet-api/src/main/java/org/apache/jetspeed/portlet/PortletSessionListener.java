package org.apache.jetspeed.portlet;

public interface PortletSessionListener {

	void login(PortletRequest request) throws PortletException;

	void logout(PortletSession session) throws PortletException;
}
