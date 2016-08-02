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
package org.cyberelay.portal.util.xml;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Terry Li
 * 
 * <ul>
 * <li>Creation Date: Jul 10, 2007
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */


public abstract class AnnotationManager implements IAnnotationManager{

	private Map<String, Class<? extends Model>> elementMap;
	private Map<String, Map<String, Field>> propertyMap;

	public AnnotationManager() {
		elementMap = new HashMap<String, Class<? extends Model>>();
		propertyMap = new HashMap<String, Map<String, Field>>();
		initModelAnnotation();
	}

	private void initModelAnnotation() {
		ModelClass modelClass = this.getClass().getAnnotation(ModelClass.class);
		if(modelClass == null){
			return;
		}
	    Class<? extends Model>[] modelClasses = modelClass.models();
	    
	    for (Class<? extends Model> clazz : modelClasses) {
			XMLElementName xmlElementName = clazz.getAnnotation(XMLElementName.class);
			String elementName;
			if(xmlElementName != null){
				elementName = xmlElementName.element();
			}else{
				elementName = clazz.getSimpleName().toLowerCase();
			}
			elementMap.put(elementName, clazz);
			propertyMap.put(elementName, new HashMap<String, Field>());
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				XMLPropertyName xmlPropertyName = field.getAnnotation(XMLPropertyName.class);
				if(xmlPropertyName != null){
					propertyMap.get(elementName).put(xmlPropertyName.property(), field);
				}else{
					propertyMap.get(elementName).put(field.getName(), field);
				}
			}
		}
	}

	public Class<? extends Model> getElementClass(String elementName) {
		return elementMap.get(elementName);
	}

	public Field getPropertyField(String elementName, String propertyName) {
		Map<String, Field> methodMap = propertyMap.get(elementName);
		if (methodMap != null) {
			return methodMap.get(propertyName);
		}
		return null;
	}
}
