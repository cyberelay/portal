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
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

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
public class SAXModelHandler extends BaseContentHandler {

	private Model rootModel;

	private Stack<Model> modelStack;

	private IAnnotationManager annotationMgr;

	public SAXModelHandler(IAnnotationManager annotationMgr) {
		this.annotationMgr = annotationMgr;
		modelStack = new Stack<Model>();
	}

	@Override
	protected void doStartElement(String uri, String localName, String name,
			Attributes atts) throws SAXException {
		Class<? extends Model> modelClass = annotationMgr.getElementClass(name);
		if (modelClass == null) {
			throw new SAXException("element:" + name + " not supported");
		}
		Model model;
		try {
			model = modelClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SAXException("error in class " + modelClass.getName()
					+ " initialization", e);
		}
		setModelAttributes(name, model, atts);
		if (!modelStack.isEmpty()) {
			try {
				modelStack.peek().addSubElement(model);
			} catch (ModelException e) {
				e.printStackTrace();
				throw new SAXException(e);
			}
		} else {
			rootModel = model;
		}
		modelStack.push(model);
	}

	@Override
	protected void doEndElement(String uri, String localName, String name)
			throws SAXException {
		modelStack.pop();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cyberelay.portal.util.BaseContentHandler#ignorableWhitespace(char[],
	 *      int, int)
	 */
	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		Model currentElement = modelStack.peek();
		if (currentElement instanceof TextBodyOwner) {
			((TextBodyOwner) currentElement).setTextBody(new String(ch, start,
					length));
		} else {
			throw new SAXException("element:" + getCurrentFullPath()
					+ " not a TextBodyOwner instance");
		}

		super.ignorableWhitespace(ch, start, length);
	}

	private void setModelAttributes(String elementName, Model model,
			Attributes atts) throws SAXException {
		for (int i = 0; i < atts.getLength(); i++) {
			String property = atts.getQName(i);
			Field field = annotationMgr.getPropertyField(elementName, property);
			if (field == null) {
				throw new SAXException("can not find field for [element:"
						+ elementName + ";property:" + property + "]");
			}
			try {
				field.set(model, atts.getValue(i));
			} catch (Exception e) {
				e.printStackTrace();
				throw new SAXException("error in invocation of field " + field
						+ " with object:" + model + "; value"
						+ atts.getValue(i), e);
			}
		}
	}

	public Model getRootModel() {
		return this.rootModel;
	}

	
}
