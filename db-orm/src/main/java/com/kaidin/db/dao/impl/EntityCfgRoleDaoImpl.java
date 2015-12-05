package com.kaidin.db.dao.impl;
// Generated 2015-10-21 10:09:50 by Hibernate Tools 3.3.0.GA
import org.springframework.stereotype.Repository;

import com.kaidin.appframe.dao.impl.BaseDaoImpl;
import com.kaidin.db.dao.interfaces.IEntityCfgRoleDao;
import com.kaidin.db.entity.EntityCfgRole;
/**
 * Dao implements for domain model class EntityCfgRole.
 * @see com.kaidin.db.dao.impl.EntityCfgRole
 */
@Repository(value="IEntityCfgRoleDao")
public class EntityCfgRoleDaoImpl extends BaseDaoImpl<EntityCfgRole> implements IEntityCfgRoleDao {

	public EntityCfgRoleDaoImpl() throws Exception {
		super(EntityCfgRole.class);
	}
}
