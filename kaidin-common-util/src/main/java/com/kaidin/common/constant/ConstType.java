/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.constant;

import java.io.Serializable;

/**
 * 本类中的属性全都是静态的常量
 * 提供一些常用的常量，方便引用
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public abstract class ConstType implements Serializable {
	private static final long serialVersionUID = -3599123231405776466L;

	/**
	 * 常用正则表达式
	 */
	public static class regex {
		/** 邮箱地址正则 */
		public static final String MAIL_ADDR = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
		/** 中文汉字正则 */
		public static final String CHANESE   = "[\u4e00-\u9fa5]";
		/** ip地址正则 */
		public static final String IP_ADDR   = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])(\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])){3}$";
	}

	/**
	 * 时间毫秒相关
	 */
	public static class time {
		/** 1秒钟毫秒数 */
		public static final long MS_OF_SECOND = 1000L;
		/** 1分钟毫秒数 */
		public static final long MS_OF_MINUTE = 60 * MS_OF_SECOND;
		/** 1小时毫秒数 */
		public static final long MS_OF_HOUR   = 60 * MS_OF_MINUTE;
		/** 1天毫秒数 */
		public static final long MS_OF_DAY    = 24 * MS_OF_HOUR;
		/** 1周毫秒数 */
		public static final long MS_OF_WEEK   = 7 * MS_OF_DAY;
	}

	/**
	 * IP地址相关
	 */
	public static class ip {
		/** 本机ip地址 Long形式 */
		public static final Long   LOCALHOST_VALUE = Long.decode("0x7f000001");
		/** 最大ip地址 Long形式 */
		public static final Long   MAX_VALUE       = Long.decode("0xffffffff");
		/** 最小ip地址 Long形式 */
		public static final Long   MIN_VALUE       = 0L;
		/** ip地址正则 */
		public static final String ADDR_REGEX      = regex.IP_ADDR;
		/** 本机域名地址 localhost形式 */
		public static final String LOCALHOST       = "localhost";
		/** 本机ip地址 127.0.0.1形式 */
		public static final String LOCALHOST_IP    = "127.0.0.1";
	}

	/**
	 * 文件大小
	 */
	public static class fileSize {
		/** 文件单位 */
		public static final String UNITS  = "KMGTE";
		/** 1K的字节数 */
		public static final long   K_SIZE = 1024;        // 2^10
		/** 1M的字节数 */
		public static final long   M_SIZE = K_SIZE << 10; // 2^20
		/** 1G的字节数 */
		public static final long   G_SIZE = M_SIZE << 10; // 2^30
		/** 1T的字节数 */
		public static final long   T_SIZE = G_SIZE << 10; // 2^40
		/** 1E的字节数 */
		public static final long   E_SIZE = T_SIZE << 10; // 2^50
		/** 1Z的字节数 */
		public static final long   Z_SIZE = E_SIZE << 10; // 2^60
	}

	/**
	 * 字符集相关
	 */
	public static class charSet {
		/** 适合做验证码，字母大写；规避0和o，1和i，2和z不容易区分 */
		public static final String BASE30     = "3456789ABCDEFGHJKLMNPQRSTUVWXY";
		/** 字母小写；0~9的数字和字母22个 */
		public static final String BASE32     = "0123456789abcdefghijklmnopqrstuv";
		/** 字母小写；0~9的数字和小写字母26个 */
		public static final String BASE36     = BASE32 + "wxyz";
		/** 0~9的数字和大小写字母52个 */
		public static final String BASE62     = BASE36 + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		/** 相比base64，删除+/0OIl */
		public static final String BASE58     = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

		/** 中文超大字符集 */
		public static final String GBK        = "GBK";
		/** ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1 */
		public static final String ISO_8859_1 = "ISO-8859-1";
		/** 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块 */
		public static final String US_ASCII   = "US-ASCII";
		/** 8 位 UCS 转换格式 */
		public static final String UTF_8      = "UTF-8";
		/** 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识 */
		public static final String UTF_16     = "UTF-16";
		/** 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序 */
		public static final String UTF_16BE   = "UTF-16BE";
		/** 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序 */
		public static final String UTF_16LE   = "UTF-16LE";
	}

	/**
	 * 基本数据类型
	 */
	public static class baseType {
		/** short */
		public static final String P_short    = "short";
		/** Short */
		public static final String P_SHORT    = "Short";
		/** int */
		public static final String P_int      = "int";
		/** Integer */
		public static final String P_INTEGER  = "Integer";
		/** long */
		public static final String P_long     = "long";
		/** Long */
		public static final String P_LONG     = "Long";
		/** float */
		public static final String P_float    = "float";
		/** Float */
		public static final String P_FLOAT    = "Float";
		/** double */
		public static final String P_double   = "double";
		/** Double */
		public static final String P_DOUBLE   = "Double";
		/** byte */
		public static final String P_byte     = "byte";
		/** Byte */
		public static final String P_BYTE     = "Byte";
		/** char */
		public static final String P_char     = "char";
		/** Char */
		public static final String P_CHAR     = "Char";
		/** boolean */
		public static final String P_boolean  = "boolean";
		/** Boolean */
		public static final String P_BOOLEAN  = "Boolean";
		/** String */
		public static final String P_STRING   = "String";
		/** Date */
		public static final String P_DATE     = "Date";
		/** Time */
		public static final String P_TIME     = "Time";
		/** DateTime */
		public static final String P_DATETIME = "DateTime";
		/** Object */
		public static final String P_OBJECT   = "Object";
	}
}
