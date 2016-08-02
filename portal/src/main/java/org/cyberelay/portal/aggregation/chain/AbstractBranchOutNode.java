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
package org.cyberelay.portal.aggregation.chain;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyberelay.portal.aggregation.BranchOutNode;
import org.cyberelay.portal.aggregation.RequestProcessChain;

/**
 * @author Roger Tang
 * 
 */
public abstract class AbstractBranchOutNode extends GenericProcessNode implements BranchOutNode {
	private RequestProcessChain branch;

	public void init() {
		branch.initChain(getPortalApplication());
	}

	/**
	 * @see org.cyberelay.portal.aggregation.RequestProcessNode#process(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public final void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (isBranchOutRequired(request, response)) {
			getBranch().process(request, response);
			return;
		}

		processNext(request, response);
	}

	public final RequestProcessChain getBranch() {
		return branch;
	}

	protected final void setBranch(RequestProcessChain branch) {
		this.branch = branch;
	}
}
