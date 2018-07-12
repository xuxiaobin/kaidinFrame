/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 转换工具
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class BeanConvert<T> {
	/** 只针对返回列表有效，根据属性值舍弃一些对象 */
	private BeanConvertFilter<T> filter;

	public BeanConvert() {
	}

	public BeanConvert(BeanConvertFilter<T> filter) {
		this.filter = filter;
	}

	/**
	 * 在destObj对象中根据属性properties填充srcValues
	 * @param destObj
	 * @param propertyArray
	 * @param valueArray
	 * @return
	 * @throws Exception
	 */
	public T convert(T destObj, String[] propertyArray, Object[] valueArray) throws Exception {
		for (int i = 0; i < propertyArray.length; i++) {
			if (null == valueArray[i]) {
				continue;
			}
			if (valueArray[i] instanceof Timestamp) {
				valueArray[i] = new Date(((Timestamp) valueArray[i]).getTime());
			}
			//			 String methodName = "set" + StringUtil.toUpperCaseAtFirst(properties[i]);
			//			 Method setMethod = destObj.getClass().getMethod(methodName, new Class[] { srcValues[i].getClass() });
			//			 Object result = setMethod.invoke(destObj, new Object[] {srcValues[i] });
			PropertyUtils.setProperty(destObj, propertyArray[i], valueArray[i]);
		}

		return destObj;
	}

	/**
	 * 在destObj对象中根据属性properties填充srcValues
	 * @param clazz
	 * @param propertyArray
	 * @param valueList
	 * @return
	 * @throws Exception
	 */
	public List<T> convert(Class<T> clazz, String[] propertyArray, List<Object[]> valueList) throws Exception {
		List<T> result = new ArrayList<>(valueList.size());

		for (Object[] valueArray : valueList) {
			T obj = clazz.newInstance();
			convert(obj, propertyArray, valueArray);
			if (null != filter && filter.doFilter(obj)) {
				continue;
			}
			result.add(obj);
		}

		return result;
	}
}
