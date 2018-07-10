/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.appframe.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * api注解
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年7月10日 上午11:12:50
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Api {
	/**
	 * API名称，遵循REST风格，如：/data/List，避免大写字母，则API名称为DataListApi
	 * 
	 * @return
	 */
	String value();
}
