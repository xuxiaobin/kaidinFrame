package com.kaidin.db.dao.impl;
// Generated 2017-5-29 14:00:59 by Hibernate Tools 3.3.0.GA
import org.springframework.stereotype.Repository;

import com.kaidin.appframe.service.impl.BaseDaoSpringImpl;
import com.kaidin.db.dao.interfaces.IEntityMemberInfoDao;
import com.kaidin.db.entity.EntityMemberInfo;
/**
 * Dao implements for domain model class EntityMemberInfo.
 * @see com.kaidin.db.dao.impl.EntityMemberInfo
 */
@Repository(value="IEntityMemberInfoDao")
public class EntityMemberInfoDaoImpl extends BaseDaoSpringImpl<EntityMemberInfo> implements IEntityMemberInfoDao {

	public EntityMemberInfoDaoImpl() throws Exception {
		super(EntityMemberInfo.class);
	}
}
