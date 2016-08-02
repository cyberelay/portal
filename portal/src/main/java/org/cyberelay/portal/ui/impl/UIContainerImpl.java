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

package org.cyberelay.portal.ui.impl;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.cyberelay.portal.ui.ContainerPane;
import org.cyberelay.portal.ui.UIPane;

/**
 * @author Roger Tang
 * 
 */
public class UIContainerImpl extends UIPaneImpl implements ContainerPane {
	
	private Orientation orientation;
	
	private Set<UIPane> children = new TreeSet<UIPane>();

	public UIContainerImpl(Template template) {
		super(template);
	}

	public void addChild(UIPane component) {
		if (component != null) {
			children.add(component);
		}
	}

	public Iterator<UIPane> children() {
		return children.iterator();
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = Orientation.toOrientation(orientation);
	}

	public State getState(HttpServletRequest req) {
		//TODO 
		return NORMAL;
	}
}
