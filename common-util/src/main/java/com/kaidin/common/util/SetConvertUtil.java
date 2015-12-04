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
}
