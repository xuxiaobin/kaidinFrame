package com.kaidin.gui.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kaidin.common.util.query.DataContainer;
import com.kaidin.common.util.query.PageLoadConfig;
import com.kaidin.db.dao.interfaces.IEntityCfgRoleDao;
import com.kaidin.db.entity.EntityCfgRole;
import com.kaidin.gui.common.constant.GuiConstType;
import com.kaidin.gui.service.interfaces.IRoleManageService;

@Service
public class RoleManageService implements IRoleManageService {
	private static final transient Logger logger = LoggerFactory.getLogger(RoleManageService.class);
	
	@Resource(name = IEntityCfgRoleDao.RESOURCE_NAME)
	private IEntityCfgRoleDao roleDao;
	
	
	/**
	 * 获取系统角色
	 * @param pageConfig
	 * @return
	 */
	@Override
	public DataContainer<EntityCfgRole> queryRole(PageLoadConfig pageConfig) {
		DataContainer<EntityCfgRole> result = new DataContainer<EntityCfgRole>(pageConfig);
		
		try {
			String hqlWhere = EntityCfgRole.P_Status + "!=:statu";
			result = roleDao.queryEntities(hqlWhere, new String[]{"statu"},
					new Object[]{GuiConstType.Status.DELETE}, pageConfig);
//			List dataList = roleDao.queryEntities();
//			result.setDataList(dataList);
		} catch (Exception e) {
			result.setErrorCode(GuiConstType.ErrorCode.SERVICE_ERR);
			result.setErrorMsg("服务错误");
			logger.error(e.getMessage());
			System.out.println(e);
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 新建角色
	 * @param newRole
	 * @return
	 */
	@Override
	public EntityCfgRole createRole(EntityCfgRole newRole) {
		try {
			newRole.setStatus(GuiConstType.Status.OK);
			newRole.setCreateTime(new Date());
			newRole = roleDao.save(newRole);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return newRole;
	}
}
