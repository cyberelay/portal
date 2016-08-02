package org.cyberelay.portletcontainer.legacy.tag;

import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.jetspeed.portlet.PortletRequest;
import org.apache.jetspeed.portlet.PortletSettings;

public class SettingsLoopTag extends PortletBodyTagSupport {

	public int doStartTag() {
		PortletRequest portletRequest = getPortletRequest();
		PortletSettings settings = portletRequest.getPortletSettings();
		if (settings != null) {
			iAttributes = settings.getAttributeNames();
		}
		return doAfterBody();
	}

	public int doAfterBody() {
		int result = SKIP_BODY;
		try {
			if (super.bodyContent != null) {
				super.bodyContent.writeOut(super.bodyContent.getEnclosingWriter());
				super.bodyContent.clearBuffer();
			}
			for (iAttribute = null; iAttributes != null && iAttributes.hasMoreElements()
					&& iAttribute == null;) {
				iAttribute = (String) iAttributes.nextElement();
				if (iPattern == null || iPattern.matcher(iAttribute).matches()) {
					super.pageContext.setAttribute("portletSettings", iAttribute);
					result = EVAL_BODY_AGAIN;
				} else {
					iAttribute = null;
				}
			}

		} catch (IOException exc) {
			getPortletContext().getLog().error("SettingsLoopTag: An I/O error occurred.", exc);
		}
		return result;
	}

	public int doEndTag() {
		try {
			if (super.bodyContent != null)
				super.bodyContent.writeOut(super.bodyContent.getEnclosingWriter());
		} catch (IOException exc) {
			getPortletContext().getLog().error("SettingsLoopTag: An I/O error occurred: ", exc);
		}
		resetCustomAttributes();
		return 6;
	}

	private void resetCustomAttributes() {
		iAttribute = null;
		iAttributes = null;
		iPattern = null;
	}

	public void release() {
		super.pageContext.removeAttribute("portletSettings");
	}

	public void setPattern(String aPattern) {
		try {
			iPattern = Pattern.compile(aPattern);
		} catch (PatternSyntaxException exc) {
			getPortletContext().getLog().error("SettingsLoopTag: Pattern syntax exception: ", exc);
			iPattern = null;
		}
	}

	private Enumeration iAttributes;
	private String iAttribute;
	private Pattern iPattern;
}
