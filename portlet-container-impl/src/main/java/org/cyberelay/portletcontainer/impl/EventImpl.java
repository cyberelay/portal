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

import java.io.Serializable;

import javax.portlet.Event;
import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import org.cyberelay.portal.util.Assert;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jun 25, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 622 $
 * <li>Last Update Time: $Date: 2008-03-05 09:43:16 +0000 (Wed, 05 Mar 2008) $
 * </ul>
 * 
 */
class EventImpl implements Event {
	private QName qname;
	private Serializable value;

	public EventImpl(QName qname, Serializable value) {
		Assert.notNull(qname, "Event name cannot be null!");
		
		this.qname = qname;
		this.value = value;
	}

	/**
	 * @see javax.portlet.Event#getName()
	 */
	public String getName() {
		return qname.getLocalPart();
	}

	/**
	 * @see javax.portlet.Event#getQName()
	 */
	public QName getQName() {
		return qname;
	}

	/**
	 * @see javax.portlet.Event#getValue()
	 */
	public Serializable getValue() {
		return value;
	}
	
	public static void main(String[] args) {
		System.out.println(new QName("{http://www.cyberelay.org/portal}", "name"));
	}

	@Override
	public String toString() {
		return "QName = [" + qname + "], payload = [" + value + "]";
	}
}
