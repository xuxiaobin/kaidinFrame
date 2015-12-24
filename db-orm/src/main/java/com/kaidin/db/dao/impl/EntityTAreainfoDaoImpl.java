package com.kaidin.db.dao.impl;
// Generated 2015-12-12 17:00:21 by Hibernate Tools 3.3.0.GA
import org.springframework.stereotype.Repository;

import com.kaidin.appframe.service.impl.BaseDaoImpl;
import com.kaidin.db.dao.interfaces.IEntityTAreainfoDao;
import com.kaidin.db.entity.EntityTAreainfo;
/**
 * Dao implements for domain model class EntityTAreainfo.
 * @see com.kaidin.db.dao.impl.EntityTAreainfo
 */
@Repository(value="IEntityTAreainfoDao")
public class EntityTAreainfoDaoImpl extends BaseDaoImpl<EntityTAreainfo> implements IEntityTAreainfoDao {

	public EntityTAreainfoDaoImpl() throws Exception {
		super(EntityTAreainfo.class);
	}
}
