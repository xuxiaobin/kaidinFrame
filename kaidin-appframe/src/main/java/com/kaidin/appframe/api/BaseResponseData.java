/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.appframe.api;

import com.kaidin.common.util.ToString;

/**
 * 返回数据公共类
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年7月10日 上午11:12:50
 */
public class BaseResponseData extends ToString {
	/**  */
	private static final long serialVersionUID = 2478562309695026125L;
	/** 请求是否成功 */
	private boolean           success          = false;
	/** 错误名称 */
	private String            errorName;
	/** 错误码 */
	private String            errorCode;
	/** 错误信息 */
	private String            errorMsg;

	/**
	 * @return property value of success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success value to assigned to property success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return property value of errorName
	 */
	public String getErrorName() {
		return errorName;
	}

	/**
	 * @param errorName value to assigned to property errorName
	 */
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	/**
	 * @return property value of errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode value to assigned to property errorCode
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return property value of errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg value to assigned to property errorMsg
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
