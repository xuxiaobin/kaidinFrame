/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util;

/**
 * 默认值工具
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年12月29日 下午7:54:32
 */
public abstract class DefaultUtil {
	/**
	 * 如果为空返回默认值
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static <T> T ifNull(T obj, T defaultValue) {
		return null != obj ? obj : defaultValue;
	}

	/**
	 * 如果为空返回默认值
	 * @param str
	 * @param defaultStr
	 * @return
	 */
	public static String ifEmpty(String str, String defaultStr) {
		return StringUtil.isEmpty(str) ? defaultStr : str;
	}
}
