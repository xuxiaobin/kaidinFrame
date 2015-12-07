package com.kaidin.gui.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceUtil {
	
	public static Object getService(String serviceName) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
//		ctx = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
		return ctx.getBean(serviceName);
	}
}
