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
package org.cyberelay.portal;

import java.util.Iterator;

/**
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Aug 10, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
public interface Client {

	String getAttribute(String name);
	
	Iterator<String> getAttributeNames();	

	/**
	 * Returns the preferred content type for this client.
	 * <p>
	 * The content type only includes the MIME type (e.g. "html/text"), not the
	 * character set.
	 * <p>
	 * 
	 * @return preferred MIME type of this client
	 */
	public String getAcceptedContentType();

	/**
	 * Gets a list of content types which the client expected. This list is
	 * ordered with the most preferable types listed first.
	 * 
	 * @return ordered list of MIME types for this client
	 */
	public Iterator<String> getAcceptedContentTypes();

}
