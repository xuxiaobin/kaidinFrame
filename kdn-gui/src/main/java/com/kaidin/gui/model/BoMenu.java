package com.kaidin.gui.model;

import java.util.List;

import com.kaidin.db.entity.EntityCfgMenu;

public class BoMenu extends EntityCfgMenu {
	private static final long serialVersionUID = -2276223240711204298L;
	private List<BoMenu> subMenuList;
	
	
	public BoMenu() {
	}
	public BoMenu(EntityCfgMenu menu) {
		super.setHref(menu.getHref());
		super.setName(menu.getName());
		super.setAlias(menu.getAlias());
		super.setCssClass(menu.getCssClass());
		super.setCssStyle(menu.getCssStyle());
		super.setOtherProperties(menu.getOtherProperties());
		super.setLevel(menu.getLevel());
		super.setParentId(menu.getParentId());
		super.setCode(menu.getCode());
		super.setSort(menu.getSort());
		super.setStatus(menu.getStatus());
	}
	
	public List<BoMenu> getSubMenuList() {
		return subMenuList;
	}
	public void setSubMenuList(List<BoMenu> subMenuList) {
		this.subMenuList = subMenuList;
	}
}
