
package org.apache.jetspeed.portlet.event;

import org.apache.jetspeed.portlet.PortletException;

/**
 * @deprecated Interface WindowListener is deprecated
 */

public interface WindowListener {

    void windowDetached(WindowEvent windowevent) throws PortletException;

    void windowMaximized(WindowEvent windowevent) throws PortletException;

    void windowMinimized(WindowEvent windowevent) throws PortletException;

    void windowClosing(WindowEvent windowevent) throws PortletException;

    void windowClosed(WindowEvent windowevent) throws PortletException;

    void windowRestored(WindowEvent windowevent) throws PortletException;
}
