package com.kaidin.db.dao.impl;
// Generated 2017-5-29 14:00:59 by Hibernate Tools 3.3.0.GA
import org.springframework.stereotype.Repository;

import com.kaidin.appframe.service.impl.BaseDaoSpringImpl;
import com.kaidin.db.dao.interfaces.IEntityCfgUserDao;
import com.kaidin.db.entity.EntityCfgUser;
/**
 * Dao implements for domain model class EntityCfgUser.
 * @see com.kaidin.db.dao.impl.EntityCfgUser
 */
@Repository(value="IEntityCfgUserDao")
public class EntityCfgUserDaoImpl extends BaseDaoSpringImpl<EntityCfgUser> implements IEntityCfgUserDao {

	public EntityCfgUserDaoImpl() throws Exception {
		super(EntityCfgUser.class);
	}
}
