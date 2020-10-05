//package com.kaidin.gui.service.impl;
//
//import com.kaidin.common.util.query.PageData;
//import com.kaidin.common.util.query.PageRequest;
//import com.kaidin.db.dao.interfaces.IEntityCfgRoleDao;
//import com.kaidin.db.entity.EntityCfgRole;
//import com.kaidin.gui.common.constant.GuiConstType;
//import com.kaidin.gui.service.interfaces.IRoleManageService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.Date;
//
//@Service
//public class RoleManageService implements IRoleManageService {
//	private static final transient Logger logger = LoggerFactory.getLogger(RoleManageService.class);
//
//	@Resource
//	private IEntityCfgRoleDao roleDao;
//
//
//	/**
//	 * 获取系统角色
//	 * @param pageReq
//	 * @return
//	 */
//	@Override
//	public PageData<EntityCfgRole> queryRole(PageRequest pageReq) {
//		PageData<EntityCfgRole> result = new PageData<>(pageReq);
//
//			String hqlWhere = EntityCfgRole.P_Status + "!=:statu";
//			result = roleDao.queryEntities(hqlWhere, new String[]{"statu"},
//					new Object[]{GuiConstType.Status.DELETE}, pageReq);
////			List dataList = roleDao.queryEntities();
////			result.setDataList(dataList);
//
//		return result;
//	}
//
//	/**
//	 * 新建角色
//	 * @param newRole
//	 * @return
//	 */
//	@Override
//	public EntityCfgRole createRole(EntityCfgRole newRole) {
//		try {
//			newRole.setStatus("");
//			newRole.setCreateTime(new Date());
//			newRole = roleDao.save(newRole);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//
//		return newRole;
//	}
//}
