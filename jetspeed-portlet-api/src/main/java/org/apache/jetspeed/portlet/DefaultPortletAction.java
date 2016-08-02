package org.apache.jetspeed.portlet;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @deprecated Class DefaultPortletAction is deprecated
 */
public final class DefaultPortletAction implements PortletAction, Serializable {
	private String name;
	private Map<String, Object> parameters;

	public DefaultPortletAction(String name) {
		this.name = name;
		parameters = new HashMap<String, Object>();
	}

	public String getName() {
		return name;
	}

	public void addParameter(String name, Object value) {
		parameters.put(name, value);
	}

	public Map<String, Object> getParameters() {
		return Collections.unmodifiableMap(parameters);
	}

	private static final long serialVersionUID = -2455799358939341518L;

}
