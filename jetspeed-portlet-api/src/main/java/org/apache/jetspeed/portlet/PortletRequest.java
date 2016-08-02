package org.apache.jetspeed.portlet;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public interface PortletRequest extends HttpServletRequest {

	Portlet.Mode getMode();

	Portlet.Mode getPreviousMode();

	void setModeModifier(Portlet.ModeModifier modemodifier) throws AccessDeniedException;

	String getMethod();

	ServletInputStream getInputStream() throws IOException;

	Locale getLocale();

	Enumeration getLocales();

	Client getClient();

	String getParameter(String s);

	Map getParameterMap();

	Enumeration getParameterNames();

	String[] getParameterValues(String s);

	void setAttribute(String s, Object obj);

	void removeAttribute(String s);

	Object getAttribute(String s);

	Enumeration getAttributeNames();

	boolean isSecure();

	PortletWindow getWindow();

	PortletData getData();

	PortletSession getPortletSession();

	PortletSession getPortletSession(boolean flag);

	Cookie[] getCookies();

	User getUser();

	PortletSettings getPortletSettings();

	long getDateHeader(String s);

	String getHeader(String s);

	Enumeration getHeaders(String s);

	Enumeration getHeaderNames();

	int getIntHeader(String s);

	void invalidateCache();
}
