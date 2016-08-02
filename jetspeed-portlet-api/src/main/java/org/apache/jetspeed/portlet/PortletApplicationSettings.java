package org.apache.jetspeed.portlet;

import java.io.IOException;
import java.util.Enumeration;

public interface PortletApplicationSettings {

	void setAttribute(String name, String value) throws AccessDeniedException;

	String getAttribute(String name);

	Enumeration getAttributeNames();

	void removeAttribute(String name) throws AccessDeniedException;

	void store() throws AccessDeniedException, IOException;
}
