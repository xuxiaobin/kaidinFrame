package com.kaidin.common.util;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 简单类型数据之间互转工具
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class DataTypeUtil {
	
	public static Byte asByte(Object obj) {
		Byte result = null;
		
		if (null != obj) {
			if (obj instanceof Number) {
				result = ((Number) obj).byteValue();
			} else {
				result = Byte.valueOf(obj.toString());
			}
		}
		
		return result;
	}
	public static Byte asByte(Object obj, byte defaultValue) {
		return null == obj? defaultValue: asByte(obj);
	}
	
	public static Short asShort(Object obj) {
		Short result = null;
		
		if (null != obj) {
			if (obj instanceof Number) {
				result = ((Number) obj).shortValue();
			} else {
				result = Short.valueOf(obj.toString());
			}
		}
		
		return result;
	}
	public static Short asShort(Object obj, short defaultValue) {
		return null == obj? defaultValue: asShort(obj);
	}

	public static Integer asInteger(Object obj) {
		Integer result = null;
		
		if (null != obj) {
			if (obj instanceof Number) {
				result = ((Number) obj).intValue();
			} else {
				result = Integer.valueOf(obj.toString());
			}
		}
		
		return result;
	}
	public static Integer asInteger(Object obj, int defaultValue) {
		return null == obj? defaultValue: asInteger(obj);
	}

	public static Long asLong(Object obj) {
		Long result = null;
		
		if (null != obj) {
			if (obj instanceof Number)
				result = ((Number) obj).longValue();
			if (obj.equals("Null") || obj.equals("")) {
				result = 0L;
			} else {
				result = Long.valueOf(obj.toString());
			}
		}
		
		return result;
	}
	public static Long asLong(Object obj, long defaultValue) {
		return null == obj? defaultValue: asLong(obj);
	}

	public static Float asFloat(Object obj) {
		Float result = null;
		
		if (null != obj) {
			if (obj instanceof Number) {
				result = ((Number) obj).floatValue();
			}
			if (obj.equals("Null") || obj.equals("")) {
				result = 0F;
			} else {
				result = Float.valueOf(obj.toString());
			}
		}
		
		return result;
	}
	public static Float asFloat(Object obj, float defaultValue) {
		return null == obj? defaultValue: asFloat(obj);
	}
	
	public static Double asDouble(Object obj) {
		Double result = null;
		
		if (null != obj) {
			if (obj instanceof Number) {
				result = ((Number) obj).doubleValue();
			} else {
				result = Double.valueOf(obj.toString());
			}
		}
		
		return result;
	}
	public static Double asDouble(Object obj, double defaultValue) {
		return null == obj? defaultValue: asDouble(obj);
	}
	
	public static BigDecimal asBigDecimal(Object obj) {
		return obj == null ? null: new BigDecimal(String.valueOf(obj));
	}
	public static BigDecimal asBigDecimal(Object obj, BigDecimal defaultValue) {
		return null == obj? defaultValue: asBigDecimal(obj);
	}

	public static Boolean asBoolean(Object obj) {
		Boolean result = null;
		
		if (null != obj) {
			if (obj instanceof Boolean) {
				result = ((Boolean) obj).booleanValue();
			} else {
				result = false;
				if (obj instanceof Character) {
					Character c = (Character) obj;
					if ('T' == c || 't' == c) {
						result = true;
					}
				} else if (obj instanceof Number) {
					if (((Number) obj).doubleValue() > 0) {
						result = true;
					}
				} else if (obj instanceof String) {
					if ("true".equalsIgnoreCase(obj.toString())
							|| "y".equalsIgnoreCase(obj.toString())) {
						result = true;
					}
				} else {
					throw new RuntimeException("dataType 'Boolean' transform error:" + obj.toString());
				}
			}
		}
		
		return result;
	}
	public static Boolean asBoolean(Object obj, boolean defaultValue) {
		return null == obj? defaultValue: asBoolean(obj);
	}

	public static Character asCharacter(Object obj) {
		Character result = null;
		
		if (null != obj) {
			if (obj instanceof Character) {
				result = ((Character) obj).charValue();
			} else if ((obj instanceof String) && (((String) obj).length() == 1)) {
				result = ((String) obj).charAt(0);
			} else if (obj instanceof Boolean) {
				if ((Boolean) obj) {
					result = 'T';
				} else {
					result = 'F';
				}
			} else {
				result = new Character(obj.toString().charAt(0));
			}
		}
		
		return result;
	}
	public static Character asCharacter(Object obj, char defaultValue) {
		return null == obj? defaultValue: asCharacter(obj);
	}

	public static String asString(Object obj) {
		if (null == obj) {
			return null;
		} else {
			return obj.toString();
		}
	}
	public static String asString(Object obj, String defaultValue) {
		return null == obj? defaultValue: asString(obj);
	}

	public static Date asDate(Object obj) {
		Date result = null;;
		
		if (null != obj) {
			if (obj instanceof Date) {
				result = (Date) obj;
			} else if (obj instanceof Number) {
				result = new Date(asLong(obj));
			} else if (obj instanceof String) {
				try {
					result = DateTimeUtil.getStringToDate(obj.toString());;
				} catch (Exception e) {
					throw new RuntimeException("dataType 'Date' transform error:" + obj.toString());
				}
			} else {
				throw new RuntimeException("dataType 'Date' transform error:" + obj.toString());
			}
		}
		
		return result;
	}
	public static Date asDate(Object obj, Date defaultValue) {
		return null == obj? defaultValue: asDate(obj);
	}
}
