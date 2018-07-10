/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.appframe.api;

import com.kaidin.common.util.ToString;

/**
 * API引擎主入参
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年7月10日 上午11:12:50
 */
public class DispatchContext extends ToString {
	/**  */
	private static final long serialVersionUID = 3799748284313108816L;
	/** 业务标识 */
	private String            biz;
	/** 业务请求场景 */
	private String            action;
	/** 业务请求参数 */
	private String            request;
	/** 业务请求参数对象 */
	private BaseRequestData   requestDate;
	/** 业务返回结果 */
	private String            response;
	/** 业务返回结果对象 */
	private BaseResponseData  responseDate;

	/**
	 * @return property value of biz
	 */
	public String getBiz() {
		return biz;
	}

	/**
	 * @param biz value to assigned to property biz
	 */
	public void setBiz(String biz) {
		this.biz = biz;
	}

	/**
	 * @return property value of action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action value to assigned to property action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return property value of request
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * @param request value to assigned to property request
	 */
	public void setRequest(String request) {
		this.request = request;
	}

	/**
	 * @return property value of requestDate
	 */
	public BaseRequestData getRequestDate() {
		return requestDate;
	}

	/**
	 * @param requestDate value to assigned to property requestDate
	 */
	public void setRequestDate(BaseRequestData requestDate) {
		this.requestDate = requestDate;
	}

	/**
	 * @return property value of response
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * @param response value to assigned to property response
	 */
	public void setResponse(String response) {
		this.response = response;
	}

	/**
	 * @return property value of responseDate
	 */
	public BaseResponseData getResponseDate() {
		return responseDate;
	}

	/**
	 * @param responseDate value to assigned to property responseDate
	 */
	public void setResponseDate(BaseResponseData responseDate) {
		this.responseDate = responseDate;
	}

}
