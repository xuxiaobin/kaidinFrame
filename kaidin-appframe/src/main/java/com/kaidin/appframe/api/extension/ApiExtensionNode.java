/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.appframe.api.extension;

/**
 * API扩展点
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年7月10日 下午4:37:18
 */
public class ApiExtensionNode {
	protected String       name;
	protected ApiExtension extension;

	/**
	 * @return property value of name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return property value of extension
	 */
	public ApiExtension getExtension() {
		return extension;
	}

}
