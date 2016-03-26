package com.kaidin.common.constant;

import java.io.Serializable;
/**
 * 本类中的属性全都是静态的常量
 * 提供一些常用的常量，方便引用
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class ConstType implements Serializable {
	private static final long serialVersionUID = -3599123231405776466L;

	/**
	 * 正则表达式
	 */
	public static class REGEX {
		public static final String MAIL_ADDR	= "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
//		public static final String IP_ADDR		= "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])(\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])){3}\\b";
		public static final String IP_ADDR		= "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])(\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])){3}$";
	}
	
	/**
	 * 时间相关
	 */
	public static class TIME {
		public static final long MS_OF_SECOND	= 1000;
		public static final long MS_OF_MINUTE	= 60 * MS_OF_SECOND;
		public static final long MS_OF_HOUR		= 60 * MS_OF_MINUTE;
		public static final long MS_OF_DAY		= 24 * MS_OF_HOUR;
		public static final long MS_OF_WEEK		= 7 * MS_OF_DAY;
	}
	
	/**
	 * IP地址相关
	 */
	public static class IP {
		public static final Long LOCALHOST_VALUE	= 2130706433L;	//本地ip地址127.0.0.1
		public static final Long MAX_VALUE			= 4294967295L;	//最大的ip地址对应的数值
		public static final Long MIN_VALUE			= 0L;	//最小的ip地址对应的数值
		
		public static final String ADDR_REGEX		= REGEX.IP_ADDR;	//ip地址的正则
		public static final String LOCALHOST		= "localhost";
		public static final String LOCALHOST_IP		= "127.0.0.1";
	}
	
	/**
	 * 字符集相关
	 */
	public static class CHARSET {
		public static final String GBK			= "GBK";	// 中文超大字符集
		public static final String ISO_8859_1	= "ISO-8859-1";	// ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1
		public static final String US_ASCII		= "US-ASCII";	// 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块
		public static final String UTF_8		= "UTF-8"; // 8 位 UCS 转换格式
		public static final String UTF_16		= "UTF-16";	// 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识
	    public static final String UTF_16BE		= "UTF-16BE";	// 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序
	    public static final String UTF_16LE		= "UTF-16LE";	// 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序
	}
	
	/**
	 * 基本数据类型
	 */
	public static class BASE_TYPE {
		public static final String DATATYPE_short	= "short";
		public static final String DATATYPE_SHORT	= "Short";
		public static final String DATATYPE_int	= "int";
		public static final String DATATYPE_INTEGER	= "Integer";
		public static final String DATATYPE_long	= "long";
		public static final String DATATYPE_LONG	= "Long";
		public static final String DATATYPE_float	= "float";
		public static final String DATATYPE_FLOAT	= "Float";
		public static final String DATATYPE_double	= "double";
		public static final String DATATYPE_DOUBLE	= "Double";
		
		public static final String DATATYPE_byte	= "byte";
		public static final String DATATYPE_BYTE	= "Byte";
		public static final String DATATYPE_char	= "char";
		public static final String DATATYPE_CHAR	= "Char";
		public static final String DATATYPE_boolean	= "boolean";
		public static final String DATATYPE_BOOLEAN	= "Boolean";
		public static final String DATATYPE_STRING	= "String";
		public static final String DATATYPE_DATE	= "Date";
		public static final String DATATYPE_TIME	= "Time";
		public static final String DATATYPE_DATETIME	= "DateTime";
		public static final String DATATYPE_OBJECT	= "Object";
	}
}
