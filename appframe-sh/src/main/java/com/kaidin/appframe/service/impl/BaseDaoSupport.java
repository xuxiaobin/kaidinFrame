package com.kaidin.appframe.service.impl;

import java.util.Map;

import org.hibernate.Query;

import com.kaidin.appframe.config.AppframeConfig;

/**
 * 操作数据库类的父类
 * 因为子类复杂，实现方法很多，所以提取公共部分
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class BaseDaoSupport {
	public final static int MAX_QUERY_LIMIT;	// 最大的数据条数限制
//	public final Class<T> entityClass;	// 实体类
//	public final String entityClassName;	// 实体类名称，方便打印日志使用
	
	
	static {
		MAX_QUERY_LIMIT = AppframeConfig.getMaxQueryLimit();
	}
//	
//	protected BaseDaoSupport(Class<T> clazz) {
//		entityClass = clazz;
//		entityClassName = clazz.getName();
//	}
	
	// ================ util =======================
	public static String getParamStr(Map<String, Object> parameter) {
		StringBuilder result = new StringBuilder();
		
		if (null != parameter && !parameter.isEmpty()) {
			for (String name: parameter.keySet()) {
				result.append(name).append(':').append(parameter.get(name)).append(',');
			}
			result.deleteCharAt(result.length() - 1);
		}
		
		return result.toString();
	}
	public static String getParamStr(String[] names, Object[] values) {
		StringBuilder result = new StringBuilder();
		
		if (null != names && 0 < names.length) {
			result.append(names[0]).append(":").append(values[0]);
			for (int i = 1; i < names.length; i++) {
				result.append(",").append(names[i]).append(":").append(values[i]);
			}
		}
		
		return result.toString();
	}
	
	
	public static void setNamedValue(Query query, String[] names, Object[] values) {
		if (null != names) {
			for (int i = 0; i < names.length; i++) {
				query.setParameter(names[i], values[i]);
			}
		}
	}
	
	public static void setRowsLimit(Query query, int rowIndex, int rowNum) {
		query.setFirstResult(1 < rowIndex ? rowIndex - 1: 0);	// 数据库从0开始计数，应用从1开始计数
		query.setMaxResults(MAX_QUERY_LIMIT < rowNum ? MAX_QUERY_LIMIT: rowNum);
	}
}
