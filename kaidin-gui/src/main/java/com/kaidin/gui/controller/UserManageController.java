package com.kaidin.gui.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.kaidin.biz.service.IUserManageService;
import com.kaidin.common.util.log.LoggerUtil;
import com.kaidin.common.util.query.PageRequest;
import com.kaidin.gui.controller.model.LoginModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kaidin.common.util.query.PageData;
import com.kaidin.db.entity.EntityCfgUser;

@Controller
@RequestMapping("/userManage")
public class UserManageController {
	private static final transient Logger LOGGER = LoggerFactory.getLogger(UserManageController.class);
	
	@Resource
	private IUserManageService userManageService;
	
	@RequestMapping
	public ModelAndView load(ModelMap modelMap) {
		ModelAndView result = new ModelAndView("userManage");
		
		PageRequest pageReq = new PageRequest();
		PageData<EntityCfgUser> dataContainer = userManageService.queryUser(pageReq);
		result.addObject("123", dataContainer);
		
		return result;
	}
	
	@RequestMapping("/register")
	public ModelAndView register(LoginModel loginModel, HttpServletRequest request) {
		LoggerUtil.debug(LOGGER, "register orgReq={0}", JSON.toJSONString(loginModel));

		ModelAndView result = new ModelAndView("student_add");
		
		EntityCfgUser newUser = new EntityCfgUser();
		newUser.setName(loginModel.getLoginName());
		newUser.setPassword(loginModel.getPassword());
		userManageService.register(newUser);

		return result;
	}
	
	/**
	 * 销户（注意不是退出登录）
	 * @return
	 */
	@RequestMapping("/unregister")
	public ModelAndView unregister(long userId) {
		ModelAndView result = new ModelAndView("student_add");
		
		userManageService.unregister(userId);

		
		return result;
	}
}
