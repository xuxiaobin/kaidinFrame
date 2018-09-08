/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util.exception;

/**
 * 业务异常，可以继承本方法做派生
 * @author kaidin@foxmail.com
 * @date 2016-5-17下午01:51:48
 */
public class BaseException extends RuntimeException {
	private static final long serialVersionUID = -232294070953752792L;
	protected String          errCode;
	protected String          errMsg;

	public BaseException() {
	}

	public BaseException(String errMsg) {
		super(errMsg);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String errMsg, Throwable cause) {
		super(errMsg, cause);
	}

	public BaseException(IExceptionCode exceptionCode) {
		errCode = exceptionCode.getErrCode();
		errMsg = exceptionCode.getErrMsg();
	}

	public BaseException(IExceptionCode exceptionCode, String errMsg) {
		errCode = exceptionCode.getErrCode();
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
