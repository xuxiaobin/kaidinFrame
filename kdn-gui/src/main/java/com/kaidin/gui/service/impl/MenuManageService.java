package com.kaidin.gui.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kaidin.db.dao.interfaces.IEntityCfgMenuDao;
import com.kaidin.db.entity.EntityCfgMenu;
import com.kaidin.gui.common.constant.GuiConstType;
import com.kaidin.gui.model.VoMenu;
import com.kaidin.gui.service.interfaces.IMenuManageService;

@Service
public class MenuManageService implements IMenuManageService {
	private static final transient Logger logger = LoggerFactory.getLogger(MenuManageService.class);
	
	@Resource(name = IEntityCfgMenuDao.RESOURCE_NAME)
	private IEntityCfgMenuDao menuDao;
	
	
	/**
	 * 从一级菜单开始获取所有菜单
	 * @return
	 */
	@Override
	public List<VoMenu> getMenu() {
		List<VoMenu> result = null;
		
		try {
			String hqlWhere = EntityCfgMenu.P_Level + "=:level"
					+ " 	and " + EntityCfgMenu.P_Status + "=:status"
					+ " order by " + EntityCfgMenu.P_Sort;
			List<EntityCfgMenu> menuList = menuDao.queryEntities(hqlWhere,
					new String[]{"level", "status"},
					new Object[]{GuiConstType.Menu.LEVEL_1, GuiConstType.ErrorCode.OK});
			logger.error("sssssssssssssssssss:" + menuList.size());
			if (null != menuList && !menuList.isEmpty()) {
				result = new ArrayList<VoMenu>(menuList.size());
				for (EntityCfgMenu menu: menuList) {
					VoMenu vo = processSubMenu(menu);
					result.add(vo);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	/**
	 * 数据库实体转换为前台页面菜单对象
	 * 自动查询数据库实体的子菜单
	 * @param menu
	 * @return
	 */
	private VoMenu processSubMenu(EntityCfgMenu menu) {
		VoMenu result = null;
		
		try {
			if (null != menu) {
				result = new VoMenu();
				result.setName(menu.getName());
				result.setUrl(menu.getUrl());
				
				String hqlWhere = EntityCfgMenu.P_ParentId + "=:parentId"
						+ " 	and " + EntityCfgMenu.P_Status + "=:status"
						+ " order by " + EntityCfgMenu.P_Sort;
				List<EntityCfgMenu> menuList = menuDao.queryEntities(hqlWhere,
						new String[]{"parentId", "status"},
						new Object[]{menu.getId(), GuiConstType.ErrorCode.OK});
				if (null != menuList && !menuList.isEmpty()) {
					ArrayList<VoMenu> subMenuList = new ArrayList<VoMenu>(menuList.size());
					for (EntityCfgMenu subMenu: menuList) {
						VoMenu vo = processSubMenu(subMenu);
						subMenuList.add(vo);
					}
					result.setSubmenuList(subMenuList);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return result;
	}
}
