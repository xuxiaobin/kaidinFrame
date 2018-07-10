/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.appframe.api;

/**
 * openApi
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年7月10日 上午11:12:50
 */
public interface OpenApi<Res extends BaseResponseData, Req extends BaseRequestData> {
	/**
	 * 客户端请求执行入口
	 * 
	 * @return
	 */
	Res execute();

	/**
	 * api实际执行入口
	 * 
	 * @param request
	 * @return
	 */
	Res execute(Req request);
}
