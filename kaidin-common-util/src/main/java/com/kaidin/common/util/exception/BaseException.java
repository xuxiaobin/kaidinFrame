package com.kaidin.common.util.exception;

/**
 * 业务异常，可以继承本方法做派生
 * @author kaidin@foxmail.com
 */
public class BaseException extends RuntimeException {
	private static final long serialVersionUID = -232294070953752792L;
	protected String errorCode;
	protected String errorDesc;
	
	public BaseException() {
	}
	public BaseException(String message) {
		super(message);
	}
	public BaseException(Throwable cause) {
		super(cause);
	}
	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}
	public BaseException(IExceptionCode exceptionCode) {
		errorCode = exceptionCode.getErrorCode();
		errorDesc = exceptionCode.getErrorEesc();
	}

	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
}
