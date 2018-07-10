/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.appframe.api.util;

import com.kaidin.appframe.api.annotation.Api;
import com.kaidin.appframe.api.annotation.Biz;

/**
 * API工具类
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年7月10日 下午4:11:40
 */
public class ApiUtil {
	/** API名称分割线 */
	private static final String SPLIT = "/";

	/**
	 * 根据业务线和API名称获取API标识
	 * @param biz
	 * @param action
	 * @return
	 */
	public static String getApiName(String biz, String action) {
		return SPLIT + biz + action;
	}

	/**
	 * 根据API的类名获取API标识
	 * @param apiClazz
	 * @return
	 */
	public static String getApiName(Class<?> apiClazz) {
		Biz biz = apiClazz.getPackage().getAnnotation(Biz.class);
		String bizName = biz.value();
		Api api = apiClazz.getAnnotation(Api.class);
		String action = api.value();
		return getApiName(bizName, action);
	}
}
