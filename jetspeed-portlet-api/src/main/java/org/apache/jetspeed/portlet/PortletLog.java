package org.apache.jetspeed.portlet;

public interface PortletLog {

	boolean isDebugEnabled();

	void debug(String s);

	boolean isInfoEnabled();

	void info(String s);

	boolean isWarnEnabled();

	void warn(String s);

	boolean isErrorEnabled();

	void error(String s);

	void error(String s, Throwable throwable);
}
