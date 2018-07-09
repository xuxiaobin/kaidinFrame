package com.sheyu.db.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.sheyu.db.dao.interfaces.IEntityMemberDao;
import com.sheyu.db.entity.EntityMember;
import com.sheyu.gui.controller.login.MybatisSqlSession;

public class IEntityMemberDaoImpl implements IEntityMemberDao {
	@Override
	public void save(EntityMember entityMember) {
		SqlSession session = null;
		try {
			session = MybatisSqlSession.getSqlSession();
			IEntityMemberDao userInfoDao = session
					.getMapper(IEntityMemberDao.class);
			userInfoDao.save(entityMember);
			session.commit();
		} finally {
			if (null != session) {
				session.close();
			}
		}
	}

	public int getUserCount() {
		int count = 0;
		SqlSession session = null;
		try {
			session = MybatisSqlSession.getSqlSession();
			IEntityMemberDao userInfoDao = session
					.getMapper(IEntityMemberDao.class);
			count = userInfoDao.getUserCount();
		} finally {
			if (null != session) {
				session.close();
			}
		}
		return count;
	}

	public List<EntityMember> getUserInfo() {
		List<EntityMember> listuser = new ArrayList<EntityMember>();
		SqlSession session = null;
		try {
			session = MybatisSqlSession.getSqlSession();
			IEntityMemberDao userInfoDao = session
					.getMapper(IEntityMemberDao.class);
			listuser = userInfoDao.getUserInfo();
		} finally {
			if (null != session) {
				session.close();
			}
		}
		return listuser;
	}

	public EntityMember getUserByName(HashMap<String, String> map) {
		EntityMember userBean = new EntityMember();
		SqlSession session = null;
		try {
			session = MybatisSqlSession.getSqlSession();
			IEntityMemberDao userInfoDao = session
					.getMapper(IEntityMemberDao.class);
			userBean = userInfoDao.getUserByName(map);
		} finally {
			if (null != session) {
				session.close();
			}
		}
		return userBean;
	}
}
