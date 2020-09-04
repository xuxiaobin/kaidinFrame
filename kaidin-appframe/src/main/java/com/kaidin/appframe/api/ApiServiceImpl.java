///**
// * Kaidin.com Inc.
// * Copyright (c) 2008-2018 All Rights Reserved.
// */
//package com.kaidin.appframe.api;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.kaidin.appframe.api.extension.ApiExtension;
//import com.kaidin.appframe.api.extension.ApiExtensionNode;
//import com.kaidin.appframe.api.util.ApiUtil;
//
///**
// * API服务获取中心实现
// * @version 1.0
// * @author kaidin@foxmail.com
// * @date 2018年7月10日 下午4:08:25
// */
//public class ApiServiceImpl implements ApiService {
//	/** 服务库 */
//	private final Map<String, OpenApi<BaseResponseData, BaseRequestData>> serviceMap        = new HashMap<>();
//	/** 扩展点列表 */
//	private final List<ApiExtension>                                      apiExtendsionList = new ArrayList<>();
////
////	public void registerContribution(Object contribution, String extensionPoint, ComponentInstance contributor) {
////		if (contribution instanceof ApiExtensionNode) {
////			ApiExtension extension = ((ApiExtensionNode) contribution).getExtension();
////			apiExtendsionList.add(extension);
////		}
////	}
//
//	/**
//	 * @see com.kaidin.appframe.api.ApiService#getApi(java.lang.String, java.lang.String)
//	 */
//	@Override
//	public OpenApi<BaseResponseData, BaseRequestData> getApi(String biz, String action) {
//		String apiName = ApiUtil.getApiName(biz, action);
//		if (serviceMap.containsKey(apiName)) {
//			return serviceMap.get(apiName);
//		}
//
//		return null;
//	}
//
////	/**
////	 * @see com.kaidin.appframe.api.ApiService#getApi(java.lang.Class)
////	 */
////	public <T extends OpenApi<BaseResponseData, BaseRequestData>> T getApi(Class<T> clazz) {
////		String apiName = ApiUtil.getApiName(clazz);
////		if (serviceMap.containsKey(apiName)) {
////			return (T) serviceMap.get(apiName);
////		}
////
////		return null;
////	}
//
//	public void startup() {
//		for (ApiExtension extension : apiExtendsionList) {
//			Map<String, OpenApi<BaseResponseData, BaseRequestData>> apiMap = extension.getApiMap();
//			Collection<OpenApi<BaseResponseData, BaseRequestData>> apiCollection = apiMap.values();
//			for (OpenApi<BaseResponseData, BaseRequestData> api : apiCollection) {
//
//			}
//			serviceMap.putAll(apiMap);
//		}
//	}
//
//}
