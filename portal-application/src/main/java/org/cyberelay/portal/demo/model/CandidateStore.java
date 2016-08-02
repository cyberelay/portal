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
package org.cyberelay.portal.demo.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.cyberelay.portal.util.PropertiesUtil;

/**
 * @author losingant
 * 
 */
public class CandidateStore {
	private static final CandidateStore INSTANCE = new CandidateStore();

	private Map<String, Candidate> candidates = new HashMap<String, Candidate>();

	protected CandidateStore() {
		load();
	}

	public static final CandidateStore getInstance() {
		return INSTANCE;
	}

	public Candidate getCandidate(String name) {
		return (Candidate) candidates.get(name);
	}

	public void saveCandidate(Candidate candidate) {
		candidates.put(candidate.getName(), candidate);
	}

	public Iterator<String> getCandidateNames() {
		return candidates.keySet().iterator();
	}

	public int size() {
		return candidates.size();
	}

	public void store() {

	}

	public void load() {
		Properties properties = PropertiesUtil
				.resourceToProperties("org/cyberelay/portal/demo/candidates.properties");
		for (int i = 1; i < 1000; i++) {
			String name = properties.getProperty("candidates[" + i + "].name");
			if (name == null) {
				break;
			}
			Candidate candidate = new Candidate();
			candidate.setName(name);
			candidate.setComments(properties.getProperty("candidates[" + i
					+ "].comments"));
			candidate.setExperiences(properties.getProperty("candidates[" + i
					+ "].experiences"));
			candidate.setLanguages(properties.getProperty("candidates[" + i
					+ "].languages"));
			candidate.setSkillset(properties.getProperty("candidates[" + i
					+ "].skillset"));
			candidate.setStatus(properties.getProperty("candidates[" + i
					+ "].status"));

			saveCandidate(candidate);
		}
	}
}
