/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util.log;

import org.slf4j.Logger;

import com.kaidin.common.util.StringUtil;

/**
 * 日志工具
 * 
 * @author kaidin@foxmail.com
 * @date 2016-5-17下午01:51:48
 */
public abstract class LoggerUtil {

	public static void debug(Logger logger, String message) {
		if (logger.isDebugEnabled()) {
			logger.debug(message);
		}
	}

	public static void debug(Logger logger, String message, Object... params) {
		if (logger.isDebugEnabled()) {
			logger.debug(StringUtil.format(message, params));
		}
	}

	public static void debug(Logger logger, Throwable throwable, String message, Object... params) {
		if (logger.isDebugEnabled()) {
			logger.debug(StringUtil.format(message, params), throwable);
		}
	}

	public static void info(Logger logger, String message) {
		if (logger.isInfoEnabled()) {
			logger.info(message);
		}
	}

	public static void info(Logger logger, String message, Object... params) {
		if (logger.isInfoEnabled()) {
			logger.info(StringUtil.format(message, params));
		}
	}

	public static void info(Logger logger, Throwable throwable, String message, Object... params) {
		if (logger.isInfoEnabled()) {
			logger.info(StringUtil.format(message, params), throwable);
		}
	}

	public static void warn(Logger logger, String message) {
		if (logger.isWarnEnabled()) {
			logger.warn(message);
		}
	}

	public static void warn(Logger logger, String message, Object... params) {
		if (logger.isWarnEnabled()) {
			logger.warn(StringUtil.format(message, params));
		}
	}

	public static void warn(Logger logger, Throwable throwable, String message, Object... params) {
		if (logger.isWarnEnabled()) {
			logger.warn(StringUtil.format(message, params), throwable);
		}
	}

	public static void error(Logger logger, String message) {
		if (logger.isErrorEnabled()) {
			logger.error(message);
		}
	}

	public static void error(Logger logger, String message, Object... params) {
		if (logger.isErrorEnabled()) {
			logger.error(StringUtil.format(message, params));
		}
	}

	public static void error(Logger logger, Throwable throwable, String message, Object... params) {
		if (logger.isErrorEnabled()) {
			logger.error(StringUtil.format(message, params), throwable);
		}
	}
}
