package com.kaidin.common.util;

/**
 * 一些基本的不太好分类的工具函数
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 20160429上午09:51:48
 */
public class BaseUtil {
	/**
	 * 比较两个对象是否相同，添加判空逻辑
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equalsWithNull(Object obj1, Object obj2) {
		boolean result;
		
		if (null == obj1) {
			result = null == obj2;
		} else {
			result = obj1.equals(obj2);
		}
		
		return result;
	}
}
