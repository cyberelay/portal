package org.apache.jetspeed.portlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;

public interface PortletSettings {

	Locale getDefaultLocale();

	String getTitle(Locale locale, Client client);

	void setAttribute(String name, String value) throws AccessDeniedException;

	String getAttribute(String name);

	Enumeration getAttributeNames();

	void removeAttribute(String value) throws AccessDeniedException;

	void store() throws AccessDeniedException, IOException;

	PortletApplicationSettings getApplicationSettings();
}
