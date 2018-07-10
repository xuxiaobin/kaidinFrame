/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 规范化toString的输出，简化日志打印
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年7月10日 上午11:41:17
 */
public class ToString implements Serializable {
	/**  */
	private static final long serialVersionUID = 6329620916091252588L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
