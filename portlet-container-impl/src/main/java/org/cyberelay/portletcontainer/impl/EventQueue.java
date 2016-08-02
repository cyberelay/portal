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
package org.cyberelay.portletcontainer.impl;

import java.util.LinkedList;
import java.util.Queue;

import javax.portlet.Event;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jul 19, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 22:17:14 +0800 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
class EventQueue {
	private Queue<Event> queue = new LinkedList<Event>();

	/**
	 * Inserts the given event into this queue.
	 * 
	 * @param event
	 */
	public void offer(Event event) {
		queue.offer(event);
	}

	/**
	 * Removes all events from this queue.
	 */
	public void clear() {
		queue.clear();
	}

	/**
	 * Queries if this queue is empty.
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	/**
	 * Retrieves, but does not remove, the head event of this queue, returning
	 * null if this queue is empty.
	 * 
	 * @return
	 */
	public Event peek() {
		return queue.peek();
	}

	/**
	 * Retrieves and removes the head event of this queue, or null if this queue
	 * is empty.
	 * 
	 * @return
	 */
	public Event poll() {
		return queue.poll();
	}

}