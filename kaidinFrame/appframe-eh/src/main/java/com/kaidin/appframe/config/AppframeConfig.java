package com.kaidin.appframe.config;

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
	private static int MAX_QUERY_LIMIT = 0;
	private static String DATA_SOURCE;
	private static String RPT_DATA_SOURCE;
	
	
	private synchronized static void init() {
		try {
			String propertiesFileName = AppframeConfig.class.getClassLoader().getResource("cfg/appframeConfig.properties").getFile();
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
			
			DATA_SOURCE = propertyMap.get(AppframeConstant.PropertiesKey.DATA_SOURCE);
			RPT_DATA_SOURCE = propertyMap.get(AppframeConstant.PropertiesKey.RPT_DATA_SOURCE);
		} catch (Exception e) {
			logger.error("read appframeConfig.properties faild.");
			logger.error(e.getMessage(), e);
			throw new AppframeException(e);
		}
	}
	
	
	public static int getMaxQueryLimit() {
		if (!IS_INIT) {
			init();
		}
		
		return MAX_QUERY_LIMIT;
	}
	
	public static String getDataSource() {
		if (!IS_INIT) {
			init();
		}
		
		return DATA_SOURCE;
	}
	
	public static String getRptDataSource() {
		if (!IS_INIT) {
			init();
		}
		
		return RPT_DATA_SOURCE;
	}
}
