package com.kaidin.appframe.util;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kaidin.appframe.service.ServiceFactory;
/**
 * 
 * @author 徐小斌
 * 20150325
 */
public class TimerMethodUtil {
	private static final Logger logger = LoggerFactory.getLogger(TimerMethodUtil.class);
	//里面存的是本机要跑的类名.方法名，比如ISnapShotServiceLocal.coutActiveStbByHour
	private static List<String> CONFIG_LIST = new ArrayList<String>();
	
	
	static {
		init();
	}
	
	public static void init() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String hostAddress = addr.getHostAddress();	//获得本机IP
			String hostName = addr.getHostName();	//获得本机名称
			logger.info("hostAddress:" + hostAddress + ", hostName:" + hostName);
			String sql = "select t.class_name,"
					+ " 	t.method_name"
					+ " from cfg_timer_executor t"
					+ " where t.host_ip = ?";
			conn = ServiceFactory.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, hostAddress);
//			ps.setString(2, hostName);
			rs = ps.executeQuery();
			while (rs.next()) {
				String className = rs.getString(1);
				String methodName = rs.getString(2);
				String value = className + "." + methodName;
				CONFIG_LIST.add(value.toLowerCase());
			}
			logger.info("read cfg_timer configs:");
			logger.info(new TimerMethodUtil().toString());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			ServiceFactory.close(conn, ps, rs);
		}
	}
	
	/**
	 * 自己获取调用方的类名和方法名判断当前定时器方法是否要运行
	 * 这个是获取调用者的方法，不是报表的方法，除非放到报表逻辑中
	 * @return
	 */
	public static boolean thisTimerNeedRun() {
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
		String callClassName = stackTraceElement.getClassName();
		String callMethodName = stackTraceElement.getMethodName();
		
		return needRun(callClassName, callMethodName);
	}
	/**
	 * 传入类和方法名，判断是否要运行
	 * @param clazz
	 * @param callMethodName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean needRun(Class clazz, String callMethodName) {
		return needRun(clazz.getName(), callMethodName);
	}
	/**
	 * 根据类名和方法名判断是否要运行
	 * @param callClassName
	 * @param callMethodName
	 * @return
	 */
	private static boolean needRun(String callClassName, String callMethodName) {
		Boolean isRun = false;
		if (null != callClassName && null != callMethodName) {
			String value = callClassName + "." + callMethodName;
			if (CONFIG_LIST.contains(value.toLowerCase())) {
				//读取到了是肯定
				isRun = true;
			} else {
				//获取不带包路径的class名称
				String[] tmpStrArray = callClassName.split("\\.");
				if (null != tmpStrArray) {
					callClassName = tmpStrArray[tmpStrArray.length - 1];
				}
				//获取不带括号的方法名称
				tmpStrArray = callMethodName.split("\\(");
				if (null != tmpStrArray) {
					callMethodName = tmpStrArray[0];
				}
				value = callClassName + "." + callMethodName;
				if (CONFIG_LIST.contains(value.toLowerCase())) {
					//读取不到的时候置为否定
					isRun = true;
				}
				//读取不到的时候置为否定
			}
		}
		logger.info(callClassName + "." + callMethodName + ":" + isRun);
		
		return isRun;
	}
	
	
	@Override
	public String toString() {
		StringBuilder build = new StringBuilder();
		for (String classMethod: CONFIG_LIST) {
			build.append(classMethod).append("\n");
		}
		
		return build.toString();
	}
}
