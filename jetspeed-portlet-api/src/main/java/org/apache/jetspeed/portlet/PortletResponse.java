package org.apache.jetspeed.portlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public interface PortletResponse extends HttpServletResponse {

	PrintWriter getWriter() throws IOException;

	String getContentType();

	String getCharacterEncoding();

	/**
	 * @deprecated Method getCharacterSet is deprecated
	 */
	String getCharacterSet();

	PortletURI createURI();

	PortletURI createURI(PortletWindow.State state);

	PortletURI createReturnURI();

	/**
	 * @deprecated Method encodeURI is deprecated
	 */
	String encodeURI(String s);

	String encodeURL(String s);

	String encodeNamespace(String s);

	void addCookie(Cookie cookie);

	boolean containsHeader(String s);

	void setDateHeader(String s, long l);

	void addDateHeader(String s, long l);

	void setHeader(String s, String s1);

	void addHeader(String s, String s1);

	void setIntHeader(String s, int i);

	void addIntHeader(String s, int i);
}
