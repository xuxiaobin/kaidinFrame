package com.kaidin.common.util;

import java.util.Random;

public class RandomUtil {
	public static int nextInt(int beginValue, int endValue) {
		int result = beginValue;
		
		int betweenValue = endValue - beginValue;
		if (0 < betweenValue) {
			result = new Random().nextInt(betweenValue) + beginValue;
		} else if (0 > betweenValue) {
			result = new Random().nextInt(-1 * betweenValue) + endValue;
		}
		
		return result;
	}
}
