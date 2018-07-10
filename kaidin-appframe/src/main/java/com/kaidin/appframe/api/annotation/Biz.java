/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.appframe.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 业务线标识
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年7月10日 下午4:19:46
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface Biz {
	/**
	 * 业务代码
	 * @return
	 */
	String value();

	/**
	 * 版本号
	 * @return
	 */
	String verion();
}
