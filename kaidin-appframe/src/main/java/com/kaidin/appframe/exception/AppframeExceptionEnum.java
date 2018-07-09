package com.kaidin.appframe.exception;

import com.kaidin.common.util.exception.IExceptionCode;

/**
 * 异常枚举
 * @author kaidin@foxmail.com
 */
public enum AppframeExceptionEnum implements IExceptionCode {
	PARAMETER_ERROR("PARAMETER_ERROR", "参数错误");

	private String errorCode;
	private String errorDesc;

	/**
	 * 构造函数不对外
	 * @param errorCode
	 * @param errorDesc
	 */
	private AppframeExceptionEnum(String errorCode, String errorDesc) {
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}

	@Override
	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String getErrorEesc() {
		return errorDesc;
	}
}
