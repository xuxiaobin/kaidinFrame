/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date数据类型相关的操作
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public abstract class DateTimeUtil {
	/** 默认的时间格式 */
	private static final String   DEFALT_PATTERN   = "yyyy-MM-dd HH:mm:ss";
	/** 单位 */
	private static final String[] UNIT_ARRAY       = new String[] { "秒", "分", "小时", "天" };
	/** 单位之间的进率 */
	private static final long[]   DECIMALISM_ARRAY = new long[] { 1000 * 60, 60, 24, Long.MAX_VALUE };

	/**
	 * 把字符串值格式化成Date类型
	 * @param dateStr
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date getStringToDate(String dateStr, String pattern) throws ParseException {
		if (null == dateStr) {
			return null;
		}

		DateFormat format = new SimpleDateFormat(pattern);
		return format.parse(dateStr);
	}

	/**
	 * 把yyyy-MM-dd HH:mm:ss形式的字符串转成date类型
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date getStringToDate(String dateStr) throws ParseException {
		return getStringToDate(dateStr, DEFALT_PATTERN);
	}

	/**
	 * 获取source的yyyy-MM-dd HH:mm:ss字符串形式
	 * @param source
	 * @return
	 */
	public static String getDateToString(Date source) {
		if (null == source) {
			return null;
		}

		DateFormat format = new SimpleDateFormat(DEFALT_PATTERN);
		return format.format(source);
	}

	/**
	 * 获取当前时间的yyyy-MM-dd HH:mm:ss字符串形式
	 * @param source
	 * @return
	 */
	public static String getDateToString() {
		return getDateToString(new Date());
	}

	/**
	 * 把毫秒数转成x天x小时x分x秒
	 * 传入时分秒之间的进制和对应的单位名称，如下
	 * new String[]{"毫秒", "秒", "分", "小时", "天"};
	 * new long[]{1000, 60, 60, 24, Long.MAX_VALUE};
	 * @param times
	 * @param units
	 * @param decimalisms
	 * @return
	 */
	public static String convertTimesToString(Long times, String[] units, long[] decimalisms) {
		if (null == times || 0 > times) {
			return null;
		}

		String result = StringUtil.EMPTY_STR;
		for (int i = 0; i < decimalisms.length; i++) {
			long count = times % decimalisms[i];
			result = count + units[i] + result;
			times /= decimalisms[i];
			if (0 >= times) {
				break;
			}
		}

		return result;
	}

	/**
	 * 把毫秒数转成x天x小时x分x秒
	 * new String[]{"毫秒", "秒", "分", "小时", "天"};
	 * new long[]{1000, 60, 60, 24, Long.MAX_VALUE};
	 * @param times
	 * @return
	 */
	public static String convertTimesToString(Long times) {
		//		String[] units = new String[]{"毫秒", "秒", "分", "小时", "天"};
		//		long [] decimalisms = new long[]{1000, 60, 60, 24, Long.MAX_VALUE};
		return convertTimesToString(times, UNIT_ARRAY, DECIMALISM_ARRAY);
	}

	// ################ 下面是date的加减运算 ###########################

	/**
	 * 添加offset个小时，offset可以为负数
	 * @param source
	 * @param offset
	 * @return
	 */
	public static Date addHours(Date source, int offset) {
		Calendar c = Calendar.getInstance();
		if (null != source) {
			c.setTime(source);
		}
		c.add(Calendar.HOUR, offset);
		return c.getTime();
	}

	/**
	 * 获取传入时间的前一个小时的开始时间
	 * 比如2010-10-23 05:03:05 => 2010-10-23 04:00:00
	 * @param source
	 * @return
	 */
	public static Date getLastHourBeginTime(Date source) {
		return getThisHourBeginTime(addHours(source, -1));
	}

	/**
	 * 获取传入时间的小时开始时间
	 * 比如2010-10-23 05:03:05 => 2010-10-23 05:00:00
	 * @param source
	 * @return
	 */
	public static Date getThisHourBeginTime(Date source) {
		Calendar c = Calendar.getInstance();
		if (null != source) {
			c.setTime(source);
		}
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取传入时间的下一天的开始时间
	 * 比如2010-10-23 05:03:05 => 2010-10-23 06:00:00
	 * @param source
	 * @return
	 */
	public static Date getNextHourBeginTime(Date source) {
		return getThisHourBeginTime(addHours(source, 1));
	}

	/**
	 * 添加offset天数，offset可以为负数
	 * @param source
	 * @param offset
	 * @return
	 */
	public static Date addDays(Date source, int offset) {
		Calendar c = Calendar.getInstance();
		if (null != source) {
			c.setTime(source);
		}
		c.add(Calendar.DATE, offset);
		return c.getTime();
	}

	/**
	 * 获取传入时间的前一天的开始时间
	 * 比如2010-10-23 05:03:05 => 2010-10-22 00:00:00
	 * @param source
	 * @return
	 */
	public static Date getLastDayBeginTime(Date source) {
		return getThisDayBeginTime(addDays(source, -1));
	}

	/**
	 * 获取传入时间的天开始时间
	 * 比如2010-10-23 05:03:05 => 2010-10-23 00:00:00
	 * @param source
	 * @return
	 */
	public static Date getThisDayBeginTime(Date source) {
		Calendar c = Calendar.getInstance();
		if (null != source) {
			c.setTime(source);
		}
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取传入时间的下一天的开始时间
	 * 比如2010-10-23 05:03:05 => 2010-10-25 00:00:00
	 * @param source
	 * @return
	 */
	public static Date getNextDayBeginTime(Date source) {
		return getThisDayBeginTime(addDays(source, 1));
	}

	/**
	 * 获取传入时间的周的上个周一开始时间
	 * 比如2010-10-23 05:03:05 => 2010-10-18 00:00:00
	 * @param source
	 * @return
	 */
	public static Date getLastWeekBeginTime(Date source) {
		return getThisWeekBeginTime(addDays(source, -7));
	}

	/**
	 * 获取传入时间的周一开始时间
	 * 比如2010-10-23 05:03:05 => 2010-10-15 00:00:00
	 * @param source
	 * @return
	 */
	public static Date getThisWeekBeginTime(Date source) {
		Calendar c = Calendar.getInstance();
		if (null != source) {
			c.setTime(source);
		}
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取传入时间的周下周一开始时间
	 * 比如2010-10-23 05:03:05 => 2010-10-25 00:00:00
	 * @param source
	 * @return
	 */
	public static Date getNextWeekBeginTime(Date source) {
		return getThisWeekBeginTime(addDays(source, 7));
	}

	public static int getDayNumOfMonth(Date source) {
		Calendar c = Calendar.getInstance();
		if (null != source) {
			c.setTime(source);
		}

		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 添加offset月数，offset可以为负数
	 * @param source
	 * @param offset
	 * @return
	 */
	public static Date addMonths(Date source, int offset) {
		Calendar c = Calendar.getInstance();
		if (null != source) {
			c.setTime(source);
		}
		c.add(Calendar.MONTH, offset);
		return c.getTime();
	}

	/**
	 * 获取传入时间的月上一个月的一号开始时间
	 * 比如2010-10-23 05:03:05 => 2010-09-01 00:00:00
	 * @param source
	 * @return
	 */
	public static Date getLastMonthBeginTime(Date source) {
		return getThisMonthBeginTime(addMonths(source, -1));
	}

	/**
	 * 获取传入时间的月的一号开始时间
	 * 比如2010-10-23 05:03:05 => 2010-10-01 00:00:00
	 * @param source
	 * @return
	 */
	public static Date getThisMonthBeginTime(Date source) {
		Calendar c = Calendar.getInstance();
		if (null != source) {
			c.setTime(source);
		}
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取传入时间的月下一个月的一号开始时间
	 * 比如2010-10-23 05:03:05 => 2010-11-01 00:00:00
	 * @param source
	 * @return
	 */
	public static Date getNextMonthBeginTime(Date source) {
		return getThisMonthBeginTime(addMonths(source, 1));
	}

	/**
	 * 获取是当年的第几天
	 * @param source
	 * @return
	 */
	public static int getDayNumOfYear(Date source) {
		Calendar c = Calendar.getInstance();
		if (null != source) {
			c.setTime(source);
		}

		return c.getActualMaximum(Calendar.DAY_OF_YEAR);
	}
}
