package com.kaidin.appframe.dao;

import com.kaidin.appframe.entity.BaseEntity;

public class AppDao<T extends BaseEntity> extends BaseDaoImpl<T> implements IBaseDao<T> {

	public AppDao(Class<T> clazz, IDaoContext aDaoContext) throws Exception {
		super(clazz, aDaoContext);
	}

	public AppDao(String jndiName, Class<T> clazz, IDaoContext aDaoContext) throws Exception {
		super(jndiName, clazz, aDaoContext);
	}
}
