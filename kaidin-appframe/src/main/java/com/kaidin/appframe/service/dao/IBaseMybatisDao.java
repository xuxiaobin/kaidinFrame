package com.kaidin.appframe.service.dao;

import java.util.List;
import java.util.Map;

import com.kaidin.appframe.exception.AppframeException;
import com.kaidin.appframe.service.dao.IBaseDao;
import com.kaidin.common.util.query.PageData;
import com.kaidin.common.util.query.PageRequest;

/**
 * 所有数据库操作的封装的mybatis接口
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public interface IBaseMybatisDao<T> extends IBaseDao<T> {
	// ################## add ##################

	// ################## delete ##################
	int deleteEntities(String sqlWhere, Map<String, Object> parameter) throws AppframeException;

	// ################## update ##################

	// ================ query entity =======================
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
	PageData<T> queryEntities(PageRequest pageReq) throws AppframeException;

	PageData<T> queryEntities(String sqlWhere, PageRequest pageReq) throws AppframeException;

	PageData<T> queryEntities(String sqlWhere, Map<String, Object> parameter, PageRequest pageReq) throws AppframeException;

	PageData<Map<String, Object>> queryNativeSql(String sql, Map<String, Object> parameter, PageRequest pageReq) throws AppframeException;
}
