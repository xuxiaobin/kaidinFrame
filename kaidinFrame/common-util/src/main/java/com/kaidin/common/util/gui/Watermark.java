package com.kaidin.common.util.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
 * 图片添加水印功能
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2016-04-28 01:51:48
 */
public class Watermark {
	private String fontName = "Fixedsys";	// 字体名称，默认Fixedsys
	private int fontStyle = Font.BOLD;	// 字体样式，默认加粗
	private int fontSize = 32;	// 字体大小，默认32
	private float fontDegree = 0;	// 字体旋转的角度
	private float diaphaneity = 1;	// 透明度
	private Color fontColor = Color.WHITE;	// 字体颜色，默认为白色
	
	
	/**
	 * 把水印图片印刷到指定图片上
	 * @param targetImage	目标图片文件
	 * @param watermarkImage	水印文件
	 * @param position	left-top：左上角，right-top：右上角，left-bottom：左下角，right-bottom：右下角
	 * @param degree	水印顺时针旋转角度
	 * @throws IOException
	 */
	public static void pressImage(String targetImage, String watermarkImage, String position, Float degree) throws IOException {
		// 目标文件
		Image src = ImageIO.read(new File(targetImage));
		int targetImageWideth = src.getWidth(null);
		int targetImageHeight = src.getHeight(null);
		BufferedImage image = new BufferedImage(targetImageWideth, targetImageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphic = image.createGraphics();
		graphic.drawImage(src, 0, 0, targetImageWideth, targetImageHeight, null);

		// 水印文件
		Image src_biao = ImageIO.read(new File(watermarkImage));
		int watermarkImageWideth = src_biao.getWidth(null);
		int watermarkImageHeight = src_biao.getHeight(null);
		
		// 水印坐标
		int watermarkImagePositionX = 0, watermarkImagePositionY = 0;
		if ("right-bottom".equals(position)) {
			watermarkImagePositionX = targetImageWideth - watermarkImageWideth;
			watermarkImagePositionY = targetImageHeight - watermarkImageHeight;
		} else if ("left-bottom".equals(position)) {
			watermarkImagePositionY = targetImageHeight - watermarkImageHeight;
		} else if ("left-top".equals(position)) {
		} else if ("right-top".equals(position)) {
			watermarkImagePositionX = targetImageWideth - watermarkImageWideth;
		} else {
			watermarkImagePositionX = (targetImageWideth - watermarkImageWideth) / 2;
			watermarkImagePositionY = (targetImageHeight - watermarkImageHeight) / 2;
		}

		if (null != degree && 0 != degree) {
			// 设置水印旋转
			double degreePositionX = watermarkImagePositionX + watermarkImageWideth / 2;
			double degreePositionY = watermarkImagePositionY + watermarkImageHeight / 2;
			graphic.rotate(degree, degreePositionX, degreePositionY);
		}
		graphic.drawImage(src_biao, watermarkImagePositionX, watermarkImagePositionY, watermarkImageWideth, watermarkImageHeight, null);
		// 水印文件结束
		graphic.dispose();
		FileOutputStream out = new FileOutputStream(targetImage);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image);
		out.close();
	}
	
	public void pressText(String targetImage, String pressText, int positionX, int positionY) throws ImageFormatException, IOException {
		Image src = ImageIO.read(new File(targetImage));
		int targetImageWideth = src.getWidth(null);
		int targetImageHeight = src.getHeight(null);
		BufferedImage image = new BufferedImage(targetImageWideth, targetImageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphic = initGraphics(image);
		graphic.drawImage(src, 0, 0, targetImageWideth, targetImageHeight, null);
		
		if (0 != fontDegree) {
			// 设置旋转
			int offset = pressText.length() * fontSize / 4;
			graphic.rotate(fontDegree, positionX + offset, positionY);
		}
		
		graphic.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, diaphaneity));
//		graphic.drawString(pressText, targetImageWideth - fontSize - positionX, targetImageHeight - fontSize / 2 - positionY);
		graphic.drawString(pressText, positionX, positionY);
		graphic.dispose();
		FileOutputStream out = new FileOutputStream(targetImage);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image);
		out.close();
	}
	
	/**
	 * 绘制类初始化
	 * @since 1.0.0
	 * @Date:2012-3-1 上午10:17:52
	 * @return Graphics2D
	 */
	private Graphics2D initGraphics(BufferedImage buffImg) {
		Graphics2D result = buffImg.createGraphics();
		
		result.setColor(fontColor);
		result.setFont(new Font(fontName, fontStyle, fontSize));
		
		
		return result;
	}

	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public int getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public float getFontDegree() {
		return fontDegree;
	}
	public void setFontDegree(float fontDegree) {
		this.fontDegree = fontDegree;
	}
	public float getDiaphaneity() {
		return diaphaneity;
	}
	public void setDiaphaneity(float diaphaneity) {
		this.diaphaneity = diaphaneity;
	}
	public Color getFontColor() {
		return fontColor;
	}
	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}
}
