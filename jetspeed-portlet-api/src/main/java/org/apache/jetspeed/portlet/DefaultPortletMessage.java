package org.apache.jetspeed.portlet;

public final class DefaultPortletMessage implements PortletMessage {

	public DefaultPortletMessage() {
		message = null;
	}

	public DefaultPortletMessage(String message) {
		this.message = null;
		setMessage(message);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String toString() {
		return "DefaultPortletMessage: message=[" + message + "]";
	}

	private String message;
}
