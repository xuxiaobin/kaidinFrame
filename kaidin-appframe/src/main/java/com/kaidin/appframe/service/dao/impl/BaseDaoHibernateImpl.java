package com.kaidin.appframe.service.dao.impl;

import com.kaidin.appframe.exception.AppframeException;
import com.kaidin.appframe.service.dao.BaseEntity;
import com.kaidin.appframe.service.dao.IBaseDao;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.util.Map;
/**
 * 操作数据库的hibernate实现
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class BaseDaoHibernateImpl<T extends BaseEntity> implements IBaseDao<T> {
	/* 实体类类型 */
	protected final Class<T> entityClass;
	protected final String fromTableWhere;
	@Resource
	protected SessionFactory sessionFactory;


	public BaseDaoHibernateImpl(Class<T> clazz) {
		entityClass = clazz;
		fromTableWhere = "from " + clazz.getName() + " where ";
	}

	// ================= add =======================
	@Override
	public T save(T entity) throws AppframeException {
		sessionFactory.getCurrentSession().save(entity);
		return entity;
	}

	// ================= delete =======================
	@Override
	public void delete(T entity) throws AppframeException {
		sessionFactory.getCurrentSession().delete(entity);
	}

	@Override
	public int deleteById(long id) throws AppframeException {
		String sql = "delete " + fromTableWhere + " id=:id";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		return BaseDaoHelper.update(query, new String[]{"id"}, new Object[]{id});
	}

	// =================== update ==========================
	@Override
	public void update(T entity) throws AppframeException {
		sessionFactory.getCurrentSession().update(entity);
	}

	@Override
	public int updateNativeSql(String sql, Map<String, Object> parameter) throws AppframeException {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		return BaseDaoHelper.update(query, parameter);
	}

	// ================= query count =======================
	@Override
	public int countNativeSql(String sql, Map<String, Object> parameter) throws AppframeException {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		return BaseDaoHelper.count(query, parameter);
	}

	// ================= query entity =======================
	@Override
	public T queryEntity(long id) throws AppframeException {
		return (T) sessionFactory.getCurrentSession().get(entityClass, id);
	}

	// ================= query nativeSql =======================

	// ================ other =======================
	@Override
	public void flush() throws AppframeException {
		sessionFactory.getCurrentSession().flush();
	}

	@Override
	public void clear() throws AppframeException {
		sessionFactory.getCurrentSession().clear();
	}

	@Override
	public void merge(T entity) throws AppframeException {
		sessionFactory.getCurrentSession().merge(entity);
	}
}
