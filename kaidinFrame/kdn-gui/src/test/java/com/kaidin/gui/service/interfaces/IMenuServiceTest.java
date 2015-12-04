package com.kaidin.gui.service.interfaces;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kaidin.gui.model.VoMenu;

public class IMenuServiceTest {
	private static IMenuManageService menuManageService;

	
	@BeforeClass
	public static void init() {
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
		menuManageService = (IMenuManageService) ctx.getBean("menuManageService");
//		AnnotationTransactionAspect
	}
	
	@Test
	public void testGetMenu() {
		List<VoMenu> voList = menuManageService.getMenu();
		
		if (null != voList && !voList.isEmpty()) {
			System.out.println("voList.size:" + voList.size());
			for (VoMenu vo: voList) {
				printMenu(vo, "");
			}
		} else {
			System.out.println("没有获取到菜单");
		}
	}
	
	private void printMenu(VoMenu vo, String formatStr) {
		if (null != vo) {
			System.out.print(formatStr);
			System.out.println(vo.getName() + "-->" + vo.getUrl());
			
			List<VoMenu> subMenuList = vo.getSubmenuList();
			if (null != subMenuList && !subMenuList.isEmpty()) {
				for (VoMenu subVo: subMenuList) {
					printMenu(subVo, formatStr + "\t");
				}
			}
		}
	}
}
