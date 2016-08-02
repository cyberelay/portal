package org.apache.jetspeed.portlet;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public interface Portlet {
	public static class Mode implements Serializable {
		private static final Map ALL_MODES = new HashMap(4);

		public static final Mode VIEW;
		public static final Mode EDIT;
		public static final Mode HELP;
		public static final Mode CONFIGURE;

		private static final Mode MODES[];
		private String identifier;
		private int value;

		static {
			VIEW = new Mode("View", 0);
			EDIT = new Mode("Edit", 1);
			HELP = new Mode("Help", 2);
			CONFIGURE = new Mode("Configure", 3);
			MODES = (new Mode[] { VIEW, EDIT, HELP, CONFIGURE });
		}

		public static Mode forIdentifier(String identifier) {
			if (identifier == null) {
				return null;
			}
			return (Mode) ALL_MODES.get(identifier.toLowerCase());
		}

		public int getId() {
			return value;
		}

		public String toString() {
			return identifier;
		}

		public Object readResolve() throws ObjectStreamException {
			try {
				return MODES[value];
			} catch (IndexOutOfBoundsException e) {
				throw new InvalidObjectException("Unknown Portlet Mode");
			}
		}

		private Mode(String identifier, int value) {
			this.identifier = identifier;
			this.value = value;
			ALL_MODES.put(identifier.toLowerCase(), this);
		}

		private static final long serialVersionUID = -1107635724990140693L;
	}

	public static class ModeModifier implements Serializable {

		public static final ModeModifier REQUESTED;
		public static final ModeModifier CURRENT;
		public static final ModeModifier PREVIOUS;

		private static final ModeModifier MODE_MODIFIERS[];

		private String identifier;
		private int value;

		static {
			REQUESTED = new ModeModifier("Requested", 0);
			CURRENT = new ModeModifier("Current", 1);
			PREVIOUS = new ModeModifier("Previous", 2);
			MODE_MODIFIERS = (new ModeModifier[] { REQUESTED, CURRENT, PREVIOUS });
		}

		public int getId() {
			return value;
		}

		public String toString() {
			return identifier;
		}

		public Object readResolve() throws ObjectStreamException {
			try {
				return MODE_MODIFIERS[value];
			} catch (IndexOutOfBoundsException e) {
				throw new InvalidObjectException("Unknown Portlet ModeModifier");
			}
		}

		private ModeModifier(String identifier, int value) {
			this.identifier = identifier;
			this.value = value;
		}

		private static final long serialVersionUID = 2240757459655346100L;
	}

	PortletConfig getPortletConfig();

	void init(PortletConfig config) throws UnavailableException;

	void initConcrete(PortletSettings settings) throws UnavailableException;

	void service(PortletRequest request, PortletResponse response)
			throws PortletException, IOException;

	void destroyConcrete(PortletSettings settings);

	void destroy(PortletConfig config);
}
