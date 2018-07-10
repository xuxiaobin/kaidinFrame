/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.appframe.api.extension;

import java.util.Map;

import org.springframework.context.ApplicationContextAware;

import com.kaidin.appframe.api.BaseRequestData;
import com.kaidin.appframe.api.BaseResponseData;
import com.kaidin.appframe.api.OpenApi;

/**
 * API扩展点
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年7月10日 下午4:26:58
 */
public interface ApiExtension extends ApplicationContextAware {
	/**
	 * 获取API库
	 * @return
	 */
	Map<String, OpenApi<BaseResponseData, BaseRequestData>> getApiMap();
}
