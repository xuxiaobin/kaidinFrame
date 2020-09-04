package com.kaidin.appframe.service.dao.impl;

import com.kaidin.appframe.exception.AppframeException;
import com.kaidin.appframe.service.dao.BaseEntity;
import com.kaidin.appframe.service.dao.IBaseHibernateDao;
import com.kaidin.common.util.collection.CollectionUtil;
import com.kaidin.common.util.query.PageData;
import com.kaidin.common.util.query.PageRequest;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 操作数据库的hibernate实现
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-6-23下午01:51:48
 */
public class BaseHibernateDaoImpl<T extends BaseEntity> extends BaseDaoHibernateImpl<T> implements IBaseHibernateDao<T> {

	public BaseHibernateDaoImpl(Class<T> clazz) {
		super(clazz);
	}

	// ==================== add ==========================

	// =================== delete ==========================
	@Override
	public int deleteEntities(String hqlWhere, Map<String, Object> parameter) throws AppframeException {
		String hql = "delete " + fromTableWhere + hqlWhere;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return BaseDaoHelper.update(query, parameter);
	}

	@Override
	public int deleteEntities(String hqlWhere, String[] names, Object[] values) throws AppframeException {
		String hql = "delete " + fromTableWhere + hqlWhere;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return BaseDaoHelper.update(query, names, values);
	}

