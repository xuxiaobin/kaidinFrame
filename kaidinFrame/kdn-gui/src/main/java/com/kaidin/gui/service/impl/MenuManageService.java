package com.kaidin.gui.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kaidin.db.dao.interfaces.IEntityCfgMenuDao;
import com.kaidin.db.entity.EntityCfgMenu;
import com.kaidin.gui.common.constant.GuiConstType;
import com.kaidin.gui.model.BoMenu;
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
	public List<BoMenu> getMenu(long userId) {
		List<BoMenu> result = null;
		
		try {
			String hqlWhere = EntityCfgMenu.P_Status + "=:status"
					+ " order by " + EntityCfgMenu.P_Sort;
			List<EntityCfgMenu> menuList = menuDao.queryEntities(hqlWhere,
					new String[]{"status"},
					new Object[]{GuiConstType.ErrorCode.OK});
			logger.error("sssssssssssssssssss:" + menuList.size());
			result = convertMenu(menuList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 将数据库实体转换为树形菜单
	 * @param menuList
	 * @return
	 */
	private List<BoMenu> convertMenu(List<EntityCfgMenu> menuList) {
		List<BoMenu> result = null;
		
		if (null != menuList && !menuList.isEmpty()) {
			result = new ArrayList<>();
			Iterator<EntityCfgMenu> menuIterator = menuList.iterator();
			while (menuIterator.hasNext()) {
				EntityCfgMenu menu = menuIterator.next();
				if (GuiConstType.Menu.LEVEL_1 == menu.getLevel()) {
					menuIterator.remove();	// 删除了省的下次遍历遇到
					result.add(getMenuTree(menu, menuList));
				}
			}
		}
		
		return result;
	}
	
	private BoMenu getMenuTree(EntityCfgMenu rootMenu, List<EntityCfgMenu> menuList) {
		BoMenu result = null;
		
		if (null != rootMenu) {
			result = new BoMenu(rootMenu);
			if (null != menuList && !menuList.isEmpty()) {
				long menuId = rootMenu.getId();
				List<BoMenu> subMenuList = result.getSubMenuList();
				
				List<EntityCfgMenu> tempList = new ArrayList<>(menuList.size());
				tempList.addAll(menuList);
				Iterator<EntityCfgMenu> menuIterator = tempList.iterator();
				while (menuIterator.hasNext()) {
					EntityCfgMenu menu = menuIterator.next();
					if (menu.getParentId() == menuId) {
						// 找到该菜单对应的子菜单了
						menuIterator.remove();	// 删除了省的下次遍历遇到
						// 获取子菜单的子菜单
						BoMenu subMenu = getMenuTree(menu, tempList);
						if (null != subMenu) {
							if (null == subMenuList) {
								subMenuList = new ArrayList<>();
							}
							subMenuList.add(subMenu);
						}
					}
				}
				if (null != subMenuList) {
					result.setSubMenuList(subMenuList);
				}
			}
		}
		
		return result;
	}
}
