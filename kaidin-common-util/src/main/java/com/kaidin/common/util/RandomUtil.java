/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util;

import java.util.Random;

import com.kaidin.common.constant.ConstType;

/**
 * 随机函数工具
 * @version 1.0
 * @author xuxiaobin	kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public abstract class RandomUtil {
	static final char[] charArray36 = ConstType.charSet.BASE36.toCharArray();

	/**
	 * 随机一个整数[beginValue, endValue]
	 * 支持endValue < beginValue
	 * @param beginValue
	 * @param endValue
	 * @return
	 */
	public static int nextInt(int beginValue, int endValue) {
		int betweenValue = endValue - beginValue;
		if (0 < betweenValue) {
			return beginValue + new Random().nextInt(betweenValue + 1);
		}
		if (0 > betweenValue) {
			return new Random().nextInt(-1 * betweenValue + 1) + endValue;
		}

		return beginValue;
	}

	/**
	 * 随机长度11的字符串，每毫秒可以随机不容易重复
	 * 36^11需要二进制57bit
	 * 6bit随机数+6bit随机数+45bit当前时间毫秒数，57bit转为base36而来
	 * 45bit可以表示到3084年
	 * @return
	 */
	public static String nextBase36Code() {
		Random random = new Random();
		long randomValue = random.nextInt(1 << 6 + 1);
		randomValue <<= 6;
		randomValue |= random.nextInt(1 << 6 + 1);
		randomValue <<= 45;
		randomValue |= System.currentTimeMillis();

		char[] charArry = new char[11];
		int count = 0;
		while (randomValue > 0) {
			Long index = randomValue % charArray36.length;
			charArry[count++] = charArray36[index.intValue()];
		}

		return new String(charArry);
	}
}
