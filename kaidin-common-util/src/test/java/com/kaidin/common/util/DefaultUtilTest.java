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
		int drfaultValue = 123;
		assertTrue(drfaultValue == DefaultUtil.ifNull(null, drfaultValue));
		assertTrue(456 == DefaultUtil.ifNull(456, drfaultValue));
	}

	@Test
	public void testIfEmpty() {
		String drfaultStr = "123456";
		assertTrue(drfaultStr == DefaultUtil.ifEmpty(null, drfaultStr));
		assertTrue(drfaultStr == DefaultUtil.ifEmpty("", drfaultStr));
		assertTrue("123".equals(DefaultUtil.ifEmpty("123", drfaultStr)));
	}

}
