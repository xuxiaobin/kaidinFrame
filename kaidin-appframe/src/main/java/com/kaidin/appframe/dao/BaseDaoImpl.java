package com.kaidin.appframe.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaidin.appframe.entity.BaseEntity;
import com.kaidin.appframe.exception.AppframeException;

/**
 * 操作数据库的实现
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public abstract class BaseDaoImpl<T extends BaseEntity> implements IBaseDao<T> {
	private static final transient Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	private static final int MAX_RESULTS = 10000;	// 最大的数据条数限制
	private final Class<T> persistentClass;	// 实体类
	private final String entityClassName;	// 实体类名称，方便打印日志使用

	// @PersistenceUnit
	// private EntityManagerFactory emf;

	// @PersistenceContext(type = PersistenceContextType.TRANSACTION)
	private final EntityManager entityManager;

	
//	// @PostConstruct
//	protected void initialize() {
//		if (null != entityManager) {
//			entityManager = null;
//		}
//		if (ServiceFactory.isEjb()) {
//			entityManager = emf.createEntityManager();
//			entityManager.setFlushMode(FlushModeType.AUTO);
//			entityManager.joinTransaction();
//		}
//	}
	public BaseDaoImpl(Class<T> clazz, IDaoContext aDaoContext) throws Exception {
		persistentClass = clazz;
		entityClassName = clazz.getName();
		// if (!ServiceFactory.isEjb()) {
		// if (null != entityManager) {
		// 	entityManager = null;
		// }
		entityManager = aDaoContext.getEntityManager();
		// }
	}
	public BaseDaoImpl(Class<T> clazz, IDaoContext aDaoContext, String jndiName) throws Exception {
		persistentClass = clazz;
		entityClassName = clazz.getName();
		// if (!ServiceFactory.isEjb()) {
		// if (null != entityManager) {
		// entityManager = null;
		// }
		entityManager = aDaoContext.getEntityManager(jndiName);
		// }
	}


	public EntityManager getEntityManager() {
		return entityManager;
	}

	
	private String getParamStr(String[] names, Object[] values) {
		StringBuffer result = new StringBuffer();
		
		if (null != names && 0 < names.length) {
			result.append(names[0]).append(":").append(values[0]);
			for (int i = 1; i < names.length; i++) {
				result.append(",").append(names[i]).append(":").append(values[i]);
			}
		}
		
		return result.toString();
	}
	
	// ================ add =======================
	public T save(T entity) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " saving entity, {}.", entity);
		}
		T result = entity;
		
		try {
			entityManager.persist(entity);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " saveing successful, {}.", entity);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " save failed, {}.", entity);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	
	// ================ delete =======================
	@Override
	public void delete(T entity) throws AppframeException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entity, {}.", entity);
			}
			entityManager.remove(entity);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entity successful, {}.", entity);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " delete failed, {}.", entity);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
	}
	
	@Override
	public void deleteById(long id) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " deleting entity, id:[{}].", id);
		}
		try {
			T entity = queryById(id);
			if (null != entity) {
				// 先查询出来再删除
				entityManager.remove(entity);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entity successful, id:[{}].", id);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " delete failed, id:[{}].", id);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
	}
	
	@Override
	public void deleteEntities(String hqlWhere, String[] names, Object[] values) throws AppframeException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entities, hqlWhere:[{}].", hqlWhere);
			}
			final String queryString = "delete from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			if (null != names) {
				for (int i = 0; i < names.length; i++) {
					query.setParameter(names[i], values[i]);
				}
			}
			query.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entity successful, hqlWhere:[{}].", hqlWhere);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " delete failed, hqlWhere:[{}; param:{}].", hqlWhere, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
	}
	
	@Override
	public void deleteByFullHql(String hql) throws AppframeException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entities, hql:[{}].", hql);
			}
			Query query = entityManager.createQuery(hql);
			query.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entity successful, hql:[{}].", hql);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " delete failed, hql:[{}].", hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
	}
	
	
	// ================ update =======================
	@Override
	public void update(T entity) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " updating entity, {}.", entity);
		}
		try {
			if (0 < entity.getId()) {
				//默认是大于0的，小于等于0的认为是特殊的
				entityManager.merge(entity);
			} else {
				save(entity);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " update successful, {}.", entity);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, {}.", entity);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
	}
	
	@Override
	public int updateByFullHql(String hql, String[] names, Object[] values) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " updateing entities, hql:[{}].", hql);
		}
		int result = 0;
		
		try {
			Query query = entityManager.createQuery(hql);
			if (null != names) {
				for (int i = 0; i < names.length; i++) {
					query.setParameter(names[i], values[i]);
				}
			}
			result = query.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " updateing entities successful, resultSize:[{}]." + result);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, hql:[{}; param:{}].", hql, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	public int updateNativeSql(String sql, String[] names, Object[] values) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " updating entities, sql:[{}].", sql);
		}
		int result = 0;
		
		try {
			Query query = entityManager.createNativeQuery(sql);
			if (null != names) {
				for (int i = 0; i < names.length; i++) {
					query.setParameter(names[i], values[i]);
				}
			}
			result = query.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " updating entities successful, resultSize:[{}]." + result);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, sql:[{}; param:{}].", sql, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	public void updateNativeSqls(String sql, String[] names, List<Object[]> valuesList) throws AppframeException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " banth update, sql:[{}].", sql);
			}
			Session session = (Session) entityManager.getDelegate();
			FlushMode mode = session.getFlushMode();
			session.setFlushMode(FlushMode.MANUAL);
			Query query = entityManager.createNativeQuery(sql);

			if (null != names) {
				for (Object[] values: valuesList) {
					for (int i = 0; i < names.length; i++) {
						query.setParameter(names[i], values[i]);
					}
					query.executeUpdate();
				}
			}
			session.setFlushMode(mode);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " banth update successful, update size:[{}]." + valuesList.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, sql:[{}; param:{}].", sql, getParamStr(names, valuesList.get(0)));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
	}
	
	
	// ================ query =======================
	@Override
	public T getReference(Object pk) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " querying entity reference with pk:[{}].", pk);
		}
		T result = null;
		
		try {
			result = entityManager.getReference(persistentClass, pk);
			if (logger.isDebugEnabled()) {
				if (null != result) {
					logger.debug(entityClassName + " query entity reference successful.");
				} else {
					logger.debug(entityClassName + " can't query entity reference.");
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, pk:[{}].", pk);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	public T queryById(long id) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " querying entity with id:[{}].", id);
		}
		T result = null;
		
		try {
			result = entityManager.find(persistentClass, id);
			if (logger.isDebugEnabled()) {
				if (null != result) {
					logger.debug(entityClassName + " query entity successful.");
				} else {
					logger.debug(entityClassName + " can't query entity.");
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, pk:[{}].", id);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T queryEntity(String hqlWhere) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
		}
		T result = null;
		
		try {
			final String queryString = " from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			query.setMaxResults(MAX_RESULTS);
			List<T> list = query.getResultList();
			if (list.isEmpty()) {
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " entity not found, hqlWhere:[{}]", hqlWhere);
				}
			} else {
				result = (T) query.getSingleResult();
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " query entity successful, hqlWhere:[{}]", hqlWhere);
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hqlWhere:[{}]", hqlWhere);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T queryEntity(String hqlWhere, String[] names, Object[] values) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " querying entity, hqlWhere:[{}]", hqlWhere);
		}
		T result = null;
		
		try {
			final String queryString = " from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			query.setMaxResults(MAX_RESULTS);
			if (null != names) {
				for (int i = 0; i < names.length; i++) {
					query.setParameter(names[i], values[i]);
				}
			}
			List<T> list = query.getResultList();
			if (list.isEmpty()) {
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " entity not found, hqlWhere:[{}]", hqlWhere);
				}
			} else {
				result = list.get(0);
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " query entity successful, hqlWhere:[{}]", hqlWhere);
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, hqlWhere:[{}; param:{}].", hqlWhere, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(String hqlWhere) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " querying all entities, hqlWhere:[{}]", hqlWhere);
		}
		List<T> result = null;
		
		try {
			final String queryString = " from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			query.setMaxResults(MAX_RESULTS);
			result = query.getResultList();
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, query result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, hqlWhere:[{}]", hqlWhere);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(String hqlWhere, int rowIndex, int rowNum) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
		}
		List<T> result = null;
		
		try {
			final String queryString = " from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);

			query.setFirstResult(0 < --rowIndex ? rowIndex: 0);	// 数据库从0开始计数，应用从1开始计数
			query.setMaxResults(MAX_RESULTS < rowNum ? MAX_RESULTS: rowNum);

			result = query.getResultList();
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, query result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, hqlWhere:[{}]", hqlWhere);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(String hqlWhere, String[] names, Object[] values) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
		}
		List<T> result = null;
		
		try {
			final String queryString = " from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			query.setMaxResults(MAX_RESULTS);
			if (null != names) {
				for (int i = 0; i < names.length; i++) {
					query.setParameter(names[i], values[i]);
				}
			}
			result = query.getResultList();
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, query result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, hqlWhere:[{}; param:{}].", hqlWhere, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(String hqlWhere, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
		}
		List<T> result = null;
		
		try {
			final String queryString = " from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);

			query.setFirstResult(0 < --rowIndex ? rowIndex: 0);	// 数据库从0开始计数，应用从1开始计数
			query.setMaxResults(MAX_RESULTS < rowNum ? MAX_RESULTS: rowNum);
			
			if (null != names) {
				for (int i = 0; i < names.length; i++) {
					query.setParameter(names[i], values[i]);
				}
			}

			result = query.getResultList();
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, query result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, hqlWhere:[{}; param:{}].", hqlWhere, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryAll() throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " querying all entities");
		}
		List<T> result = null;
		
		try {
			final String queryString = "from " + entityClassName;
			Query query = entityManager.createQuery(queryString);
			query.setMaxResults(MAX_RESULTS);
			result = query.getResultList();
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying all entities successful, query result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query all failed.");
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}

	/**
	 * 因为对hql无约束， 可能返回结果的类型不是安全的
	 */
	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List queryByFullHql(String hql) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
		}
		List<T> result = null;
		
		try {
			Query query = entityManager.createQuery(hql);
			/*
			 * createQuery("select c as c, c.name as name from Customer c")
			 * .setResultTransformer(Transformers.aliasToBean(YourClass.class));
			 */
			query.setMaxResults(MAX_RESULTS);
			result = query.getResultList();
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, query result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, hql:[{}]", hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	/**
	 * 因为对hql无约束， 可能返回结果的类型不是安全的
	 */
	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List queryByFullHql(String hql, int rowIndex, int rowNum) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
		}
		List<T> result = null;
		
		try {
			Query query = entityManager.createQuery(hql);
			query.setFirstResult(0 < --rowIndex ? rowIndex: 0);	// 数据库从0开始计数，应用从1开始计数
			query.setMaxResults(MAX_RESULTS < rowNum ? MAX_RESULTS: rowNum);

			result = query.getResultList();
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, query result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, hql:[{}]", hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}

	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List queryByFullHql(String hql, String[] names, Object[] values) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
		}
		List<T> result = null;
		
		try {
			Query query = entityManager.createQuery(hql);
			query.setMaxResults(MAX_RESULTS);
			if (null != names) {
				for (int i = 0; i < names.length; i++) {
					query.setParameter(names[i], values[i]);
				}
			}
			result = query.getResultList();
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, query result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, hql:[{}; param:{}].", hql, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	/**
	 * 因为对hql无约束， 可能返回结果的类型不是安全的
	 */
	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List queryByFullHql(String hql, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
		}
		List<T> result = null;
		
		try {
			Query query = entityManager.createQuery(hql);
			query.setFirstResult(0 < --rowIndex ? rowIndex: 0);	// 数据库从0开始计数，应用从1开始计数
			query.setMaxResults(MAX_RESULTS < rowNum ? MAX_RESULTS: rowNum);
			
			if (null != names) {
				for (int i = 0; i < names.length; i++) {
					query.setParameter(names[i], values[i]);
				}
			}
			result = query.getResultList();
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, query result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, hql:[{}; param:{}].", hql, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List queryNativeSql(String sql, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " querying entities, sql:[{}]", sql);
		}
		List result = null;
		
		try {
			Query query = entityManager.createNativeQuery(sql);
			query.setFirstResult(0 < --rowIndex ? rowIndex: 0);	// 数据库从0开始计数，应用从1开始计数
			query.setMaxResults(MAX_RESULTS < rowNum ? MAX_RESULTS: rowNum);
			
			if (null != names) {
				for (int i = 0; i < names.length; i++) {
					query.setParameter(names[i], values[i]);
				}
			}

			result = query.getResultList();
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, query result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, sql:[{}; param:{}].", sql, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List queryByFullHqlNoLimit(String hql, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
		}
		List<T> result = null;
		
		try {
			Query query = entityManager.createQuery(hql);
			query.setFirstResult(0 < --rowIndex ? rowIndex: 0);	// 数据库从0开始计数，应用从1开始计数
//			query.setMaxResults(MAX_RESULTS < rowNum ? MAX_RESULTS: rowNum);
			query.setMaxResults(rowNum);
			if (null != names) {
				for (int i = 0; i < names.length; i++) {
					query.setParameter(names[i], values[i]);
				}
			}
			result = query.getResultList();
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, query result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, hql:[{}; param:{}].", hql, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	
	// ================ other =======================
	@Override
	public void flush() throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " flushing.");
		}
		try {
			entityManager.flush();
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " flushing successful.");
			}
		} catch (Exception e) {
			logger.error(entityClassName + " flush failed.");
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
	}

	@Override
	public void clear() throws AppframeException {
		if (logger.isDebugEnabled()) {
			logger.debug(entityClassName + " clearing.");
		}
		try {
			entityManager.clear();
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " clearing successful.");
			}
		} catch (Exception e) {
			logger.error(entityClassName + " clear failed.");
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
	}
	
	@Override
	public void merge(T entity) throws AppframeException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " merge entity, {}.", entity);
			}
			entityManager.merge(entity);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " merge entity successful, {}.", entity);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " merge failed, {}.", entity);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
	}
}
