package org.apache.jetspeed.portlet.event;

import org.apache.jetspeed.portlet.PortletAction;

public interface ActionEvent extends Event {

    /**
     * @deprecated Method getAction is deprecated
     */
    PortletAction getAction();

    String getActionString();

    int ACTION_PERFORMED = 1001;
}
