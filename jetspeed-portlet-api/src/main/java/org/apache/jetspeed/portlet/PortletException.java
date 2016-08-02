package org.apache.jetspeed.portlet;

import java.io.PrintStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;

public class PortletException extends ServletException {

	public PortletException() {
	}

	public PortletException(String text) {
		super(text);
	}

	public PortletException(String text, Throwable cause) {
		super(text, cause);
	}

	public PortletException(Throwable cause) {
		super(cause);
	}

	public void printStackTrace() {
		printStackTrace(System.err);
	}

	public void printStackTrace(PrintStream out) {
		printStackTrace(new PrintWriter(out, true));
	}

	public void printStackTrace(PrintWriter out) {
		super.printStackTrace(out);
		if (getRootCause() != null) {
			out.println();
			out.print("Nested Exception is ");
			getRootCause().printStackTrace(out);
		}
	}
}
