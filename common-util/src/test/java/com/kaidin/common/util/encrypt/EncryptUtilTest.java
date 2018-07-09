package com.kaidin.common.util.encrypt;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.kaidin.common.util.StringUtil;
import com.kaidin.common.util.encrypt.EncryptUtil.EncryptType;
/**
 * @version	1.0
 * @author	kaidin@foxmail.com
 * @date	2015-6-23下午01:51:48
 */
public class EncryptUtilTest {
	private static String str_12345 = "123456";
	private static String str_admin123 = "admin123";
	private static String str_enmpty = StringUtil.EMPTY_STR;
	private static String str_null = null;
	private static File file = new File(EncryptUtilTest.class.getClassLoader().getResource("md5TestFile.txt").getFile());
	
	
	@Test
	public void testMd5() {
		assertEquals("e10adc3949ba59abbe56e057f20f883e", EncryptUtil.md5(str_12345));
		assertEquals("0192023a7bbd73250516f069df18b500", EncryptUtil.md5(str_admin123));
		assertEquals("d41d8cd98f00b204e9800998ecf8427e", EncryptUtil.md5(str_enmpty));
		assertEquals(null, EncryptUtil.md5(str_null));
	}

	@Test
	public void testFileMd5() throws IOException {
		String md5OfFile = EncryptUtil.md5(file);
		System.out.println("md5OfFile:" + md5OfFile);
		assertEquals("518ca85c349252d4a49989d5a2db264b", md5OfFile);
	}
	
	@Test
	public void testSha1() {
		assertEquals("7c4a8d09ca3762af61e59520943dc26494f8941b", EncryptUtil.sha1(str_12345));
		assertEquals("f865b53623b121fd34ee5426c792e5c33af8c227", EncryptUtil.sha1(str_admin123));
		assertEquals("da39a3ee5e6b4b0d3255bfef95601890afd80709", EncryptUtil.sha1(str_enmpty));
		assertEquals(null, EncryptUtil.md5(str_null));
	}
	
	@Test
	public void testFileSha1() throws IOException {
		String sha1OfFile = EncryptUtil.sha1(file);
		System.out.println("sha1OfFile:" + sha1OfFile);
		assertEquals("e65986b5b325885044179b468a2bccb30be38c6f", sha1OfFile);
	}
	
	public static void main(String[] args) {
		System.out.println(EncryptType.MD5);
		EncryptUtil.encrypt("123", EncryptType.MD5);
	}
}
