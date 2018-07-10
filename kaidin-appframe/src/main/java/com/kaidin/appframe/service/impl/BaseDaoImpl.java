package com.kaidin.appframe.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaidin.appframe.entity.BaseEntity;
import com.kaidin.appframe.exception.AppframeException;
import com.kaidin.appframe.service.interfaces.IBaseDao;
import com.kaidin.appframe.service.interfaces.IDaoContext;
import com.kaidin.common.util.DataTypeUtil;
import com.kaidin.common.util.query.DataContainer;
import com.kaidin.common.util.query.PageLoadConfig;
/**
 * 操作数据库的ejb实现
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class BaseDaoImpl<T extends BaseEntity> implements IBaseDao<T> {
	private static final transient Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	private final Class<T> entityClass;	// 实体类
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
	public BaseDaoImpl(Class<T> clazz, IDaoContext aDaoContext) throws AppframeException {
		entityClass = clazz;
		entityClassName = clazz.getName();
		// if (!ServiceFactory.isEjb()) {
		// if (null != entityManager) {
		// 	entityManager = null;
		// }
		entityManager = aDaoContext.getEntityManager();
		// }
	}
	public BaseDaoImpl(Class<T> clazz, IDaoContext aDaoContext, String jndiName) throws AppframeException {
		entityClass = clazz;
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


	// ==================== add ==========================
	@Override
	public T save(T entity) throws AppframeException {
		T result = entity;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " saving entity, {}.", entity);
			}
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
	
	
	// =================== delete ==========================
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
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entity, id:[{}].", id);
			}
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
	public int deleteEntities(String hqlWhere, Map<String, Object> parameter) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entities, hqlWhere:[{}].", hqlWhere);
			}
			final String queryString = "delete from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			result = BaseDaoHelper.update(query, parameter);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entity successful, hqlWhere:[{}].", hqlWhere);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " delete failed, hqlWhere:[{}; param:{}].", hqlWhere, BaseDaoHelper.getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	public int deleteEntities(String hqlWhere, String[] names, Object[] values) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entities, hqlWhere:[{}].", hqlWhere);
			}
			final String queryString = "delete from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			result = BaseDaoHelper.update(query, names, values);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entity successful, hqlWhere:[{}].", hqlWhere);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " delete failed, hqlWhere:[{}; param:{}].", hqlWhere, BaseDaoHelper.getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	public int deleteByFullHql(String hql, Map<String, Object> parameter) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entities, hql:[{}].", hql);
			}
			Query query = entityManager.createQuery(hql);
			result = BaseDaoHelper.update(query, parameter);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entity successful, hql:[{}].", hql);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " delete failed, hql:[{}].", hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	public int deleteByFullHql(String hql, String[] names, Object[] values) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entities, hql:[{}].", hql);
			}
			Query query = entityManager.createQuery(hql);
			result = BaseDaoHelper.update(query, names, values);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entity successful, hql:[{}].", hql);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " delete failed, hql:[{}].", hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	
	// ================ update =======================
	@Override
	public void update(T entity) throws AppframeException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " updating entity, {}.", entity);
			}
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
	public int updateByFullHql(String hql, Map<String, Object> parameter) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " updateing entities, hql:[{}].", hql);
			}
			Query query = entityManager.createQuery(hql);
			result = BaseDaoHelper.update(query, parameter);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " updateing entities successful, resultSize:[{}]." + result);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, hql:[{}; param:{}].", hql, BaseDaoHelper.getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	public int updateByFullHql(String hql, String[] names, Object[] values) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " updateing entities, hql:[{}].", hql);
			}
			Query query = entityManager.createQuery(hql);
			result = BaseDaoHelper.update(query, names, values);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " updateing entities successful, resultSize:[{}]." + result);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, hql:[{}; param:{}].", hql, BaseDaoHelper.getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	public int updateNativeSql(String sql, Map<String, Object> parameter) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " updating entities, sql:[{}].", sql);
			}
			Query query = entityManager.createNativeQuery(sql);
			result = BaseDaoHelper.update(query, parameter);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " updating entities successful, resultSize:[{}]." + result);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, sql:[{}; param:{}].", sql, BaseDaoHelper.getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	public int updateNativeSql(String sql, String[] names, Object[] values) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " updating entities, sql:[{}].", sql);
			}
			Query query = entityManager.createNativeQuery(sql);
			result = BaseDaoHelper.update(query, names, values);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " updating entities successful, resultSize:[{}]." + result);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, sql:[{}; param:{}].", sql, BaseDaoHelper.getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	public List<Integer> updateNativeSqls(String sql, String[] names, List<Object[]> valuesList) throws AppframeException {
		List<Integer> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " banth update, sql:[{}].", sql);
			}
			Session session = (Session) entityManager.getDelegate();
			FlushMode mode = session.getFlushMode();
			session.setFlushMode(FlushMode.MANUAL);
			Query query = entityManager.createNativeQuery(sql);
			if (null != valuesList) {
				result = new ArrayList<>(valuesList.size());
				for (Object[] values: valuesList) {
					result.add(BaseDaoHelper.update(query, names, values));
				}
			}
			session.setFlushMode(mode);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " banth update successful, update size:[{}]." + valuesList.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, sql:[{}; param:{}].", sql, BaseDaoHelper.getParamStr(names, valuesList.get(0)));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	
	// ================ query =======================
	@Override
	public T getReference(long id) throws AppframeException {
		T result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entity reference with id:[{}].", id);
			}
			result = entityManager.getReference(entityClass, id);
			if (logger.isDebugEnabled()) {
				if (null != result) {
					logger.debug(entityClassName + " query entity reference successful.");
				} else {
					logger.debug(entityClassName + " can't query entity reference.");
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, id:[{}].", id);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	public T queryById(long id) throws AppframeException {
		T result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entity with id:[{}].", id);
			}
			result = entityManager.find(entityClass, id);
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
		T result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
			}
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			Object obj = query.getSingleResult();
			if (null == obj) {
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " entity not found, hqlWhere:[{}]", hqlWhere);
				}
			} else {
				result = (T) obj;
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
	public T queryEntity(String hqlWhere, Map<String, Object> parameter) throws AppframeException {
		T result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entity, hqlWhere:[{}]", hqlWhere);
			}
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			BaseDaoHelper.setNamedValue(query, parameter);
			Object obj = query.getSingleResult();
			if (null == obj) {
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " entity not found, hqlWhere:[{}]", hqlWhere);
				}
			} else {
				result = (T) obj;
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " query entity successful, hqlWhere:[{}]", hqlWhere);
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hqlWhere:[{}; param:{}].", hqlWhere, BaseDaoHelper.getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	@SuppressWarnings("unchecked")
	public T queryEntity(String hqlWhere, String[] names, Object[] values) throws AppframeException {
		T result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entity, hqlWhere:[{}]", hqlWhere);
			}
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			BaseDaoHelper.setNamedValue(query, names, values);
			Object obj = query.getSingleResult();
			if (null == obj) {
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " entity not found, hqlWhere:[{}]", hqlWhere);
				}
			} else {
				result = (T) obj;
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " query entity successful, hqlWhere:[{}]", hqlWhere);
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hqlWhere:[{}; param:{}].", hqlWhere, BaseDaoHelper.getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryEntities() throws AppframeException {
		List<T> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying all entities");
			}
			final String queryString = "from " + entityClassName;
			Query query = entityManager.createQuery(queryString);
			result = BaseDaoHelper.list(query, null, null);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying all entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query all failed.");
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(String hqlWhere) throws AppframeException {
		List<T> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying all entities, hqlWhere:[{}]", hqlWhere);
			}
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			result = BaseDaoHelper.list(query, null, null);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
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
	public List<T> queryEntities(String hqlWhere, int rowIndex, int rowNum) throws AppframeException {
		List<T> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
			}
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			result = BaseDaoHelper.list(query, null, rowIndex, rowNum);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
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
	public List<T> queryEntities(String hqlWhere, Map<String, Object> parameter) throws AppframeException {
		List<T> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
			}
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			result = BaseDaoHelper.list(query, parameter, null);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hqlWhere:[{}; param:{}].", hqlWhere, BaseDaoHelper.getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(String hqlWhere, String[] names, Object[] values) throws AppframeException {
		List<T> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
			}
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			BaseDaoHelper.setNamedValue(query, names, values);
			result = BaseDaoHelper.list(query, null, null);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hqlWhere:[{}; param:{}].", hqlWhere, BaseDaoHelper.getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(String hqlWhere, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException {
		List<T> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
			}
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			result = BaseDaoHelper.list(query, parameter, null);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hqlWhere:[{}; param:{}].", hqlWhere, BaseDaoHelper.getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(String hqlWhere, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException {
		List<T> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
			}
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			BaseDaoHelper.setNamedValue(query, names, values);
			result = BaseDaoHelper.list(query, null, rowIndex, rowNum);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hqlWhere:[{}; param:{}].", hqlWhere, BaseDaoHelper.getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public DataContainer<T> queryEntities(PageLoadConfig pageLoadCfg) throws AppframeException {
		DataContainer<T> result = new DataContainer<>(pageLoadCfg);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying all entities");
			}
			
			int totalCount = countByFullHql("select count(*) from " + entityClassName, null, null);
			result.setTotalCount(totalCount);
			if (0 < totalCount) {
				String where = " ";
				if (null != pageLoadCfg) {
					where += pageLoadCfg.toSortSql();
				}
				
				final String queryString = "from " + entityClassName + where;
				Query query = entityManager.createQuery(queryString);
				List<T> dataList = BaseDaoHelper.list(query, null, pageLoadCfg);
				result.setDataList(dataList);
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " querying all entities successful, result size:[{}]." + dataList.size());
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query all failed.");
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public DataContainer<T> queryEntities(String hqlWhere, PageLoadConfig pageLoadCfg) throws AppframeException {
		DataContainer<T> result = new DataContainer<>(pageLoadCfg);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
			}
			String where = " where " + hqlWhere;
			int totalCount = countByFullHql("select count(*) from " + entityClassName + where, null, null);
			result.setTotalCount(totalCount);
			if (0 < totalCount) {
				if (null != pageLoadCfg) {
					where += pageLoadCfg.toSortSql();
				}
				final String queryString = "from " + entityClassName + where;
				Query query = entityManager.createQuery(queryString);
				
				List<T> dataList = BaseDaoHelper.list(query, null, pageLoadCfg);
				result.setDataList(dataList);
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " querying entities successful, result size:[{}]." + dataList.size());
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
	public DataContainer<T> queryEntities(String hqlWhere, Map<String, Object> parameter, PageLoadConfig pageLoadCfg) throws AppframeException {
		DataContainer<T> result = new DataContainer<>(pageLoadCfg);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
			}
			String where = " where " + hqlWhere;
			int totalCount = countByFullHql("select count(*) from " + entityClassName + where, parameter);
			result.setTotalCount(totalCount);
			if (0 < totalCount) {
				if (null != pageLoadCfg) {
					where += pageLoadCfg.toSortSql();
				}
				final String queryString = "from " + entityClassName + where;
				Query query = entityManager.createQuery(queryString);
				List<T> dataList = BaseDaoHelper.list(query, parameter, pageLoadCfg);
				result.setDataList(dataList);
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " querying entities successful, result size:[{}]." + dataList.size());
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hqlWhere:[{}; param:{}].", hqlWhere, BaseDaoHelper.getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	@SuppressWarnings("unchecked")
	public DataContainer<T> queryEntities(String hqlWhere, String[] names, Object[] values, PageLoadConfig pageLoadCfg) throws AppframeException {
		DataContainer<T> result = new DataContainer<>(pageLoadCfg);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
			}
			String where = " where " + hqlWhere;
			int totalCount = countByFullHql("select count(*) from " + entityClassName + where, names, values);
			result.setTotalCount(totalCount);
			if (0 < totalCount) {
				if (null != pageLoadCfg) {
					where += pageLoadCfg.toSortSql();
				}
				final String queryString = "from " + entityClassName + where;
				Query query = entityManager.createQuery(queryString);
				BaseDaoHelper.setNamedValue(query, names, values);
				List<T> dataList = BaseDaoHelper.list(query, null, pageLoadCfg);
				result.setDataList(dataList);
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " querying entities successful, result size:[{}]." + dataList.size());
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hqlWhere:[{}; param:{}].", hqlWhere, BaseDaoHelper.getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	// ################################################################
	@Override
	public int countByFullHql(String hql, Map<String, Object> parameter) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " count, hql:[{}]", hql);
			}
			Query query = entityManager.createQuery(hql);
			BaseDaoHelper.setNamedValue(query, parameter);
			result = DataTypeUtil.getAsInteger(query.getSingleResult());
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " count successful, count:[{}]", result);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " count failed, hql:[{}]", hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	public int countByFullHql(String hql, String[] names, Object[] values) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " count, hql:[{}]", hql);
			}
			Query query = entityManager.createQuery(hql);
			BaseDaoHelper.setNamedValue(query, names, values);
			result = DataTypeUtil.getAsInteger(query.getSingleResult());
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " count successful, count:[{}]", result);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " count failed, hql:[{}]", hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	public int countNativeSql(String sql, Map<String, Object> parameter) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " count, sql:[{}]", sql);
			}
			Query query = entityManager.createNativeQuery(sql);
			BaseDaoHelper.setNamedValue(query, parameter);
			result = DataTypeUtil.getAsInteger(query.getSingleResult());
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " count successful, count:[{}]", result);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " count failed, hql:[{}]", sql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	public int countNativeSql(String sql, String[] names, Object[] values) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " count, sql:[{}]", sql);
			}
			Query query = entityManager.createNativeQuery(sql);
			BaseDaoHelper.setNamedValue(query, names, values);
			result = DataTypeUtil.getAsInteger(query.getSingleResult());
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " count successful, count:[{}]", result);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " count failed, hql:[{}]", sql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	
	/**
	 * 因为对hql无约束， 可能返回结果的类型不是安全的
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List queryByFullHql(String hql) throws AppframeException {
		List result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			Query query = entityManager.createQuery(hql);
			/*
			 * createQuery("select c as c, c.name as name from Customer c")
			 * .setResultTransformer(Transformers.aliasToBean(YourClass.class));
			 */
			result = BaseDaoHelper.list(query, null, null);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}]", hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	/**
	 * 因为对hql无约束， 可能返回结果的类型不是安全的
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List queryByFullHql(String hql, int rowIndex, int rowNum) throws AppframeException {
		List result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			Query query = entityManager.createQuery(hql);
			result = BaseDaoHelper.list(query, null, rowIndex, rowNum);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}]", hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List queryByFullHql(String hql, Map<String, Object> parameter) throws AppframeException {
		List result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			Query query = entityManager.createQuery(hql);
			result = BaseDaoHelper.list(query, parameter, null);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}; param:{}].", hql, BaseDaoHelper.getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	@SuppressWarnings("rawtypes")
	public List queryByFullHql(String hql, String[] names, Object[] values) throws AppframeException {
		List result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			Query query = entityManager.createQuery(hql);
			BaseDaoHelper.setNamedValue(query, names, values);
			result = BaseDaoHelper.list(query, null, null);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}; param:{}].", hql, BaseDaoHelper.getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public List queryByFullHql(String hql, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException {
		List result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			Query query = entityManager.createQuery(hql);
			result = BaseDaoHelper.list(query, parameter, rowIndex, rowNum);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}; param:{}].", hql, BaseDaoHelper.getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	/**
	 * 因为对hql无约束， 可能返回结果的类型不是安全的
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List queryByFullHql(String hql, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException {
		List result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			Query query = entityManager.createQuery(hql);
			BaseDaoHelper.setNamedValue(query, names, values);
			result = BaseDaoHelper.list(query, null, rowIndex, rowNum);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}; param:{}].", hql, BaseDaoHelper.getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}

	
	@Override
	@SuppressWarnings("rawtypes")
	public List queryNativeSql(String sql, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException {
		List result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, sql:[{}]", sql);
			}
			Query query = entityManager.createNativeQuery(sql);
			result = BaseDaoHelper.list(query, parameter, rowIndex, rowNum);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, sql:[{}; param:{}].", sql, BaseDaoHelper.getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	@SuppressWarnings("rawtypes")
	public List queryNativeSql(String sql, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException {
		List result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, sql:[{}]", sql);
			}
			Query query = entityManager.createNativeQuery(sql);
			BaseDaoHelper.setNamedValue(query, names, values);
			result = BaseDaoHelper.list(query, null, rowIndex, rowNum);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, sql:[{}; param:{}].", sql, BaseDaoHelper.getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public List queryByFullHqlNoLimit(String hql, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException {
		List result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			Query query = entityManager.createQuery(hql);
			result = BaseDaoHelper.list(query, parameter, rowIndex, rowNum);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}; param:{}].", hql, BaseDaoHelper.getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List queryByFullHqlNoLimit(String hql, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException {
		List<T> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			Query query = entityManager.createQuery(hql);
			BaseDaoHelper.setNamedValue(query, names, values);
			result = BaseDaoHelper.list(query, null, rowIndex, rowNum);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}; param:{}].", hql, BaseDaoHelper.getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public DataContainer queryByFullHql(String hql, PageLoadConfig pageLoadCfg) throws AppframeException {
		DataContainer result = new DataContainer(pageLoadCfg);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			String hqlCount = "select count(*) from (" + hql + ")";
			int totalCount = countByFullHql(hqlCount, null, null);
			result.setTotalCount(totalCount);
			if (0 < totalCount) {
				if (null != pageLoadCfg) {
					hql += " " + pageLoadCfg.toSortSql();
				}
				Query query = entityManager.createQuery(hql);
				List dataList = BaseDaoHelper.list(query, null, pageLoadCfg);
				result.setDataList(dataList);
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " querying entities successful, result size:[{}]." + dataList.size());
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}]", hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataContainer queryByFullHql(String hql, Map<String, Object> parameter, PageLoadConfig pageLoadCfg) throws AppframeException {
		DataContainer result = new DataContainer(pageLoadCfg);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			String hqlCount = "select count(*) from (" + hql + ")";
			int totalCount = countByFullHql(hqlCount, null, null);
			result.setTotalCount(totalCount);
			if (0 < totalCount) {
				if (null != pageLoadCfg) {
					hql += " " + pageLoadCfg.toSortSql();
				}
				Query query = entityManager.createQuery(hql);
				List dataList = BaseDaoHelper.list(query, parameter, pageLoadCfg);
				result.setDataList(dataList);
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " querying entities successful, result size:[{}]." + dataList.size());
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}]", hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public DataContainer queryByFullHql(String hql, String[] names, Object[] values, PageLoadConfig pageLoadCfg) throws AppframeException {
		DataContainer result = new DataContainer(pageLoadCfg);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			String hqlCount = "select count(*) from (" + hql + ")";
			int totalCount = countByFullHql(hqlCount, names, values);
			result.setTotalCount(totalCount);
			if (0 < totalCount) {
				if (null != pageLoadCfg) {
					hql += " " + pageLoadCfg.toSortSql();
				}
				Query query = entityManager.createQuery(hql);
				BaseDaoHelper.setNamedValue(query, names, values);
				List dataList = BaseDaoHelper.list(query, null, pageLoadCfg);
				result.setDataList(dataList);
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " querying entities successful, result size:[{}]." + dataList.size());
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}]", hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataContainer queryNativeSql(String sql, Map<String, Object> parameter, PageLoadConfig pageLoadCfg) throws AppframeException {
		DataContainer result = new DataContainer(pageLoadCfg);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, sql:[{}]", sql);
			}
			String sqlCount = "select count(*) from (" + sql + ")";
			int totalCount = countNativeSql(sqlCount, parameter);
			result.setTotalCount(totalCount);
			if (0 < totalCount) {
				Query query = entityManager.createNativeQuery(sql);
				List dataList = BaseDaoHelper.list(query, parameter, pageLoadCfg);
				result.setDataList(dataList);
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " querying entities successful, result size:[{}]." + dataList.size());
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, sql:[{}; param:{}].", sql, BaseDaoHelper.getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public DataContainer queryNativeSql(String sql, String[] names, Object[] values, PageLoadConfig pageLoadCfg) throws AppframeException {
		DataContainer result = new DataContainer(pageLoadCfg);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, sql:[{}]", sql);
			}
			String sqlCount = "select count(*) from (" + sql + ")";
			int totalCount = countNativeSql(sqlCount, names, values);
			result.setTotalCount(totalCount);
			if (0 < totalCount) {
				Query query = entityManager.createNativeQuery(sql);
				BaseDaoHelper.setNamedValue(query, names, values);
				List dataList = BaseDaoHelper.list(query, null, pageLoadCfg);
				result.setDataList(dataList);
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " querying entities successful, result size:[{}]." + dataList.size());
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, sql:[{}; param:{}].", sql, BaseDaoHelper.getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	
	// =================== other ==========================
	@Override
	public void flush() throws AppframeException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " flushing.");
			}
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
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " clearing.");
			}
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
