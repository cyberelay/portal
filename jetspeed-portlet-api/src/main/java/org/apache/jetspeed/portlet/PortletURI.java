package org.apache.jetspeed.portlet;

public interface PortletURI {

	void addParameter(String name, String value);

	/**
	 * @deprecated Method addAction is deprecated
	 */
	void addAction(PortletAction portletaction);

	void addAction(String action);

	String toString();
}
