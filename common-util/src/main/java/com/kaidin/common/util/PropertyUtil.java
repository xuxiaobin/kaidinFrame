package com.kaidin.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

import com.kaidin.common.constant.ConstType;

/**
 * 读取properties文件工具
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class PropertyUtil {
	
	/**
	 * 读取Properties文件的键值对，组成Entry集合返回
	 * @param propertyFileName
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, String> readPropertyFile(String propertyFileName) throws Exception {
		HashMap<String, String> result = null;
		
		InputStream fileInputStream = null;
		try {
			try {
				fileInputStream = new FileInputStream(propertyFileName);
			} catch (FileNotFoundException e) {
				fileInputStream = PropertyUtil.class.getClassLoader().getResourceAsStream(propertyFileName);
			}
			result = readPropertyFile(fileInputStream);
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != fileInputStream) {
				fileInputStream.close();
			}
		}
		
		return result;
	}
	
	/**
	 * 读取Properties文件的键值对，组成Entry集合返回，inputStream需要自己关闭
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, String> readPropertyFile(InputStream inputStream) throws Exception {
		HashMap<String, String> result = null;
		
		try {
			Properties proper = new Properties();
			proper.load(inputStream);
			Set<Entry<Object, Object>> entrySet = proper.entrySet();
			if (null != entrySet && !entrySet.isEmpty()) {
				result = new HashMap<String, String>(entrySet.size());
				for (Entry<Object, Object> entry: entrySet) {
					String key = String.valueOf(entry.getKey());
					if (null != key && key.startsWith("ï»¿")) {
						//首行读取异常（文件编码的问题）
						continue;
					}
					result.put(key, String.valueOf(entry.getValue()));
				}
			}
			if (0 == result.size()) {
				result = null;
			}
		} catch (Exception e) {
			throw e;
		}
		
		return result;
	}
	
	public static String readPropertyFile(String propertyFileName, String key) throws Exception {
		String result = null;
		
		HashMap<String, String> propertyMap = readPropertyFile(propertyFileName);
		if (null != propertyMap) {
			result = propertyMap.get(key);
		}
		
		return result;
	}
	
	public static void putProperty(Object configBean, String propertyFileName) throws Exception {
		HashMap<String, String> propertyMap = readPropertyFile(propertyFileName);
		if (null != propertyMap) {
			for (Entry<String, String> entry: propertyMap.entrySet()) {
				setSimpleProperty(configBean, entry.getKey(), entry.getValue());
			}
		}
	}
	
	public static void setSimpleProperty(Object bean, String name, String value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		@SuppressWarnings("rawtypes")
		Class clazz = PropertyUtils.getPropertyType(bean, name);
		if (null == clazz) {
			return;
		}
		String fieldTypeName = clazz.getName();
		if (fieldTypeName.equals(Integer.class.getName()) || ConstType.BASE_TYPE.DATATYPE_int.equals(fieldTypeName)) {
			PropertyUtils.setSimpleProperty(bean, name, Integer.valueOf(value));
		} else if (fieldTypeName.equals(Long.class.getName()) || ConstType.BASE_TYPE.DATATYPE_long.equals(fieldTypeName)) {
			PropertyUtils.setSimpleProperty(bean, name, Long.valueOf(value));
		} else if (fieldTypeName.equals(Float.class.getName()) || ConstType.BASE_TYPE.DATATYPE_float.equals(fieldTypeName)) {
			PropertyUtils.setSimpleProperty(bean, name, Float.valueOf(value));
		} else if (fieldTypeName.equals(Double.class.getName()) || ConstType.BASE_TYPE.DATATYPE_double.equals(fieldTypeName)) {
			PropertyUtils.setSimpleProperty(bean, name, Double.valueOf(value));
		} else {
			PropertyUtils.setSimpleProperty(bean, name, value);
		}
	}
}
