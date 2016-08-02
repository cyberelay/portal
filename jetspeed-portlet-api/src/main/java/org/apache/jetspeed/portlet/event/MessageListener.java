package org.apache.jetspeed.portlet.event;

import org.apache.jetspeed.portlet.PortletException;

public interface MessageListener {

    void messageReceived(MessageEvent messageEvent) throws PortletException;
}
