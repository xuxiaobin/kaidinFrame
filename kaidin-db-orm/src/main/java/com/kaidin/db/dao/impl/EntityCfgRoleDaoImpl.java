package com.kaidin.db.dao.impl;
// Generated 2017-5-29 14:00:59 by Hibernate Tools 3.3.0.GA
import org.springframework.stereotype.Repository;

import com.kaidin.appframe.service.impl.BaseDaoSpringImpl;
import com.kaidin.db.dao.interfaces.IEntityCfgRoleDao;
import com.kaidin.db.entity.EntityCfgRole;
/**
 * Dao implements for domain model class EntityCfgRole.
 * @see com.kaidin.db.dao.impl.EntityCfgRole
 */
@Repository(value="IEntityCfgRoleDao")
public class EntityCfgRoleDaoImpl extends BaseDaoSpringImpl<EntityCfgRole> implements IEntityCfgRoleDao {

	public EntityCfgRoleDaoImpl() throws Exception {
		super(EntityCfgRole.class);
	}
}
