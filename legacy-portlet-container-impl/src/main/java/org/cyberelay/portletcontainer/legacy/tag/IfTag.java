package org.cyberelay.portletcontainer.legacy.tag;

import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.jetspeed.portlet.Capability;
import org.apache.jetspeed.portlet.Client;
import org.apache.jetspeed.portlet.PortletRequest;
import org.apache.jetspeed.portlet.PortletWindow;

public class IfTag extends PortletTagSupport {

	public int doStartTag() {
		PortletRequest portletRequest = getPortletRequest();
		if (markup != null) {
			Client client = portletRequest.getClient();
			if (client != null && !included(markup, client.getMarkupName())) {
				return SKIP_BODY;
			}
		}
		if (mimetype != null) {
			Client client = portletRequest.getClient();
			if (client != null && !included(mimetype, client.getMimeType())) {
				return SKIP_BODY;
			}
		}
		if (mode != null && !included(mode, portletRequest.getMode())) {
			return SKIP_BODY;
		}
		if (previousMode != null && !included(previousMode, portletRequest.getPreviousMode())) {
			return SKIP_BODY;
		}
		if (state != null) {
			PortletWindow.State state = portletRequest.getWindow().getWindowState();
			if (state != null && !included(this.state, state)) {
				return SKIP_BODY;
			}
		}
		if (locale != null) {
			Locale locale = portletRequest.getLocale();
			if (!included(this.locale, locale.toString())
					&& !included(this.locale, locale.getLanguage())) {
				return SKIP_BODY;
			}
		}
		if (capability != null) {
			Client client = portletRequest.getClient();
			if (client != null && !includedCapabilities(capability, client)) {
				return SKIP_BODY;
			}
		}
		return EVAL_BODY_INCLUDE;
	}

	public final int doEndTag() {
		resetCustomAttributes();
		return EVAL_PAGE;
	}

	public void setMode(String aMode) {
		mode = aMode;
	}

	public void setPreviousMode(String aPreviousMode) {
		previousMode = aPreviousMode;
	}

	public void setState(String aState) {
		state = aState;
	}

	public void setLocale(String aLocale) {
		locale = aLocale;
	}

	public void setMarkup(String aMarkup) {
		markup = aMarkup;
	}

	public void setMimetype(String aMimetype) {
		mimetype = aMimetype;
	}

	public void setCapability(String aCapability) {
		capability = aCapability;
	}

	private void resetCustomAttributes() {
		mode = null;
		previousMode = null;
		state = null;
		markup = null;
		mimetype = null;
		capability = null;
	}

	private boolean included(String aList, Object aItem) {
		boolean result = false;
		if (aList != null && aItem != null) {
			StringTokenizer tokenizer = new StringTokenizer(aList, ",;");
			String aItemString = aItem.toString();
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				if (token.equalsIgnoreCase(aItemString)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	private boolean includedCapabilities(String aList, Client aClient) {
		if (aList != null && aClient != null) {
			for (StringTokenizer tokenizer = new StringTokenizer(aList, ",;"); tokenizer
					.hasMoreTokens();) {
				String token = tokenizer.nextToken();
				if (aClient.isCapableOf(Capability.forIdentifier(token.toUpperCase())))
					return true;
			}

		}
		return false;
	}

	private String mode;
	private String previousMode;
	private String state;
	private String locale;
	private String markup;
	private String mimetype;
	private String capability;
}
