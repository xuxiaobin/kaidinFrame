package com.kaidin.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * 集合转换工具
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class SetConvertUtil {
	
	/**
	 * 将字符串列表转换为定长的字符串数组
	 * @param dataList
	 * @return
	 */
	public final static String[] listToArray(List<String> dataList) {
		String[] result = null;
		
		if (null != dataList) {
			result = dataList.toArray(new String[dataList.size()]);
		}
		
		return result;
	}
	
	/**
	 * 将字符串数组转换为定长的字符串列表
	 * @param dataArray
	 * @return
	 */
	public final static ArrayList<String> arrayToList(String[] dataArray) {
		ArrayList<String> result = null;
		
		if (null != dataArray) {
			result = (ArrayList<String>) Arrays.asList(dataArray);
		}
		
		return result;
	}
	
	/**
	 * 仿照oracle的decode方法
	 * @param obj
	 * @param defaultValue
	 * @param expect1Obj
	 * @param expect1Value
	 * @param objArrray
	 * @return
	 */
	public final static Object decode(Object obj, Object defaultValue, Object expect1Obj, Object expect1Value, Object... objArrray) {
		Object result = defaultValue;
		
		if (BaseUtil.equalsWithNull(obj, expect1Obj)) {
			result = expect1Value;
		} else {
			for (int i = 0; i < objArrray.length; i++) {
				if (BaseUtil.equalsWithNull(obj, objArrray[i++])) {
					if (i < objArrray.length) {
						result = objArrray[i];
					}
					break;
				}
			}
		}
		
		return result;
	}
}
