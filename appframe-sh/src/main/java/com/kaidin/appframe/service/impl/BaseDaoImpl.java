package com.kaidin.appframe.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kaidin.appframe.config.AppframeConfig;
import com.kaidin.appframe.entity.BaseEntity;
import com.kaidin.appframe.exception.AppframeException;
import com.kaidin.appframe.service.interfaces.IBaseDao;
import com.kaidin.common.util.DataTypeUtil;
import com.kaidin.common.util.query.DataContainer;
import com.kaidin.common.util.query.PageLoadConfig;
/**
 * 操作数据库的HibernateTemplate实现
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
@Transactional
public class BaseDaoImpl<T extends BaseEntity> extends HibernateDaoSupport implements IBaseDao<T> {
	private static final transient Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	private static final int MAX_QUERY_LIMIT;	// 最大的查询数据条数限制，默认10000
	private final Class<T> entityClass;	// dao所对应的实体类
	private final String entityClassName;	// 实体类名称，方便组装hql和打印日志使用
	private HibernateTemplate hibernateTemplate;	// hibernate的模板，在setSessionFactory方法中从父类获取
	
	
	static {
		MAX_QUERY_LIMIT = AppframeConfig.getMaxQueryLimit();
		logger.debug("init MAX_QUERY_LIMIT:" + MAX_QUERY_LIMIT);
	}
	public BaseDaoImpl(Class<T> clazz) throws AppframeException {
		entityClass = clazz;
		entityClassName = clazz.getName();
	}

	@Resource(name = "sessionFactory")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
		hibernateTemplate = getHibernateTemplate();
	}


	// ================ util =======================
	private String getParamStr(Map<String, Object> parameter) {
		StringBuilder result = new StringBuilder();
		
		if (null != parameter && !parameter.isEmpty()) {
			for (String name: parameter.keySet()) {
				result.append(name).append(':').append(parameter.get(name)).append(',');
			}
			result.deleteCharAt(result.length() - 1);
		}
		
		return result.toString();
	}
	private String getParamStr(String[] names, Object[] values) {
		StringBuilder result = new StringBuilder();
		
		if (null != names) {
			result.append(names[0]).append(':').append(values[0]);
			for (int i = 1; i < names.length; i++) {
				result.append(',').append(names[i]).append(':').append(values[i]);
			}
			result.deleteCharAt(result.length() - 1);
		}
		
		return result.toString();
	}
	
	// ================ add =======================
	@Override
	public T save(T entity) throws AppframeException {
		T result = entity;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " saving entity, {}.", entity);
			}
			hibernateTemplate.persist(entity);
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
			hibernateTemplate.delete(entity);
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
				hibernateTemplate.delete(entity);
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
	public int deleteEntities(String hqlWhere, final String[] names, final Object[] values) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entities, hqlWhere:[{}].", hqlWhere);
			}
			final String queryString = "delete from " + entityClassName + " where " + hqlWhere;
			result = hibernateTemplate.execute(
					new HibernateCallback<Integer>() {
						public Integer doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(queryString);
							if (null != names) {
								for (int i = 0; i < names.length; i++) {
									query.setParameter(names[i], values[i]);
								}
							}
							return query.executeUpdate();
						}
					}
			);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entity successful, hqlWhere:[{}].", hqlWhere);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " delete failed, hqlWhere:[{}; param:{}].", hqlWhere, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	public int deleteEntities(String hqlWhere, final Map<String, Object> parameter) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entities, hqlWhere:[{}].", hqlWhere);
			}
			final String queryString = "delete from " + entityClassName + " where " + hqlWhere;
			result = hibernateTemplate.execute(
					new HibernateCallback<Integer>() {
						public Integer doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(queryString);
							if (null != parameter) {
								for (String name: parameter.keySet()) {
									query.setParameter(name, parameter.get(name));
								}
							}
							return query.executeUpdate();
						}
					}
			);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entity successful, hqlWhere:[{}].", hqlWhere);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " delete failed, hqlWhere:[{}; param:{}].", hqlWhere, getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	public int deleteByFullHql(final String hql) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " deleting entities, hql:[{}].", hql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<Integer>() {
						public Integer doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(hql);
							return query.executeUpdate();
						}
					}
			);
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
				hibernateTemplate.merge(entity);
			} else {
				hibernateTemplate.save(entity);
//				save(entity);
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
	public int updateByFullHql(final String hql, final String[] names, final Object[] values) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " updateing entities, hql:[{}].", hql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<Integer>() {
						public Integer doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(hql);
							if (null != names) {
								for (int i = 0; i < names.length; i++) {
									query.setParameter(names[i], values[i]);
								}
							}
							return query.executeUpdate();
						}
					}
			);
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
	public int updateByFullHql(final String hql, final Map<String, Object> parameter) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " updateing entities, hql:[{}].", hql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<Integer>() {
						public Integer doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(hql);
							if (null != parameter) {
								for (String name: parameter.keySet()) {
									query.setParameter(name, parameter.get(name));
								}
							}
							return query.executeUpdate();
						}
					}
			);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " updateing entities successful, resultSize:[{}]." + result);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, hql:[{}; param:{}].", hql, getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	public int updateNativeSql(final String sql, final String[] names, final Object[] values) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " updating entities, sql:[{}].", sql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<Integer>() {
						public Integer doInHibernate(Session session) throws HibernateException {
							Query query = session.createSQLQuery(sql);
							if (null != names) {
								for (int i = 0; i < names.length; i++) {
									query.setParameter(names[i], values[i]);
								}
							}
							return query.executeUpdate();
						}
					}
			);
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
	public int updateNativeSql(final String sql, final Map<String, Object> parameter) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " updating entities, sql:[{}].", sql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<Integer>() {
						public Integer doInHibernate(Session session) throws HibernateException {
							Query query = session.createSQLQuery(sql);
							if (null != parameter) {
								for (String name: parameter.keySet()) {
									query.setParameter(name, parameter.get(name));
								}
							}
							return query.executeUpdate();
						}
					}
			);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " updating entities successful, resultSize:[{}]." + result);
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, sql:[{}; param:{}].", sql, getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	public List<Integer> updateNativeSqls(final String sql, final String[] names, final List<Object[]> valuesList) throws AppframeException {
		List<Integer> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " banth update, sql:[{}].", sql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<List<Integer>>() {
						public List<Integer> doInHibernate(Session session) throws HibernateException {
							List<Integer> dataList = null;
							FlushMode mode = session.getFlushMode();
							session.setFlushMode(FlushMode.MANUAL);
							Query query = session.createSQLQuery(sql);
							if (null != names) {
								dataList = new ArrayList<Integer>(valuesList.size());
								for (Object[] values: valuesList) {
									for (int i = 0; i < names.length; i++) {
										query.setParameter(names[i], values[i]);
									}
									int count = query.executeUpdate();
									dataList.add(count);
								}
							}
							session.setFlushMode(mode);
							return dataList;
						}
					}
			);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " banth update successful, update size:[{}]." + valuesList.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " update failed, sql:[{}; param:{}].", sql, getParamStr(names, valuesList.get(0)));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	
	// ================ query =======================
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public T getReference(long id) throws AppframeException {
		T result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entity reference with id:[{}].", id);
			}
			result = hibernateTemplate.load(entityClass, id);
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public T queryById(long id) throws AppframeException {
		T result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entity with id:[{}].", id);
			}
			result = hibernateTemplate.get(entityClass, id);
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public T queryEntity(final String hqlWhere) throws AppframeException {
		T result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
			}
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			List<T> list = hibernateTemplate.execute(
					new HibernateCallback<List<T>>() {
						public List<T> doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(queryString);
							query.setMaxResults(1);
							return query.list();
						}
					}
			);
			if (list.isEmpty()) {
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " entity not found, hqlWhere:[{}]", hqlWhere);
				}
			} else {
				result = (T) list.get(0);
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public T queryEntity(String hqlWhere, final String[] names, final Object[] values) throws AppframeException {
		T result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entity, hqlWhere:[{}]", hqlWhere);
			}
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			List<T> list = hibernateTemplate.execute(
					new HibernateCallback<List<T>>() {
						public List<T> doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(queryString);
							query.setMaxResults(1);
							if (null != names) {
								for (int i = 0; i < names.length; i++) {
									query.setParameter(names[i], values[i]);
								}
							}
							return query.list();
						}
					}
			);
			
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
			logger.error(entityClassName + " query failed, hqlWhere:[{}; param:{}].", hqlWhere, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public T queryEntity(String hqlWhere, final Map<String, Object> parameter) throws AppframeException {
		T result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entity, hqlWhere:[{}]", hqlWhere);
			}
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			List<T> list = hibernateTemplate.execute(
					new HibernateCallback<List<T>>() {
						public List<T> doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(queryString);
							query.setMaxResults(1);
							if (null != parameter) {
								for (String name: parameter.keySet()) {
									query.setParameter(name, parameter.get(name));
								}
							}
							return query.list();
						}
					}
			);
			
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
			logger.error(entityClassName + " query failed, hqlWhere:[{}; param:{}].", hqlWhere, getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public List<T> queryEntities() throws AppframeException {
		List<T> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying all entities");
			}
			final String queryString = "from " + entityClassName;
			result = hibernateTemplate.execute(
					new HibernateCallback<List<T>>() {
						public List<T> doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(queryString);
							query.setMaxResults(MAX_QUERY_LIMIT);
							return query.list();
						}
					}
			);
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(String hqlWhere) throws AppframeException {
		List<T> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying all entities, hqlWhere:[{}]", hqlWhere);
			}
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			result = hibernateTemplate.execute(
					new HibernateCallback<List<T>>() {
						public List<T> doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(queryString);
							query.setMaxResults(MAX_QUERY_LIMIT);
							return query.list();
						}
					}
			);
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(String hqlWhere, final int rowIndex, final int rowNum) throws AppframeException {
		List<T> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
			}
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			result = hibernateTemplate.execute(
					new HibernateCallback<List<T>>() {
						public List<T> doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(queryString);
							query.setFirstResult(1 < rowIndex ? rowIndex - 1: 0);	// 数据库从0开始计数，应用从1开始计数
							query.setMaxResults(MAX_QUERY_LIMIT < rowNum ? MAX_QUERY_LIMIT: rowNum);
							return query.list();
						}
					}
			);
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(String hqlWhere, final String[] names, final Object[] values) throws AppframeException {
		List<T> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
			}
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			result = hibernateTemplate.execute(
					new HibernateCallback<List<T>>() {
						public List<T> doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(queryString);
							query.setMaxResults(MAX_QUERY_LIMIT);
							if (null != names) {
								for (int i = 0; i < names.length; i++) {
									query.setParameter(names[i], values[i]);
								}
							}
							return query.list();
						}
					}
			);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hqlWhere:[{}; param:{}].", hqlWhere, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(String hqlWhere, final Map<String, Object> parameter) throws AppframeException {
		List<T> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
			}
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			result = hibernateTemplate.execute(
					new HibernateCallback<List<T>>() {
						public List<T> doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(queryString);
							query.setMaxResults(MAX_QUERY_LIMIT);
							if (null != parameter) {
								for (String name: parameter.keySet()) {
									query.setParameter(name, parameter.get(name));
								}
							}
							return query.list();
						}
					}
			);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hqlWhere:[{}; param:{}].", hqlWhere, getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(final String hqlWhere, final String[] names, final Object[] values, final int rowIndex, final int rowNum) throws AppframeException {
		List<T> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
			}
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			result = hibernateTemplate.execute(
					new HibernateCallback<List<T>>() {
						public List<T> doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(queryString);
							query.setFirstResult(1 < rowIndex ? rowIndex - 1: 0);	// 数据库从0开始计数，应用从1开始计数
							query.setMaxResults(MAX_QUERY_LIMIT < rowNum ? MAX_QUERY_LIMIT: rowNum);
							if (null != names) {
								for (int i = 0; i < names.length; i++) {
									query.setParameter(names[i], values[i]);
								}
							}
							return query.list();
						}
					}
			);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hqlWhere:[{}; param:{}].", hqlWhere, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(final String hqlWhere, final Map<String, Object> parameter, final int rowIndex, final int rowNum) throws AppframeException {
		List<T> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
			}
			final String queryString = "from " + entityClassName + " where " + hqlWhere;
			result = hibernateTemplate.execute(
					new HibernateCallback<List<T>>() {
						public List<T> doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(queryString);
							query.setFirstResult(1 < rowIndex ? rowIndex - 1: 0);	// 数据库从0开始计数，应用从1开始计数
							query.setMaxResults(MAX_QUERY_LIMIT < rowNum ? MAX_QUERY_LIMIT: rowNum);
							if (null != parameter) {
								for (String name: parameter.keySet()) {
									query.setParameter(name, parameter.get(name));
								}
							}
							return query.list();
						}
					}
			);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hqlWhere:[{}; param:{}].", hqlWhere, getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public DataContainer<T> queryEntities(final PageLoadConfig pageLoadCfg) throws AppframeException {
		DataContainer<T> result = new DataContainer<T>(pageLoadCfg);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying all entities");
			}
			int totalCount = countByFullHql("select count(*) from " + entityClassName, null);
			result.setTotalCount(totalCount);
			if (0 < totalCount) {
				String where = " ";
				if (null != pageLoadCfg) {
					where += pageLoadCfg.toSortSql();
				}
				final String queryString = "from " + entityClassName + where;
				List<T> dataList = hibernateTemplate.execute(
						new HibernateCallback<List<T>>() {
							public List<T> doInHibernate(Session session) throws HibernateException {
								Query query = session.createQuery(queryString);
								query.setFirstResult(pageLoadCfg.getOffset() - 1);	// 数据库从0开始计数，应用从1开始计数
								query.setMaxResults(MAX_QUERY_LIMIT < pageLoadCfg.getLimit() ? MAX_QUERY_LIMIT: pageLoadCfg.getLimit());
								return query.list();
							}
						}
				);
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public DataContainer<T> queryEntities(String hqlWhere, final PageLoadConfig pageLoadCfg) throws AppframeException {
		DataContainer<T> result = new DataContainer<T>(pageLoadCfg);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hqlWhere:[{}]", hqlWhere);
			}
			String where = " where " + hqlWhere;
			int totalCount = countByFullHql("select count(*) from " + entityClassName + where, null);
			result.setTotalCount(totalCount);
			if (0 < totalCount) {
				if (null != pageLoadCfg) {
					where += pageLoadCfg.toSortSql();
				}
				final String queryString = "from " + entityClassName + where;
				List<T> dataList = hibernateTemplate.execute(
						new HibernateCallback<List<T>>() {
							public List<T> doInHibernate(Session session) throws HibernateException {
								Query query = session.createQuery(queryString);
								query.setFirstResult(pageLoadCfg.getOffset() - 1);	// 数据库从0开始计数，应用从1开始计数
								query.setMaxResults(MAX_QUERY_LIMIT < pageLoadCfg.getLimit() ? MAX_QUERY_LIMIT: pageLoadCfg.getLimit());
								return query.list();
							}
						}
				);
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public DataContainer<T> queryEntities(String hqlWhere, final String[] names, final Object[] values, final PageLoadConfig pageLoadCfg) throws AppframeException {
		DataContainer<T> result = new DataContainer<T>(pageLoadCfg);
		
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
				List<T> dataList = hibernateTemplate.execute(
						new HibernateCallback<List<T>>() {
							public List<T> doInHibernate(Session session) throws HibernateException {
								Query query = session.createQuery(queryString);
								query.setFirstResult(pageLoadCfg.getOffset() - 1);	// 数据库从0开始计数，应用从1开始计数
								query.setMaxResults(MAX_QUERY_LIMIT < pageLoadCfg.getLimit() ? MAX_QUERY_LIMIT: pageLoadCfg.getLimit());
								if (null != names) {
									for (int i = 0; i < names.length; i++) {
										query.setParameter(names[i], values[i]);
									}
								}
								return query.list();
							}
						}
				);
				result.setDataList(dataList);
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " querying entities successful, result size:[{}]." + dataList.size());
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hqlWhere:[{}; param:{}].", hqlWhere, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public DataContainer<T> queryEntities(String hqlWhere, final Map<String, Object> parameter, final PageLoadConfig pageLoadCfg) throws AppframeException {
		DataContainer<T> result = new DataContainer<T>(pageLoadCfg);
		
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
				List<T> dataList = hibernateTemplate.execute(
						new HibernateCallback<List<T>>() {
							public List<T> doInHibernate(Session session) throws HibernateException {
								Query query = session.createQuery(queryString);
								query.setFirstResult(pageLoadCfg.getOffset() - 1);	// 数据库从0开始计数，应用从1开始计数
								query.setMaxResults(MAX_QUERY_LIMIT < pageLoadCfg.getLimit() ? MAX_QUERY_LIMIT: pageLoadCfg.getLimit());
								if (null != parameter) {
									for (String name: parameter.keySet()) {
										query.setParameter(name, parameter.get(name));
									}
								}
								return query.list();
							}
						}
				);
				result.setDataList(dataList);
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " querying entities successful, result size:[{}]." + dataList.size());
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hqlWhere:[{}; param:{}].", hqlWhere, getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	
	// ################################################################
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public int countByFullHql(final String hql, final String[] names, final Object[] values) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " count, hql:[{}]", hql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<Integer>() {
						public Integer doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(hql);
							query.setMaxResults(1);
							if (null != names) {
								for (int i = 0; i < names.length; i++) {
									query.setParameter(names[i], values[i]);
								}
							}
							List<Long> dataList = query.list();
							if (null == dataList || dataList.isEmpty()) {
								return 0;
							}
							return DataTypeUtil.getAsInteger(dataList.get(0));
						}
					}
			);
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public int countByFullHql(final String hql, final Map<String, Object> parameter) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " count, hql:[{}]", hql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<Integer>() {
						public Integer doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(hql);
							query.setMaxResults(1);
							if (null != parameter) {
								for (String name: parameter.keySet()) {
									query.setParameter(name, parameter.get(name));
								}
							}
							List<Long> dataList = query.list();
							if (null == dataList || dataList.isEmpty()) {
								return 0;
							}
							return DataTypeUtil.getAsInteger(dataList.get(0));
						}
					}
			);
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public int countNativeSql(final String sql, final String[] names, final Object[] values) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " count, sql:[{}]", sql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<Integer>() {
						public Integer doInHibernate(Session session) throws HibernateException {
							Query query = session.createSQLQuery(sql);
							query.setMaxResults(1);
							if (null != names) {
								for (int i = 0; i < names.length; i++) {
									query.setParameter(names[i], values[i]);
								}
							}
							List<Long> dataList = query.list();
							if (null == dataList || dataList.isEmpty()) {
								return 0;
							}
							return DataTypeUtil.getAsInteger(dataList.get(0));
						}
					}
			);
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public int countNativeSql(final String sql, final Map<String, Object> parameter) throws AppframeException {
		int result = 0;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " count, sql:[{}]", sql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<Integer>() {
						public Integer doInHibernate(Session session) throws HibernateException {
							Query query = session.createSQLQuery(sql);
							query.setMaxResults(1);
							if (null != parameter) {
								for (String name: parameter.keySet()) {
									query.setParameter(name, parameter.get(name));
								}
							}
							List<Long> dataList = query.list();
							if (null == dataList || dataList.isEmpty()) {
								return 0;
							}
							return DataTypeUtil.getAsInteger(dataList.get(0));
						}
					}
			);
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("rawtypes")
	public List queryByFullHql(final String hql) throws AppframeException {
		List result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<List>() {
						public List doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(hql);
							query.setMaxResults(MAX_QUERY_LIMIT);
							return query.list();
						}
					}
			);
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("rawtypes")
	public List queryByFullHql(final String hql, final int rowIndex, final int rowNum) throws AppframeException {
		List result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<List>() {
						public List doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(hql);
							query.setFirstResult(1 < rowIndex ? rowIndex - 1: 0);	// 数据库从0开始计数，应用从1开始计数
							query.setMaxResults(MAX_QUERY_LIMIT < rowNum ? MAX_QUERY_LIMIT: rowNum);
							return query.list();
						}
					}
			);
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("rawtypes")
	public List queryByFullHql(final String hql, final String[] names, final Object[] values) throws AppframeException {
		List result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<List>() {
						public List doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(hql);
							query.setMaxResults(MAX_QUERY_LIMIT);
							if (null != names) {
								for (int i = 0; i < names.length; i++) {
									query.setParameter(names[i], values[i]);
								}
							}
							return query.list();
						}
					}
			);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}; param:{}].", hql, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("rawtypes")
	public List queryByFullHql(final String hql, final Map<String, Object> parameter) throws AppframeException {
		List result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<List>() {
						public List doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(hql);
							query.setMaxResults(MAX_QUERY_LIMIT);
							if (null != parameter) {
								for (String name: parameter.keySet()) {
									query.setParameter(name, parameter.get(name));
								}
							}
							return query.list();
						}
					}
			);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}; param:{}].", hql, getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	/**
	 * 因为对hql无约束， 可能返回结果的类型不是安全的
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("rawtypes")
	public List queryByFullHql(final String hql, final String[] names, final Object[] values, final int rowIndex, final int rowNum) throws AppframeException {
		List result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<List>() {
						public List doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(hql);
							query.setFirstResult(1 < rowIndex ? rowIndex - 1: 0);	// 数据库从0开始计数，应用从1开始计数
							query.setMaxResults(MAX_QUERY_LIMIT < rowNum ? MAX_QUERY_LIMIT: rowNum);
							if (null != names) {
								for (int i = 0; i < names.length; i++) {
									query.setParameter(names[i], values[i]);
								}
							}
							return query.list();
						}
					}
			);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}; param:{}].", hql, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	/**
	 * 因为对hql无约束， 可能返回结果的类型不是安全的
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("rawtypes")
	public List queryByFullHql(final String hql, final Map<String, Object> parameter, final int rowIndex, final int rowNum) throws AppframeException {
		List result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<List>() {
						public List doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(hql);
							query.setFirstResult(1 < rowIndex ? rowIndex - 1: 0);	// 数据库从0开始计数，应用从1开始计数
							query.setMaxResults(MAX_QUERY_LIMIT < rowNum ? MAX_QUERY_LIMIT: rowNum);
							if (null != parameter) {
								for (String name: parameter.keySet()) {
									query.setParameter(name, parameter.get(name));
								}
							}
							return query.list();
						}
					}
			);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}; param:{}].", hql, getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("rawtypes")
	public List queryNativeSql(final String sql, final String[] names, final Object[] values, final int rowIndex, final int rowNum) throws AppframeException {
		List result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, sql:[{}]", sql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<List>() {
						public List doInHibernate(Session session) throws HibernateException {
							Query query = session.createSQLQuery(sql);
							query.setFirstResult(0 < rowIndex ? rowIndex - 1: 0);	// 数据库从0开始计数，应用从1开始计数
							query.setMaxResults(MAX_QUERY_LIMIT < rowNum ? MAX_QUERY_LIMIT: rowNum);
							if (null != names) {
								for (int i = 0; i < names.length; i++) {
									query.setParameter(names[i], values[i]);
								}
							}
							return query.list();
						}
					}
			);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, sql:[{}; param:{}].", sql, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("rawtypes")
	public List queryNativeSql(final String sql, final Map<String, Object> parameter, final int rowIndex, final int rowNum) throws AppframeException {
		List result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, sql:[{}]", sql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<List>() {
						public List doInHibernate(Session session) throws HibernateException {
							Query query = session.createSQLQuery(sql);
							query.setFirstResult(0 < rowIndex ? rowIndex - 1: 0);	// 数据库从0开始计数，应用从1开始计数
							query.setMaxResults(MAX_QUERY_LIMIT < rowNum ? MAX_QUERY_LIMIT: rowNum);
							if (null != parameter) {
								for (String name: parameter.keySet()) {
									query.setParameter(name, parameter.get(name));
								}
							}
							return query.list();
						}
					}
			);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, sql:[{}; param:{}].", sql, getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List queryByFullHqlNoLimit(final String hql, final String[] names, final Object[] values, final int rowIndex, final int rowNum) throws AppframeException {
		List<T> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<List>() {
						public List doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(hql);
							query.setFirstResult(1 < rowIndex ? rowIndex - 1: 0);	// 数据库从0开始计数，应用从1开始计数
							query.setMaxResults(MAX_QUERY_LIMIT < rowNum ? MAX_QUERY_LIMIT: rowNum);
							if (null != names) {
								for (int i = 0; i < names.length; i++) {
									query.setParameter(names[i], values[i]);
								}
							}
							return query.list();
						}
					}
			);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}; param:{}].", hql, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List queryByFullHqlNoLimit(final String hql, final Map<String, Object> parameter, final int rowIndex, final int rowNum) throws AppframeException {
		List<T> result = null;
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			result = hibernateTemplate.execute(
					new HibernateCallback<List>() {
						public List doInHibernate(Session session) throws HibernateException {
							Query query = session.createQuery(hql);
							query.setFirstResult(1 < rowIndex ? rowIndex - 1: 0);	// 数据库从0开始计数，应用从1开始计数
							query.setMaxResults(MAX_QUERY_LIMIT < rowNum ? MAX_QUERY_LIMIT: rowNum);
							if (null != parameter) {
								for (String name: parameter.keySet()) {
									query.setParameter(name, parameter.get(name));
								}
							}
							return query.list();
						}
					}
			);
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities successful, result size:[{}]." + result.size());
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, hql:[{}; param:{}].", hql, getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings({"rawtypes", "unchecked"})
	public DataContainer queryByFullHql(String hql, final PageLoadConfig pageLoadCfg) throws AppframeException {
		DataContainer result = new DataContainer(pageLoadCfg);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			String hqlCount = "select count(*) from (" + hql + ")";
			int totalCount = countByFullHql(hqlCount, null);
			result.setTotalCount(totalCount);
			if (0 < totalCount) {
				if (null != pageLoadCfg) {
					hql += " " + pageLoadCfg.toSortSql();
				}
				final String queryString = hql;
				List dataList = hibernateTemplate.execute(
						new HibernateCallback<List>() {
							public List doInHibernate(Session session) throws HibernateException {
								Query query = session.createQuery(queryString);
								query.setFirstResult(pageLoadCfg.getOffset() - 1);	// 数据库从0开始计数，应用从1开始计数
								query.setMaxResults(MAX_QUERY_LIMIT < pageLoadCfg.getLimit() ? MAX_QUERY_LIMIT: pageLoadCfg.getLimit());
								return query.list();
							}
						}
				);
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings({"rawtypes", "unchecked"})
	public DataContainer queryByFullHql(String hql, final String[] names, final Object[] values, final PageLoadConfig pageLoadCfg) throws AppframeException {
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
				final String queryString = hql;
				List dataList = hibernateTemplate.execute(
						new HibernateCallback<List>() {
							public List doInHibernate(Session session) throws HibernateException {
								Query query = session.createQuery(queryString);
								query.setFirstResult(pageLoadCfg.getOffset() - 1);	// 数据库从0开始计数，应用从1开始计数
								query.setMaxResults(MAX_QUERY_LIMIT < pageLoadCfg.getLimit() ? MAX_QUERY_LIMIT: pageLoadCfg.getLimit());
								if (null != names) {
									for (int i = 0; i < names.length; i++) {
										query.setParameter(names[i], values[i]);
									}
								}
								return query.list();
							}
						}
				);
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings({"rawtypes", "unchecked"})
	public DataContainer queryByFullHql(String hql, final Map<String, Object> parameter, final PageLoadConfig pageLoadCfg) throws AppframeException {
		DataContainer result = new DataContainer(pageLoadCfg);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, hql:[{}]", hql);
			}
			String hqlCount = "select count(*) from (" + hql + ")";
			int totalCount = countByFullHql(hqlCount, parameter);
			result.setTotalCount(totalCount);
			if (0 < totalCount) {
				if (null != pageLoadCfg) {
					hql += " " + pageLoadCfg.toSortSql();
				}
				final String queryString = hql;
				List dataList = hibernateTemplate.execute(
						new HibernateCallback<List>() {
							public List doInHibernate(Session session) throws HibernateException {
								Query query = session.createQuery(queryString);
								query.setFirstResult(pageLoadCfg.getOffset() - 1);	// 数据库从0开始计数，应用从1开始计数
								query.setMaxResults(MAX_QUERY_LIMIT < pageLoadCfg.getLimit() ? MAX_QUERY_LIMIT: pageLoadCfg.getLimit());
								if (null != parameter) {
									for (String name: parameter.keySet()) {
										query.setParameter(name, parameter.get(name));
									}
								}
								return query.list();
							}
						}
				);
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings({"rawtypes", "unchecked"})
	public DataContainer queryNativeSql(final String sql, final String[] names, final Object[] values, final PageLoadConfig pageLoadCfg) throws AppframeException {
		DataContainer result = new DataContainer(pageLoadCfg);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, sql:[{}]", sql);
			}
			String sqlCount = "select count(*) from (" + sql + ")";
			int totalCount = countNativeSql(sqlCount, names, values);
			result.setTotalCount(totalCount);
			if (0 < totalCount) {
				List dataList = hibernateTemplate.execute(
						new HibernateCallback<List>() {
							public List doInHibernate(Session session) throws HibernateException {
								Query query = session.createSQLQuery(sql);
								query.setFirstResult(pageLoadCfg.getOffset() - 1);	// 数据库从0开始计数，应用从1开始计数
								query.setMaxResults(MAX_QUERY_LIMIT < pageLoadCfg.getLimit() ? MAX_QUERY_LIMIT: pageLoadCfg.getLimit());
								if (null != names) {
									for (int i = 0; i < names.length; i++) {
										query.setParameter(names[i], values[i]);
									}
								}
								return query.list();
							}
						}
				);
				result.setDataList(dataList);
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " querying entities successful, result size:[{}]." + dataList.size());
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, sql:[{}; param:{}].", sql, getParamStr(names, values));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings({"rawtypes", "unchecked"})
	public DataContainer queryNativeSql(final String sql, final Map<String, Object> parameter, final PageLoadConfig pageLoadCfg) throws AppframeException {
		DataContainer result = new DataContainer(pageLoadCfg);
		
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " querying entities, sql:[{}]", sql);
			}
			String sqlCount = "select count(*) from (" + sql + ")";
			int totalCount = countNativeSql(sqlCount, parameter);
			result.setTotalCount(totalCount);
			if (0 < totalCount) {
				List dataList = hibernateTemplate.execute(
						new HibernateCallback<List>() {
							public List doInHibernate(Session session) throws HibernateException {
								Query query = session.createSQLQuery(sql);
								query.setFirstResult(pageLoadCfg.getOffset() - 1);	// 数据库从0开始计数，应用从1开始计数
								query.setMaxResults(MAX_QUERY_LIMIT < pageLoadCfg.getLimit() ? MAX_QUERY_LIMIT: pageLoadCfg.getLimit());
								if (null != parameter) {
									for (String name: parameter.keySet()) {
										query.setParameter(name, parameter.get(name));
									}
								}
								return query.list();
							}
						}
				);
				result.setDataList(dataList);
				if (logger.isDebugEnabled()) {
					logger.debug(entityClassName + " querying entities successful, result size:[{}]." + dataList.size());
				}
			}
		} catch (Exception e) {
			logger.error(entityClassName + " query failed, sql:[{}; param:{}].", sql, getParamStr(parameter));
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
		
		return result;
	}
	
	
	// ================ other =======================
	@Override
	public void flush() throws AppframeException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(entityClassName + " flushing.");
			}
			hibernateTemplate.flush();
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
			hibernateTemplate.clear();
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
			hibernateTemplate.merge(entity);
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
