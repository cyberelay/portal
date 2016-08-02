/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cyberelay.portal.ui;

import java.util.Iterator;

/**
 * @author Roger Tang
 * 
 *
 */
public interface ContainerPane extends UIPane {

	Orientation VERTICAL = new Orientation("Vertical");

	Orientation HORIZONTAL = new Orientation("Horizontal");

	/**
	 * return an iterator of UIComponent type objects.
	 * 
	 * @return
	 */
	Iterator<UIPane> children();

	Orientation getOrientation();

	class Orientation {
		private String name;

		private Orientation(String name) {
			this.name = name;
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof Orientation && ((Orientation) obj).getName().equals(name);
		}

		public String getName() {
			return name;
		}

		@Override
		public int hashCode() {
			return name.hashCode();
		}

		public static Orientation toOrientation(String value) {
			if ("V".equalsIgnoreCase(value) || "vertical".equalsIgnoreCase(value)) {
				return VERTICAL;
			} else if ("H".equalsIgnoreCase(value) || "horizontal".equalsIgnoreCase(value)) {
				return HORIZONTAL;
			}

			return null;
		}
	}
}
