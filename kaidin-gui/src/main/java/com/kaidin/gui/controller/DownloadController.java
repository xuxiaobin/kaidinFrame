package com.kaidin.gui.controller;

import com.kaidin.common.util.query.PageData;
import com.kaidin.common.util.query.PageRequest;
import com.kaidin.gui.controller.model.BoDownloadFile;
import com.kaidin.gui.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/download.html")
public class DownloadController {
	private static final transient Logger LOGGER = LoggerFactory.getLogger(DownloadController.class);
	
	@RequestMapping
	public ModelAndView load(ModelMap modelMap) {
		ModelAndView result = new ModelAndView("download");
		
		File dir = new File("/usr/local/isa/tomcat8/bin");
		PageRequest pageReq = new PageRequest();
		pageReq.setOffset(1);
		pageReq.setLimit(15);
		PageData<List<BoDownloadFile>> dataContainer = FileService.getDirFiles(dir, null);
		result.addObject("", dataContainer);
		
		return result;
	}
}
