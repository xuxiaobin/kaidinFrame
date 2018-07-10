/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.appframe.api;

import java.util.Iterator;

/**
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年7月10日 上午11:12:50
 */
public class ApiInvocation implements Invocation {
	private OpenApi<BaseResponseData, BaseRequestData> api;
	private final Iterator<ApiInterceptor>             interceptorChain;

	/**
	 * 构造函数传入拦截器链
	 * 
	 * @param interceptorChain
	 */
	public ApiInvocation(final Iterator<ApiInterceptor> interceptorChain) {
		this.interceptorChain = interceptorChain;
	}

	@Override
	public void invoke() {
		if (null != interceptorChain && interceptorChain.hasNext()) {
			ApiInterceptor apiInterceptor = interceptorChain.next();
			apiInterceptor.intercept(this);
			return;
		}
		BaseResponseData responseData = api.execute();
	}

}
