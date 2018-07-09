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
import com.kaidin.appframe.service.interfaces.IBaseHibernateDao;
import com.kaidin.appframe.service.interfaces.IDaoContext;
import com.kaidin.common.util.DataTypeUtil;
import com.kaidin.common.util.query.PageData;
import com.kaidin.common.util.query.PageLoadConfig;

/**
 * 操作数据库的ejb实现
 * 
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class BaseDaoEjbImpl<T extends BaseEntity> implements IBaseHibernateDao<T> {
	private static final transient Logger logger = LoggerFactory.getLogger(BaseDaoEjbImpl.class);
	private final Class<T>                entityClass;                                           // 实体类
	private final String                  entityClassName;                                       // 实体类名称，方便打印日志使用

	// @PersistenceUnit
	// private EntityManagerFactory emf;

	// @PersistenceContext(type = PersistenceContextType.TRANSACTION)
	private final EntityManager           entityManager;

	// // @PostConstruct
	// protected void initialize() {
	// if (null != entityManager) {
	// entityManager = null;
	// }
	// if (ServiceFactory.isEjb()) {
	// entityManager = emf.createEntityManager();
	// entityManager.setFlushMode(FlushModeType.AUTO);
	// entityManager.joinTransaction();
	// }
	// }
	public BaseDaoEjbImpl(Class<T> clazz, IDaoContext aDaoContext) throws AppframeException {
		entityClass = clazz;
		entityClassName = clazz.getName();
		// if (!ServiceFactory.isEjb()) {
		// if (null != entityManager) {
		// entityManager = null;
		// }
		entityManager = aDaoContext.getEntityManager();
		// }
	}

	public BaseDaoEjbImpl(Class<T> clazz, IDaoContext aDaoContext, String jndiName) throws AppframeException {
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
			logger.debug("{} saving entity, {}.", entityClassName, entity);
			entityManager.persist(entity);
			logger.debug("{} saveing successful, {}.", entityClassName, entity);
		} catch (Exception e) {
			logger.error("{} save failed, {}.", entityClassName, entity);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}

		return result;
	}

	// =================== delete ==========================
	@Override
	public void delete(T entity) throws AppframeException {
		try {
			logger.debug("{} deleting entity, {}.", entityClassName, entity);
			entityManager.remove(entity);
			logger.debug("{} deleting entity successful, {}.", entityClassName, entity);
		} catch (Exception e) {
			logger.error("{} delete failed, {}.", entityClassName, entity);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
	}

	@Override
	public int deleteById(long id) throws AppframeException {
		try {
			logger.debug("{} deleting entity, id:[{}].", entityClassName, id);
			T entity = queryEntity(id);
			if (null != entity) {
				// 先查询出来再删除
				entityManager.remove(entity);
			}
			logger.debug("{} deleting entity successful, id:[{}].", entityClassName, id);
		} catch (Exception e) {
			logger.error("{} delete failed, id:[{}].", entityClassName, id);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
	}

	@Override
	public int deleteEntities(String hqlWhere, Map<String, Object> parameter) throws AppframeException {
		int result = 0;

		try {
			logger.debug("{} deleting entities, hqlWhere:[{}].", entityClassName, hqlWhere);
			final String queryString = "delete from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			result = BaseDaoHelper.update(query, parameter);
			logger.debug("{} deleting entity successful, hqlWhere:[{}].", entityClassName, hqlWhere);
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
			logger.debug("{} deleting entities, hqlWhere:[{}].", entityClassName, hqlWhere);
			final String queryString = "delete from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			result = BaseDaoHelper.update(query, names, values);
			logger.debug("{} deleting entity successful, hqlWhere:[{}].", entityClassName, hqlWhere);
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
			logger.debug("{} deleting entities, hql:[{}].", entityClassName, hql);
			Query query = entityManager.createQuery(hql);
			result = BaseDaoHelper.update(query, parameter);
			logger.debug("{} deleting entity successful, hql:[{}].", entityClassName, hql);
		} catch (Exception e) {
			logger.error("{} delete failed, hql:[{}].", entityClassName, hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}

		return result;
	}

	@Override
	public int deleteByFullHql(String hql, String[] names, Object[] values) throws AppframeException {
		int result = 0;

		try {
			logger.debug("{} deleting entities, hql:[{}].", entityClassName, hql);
			Query query = entityManager.createQuery(hql);
			result = BaseDaoHelper.update(query, names, values);
			logger.debug("{} deleting entity successful, hql:[{}].", entityClassName, hql);
		} catch (Exception e) {
			logger.error("{} delete failed, hql:[{}].", entityClassName, hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}

		return result;
	}

	// ================ update =======================
	@Override
	public void update(T entity) throws AppframeException {
		try {
			logger.debug("{} updating entity, {}.", entityClassName, entity);
			if (0 < entity.getId()) {
				// 默认是大于0的，小于等于0的认为是特殊的
				entityManager.merge(entity);
			} else {
				save(entity);
			}
			logger.debug("{} update successful, {}.", entityClassName, entity);
		} catch (Exception e) {
			logger.error("{} update failed, {}.", entityClassName, entity);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
	}

	@Override
	public int updateByFullHql(String hql, Map<String, Object> parameter) throws AppframeException {
		int result = 0;

		try {
			logger.debug("{} updateing entities, hql:[{}].", entityClassName, hql);
			Query query = entityManager.createQuery(hql);
			result = BaseDaoHelper.update(query, parameter);
			logger.debug("{} updateing entities successful, resultSize:[{}].", entityClassName, result);
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
			logger.debug("{} updateing entities, hql:[{}].", entityClassName, hql);
			Query query = entityManager.createQuery(hql);
			result = BaseDaoHelper.update(query, names, values);
			logger.debug("{} updateing entities successful, resultSize:[{}].", entityClassName, result);
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
			logger.debug("{} updating entities, sql:[{}].", entityClassName, sql);
			Query query = entityManager.createNativeQuery(sql);
			result = BaseDaoHelper.update(query, parameter);
			logger.debug("{} updating entities successful, resultSize:[{}].", entityClassName, result);
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
			logger.debug("{} updating entities, sql:[{}].", entityClassName, sql);
			Query query = entityManager.createNativeQuery(sql);
			result = BaseDaoHelper.update(query, names, values);
			logger.debug("{} updating entities successful, resultSize:[{}].", entityClassName, result);
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
			logger.debug("{} banth update, sql:[{}].", entityClassName, sql);
			Session session = (Session) entityManager.getDelegate();
			FlushMode mode = session.getFlushMode();
			session.setFlushMode(FlushMode.MANUAL);
			Query query = entityManager.createNativeQuery(sql);
			if (null != valuesList) {
				result = new ArrayList<>(valuesList.size());
				for (Object[] values : valuesList) {
					result.add(BaseDaoHelper.update(query, names, values));
				}
			}
			session.setFlushMode(mode);
			logger.debug("{} banth update successful, update size:[{}].", entityClassName, valuesList.size());
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
			logger.debug("{} querying entity reference with id:[{}].", entityClassName, id);
			result = entityManager.getReference(entityClass, id);
			if (null != result) {
				logger.debug("{} query entity reference successful.", entityClassName);
			} else {
				logger.debug("{} can't query entity reference.", entityClassName);
			}
		} catch (Exception e) {
			logger.error("{} query failed, id:[{}].", entityClassName, id);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}

		return result;
	}

	@Override
	public T queryEntity(long id) throws AppframeException {
		T result = null;

		try {
			logger.debug("{} querying entity with id:[{}].", entityClassName, id);
			result = entityManager.find(entityClass, id);
			if (null != result) {
				logger.debug("{} query entity successful.", entityClassName);
			} else {
				logger.debug("{} can't query entity.", entityClassName);
			}
		} catch (Exception e) {
			logger.error("{} query failed, pk:[{}].", entityClassName, id);
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
			logger.debug("{} querying entities, hqlWhere:[{}]", entityClassName, hqlWhere);
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			Object obj = query.getSingleResult();
			if (null == obj) {
				logger.debug("{} entity not found, hqlWhere:[{}]", entityClassName, hqlWhere);
			} else {
				result = (T) obj;
				logger.debug("{} query entity successful, hqlWhere:[{}]", entityClassName, hqlWhere);
			}
		} catch (Exception e) {
			logger.error("{} query failed, hqlWhere:[{}]", entityClassName, hqlWhere);
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
			logger.debug("{} querying entity, hqlWhere:[{}]", entityClassName, hqlWhere);
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			BaseDaoHelper.setNamedValue(query, parameter);
			Object obj = query.getSingleResult();
			if (null == obj) {
				logger.debug("{} entity not found, hqlWhere:[{}]", entityClassName, hqlWhere);
			} else {
				result = (T) obj;
				logger.debug("{} query entity successful, hqlWhere:[{}]", entityClassName, hqlWhere);
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
			logger.debug("{} querying entity, hqlWhere:[{}]", entityClassName, hqlWhere);
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			BaseDaoHelper.setNamedValue(query, names, values);
			Object obj = query.getSingleResult();
			if (null == obj) {
				logger.debug("{} entity not found, hqlWhere:[{}]", entityClassName, hqlWhere);
			} else {
				result = (T) obj;
				logger.debug("{} query entity successful, hqlWhere:[{}]", entityClassName, hqlWhere);
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
			logger.debug("{} querying all entities.", entityClassName);
			final String queryString = "from " + entityClassName;
			Query query = entityManager.createQuery(queryString);
			result = BaseDaoHelper.list(query, null, null);
			logger.debug("{} querying all entities successful, result size:[{}].", entityClassName, result.size());
		} catch (Exception e) {
			logger.error("{} query all failed.", entityClassName);
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
			logger.debug("{} querying all entities, hqlWhere:[{}]", entityClassName, hqlWhere);
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			result = BaseDaoHelper.list(query, null, null);
			logger.debug("{} querying entities successful, result size:[{}].", entityClassName, result.size());
		} catch (Exception e) {
			logger.error("{} query failed, hqlWhere:[{}]", entityClassName, hqlWhere);
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
			logger.debug("{} querying entities, hqlWhere:[{}]", entityClassName, hqlWhere);
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			result = BaseDaoHelper.list(query, null, rowIndex, rowNum);
			logger.debug("{} querying entities successful, result size:[{}].", entityClassName, result.size());
		} catch (Exception e) {
			logger.error("{} query failed, hqlWhere:[{}]", entityClassName, hqlWhere);
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
			logger.debug("{} querying entities, hqlWhere:[{}]", entityClassName, hqlWhere);
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			result = BaseDaoHelper.list(query, parameter, null);
			logger.debug("{} querying entities successful, result size:[{}].", entityClassName, result.size());
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
			logger.debug("{} querying entities, hqlWhere:[{}]", entityClassName, hqlWhere);
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			BaseDaoHelper.setNamedValue(query, names, values);
			result = BaseDaoHelper.list(query, null, null);
			logger.debug("{} querying entities successful, result size:[{}].", entityClassName, result.size());
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
			logger.debug("{} querying entities, hqlWhere:[{}]", entityClassName, hqlWhere);
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			result = BaseDaoHelper.list(query, parameter, null);
			logger.debug("{} querying entities successful, result size:[{}].", entityClassName, result.size());
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
			logger.debug("{} querying entities, hqlWhere:[{}]", entityClassName, hqlWhere);
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			Query query = entityManager.createQuery(queryString);
			BaseDaoHelper.setNamedValue(query, names, values);
			result = BaseDaoHelper.list(query, null, rowIndex, rowNum);
			logger.debug("{} querying entities successful, result size:[{}].", entityClassName, result.size());
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hqlWhere:[{}; param:{}].", hqlWhere, BaseDaoHelper.getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}

		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PageData<T> queryEntities(PageLoadConfig pageLoadCfg) throws AppframeException {
		PageData<T> result = new PageData<>(pageLoadCfg);

		try {
			logger.debug("{} querying all entities.", entityClassName);
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
				logger.debug("{} querying all entities successful, result size:[{}].", entityClassName, dataList.size());
			}
		} catch (Exception e) {
			logger.error("{} query all failed.", entityClassName);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}

		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PageData<T> queryEntities(String hqlWhere, PageLoadConfig pageLoadCfg) throws AppframeException {
		PageData<T> result = new PageData<>(pageLoadCfg);

		try {
			logger.debug("{} querying entities, hqlWhere:[{}]", entityClassName, hqlWhere);
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
				logger.debug("{} querying entities successful, result size:[{}].", entityClassName, dataList.size());
			}
		} catch (Exception e) {
			logger.error("{} query failed, hqlWhere:[{}]", entityClassName, hqlWhere);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}

		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PageData<T> queryEntities(String hqlWhere, Map<String, Object> parameter, PageLoadConfig pageLoadCfg) throws AppframeException {
		PageData<T> result = new PageData<>(pageLoadCfg);

		try {
			logger.debug("{} querying entities, hqlWhere:[{}]", entityClassName, hqlWhere);
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
				logger.debug("{} querying entities successful, result size:[{}].", entityClassName, dataList.size());
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
	public PageData<T> queryEntities(String hqlWhere, String[] names, Object[] values, PageLoadConfig pageLoadCfg) throws AppframeException {
		PageData<T> result = new PageData<>(pageLoadCfg);

		try {
			logger.debug("{} querying entities, hqlWhere:[{}]", entityClassName, hqlWhere);
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
				logger.debug("{} querying entities successful, result size:[{}].", entityClassName, dataList.size());
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
			logger.debug("{} count, hql:[{}]", entityClassName, hql);
			Query query = entityManager.createQuery(hql);
			BaseDaoHelper.setNamedValue(query, parameter);
			result = DataTypeUtil.asInteger(query.getSingleResult());
			logger.debug("{} count successful, count:[{}]", entityClassName, result);
		} catch (Exception e) {
			logger.error("{} count failed, hql:[{}]", entityClassName, hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}

		return result;
	}

	@Override
	public int countByFullHql(String hql, String[] names, Object[] values) throws AppframeException {
		int result = 0;

		try {
			logger.debug("{} count, hql:[{}]", entityClassName, hql);
			Query query = entityManager.createQuery(hql);
			BaseDaoHelper.setNamedValue(query, names, values);
			result = DataTypeUtil.asInteger(query.getSingleResult());
			logger.debug("{} count successful, count:[{}]", entityClassName, result);
		} catch (Exception e) {
			logger.error("{} count failed, hql:[{}]", entityClassName, hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}

		return result;
	}

	@Override
	public int countNativeSql(String sql, Map<String, Object> parameter) throws AppframeException {
		int result = 0;

		try {
			logger.debug("{} count, sql:[{}]", entityClassName, sql);
			Query query = entityManager.createNativeQuery(sql);
			BaseDaoHelper.setNamedValue(query, parameter);
			result = DataTypeUtil.asInteger(query.getSingleResult());
			logger.debug("{} count successful, count:[{}]", entityClassName, result);
		} catch (Exception e) {
			logger.error("{} count failed, hql:[{}]", entityClassName, sql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}

		return result;
	}

	@Override
	public int countNativeSql(String sql, String[] names, Object[] values) throws AppframeException {
		int result = 0;

		try {
			logger.debug("{} count, sql:[{}]", entityClassName, sql);
			Query query = entityManager.createNativeQuery(sql);
			BaseDaoHelper.setNamedValue(query, names, values);
			result = DataTypeUtil.asInteger(query.getSingleResult());
			logger.debug("{} count successful, count:[{}]", entityClassName, result);
		} catch (Exception e) {
			logger.error("{} count failed, hql:[{}]", entityClassName, sql);
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
			logger.debug("{} querying entities, hql:[{}]", entityClassName, hql);
			Query query = entityManager.createQuery(hql);
			/*
			 * createQuery("select c as c, c.name as name from Customer c")
			 * .setResultTransformer(Transformers.aliasToBean(YourClass.class));
			 */
			result = BaseDaoHelper.list(query, null, null);
			logger.debug("{} querying entities successful, result size:[{}].", entityClassName, result.size());
		} catch (Exception e) {
			logger.error("{} query failed, hql:[{}]", entityClassName, hql);
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
			logger.debug("{} querying entities, hql:[{}]", entityClassName, hql);
			Query query = entityManager.createQuery(hql);
			result = BaseDaoHelper.list(query, null, rowIndex, rowNum);
			logger.debug("{} querying entities successful, result size:[{}].", entityClassName, result.size());
		} catch (Exception e) {
			logger.error("{} query failed, hql:[{}]", entityClassName, hql);
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
	public List queryByFullHql(String hql, Map<String, Object> parameter) throws AppframeException {
		List result = null;

		try {
			logger.debug("{} querying entities, hql:[{}]", entityClassName, hql);
			Query query = entityManager.createQuery(hql);
			result = BaseDaoHelper.list(query, parameter, null);
			logger.debug("{} querying entities successful, result size:[{}].", entityClassName, result.size());
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
	public List queryByFullHql(String hql, String[] names, Object[] values) throws AppframeException {
		List result = null;

		try {
			logger.debug("{} querying entities, hql:[{}]", entityClassName, hql);
			Query query = entityManager.createQuery(hql);
			BaseDaoHelper.setNamedValue(query, names, values);
			result = BaseDaoHelper.list(query, null, null);
			logger.debug("{} querying entities successful, result size:[{}].", entityClassName, result.size());
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}; param:{}].", hql, BaseDaoHelper.getParamStr(names, values));
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
	public List queryByFullHql(String hql, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException {
		List result = null;

		try {
			logger.debug("{} querying entities, hql:[{}]", entityClassName, hql);
			Query query = entityManager.createQuery(hql);
			result = BaseDaoHelper.list(query, parameter, rowIndex, rowNum);
			logger.debug("{} querying entities successful, result size:[{}].", entityClassName, result.size());
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
			logger.debug("{} querying entities, hql:[{}]", entityClassName, hql);
			Query query = entityManager.createQuery(hql);
			BaseDaoHelper.setNamedValue(query, names, values);
			result = BaseDaoHelper.list(query, null, rowIndex, rowNum);
			logger.debug("{} querying entities successful, result size:[{}].", entityClassName, result.size());
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}; param:{}].", hql, BaseDaoHelper.getParamStr(names, values));
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
	public List queryNativeSql(String sql, Map<String, Object> parameter) throws AppframeException {
		List result = null;

		try {
			logger.debug("{} querying entities, sql:[{}]", entityClassName, sql);
			Query query = entityManager.createNativeQuery(sql);
			result = BaseDaoHelper.list(query, parameter, null);
			logger.debug("{} querying entities successful, result size:[{}].", entityClassName, result.size());
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, sql:[{}; param:{}].", sql, BaseDaoHelper.getParamStr(parameter));
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
	public List queryNativeSql(String sql, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException {
		List result = null;

		try {
			logger.debug("{} querying entities, sql:[{}]", entityClassName, sql);
			Query query = entityManager.createNativeQuery(sql);
			result = BaseDaoHelper.list(query, parameter, rowIndex, rowNum);
			logger.debug("{} querying entities successful, result size:[{}].", entityClassName, result.size());
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, sql:[{}; param:{}].", sql, BaseDaoHelper.getParamStr(parameter));
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
	public List queryNativeSql(String sql, String[] names, Object[] values) throws AppframeException {
		List result = null;

		try {
			logger.debug("{} querying entities, sql:[{}]", entityClassName, sql);
			Query query = entityManager.createNativeQuery(sql);
			BaseDaoHelper.setNamedValue(query, names, values);
			result = BaseDaoHelper.list(query, null, null);
			logger.debug("{} querying entities successful, result size:[{}].", entityClassName, result.size());
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, sql:[{}; param:{}].", sql, BaseDaoHelper.getParamStr(names, values));
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
	public List queryNativeSql(String sql, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException {
		List result = null;

		try {
			logger.debug("{} querying entities, sql:[{}]", entityClassName, sql);
			Query query = entityManager.createNativeQuery(sql);
			BaseDaoHelper.setNamedValue(query, names, values);
			result = BaseDaoHelper.list(query, null, rowIndex, rowNum);
			logger.debug("{} querying entities successful, result size:[{}].", entityClassName, result.size());
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, sql:[{}; param:{}].", sql, BaseDaoHelper.getParamStr(names, values));
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
	public List queryByFullHqlNoLimit(String hql, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException {
		List result = null;

		try {
			logger.debug("{} querying entities, hql:[{}]", entityClassName, hql);
			Query query = entityManager.createQuery(hql);
			result = BaseDaoHelper.list(query, parameter, rowIndex, rowNum);
			logger.debug("{} querying entities successful, result size:[{}].", entityClassName, result.size());
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List queryByFullHqlNoLimit(String hql, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException {
		List<T> result = null;

		try {
			logger.debug("{} querying entities, hql:[{}]", entityClassName, hql);
			Query query = entityManager.createQuery(hql);
			BaseDaoHelper.setNamedValue(query, names, values);
			result = BaseDaoHelper.list(query, null, rowIndex, rowNum);
			logger.debug("{} querying entities successful, result size:[{}].", entityClassName, result.size());
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}; param:{}].", hql, BaseDaoHelper.getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}

		return result;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PageData queryByFullHql(String hql, PageLoadConfig pageLoadCfg) throws AppframeException {
		PageData result = new PageData(pageLoadCfg);

		try {
			logger.debug("{} querying entities, hql:[{}]", entityClassName, hql);
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
				logger.debug("{} querying entities successful, result size:[{}].", entityClassName, dataList.size());
			}
		} catch (Exception e) {
			logger.error("{} query failed, hql:[{}]", entityClassName, hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}

		return result;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PageData queryByFullHql(String hql, Map<String, Object> parameter, PageLoadConfig pageLoadCfg) throws AppframeException {
		PageData result = new PageData(pageLoadCfg);

		try {
			logger.debug("{} querying entities, hql:[{}]", entityClassName, hql);
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
				logger.debug("{} querying entities successful, result size:[{}].", entityClassName, dataList.size());
			}
		} catch (Exception e) {
			logger.error("{} query failed, hql:[{}]", entityClassName, hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}

		return result;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PageData queryByFullHql(String hql, String[] names, Object[] values, PageLoadConfig pageLoadCfg) throws AppframeException {
		PageData result = new PageData(pageLoadCfg);

		try {
			logger.debug("{} querying entities, hql:[{}]", entityClassName, hql);
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
				logger.debug("{} querying entities successful, result size:[{}].", entityClassName, dataList.size());
			}
		} catch (Exception e) {
			logger.error("{} query failed, hql:[{}]", entityClassName, hql);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}

		return result;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PageData queryNativeSql(String sql, Map<String, Object> parameter, PageLoadConfig pageLoadCfg) throws AppframeException {
		PageData result = new PageData(pageLoadCfg);

		try {
			logger.debug("{} querying entities, sql:[{}]", entityClassName, sql);
			String sqlCount = "select count(*) from (" + sql + ")";
			int totalCount = countNativeSql(sqlCount, parameter);
			result.setTotalCount(totalCount);
			if (0 < totalCount) {
				Query query = entityManager.createNativeQuery(sql);
				List dataList = BaseDaoHelper.list(query, parameter, pageLoadCfg);
				result.setDataList(dataList);
				logger.debug("{} querying entities successful, result size:[{}].", entityClassName, dataList.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, sql:[{}; param:{}].", sql, BaseDaoHelper.getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}

		return result;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PageData queryNativeSql(String sql, String[] names, Object[] values, PageLoadConfig pageLoadCfg) throws AppframeException {
		PageData result = new PageData(pageLoadCfg);

		try {
			logger.debug("{} querying entities, sql:[{}]", entityClassName, sql);
			String sqlCount = "select count(*) from (" + sql + ")";
			int totalCount = countNativeSql(sqlCount, names, values);
			result.setTotalCount(totalCount);
			if (0 < totalCount) {
				Query query = entityManager.createNativeQuery(sql);
				BaseDaoHelper.setNamedValue(query, names, values);
				List dataList = BaseDaoHelper.list(query, null, pageLoadCfg);
				result.setDataList(dataList);
				logger.debug("{} querying entities successful, result size:[{}].", entityClassName, dataList.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, sql:[{}; param:{}].", sql, BaseDaoHelper.getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}

		return result;
	}

	// ================ other =======================
	@Override
	public void flush() throws AppframeException {
		try {
			logger.debug("{} flushing.", entityClassName);
			entityManager.flush();
			logger.debug("{} flushing successful.", entityClassName);
		} catch (Exception e) {
			logger.error("{} flush failed.", entityClassName);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
	}

	@Override
	public void clear() throws AppframeException {
		try {
			logger.debug("{} clearing.", entityClassName);
			entityManager.clear();
			logger.debug("{} clearing successful.", entityClassName);
		} catch (Exception e) {
			logger.error("{} clear failed.", entityClassName);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
	}

	@Override
	public void merge(T entity) throws AppframeException {
		try {
			logger.debug("{} merge entity, {}.", entityClassName, entity);
			entityManager.merge(entity);
			logger.debug("{} merge entity successful, {}.", entityClassName, entity);
		} catch (Exception e) {
			logger.error("{} merge failed, {}.", entityClassName, entity);
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
	}
}
