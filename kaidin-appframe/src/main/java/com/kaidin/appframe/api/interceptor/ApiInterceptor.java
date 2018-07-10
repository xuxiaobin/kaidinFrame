/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.appframe.api.interceptor;

import com.kaidin.appframe.api.ApiInvocation;

/**
 * API处理拦截器
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年7月10日 下午6:10:06
 */
public interface ApiInterceptor {
	/**
	 * 拦截器
	 * @param invocation
	 * @return
	 */
	String intercept(ApiInvocation invocation);
}
