package org.apache.jetspeed.portlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;
import javax.servlet.ServletContext;
import org.apache.jetspeed.portlet.service.PortletService;
import org.apache.jetspeed.portlet.service.PortletServiceNotFoundException;
import org.apache.jetspeed.portlet.service.PortletServiceUnavailableException;

public interface PortletContext extends ServletContext {

	String getInitParameter(String name);

	Enumeration getInitParameterNames();

	void setAttribute(String name, Object value);

	Object getAttribute(String name);

	Enumeration getAttributeNames();

	void removeAttribute(String name);

	void include(String path, PortletRequest request, PortletResponse response)
			throws PortletException, IOException;

	InputStream getResourceAsStream(String path);

	InputStream getResourceAsStream(String path, Client client, Locale locale);

	String getText(String bundle, String key, Locale locale);

	/**
	 * @deprecated Method send is deprecated
	 */
	void send(String targetPortletName, PortletMessage message, PortletRequest request)
			throws AccessDeniedException;

	void send(String targetPortletName, PortletMessage message) throws AccessDeniedException;

	void send(String targetPortletName, DefaultPortletMessage message) throws AccessDeniedException;

	PortletService getService(Class clazz)
			throws PortletServiceUnavailableException, PortletServiceNotFoundException;

	int getMajorVersion();

	int getMinorVersion();

	String getContainerInfo();

	PortletLog getLog();
}
