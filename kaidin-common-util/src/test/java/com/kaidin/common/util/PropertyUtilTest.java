/**
 * Kaidin.com Inc.
 * Copyright (c) 2008-2018 All Rights Reserved.
 */
package com.kaidin.common.util;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class PropertyUtilTest {

	/**
	 * 读取配置文件并显示
	 * 
	 * @throws Exception
	 */
	@Test
	public void testReadPropertiesFile() throws Exception {
		String propertiesFileName = PropertyUtilTest.class.getClassLoader().getResource("dataSource.properties").getFile();
		Map<String, String> propertyMap = PropertyUtil.readPropertyFile(propertiesFileName);
		if (null != propertyMap) {
			for (Entry<String, String> entry : propertyMap.entrySet()) {
				System.out.println(entry.getKey() + "=" + entry.getValue());
			}
		}
	}

	@Test
	public void testPutProperty() throws Exception {
		// Config config = new Config();
		// PropertiesReadUtil.putProperty(config, "config.properties");
		// System.out.println(config);
	}
}
