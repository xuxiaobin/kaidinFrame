package com.kaidin.db.dao.impl;
// Generated 2015-10-21 10:09:50 by Hibernate Tools 3.3.0.GA
import org.springframework.stereotype.Repository;

import com.kaidin.appframe.dao.impl.BaseDaoImpl;
import com.kaidin.db.dao.interfaces.IEntityRltUserRoleDao;
import com.kaidin.db.entity.EntityRltUserRole;
/**
 * Dao implements for domain model class EntityRltUserRole.
 * @see com.kaidin.db.dao.impl.EntityRltUserRole
 */
@Repository(value="IEntityRltUserRoleDao")
public class EntityRltUserRoleDaoImpl extends BaseDaoImpl<EntityRltUserRole> implements IEntityRltUserRoleDao {

	public EntityRltUserRoleDaoImpl() throws Exception {
		super(EntityRltUserRole.class);
	}
}
