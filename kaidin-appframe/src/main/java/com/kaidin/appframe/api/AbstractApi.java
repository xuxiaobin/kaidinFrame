/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.appframe.api;

/**
 * 基础openApi的抽象实现
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年7月10日 下午3:44:06
 */
public abstract class AbstractApi<Res extends BaseResponseData, Req extends BaseRequestData> implements OpenApi<Res, Req> {

	/**
	 * @see com.kaidin.appframe.api.OpenApi#execute()
	 */
	@Override
	public Res execute() {
		runtimeco
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see com.kaidin.appframe.api.OpenApi#execute(com.kaidin.appframe.api.BaseRequestData)
	 */
	@Override
	public Res execute(Req request) {
		// TODO Auto-generated method stub
		return null;
	}

}
