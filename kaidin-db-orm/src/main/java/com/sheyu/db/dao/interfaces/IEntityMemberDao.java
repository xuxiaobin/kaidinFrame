package com.sheyu.db.dao.interfaces;

import java.util.HashMap;
import java.util.List;

import com.sheyu.db.entity.EntityMember;

public interface IEntityMemberDao {
	void save(EntityMember entityMember);

	int getUserCount();

	List<EntityMember> getUserInfo();

	EntityMember getUserByName(HashMap<String, String> map);
}
