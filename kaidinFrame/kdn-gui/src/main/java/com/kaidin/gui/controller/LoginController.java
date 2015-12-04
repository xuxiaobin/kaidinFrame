package com.kaidin.gui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kaidin.common.util.EncryptUtil;
import com.kaidin.common.util.query.DataContainer;
import com.kaidin.db.entity.EntityCfgUser;
import com.kaidin.gui.common.constant.GuiConstType;
import com.kaidin.gui.service.interfaces.IUserManageService;
/**
 * 用户登陆应用服务控制器
 * @author xuxiaobin	kaidin@foxmail.com
 *
 */
@Controller
@RequestMapping("/login.html")
public class LoginController {
	private static final transient Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private IUserManageService userManageService;
	
	
	@RequestMapping
	public String load(ModelMap modelMap) {
		return "login/login";
	}
	
	@RequestMapping(params = "method=login")
	public ModelAndView login(HttpServletRequest request, ModelMap modelMap) {
		ModelAndView result = new ModelAndView("login/login");
		
		boolean isCaptchaOk = CaptchaController.isCaptchaOk(request);
		if (isCaptchaOk) {
			String loginName = request.getParameter("loginName");
			String loginPasswd = request.getParameter("password");
			logger.debug("loginPasswd:" + EncryptUtil.md5(loginPasswd));
			
			DataContainer<EntityCfgUser> data = userManageService.login(loginName, loginPasswd);
			if (GuiConstType.ErrorCode.OK == data.getErrorCode()) {
				EntityCfgUser user = data.getDataList().get(0);
				result.addObject("userAlias", user);
				result.setViewName("index");
				HttpSession session = request.getSession();
				session.setAttribute(GuiConstType.SessionKey.USER, user);
			} else {
				result.addObject(GuiConstType.ERROR_MSG, "用户名或者密码错误");
			}
		} else {
			result.addObject(GuiConstType.ERROR_MSG, "验证码错误");
		}
		
		return result;
	}
	
	@RequestMapping(params = "method=logout")
	public ModelAndView logout(HttpServletRequest request, ModelMap modelMap) {
		ModelAndView result = new ModelAndView("login/logout");
		
		HttpSession session = request.getSession(false);
		if (null != session) {
			session.invalidate();
		}
		
		return result;
	}
}
