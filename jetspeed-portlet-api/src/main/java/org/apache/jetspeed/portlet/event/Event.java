package org.apache.jetspeed.portlet.event;

import org.apache.jetspeed.portlet.Portlet;
import org.apache.jetspeed.portlet.PortletRequest;

public interface Event {

    PortletRequest getRequest();

    /**
     * @deprecated Method getPortlet is deprecated
     */
    Portlet getPortlet();
}
