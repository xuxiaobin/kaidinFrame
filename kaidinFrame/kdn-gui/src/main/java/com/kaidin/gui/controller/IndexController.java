package com.kaidin.gui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index.html")
public class IndexController {

	@RequestMapping
	public String load(HttpServletRequest request, ModelMap modelMap) {
		HttpSession session = request.getSession();
		modelMap.put("userAlias", session.getValue("userAlias"));
		
		
		return "index";
	}
}
