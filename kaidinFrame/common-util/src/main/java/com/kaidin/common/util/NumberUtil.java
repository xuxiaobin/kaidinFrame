package com.kaidin.common.util;

import java.text.DecimalFormat;
/**
 * 数值转换工具
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class NumberUtil {
	private static final String DECIMAL_PLACES_2	= "#0.00";
	private static final String DECIMAL_PLACES_4	= "#0.0000";
	private static final String DECIMAL_PLACES_PERCENT_2	= "#0.00%";

	
	/**
	 * 两数相除，判空和除数不能为0
	 * @param dividend
	 * @param divisor
	 * @return
	 */
	public static Number divided(Number dividend, Number divisor) {
		Number result = null;

		if (null != dividend && null != divisor && 0.0 != divisor.doubleValue()) {
			// 判空和除数不能为0
			result = dividend.doubleValue() / divisor.doubleValue();
		}

		return result;
	}

	/**
	 * 格式化数字保留2位小数
	 * @param number
	 * @return
	 */
	public static String format2Decimal(Number number) {
		return formatNumber(number, DECIMAL_PLACES_2);
	}

	/**
	 * 格式化数字保留4位小数
	 * 
	 * @param number
	 * @return
	 */
	public static String format4Decimal(Number number) {
		return formatNumber(number, DECIMAL_PLACES_4);
	}

	/**
	 * 格式化数据到2位小数百分比
	 * @param number
	 * @return
	 */
	public static String formatPercent(Number number) {
		return formatNumber(number, DECIMAL_PLACES_PERCENT_2);
	}

	private static String formatNumber(Number number, String pattern) {
		String result = "N/A";

		if (number != null) {
			DecimalFormat df = new DecimalFormat();
			df.applyPattern(pattern);
			result = df.format(number);
		}

		return result;
	}

	/**
	 * 格式化小数后面的0，如果是整数的话去掉小数点
	 * @param number
	 * @return
	 */
	public static String formatEndOf0(Number number) {
		String result = null;

		result = String.valueOf(number);
		if (null != result && 0 < result.indexOf(".")) {
			// 正则表达
			result = result.replaceAll("0+?$", ""); // 去掉后面无用的零
			result = result.replaceAll("[.]$", ""); // 如小数点后面全是零则去掉小数点
		}

		return result;
	}
}
