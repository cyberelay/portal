
package org.cyberelay.portletcontainer.legacy.tag;

import org.apache.jetspeed.portlet.PortletURI;

public class URIActionTag extends PortletTagSupport {

    public int doStartTag() {
        PortletURI portletURI = (PortletURI) pageContext.getAttribute("portletURI");
        if (portletURI != null) {
            portletURI.addAction(action);
        }
        pageContext.setAttribute("portletURI", portletURI);
        action = null;
        return SKIP_BODY;
    }

    public void setName(String name) {
        action = name;
    }

    private String action;
}
