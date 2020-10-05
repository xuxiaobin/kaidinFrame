package com.kaidin.gui.controller;

import com.kaidin.common.util.query.PageData;
import com.kaidin.common.util.query.PageRequest;
import com.kaidin.db.entity.EntityCfgRole;
import com.kaidin.gui.service.impl.IRoleManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/roleManage.html")
public class RoleManageController {
	private static final transient Logger logger = LoggerFactory.getLogger(RoleManageController.class);
	
//	@Autowired
	private IRoleManageService roleManageService;
	
	
	public RoleManageController() {
	}
	
	@RequestMapping
	public ModelAndView load(ModelMap modelMap) {
		ModelAndView result = new ModelAndView("roleManage");
		
		PageRequest pageReq = new PageRequest();
		PageData<EntityCfgRole> dataContainer = roleManageService.queryRole(pageReq);
		result.addObject("123", dataContainer);
		
		return result;
	}
	
	@RequestMapping(params = "method=create")
	public ModelAndView createRole(HttpServletRequest request, ModelMap modelMap) {
		ModelAndView result = new ModelAndView("student_add");
		
		try {
			EntityCfgRole newRole = new EntityCfgRole();
			roleManageService.createRole(newRole);
			modelMap.put("addstate", "添加成功");
		} catch(Exception e){
			logger.error(e.getMessage(), e);
			modelMap.put("addstate", "添加失败");
		}
		
		return result;
	}
}
