package org.apache.jetspeed.portlet;

import java.io.PrintStream;
import java.io.PrintWriter;

public class UnavailableException extends PortletException {
	private Portlet portlet;
	private boolean permanent;
	private int seconds;

	/**
	 * @deprecated Method UnavailableException is deprecated
	 */
	public UnavailableException(Portlet portlet, String msg) {
		super(msg);
		this.portlet = portlet;
		permanent = true;
	}

	/**
	 * @deprecated Method UnavailableException is deprecated
	 */
	public UnavailableException(int seconds, Portlet portlet, String msg) {
		super(msg);
		this.portlet = portlet;
		if (seconds <= 0)
			this.seconds = -1;
		else
			this.seconds = seconds;
		permanent = false;
	}

	public UnavailableException(String msg) {
		super(msg);
		permanent = true;
	}

	public UnavailableException(String msg, int seconds) {
		super(msg);
		if (seconds <= 0)
			this.seconds = -1;
		else
			this.seconds = seconds;
		permanent = false;
	}

	public boolean isPermanent() {
		return permanent;
	}

	/**
	 * @deprecated Method getPortlet is deprecated
	 */
	public Portlet getPortlet() {
		return portlet;
	}

	public int getUnavailableSeconds() {
		return permanent ? -1 : seconds;
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
