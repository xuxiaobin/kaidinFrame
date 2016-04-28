package com.kaidin.common.util.gui;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.kaidin.common.util.gui.Captcha;

/**
 * @version	1.0
 * @author	kaidin@foxmail.com
 * @date	2015-6-27下午06:16:46
 */
public class CaptchaTest {
	
	@Test
	public void testCreateImage0() throws FileNotFoundException, IOException {
		Captcha codeBuilder = new Captcha();
		char[] codeArray = codeBuilder.createImage(new FileOutputStream("c:/code0.png"));
		System.out.println(codeArray);
	}
	
	@Test
	public void testCreateImage1() throws FileNotFoundException, IOException {
		char[] codeArray = new char[]{'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M'};
		
		Captcha codeBuilder = new Captcha();
		codeBuilder.createImage(codeArray, new FileOutputStream("c:/test/code0.png"));
		
		System.out.println(codeArray);
	}
}
