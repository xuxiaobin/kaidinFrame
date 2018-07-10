/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util;

import java.util.Random;

/**
 * 随机函数工具
 * @version 1.0
 * @author xuxiaobin	kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public abstract class RandomUtil {
	/**
	 * 随机一个整数
	 * @param beginValue
	 * @param endValue
	 * @return
	 */
	public static int nextInt(int beginValue, int endValue) {
		int betweenValue = endValue - beginValue;
		if (0 < betweenValue) {
			return beginValue + new Random().nextInt(betweenValue);
		}
		if (0 > betweenValue) {
			return new Random().nextInt(-1 * betweenValue) + endValue;
		}

		return beginValue;
	}
}
