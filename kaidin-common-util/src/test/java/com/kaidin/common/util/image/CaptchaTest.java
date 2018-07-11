/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util.image;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

/**
 * 验证码生成测试
 * @version	1.0
 * @author	kaidin@foxmail.com
 * @date	2015-6-27下午06:16:46
 */
public class CaptchaTest {

	/**
	 * 默认的随机验证码
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test
	public void testCreateImage0() throws FileNotFoundException, IOException {
		Captcha codeBuilder = new Captcha();
		char[] codeArray = codeBuilder.createImage(new FileOutputStream("src/test/resources/image/output/code0.png"));
		System.out.println(codeArray);
	}

	/**
	 * 指定字符的验证码
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test
	public void testCreateImage1() throws FileNotFoundException, IOException {
		char[] codeArray = new char[] { 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M' };

		Captcha codeBuilder = new Captcha();
		codeBuilder.createImage(codeArray, new FileOutputStream("src/test/resources/image/output/code1.png"));

		System.out.println(codeArray);
	}
}
