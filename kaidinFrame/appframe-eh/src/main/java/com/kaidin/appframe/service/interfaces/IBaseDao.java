package com.kaidin.appframe.service.interfaces;

import java.util.List;

import com.kaidin.appframe.exception.AppframeException;
import com.kaidin.common.util.query.DataContainer;
import com.kaidin.common.util.query.PageLoadConfig;
/**
 * 所有数据库操作的封装
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public interface IBaseDao<T> {
	// ================ add =======================
	public T save(T entity) throws AppframeException;
	
	
	// ================ delete =======================
	public void delete(T entity) throws AppframeException;
	public void deleteById(long id) throws AppframeException;
	public int deleteEntities(String hqlWhere, String[] names, Object[] values) throws AppframeException;
	public int deleteByFullHql(String hql) throws AppframeException;
	
	// ================ update =======================
	public void update(T entity) throws AppframeException;
	public int updateByFullHql(String hql, String[] names, Object[] values) throws AppframeException;
	public int updateNativeSql(String sql, String[] names, Object[] values) throws AppframeException;
	public List<Integer> updateNativeSqls(String sql, String[] names, List<Object[]> valuesList) throws AppframeException;
	
	// ================ query =======================
	public T getReference(long id) throws AppframeException;
	public T queryById(long id) throws AppframeException;
	
	public T queryEntity(String hqlWhere) throws AppframeException;
	public T queryEntity(String hqlWhere, String[] names, Object[] values) throws AppframeException;
	public List<T> queryEntities() throws AppframeException;
	public List<T> queryEntities(String hqlWhere) throws AppframeException;
	public List<T> queryEntities(String hqlWhere, int rowIndex, int rowNum) throws AppframeException;
	public List<T> queryEntities(String hqlWhere, String[] names, Object[] values) throws AppframeException;
	public List<T> queryEntities(String hqlWhere, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException;
	
	public DataContainer<T> queryEntities(PageLoadConfig pageLoadCfg) throws AppframeException;
	public DataContainer<T> queryEntities(String hqlWhere, PageLoadConfig pageLoadCfg) throws AppframeException;
	public DataContainer<T> queryEntities(String hqlWhere, String[] names, Object[] values, PageLoadConfig pageLoadCfg) throws AppframeException;

	public int countByFullHql(String hql, String[] names, Object[] values) throws AppframeException;
	public int countNativeSql(String sql, String[] names, Object[] values) throws AppframeException;
	
	@SuppressWarnings("rawtypes")
	public List queryByFullHql(String hql) throws AppframeException;
	@SuppressWarnings("rawtypes")
	public List queryByFullHql(String hql, int rowIndex, int rowNum) throws AppframeException;
	@SuppressWarnings("rawtypes")
	public List queryByFullHql(String hql, String[] names, Object[] values) throws AppframeException;
	@SuppressWarnings("rawtypes")
	public List queryByFullHql(String hql, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException;

	@SuppressWarnings("rawtypes")
	public List queryNativeSql(String sql, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException;
	@SuppressWarnings("rawtypes")
	public List queryByFullHqlNoLimit(String hql, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException;
	
	@SuppressWarnings("rawtypes")
	public DataContainer queryByFullHql(String hql, PageLoadConfig pageLoadCfg) throws AppframeException;
	@SuppressWarnings("rawtypes")
	public DataContainer queryByFullHql(String hql, String[] names, Object[] values, PageLoadConfig pageLoadCfg) throws AppframeException;
	@SuppressWarnings("rawtypes")
	public DataContainer queryNativeSql(String sql, String[] names, Object[] values, PageLoadConfig pageLoadCfg) throws AppframeException;
	// ================ other =======================
	public void clear() throws AppframeException;
	public void flush() throws AppframeException;
	public void merge(T entity) throws AppframeException;
}
