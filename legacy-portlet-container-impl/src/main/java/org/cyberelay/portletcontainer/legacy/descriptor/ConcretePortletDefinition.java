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

package org.cyberelay.portletcontainer.legacy.descriptor;

import java.util.Locale;
import java.util.Map;

/**
 * 
 * @author Roger Tang
 * 
 * <ul>
 * <li>Creation Date: Jan 21, 2008
 * <li>Last Editor: $Author: losingant $
 * <li>Current Revision: $Revision: 507 $
 * <li>Last Update Time: $Date: 2008-02-13 14:17:14 +0000 (Wed, 13 Feb 2008) $
 * </ul>
 * 
 */
public interface ConcretePortletDefinition {

    String getUniqueName();

    String getAbstractPortletName();

    String getConcretePortletApplicationName();

    String getParameter(String name);

    Map<String, String> getParameters();

    Locale getDefaultLocale();

    String getTitle(Locale locale);

    String getTitle();

    String getShortTitle(Locale locale);

    String getShortTitle();

    String getDescription(Locale locale);

    String getDescription();

    LocaleData getLocaleData(Locale locale);
    
    Locale[] getSupportedLocales();
    
    LocaleData[] getLocaleData();
    
    class LocaleData {
        private Locale locale;
        private String title;
        private String shortTitle;
        private String description;

        public LocaleData(Locale locale) {
            this.locale = locale;
        }
        
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Locale getLocale() {
            return locale;
        }

        public String getShortTitle() {
            return shortTitle;
        }

        public void setShortTitle(String shortTitle) {
            this.shortTitle = shortTitle;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
