package org.cyberelay.portletcontainer.legacy.tag;

import java.io.IOException;
import java.util.MissingResourceException;

import javax.servlet.jsp.JspException;

import org.apache.jetspeed.portlet.PortletRequest;

public class TextTag extends PortletTagSupport {

	public int doStartTag() throws JspException {
		PortletRequest portletRequest = getPortletRequest();
		try {
			super.pageContext.getOut().print(
					getPortletContext().getText(bundle, key, portletRequest.getLocale()));
		} catch (IOException exc) {
			getPortletContext().getLog().error(
					"TextTag: doStartTag() Error writing to output stream: ", exc);
			return EVAL_BODY_INCLUDE;
		} catch (MissingResourceException exc) {
			getPortletContext().getLog().error(
					"TextTag: doStartTag() Error reading text from resource bundle: ", exc);
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	private String key;
	private String bundle;

}
