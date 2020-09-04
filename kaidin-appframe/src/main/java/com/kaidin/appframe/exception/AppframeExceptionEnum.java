package com.kaidin.appframe.exception;

import com.kaidin.common.util.exception.IExceptionCode;

/**
 * 异常枚举
 * @author kaidin@foxmail.com
 */
public enum AppframeExceptionEnum implements IExceptionCode {
	PARAMETER_ILLEGAL("PARAMETER_ILLEGAL", "参数异常");

	private String errCode;
	private String errMsg;

	/**
	 * 构造函数不对外
	 * @param errCode
	 * @param errMsg
	 */
	private AppframeExceptionEnum(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	@Override
	public String getErrCode() {
		return errCode;
	}

	@Override
	public String getErrMsg() {
		return errMsg;
	}
}
