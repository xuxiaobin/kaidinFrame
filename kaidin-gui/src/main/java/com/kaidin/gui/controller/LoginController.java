package com.kaidin.gui.controller;

import com.alibaba.fastjson.JSON;
import com.kaidin.biz.common.constant.CommonErrEnum;
import com.kaidin.biz.service.IUserManageService;
import com.kaidin.common.util.exception.AssertUtil;
import com.kaidin.common.util.log.LoggerUtil;
import com.kaidin.common.util.query.PageData;
import com.kaidin.common.util.query.PageDataBuilder;
import com.kaidin.db.entity.EntityCfgUser;
import com.kaidin.gui.common.constant.GuiConstType;
import com.kaidin.gui.controller.model.LoginModel;
import com.kaidin.gui.controller.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户登陆应用服务控制器
 * @author xuxiaobin	kaidin@foxmail.com
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	private static final transient Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Resource
	private IUserManageService userManageService;
	
	@RequestMapping
	public String load() {
		return "login/login";
	}

	@RequestMapping(params = "method=login", method = RequestMethod.POST)
	public ModelAndView login(LoginModel loginModel, HttpServletRequest request) {
		LoggerUtil.debug(LOGGER, "login orgReq={0}", JSON.toJSONString(loginModel));
		if (null != WebUtil.getSessionAttribute(GuiConstType.SessionKey.USER)) {
			return new ModelAndView("index");
		}

		// 检验验证码
		boolean isCaptchaOk = CaptchaController.isCaptchaOk(loginModel.getCaptchaCode());
		AssertUtil.isTrue(isCaptchaOk, CommonErrEnum.PARAMETER_ILLEGAL, "验证码错误");
		// 校验用户名密码
		PageData<EntityCfgUser> data = userManageService.login(loginModel.getLoginName(), loginModel.getPassword());
		LoggerUtil.debug(LOGGER, "login data={0}", JSON.toJSONString(data));
		AssertUtil.isTrue(data);
		EntityCfgUser user = data.getData();
		// 缓存用户信息
		HttpSession session = WebUtil.getSession();
		session.setAttribute(GuiConstType.SessionKey.USER, user);
		// 构建返回值
		ModelAndView result = new ModelAndView("index");
		result.addObject("userAlias", user);
		result.setViewName("index");

		return result;
	}

	@RequestMapping(value = "/login.json", method = RequestMethod.POST)
	@ResponseBody
	public PageData<String> login(LoginModel loginModel) {
		LoggerUtil.debug(LOGGER, "login.json orgReq={0}", JSON.toJSONString(loginModel));

		EntityCfgUser user = WebUtil.getSessionAttribute(GuiConstType.SessionKey.USER);
		if (null != user) {
			// 用户已经登陆过
			return PageDataBuilder.success(user.getAlias());
		}
		AssertUtil.isNotNull(loginModel, CommonErrEnum.PARAMETER_ILLEGAL, "参数不能为空");
		// 检验验证码
		boolean isCaptchaOk = CaptchaController.isCaptchaOk(loginModel.getCaptchaCode());
		AssertUtil.isTrue(isCaptchaOk, CommonErrEnum.PARAMETER_ILLEGAL, "验证码错误");
		// 校验用户名密码
		PageData<EntityCfgUser> data = userManageService.login(loginModel.getLoginName(), loginModel.getPassword());
		LoggerUtil.debug(LOGGER, "login data={0}", JSON.toJSONString(data));
		AssertUtil.isTrue(data);
		// 缓存用户信息
		user = data.getData();
		HttpSession session = WebUtil.getSession(true);
		session.setAttribute(GuiConstType.SessionKey.USER, user);

		return PageDataBuilder.success(user.getAlias());
	}
	
	@RequestMapping(params = "method=logout")
	public ModelAndView logout(HttpServletRequest req) {
		ModelAndView result = new ModelAndView("login/logout");
		
		HttpSession session = req.getSession(false);
		if (null != session) {
			session.invalidate();
		}
		
		return result;
	}

	/**
	 * 注销登陆
	 * @return
	 */
	@RequestMapping("/logout.json")
	@ResponseBody
	public PageData<Boolean> logout() {
		HttpSession session = WebUtil.getRequest().getSession(false);
		if (null != session) {
			session.invalidate();
		}

		return PageDataBuilder.success(true);
	}
}
