
package org.cyberelay.portletcontainer.legacy.tag;

import org.apache.jetspeed.portlet.PortletURI;

public class URIParameterTag extends PortletTagSupport {

    public int doStartTag() {
        PortletURI portletURI = (PortletURI) pageContext.getAttribute("portletURI");
        if (portletURI != null) {
            portletURI.addParameter(paramName, paramValue);
        }
        pageContext.setAttribute("portletURI", portletURI);
        return SKIP_BODY;
    }

    public void setName(String name) {
        paramName = name;
    }

    public void setValue(String value) {
        paramValue = value;
    }

    private String paramName;
    private String paramValue;
}
