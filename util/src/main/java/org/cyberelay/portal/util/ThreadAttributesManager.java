package org.cyberelay.portal.util;

import java.util.HashMap;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
public class ThreadAttributesManager {
	private static class ThreadLocalAttributes extends ThreadLocal {

		public Object initialValue() {
			return new HashMap();
		}

		public Object getAttribute(String name) {
			HashMap map = (HashMap) super.get();
			return map.get(name);
		}

		public void setAttribute(String name, Object value) {
			HashMap map = (HashMap) super.get();
			map.put(name, value);
		}

		public void removeAttribute(String name) {
			HashMap map = (HashMap) super.get();
			map.remove(name);
		}

		public void clear() {
			HashMap map = (HashMap) super.get();
			map.clear();
		}

		private ThreadLocalAttributes() {
		}

	}

	public ThreadAttributesManager() {
	}

	public static Object getAttribute(String name) {
		return attributes.getAttribute(name);
	}

	public static void setAttribute(String name, Object value) {
		attributes.setAttribute(name, value);
	}

	public static void removeAttribute(String name) {
		attributes.removeAttribute(name);
	}

	public static void clear() {
		attributes.clear();
	}

	private static ThreadLocalAttributes attributes = new ThreadLocalAttributes();

}
