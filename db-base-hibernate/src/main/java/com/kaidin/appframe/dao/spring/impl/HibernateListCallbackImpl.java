package com.kaidin.appframe.dao.spring.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.kaidin.appframe.config.AppframeConfig;
import com.kaidin.common.util.query.PageLoadConfig;

public class HibernateListCallbackImpl<T> implements HibernateCallback<T> {
	private String queryString;
	private final String[] names;
	private final Object[] values;
	private final PageLoadConfig pageLoadCfg;
	
	
	public HibernateListCallbackImpl(String queryString, String[] names, Object[] values, PageLoadConfig pageLoadCfg) {
		this.queryString = queryString;
		this.names = names;
		this.values = values;
		this.pageLoadCfg = pageLoadCfg;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public T doInHibernate(Session session) throws HibernateException, SQLException {
		Query query = session.createQuery(queryString);
		query.setFirstResult(pageLoadCfg.getOffset() - 1);	// 数据库从0开始计数，应用从1开始计数
		int rowNum = pageLoadCfg.getLimit();
		rowNum = AppframeConfig.getMaxQueryLimit() < rowNum ? AppframeConfig.getMaxQueryLimit(): rowNum;
		query.setMaxResults(rowNum);
		if (null != names) {
			for (int i = 0; i < names.length; i++) {
				query.setParameter(names[i], values[i]);
			}
		}
		return (T) query.list();
	}
}
