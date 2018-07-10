/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.appframe.api.engine;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.kaidin.appframe.api.ApiInvocation;
import com.kaidin.appframe.api.ApiService;
import com.kaidin.appframe.api.BaseRequestData;
import com.kaidin.appframe.api.BaseResponseData;
import com.kaidin.appframe.api.DispatchContext;
import com.kaidin.appframe.api.OpenApi;
import com.kaidin.appframe.api.annotation.Api;
import com.kaidin.appframe.api.annotation.Biz;
import com.kaidin.appframe.api.interceptor.ApiInterceptor;

/**
 * 核心引擎实现
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年7月10日 下午3:38:49
 */
public class ApiEngineImpl implements ApiEngine {
	/** api服务 */
	@Resource
	private ApiService           apiService;
	/** 拦截器 */
	private List<ApiInterceptor> interceptorList = new ArrayList<>();

	/**
	 * @see com.kaidin.appframe.api.engine.ApiEngine#run(com.kaidin.appframe.api.DispatchContext)
	 */
	@Override
	public String run(DispatchContext dispatchContext) {
		OpenApi<BaseResponseData, BaseRequestData> openapi = apiService.getApi(dispatchContext.getBiz(),
		        dispatchContext.getAction());
		run(openapi, dispatchContext);

		return dispatchContext.getResponse();
	}

	/**
	 * @see com.kaidin.appframe.api.engine.ApiEngine#run(java.lang.Class, com.kaidin.appframe.api.BaseRequestData)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <Res extends BaseResponseData, Req extends BaseRequestData> Res run(
	        Class<? extends OpenApi<? extends Res, ? extends Req>> apiClazz, Req requestData) {
		OpenApi<? extends BaseResponseData, ? extends BaseRequestData> openApi = (OpenApi<? extends BaseResponseData, ? extends BaseRequestData>) apiService
		        .getApi(apiClazz);
		Biz biz = apiClazz.getPackage().getAnnotation(Biz.class);
		String bizName = biz.value();
		Api api = apiClazz.getAnnotation(Api.class);
		String action = api.value();
		DispatchContext dispatchContent = new DispatchContext();
		dispatchContent.setBiz(bizName);
		dispatchContent.setAction(action);
		dispatchContent.setRequestDate(requestData);
		run(openApi, dispatchContent);

		return (Res) dispatchContent.getResponseDate();
	}

	/**
	 * 根据上下文信息来运行指定的API
	 * @param openApi
	 * @param dispatchContent
	 */
	private void run(OpenApi<? extends BaseResponseData, ? extends BaseRequestData> openApi, DispatchContext dispatchContent) {
		if (null == dispatchContent) {
			dispatchContent = new DispatchContext();
		}
		if (null == openApi) {
			dispatchContent.setRequestDate(null);
			return;
		}

		ApiInvocation invocation = new ApiInvocation(interceptorList.iterator());
		invocation.setDispatchContext(dispatchContent);
		invocation.setApi(openApi);
		invocation.invoke();
	}

}
