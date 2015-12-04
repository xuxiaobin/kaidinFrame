package com.kaidin.gui.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kaidin.db.dao.interfaces.IEntityStudentDao;
import com.kaidin.db.entity.EntityStudent;

@Controller
@RequestMapping("/student.html")
public class StudentController {
	private static final transient Logger logger = LoggerFactory.getLogger(StudentController.class);
	
	@Resource(name = IEntityStudentDao.RESOURCE_NAME)
	private IEntityStudentDao studentDao;
	
	
	public StudentController() {
	}
	
	@RequestMapping
	public String load(ModelMap modelMap){
		List<EntityStudent> list = studentDao.queryEntities();
		modelMap.put("list", list);
		
		return "student";
	}
	
	@RequestMapping(params = "method=add")
	public String add(HttpServletRequest request, ModelMap modelMap) throws Exception{
		return "student_add";
	}
	
	@RequestMapping(params = "method=save")
	public String save(HttpServletRequest request, ModelMap modelMap){
		String user = request.getParameter("user");
		String psw = request.getParameter("psw");
		EntityStudent st = new EntityStudent();
		st.setName(user);
		st.setPsw(psw);
		try {
			studentDao.save(st);
			modelMap.put("addstate", "添加成功");
		} catch(Exception e){
			logger.error(e.getMessage());
			modelMap.put("addstate", "添加失败");
		}
		
		return "student_add";
	}
	
	@RequestMapping(params = "method=del")
	public void del(@RequestParam("id") String id, HttpServletResponse response){
		try {
			EntityStudent st = new EntityStudent();
			st.setId(Long.valueOf(id));
			studentDao.delete(st);
			response.getWriter().print("{\"del\":\"true\"}");
		} catch(Exception e){
			logger.error(e.getMessage());
		}
	}
}
