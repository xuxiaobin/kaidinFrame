package com.kaidin.gui.service.interfaces;

import com.kaidin.common.util.query.PageData;
import com.kaidin.common.util.query.PageLoadConfig;
import com.kaidin.db.entity.EntityCfgRole;

public interface IRoleManageService {
	/**
	 * 获取系统角色
	 * @param pageConfig
	 * @return
	 */
	PageData<EntityCfgRole> queryRole(PageLoadConfig pageConfig);
	
	/**
	 * 新建角色
	 * @param newRole
	 * @return
	 */
	EntityCfgRole createRole(EntityCfgRole newRole);
}
