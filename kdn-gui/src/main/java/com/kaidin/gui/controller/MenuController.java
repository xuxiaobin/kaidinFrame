package com.kaidin.gui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.kaidin.db.dao.interfaces.IEntityCfgMenuDao;
import com.kaidin.db.entity.EntityCfgMenu;
import com.kaidin.gui.common.constant.GuiConstType;
import com.kaidin.gui.model.VoMenu;

@Controller
public class MenuController {
	private static final transient Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	@Resource(name = IEntityCfgMenuDao.RESOURCE_NAME)
	private IEntityCfgMenuDao dao;
}
