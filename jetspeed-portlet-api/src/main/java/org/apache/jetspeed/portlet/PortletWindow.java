package org.apache.jetspeed.portlet;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public interface PortletWindow {
	public static class State implements Serializable {
		private static Map allStates = new HashMap();

		public static State forIdentifier(String identifier) {
			if (identifier == null) {
				return null;
			}
			return (State) allStates.get(identifier.toLowerCase());
		}

		public int getId() {
			return value;
		}

		public String toString() {
			return identifier;
		}

		public Object readResolve() throws ObjectStreamException {
			try {
				return STATES[value];
			} catch (IndexOutOfBoundsException e) {
				throw new InvalidObjectException("Unknown Portlet State");
			}
		}

		public static final State NORMAL;
		public static final State MAXIMIZED;
		public static final State MINIMIZED;

		/**
		 * @deprecated Field DETACHED is deprecated
		 */
		public static final State DETACHED;

		/**
		 * @deprecated Field MOVING is deprecated
		 */
		public static final State MOVING;

		/**
		 * @deprecated Field RESIZING is deprecated
		 */
		public static final State RESIZING;

		/**
		 * @deprecated Field CLOSED is deprecated
		 */
		public static final State CLOSED;

		public static final State SOLO;

		private static final State STATES[];

		private String identifier;
		private int value;

		static {
			NORMAL = new State("Normal", 0);
			MAXIMIZED = new State("Maximized", 1);
			MINIMIZED = new State("Minimized", 2);
			DETACHED = new State("Detached", 3);
			MOVING = new State("Moving", 4);
			RESIZING = new State("Resizing", 5);
			CLOSED = new State("Closed", 6);
			SOLO = new State("Solo", 7);
			STATES = (new State[] { NORMAL, MAXIMIZED, MINIMIZED, DETACHED, MOVING, RESIZING,
					CLOSED, SOLO });
		}

		private State(String identifier, int value) {
			this.identifier = identifier;
			this.value = value;
			allStates.put(identifier.toLowerCase(), this);
		}

		private static final long serialVersionUID = -8595705707041860550L;
	}

	/**
	 * @deprecated Method isDetached is deprecated
	 */
	boolean isDetached();

	/**
	 * @deprecated Method isMaximized is deprecated
	 */
	boolean isMaximized();

	/**
	 * @deprecated Method isMinimized is deprecated
	 */
	boolean isMinimized();

	State getWindowState();

	void setWindowState(State state) throws AccessDeniedException;
}
