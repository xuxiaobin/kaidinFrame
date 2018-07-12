/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util;

import java.text.MessageFormat;

/**
 * 字符串独立的常用方法
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public abstract class StringUtil {
	/** 空字符串 */
	public static final String EMPTY_STR = "";

	/**
	 * 判断字符串是否为空
	 * StringUtil.isEmpty(null)	= true
	 * StringUtil.isEmpty("")	= true
	 * StringUtil.isEmpty(" ")	= false
	 * StringUtil.isEmpty("xb")	= false
	 * StringUtil.isEmpty(" xb ")	= false
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return null == str || str.isEmpty();
	}

	/**
	 * 判断字符串是否不为空
	 * StringUtil.isNotEmpty(null)	= false
	 * StringUtil.isNotEmpty("")	= false
	 * StringUtil.isNotEmpty(" ")	= true
	 * StringUtil.isNotEmpty("xb")	= true
	 * StringUtil.isNotEmpty(" xb ")	= true
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return null != str && !str.isEmpty();
	}

	/**
	 * 判断字符串只是由空白字符（空格或者制表符）组成，或者为空（包括null）
	 * StringUtil.isBlank(null)	= true
	 * StringUtil.isBlank("")	= true
	 * StringUtil.isBlank(" ")	= true
	 * StringUtil.isBlank("xb")	= false
	 * StringUtil.isBlank(" xb ")	= false
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		if (null == str) {
			return true;
		}
		for (char ch : str.toCharArray()) {
			if (!Character.isWhitespace(ch)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断字符串是否包含摸个字符
	 * @param str
	 * @param s
	 * @return
	 */
	public static boolean contains(String str, CharSequence s) {
		if (null == str) {
			return false;
		}
		return str.contains(s);
	}

	/**
	 * 判断是否包含空白字符
	 * @param str
	 * @return
	 */
	public static boolean containsWhitespace(CharSequence str) {
		if (null == str) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断字符串只是由非空白字符（空格或者制表符）组成，或者为非空（包括null）
	 * StringUtil.isBlank(null)	= true
	 * StringUtil.isBlank("")	= true
	 * StringUtil.isBlank(" ")	= true
	 * StringUtil.isBlank("xb")	= false
	 * StringUtil.isBlank(" xb ")	= false
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		if (null == str) {
			return false;
		}
		for (char ch : str.toCharArray()) {
			if (!Character.isWhitespace(ch)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断两个字符串是否相同，添加判空逻辑（大小写敏感）
	 * StringUtil.equals(null, null)	= true
	 * StringUtil.equals(null, "xb")	= false
	 * StringUtil.equals("xb", null)	= false
	 * StringUtil.equals("xb", "xb")	= true
	 * StringUtil.equals("xb", "XB")	= false
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equals(String str1, String str2) {
		return BaseUtil.equals(str1, str2);
	}

	/**
	 * 忽略大小写之后判断两个字符串是否相同，添加判空逻辑（大小写不敏感）
	 * StringUtil.equalsIgnoreCase(null, null)	= true
	 * StringUtil.equalsIgnoreCase(null, "xb")	= false
	 * StringUtil.equalsIgnoreCase("xb", null)	= false
	 * StringUtil.equalsIgnoreCase("xb", "xb")	= true
	 * StringUtil.equalsIgnoreCase("xb", "XB")	= true
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		if (null == str1) {
			return null == str2;
		}

		return str1.equalsIgnoreCase(str2);
	}

	/**
	 * 判断字符串str是否是prefix开头（大小写敏感）
	 * StringUtil.startsWith(null, null)	= false
	 * StringUtil.startsWith(null, "xb")	= false
	 * StringUtil.startsWith("xb", null)	= false
	 * StringUtil.startsWith("abc", "abc")	= true
	 * StringUtil.startsWith("abc", "abcd")	= false
	 * StringUtil.startsWith(" abcd", " abc")	= true
	 * @param str
	 * @param prefix
	 * @return
	 */
	public static boolean startsWith(String str, String prefix) {
		if (null == str || null == prefix) {
			return false;
		}
		return str.startsWith(prefix);
	}

	/**
	 * 判断字符串str是否是prefix开头（大小写不敏感）
	 * StringUtil.startsWithIgnoreCase(null, null)	= false
	 * StringUtil.startsWithIgnoreCase(null, "xb")	= false
	 * StringUtil.startsWithIgnoreCase("xb", null)	= false
	 * StringUtil.startsWithIgnoreCase("abc", "aBc")	= true
	 * StringUtil.startsWithIgnoreCase("abc", "abcd")	= false
	 * StringUtil.startsWithIgnoreCase(" abcd", " ABc")	= true
	 * @param str
	 * @param prefix
	 * @return
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (null == str || null == prefix) {
			return false;
		}
		if (str.startsWith(prefix)) {
			return true;
		}
		if (str.length() < prefix.length()) {
			return false;
		}
		String tmpStr = str.substring(0, prefix.length());
		return tmpStr.equalsIgnoreCase(prefix);
	}

	/**
	 * 判断字符串str是否是suffix结尾（大小写敏感）
	 * StringUtil.endWith(null, null)	= false
	 * StringUtil.endWith(null, "xb")	= false
	 * StringUtil.endWith("xb", null)	= false
	 * StringUtil.endWith("abc", "abc")	= true
	 * StringUtil.endWith("bcd", "abcd")	= false
	 * StringUtil.endWith(" abcd", "bcd")	= true
	 * @param str
	 * @param prefix
	 * @return
	 */
	public static boolean endWith(String str, String suffix) {
		if (null == str || null == suffix) {
			return false;
		}
		return str.endsWith(suffix);
	}

	/**
	 * 判断字符串str是否是suffix结尾（大小写不敏感）
	 * StringUtil.endWithIgnoreCase(null, null)	= false
	 * StringUtil.endWithIgnoreCase(null, "xb")	= false
	 * StringUtil.endWithIgnoreCase("xb", null)	= false
	 * StringUtil.endWithIgnoreCase("abc", "aBc")	= true
	 * StringUtil.endWithIgnoreCase("abc", "abcd")	= false
	 * StringUtil.endWithIgnoreCase(" abcd", "BCD")	= true
	 * @param str
	 * @param prefix
	 * @return
	 */
	public static boolean endWithIgnoreCase(String str, String suffix) {
		if (null == str || null == suffix) {
			return false;
		}
		if (str.endsWith(suffix)) {
			return true;
		}
		int strLength = str.length();
		int suffixLength = suffix.length();
		if (strLength < suffixLength) {
			return false;
		}
		String tmpStr = str.substring(strLength - suffixLength);
		return tmpStr.equalsIgnoreCase(suffix);
	}

	/**
	 * 将首字母大写
	 * StringUtil.toUpperCaseAtFirst(null)	= null
	 * StringUtil.toUpperCaseAtFirst("")	= ""
	 * StringUtil.toUpperCaseAtFirst("abc")	= "Abc"
	 * @param str
	 * @return
	 */
	public static String toUpperCaseAtFirst(String str) {
		if (isEmpty(str)) {
			return str;
		}
		String firstChar = str.substring(0, 1);
		return str.replaceFirst(firstChar, firstChar.toUpperCase());
	}

	/**
	 * 将字符串字母大写
	 * StringUtil.toUpperCase(null)	= null
	 * StringUtil.toUpperCase("")	= ""
	 * StringUtil.toUpperCase("abc")	= "ABC"
	 * @param str
	 * @return
	 */
	public static String toUpperCase(String str) {
		if (null == str) {
			return null;
		}
		return str.toUpperCase();
	}

	/**
	 * 将字符串字母小写
	 * StringUtil.toUpperCase(null)	= null
	 * StringUtil.toUpperCase("")	= ""
	 * StringUtil.toUpperCase("ABC")	= "abc"
	 * @param str
	 * @return
	 */
	public static String toLowerCase(String str) {
		if (null == str) {
			return null;
		}
		return str.toLowerCase();
	}

	/**
	 * 将驼峰命名转为下划线命名方式
	 * StringUtil.upperCase2Underline(null)	= null
	 * StringUtil.upperCase2Underline("")	= ""
	 * StringUtil.upperCase2Underline("helloWorld")	= "hello_world"
	 * @param str
	 * @return
	 */
	public static String upperCase2Underline(String str) {
		if (null == str) {
			return null;
		}

		int length = str.length();
		StringBuilder builder = new StringBuilder(str);
		for (int i = 0; i < length; i++) {
			char c = builder.charAt(i);
			if ('A' <= c && 'Z' >= c) {
				c += 32;
				if (0 < i) {
					builder.replace(i, ++i, "_" + c);
					length++;
				} else {
					builder.replace(i, i + 1, String.valueOf(c));
				}
			}
		}

		return builder.toString();
	}

	/**
	 * 将下划线命名转为驼峰命名方式
	 * StringUtil.upperCase2Underline(null)	= null
	 * StringUtil.upperCase2Underline("")	= ""
	 * StringUtil.upperCase2Underline("hello_world")	= "helloWorld"
	 * @param str
	 * @return
	 */
	public static String underline2UpperCase(String str) {
		if (null == str) {
			return null;
		}

		int length = str.length();
		StringBuilder builder = new StringBuilder(str);
		for (int currentIndex = 0; currentIndex < length; currentIndex++) {
			char currentChar = builder.charAt(currentIndex);
			if ('_' == currentChar) {
				int nextIndex = currentIndex + 1;
				if (nextIndex < length) {
					char nextChar = builder.charAt(nextIndex);
					if ('a' <= nextChar && 'z' >= nextChar) {
						length--;
						if (0 == currentIndex) {
							builder.deleteCharAt(currentIndex--);
							continue;
						}
						nextChar -= 32;
						builder.replace(currentIndex, ++nextIndex, String.valueOf(nextChar));
					} else if ('_' == nextChar) {
						builder.deleteCharAt(currentIndex--);
						length--;
					}
				} else {
					builder.deleteCharAt(currentIndex);
					length--;
				}
			}
		}

		return builder.toString();
	}

	/**
	 * 获取指定字符串的子串，负索引表示从尾部开始计算
	 * StringUtil.subString(null, *, *)	= null
	 * StringUtil.subString("", *, *)	= ""
	 * StringUtil.subString("abc", 0, 2)	= "ab"
	 * StringUtil.subString("abc", 2, 0)	= ""
	 * StringUtil.subString("abc", 2, 4)	= "c"
	 * StringUtil.subString("abc", 4, 6)	= ""
	 * StringUtil.subString("abc", 2, 2)	= ""
	 * StringUtil.subString("abc", -2, -1)	= "b"
	 * StringUtil.subString("abc", -4, 2)	= "ab"
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static String subString(String str, int start, int end) {
		if (isEmpty(str)) {
			return str;
		}
		int strLen = str.length();
		if (end < 0) {
			end += strLen;
		}
		if (start < 0) {
			start += strLen;
		}

		if (end > strLen) {
			end = strLen;
		}
		if (start > end) {
			return EMPTY_STR;
		}

		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	/**
	 * 格式化参数输出
	 * @param message
	 * @param params
	 * @return
	 */
	public static String format(String message, Object... params) {
		if (null == params || 0 == params.length || null == message) {
			return message;
		}
		return MessageFormat.format(message, params);
	}
}
