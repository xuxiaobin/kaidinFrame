/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.appframe.api;

/**
 * API获取服务
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年7月10日 下午4:05:39
 */
public interface ApiService {
	/**
	 * 根据业务biz，action获取API
	 * @param biz
	 * @param action
	 * @return
	 */
	OpenApi<BaseResponseData, BaseRequestData> getApi(String biz, String action);

	/**
	 * 根据API类型获取API
	 * @param clazz
	 * @return
	 */
	<T extends OpenApi<?, ?>> T getApi(Class<T> clazz);
}
