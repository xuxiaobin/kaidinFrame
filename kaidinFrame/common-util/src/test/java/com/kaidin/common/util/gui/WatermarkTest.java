package com.kaidin.common.util.gui;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.IOException;

import org.junit.Test;

import com.sun.image.codec.jpeg.ImageFormatException;

public class WatermarkTest {

	@Test
	public void testPressImage() throws IOException {
		String watermarkImage = "C:/test/blacklist.png";
		Watermark.pressImage("C:/test/1.jpg", watermarkImage, "left-top", 45F);
		Watermark.pressImage("C:/test/2.jpg", watermarkImage, "right-top", 90F);
		Watermark.pressImage("C:/test/3.jpg", watermarkImage, "left-bottom", 135F);
		Watermark.pressImage("C:/test/4.jpg", watermarkImage, "right-bottom", 180F);
		Watermark.pressImage("C:/test/5.jpg", watermarkImage, null, -45F);
	}

	@Test
	public void testPressText() throws ImageFormatException, IOException {
		Watermark watermark = new Watermark();
		watermark.setFontColor(Color.RED);
		watermark.setDiaphaneity(0.5F);
		watermark.setFontDegree(-45F);
		watermark.pressText("C:/test/1.jpg", "hello workd!", 280, 140);
		watermark.setFontDegree(0);
		watermark.pressText("C:/test/1.jpg", "hello workd!", 280, 140);
	}

}
