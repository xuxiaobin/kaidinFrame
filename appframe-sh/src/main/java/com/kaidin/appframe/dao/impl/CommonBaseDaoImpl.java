package com.kaidin.appframe.dao.impl;

/**
 * 操作数据库类的父类
 * 因为子类复杂，实现方法很多，所以提取公共部分
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class CommonBaseDaoImpl<T> {
	protected static final int MAX_QUERY_LIMIT = 10000;	// 最大的数据条数限制
	protected final Class<T> entityClass;	// 实体类
	protected final String entityClassName;	// 实体类名称，方便打印日志使用
	
	
	protected CommonBaseDaoImpl(Class<T> clazz) {
		entityClass = clazz;
		entityClassName = clazz.getName();
	}
	
	// ================ util =======================
	protected String getParamStr(String[] names, Object[] values) {
		StringBuilder result = new StringBuilder();
		
		if (null != names && 0 < names.length) {
			result.append(names[0]).append(":").append(values[0]);
			for (int i = 1; i < names.length; i++) {
				result.append(",").append(names[i]).append(":").append(values[i]);
			}
		}
		
		return result.toString();
	}
}
