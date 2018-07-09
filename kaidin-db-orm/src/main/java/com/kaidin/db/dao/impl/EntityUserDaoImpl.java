package com.kaidin.db.dao.impl;
// Generated 2017-5-29 14:00:59 by Hibernate Tools 3.3.0.GA
import org.springframework.stereotype.Repository;

import com.kaidin.appframe.service.impl.BaseDaoSpringImpl;
import com.kaidin.db.dao.interfaces.IEntityUserDao;
import com.kaidin.db.entity.EntityUser;
/**
 * Dao implements for domain model class EntityUser.
 * @see com.kaidin.db.dao.impl.EntityUser
 */
@Repository(value="IEntityUserDao")
public class EntityUserDaoImpl extends BaseDaoSpringImpl<EntityUser> implements IEntityUserDao {

	public EntityUserDaoImpl() throws Exception {
		super(EntityUser.class);
	}
}
