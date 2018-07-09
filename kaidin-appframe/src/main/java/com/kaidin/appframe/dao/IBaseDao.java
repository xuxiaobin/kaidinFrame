package com.kaidin.appframe.dao;

import java.util.List;

import com.kaidin.appframe.exception.AppframeException;
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
	public void deleteEntities(String hql, String[] names, Object[] values) throws AppframeException;
	public void deleteByFullHql(String hql) throws AppframeException;
	
	// ================ update =======================
	public void update(T entity) throws AppframeException;
	public int updateByFullHql(String hql, String[] names, Object[] values) throws AppframeException;
	public int updateNativeSql(String sql, String[] names, Object[] values) throws AppframeException;
	public void updateNativeSqls(String sql, String[] names, List<Object[]> valuesList) throws AppframeException;
	
	// ================ query =======================
	public T getReference(Object o) throws AppframeException;
	public T queryById(long id) throws AppframeException;
	
	public T queryEntity(String hqlWhere) throws AppframeException;
	public T queryEntity(String hqlWhere, String[] names, Object[] values) throws AppframeException;
	public List<T> queryEntities(String hqlWhere) throws AppframeException;
	public List<T> queryEntities(String hqlWhere, int rowIndex, int rowNum) throws AppframeException;
	public List<T> queryEntities(String hqlWhere, String[] names, Object[] values) throws AppframeException;
	public List<T> queryEntities(String hqlWhere, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException;
	public List<T> queryAll() throws AppframeException;

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
	
	// ================ other =======================
	public void clear() throws AppframeException;
	public void flush() throws AppframeException;
	public void merge(T entity) throws AppframeException;
}
