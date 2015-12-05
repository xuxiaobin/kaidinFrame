package com.kaidin.db.dao.impl;
// Generated 2015-10-21 10:09:50 by Hibernate Tools 3.3.0.GA
import org.springframework.stereotype.Repository;

import com.kaidin.appframe.dao.impl.BaseDaoImpl;
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
