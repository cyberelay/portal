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
package org.cyberelay.portletcontainer.legacy.descriptor.parser;

/**
 * @author Terry Li
 * 
 *
 */
interface XmlConstants {
	String VAL_TRUE = "true";
	String VAL_FALSE = "false";
	String VAL_PAGE = "page";
	String VAL_CONTROL = "control";
	String VAL_CONTAINER = "container";
	String VAL_H_CONTAINER = "h-container";
	String VAL_V_CONTAINER = "v-container";
	String VAL_UNDEFINED = "undefined";

	String AT_ID = "@id";
	String AT_NAME = "@name";
	String AT_HREF = "@href";
	String AT_CLASS = "@class";
	String AT_WIDTH = "@width";
	String AT_SCREENREF = "@screenref";
	String AT_THEMEREF = "@themeref";
	String AT_SKINREF = "@skinref";
	String AT_CONTEXTPATH = "@contextpath";
	String AT_PATH = "@path";
	String AT_DEFAULT = "@default";
	String AT_PORTLETCONTEXTREF = "@portletcontextref";
	String AT_PORTLETREF = "@portletref";
	String AT_TYPE = "@type";
	String AT_ACTIVE = "@active";
	String AT_UNIQUENAME = "@uniquename";
	String AT_OBJECTID = "@objectid";
	String AT_MARKUP = "@markup";
	String AT_LOCALE = "@locale";
	String AT_ORIENTATION = "@orientation";
	String AT_ORDINAL = "@ordinal";
	String AT_UID = "@uid";

	String ATTR_PATH = "path";
	String ATTR_TYPE = "type";
	String ATTR_UNIQUENAME = "uniquename";
	String ATTR_CONTEXTNAME = "contextname";
	String ATTR_CONTEXTPATH = "contextpath";
	String ATTR_DEFAULT = "default";
	String ATTR_THEMEREF = "themeref";
	String ATTR_SKINREF = "skinref";
	String ATTR_WIDTH = "width";
	String ATTR_ACTIVE = "active";
	String ATTR_PORTLETREF = "portletref";
	String ATTR_PORTLETAPPREF = "portletappref";
	String ATTR_PORTLETCONTEXTREF = "portletcontextref";
	String ATTR_ID = "id";
	String ATTR_CLASS = "class";
	String ATTR_NAME = "name";
	String ATTR_LOCALE = "locale";
	String ATTR_HREF = "href";
	String ATTR_MARKUP = "markup";
	String ATTR_ORDINAL = "ordinal";;
	String ATT_NAMESPACE = "namespace";

	String ELMT_PORTALDEF = "portaldef";
	String ELMT_TEMPLATE = "template";
	String ELMT_PORTLET_APP = "portlet-app";
	String ELMT_PORTLET_CONTEXT = "portlet-context";
	String ELMT_CONTENT_NODE = "content-node";
	String ELMT_PORTLETINSTANCE = "portletinstance";

	String ELMT_PORTLET_APP_DEF = "portlet-app-def";
	String ELMT_PORTLET_APP_NAME = "portlet-app-name";
	String ELMT_PORTLET = "portlet";
	String ELMT_PORTLET_NAME = "portlet-name";
	String ELMT_ALLOWS = "allows";
	String ELMT_SUPPORTS = "supports";
	String ELMT_SUPPORTED_MARKUP = "supported-markup";
	String ELMT_LOCALEDATA = "localedata";
	String ELMT_MARKUP = "markup";
	String ELMT_INIT_PARAM = "init-param";
	String ELMT_CONFIG_PARAM = "config-param";
	String ELMT_PARAM_NAME = "param-name";
	String ELMT_PARAM_VALUE = "param-value";
	String ELMT_CONCRETE_PORTLET_APP = "concrete-portlet-app";
	String ELMT_CONTEXT_PARAM = "context-param";
	String ELMT_CONCRETE_PORTLET = "concrete-portlet";
	String ELMT_DEFAULT_LOCALE = "default-locale";
	String ELMT_LANGUAGE = "language";
	String ELMT_TITLE = "title";
	String ELMT_TITLE_SHORT = "title-short";
	String ELMT_DESCRIPTION = "description";
	String ELMT_PORTLET_INSTANCE = "portletinstance";
	String ELMT_COMPONENT = "component";
}
