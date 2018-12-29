/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年12月29日 下午8:03:00
 */
public class DefaultUtilTest {

	@Test
	public void testIfNull() {
		int defaultValue = 123;
		assertTrue(defaultValue == DefaultUtil.ifNull(null, defaultValue));
		int value = 456;
		assertTrue(value == DefaultUtil.ifNull(value, defaultValue));
	}

	@Test
	public void testIfEmpty() {
		String defaultStr = "123456";
		assertTrue(defaultStr == DefaultUtil.ifEmpty(null, defaultStr));
		assertTrue(defaultStr == DefaultUtil.ifEmpty("", defaultStr));
		String value = "123";
		assertTrue(value.equals(DefaultUtil.ifEmpty(value, defaultStr)));
	}

}
