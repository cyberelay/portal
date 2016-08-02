package org.cyberelay.portletcontainer.legacy.tag;

import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.jetspeed.portlet.PortletData;

public class DataLoopTag extends PortletBodyTagSupport {

	public int doStartTag() {
		PortletData data = getPortletRequest().getData();
		if (data != null) {
			iAttributes = data.getAttributeNames();
		}
		return doAfterBody();
	}

	public int doAfterBody() {
		int result = SKIP_BODY;
		try {
			if (bodyContent != null) {
				bodyContent.writeOut(bodyContent.getEnclosingWriter());
				bodyContent.clearBuffer();
			}
			for (iAttribute = null; iAttributes != null && iAttributes.hasMoreElements()
					&& iAttribute == null;) {
				iAttribute = (String) iAttributes.nextElement();
				if (iPattern == null || iPattern.matcher(iAttribute).matches()) {
					pageContext.setAttribute("portletData", iAttribute);
					result = EVAL_BODY_AGAIN;
				} else {
					iAttribute = null;
				}
			}

		} catch (IOException exc) {
			getPortletContext().getLog().error("DataLoopTag: An I/O error occurred.", exc);
		}
		return result;
	}

	public int doEndTag() {
		try {
			if (bodyContent != null)
				bodyContent.writeOut(bodyContent.getEnclosingWriter());
		} catch (IOException exc) {
			getPortletContext().getLog().error("DataLoopTag: An I/O error occurred: ", exc);
		}
		resetCustomAttributes();
		return EVAL_PAGE;
	}

	private void resetCustomAttributes() {
		iAttribute = null;
		iAttributes = null;
		iPattern = null;
	}

	public void release() {
		pageContext.removeAttribute("portletData");
	}

	public void setPattern(String aPattern) {
		try {
			iPattern = Pattern.compile(aPattern);
		} catch (PatternSyntaxException exc) {
			getPortletContext().getLog().error("DataLoopTag: Pattern syntax exception: ", exc);
			iPattern = null;
		}
	}

	private Enumeration iAttributes;
	private String iAttribute;
	private Pattern iPattern;

}