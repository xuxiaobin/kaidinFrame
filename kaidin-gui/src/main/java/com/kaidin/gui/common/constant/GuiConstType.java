package com.kaidin.gui.common.constant;

import com.kaidin.biz.service.ICaptchaService;

import java.io.Serializable;

public class GuiConstType implements Serializable {
	private static final long serialVersionUID = 4809630076137129264L;
	
	/**
	 * 保存session中数据的key
	 */
	public static class SessionKey {
		// 验证码信息
		public static final String CAPTCHA = ICaptchaService.CAPTCHA;
		// 用户信息
		public static final String USER	= "user";
	}
	
	/**
	 * 菜单等级
	 */
	public static class Menu {
		// 顶级菜单编码
		public static final short LEVEL_1 = 1;
		// 二级菜单编码
		public static final short LEVEL_2 = 2;
		// 三级菜单编码
		public static final short LEVEL_3 = 3;
		// 四级菜单编码
		public static final short LEVEL_4 = 4;
	}
}
