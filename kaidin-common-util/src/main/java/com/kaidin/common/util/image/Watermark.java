package com.kaidin.common.util.image;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
 * 图片添加水印功能
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2016-04-28 01:51:48
 */
@SuppressWarnings("restriction")
public class Watermark extends BaseImage {
	
	/**
	 * 把水印图片印刷到指定图片上
	 * @param imageFile	目标图片文件
	 * @param watermarkImage	水印文件
	 * @param position	left-top：左上角，right-top：右上角，left-bottom：左下角，right-bottom：右下角
	 * @param degree	水印顺时针旋转角度
	 * @throws IOException
	 */
	public static void pressImage(String imageFile, String watermarkImage, String position, Float degree) throws IOException {
		// 目标文件
		Image src = ImageIO.read(new File(imageFile));
		int srcImageWidth = src.getWidth(null);
		int srcImageHeight = src.getHeight(null);
		BufferedImage image = new BufferedImage(srcImageWidth, srcImageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphic = image.createGraphics();
		graphic.drawImage(src, 0, 0, null);

		// 水印文件
		Image src_biao = ImageIO.read(new File(watermarkImage));
		int watermarkImageWidth = src_biao.getWidth(null);
		int watermarkImageHeight = src_biao.getHeight(null);
		
		// 水印坐标
		int watermarkImagePositionX = 0, watermarkImagePositionY = 0;
		if ("right-bottom".equals(position)) {
			watermarkImagePositionX = srcImageWidth - watermarkImageWidth;
			watermarkImagePositionY = srcImageHeight - watermarkImageHeight;
		} else if ("left-bottom".equals(position)) {
			watermarkImagePositionY = srcImageHeight - watermarkImageHeight;
		} else if ("left-top".equals(position)) {
		} else if ("right-top".equals(position)) {
			watermarkImagePositionX = srcImageWidth - watermarkImageWidth;
		} else {
			watermarkImagePositionX = (srcImageWidth - watermarkImageWidth) / 2;
			watermarkImagePositionY = (srcImageHeight - watermarkImageHeight) / 2;
		}

		if (null != degree && 0 != degree) {
			// 设置水印旋转
			double degreePositionX = watermarkImagePositionX + watermarkImageWidth / 2;
			double degreePositionY = watermarkImagePositionY + watermarkImageHeight / 2;
			graphic.rotate(DegreeUtil.angle2radian(degree), degreePositionX, degreePositionY);
		}
		graphic.drawImage(src_biao, watermarkImagePositionX, watermarkImagePositionY, watermarkImageWidth, watermarkImageHeight, null);
		// 水印文件结束
		graphic.dispose();
		createImageFile(image, imageFile);
	}
	
	public void pressText(String imageFile, String pressText, int positionX, int positionY) throws IOException {
		Image src = ImageIO.read(new File(imageFile));
		int imageWidth = src.getWidth(null);
		int imageHeight = src.getHeight(null);
		BufferedImage bufferImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphic = initGraphics(bufferImage, src);
		
		if (0 != fontDegree) {
			// 设置旋转
			int offset = pressText.length() * font.getSize() / 4;
			graphic.rotate(DegreeUtil.angle2radian(fontDegree), positionX + offset, positionY);
		}
//		graphic.drawString(pressText, imageWidth - fontSize - positionX, imageHeight - fontSize / 2 - positionY);
		graphic.drawString(pressText, positionX, positionY);
		graphic.dispose();
		createImageFile(bufferImage, imageFile);
	}
	
	public void pressText(String imageFile, String pressText) throws IOException {
		Image src = ImageIO.read(new File(imageFile));
		int imageWidth = src.getWidth(null);	// 源图宽度
		int imageHeight = src.getHeight(null);	// 源图高度
		int imageDiagonalLength = (int) Math.sqrt(Math.pow(imageWidth, 2) + Math.pow(imageHeight, 2));	// 源图对角线长度
		int fontSize = imageDiagonalLength / 25;	// 水印文字大小
		
		font = new Font(font.getFontName(), font.getStyle(), fontSize);
		String watermarkText = pressText;
		int pressTextLimit = imageDiagonalLength - pressText.length() * fontSize;
		while (pressTextLimit > watermarkText.length() * fontSize) {
			watermarkText += "   " + pressText;
		}
		
		BufferedImage bufferImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphic = initGraphics(bufferImage, src);
		double degreeTan = -1.0 * imageHeight / imageWidth;	// 水印旋转角度的tan值
		Double degree = Math.atan(degreeTan);	// 水印旋转角度
		if (0 != degree) {
			// 设置旋转
			graphic.rotate(degree.floatValue());
		}
		
		int firstLength = imageWidth * imageHeight / imageDiagonalLength;
		int secondLength =  imageHeight * imageDiagonalLength / imageWidth;
		int positionX = 0;
		int step = (int) (fontSize * 2.5);
		for (int positionY = step; positionY < firstLength * 2 - step; positionY += step) {
			if (positionY < firstLength) {
				positionX = (int) (positionY * degreeTan);
			} else {
				positionX = (int) ((secondLength - positionY) / degreeTan);
			}
//			System.out.println("firstLength:" + firstLength+ ", positionX:" + positionX + ", positionY:" + positionY + ", degreeTan:" + degreeTan);
			graphic.drawString(watermarkText, positionX, positionY);
		}
		
		graphic.dispose();
		createImageFile(bufferImage, imageFile);
	}
	
	/**
	 * 绘制类初始化
	 * @since 1.0.0
	 * @Date:2012-3-1 上午10:17:52
	 * @return Graphics2D
	 */
	private Graphics2D initGraphics(BufferedImage buffImg, Image image) {
		Graphics2D result = buffImg.createGraphics();
		
		result.setColor(fontColor);
		result.setFont(font);
		result.drawImage(image, 0, 0, null);
		result.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, diaphaneity));
		
		return result;
	}
	
	private static void createImageFile(BufferedImage image, String fileName) throws IOException {
		try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);
			encoder.encode(image);
		} catch (IOException e) {
			throw e;
		}
	}
}
