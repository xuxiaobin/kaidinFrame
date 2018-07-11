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

/**
 * 图片加水印测试
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2018年7月11日 下午10:13:38
 */
public class WatermarkTest {
	private static final String OUTPUT_PATH       = "src/test/resources/image/output/";
	private static final String SOURCE_IMAGE_NAME = "src/test/resources/image/source.jpg";

	/**
	 * 水印是一个图片
	 * @throws IOException
	 */
	@Test
	public void testPressImage() throws IOException {
		String watermarkImage = "src/test/resources/image/logo.jpg";

		String outputImageFilePath = OUTPUT_PATH + "left-top.jpg";
		FileUtil.deleteFiles(outputImageFilePath);
		FileUtil.copyFiles(SOURCE_IMAGE_NAME, outputImageFilePath);
		Watermark.pressImage(outputImageFilePath, watermarkImage, "left-top", 45F);

		outputImageFilePath = OUTPUT_PATH + "right-top.jpg";
		FileUtil.deleteFiles(outputImageFilePath);
		FileUtil.copyFiles(SOURCE_IMAGE_NAME, outputImageFilePath);
		Watermark.pressImage(outputImageFilePath, watermarkImage, "right-top", 90F);

		outputImageFilePath = OUTPUT_PATH + "left-bottom.jpg";
		FileUtil.deleteFiles(outputImageFilePath);
		FileUtil.copyFiles(SOURCE_IMAGE_NAME, outputImageFilePath);
		Watermark.pressImage(outputImageFilePath, watermarkImage, "left-bottom", 135F);

		outputImageFilePath = OUTPUT_PATH + "right-bottom.jpg";
		FileUtil.deleteFiles(outputImageFilePath);
		FileUtil.copyFiles(SOURCE_IMAGE_NAME, outputImageFilePath);
		Watermark.pressImage(outputImageFilePath, watermarkImage, "right-bottom", 180F);

		outputImageFilePath = OUTPUT_PATH + "middle.jpg";
		FileUtil.deleteFiles(outputImageFilePath);
		FileUtil.copyFiles(SOURCE_IMAGE_NAME, outputImageFilePath);
		Watermark.pressImage(outputImageFilePath, watermarkImage, null, -45F);
	}

	/**
	 * 添加文字水印
	 * @throws IOException
	 */
	@Test
	public void testPressText() throws IOException {
		String imageFileName = OUTPUT_PATH + "pressText.jpg";
		FileUtil.deleteFiles(imageFileName);
		FileUtil.copyFiles(SOURCE_IMAGE_NAME, imageFileName);

		Watermark watermark = new Watermark();
		watermark.setFont(new Font("黑体", Font.BOLD, 10));
		watermark.setFontColor(Color.WHITE);
		watermark.setDiaphaneity(0.23F);
		watermark.pressText(imageFileName, "该文件只给kaidin开公司使用");
	}
}
