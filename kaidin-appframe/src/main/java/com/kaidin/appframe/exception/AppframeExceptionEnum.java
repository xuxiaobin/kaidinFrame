package com.kaidin.appframe.exception;

import com.kaidin.common.util.exception.IExceptionCode;

/**
 * 异常枚举
 * @author kaidin@foxmail.com
 */
public enum AppframeExceptionEnum implements IExceptionCode {
	PARAMETER_ERROR("PARAMETER_ERROR", "参数错误");

	private String errCode;
	private String errMsg;

	/**
	 * 构造函数不对外
	 * @param errorCode
	 * @param errorDesc
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
