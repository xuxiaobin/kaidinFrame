package com.kaidin.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testIsEmpty() {
		assertTrue(StringUtil.isEmpty(null));
		assertTrue(StringUtil.isEmpty(StringUtil.EMPTY_STR));
		assertFalse(StringUtil.isEmpty("a"));
	}

	@Test
	public void testIsNotEmpty() {
		assertFalse(StringUtil.isNotEmpty(null));
		assertFalse(StringUtil.isNotEmpty(StringUtil.EMPTY_STR));
		assertTrue(StringUtil.isNotEmpty("a"));
	}

	@Test
	public void testIsBlank() {
		assertTrue(StringUtil.isBlank(null));
		assertTrue(StringUtil.isBlank(StringUtil.EMPTY_STR));
		assertTrue(StringUtil.isBlank(" "));
		assertTrue(StringUtil.isBlank(" 	 	"));
		assertFalse(StringUtil.isBlank(" 1	 	"));
	}

	@Test
	public void testContainsWhitespace() {
		assertFalse(StringUtil.containsWhitespace(null));
		assertFalse(StringUtil.containsWhitespace(StringUtil.EMPTY_STR));
		assertTrue(StringUtil.containsWhitespace(" "));
		assertTrue(StringUtil.containsWhitespace("	"));
		assertTrue(StringUtil.containsWhitespace("123 456"));
		assertFalse(StringUtil.containsWhitespace("123456"));
	}

	@Test
	public void testIsNotBlank() {
		assertFalse(StringUtil.isNotBlank(null));
		assertFalse(StringUtil.isNotBlank(StringUtil.EMPTY_STR));
		assertFalse(StringUtil.isNotBlank(" "));
		assertFalse(StringUtil.isNotBlank(" 	 	"));
		assertTrue(StringUtil.isNotBlank(" 1	 	"));
	}

	@Test
	public void testEquals() {
		assertTrue(StringUtil.equals(null, null));
		assertFalse(StringUtil.equals(null, "12345"));
		assertFalse(StringUtil.equals("12345abc", null));
		assertTrue(StringUtil.equals("12345abc", 12345 + "abc"));
		assertFalse(StringUtil.equals("12345abC", 12345 + "abc"));
	}

	@Test
	public void testEqualsIgnoreCase() {
		assertTrue(StringUtil.equalsIgnoreCase(null, null));
		assertFalse(StringUtil.equalsIgnoreCase(null, "12345"));
		assertFalse(StringUtil.equalsIgnoreCase("12345abc", null));
		assertTrue(StringUtil.equalsIgnoreCase("12345abc", 12345 + "abc"));
		assertTrue(StringUtil.equalsIgnoreCase("12345abC", 12345 + "abc"));
	}

	@Test
	public void testStartWith() {
		assertFalse(StringUtil.startsWith(null, null));
		assertFalse(StringUtil.startsWith(null, "abc"));
		assertFalse(StringUtil.startsWith("abc", null));
		assertTrue(StringUtil.startsWith("abcd", "abc"));
		assertFalse(StringUtil.startsWith("abcd", "Abc"));
		assertFalse(StringUtil.startsWith("abcd", "abcc"));
		assertTrue(StringUtil.startsWith(" abcd", " abc"));
	}

	@Test
	public void testStartWithIgnoreCase() {
		assertFalse(StringUtil.startsWithIgnoreCase(null, null));
		assertFalse(StringUtil.startsWithIgnoreCase(null, "abc"));
		assertFalse(StringUtil.startsWithIgnoreCase("abc", null));
		assertTrue(StringUtil.startsWithIgnoreCase("abcd", "abc"));
		assertTrue(StringUtil.startsWithIgnoreCase("abcd", "Abc"));
		assertFalse(StringUtil.startsWithIgnoreCase("abcd", "abcc"));
		assertTrue(StringUtil.startsWithIgnoreCase(" abcd", " ABC"));
	}

	@Test
	public void testEndWith() {
		assertFalse(StringUtil.endWith(null, null));
		assertFalse(StringUtil.endWith(null, "abc"));
		assertFalse(StringUtil.endWith("abc", null));
		assertTrue(StringUtil.endWith("abcd", "bcd"));
		assertFalse(StringUtil.endWith("abcd", "bCd"));
		assertFalse(StringUtil.endWith("abcd", "abcc"));
		assertTrue(StringUtil.endWith(" abcd ", "bcd "));
	}

	@Test
	public void testEndWithIgnoreCase() {
		assertFalse(StringUtil.endWithIgnoreCase(null, null));
		assertFalse(StringUtil.endWithIgnoreCase(null, "abc"));
		assertFalse(StringUtil.endWithIgnoreCase("abc", null));
		assertTrue(StringUtil.endWithIgnoreCase("abcd", "bcd"));
		assertTrue(StringUtil.endWithIgnoreCase("abcd", "bCd"));
		assertFalse(StringUtil.endWithIgnoreCase("abcd", "abcc"));
		assertTrue(StringUtil.endWithIgnoreCase("abcd ", "Bcd "));
	}

	@Test
	public void testToUpperCase() {
		assertNull(StringUtil.toUpperCase(null));
		assertEquals(StringUtil.EMPTY_STR, StringUtil.toUpperCase(StringUtil.EMPTY_STR));
		assertEquals(" A 23C", StringUtil.toUpperCase(" a 23c"));
	}

	@Test
	public void testToUpperCaseAtFirst() {
		assertNull(StringUtil.toUpperCaseAtFirst(null));
		assertEquals("Config.properties", StringUtil.toUpperCaseAtFirst("config.properties"));
		assertEquals("12345", StringUtil.toUpperCaseAtFirst("12345"));
	}

	@Test
	public void testToLowerCase() {
		assertNull(StringUtil.toLowerCase(null));
		assertEquals(StringUtil.EMPTY_STR, StringUtil.toLowerCase(StringUtil.EMPTY_STR));
		assertEquals(" a 23c", StringUtil.toLowerCase(" A 23c"));
	}

	@Test
	public void testUpperCase2Underline() {
		assertNull(StringUtil.upperCase2Underline(null));
		assertEquals(StringUtil.EMPTY_STR, StringUtil.upperCase2Underline(StringUtil.EMPTY_STR));
		assertEquals("123456", StringUtil.upperCase2Underline("123456"));
		assertEquals("12_a345_b_c6_z", StringUtil.upperCase2Underline("12A345BC6Z"));
		assertEquals("s_d_x12_a345_b_c_d6_z", StringUtil.upperCase2Underline("SDX12A345BCD6Z"));
	}

	@Test
	public void testUnderline2UpperCase() {
		assertNull(StringUtil.underline2UpperCase(null));
		assertEquals(StringUtil.EMPTY_STR, StringUtil.underline2UpperCase(StringUtil.EMPTY_STR));
		assertEquals("123456", StringUtil.underline2UpperCase("123456"));
		assertEquals("_12_3456", StringUtil.underline2UpperCase("_12____3456_"));
		assertEquals("_12A345BC6Z", StringUtil.underline2UpperCase("___12_a345_b_c6_z___"));
		assertEquals("sDX12A345BCD6Z", StringUtil.underline2UpperCase("s_d_x12__a345_b_c_d6_z"));
		assertEquals("sDX12A345BCD6Z", StringUtil.underline2UpperCase("_s_d_x12__a345_b_c_d6_z"));
	}

	@Test
	public void testSubstring() {
		assertEquals(null, StringUtil.subString(null, 1, 2));
		assertEquals(StringUtil.EMPTY_STR, StringUtil.subString(StringUtil.EMPTY_STR, 1, 2));
		assertEquals("12", StringUtil.subString("123456", 0, 2));
		assertEquals(StringUtil.EMPTY_STR, StringUtil.subString("123456", 2, 0));
		assertEquals("3456", StringUtil.subString("123456", 2, 10));
		assertEquals(StringUtil.EMPTY_STR, StringUtil.subString("123456", 9, 10));
		assertEquals(StringUtil.EMPTY_STR, StringUtil.subString("123456", 2, 2));
		assertEquals("34", StringUtil.subString("123456", -4, -2));
		assertEquals("2345", StringUtil.subString("123456", -5, 5));
	}
}