	@Override
	public int deleteByFullHql(String hql, Map<String, Object> parameter) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return BaseDaoHelper.update(query, parameter);
	}

	@Override
	public int deleteByFullHql(String hql, String[] names, Object[] values) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return BaseDaoHelper.update(query, names, values);
	}

	// ================ update =======================
	@Override
	public int updateByFullHql(String hql, Map<String, Object> parameter) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return BaseDaoHelper.update(query, parameter);
	}

	@Override
	public int updateByFullHql(String hql, String[] names, Object[] values) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return BaseDaoHelper.update(query, names, values);
	}

	@Override
	public int updateNativeSql(String sql, Map<String, Object> parameter) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		return BaseDaoHelper.update(query, parameter);
	}
	@Override
	public int updateNativeSql(String sql, String[] names, Object[] values) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		return BaseDaoHelper.update(query, names, values);
	}

	@Override
	public List<Integer> updateNativeSqls(String sql, String[] names, List<Object[]> valuesList) throws AppframeException {
		List<Integer> result = null;

		Session session = sessionFactory.getCurrentSession();
		FlushMode orgMode = session.getFlushMode();
		session.setFlushMode(FlushMode.MANUAL);
		Query query = session.createQuery(sql);
		if (null != valuesList) {
			result = new ArrayList<>(valuesList.size());
			for (Object[] values : valuesList) {
				result.add(BaseDaoHelper.update(query, names, values));
			}
		}
		session.setFlushMode(orgMode);

		return result;
	}

	// ================= query count =======================
	@Override
	public int countEntityies(String hqlWhere, Map<String, Object> parameter) throws AppframeException {
		String hql = "select count(1) " + fromTableWhere + hqlWhere;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return BaseDaoHelper.count(query, parameter);
	}

	@Override
	public int countEntityies(String hqlWhere, String[] names, Object[] values) throws AppframeException {
		String hql = "select count(1) " + fromTableWhere + hqlWhere;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		BaseDaoHelper.setNamedValue(query, names, values);
		return BaseDaoHelper.count(query, null);
	}
	@Override
	public int countByFullHql(String hql, Map<String, Object> parameter) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return BaseDaoHelper.count(query, parameter);
	}

	@Override
	public int countByFullHql(String hql, String[] names, Object[] values) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		BaseDaoHelper.setNamedValue(query, names, values);
		return BaseDaoHelper.count(query, null);
	}

	@Override
	public int countNativeSql(String sql, Map<String, Object> parameter) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		return BaseDaoHelper.count(query, parameter);
	}

	@Override
	public int countNativeSql(String sql, String[] names, Object[] values) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		BaseDaoHelper.setNamedValue(query, names, values);
		return BaseDaoHelper.count(query, null);
	}

	// ================ query =======================
	@Override
	@SuppressWarnings("unchecked")
	public T getReference(long id) throws AppframeException {
		return (T) sessionFactory.getCurrentSession().load(entityClass, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T queryEntity(String hqlWhere) throws AppframeException {
		String hql = fromTableWhere + hqlWhere;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<T> list = BaseDaoHelper.list(query, null, null);
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}

		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T queryEntity(String hqlWhere, Map<String, Object> parameter) throws AppframeException {
		String hql = fromTableWhere + hqlWhere;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<T> list = BaseDaoHelper.list(query, parameter, null);
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}

		return null;
	}
	@Override
	@SuppressWarnings("unchecked")
	public T queryEntity(String hqlWhere, String[] names, Object[] values) throws AppframeException {
		String hql = fromTableWhere + hqlWhere;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		BaseDaoHelper.setNamedValue(query, names, values);
		List<T> list = BaseDaoHelper.list(query, null, null);
		if (CollectionUtil.isNotEmpty(list)) {
			return list.get(0);
		}

		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryEntities() throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(fromTableWhere);
		return BaseDaoHelper.list(query, null, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(String hqlWhere) throws AppframeException {
		String hql = fromTableWhere + hqlWhere;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return BaseDaoHelper.list(query, null, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(String hqlWhere, int rowIndex, int rowNum) throws AppframeException {
		String hql = fromTableWhere + hqlWhere;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return BaseDaoHelper.list(query, null, rowIndex, rowNum);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(String hqlWhere, Map<String, Object> parameter) throws AppframeException {
		String hql = fromTableWhere + hqlWhere;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return BaseDaoHelper.list(query, parameter, null);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(String hqlWhere, String[] names, Object[] values) throws AppframeException {
		String hql = fromTableWhere + hqlWhere;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		BaseDaoHelper.setNamedValue(query, names, values);
		return BaseDaoHelper.list(query, null, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(String hqlWhere, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException {
		String hql = fromTableWhere + hqlWhere;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return BaseDaoHelper.list(query, parameter, rowIndex, rowNum);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<T> queryEntities(String hqlWhere, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException {
		String hql = fromTableWhere + hqlWhere;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		BaseDaoHelper.setNamedValue(query, names, values);
		return BaseDaoHelper.list(query, null, rowIndex, rowNum);
	}

	@Override
	@SuppressWarnings("unchecked")
	public PageData<T> queryEntities(PageRequest pageReq) throws AppframeException {
		PageData<T> result = new PageData<>(pageReq);

		int totalCount = countByFullHql("select count(1) " + fromTableWhere, null);
		result.setTotalCount(totalCount);
		if (0 == totalCount || (null != pageReq && pageReq.getOffset() > totalCount)) {
			return result;
		}
		String where = " ";
		if (null != pageReq) {
			where += pageReq.toSortSql();
		}
		String hql = fromTableWhere + where;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<T> dataList = BaseDaoHelper.list(query, null, pageReq);
		result.setDataList(dataList);

		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PageData<T> queryEntities(String hqlWhere, PageRequest pageReq) throws AppframeException {
		PageData<T> result = new PageData<>(pageReq);

		int totalCount = countEntityies(hqlWhere, null);
		result.setTotalCount(totalCount);
		if (0 == totalCount || (null != pageReq && pageReq.getOffset() > totalCount)) {
			return result;
		}

		if (null != pageReq) {
			hqlWhere += pageReq.toSortSql();
		}
		String hql = fromTableWhere + hqlWhere;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<T> dataList = BaseDaoHelper.list(query, null, pageReq);
		result.setDataList(dataList);

		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PageData<T> queryEntities(String hqlWhere, Map<String, Object> parameter, PageRequest pageReq) throws AppframeException {
		PageData<T> result = new PageData<>(pageReq);

		int totalCount = countEntityies(hqlWhere, parameter);
		result.setTotalCount(totalCount);
		if (0 == totalCount || (null != pageReq && pageReq.getOffset() > totalCount)) {
			return result;
		}

		if (null != pageReq) {
			hqlWhere += pageReq.toSortSql();
		}
		String hql = fromTableWhere + hqlWhere;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<T> dataList = BaseDaoHelper.list(query, parameter, pageReq);
		result.setDataList(dataList);

		return result;
	}
	@Override
	@SuppressWarnings("unchecked")
	public PageData<T> queryEntities(String hqlWhere, String[] names, Object[] values, PageRequest pageReq) throws AppframeException {
		PageData<T> result = new PageData<>(pageReq);

		int totalCount = countEntityies(hqlWhere, names, values);
		result.setTotalCount(totalCount);
		if (0 == totalCount || (null != pageReq && pageReq.getOffset() > totalCount)) {
			return result;
		}
		if (null != pageReq) {
			hqlWhere += pageReq.toSortSql();
		}
		String hql = fromTableWhere + hqlWhere;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		BaseDaoHelper.setNamedValue(query, names, values);
		List<T> dataList = BaseDaoHelper.list(query, null, pageReq);
		result.setDataList(dataList);

		return result;
	}

	/**
	 * 因为对hql无约束， 可能返回结果的类型不是安全的
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List queryByFullHql(String hql) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(fromTableWhere);
		return BaseDaoHelper.list(query, null, null);
	}

	/**
	 * 因为对hql无约束， 可能返回结果的类型不是安全的
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List queryByFullHql(String hql, int rowIndex, int rowNum) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return BaseDaoHelper.list(query, null, rowIndex, rowNum);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List queryByFullHql(String hql, Map<String, Object> parameter) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return BaseDaoHelper.list(query, parameter, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List queryByFullHql(String hql, String[] names, Object[] values) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		BaseDaoHelper.setNamedValue(query, names, values);
		return BaseDaoHelper.list(query, null, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List queryByFullHql(String hql, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return BaseDaoHelper.list(query, parameter, rowIndex, rowNum);
	}

	/**
	 * 因为对hql无约束， 可能返回结果的类型不是安全的
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List queryByFullHql(String hql, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		BaseDaoHelper.setNamedValue(query, names, values);
		return BaseDaoHelper.list(query, null, rowIndex, rowNum);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List queryNativeSql(String sql, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		return BaseDaoHelper.list(query, parameter, rowIndex, rowNum);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List queryNativeSql(String sql, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		BaseDaoHelper.setNamedValue(query, names, values);
		return BaseDaoHelper.list(query, null, rowIndex, rowNum);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List queryByFullHqlNoLimit(String hql, Map<String, Object> parameter, int rowIndex, int rowNum) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query = BaseDaoHelper.setNamedValue(query, parameter);
		query = BaseDaoHelper.setRowsLimit(query, rowIndex, rowNum);
		query.setMaxResults(rowNum);

		return query.list();
	}
	@Override
	@SuppressWarnings("unchecked")
	public List queryByFullHqlNoLimit(String hql, String[] names, Object[] values, int rowIndex, int rowNum) throws AppframeException {
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query = BaseDaoHelper.setNamedValue(query, names, values);
		query = BaseDaoHelper.setRowsLimit(query, rowIndex, rowNum);
		query.setMaxResults(rowNum);

		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public PageData queryByFullHql(String hql, PageRequest pageReq) throws AppframeException {
		PageData result = new PageData(pageReq);

		int totalCount = countByFullHql("select count(1) from (" + hql + ")", null);
		result.setTotalCount(totalCount);
		if (0 == totalCount || (null != pageReq && pageReq.getOffset() > totalCount)) {
			return result;
		}
		if (null != pageReq) {
			hql += pageReq.toSortSql();
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List dataList = BaseDaoHelper.list(query, null, pageReq);
		result.setDataList(dataList);

		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PageData queryByFullHql(String hql, Map<String, Object> parameter, PageRequest pageReq) throws AppframeException {
		PageData result = new PageData(pageReq);

		int totalCount = countByFullHql("select count(1) from (" + hql + ")", parameter);
		result.setTotalCount(totalCount);
		if (0 == totalCount || (null != pageReq && pageReq.getOffset() > totalCount)) {
			return result;
		}
		if (null != pageReq) {
			hql += pageReq.toSortSql();
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List dataList = BaseDaoHelper.list(query, parameter, pageReq);
		result.setDataList(dataList);

		return result;
	}
	@Override
	@SuppressWarnings("unchecked")
	public PageData queryByFullHql(String hql, String[] names, Object[] values, PageRequest pageReq) throws AppframeException {
		PageData result = new PageData<>(pageReq);

		int totalCount = countByFullHql("select count(1) from (" + hql +")", names, values);
		result.setTotalCount(totalCount);
		if (0 == totalCount || (null != pageReq && pageReq.getOffset() > totalCount)) {
			return result;
		}
		if (null != pageReq) {
			hql += pageReq.toSortSql();
		}
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query = BaseDaoHelper.setNamedValue(query, names, values);
		List dataList = BaseDaoHelper.list(query, null, pageReq);
		result.setDataList(dataList);

		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PageData queryNativeSql(String sql, Map<String, Object> parameter, PageRequest pageReq) throws AppframeException {
		PageData result = new PageData<>(pageReq);

		int totalCount = countNativeSql("select count(1) from (" + sql + ")", parameter);
		result.setTotalCount(totalCount);
		if (0 == totalCount || (null != pageReq && pageReq.getOffset() > totalCount)) {
			return result;
		}
		if (null != pageReq) {
			sql += pageReq.toSortSql();
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		List dataList = BaseDaoHelper.list(query, parameter, pageReq);
		result.setDataList(dataList);

		return result;
	}
	@Override
	@SuppressWarnings("unchecked")
	public PageData queryNativeSql(String sql, String[] names, Object[] values, PageRequest pageReq) throws AppframeException {
		PageData result = new PageData(pageReq);

		int totalCount = countNativeSql("select count(1) from (" + sql +")", names, values);
		result.setTotalCount(totalCount);
		if (0 == totalCount || (null != pageReq && pageReq.getOffset() > totalCount)) {
			return result;
		}
		if (null != pageReq) {
			sql += pageReq.toSortSql();
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query = BaseDaoHelper.setNamedValue(query, names, values);
		List dataList = BaseDaoHelper.list(query, null, pageReq);
		result.setDataList(dataList);

		return result;
	}

	// =================== other ==========================
}
