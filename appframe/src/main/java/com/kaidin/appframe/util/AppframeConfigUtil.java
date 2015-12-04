package com.kaidin.appframe.util;

import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaidin.common.util.DataTypeUtil;
import com.kaidin.common.util.PropertyUtil;

public class AppframeConfigUtil {
	private static final transient Logger logger = LoggerFactory.getLogger(AppframeConfigUtil.class);
	private static ConcurrentHashMap<String, String> CFG_MAP = null;
	
	
	static {
		try {
			CFG_MAP = new ConcurrentHashMap<String, String>(
					PropertyUtil.readPropertyFile("appframeConfig.properties"));
		} catch (Exception e) {
			logger.error("read appframeConfig.properties faild.");
			logger.error(e.getMessage(), e);
		}
	}
	
	public static String getDataSource() {
		return CFG_MAP.get("dataSource");
	}
	
	public static boolean isEjb3() {
		boolean isEjb3 = false;
		
		String value = CFG_MAP.get("isEjb3");
		if (null != value) {
			isEjb3 = DataTypeUtil.getAsBoolean(value.trim());
		}
		
		return isEjb3;
	}
	
	public static String getRptDataSource() {
		return CFG_MAP.get("rptDataSource");
	}
}
