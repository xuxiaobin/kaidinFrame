package com.kaidin.common.util.exception;

import java.io.Serializable;

/**
 * 异常状态码实现本接口
 * @author kaidin@foxmail.com
 */
public interface IExceptionCode extends Serializable {
	/**
	 * 错误码
	 * @return
	 */
	String getErrorCode();

	/**
	 * 错误描述信息
	 * @return
	 */
	String getErrorEesc();
}
