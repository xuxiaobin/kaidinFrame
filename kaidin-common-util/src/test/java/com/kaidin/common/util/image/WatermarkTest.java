package com.kaidin.common.util.image;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import org.junit.Test;

public class WatermarkTest {

	@Test
	public void testPressImage() throws IOException {
		String watermarkImage = "C:/xiaobin/test/blacklist.png";
		Watermark.pressImage("C:/xiaobin/test/1.jpg", watermarkImage, "left-top", 45F);
		Watermark.pressImage("C:/xiaobin/test/2.jpg", watermarkImage, "right-top", 90F);
		Watermark.pressImage("C:/xiaobin/test/3.jpg", watermarkImage, "left-bottom", 135F);
		Watermark.pressImage("C:/xiaobin/test/4.jpg", watermarkImage, "right-bottom", 180F);
		Watermark.pressImage("C:/xiaobin/test/5.jpg", watermarkImage, null, -45F);
	}

	@Test
	public void testPressText() throws IOException {
		Watermark watermark = new Watermark();
		watermark.setFont(new Font("黑体", Font.BOLD, 10));
		watermark.setFontColor(Color.WHITE);
		watermark.setDiaphaneity(0.23F);
		watermark.pressText("C:/xiaobin/3.jpg", "该文件只给xx开公司使用");
	}
}
