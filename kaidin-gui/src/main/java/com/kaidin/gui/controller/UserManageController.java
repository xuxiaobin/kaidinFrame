package com.kaidin.gui.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kaidin.common.util.query.DataContainer;
import com.kaidin.common.util.query.PageLoadConfig;
import com.kaidin.db.entity.EntityCfgUser;
import com.kaidin.gui.common.constant.GuiConstType;
import com.kaidin.gui.service.interfaces.IUserManageService;

@Controller
@RequestMapping("/userManage.html")
public class UserManageController {
	private static final transient Logger logger = LoggerFactory.getLogger(UserManageController.class);
	
	@Autowired
	private IUserManageService userManageService;
	
	
	public UserManageController() {
	}
	
	@RequestMapping
	public ModelAndView load(ModelMap modelMap) {
		ModelAndView result = new ModelAndView("userManage");
		
		PageLoadConfig pageConfig = new PageLoadConfig();
		DataContainer<EntityCfgUser> dataContainer = userManageService.queryUser(pageConfig);
		result.addObject(GuiConstType.DATA_CONTAINER, dataContainer);
		
		return result;
	}
	
	@RequestMapping(params = "method=register")
	public ModelAndView register(HttpServletRequest request, ModelMap modelMap) {
		ModelAndView result = new ModelAndView("student_add");
		
		try {
			EntityCfgUser newUser = new EntityCfgUser();
			userManageService.register(newUser);
			modelMap.put("addstate", "添加成功");
		} catch(Exception e){
			logger.error(e.getMessage(), e);
			modelMap.put("addstate", "添加失败");
		}
		
		return result;
	}
	
	/**
	 * 销户（注意不是退出登录）
	 * @return
	 */
	@RequestMapping(params = "method=unregister")
	public ModelAndView unregister(long userId) {
		ModelAndView result = new ModelAndView("student_add");
		
		try {
			userManageService.unregister(userId);
		} catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return result;
	}
}
