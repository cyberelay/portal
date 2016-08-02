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
package org.cyberelay.portal.service.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.cyberelay.portal.PageDefinition;
import org.cyberelay.portal.PortalURL;
import org.cyberelay.portal.User;
import org.cyberelay.portal.impl.SoloPortletPageDefinition;
import org.cyberelay.portal.service.PageDefinitionService;
import org.cyberelay.portal.service.ServiceInitializationException;
import org.cyberelay.portal.util.PortalRequestUtil;
import org.cyberelay.portal.util.logging.Logger;
import org.cyberelay.portal.util.logging.LoggerFactory;

/**
 * @author Terry Li
 * 
 * <ul>
 * <li>Creation Date: 2007-7-14
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 684 $
 * <li>Last Update Time: $Date: 2008-03-23 10:24:06 +0000 (Sun, 23 Mar 2008) $
 * </ul>
 * 
 */
public class PageDefinitionServiceImpl extends AbstractPortalApplicationService implements
		PageDefinitionService {
	private static final Logger LOG = LoggerFactory.getLogger(PageDefinitionServiceImpl.class);

	private long lastModificationFlag = Long.MIN_VALUE;

	private Map<String, PageDefinition> pageDefinitions = new HashMap<String, PageDefinition>();

	public PageDefinition getPage(HttpServletRequest request) {
		PortalURL url = PortalRequestUtil.getRequestURL(request);
		User user = PortalRequestUtil.getUser(request);
		
		return getPage(url, user);
	}
	
	public PageDefinition getPage(PortalURL url, User user) {
		if(SoloPortletPageDefinition.isSoloPortletPageURL(url)) {
			return new SoloPortletPageDefinition(url);
		}
		return getPage(url.getPageID());
	}

	protected void init() throws ServiceInitializationException {
		try {
			refresh();
		} catch (PortalDefinitionParsingException e) {
			throw new ServiceInitializationException("Fail to parse portal definition.", e);
		}
	}

	public void refresh() throws PortalDefinitionParsingException {
		File[] files = getDefinitionFiles();

		long currentModificationFlag = getCurrentModificationFlag(files);
		if (lastModificationFlag == currentModificationFlag) {
			return;
		}
		PortalDefinitionParser parser = new PortalDefinitionParser();
		for (File file : files) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Parsing portal definition [" + file.getAbsolutePath() + "]...");
			}
			pageDefinitions.putAll(parser.parse(file));
		}

		lastModificationFlag = currentModificationFlag;

	}

	private long getCurrentModificationFlag(File[] files) {
		long flag = 0L;
		for (File file : files) {
			flag += file.lastModified();
		}

		return flag;
	}

	private File[] getDefinitionFiles() {
		String dir = getPortalApplication().getConfiguration().getHomeDirectory();

		return new File(dir).listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".xml");
			}
		});
	}

	private PageDefinition getPage(String pageID) {
		return pageDefinitions.get(pageID);
	}

}
