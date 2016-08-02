package org.apache.jetspeed.portlet.event;

import org.apache.jetspeed.portlet.PortletMessage;

public interface MessageEvent extends Event {

    PortletMessage getMessage();

    int MESSAGE_RECEIVED = 1001;
}
