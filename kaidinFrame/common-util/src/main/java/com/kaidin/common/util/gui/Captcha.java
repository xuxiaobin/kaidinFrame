package com.kaidin.common.util.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
/**
 * 图片验证码生成
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class Captcha {
	private static final char[] CODE_SET = "3456789ABCEFGHJKLMNPQRSTUVWXY".toCharArray();
	public static int MIN_CODE_COUNT = 4;	// 字符个数，最少4个（默认）
	private static final int MIN_FONT_SIZE = 20;	// 最小的字体大小，像素
	private static int FONT_SIZE = 32;	// 字符大小，（当做图片高度使用）
	private static final float CODE_WIDTH = 0.85F;	// 字符宽度百分比（完全按照字符间距会显得太大）
	
	
//	/**
//	 * 创建验证码对象
//	 * @since 1.0.0
//	 * @Date:2012-3-1 上午10:26:20
//	 * @return CheckCode
//	 */
//	public Captcha createCheckCode() {
//		Captcha captcha = new Captcha();
//		
//		char[] codeArray = createCaptchaCode();
//		captcha.createImage(codeArray);
//		
//		captcha.setCheckCodeStr(String.valueOf(codeArray));
//		
//		return captcha;
//	}

	/**
	 * 生成验证码字符数组，可以指定字符个数，最少4个字符
	 * @param codeCount
	 * @return
	 */
	public static char[] createCaptchaCode(int codeCount) {
		//如果传入的字符长度大于默认的长度就用传入的，否则用默认的
		codeCount = codeCount > MIN_CODE_COUNT ? codeCount: MIN_CODE_COUNT;
		char[] result = new char[codeCount];

		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < codeCount; i++) {
			result[i] = CODE_SET[random.nextInt(CODE_SET.length)];
		}

		return result;
	}
	public static char[] createCaptchaCode() {
		return createCaptchaCode(MIN_CODE_COUNT);
	}
	
	
	public void createImage(char[] codeArray, OutputStream output) throws IOException {
		if (null == codeArray) {
			codeArray = createCaptchaCode();
		}
		int imgWidth = Double.valueOf(FONT_SIZE * (codeArray.length * CODE_WIDTH + (1 - CODE_WIDTH))).intValue();
		BufferedImage buffImg = new BufferedImage(imgWidth, FONT_SIZE, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = graphicsInit(buffImg);
		drawDisturbLine(buffImg);	// 画干扰线
		drawCurve(buffImg);	// 添加干扰曲线
		drawCode(graphics, codeArray);	// 画字符
		buffImg = twistImage(buffImg);	// 扭曲图片
//		drawCurve(graphics);	// 添加干扰曲线
		
		ImageIO.write(buffImg, "PNG", output);
	}
	
	/**
	 * 绘制类初始化
	 * @since 1.0.0
	 * @Date:2012-3-1 上午10:17:52
	 * @return Graphics2D
	 */
	private Graphics2D graphicsInit(BufferedImage buffImg) {
		Graphics2D graphics;
		
		graphics = buffImg.createGraphics();
//		graphics.setColor(Color.WHITE);
//		buffImg = graphics.getDeviceConfiguration().createCompatibleImage(buffImg.getWidth(), buffImg.getHeight(), Transparency.TRANSLUCENT);
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.8f));
//		graphics.dispose();
//		graphics = buffImg.createGraphics();
		graphics.fillRect(0, 0, buffImg.getWidth(), FONT_SIZE);
