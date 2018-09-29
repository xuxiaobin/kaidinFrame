/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 简单类型数据之间互转工具
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public abstract class DataTypeUtil {
	private static final String NULL_STR = "null";

	/**
	 * 如果value不为null，返回value，否则返回defaultValue
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static <T> T asDefault(T value, T defaultValue) {
		return null != value ? value : defaultValue;
	}

	public static Byte asByte(Object obj) {
		if (null == obj) {
			return null;
		}
		if (obj instanceof Number) {
			return ((Number) obj).byteValue();
		}
		String objStr = obj.toString();
		if (0 < objStr.length() && !NULL_STR.equals(objStr)) {
			return Byte.valueOf(objStr, 10);
		}

		return null;
	}

	public static Byte asByte(Object obj, byte defaultValue) {
		return null == obj ? defaultValue : asByte(obj);
	}

	public static Short asShort(Object obj) {
		if (null == obj) {
			return null;
		}
		if (obj instanceof Number) {
			return ((Number) obj).shortValue();
		}
		String objStr = obj.toString();
		if (0 < objStr.length() && !NULL_STR.equals(objStr)) {
			return Short.valueOf(objStr, 10);
		}

		return null;
	}

	public static Short asShort(Object obj, short defaultValue) {
		return null == obj ? defaultValue : asShort(obj);
	}

	public static Integer asInteger(Object obj) {
		if (null == obj) {
			return null;
		}
		if (obj instanceof Number) {
			return ((Number) obj).intValue();
		}
		String objStr = obj.toString();
		if (0 < objStr.length() && !NULL_STR.equals(objStr)) {
			return Integer.valueOf(objStr);
		}

		return null;
	}

	public static Integer asInteger(Object obj, int defaultValue) {
		return null == obj ? defaultValue : asInteger(obj);
	}

	public static Long asLong(Object obj) {
		if (null == obj) {
			return null;
		}
		if (obj instanceof Number) {
			return ((Number) obj).longValue();
		}
		String objStr = obj.toString();
		if (0 < objStr.length() && !NULL_STR.equals(objStr)) {
			return Long.valueOf(objStr);
		}

		return null;
	}

	public static Long asLong(Object obj, long defaultValue) {
		return null == obj ? defaultValue : asLong(obj);
	}

	public static Float asFloat(Object obj) {
		if (null == obj) {
			return null;
		}
		if (obj instanceof Number) {
			return ((Number) obj).floatValue();
		}
		String objStr = obj.toString();
		if (0 < objStr.length() && !NULL_STR.equals(objStr)) {
			return Float.valueOf(objStr);
		}

		return null;
	}

	public static Float asFloat(Object obj, float defaultValue) {
		return null == obj ? defaultValue : asFloat(obj);
	}

	public static Double asDouble(Object obj) {
		if (null == obj) {
			return null;
		}
		if (obj instanceof Number) {
			return ((Number) obj).doubleValue();
		}
		String objStr = obj.toString();
		if (0 < objStr.length() && !NULL_STR.equals(objStr)) {
			return Double.valueOf(objStr);
		}

		return null;
	}

	public static Double asDouble(Object obj, double defaultValue) {
		return null == obj ? defaultValue : asDouble(obj);
	}

	public static BigDecimal asBigDecimal(Object obj) {
		if (null == obj) {
			return null;
		}
		String objStr = String.valueOf(obj);
		if (0 < objStr.length() && !NULL_STR.equals(objStr)) {
			return new BigDecimal(objStr);
		}

		return null;
	}

	public static BigDecimal asBigDecimal(Object obj, BigDecimal defaultValue) {
		return null == obj ? defaultValue : asBigDecimal(obj);
	}

	public static Boolean asBoolean(Object obj) {
		if (null == obj) {
			return null;
		}

		if (obj instanceof Boolean) {
			return (Boolean) obj;
		}
		if (obj instanceof Character) {
			Character c = (Character) obj;
			return ('T' == c || 't' == c);
		}
		if (obj instanceof Number) {
			return (((Number) obj).doubleValue() > 0);
		}
		if (obj instanceof String) {
			String objStr = obj.toString();
			return ("true".equalsIgnoreCase(objStr) || "y".equalsIgnoreCase(objStr) || "yes".equalsIgnoreCase(objStr));
		}
		throw new RuntimeException("dataType 'Boolean' transform error:" + obj.toString());
	}

	public static Boolean asBoolean(Object obj, boolean defaultValue) {
		return null == obj ? defaultValue : asBoolean(obj);
	}

	public static Character asCharacter(Object obj) {
		if (null == obj) {
			return null;
		}
		if (obj instanceof Character) {
			return ((Character) obj).charValue();
		}
		if ((obj instanceof String) && (((String) obj).length() == 1)) {
			return ((String) obj).charAt(0);
		}
		if (obj instanceof Boolean) {
			return (Boolean) obj ? 'T' : 'F';
		}
		return new Character(obj.toString().charAt(0));
	}

	public static Character asCharacter(Object obj, char defaultValue) {
		return null == obj ? defaultValue : asCharacter(obj);
	}

	public static String asString(Object obj) {
		return null == obj ? null : obj.toString();
	}

	public static String asString(Object obj, String defaultValue) {
		return null == obj ? defaultValue : asString(obj);
	}

	public static Date asDate(Object obj) {
		if (null == obj) {
			return null;
		}

		if (obj instanceof Date) {
			return (Date) obj;
		}
		if (obj instanceof Number) {
			return new Date(asLong(obj));
		}
		if (obj instanceof String) {
			try {
				return DateTimeUtil.getStringToDate(obj.toString());
			} catch (Exception e) {
				throw new RuntimeException("dataType 'Date' transform error:" + obj.toString());
			}
		}
		throw new RuntimeException("dataType 'Date' transform error:" + obj.toString());
	}

	public static Date asDate(Object obj, Date defaultValue) {
		return null == obj ? defaultValue : asDate(obj);
	}
}
