/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

import com.kaidin.common.constant.ConstType;

/**
 * 读取properties文件工具
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public abstract class PropertyUtil {

	/**
	 * 读取Properties文件的键值对，组成Entry集合返回
	 * 
	 * @param propertyFileName
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> readPropertyFile(String propertyFileName) throws IOException {
		Map<String, String> result = null;

		InputStream inputStream = null;
		try {
			try {
				inputStream = new FileInputStream(propertyFileName);
			} catch (FileNotFoundException e) {
				inputStream = PropertyUtil.class.getClassLoader().getResourceAsStream(propertyFileName);
			}
			result = readPropertyFile(inputStream);
		} catch (IOException e) {
			throw e;
		} finally {
			if (null != inputStream) {
				inputStream.close();
			}
		}

		return result;
	}

	/**
	 * 读取Properties文件的键值对，组成Entry集合返回，inputStream需要自己关闭
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> readPropertyFile(InputStream inputStream) throws IOException {
		Map<String, String> result = null;

		try {
			Properties proper = new Properties();
			proper.load(inputStream);
			Set<Entry<Object, Object>> entrySet = proper.entrySet();
			if (CollectionUtil.isEmpty(entrySet)) {
				return null;
			}
			result = new HashMap<>(entrySet.size());
			for (Entry<Object, Object> entry : entrySet) {
				String key = String.valueOf(entry.getKey());
				if (StringUtil.startsWith(key, "ï»¿")) {
					// 首行读取异常（文件编码的问题）
					continue;
				}
				result.put(key, String.valueOf(entry.getValue()));
			}
			if (0 == result.size()) {
				return null;
			}
		} catch (Exception e) {
			throw e;
		}

		return result;
	}

	/**
	 * 读取属性文件中指定的key
	 * @param propertyFileName
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String readPropertyFile(String propertyFileName, String key) throws Exception {
		Map<String, String> propertyMap = readPropertyFile(propertyFileName);
		if (null != propertyMap) {
			return propertyMap.get(key);
		}

		return null;
	}

	/**
	 * 在对象configBean设置指定的值
	 * @param targetObj
	 * @param propertyFileName
	 * @throws Exception
	 */
	public static void putProperty(Object targetObj, String propertyFileName) throws Exception {
		Map<String, String> propertyMap = readPropertyFile(propertyFileName);

		if (CollectionUtil.isEmpty(propertyMap)) {
			return;
		}
		for (Entry<String, String> entry : propertyMap.entrySet()) {
			setSimpleProperty(targetObj, entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 设置简单的值
	 * @param bean
	 * @param name
	 * @param value
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static void setSimpleProperty(Object bean, String name, String value) throws IllegalAccessException,
	        InvocationTargetException, NoSuchMethodException {
		Class<?> clazz = PropertyUtils.getPropertyType(bean, name);
		if (null == clazz) {
			return;
		}
		String fieldTypeName = clazz.getName();
		if (fieldTypeName.equals(Integer.class.getName()) || ConstType.baseType.P_int.equals(fieldTypeName)) {
			PropertyUtils.setSimpleProperty(bean, name, Integer.valueOf(value));
		} else if (fieldTypeName.equals(Long.class.getName()) || ConstType.baseType.P_long.equals(fieldTypeName)) {
			PropertyUtils.setSimpleProperty(bean, name, Long.valueOf(value));
		} else if (fieldTypeName.equals(Float.class.getName()) || ConstType.baseType.P_float.equals(fieldTypeName)) {
			PropertyUtils.setSimpleProperty(bean, name, Float.valueOf(value));
		} else if (fieldTypeName.equals(Double.class.getName()) || ConstType.baseType.P_double.equals(fieldTypeName)) {
			PropertyUtils.setSimpleProperty(bean, name, Double.valueOf(value));
		} else {
			PropertyUtils.setSimpleProperty(bean, name, value);
		}
	}
}
