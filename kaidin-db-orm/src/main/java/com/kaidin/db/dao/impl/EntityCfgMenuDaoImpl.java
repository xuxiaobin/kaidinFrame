package com.kaidin.db.dao.impl;
// Generated 2017-5-29 14:00:59 by Hibernate Tools 3.3.0.GA
import org.springframework.stereotype.Repository;

import com.kaidin.appframe.service.impl.BaseDaoSpringImpl;
import com.kaidin.db.dao.interfaces.IEntityCfgMenuDao;
import com.kaidin.db.entity.EntityCfgMenu;
/**
 * Dao implements for domain model class EntityCfgMenu.
 * @see com.kaidin.db.dao.impl.EntityCfgMenu
 */
@Repository(value="IEntityCfgMenuDao")
public class EntityCfgMenuDaoImpl extends BaseDaoSpringImpl<EntityCfgMenu> implements IEntityCfgMenuDao {

	public EntityCfgMenuDaoImpl() throws Exception {
		super(EntityCfgMenu.class);
	}
}