//		graphics.drawRect(0, 0, IMG_WIDTH - 1, IMG_HEIGHT - 1);
		
		return graphics;
	}
	
	/**
	 * 将字符画到画布上
	 * @param graphics
	 * @param codeArray
	 */
	private void drawCode(Graphics2D graphics, char[] codeArray) {
		int codeWidth = Double.valueOf(FONT_SIZE * CODE_WIDTH).intValue();	// 字符的宽度
		int codeX = Double.valueOf(codeWidth * (1 - CODE_WIDTH)).intValue();	// 开始画的横坐标
		int codeY = Double.valueOf(FONT_SIZE * CODE_WIDTH).intValue();	// 开始画的纵坐标
		System.out.println("(" + codeX + "," + codeY + ")");
		graphics.setFont(new Font("Fixedsys", Font.BOLD, FONT_SIZE));
		for (int index = 0; index < codeArray.length; index++) {
//			graphics.drawString(String.valueOf(codeArray[i]), codeX, codeY);
			// 每次画一个字符
			Color color = createColor();
			System.out.println("\t" + color);
			graphics.setColor(color);
			graphics.drawChars(codeArray, index, 1, codeX, codeY);
			codeX += codeWidth;	// 横坐标偏移一个字符距离
		}
	}
	
	/**
	 * 绘制干扰线
	 * @param graphics
	 */
	private void drawDisturbLine(BufferedImage buffImg) {
		int xOffset = 20, yOffset = 10;	// 两个点之间的偏移量
		int x1, y1;	// 第一个点的坐标
		int x2, y2; // 第二个点的坐标
		Graphics2D graphics = buffImg.createGraphics();
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < MIN_CODE_COUNT * 3; i++) {
			x1 = random.nextInt(buffImg.getWidth() - xOffset);
			y1 = random.nextInt(FONT_SIZE - yOffset);
			x2 = x1 + random.nextInt(xOffset);
			y2 = y1 + random.nextInt(yOffset);
			graphics.setColor(createColor());
			graphics.drawLine(x1, y1, x2, y2);
		}
	}
	
	/**
	 * 添加干扰曲线
	 * @param buffImg
	 */
	private void drawCurve(BufferedImage buffImg) {
		Graphics2D graphics = buffImg.createGraphics();
		graphics.setColor(createColor());
		
		Random random = new Random(System.currentTimeMillis());
		double amplitude = random.nextFloat() * 10 + 4;	// 波形的幅度倍数，越大扭曲的程序越高，一般为3
		double phase = random.nextFloat() * 2 * Math.PI;	// 波形的起始相位，取值区间（0-2＊PI）
		for (int x = 0; x < buffImg.getWidth(); x++) {
			double dx = 2 * Math.PI * x / buffImg.getWidth() + phase;	// 将y坐标映射到2PI上，加上相位
			int offset = (int) (Math.sin(dx) * amplitude) + 9;	// 正弦函数乘以振幅
			//画一小段竖线
			if (0 < offset && offset < FONT_SIZE) {
				graphics.drawLine(x, offset, x, offset + 2);
			}
		}
	}
	

	/**
	 * 随机颜色
	 * @return Color
	 */
	private Color createColor() {
//		Color result[] = new Color[10];
//		
//		result[0] = new Color(113, 31, 71);
//		result[1] = new Color(37, 0, 37);
//		result[2] = new Color(111, 33, 36);
//		result[3] = new Color(0, 0, 112);
//		result[4] = new Color(14, 51, 16);
//		result[5] = new Color(1, 1, 1);
//		result[6] = new Color(72, 14, 73);
//		result[7] = new Color(65, 67, 29);
//		result[8] = new Color(116, 86, 88);
//		result[9] = new Color(41, 75, 71);
//		Random random = new Random(System.currentTimeMillis());
//		
//		return result[random.nextInt(result.length)];
		
		Random random = new Random();
		int r = random.nextInt(170);
		int g = random.nextInt(170);
		int b = random.nextInt(170);
		while (true) {
			int rg = Math.abs(r - g);
			int rb = Math.abs(r - b);
			int gb = Math.abs(g - b);
			System.out.println(r + "," + g + "," + b);
			if (10 < rg && 10 < rb && 10 < gb) {
				break;
			}
			r = random.nextInt(200);
			g = random.nextInt(200);
			b = random.nextInt(200);
		}
		
		Color result = new Color(r, g, b);
		
		return result;
	}

	/**
	 * 纵坐标不变，横向正弦曲线Wave扭曲图片
	 * @since 1.0.0
	 * @Date:2012-3-1 下午12:49:47
	 * @return BufferedImage
	 */
	private BufferedImage twistImage(BufferedImage buffImg) {
		int imgWidth = buffImg.getWidth();
		int imgHeight = buffImg.getHeight();
		
		BufferedImage result = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = result.createGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, imgWidth, imgHeight);
		
		Random random = new Random(System.currentTimeMillis());
		double amplitude = random.nextInt(3) + 3;	// 波形的幅度倍数，越大扭曲的程序越高，一般为3
		double phase = random.nextInt(6);	// 波形的起始相位，取值区间（0-2＊PI）
		for (int y = 0; y < imgHeight; y++) {
			double dy = 2 * Math.PI * y / imgHeight + phase;	// 将y坐标映射到2PI上，加上相位
			int offset = (int) (Math.sin(dy) * amplitude);	// 正弦函数乘以振幅
			for (int x1 = 0; x1 < imgWidth; x1++) {
				int x2 = x1 + offset;
				if (0 <= x2 && x2 < imgWidth) {
					//把原来的图上信息拷贝到新的图上
					result.setRGB(x2, y, buffImg.getRGB(x1, y));
				}
			}
		}
		
		return result;
	}

	/**
	 * 获取字体大小
	 * @return
	 */
	public static int getFontSize() {
		return FONT_SIZE;
	}
	/**
	 * 设置字体大小
	 * @param fontSize
	 */
	public static void setFontSize(int fontSize) {
		if (MIN_FONT_SIZE < fontSize) {
			FONT_SIZE = fontSize;
		}
	}

	/**
	 * 获取当前设置的字符数量
	 * @return
	 */
	public static int getCodeCount() {
		return MIN_CODE_COUNT;
	}
	/**
	 * 设置字符数量
	 * @param codeCount
	 */
	public static void setCodeCount(int codeCount) {
		MIN_CODE_COUNT = codeCount;
	}
}
