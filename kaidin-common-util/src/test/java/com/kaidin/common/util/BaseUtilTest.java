/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BaseUtilTest {

	@Test
	public void testEquals() {
		assertTrue(BaseUtil.equals(null, null));
		assertFalse(BaseUtil.equals(null, "12345"));
		assertFalse(BaseUtil.equals("12345abc", null));
		assertTrue(BaseUtil.equals("12345abc", 12345 + "abc"));
		assertFalse(BaseUtil.equals("12345abC", 12345 + "abc"));
	}

	@Test
	public void testEqualsType() {
		assertFalse(BaseUtil.equalsType(null, null));
		assertFalse(BaseUtil.equalsType(null, "12345"));
		assertFalse(BaseUtil.equalsType("12345abc", null));
		assertTrue(BaseUtil.equalsType("12345abc", 12345 + "abc"));
		assertFalse(BaseUtil.equalsType("12345abC", 12345));
	}
}
