/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util.image;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import org.junit.Test;

import com.kaidin.common.util.FileUtil;

public class WatermarkTest {
	private static final String OUTPUT_PATH       = "src/test/resources/image/output/";
	private static final String SOURCE_IMAGE_NAME = "src/test/resources/image/source.jpg";

	@Test
	public void testPressImage() throws IOException {
		String watermarkImage = "src/test/resources/image/logo.jpg";

		FileUtil.copyFiles(SOURCE_IMAGE_NAME, OUTPUT_PATH + "left-top.jpg");
		Watermark.pressImage(OUTPUT_PATH + "left-top.jpg", watermarkImage, "left-top", 45F);

		FileUtil.copyFiles(SOURCE_IMAGE_NAME, OUTPUT_PATH + "right-top.jpg");
		Watermark.pressImage(OUTPUT_PATH + "right-top.jpg", watermarkImage, "right-top", 90F);

		FileUtil.copyFiles(SOURCE_IMAGE_NAME, OUTPUT_PATH + "left-bottom.jpg");
		Watermark.pressImage(OUTPUT_PATH + "left-bottom.jpg", watermarkImage, "left-bottom", 135F);

		FileUtil.copyFiles(SOURCE_IMAGE_NAME, OUTPUT_PATH + "right-bottom.jpg");
		Watermark.pressImage(OUTPUT_PATH + "right-bottom.jpg", watermarkImage, "right-bottom", 180F);

		FileUtil.copyFiles(SOURCE_IMAGE_NAME, OUTPUT_PATH + "other.jpg");
		Watermark.pressImage(OUTPUT_PATH + "middle.jpg", watermarkImage, null, -45F);
	}

	@Test
	public void testPressText() throws IOException {
		String imageFileName = OUTPUT_PATH + "pressText.jpg";
		FileUtil.copyFiles(SOURCE_IMAGE_NAME, imageFileName);

		Watermark watermark = new Watermark();
		watermark.setFont(new Font("黑体", Font.BOLD, 10));
		watermark.setFontColor(Color.WHITE);
		watermark.setDiaphaneity(0.23F);
		watermark.pressText(imageFileName, "该文件只给kaidin开公司使用");
	}
}
