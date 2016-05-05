package com.kaidin.common.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class BaseUtilTest {

	@Test
	public void testEquals() {
		assertTrue(BaseUtil.equalsWithNull(null, null));
		assertFalse(BaseUtil.equalsWithNull(null, "12345"));
		assertFalse(BaseUtil.equalsWithNull("12345abc", null));
		assertTrue(BaseUtil.equalsWithNull("12345abc", 12345 + "abc"));
		assertFalse(BaseUtil.equalsWithNull("12345abC", 12345 + "abc"));
	}
}
