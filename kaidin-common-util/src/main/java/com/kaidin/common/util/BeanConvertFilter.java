/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util;

/**
 * 转换工具过滤器
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public interface BeanConvertFilter<T> {
	/**
	 * 过滤逻辑
	 * @param obj
	 * @return
	 */
	public boolean doFilter(T obj);
}
