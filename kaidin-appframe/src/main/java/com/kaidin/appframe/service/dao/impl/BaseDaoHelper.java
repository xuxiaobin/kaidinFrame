package com.kaidin.appframe.service.dao.impl;

import com.kaidin.appframe.config.AppframeConfig;
import com.kaidin.common.util.collection.CollectionUtil;
import com.kaidin.common.util.query.PageRequest;
import org.hibernate.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * 操作数据库类的父类
 * 因为子类复杂，实现方法很多，所以提取公共部分
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class BaseDaoHelper {
	public final static int MAX_QUERY_LIMIT;	// 最大的数据条数限制

	static {
		MAX_QUERY_LIMIT = AppframeConfig.getMaxQueryLimit();
	}

	// =================== update =========================
	public static int update(Query query, Map<String, Object> parameter) {
		query = setNamedValue(query, parameter);
		query = setRowsLimit(query, null);
		return query.executeUpdate();
	}

	public static int update(javax.persistence.Query query, Map<String, Object> parameter) {
		query = setNamedValue(query, parameter);
		query = setRowsLimit(query, null);
		return query.executeUpdate();
	}

	public static int update(Query query, String[] names, final Object[] values) {
		query = setNamedValue(query, names, values);
		query = setRowsLimit(query, null);
		return query.executeUpdate();
	}

	public static int update(javax.persistence.Query query, String[] names, final Object[] values) {
		query = setNamedValue(query, names, values);
		query = setRowsLimit(query, null);
		return query.executeUpdate();
	}

	// =================== count =========================
	public static int count(Query query, Map<String, Object> parameter) {
		query = setNamedValue(query, parameter);
		List<BigInteger> list = query.list();
		return CollectionUtil.isEmpty(list) ? 0 : list.get(0).intValue();
	}

	public static int count(javax.persistence.Query query, Map<String, Object> parameter) {
		query = setNamedValue(query, parameter);
		List<BigInteger> list = query.getResultList();
		return CollectionUtil.isEmpty(list) ? 0 : list.get(0).intValue();
	}

	// =================== list =========================
	@SuppressWarnings("rawtypes")
	public static List list(Query query, Map<String, Object> parameter, PageRequest pageLoadCfg) {
		query = setNamedValue(query, parameter);
		query = setRowsLimit(query, pageLoadCfg);
		return query.list();
	}

	@SuppressWarnings("rawtypes")
	public static List list(javax.persistence.Query query, Map<String, Object> parameter, PageRequest pageLoadCfg) {
		query = setNamedValue(query, parameter);
		query = setRowsLimit(query, pageLoadCfg);
		
		return query.getResultList();
	}
	@SuppressWarnings("rawtypes")
	public static List list(Query query, Map<String, Object> parameter, int rowIndex, int rowNum) {
		query = setNamedValue(query, parameter);
		query = setRowsLimit(query, rowIndex, rowNum);
		
		return query.list();
	}
	@SuppressWarnings("rawtypes")
	public static List list(javax.persistence.Query query, Map<String, Object> parameter, int rowIndex, int rowNum) {
		query = setNamedValue(query, parameter);
		query = setRowsLimit(query, rowIndex, rowNum);
		
		return query.getResultList();
	}
	
	// =================== setValue =========================
	/**
	 * 命名查询参数设置
	 * @param query
	 * @param parameter
	 */
	public static Query setNamedValue(Query query, Map<String, Object> parameter) {
		if (CollectionUtil.isNotEmpty(parameter)) {
			for (String name: parameter.keySet()) {
				query.setParameter(name, parameter.get(name));
			}
		}
		
		return query;
	}
	/**
	 * 命名查询参数设置
	 * @param query
	 * @param parameter
	 */
	public static javax.persistence.Query setNamedValue(javax.persistence.Query query, Map<String, Object> parameter) {
		if (CollectionUtil.isNotEmpty(parameter)) {
			for (String name: parameter.keySet()) {
				query.setParameter(name, parameter.get(name));
			}
		}
		
		return query;
	}
	/**
	 * 命名查询参数设置
	 * @param query
	 * @param names
	 * @param values
	 */
	public static Query setNamedValue(Query query, String[] names, Object[] values) {
		if (null != names) {
			for (int i = 0; i < names.length; i++) {
				query.setParameter(names[i], values[i]);
			}
		}
		
		return query;
	}
	/**
	 * 命名查询参数设置
	 * @param query
	 * @param names
	 * @param values
	 */
	public static javax.persistence.Query setNamedValue(javax.persistence.Query query, String[] names, Object[] values) {
		if (null != names) {
			for (int i = 0; i < names.length; i++) {
				query.setParameter(names[i], values[i]);
			}
		}
		
		return query;
	}

	/**
	 * 设置返回的记录条数限制，避免一次查询不必要的大量数据，提高查询性能
	 * @param query
	 * @param rowIndex
	 * @param rowNum
	 */
	public static Query setRowsLimit(Query query, int rowIndex, int rowNum) {
		// 数据库从0开始计数，应用从1开始计数
		query.setFirstResult(Math.max(rowIndex - 1, 0));
		return query.setMaxResults(Math.min(rowNum, MAX_QUERY_LIMIT));
	}
	/**
	 * 设置返回的记录条数限制，避免一次查询不必要的大量数据，提高查询性能
	 * @param query
	 * @param rowIndex
	 * @param rowNum
	 */
	public static javax.persistence.Query setRowsLimit(javax.persistence.Query query, int rowIndex, int rowNum) {
		// 数据库从0开始计数，应用从1开始计数
		query.setFirstResult(Math.max(rowIndex - 1, 0));
		return query.setMaxResults(Math.min(rowNum, MAX_QUERY_LIMIT));
	}
	/**
	 * 设置返回的记录条数限制，避免一次查询不必要的大量数据，提高查询性能
	 * @param query
	 * @param pageReq
	 */
	public static Query setRowsLimit(Query query, PageRequest pageReq) {
		query.setMaxResults(MAX_QUERY_LIMIT);
		if (null != pageReq) {
			// 数据库从0开始计数，应用从1开始计数
			query.setFirstResult(pageReq.getOffset() - 1);
			query.setMaxResults(Math.min(pageReq.getLimit(), MAX_QUERY_LIMIT));
		}

		return query;
	}

	/**
	 * 设置返回的记录条数限制，避免一次查询不必要的大量数据，提高查询性能
	 * @param query
	 * @param pageReq
	 */
	public static javax.persistence.Query setRowsLimit(javax.persistence.Query query, PageRequest pageReq) {
		query.setMaxResults(MAX_QUERY_LIMIT);
		if (null != pageReq) {
			// 数据库从0开始计数，应用从1开始计数
			query.setFirstResult(pageReq.getOffset() - 1);
			query.setMaxResults(Math.min(pageReq.getLimit(), MAX_QUERY_LIMIT));
		}

		return query;
	}
	
	// =================== logUtil =========================
	/**
	 * 将参数组成一个字符串，方便打印	
	 * @param parameter
	 * @return
	 */
	public static String getParamStr(Map<String, Object> parameter) {
		StringBuilder result = new StringBuilder();
		
		if (CollectionUtil.isNotEmpty(parameter)) {
			for (String name: parameter.keySet()) {
				result.append(name).append(':').append(parameter.get(name)).append(',');
			}
			result.deleteCharAt(result.length() - 1);
		}
		
		return result.toString();
	}
	/**
	 * 将参数组成一个字符串，方便打印	
	 * @param names
	 * @param values
	 * @return
	 */
	public static String getParamStr(String[] names, Object[] values) {
		StringBuilder result = new StringBuilder();
		
		if (CollectionUtil.isNotEmpty(names)) {
			result.append(names[0]).append(":").append(values[0]);
			for (int i = 1; i < names.length; i++) {
				result.append(",").append(names[i]).append(":").append(values[i]);
			}
		}
		
		return result.toString();
	}
}
