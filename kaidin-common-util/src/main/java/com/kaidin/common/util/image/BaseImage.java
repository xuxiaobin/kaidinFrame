/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util.image;

import java.awt.Color;
import java.awt.Font;

/**
 * 角度转换工具
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2016-5-17下午01:51:48
 */
public class BaseImage {
	/** 默认字体（加粗大小为32） */
	protected Font  font        = new Font("Fixedsys", Font.BOLD, 32);
	/** 字体旋转的角度 */
	protected float fontDegree  = 0;
	/** 透明度 */
	protected float diaphaneity = 0.8F;
	/** 字体颜色，默认为白色 */
	protected Color fontColor   = Color.WHITE;

	/**
	 * @return property value of font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @param font value to assigned to property font
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * @return property value of fontDegree
	 */
	public float getFontDegree() {
		return fontDegree;
	}

	/**
	 * @param fontDegree value to assigned to property fontDegree
	 */
	public void setFontDegree(float fontDegree) {
		this.fontDegree = fontDegree;
	}

	/**
	 * @return property value of diaphaneity
	 */
	public float getDiaphaneity() {
		return diaphaneity;
	}

	/**
	 * @param diaphaneity value to assigned to property diaphaneity
	 */
	public void setDiaphaneity(float diaphaneity) {
		this.diaphaneity = diaphaneity;
	}

	/**
	 * @return property value of fontColor
	 */
	public Color getFontColor() {
		return fontColor;
	}

	/**
	 * @param fontColor value to assigned to property fontColor
	 */
	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

}
