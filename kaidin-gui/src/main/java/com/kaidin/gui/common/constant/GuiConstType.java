package com.kaidin.gui.common.constant;

import java.io.Serializable;

public class GuiConstType implements Serializable {
	private static final long serialVersionUID = 4809630076137129264L;
	
	/**
	 * 保存session中数据的key
	 */
	public static class SessionKey {
		public static final String CAPTCHA = "captcha";	// 验证码信息
		public static final String USER	= "user";	// 用户信息
	}
	
	/**
	 * 状态
	 */
	public static class Status {
		public static final short OK	= 0x00;	// 状态正常
		public static final short NORMAL	= OK;	// 状态正常
		
		// 大于0的认为是正常使用的状态，
		public static final short ATTEMPT_1	= 0x01;	// 尝试1次
		public static final short ATTEMPT_2	= ATTEMPT_1 + 1;	// 尝试2次
		public static final short ATTEMPT_3	= ATTEMPT_2 + 1;	// 尝试3次
		public static final short ATTEMPT_4	= ATTEMPT_3 + 1;	// 尝试3次
		public static final short ATTEMPT_MAX_TIMES	= 3;	// 最大尝试次数
		public static final short RESET_PASSWD	= 0x0F;	// 强制 重置密码标志
		
		// 小于0的认为是不能正常使用的状态
		public static final short LOCK	= -0x10;	// 锁住
		
		public static final short DELETE	= -0x11;	// 逻辑删除标记
		public static final short UNUSE	= DELETE;	// 逻辑删除标记
	}
	/**
	 * 菜单等级
	 */
	public static class Menu {
		public static final short LEVEL_1 = 1;	// 顶级菜单编码
		public static final short LEVEL_2 = 2;	// 二级菜单编码
		public static final short LEVEL_3 = 3;	// 三级菜单编码
		public static final short LEVEL_4 = 4;	// 四级菜单编码
	}
}
