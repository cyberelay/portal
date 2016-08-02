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
package org.cyberelay.portal.util;

import java.lang.reflect.Method;

/**
 * @author Terry Li
 * 
 */
public class ReflectionUtil {

	public static Object invoke(Object obj, String methodName) {
		Class clazz = obj.getClass();
		Method method = null;
		try {
			method = clazz.getMethod(methodName);
		} catch (Exception e) {
			throw new RuntimeException("Can not get the method["
					+ methodName
					+ "] from the class["
					+ clazz.getName()
					+ "]! caused by: "
					+ e.getMessage(), e);
		}
		try {
			return method.invoke(obj);
		} catch (Exception e) {
			throw new RuntimeException("Unexpected exception occurs while invoking the method["
					+ methodName
					+ "] in the class["
					+ clazz.getName()
					+ "]! caused by: "
					+ e.getMessage(), e);
		}
	}

	public static Object newInstance(String clazz) throws ObjectInstantiationException {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			return classLoader.loadClass(clazz).newInstance();
		} catch (ClassNotFoundException e) {
			throw new ObjectInstantiationException("[" + clazz + "] not found!", e);
		} catch (InstantiationException e) {
			throw new ObjectInstantiationException("[" + clazz + "] instantiation error!", e);
		} catch (IllegalAccessException e) {
			throw new ObjectInstantiationException("Unexpected exception with instantiation of ["
					+ clazz
					+ "] ", e);
		}
	}

}
