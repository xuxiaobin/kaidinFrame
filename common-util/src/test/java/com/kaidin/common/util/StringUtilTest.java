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
		assertFalse(StringUtil.isNotBlank(null));
		assertFalse(StringUtil.isNotBlank(""));
		assertFalse(StringUtil.isNotBlank(" "));
		assertFalse(StringUtil.isNotBlank(" 	 	"));
		assertTrue(StringUtil.isNotBlank(" 1	 	"));
	}
	
	@Test
	public void testUpperCase2Underline() {
		assertNull(StringUtil.upperCase2Underline(null));
		assertEquals("", StringUtil.upperCase2Underline(""));
		assertEquals("123456", StringUtil.upperCase2Underline("123456"));
		assertEquals("12_a345_b_c6_z", StringUtil.upperCase2Underline("12A345BC6Z"));
		assertEquals("s_d_x12_a345_b_c_d6_z", StringUtil.upperCase2Underline("SDX12A345BCD6Z"));
	}
	
	@Test
	public void testUnderline2UpperCase() {
		assertNull(StringUtil.underline2UpperCase(null));
		assertEquals("", StringUtil.underline2UpperCase(""));
		assertEquals("123456", StringUtil.underline2UpperCase("123456"));
		assertEquals("_12_3456", StringUtil.underline2UpperCase("_12____3456_"));
		assertEquals("_12A345BC6Z", StringUtil.underline2UpperCase("___12_a345_b_c6_z___"));
		assertEquals("sDX12A345BCD6Z", StringUtil.underline2UpperCase("s_d_x12__a345_b_c_d6_z"));
		assertEquals("sDX12A345BCD6Z", StringUtil.underline2UpperCase("_s_d_x12__a345_b_c_d6_z"));
	}
}
