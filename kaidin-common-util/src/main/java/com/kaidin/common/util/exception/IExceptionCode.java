/**
 * Kaidin.com Inc.
 * Copyright (c) 200import java.io.Serializable;
*/
package com.kaidin.common.util.exception;

import java.io.Serializable;

/**
 * 异常状态码实现本接口
 * @author kaidin@foxmail.com
 * @date 2016-5-17下午01:51:48
 */
public interface IExceptionCode extends Serializable {
	/**
	 * 错误码
	 * @return
	 */
	String getErrCode();

	/**
	 * 错误描述信息
	 * @return
	 */
	String getErrMsg();
}
