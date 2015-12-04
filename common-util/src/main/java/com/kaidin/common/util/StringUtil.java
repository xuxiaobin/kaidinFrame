package com.kaidin.common.util;
/**
 * 字符串独立的常用方法
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class StringUtil {
	/**
	 * 将首字母大写
	 * @param str
	 * @return
	 */
	public static String toUpperCaseAtFirst(String str) {
		if (null != str && !str.isEmpty()) {
			String firstChar = str.substring(0, 1);
			return str.replaceFirst(firstChar, firstChar.toUpperCase());
		} else {
			return str;
		}
	}
	
	/**
	 * 判断字符串只是由空白字符组成，或者为空（包括null）
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		boolean isBlank = true;
		
		if (null != str && 0 < str.trim().length()) {
			isBlank = false;
		}
		
		return isBlank;
	}
}
