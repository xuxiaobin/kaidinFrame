package com.kaidin.db.dao.impl;
// Generated 2015-10-21 10:09:50 by Hibernate Tools 3.3.0.GA
import org.springframework.stereotype.Repository;

import com.kaidin.appframe.dao.BaseDaoImpl;
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
