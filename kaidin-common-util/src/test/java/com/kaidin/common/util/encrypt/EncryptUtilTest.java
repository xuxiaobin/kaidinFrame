/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util.encrypt;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.kaidin.common.util.StringUtil;

/**
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class EncryptUtilTest {
	private static String STR_12345    = "123456";
	private static String STR_ADMIN123 = "admin123";
	private static String STR_NULL     = null;
	private static File   FILE         = new File(EncryptUtilTest.class.getClassLoader().getResource("md5TestFile.txt").getFile());

	@Test
	public void testMd5() {
		assertEquals("e10adc3949ba59abbe56e057f20f883e", EncryptUtil.md5(STR_12345));
		assertEquals("0192023a7bbd73250516f069df18b500", EncryptUtil.md5(STR_ADMIN123));
		assertEquals("d41d8cd98f00b204e9800998ecf8427e", EncryptUtil.md5(StringUtil.EMPTY_STR));
		assertEquals(null, EncryptUtil.md5(STR_NULL));
	}

	@Test
	public void testFileMd5() throws IOException {
		String md5OfFile = EncryptUtil.md5(FILE);
		System.out.println("md5OfFile:" + md5OfFile);
		assertEquals("518ca85c349252d4a49989d5a2db264b", md5OfFile);
	}

	@Test
	public void testSha1() {
		assertEquals("7c4a8d09ca3762af61e59520943dc26494f8941b", EncryptUtil.sha1(STR_12345));
		assertEquals("f865b53623b121fd34ee5426c792e5c33af8c227", EncryptUtil.sha1(STR_ADMIN123));
		assertEquals("da39a3ee5e6b4b0d3255bfef95601890afd80709", EncryptUtil.sha1(StringUtil.EMPTY_STR));
		assertEquals(null, EncryptUtil.md5(STR_NULL));
	}

	@Test
	public void testFileSha1() throws IOException {
		String sha1OfFile = EncryptUtil.sha1(FILE);
		System.out.println("sha1OfFile:" + sha1OfFile);
		assertEquals("e65986b5b325885044179b468a2bccb30be38c6f", sha1OfFile);
	}
}
