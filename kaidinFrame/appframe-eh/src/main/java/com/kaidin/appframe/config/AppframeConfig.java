package com.kaidin.appframe.config;

import java.net.URL;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaidin.appframe.exception.AppframeException;
import com.kaidin.common.util.DataTypeUtil;
import com.kaidin.common.util.PropertyUtil;
/**
 * 读取配置使用
 * @version 1.0
 * @author kaidin@foxmail.com
 * @date 2015-10-23下午01:51:48
 */
public class AppframeConfig {
	private static final transient Logger logger = LoggerFactory.getLogger(AppframeConfig.class);
	private static boolean IS_INIT = false;	// 是否从配置文件读取配置文件
	private static int MAX_QUERY_LIMIT = 0;	// 一次查询最大的数据条数
	private static String DATA_SOURCE_NAME = null;	// 数据源名称
	private static String RPT_DATA_SOURCE_NAME = null;	// 报表数据源名称
	private static boolean IS_EJB3 = false;	// 是否是ejb3
	
	
	private static void init() {
		if (!IS_INIT) {
			readConfig();
			IS_INIT = true;
		}
	}
	private synchronized static void readConfig() {
		try {
			URL resourceUrl = AppframeConfig.class.getClassLoader().getResource("cfg/appframeConfig.properties");
			String propertiesFileName = resourceUrl.getFile();
			HashMap<String, String> propertyMap = PropertyUtil.readPropertyFile(propertiesFileName);
			if (logger.isDebugEnabled()) {
				logger.debug("read cfg/appframeConfig.properties.");
				for (String key: propertyMap.keySet()) {
					logger.debug(key + ":" + propertyMap.get(key));
				}
			}
			
			String valueStr = propertyMap.get(AppframeConstant.PropertiesKey.MAX_RESULTS);
			MAX_QUERY_LIMIT = DataTypeUtil.getAsInteger(valueStr);
			if (1 > MAX_QUERY_LIMIT) {
				// 配得不对劲的话就用默认的
				MAX_QUERY_LIMIT = AppframeConstant.MAX_QUERY_LIMIT;
			}
			
			DATA_SOURCE_NAME = propertyMap.get(AppframeConstant.PropertiesKey.DATA_SOURCE);
			RPT_DATA_SOURCE_NAME = propertyMap.get(AppframeConstant.PropertiesKey.RPT_DATA_SOURCE);
			
			String isEjb3Str = propertyMap.get(AppframeConstant.PropertiesKey.IS_EJB3);
			if (null != isEjb3Str) {
				IS_EJB3 = DataTypeUtil.getAsBoolean(isEjb3Str);
			} else {
				logger.error("read appframeConfig.properties with isEjb3:" + isEjb3Str);
			}
			
			logger.debug("read appframeConfig.properties success.");
		} catch (Exception e) {
			logger.error("read appframeConfig.properties faild.");
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
	}
	
	
	public static int getMaxQueryLimit() {
		init();
		return MAX_QUERY_LIMIT;
	}
	
	public static String getDataSourceName() {
		init();
		return DATA_SOURCE_NAME;
	}
	public static String getRptDataSourceName() {
		init();
		return RPT_DATA_SOURCE_NAME;
	}
	
	public static boolean getIsEjb3() {
		init();
		return IS_EJB3;
	}
}
