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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jul 9, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
public interface UIPane extends UIComponent {

	State MAXIMIZED = new State("Maximized");
	
	State MINIMIZED = new State("Minimized");

	State NORMAL = new State("Normal");
	
	class State {
		private String name;
		
		private State(String name) {
			this.name = name;			
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	State getState(HttpServletRequest req);
	
	boolean isActive();

	String getWidth();

	int getOrdinal();

}