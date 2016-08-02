package org.cyberelay.portletcontainer.legacy.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

public class PortletBodyTagSupport extends PortletTagSupport implements BodyTag {

	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	public void setBodyContent(BodyContent b) {
		bodyContent = b;
	}

	public void doInitBody() throws JspException {
	}

	public int doAfterBody() throws JspException {
		return SKIP_BODY;
	}

	public void release() {
		bodyContent = null;
		super.release();
	}

	public BodyContent getBodyContent() {
		return bodyContent;
	}

	public JspWriter getPreviousOut() {
		return bodyContent.getEnclosingWriter();
	}

	protected BodyContent bodyContent;
}
