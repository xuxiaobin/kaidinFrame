package com.kaidin.common.util;

/**
 * 一些基本的不太好分类的工具函数
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 20160429上午09:51:48
 */
public abstract class BaseUtil {
	/**
	 * 比较两个对象是否相同，添加判空逻辑
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equals(Object obj1, Object obj2) {
		if (null == obj1) {
			return null == obj2;
		}
		
		return obj1.equals(obj2);
	}
	
	/**
	 * 比较类似是否相同
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equalsType(Object obj1, Object obj2) {
		if (null != obj1 && null != obj2) {
			return obj1.getClass().equals(obj2.getClass());
		}
		
		return false;
	}
}
