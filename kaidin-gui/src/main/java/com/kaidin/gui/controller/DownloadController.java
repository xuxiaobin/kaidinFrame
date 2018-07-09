package com.kaidin.gui.controller;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kaidin.common.util.query.PageData;
import com.kaidin.common.util.query.PageLoadConfig;
import com.kaidin.gui.common.constant.GuiConstType;
import com.kaidin.gui.model.BoDownloadFile;
import com.kaidin.gui.service.FileService;

@Controller
@RequestMapping("/download.html")
public class DownloadController {
	private static final transient Logger logger = LoggerFactory.getLogger(DownloadController.class);
	
	
	@RequestMapping
	public ModelAndView load(ModelMap modelMap) {
		ModelAndView result = new ModelAndView("download");
		
		File dir = new File("/usr/local/isa/tomcat8/bin");
		PageLoadConfig pageLoadCfg = new PageLoadConfig();
		pageLoadCfg.setOffset(1);
		pageLoadCfg.setLimit(15);
		PageData<BoDownloadFile> dataContainer = FileService.getDirFiles(dir, null);
		result.addObject(GuiConstType.DATA_CONTAINER, dataContainer);
		
		return result;
	}
}
