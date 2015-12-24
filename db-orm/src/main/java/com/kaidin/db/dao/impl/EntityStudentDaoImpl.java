package com.kaidin.db.dao.impl;
// Generated 2015-12-12 17:00:21 by Hibernate Tools 3.3.0.GA
import org.springframework.stereotype.Repository;

import com.kaidin.appframe.service.impl.BaseDaoImpl;
import com.kaidin.db.dao.interfaces.IEntityStudentDao;
import com.kaidin.db.entity.EntityStudent;
/**
 * Dao implements for domain model class EntityStudent.
 * @see com.kaidin.db.dao.impl.EntityStudent
 */
@Repository(value="IEntityStudentDao")
public class EntityStudentDaoImpl extends BaseDaoImpl<EntityStudent> implements IEntityStudentDao {

	public EntityStudentDaoImpl() throws Exception {
		super(EntityStudent.class);
	}
}
