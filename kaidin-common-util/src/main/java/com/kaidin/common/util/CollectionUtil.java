/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 集合转换工具
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public abstract class CollectionUtil {
	/**
	 * 判断结合是否为空
	 * 
	 * @param collection
	 * @return
	 */
	public static <T> boolean isEmpty(Collection<T> collection) {
		return null == collection || collection.isEmpty();
	}

	/**
	 * 判断结合是否不为空
	 * 
	 * @param collection
	 * @return
	 */
	public static <T> boolean isNotEmpty(Collection<T> collection) {
		return null != collection && !collection.isEmpty();
	}

	/**
	 * 判断数组是否为不空
	 * 
	 * @param array
	 * @return
	 */
	public static <T> boolean isEmpty(T[] array) {
		if (null == array || 0 == array.length) {
			return true;
		}
		for (T item : array) {
			if (null != item) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断数组是否为不空
	 * 
	 * @param array
	 * @return
	 */
	public static <T> boolean isNotEmpty(T[] array) {
		if (null == array || 0 == array.length) {
			return false;
		}

		for (T item : array) {
			if (null != item) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断map是否为空
	 * 
	 * @param map
	 * @return
	 */
	public static <K, V> boolean isEmpty(Map<K, V> map) {
		return null == map || map.isEmpty();
	}

	/**
	 * 判断map是否为不空
	 * 
	 * @param map
	 * @return
	 */
	public static <K, V> boolean isNotEmpty(Map<K, V> map) {
		return null != map && !map.isEmpty();
	}

	/**
	 * 将字符串列表转换为定长的字符串数组
	 * 
	 * @param dataList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] asArray(Collection<T> dataList) {
		if (null == dataList) {
			return null;
		}

		return (T[]) dataList.toArray();
	}

	/**
	 * 将字符串数组转换为定长的字符串列表
	 * 
	 * @param dataArray
	 * @return
	 */
	public static <T> ArrayList<T> asArrayList(T[] dataArray) {
		if (null == dataArray) {
			return null;
		}

		return (ArrayList<T>) Arrays.asList(dataArray);
	}

	/**
	 * 截取list
	 * @param dataList
	 * @param size
	 * @return
	 */
	public <T> List<T> subList(List<T> dataList, int size) {
		return subList(dataList, 0, size);
	}

	/**
	 * 截取list
	 * @param dataList
	 * @param fromIndex
	 * @param toIndex
	 * @return
	 */
	public <T> List<T> subList(List<T> dataList, int fromIndex, int toIndex) {
		if (0 >= toIndex || CollectionUtil.isEmpty(dataList)) {
			return null;
		}
		int listSize = dataList.size();
		fromIndex = Math.min(fromIndex, listSize);
		toIndex = Math.min(toIndex, listSize);

		return dataList.subList(fromIndex, toIndex);
	}

	/**
	 * 仿照oracle的decode方法
	 * 
	 * @param obj
	 * @param defaultValue
	 * @param expect1Obj
	 * @param expect1Value
	 * @param objArrray
	 * @return
	 */
	public static Object decode(Object obj, Object defaultValue, Object expect1Obj, Object expect1Value, Object... objArrray) {
		if (BaseUtil.equals(obj, expect1Obj)) {
			return expect1Value;
		}

		for (int i = 0; i < objArrray.length; i++) {
			if (BaseUtil.equals(obj, objArrray[i++])) {
				if (i < objArrray.length) {
					return objArrray[i];
				}
			}
		}

		return defaultValue;
	}
}
