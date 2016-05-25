package com.csii.minispring.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AssertUtils {

	private static final Log logger = LogFactory.getLog(BeanUtils.class);

	/**
	 * @param object,the object you want to check.
	 * @param msg,error messages.
	 * @throws IllegalArgumentException ,if the object is null,throw IllegalArgumentException.
	 * @author CSII Rongbo
	 */
	public static void notNull(Object object, String msg) {
		if (object == null) {
			logger.error(msg);
			throw new IllegalArgumentException(msg);
		}
	}

	/**
	 * check if a string is empty or null.
	 * @author Rongbo
	 */
	public static void notEmpty(String arg,String msg) {
		if(arg == null || "".equals(arg)) {
			throw new IllegalArgumentException(msg);
		}
	}

}
