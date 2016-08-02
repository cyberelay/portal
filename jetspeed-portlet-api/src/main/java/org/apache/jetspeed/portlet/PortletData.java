package org.apache.jetspeed.portlet;

import java.io.IOException;
import java.util.Enumeration;

public interface PortletData {

	void setAttribute(String name, Object value) throws AccessDeniedException;

	Object getAttribute(String name);

	Enumeration getAttributeNames();

	void removeAttribute(String name) throws AccessDeniedException;

	/**
	 * @deprecated Method removeAllAttributes is deprecated
	 */
	void removeAllAttributes() throws AccessDeniedException;

	void store() throws AccessDeniedException, IOException;
}
