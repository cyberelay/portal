package org.apache.jetspeed.portlet.event;

import org.apache.jetspeed.portlet.PortletException;

public interface ActionListener {

    void actionPerformed(ActionEvent actionEvent) throws PortletException;
}
