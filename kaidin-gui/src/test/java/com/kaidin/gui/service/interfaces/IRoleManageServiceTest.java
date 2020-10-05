//package com.kaidin.gui.service.interfaces;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.kaidin.common.util.query.PageData;
//import com.kaidin.common.util.query.PageLoadConfig;
//import com.kaidin.db.entity.EntityCfgRole;
//import com.kaidin.gui.common.constant.GuiConstType;
//import com.kaidin.gui.util.ServiceUtil;
//
//public class IRoleManageServiceTest {
//	private static IRoleManageService roleManageService = null;
//
//	@BeforeClass
//	public static void init() {
//		roleManageService = (IRoleManageService) ServiceUtil.getService("roleManageService");
//	}
//
//	@Test
//	public void testQueryRole() {
//		ModelAndView result = new ModelAndView("roleManage");
//
//		PageLoadConfig pageConfig = new PageLoadConfig();
//		PageData<EntityCfgRole> dataContainer = roleManageService.queryRole(pageConfig);
//		result.addObject(GuiConstType.DATA_CONTAINER, dataContainer);
//		System.out.println(dataContainer);
//	}
//
//	@Test
//	public void testCreateRole() {
//	}
//}
