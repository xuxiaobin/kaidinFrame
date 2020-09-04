package com.kaidin.appframe.service.dao;

import java.util.List;
import java.util.Map;

import com.kaidin.appframe.exception.AppframeException;
import com.kaidin.common.util.query.PageData;
import com.kaidin.common.util.query.PageRequest;

/**
 * 所有数据库操作的封装
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public interface IBaseHibernateDao<T> extends IBaseDao<T> {
	// ================ add =======================

	// ================ delete =======================
	int deleteEntities(String hqlWhere, Map<String, Object> parameter) throws AppframeException;

	int deleteEntities(String hqlWhere, String[] names, Object[] values) throws AppframeException;

	int deleteByFullHql(String hql, Map<String, Object> parameter) throws AppframeException;

	int deleteByFullHql(String hql, String[] names, Object[] values) throws AppframeException;

	// =================== update ==========================
	int updateByFullHql(String hql, Map<String, Object> parameter) throws AppframeException;

	int updateByFullHql(String hql, String[] names, Object[] values) throws AppframeException;

	int updateNativeSql(String sql, Map<String, Object> parameter) throws AppframeException;

	int updateNativeSql(String sql, String[] names, Object[] values) throws AppframeException;

	List<Integer> updateNativeSqls(String sql, String[] names, List<Object[]> valuesList) throws AppframeException;

	// ================= query count =======================
	int countEntityies(String hqlWhere, Map<String, Object> parameter) throws AppframeException;

	int countEntityies(String hqlWhere, String[] names, Object[] values) throws AppframeException;

	int countByFullHql(String hql, Map<String, Object> parameter) throws AppframeException;

	int countByFullHql(String hql, String[] names, Object[] values) throws AppframeException;

	int countNativeSql(String sql, Map<String, Object> parameter) throws AppframeException;

	int countNativeSql(String sql, String[] names, Object[] values) throws AppframeException;

	// ================ query =======================
	T getReference(long id) throws AppframeException;

	T queryEntity(String hqlWhere) throws AppframeException;

	T queryEntity(String hqlWhere, Map<String, Object> parameter) throws AppframeException;

	T queryEntity(String hqlWhere, String[] names, Object[] values) throws AppframeException;

	List<T> queryEntities() throws AppframeException;

	List<T> queryEntities(String hqlWhere) throws AppframeException;

	List<T> queryEntities(String hqlWhere, int rowIndex, int rowNum) throws AppframeException;

	List<T> queryEntities(String hqlWhere, Map<String, Object> parameter) throws AppframeException;

	List<T> queryEntities(String hqlWhere, String[] names, Object[] values) throws AppframeException;

	List<T> queryEntities(String hqlWhere, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException;

	List<T> queryEntities(String hqlWhere, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException;

	PageData<T> queryEntities(PageRequest pageLoadCfg) throws AppframeException;

	PageData<T> queryEntities(String hqlWhere, PageRequest pageLoadCfg) throws AppframeException;

	PageData<T> queryEntities(String hqlWhere, Map<String, Object> parameter, PageRequest pageLoadCfg) throws AppframeException;

	PageData<T> queryEntities(String hqlWhere, String[] names, Object[] values, PageRequest pageLoadCfg) throws AppframeException;

	List queryByFullHql(String hql) throws AppframeException;

	List queryByFullHql(String hql, int rowIndex, int rowNum) throws AppframeException;

	List queryByFullHql(String hql, Map<String, Object> parameter) throws AppframeException;

	List queryByFullHql(String hql, String[] names, Object[] values) throws AppframeException;

	List queryByFullHql(String hql, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException;

	List queryByFullHql(String hql, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException;

	List queryNativeSql(String sql, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException;

	List queryNativeSql(String sql, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException;

	List queryByFullHqlNoLimit(String hql, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException;

	List queryByFullHqlNoLimit(String hql, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException;

	PageData queryByFullHql(String hql, PageRequest pageLoadCfg) throws AppframeException;

	PageData queryByFullHql(String hql, Map<String, Object> parameter, PageRequest pageLoadCfg) throws AppframeException;

	PageData queryByFullHql(String hql, String[] names, Object[] values, PageRequest pageLoadCfg) throws AppframeException;

	PageData queryNativeSql(String sql, Map<String, Object> parameter, PageRequest pageReq) throws AppframeException;

	PageData queryNativeSql(String sql, String[] names, Object[] values, PageRequest pageReq) throws AppframeException;

	// ================ other =======================
}
