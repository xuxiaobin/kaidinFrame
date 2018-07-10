/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.appframe.api.engine;

import com.kaidin.appframe.api.BaseRequestData;
import com.kaidin.appframe.api.BaseResponseData;
import com.kaidin.appframe.api.DispatchContext;
import com.kaidin.appframe.api.OpenApi;

/**
 * api核心引擎
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年7月10日 下午3:37:46
 */
public interface ApiEngine {

	/**
	 * 运行入口
	 * @param context
	 * @return
	 */
	String run(DispatchContext context);

	/**
	 * 特定API运行入口
	 * @param apiClazz
	 * @param requestData
	 * @return
	 */
	<Res extends BaseResponseData, Req extends BaseRequestData> Res run(
	        Class<? extends OpenApi<? extends Res, ? extends Req>> apiClazz, Req requestData);
}
