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
	 * 判断字符串只是由空白字符（空格或者制表符）组成，或者为空（包括null）
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		boolean isBlank = false;
		
		if (null != str && 0 < str.trim().length()) {
			isBlank = true;
		}
		
		return isBlank;
	}
	
	/**
	 * 将驼峰命名转为下划线命名方式
	 * 如：helloWorld->hello_world
	 * @param str
	 * @return
	 */
	public static String upperCase2Underline(String str) {
		String result = null;
		
		if (null != str) {
			StringBuilder builder = new StringBuilder(str);
			for (int i = 0; i < builder.length(); i++) {
				char c = builder.charAt(i);
				if ('A' <= c && 'Z' >= c) {
					c += 32;
					if (0 < i) {
						builder.replace(i, ++i, "_" + c);
					} else {
						builder.replace(i, i + 1, String.valueOf(c));
					}
				}
			}
			result = builder.toString();
		}
		
		return result;
	}
	
	/**
	 * 将下划线命名转为驼峰命名方式
	 * 如：hello_world->helloWorld
	 * @param str
	 * @return
	 */
	public static String underline2UpperCase(String str) {
		String result = null;
		
		if (null != str) {
			StringBuilder builder = new StringBuilder(str);
			for (int currentIndex = 0; currentIndex < builder.length(); currentIndex++) {
				char currentChar = builder.charAt(currentIndex);
				if ('_' == currentChar) {
					int nextIndex = currentIndex + 1;
					if (nextIndex < builder.length()) {
						char nextChar = builder.charAt(nextIndex);
						if ('a' <= nextChar && 'z' >= nextChar) {
							if (0 == currentIndex) {
								builder.deleteCharAt(currentIndex--);
								continue;
							}
							nextChar -= 32;
							builder.replace(currentIndex, ++nextIndex, String.valueOf(nextChar));
						} else if ('_' == nextChar) {
							builder.deleteCharAt(currentIndex--);
						}
					} else {
						builder.deleteCharAt(currentIndex);
					}
				}
			}
			result = builder.toString();
		}
		
		return result;
	}
}
