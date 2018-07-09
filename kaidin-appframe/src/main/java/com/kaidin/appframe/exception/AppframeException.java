package com.kaidin.appframe.exception;

import com.kaidin.common.util.exception.BaseException;
import com.kaidin.common.util.exception.IExceptionCode;

/**
 * 各个应用继承此Exception 例如KaidinException
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class AppframeException extends BaseException {
	private static final long serialVersionUID = -6150807000016793091L;

	public AppframeException() {
		super();
	}

	public AppframeException(String message) {
		super(message);
	}

	public AppframeException(Throwable cause) {
		super(cause);
	}

	public AppframeException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppframeException(IExceptionCode exceptionCode) {
		super(exceptionCode);
	}
}
