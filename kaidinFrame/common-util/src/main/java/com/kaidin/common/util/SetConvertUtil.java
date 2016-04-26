package com.kaidin.common.util;

import java.util.ArrayList;
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
	public static String[] listToArray(List<String> dataList) {
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
	public static ArrayList<String> arrayToList(String[] dataArray) {
		ArrayList<String> result = null;
		
		if (null != dataArray) {
			result = new ArrayList<String>(dataArray.length);
			for (String value: dataArray) {
				result.add(value);
			}
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
	public static Object decode(Object obj, Object defaultValue, Object expect1Obj, Object expect1Value, Object... objArrray) {
		Object result = defaultValue;
		
		if (null == obj) {
			if (null == expect1Obj) {
				result = expect1Value;
			} else {
				for (int i = 0; i < objArrray.length; i++) {
					if (null == objArrray[i++]) {
						if (i < objArrray.length) {
							result = objArrray[i];
						}
						break;
					}
				}
			}
		} else {
			if (obj.equals(expect1Obj)) {
				result = expect1Value;
			} else {
				for (int i = 0; i < objArrray.length; i++) {
					if (obj.equals(objArrray[i++])) {
						if (i < objArrray.length) {
							result = objArrray[i];
						}
						break;
					}
				}
			}
		}
		
		return result;
	}
}
