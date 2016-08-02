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
package org.cyberelay.portletcontainer;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

/**
 * @author Roger Tang
 * 
 */
public final class PortletContainerInfo {
	private static final Set<String> SUPPORTED_RUNTIME_OPTIONS;
	
	static {
		Set<String> set = new HashSet<String>();
		set.add("javax.portlet.actionScopedRequestAttributes");
		
		SUPPORTED_RUNTIME_OPTIONS = Collections.unmodifiableSet(set);
	}
	
	private PortletContainerInfo() {
		// disregard.
	}

	public static boolean isContainerEventName(QName name) {
		//TODO FIXME
		return false;
	}
	
	public static String getContainerEventType(QName name) {
		//TODO FIXME
		return null;
	}
	
	public static Enumeration<String> getSupportedRuntimeOptions() {
		return Collections.enumeration(SUPPORTED_RUNTIME_OPTIONS);
	}
	
	public static boolean isRuntimeOptionSupported(String option) {
		return SUPPORTED_RUNTIME_OPTIONS.contains(option);
	}
	
	public static String getName() {
		return "Cyberelay Portlet Container";
	}

	public static String getInfo() {
		return getName() + "/" + getMajorVersion() + "." + getMinorVersion();
	}

	public static int getMajorVersion() {
		return 1;
	}

	public static int getMinorVersion() {
		return 0;
	}
	
	public static int getSupportedMajorPortletVersion() {
		return 2;
	}
	
	public static int getSupportedMinorPortletVersion() {
		return 0;
	}
	
}
