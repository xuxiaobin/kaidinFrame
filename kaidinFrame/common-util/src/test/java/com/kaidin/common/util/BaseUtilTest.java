package com.kaidin.common.util;

import static org.junit.Assert.*;

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
}
