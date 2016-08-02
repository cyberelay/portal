package org.apache.jetspeed.portlet;

import java.util.Enumeration;

public interface User {

	String getID();

	String getUserID();

	String getGivenName();

	String getFamilyName();

	String getFullName();

	String getNickName();

	long getLastLoginTime();

	Object getAttribute(String s);

	Enumeration getAttributeNames();

	void setUserID(String s);

	void setGivenName(String s);

	void setFamilyName(String s);

	void setNickName(String s);

	void setAttribute(String s, Object obj);

	boolean isModifiable();
}
