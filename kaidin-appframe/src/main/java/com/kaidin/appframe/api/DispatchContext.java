/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.appframe.api;

/**
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年7月10日 上午11:12:50
 */
public class DispatchContext {
	/** 业务标识 */
	private String           biz;
	/** 业务请求场景 */
	private String           action;
	/** 业务请求参数 */
	private String           request;
	/** 业务请求参数对象 */
	private BaseRequestData  requestDate;
	/** 业务返回结果 */
	private String           response;
	/** 业务返回结果对象 */
	private BaseResponseData responseDate;

	public String getBiz() {
		return biz;
	}

	public void setBiz(String biz) {
		this.biz = biz;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public BaseRequestData getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(BaseRequestData requestDate) {
		this.requestDate = requestDate;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public BaseResponseData getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(BaseResponseData responseDate) {
		this.responseDate = responseDate;
	}

}
