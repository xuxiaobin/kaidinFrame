package com.kaidin.db.dao.impl;
// Generated 2015-10-21 10:09:50 by Hibernate Tools 3.3.0.GA
import org.springframework.stereotype.Repository;

import com.kaidin.appframe.dao.impl.BaseDaoImpl;
import com.kaidin.db.dao.interfaces.IEntityCfgMenuDao;
import com.kaidin.db.entity.EntityCfgMenu;
/**
 * Dao implements for domain model class EntityCfgMenu.
 * @see com.kaidin.db.dao.impl.EntityCfgMenu
 */
@Repository(value="IEntityCfgMenuDao")
public class EntityCfgMenuDaoImpl extends BaseDaoImpl<EntityCfgMenu> implements IEntityCfgMenuDao {

	public EntityCfgMenuDaoImpl() throws Exception {
		super(EntityCfgMenu.class);
	}
}
