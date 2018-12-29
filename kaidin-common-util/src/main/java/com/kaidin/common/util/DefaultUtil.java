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
	 * @param drfaultValue
	 * @return
	 */
	public static <T> T ifNull(T obj, T drfaultValue) {
		return null != obj ? obj : drfaultValue;
	}

	/**
	 * 如果为空返回默认值
	 * @param str
	 * @param drfaultStr
	 * @return
	 */
	public static String ifEmpty(String str, String drfaultStr) {
		return StringUtil.isEmpty(str) ? drfaultStr : str;
	}
}
