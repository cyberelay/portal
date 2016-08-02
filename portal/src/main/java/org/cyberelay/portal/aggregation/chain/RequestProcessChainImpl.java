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

import org.cyberelay.portal.PortalApplication;
import org.cyberelay.portal.aggregation.RequestProcessChain;
import org.cyberelay.portal.aggregation.RequestProcessNode;
import org.cyberelay.portal.util.Assert;

/**
 * @author Roger Tang
 * 
 */
public class RequestProcessChainImpl implements RequestProcessChain {

	private RequestProcessNode chainFirstNode;

	public RequestProcessChainImpl() {
		// TODO To implement configurable chain.
	}

	/**
	 * @see org.cyberelay.portal.aggregation.RequestProcessChain#initChain(org.cyberelay.portal.PortalApplication)
	 */
	public void initChain(PortalApplication portal) {
		RequestProcessNode node = chainFirstNode;
		while (node != null) {
			node.init(portal);
			node = node.getNext();
		}
	}

	/**
	 * @see org.cyberelay.portal.aggregation.RequestProcessChain#process(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void process(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		if (chainFirstNode != null)
			chainFirstNode.process(req, res);
	}

	/**
	 * 
	 * @see org.cyberelay.portal.aggregation.RequestProcessChain#append(org.cyberelay.portal.aggregation.RequestProcessNode)
	 */
	public void append(RequestProcessNode node) {
		Assert.notNull(node);
		node.setNext(null);

		if (chainFirstNode == null) {
			chainFirstNode = node;
			return;
		}

		RequestProcessNode current = chainFirstNode;
		RequestProcessNode next = current.getNext();
		while (next != null) {
			current = next;
			next = current.getNext();
		}
		current.setNext(node);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Portal Request Process Chain {\n");
		RequestProcessNode current = chainFirstNode;
		int index = 0;
		while (current != null) {
			sb.append("--Node[");
			sb.append(index++);
			sb.append("] = ");
			sb.append(current.getClass().getName());
			sb.append("\n");

			current = current.getNext();
		}
		sb.append("}");
		return sb.toString();
	}

}
