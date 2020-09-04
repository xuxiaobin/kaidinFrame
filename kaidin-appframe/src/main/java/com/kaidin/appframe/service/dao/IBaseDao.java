package com.kaidin.appframe.service.dao;

import java.util.List;
import java.util.Map;

import com.kaidin.appframe.exception.AppframeException;

/**
 * 所有数据库操作的封装的基类接口
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public interface IBaseDao<T> {
	// ================= add =======================
	T save(T entity) throws AppframeException;

	// ================= delete =======================
	void delete(T entity) throws AppframeException;

	int deleteById(long id) throws AppframeException;

	// =================== update ==========================
	void update(T entity) throws AppframeException;

	int updateNativeSql(String sql, Map<String, Object> parameter) throws AppframeException;

	// ================= query count =======================
	int countNativeSql(String sql, Map<String, Object> parameter) throws AppframeException;

	// ================= query entity =======================
	T queryEntity(long id) throws AppframeException;

	// ================= query nativeSql =======================

	// ================ other =======================
	 void clear() throws AppframeException;
	 void flush() throws AppframeException;
	 void merge(T entity) throws AppframeException;
}
