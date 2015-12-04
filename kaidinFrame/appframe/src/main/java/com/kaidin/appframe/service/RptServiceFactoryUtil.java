package com.kaidin.appframe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaidin.appframe.util.AppframeConfigUtil;

public class RptServiceFactoryUtil {
	private static final transient Logger logger = LoggerFactory.getLogger(RptServiceFactoryUtil.class);
	private static String apJndiName = "isa-qms/ApEntityManagerEjbFactory/local";
	
	
	public static Object getDAO(Class aInterfaceClass)throws Exception {
		String rptDataSource = AppframeConfigUtil.getRptDataSource();
		if (null == rptDataSource) {
			return ServiceFactory.getDAO(aInterfaceClass);
		} else {
			return ServiceFactory.getDAO(apJndiName, aInterfaceClass);
		}
	}
}
