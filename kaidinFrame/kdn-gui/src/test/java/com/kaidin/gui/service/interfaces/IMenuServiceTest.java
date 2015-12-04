package com.kaidin.gui.service.interfaces;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.kaidin.gui.model.BoMenu;
import com.kaidin.gui.util.ServiceUtil;

public class IMenuServiceTest {
	private static IMenuManageService menuManageService;

	
	@BeforeClass
	public static void init() {
		menuManageService = (IMenuManageService) ServiceUtil.getService("menuManageService");
	}
	
	@Test
	public void testGetMenu() {
		List<BoMenu> voList = menuManageService.getMenu(1);
		
		if (null != voList && !voList.isEmpty()) {
			System.out.println("voList.size:" + voList.size());
			for (BoMenu vo: voList) {
				printMenu(vo, "");
			}
		} else {
			System.out.println("没有获取到菜单");
		}
	}
	
	private void printMenu(BoMenu vo, String formatStr) {
		if (null != vo) {
			System.out.print(formatStr);
			System.out.println(vo.getName() + "-->" + vo.getHref());
			
			List<BoMenu> subMenuList = vo.getSubMenuList();
			if (null != subMenuList && !subMenuList.isEmpty()) {
				for (BoMenu subVo: subMenuList) {
					printMenu(subVo, formatStr + "\t");
				}
			}
		}
	}
}
