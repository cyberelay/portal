package org.apache.jetspeed.portlet;

import java.util.EventListener;
import javax.servlet.http.HttpServletRequest;

public interface RequestEventListener extends EventListener {

	void beginRequest(HttpServletRequest httpservletrequest);

	void endRequest(HttpServletRequest httpservletrequest);
}