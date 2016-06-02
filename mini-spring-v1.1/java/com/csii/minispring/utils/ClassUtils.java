package com.csii.minispring.utils;

import java.lang.reflect.Method;

/**
 * util method to operate Class.
 * 
 * @author CSII Rongbo
 * 
 */
public abstract class ClassUtils {

	/**
	 * get default class loader.
	 * @author Rongbo
	 */
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
			throw new RuntimeException(ex);
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = ClassUtils.class.getClassLoader();
		}
		return cl;
	}

	/**
	 * get setter method by method name.if not found, throw NoSuchMethodException
	 * @param methodName,method name.
	 * @param clazz,the class obj.
	 * @return the setter method object.
	 * @throws NoSuchMethodException
	 */
	public static Method getSetterMethod(String methodName, Class clazz) throws NoSuchMethodException {
		AssertUtils.notEmpty(methodName, "method name can not be null or empty.");
		AssertUtils.notNull(clazz, "clazz can not be null.");
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (methodName.equals(method.getName()) 
					&& method.getParameterTypes().length == 1) {
				return method;
			}
		}
		// not found set method, throw this exception.
		throw new NoSuchMethodException("method name:" + methodName);
	}
}
