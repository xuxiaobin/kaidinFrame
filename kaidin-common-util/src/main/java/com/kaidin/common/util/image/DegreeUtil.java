/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util.image;

import com.kaidin.common.util.DataTypeUtil;
/**
 * 角度转换工具
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2016-5-17下午01:51:48
 */
public abstract class DegreeUtil {
	/**
	 * 将角度转为0~2pi范围的弧度
	 * @param angle
	 * @return
	 */
	public static float angle2radian(float angle) {
		double result = angle / 180 * Math.PI % (2 * Math.PI);
		return DataTypeUtil.asFloat(0 > result? result + Math.PI * 2: result);
	}
	
	/**
	 * 将弧度转换为0~360度范围的角度
	 * @param radian
	 * @return
	 */
	public static float radian2angle(float radian) {
		float result = DataTypeUtil.asFloat(radian / Math.PI * 180 % 360);
		return 0 > result? result + 360: result;
	}
}
