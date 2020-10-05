package com.kaidin.gui.service.impl;

import com.kaidin.common.util.query.PageData;
import com.kaidin.common.util.query.PageRequest;
import com.kaidin.db.entity.EntityCfgRole;

public interface IRoleManageService {
	/**
	 * 获取系统角色
	 * @param rageRequest
	 * @return
	 */
	PageData<EntityCfgRole> queryRole(PageRequest rageRequest);
	
	/**
	 * 新建角色
	 * @param newRole
	 * @return
	 */
	EntityCfgRole createRole(EntityCfgRole newRole);
}
