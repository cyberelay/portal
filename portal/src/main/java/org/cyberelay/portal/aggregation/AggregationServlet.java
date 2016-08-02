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

package org.cyberelay.portal.aggregation;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyberelay.portal.PortalApplication;
import org.cyberelay.portal.PortalConstants;
import org.cyberelay.portal.aggregation.chain.RequestProcessChainImpl;
import org.cyberelay.portal.util.Assert;
import org.cyberelay.portal.util.ObjectInstantiationException;
import org.cyberelay.portal.util.ReflectionUtil;
import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;

/**
 * @author Roger Tang
 * 
 */
public class AggregationServlet extends HttpServlet implements Servlet, PortalConstants {
	private static final String REQUEST_PROCESS_CHAIN = "portal.request.process.chain";

	private static final Logger LOG = LoggerFactory.getLogger(AggregationServlet.class);

	private RequestProcessChain processChain;

	private PortalApplication portal;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		portal = (PortalApplication) config.getServletContext().getAttribute(PORTAL_APPLICATION);
		Assert.notNull(portal);
		initProcessChain();
		LOG.info("Aggregation Servlet Process Chain = " + processChain);
	}

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			processChain.process(req, res);
		} catch (ServletException e) {
			// TODO. Aggregation Servlet Error handling here.
			throw e;
		}
	}

	/* =========================================================================================== */

	private void initProcessChain() throws ServletException {
		String[] nodes = portal.getConfiguration().getValues(REQUEST_PROCESS_CHAIN);
		processChain = new RequestProcessChainImpl();
		for (String nodeClass : nodes) {
			processChain.append(createNode(nodeClass));
		}
		processChain.initChain(portal);
	}

	private RequestProcessNode createNode(String nodeClass) throws ServletException {
		LOG.debug("Initializing [" + nodeClass + "]...");
		try {
			return (RequestProcessNode) ReflectionUtil.newInstance(nodeClass);
		} catch (ObjectInstantiationException e) {
			throw new ServletException("Cannot create RequestProcessNode! class = ["
					+ nodeClass
					+ "]", e);
		}
	}


}