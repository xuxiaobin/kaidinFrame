package com.kaidin.common.util;

import java.util.Random;
/**
 * 随机函数工具
 * @version 1.0
 * @author xuxiaobin	kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public abstract class RandomUtil {
	public static int nextInt(int beginValue, int endValue) {
		int result = beginValue;
		
		int betweenValue = endValue - beginValue;
		if (0 < betweenValue) {
			result += new Random().nextInt(betweenValue);
		} else if (0 > betweenValue) {
			result = new Random().nextInt(-1 * betweenValue) + endValue;
		}
		
		return result;
	}
}
