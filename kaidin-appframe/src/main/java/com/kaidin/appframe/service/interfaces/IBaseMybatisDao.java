package com.kaidin.appframe.service.interfaces;

import java.util.List;
import java.util.Map;

import com.kaidin.appframe.exception.AppframeException;
import com.kaidin.common.util.query.PageData;
import com.kaidin.common.util.query.PageLoadConfig;

/**
 * 所有数据库操作的封装的mybatis接口
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public interface IBaseMybatisDao<T> extends IBaseDao<T> {
	// ################## add ##################
	T save(T entity) throws AppframeException;

	// ################## delete ##################
	int delete(T entity) throws AppframeException;

	int deleteEntities(String sqlWhere, Map<String, Object> parameter) throws AppframeException;

	// ################## update ##################
	int update(T entity) throws AppframeException;

	// ================ query entity =======================
	T queryEntity(long id) throws AppframeException;

	T queryEntity(String sqlWhere) throws AppframeException;

	T queryEntity(String sqlWhere, Map<String, Object> parameter) throws AppframeException;

	// ################## query List ##################
	List<T> queryEntities() throws AppframeException;

	List<T> queryEntities(String sqlWhere) throws AppframeException;

	List<T> queryEntities(String sqlWhere, int rowIndex, int rowNum) throws AppframeException;

	List<T> queryEntities(String sqlWhere, Map<String, Object> parameter) throws AppframeException;

	List<T> queryEntities(String sqlWhere, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException;

	List<Map<String, Object>> queryNativeSql(String sql, Map<String, Object> parameter) throws AppframeException;

	List<Map<String, Object>> queryNativeSql(String sql, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException;

	// ################## query PageData ##################
	PageData<T> queryEntities(PageLoadConfig pageLoadCfg) throws AppframeException;

	PageData<T> queryEntities(String sqlWhere, PageLoadConfig pageLoadCfg) throws AppframeException;

	PageData<T> queryEntities(String sqlWhere, Map<String, Object> parameter, PageLoadConfig pageLoadCfg) throws AppframeException;

	PageData<Map<String, Object>> queryNativeSql(String sql, Map<String, Object> parameter, PageLoadConfig pageLoadCfg) throws AppframeException;
}
