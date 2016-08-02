package org.apache.jetspeed.portlet;

import java.util.Enumeration;
import javax.servlet.http.HttpSession;

public interface PortletSession extends HttpSession {

	/**
	 * @deprecated Method getUser is deprecated
	 */
	User getUser();

	long getCreationTime();

	long getLastAccessedTime();

	void setAttribute(String name, Object value);

	Object getAttribute(String name);

	Enumeration getAttributeNames();

	void removeAttribute(String name);
}
