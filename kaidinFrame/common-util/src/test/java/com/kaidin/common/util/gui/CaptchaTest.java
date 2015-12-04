package com.kaidin.common.util.gui;

import java.io.FileOutputStream;
import java.io.IOException;

import com.kaidin.common.util.gui.Captcha;

/**
 * @version	1.0
 * @author	kaidin@foxmail.com
 * @date	2015-6-27下午06:16:46
 */
public class CaptchaTest {
	public static void main(String[] args) throws IOException {
		char[] codeArray = Captcha.createCaptchaCode(4);
		
		codeArray = new char[]{'M', 'M', 'M', 'M', 'M', 'M', 'M', 'M'};
		
		Captcha codeBuilder = new Captcha();
		codeBuilder.createImage(codeArray, new FileOutputStream("c:/code.png"));
		
		System.out.println(codeArray);
	}
}
