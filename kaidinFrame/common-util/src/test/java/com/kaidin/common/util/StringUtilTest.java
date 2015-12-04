package com.kaidin.common.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testToUpperCaseAtFirst() {
		assertEquals("Config.properties", StringUtil.toUpperCaseAtFirst("config.properties"));
		assertEquals("12345", StringUtil.toUpperCaseAtFirst("12345"));
	}

	@Test
	public void testIsBlank() {
		assertTrue(StringUtil.isBlank(null));
		assertTrue(StringUtil.isBlank(""));
		assertTrue(StringUtil.isBlank(" "));
		assertTrue(StringUtil.isBlank(" 	 	"));
		assertFalse(StringUtil.isBlank(" 1	 	"));
	}
}
